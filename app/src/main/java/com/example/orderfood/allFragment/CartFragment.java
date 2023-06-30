package com.example.orderfood.allFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfood.DatabaseApp.MyDataBase;
import com.example.orderfood.R;
import com.example.orderfood.allAdapter.UserCartAdapter;
import com.example.orderfood.allModel.BillModel;
import com.example.orderfood.allModel.CartModel;
import com.example.orderfood.allModel.RevenueModel;
import com.example.orderfood.allModel.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {
    RecyclerView RecyCart;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<CartModel> cartModelArrayList;
    BillModel billModel;
    UserCartAdapter userCartAdapter;
    TextView tvMoney;
    Button btnOrder;
    float sumPrice=0;
    UserModel userModel;
    public CartFragment() {
        // Required empty public constructor
    }
    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXa(view);
        Intent intent=getActivity().getIntent();
        userModel= (UserModel) intent.getSerializableExtra("userModel");
        LinearLayoutManager manager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        RecyCart.setLayoutManager(manager);
        getDataCart();
        billModel=new BillModel(cartModelArrayList);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             orderFood();
            }
        });
    }

    private void orderFood() {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("BillModel");
        DatabaseReference referenceUser=database.getReference("UserModel");
        DatabaseReference referenceRevenue=database.getReference("RevenueModel");
        BottomSheetDialog sheetDialog=new BottomSheetDialog(getActivity());
        sheetDialog.setContentView(R.layout.sheetorder_item);
        TextView tvproductBill=sheetDialog.findViewById(R.id.tvproductBill);
        TextView tvMoneyBill=sheetDialog.findViewById(R.id.tvMoneyBill);
        EditText edtTT=sheetDialog.findViewById(R.id.edtTT);
        EditText edtname=sheetDialog.findViewById(R.id.nameUser);
        EditText edtSdt=sheetDialog.findViewById(R.id.edtNumberP);
        EditText edtAdress=sheetDialog.findViewById(R.id.edtAdress);
        Button btnHuy=sheetDialog.findViewById(R.id.btnHuyD);
        Button btnOrder=sheetDialog.findViewById(R.id.btnOrder);
        tvMoneyBill.setText(String.valueOf(sumPrice+" VNĐ"));
        tvproductBill.setText(billModel.getProductBill());
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetDialog.cancel();
            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String method=edtTT.getText().toString();
                String name=edtname.getText().toString();
                int sdt= Integer.parseInt(edtSdt.getText().toString());
                String adress=edtAdress.getText().toString();
                Date date=new Date();
                //
                final String id=reference.push().getKey();
                BillModel model=new BillModel(id,userModel.getId_User(),userModel.getEmail_User(),name,method,sdt,sumPrice,adress,cartModelArrayList,format.format(date),false);
                if (id!=null){
                    reference.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getActivity(), "Thanh cong", Toast.LENGTH_SHORT).show();
                            setDataSumMoney();
                            MyDataBase.getInstance(getActivity()).myDAO().DeleteAll();
                            cartModelArrayList= (ArrayList<CartModel>) MyDataBase.getInstance(getActivity()).myDAO().getAllCart();
                            userCartAdapter.setData(cartModelArrayList);
                            userCartAdapter.notifyDataSetChanged();
                            sheetDialog.cancel();
                        }
                    });
                    referenceUser.child(userModel.getId_User()).child("BillModel").child(id).setValue(model);
                    String idRevenue=referenceRevenue.push().getKey();
                    RevenueModel revenueModel=new RevenueModel(idRevenue,id,sumPrice,format.format(date));
                    if (idRevenue!=null) {
                        Log.d("TAG", "onClick: "+format.format(date));
                        String date2=format.format(date);
                        referenceRevenue.child(date2).child(idRevenue).setValue(revenueModel);
                    }
                }
            }
        });

        sheetDialog.show();
    }

    private void getDataCart() {
        userCartAdapter=new UserCartAdapter(getActivity(), new UserCartAdapter.InterCart() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void UpdateCart(CartModel cartModel) {
                cartModel.setQuantityFood(cartModel.getQuantityFood()+1);
                MyDataBase.getInstance(getActivity()).myDAO().UpdateCart(cartModel);
                userCartAdapter.notifyDataSetChanged();
                setDataSumMoney();
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void RemoveCart(CartModel cartModel) {
                if (cartModel.getQuantityFood()>1){
                    cartModel.setQuantityFood(cartModel.getQuantityFood()-1);
                    MyDataBase.getInstance(getActivity()).myDAO().UpdateCart(cartModel);
                    setDataSumMoney();
                }else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setTitle("DELETE");
                    builder.setMessage("Do you want delete ?");
                    builder.setNegativeButton("NO",null);
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MyDataBase.getInstance(getActivity()).myDAO().DeleteCart(cartModel);
                            cartModelArrayList= (ArrayList<CartModel>) MyDataBase.getInstance(getActivity()).myDAO().getAllCart();
                            userCartAdapter.setData(cartModelArrayList);
                            userCartAdapter.notifyDataSetChanged();
                            setDataSumMoney();
                        }
                    });
                    builder.show();
                }
                userCartAdapter.notifyDataSetChanged();

            }

            @Override
            public void DeleteCart(CartModel cartModel) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("DELETE");
                builder.setMessage("Do you want delete ?");
                builder.setNegativeButton("NO",null);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyDataBase.getInstance(getActivity()).myDAO().DeleteCart(cartModel);
                        cartModelArrayList= (ArrayList<CartModel>) MyDataBase.getInstance(getActivity()).myDAO().getAllCart();
                        userCartAdapter.setData(cartModelArrayList);
                        userCartAdapter.notifyDataSetChanged();
                        setDataSumMoney();
                    }
                });
                builder.show();
            }
        });
        cartModelArrayList=new ArrayList<>();
        cartModelArrayList= (ArrayList<CartModel>) MyDataBase.getInstance(getActivity()).myDAO().getAllCart();
        userCartAdapter.setData(cartModelArrayList);
        setDataSumMoney();
        RecyCart.setAdapter(userCartAdapter);
    }
    public void setDataSumMoney(){
        if (cartModelArrayList==null){
            tvMoney.setText(String.valueOf("0"));
        }else {
            sumPrice=Math.round(MyDataBase.getInstance(getActivity()).myDAO().sumMoney());
            tvMoney.setText(String.valueOf(sumPrice));
        }
    }
    public void anhXa(View view){
        RecyCart=view.findViewById(R.id.RecyCart);
        tvMoney=view.findViewById(R.id.tvMoney);
        btnOrder=view.findViewById(R.id.btnOrder);
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}