package com.example.orderfood.allActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.orderfood.R;
import com.example.orderfood.allAdapter.AdminOrderAdapter;
import com.example.orderfood.allModel.BillModel;
import com.example.orderfood.allModel.UserModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HistoryOrderActivity extends AppCompatActivity {
    RecyclerView RecyHis;
    ImageView imgbackHis;
    AdminOrderAdapter adminOrderAdapter;
    ArrayList<BillModel> billModelArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);
        RecyHis=findViewById(R.id.RecyHisOrder);
        imgbackHis=findViewById(R.id.imgbackHis);
        LinearLayoutManager manager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        RecyHis.setLayoutManager(manager);
        getDataOrder();
        imgbackHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void getDataOrder() {
        Intent intent=getIntent();
        UserModel  userModel= (UserModel) intent.getSerializableExtra("userModel");
        billModelArrayList=new ArrayList<>();
        adminOrderAdapter=new AdminOrderAdapter(this,userModel);
        Log.d("TAG", "getDataOrder: "+userModel.getId_User());
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("UserModel").child(userModel.getId_User()).child("BillModel");
        reference.addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BillModel billModel=snapshot.getValue(BillModel.class);
                if (billModel==null){
                    return;
                }
                billModelArrayList.add(billModel);
                adminOrderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adminOrderAdapter.setData(billModelArrayList);
        RecyHis.setAdapter(adminOrderAdapter);

    }
}