package com.example.orderfood.allActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.orderfood.DatabaseApp.MyDataBase;
import com.example.orderfood.R;
import com.example.orderfood.allAdapter.DetailAdapter;
import com.example.orderfood.allModel.CartModel;
import com.example.orderfood.allModel.FoodModel;

import java.util.ArrayList;
import java.util.Arrays;

public class DetailFood extends AppCompatActivity implements View.OnClickListener {
    ImageButton imgBack,imgCart;
    Button btnAddcart;
    ImageView imgFood;
    TextView tvName,tvPrice,tvPriceold,tvDescription,tvSaleDetail;
    GridView RecyImgOther;
    FoodModel foodModel;
    String[] ArrImg;
    ArrayList<String> stringArrayList;
    DetailAdapter detailAdapter;
    float priceSale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);
        anhXa();
        imgBack.setOnClickListener(this);
        btnAddcart.setOnClickListener(this);
        imgCart.setOnClickListener(this);
        setDataDetail();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.imgBackDetail:
            onBackPressed();
            break;
        case R.id.imgCart:
            break;
        case R.id.btnAddcart:
            AddCart();
            break;
    }
    }

    private void AddCart() {
        CartModel model=null;
        model=MyDataBase.getInstance(this).myDAO().findByID(foodModel.getIdFood());
        if (model==null){
            CartModel cartModel=new CartModel(foodModel.getIdFood(),foodModel.getNameFood(),priceSale,1,foodModel.getImageFood());
            MyDataBase.getInstance(this).myDAO().AddCart(cartModel);
            Toast.makeText(this, "Thanh cong", Toast.LENGTH_SHORT).show();
        }else {
        model.setQuantityFood(model.getQuantityFood()+1);
        MyDataBase.getInstance(this).myDAO().UpdateCart(model);
        Toast.makeText(this, "Thanh cong", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("SetTextI18n")
    private void setDataDetail() {
        Intent intent=getIntent();
        foodModel= (FoodModel) intent.getSerializableExtra("FoodModel");
        stringArrayList=new ArrayList<>();
        Glide.with(this).load(foodModel.getImageFood()).placeholder(R.drawable.ic_action_placehodel).error(R.drawable.ic_action_err).into(imgFood);
        tvName.setText(foodModel.getNameFood());
        tvPriceold.setText(String.valueOf(foodModel.getPriceFood()+"VNĐ"));
        tvPriceold.setPaintFlags(tvPriceold.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        tvDescription.setText(foodModel.getDescriptionFood());
        priceSale=foodModel.getPriceFood()-(foodModel.getPriceFood()*foodModel.getSaleFood()/100);
        tvPrice.setText(priceSale+"VNĐ");
        tvSaleDetail.setText("Giảm "+foodModel.getSaleFood()+"%");
        ArrImg=foodModel.getImageOther().split(";");
        Log.d("TAG", "setDataDetail: "+ArrImg.length);
        stringArrayList.addAll(Arrays.asList(ArrImg));
        detailAdapter=new DetailAdapter(this,stringArrayList,R.layout.imgother_item);
         RecyImgOther.setAdapter(detailAdapter);
    }
    private void anhXa() {
        imgBack=findViewById(R.id.imgBackDetail);
        imgCart=findViewById(R.id.imgCart);
        imgFood=findViewById(R.id.imgBanner);
        btnAddcart=findViewById(R.id.btnAddcart);
        tvName=findViewById(R.id.tvNameDeatail);
        tvPrice=findViewById(R.id.tvPriceDetail);
        tvPriceold=findViewById(R.id.tvPriceDetailOld);
        tvDescription=findViewById(R.id.tvDescriptionD);
        RecyImgOther=findViewById(R.id.RecyImg);
        tvSaleDetail=findViewById(R.id.tvSaleDetail);
    }


}