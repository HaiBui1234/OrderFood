package com.example.orderfood.allAdapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.R;
import com.example.orderfood.allModel.BillModel;
import com.example.orderfood.allModel.UserModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.ViewHodelAdmin> {
    Context mContext;
    private ArrayList<BillModel> billModelArrayList;
    UserModel userModel;

    public AdminOrderAdapter(Context mContext, UserModel userModel) {
        this.mContext = mContext;
        this.userModel = userModel;
    }

    public void setData(ArrayList<BillModel> billModelArrayList){
        this.billModelArrayList=billModelArrayList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHodelAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.adminorder_item,parent,false);
        return new ViewHodelAdmin(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodelAdmin holder, int position) {
        BillModel billModel=billModelArrayList.get(position);
        if (billModel==null){
            return;
        }
        holder.tvMa.setText(billModel.getIdBill());
        holder.tvEmail.setText(billModel.getEmailBill());
        holder.tvPhone.setText(String.valueOf(billModel.getPhoneBill()));
        holder.tvName.setText(billModel.getNameUser());
        holder.tvAdress.setText(billModel.getAdressBill());
        holder.tvMenu.setText(billModel.getProductBill());
        holder.tvDate.setText(billModel.getDateBill());
        holder.tvSumMoney.setText(String.valueOf(billModel.getMoneyBill()));
        holder.tvPTTT.setText(billModel.getPaymentMethodBill());
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference reference=database.getReference("BillModel").child(billModel.getIdBill()).child("checkBill");
        final DatabaseReference reference1=database.getReference("UserModel").child(billModel.getIdUser())
                                                    .child("BillModel").child(billModel.getIdBill())
                                                     .child("checkBill");
        if(!userModel.isActive()){
            holder.ckCheck.setVisibility(View.INVISIBLE);
        }else {
            holder.ckCheck.setVisibility(View.VISIBLE);
        }
        holder.ckCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (holder.ckCheck.isChecked()){
                    reference.setValue(true);
                    reference1.setValue(true);
                    holder.idLine.setBackgroundResource(R.drawable.border_btnhuy);
                }else {
                    reference.setValue(false);
                    reference1.setValue(false);
                    holder.idLine.setBackgroundResource(0);
                }
            }
        });
        holder.ckCheck.setChecked(billModel.getCheckBill());
        if (billModel.getCheckBill()){
            holder.idLine.setBackgroundResource(R.drawable.border_btnhuy);
        }

    }

    @Override
    public int getItemCount() {
        if (billModelArrayList==null){
            return 0;
        }
        return billModelArrayList.size();

    }

    public static class ViewHodelAdmin extends RecyclerView.ViewHolder {
        TextView tvMa,tvEmail,tvName,tvPhone,tvAdress,tvMenu,tvDate,tvSumMoney,tvPTTT;
        CheckBox ckCheck;
        LinearLayout idLine;
        public ViewHodelAdmin(@NonNull View itemView) {
            super(itemView);
            tvMa=itemView.findViewById(R.id.tvCode);
            tvEmail=itemView.findViewById(R.id.tvMailU);
            tvPhone=itemView.findViewById(R.id.tvPhone);
            tvName=itemView.findViewById(R.id.tvNameU);
            tvAdress=itemView.findViewById(R.id.tvAdressU);
            tvMenu=itemView.findViewById(R.id.tvMenu);
            tvDate=itemView.findViewById(R.id.tvDateO);
            tvSumMoney=itemView.findViewById(R.id.SumMoney);
            tvPTTT=itemView.findViewById(R.id.tvPT);
            ckCheck=itemView.findViewById(R.id.CkCheckTT);
            idLine=itemView.findViewById(R.id.idLine);
        }
    }
}
