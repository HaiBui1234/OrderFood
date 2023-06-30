package com.example.orderfood.allActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.allModel.FoodModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateFoodActivity extends AppCompatActivity {
    ImageView imgback;
    EditText edName,edDesscription,edPrice,edSale,imgFood,imgBanner,imgOther;
    Button btnUpdateFood;
    CheckBox ckPopular;
    FoodModel foodModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);
        anhXa();
        Intent intent=getIntent();
        foodModel= (FoodModel) intent.getSerializableExtra("FoodModel");
        setDataOld();
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnUpdateFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateFood();
            }
        });


    }

    private void UpdateFood() {
        String nameFood=edName.getText().toString().trim();
        String desscriptionFood=edDesscription.getText().toString().trim();
        float priceFood=Float.parseFloat( edPrice.getText().toString().trim());
        float saleFood=Float.parseFloat(edSale.getText().toString().trim());
        String imageFood=imgFood.getText().toString().trim();
        String imagebanner=imgBanner.getText().toString().trim();
        String imageOther=imgOther.getText().toString().trim();
        boolean blPopular= ckPopular.isChecked();
        if (nameFood.isEmpty()||desscriptionFood.isEmpty()||String.valueOf(priceFood).isEmpty()||String.valueOf(saleFood).isEmpty()||imagebanner.isEmpty()||imageFood.isEmpty()){
            Toast.makeText(this, "Khong de trong", Toast.LENGTH_SHORT).show();
            return;
        }
        FoodModel foodModelUp=new FoodModel(foodModel.getIdFood(),nameFood,desscriptionFood,priceFood,saleFood,imageFood,blPopular,imagebanner,imageOther);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("FoodModel");
        reference.child(foodModel.getIdFood()).updateChildren(foodModelUp.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(UpdateFoodActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anhXa() {
        imgback=findViewById(R.id.imgbackUp);
        edName=findViewById(R.id.nameFoodUp);
        edDesscription=findViewById(R.id.descriptionFoodUp);
        edPrice=findViewById(R.id.priceFoodUp);
        edSale=findViewById(R.id.saleFoodUp);
        imgFood=findViewById(R.id.imageFoodUp);
        imgBanner=findViewById(R.id.imageBannerUp);
        imgOther=findViewById(R.id.imageOtherUp);
        ckPopular=findViewById(R.id.ckPopularUp);
        btnUpdateFood=findViewById(R.id.btnUpdateFood);
    }
    private void setDataOld(){
        edName.setText(foodModel.getNameFood());
        edDesscription.setText(foodModel.getDescriptionFood());
        edPrice.setText(String.valueOf(foodModel.getPriceFood()));
        edSale.setText(String.valueOf(foodModel.getSaleFood()));
        imgFood.setText(foodModel.getImageFood());
        imgBanner.setText(foodModel.getImageBanner());
        imgOther.setText(foodModel.getImageOther());
        ckPopular.setChecked(foodModel.isPopularFood());

    }
}