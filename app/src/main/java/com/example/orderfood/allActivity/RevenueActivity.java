package com.example.orderfood.allActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orderfood.R;
import com.example.orderfood.allAdapter.DoanhThuAdapter;
import com.example.orderfood.allModel.BillModel;
import com.example.orderfood.allModel.RevenueModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class RevenueActivity extends AppCompatActivity {
    RecyclerView RecyDoanhThu;
    Button btnTuNgay,btnDenNgay,btnDoanhThu;
    ImageView imgBack;
    EditText edtTuNgay,edtDenNgay;
    int mDay,mMonth,mYear;
    TextView tongDT;
    ArrayList<RevenueModel> revenueModelArrayList;
    DoanhThuAdapter doanhThuAdapter;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue);
        anhXa();
        LinearLayoutManager manager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        RecyDoanhThu.setLayoutManager(manager);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                mYear=calendar.get(Calendar.YEAR);
                mMonth=calendar.get(Calendar.MONTH);
                mDay=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog=new DatePickerDialog(RevenueActivity.this,0,mDateTuNgay, mYear,mMonth,mDay);
                dialog.show();
            }
        });
        btnDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                mYear=calendar.get(Calendar.YEAR);
                mMonth=calendar.get(Calendar.MONTH);
                mDay=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog=new DatePickerDialog(RevenueActivity.this,0,mDateDenNgay, mYear,mMonth,mDay);
                dialog.show();
            }
        });
        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getDataDoanhThu();
            }
        });
    }

    private void getDataDoanhThu() {
        if (edtDenNgay.getText().toString().isEmpty()||edtTuNgay.getText().toString().isEmpty()){
            return;
        }
        final float[] a = {0};
        revenueModelArrayList=new ArrayList<>();
        doanhThuAdapter=new DoanhThuAdapter(this);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("RevenueModel");
        Query query=reference.orderByKey().startAt(edtTuNgay.getText().toString()).endAt(edtDenNgay.getText().toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot revenueSnapshot : dateSnapshot.getChildren()) {
                        RevenueModel revenueAmount = revenueSnapshot.getValue(RevenueModel.class);
                        a[0] = a[0] +revenueAmount.getPriceSum();
                        revenueModelArrayList.add(revenueAmount);
                    }
                }
                tongDT.setText(String.valueOf("Tổng doang thu: "+a[0]));
                doanhThuAdapter.notifyDataSetChanged();
                Log.d("TAG", "onDataChange: "+a[0]);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });
        doanhThuAdapter.setData(revenueModelArrayList);
        RecyDoanhThu.setAdapter(doanhThuAdapter);
    }

    DatePickerDialog.OnDateSetListener mDateTuNgay=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            mYear=i;
            mMonth=i1;
            mDay=i2;
            GregorianCalendar calendar=new GregorianCalendar(mYear,mMonth,mDay);
            edtTuNgay.setText(format.format(calendar.getTime()));
        }
    };
    DatePickerDialog.OnDateSetListener mDateDenNgay=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            mYear=i;
            mMonth=i1;
            mDay=i2;
            GregorianCalendar calendar=new GregorianCalendar(mYear,mMonth,mDay);
            edtDenNgay.setText(format.format(calendar.getTime()));
        }
    };
    private void anhXa() {
        btnTuNgay=findViewById(R.id.btnTuNgay);
        btnDenNgay=findViewById(R.id.btnDenNgay);
        btnDoanhThu=findViewById(R.id.btnDoanhThu);
        imgBack=findViewById(R.id.imgbackReven);
        edtTuNgay=findViewById(R.id.edtTuNgay);
        edtDenNgay=findViewById(R.id.edtDenNgay);
        RecyDoanhThu=findViewById(R.id.RecyDoanhThu);
        tongDT=findViewById(R.id.tongDT);
    }
}