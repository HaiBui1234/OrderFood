package com.example.orderfood.allActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.allModel.FoodModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFoodActivity extends AppCompatActivity {
    ImageButton imgback;
    EditText edName,edDesscription,edPrice,edSale,imgFood,imgBanner,imgOther;
    Button btnAddFood;
    CheckBox ckPopular;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        //
        anhXa();
        //
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFood();
            }
        });
    }

    private void AddFood() {
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
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("FoodModel");
        String id=reference.push().getKey();
        FoodModel foodModel=new FoodModel(id,nameFood,desscriptionFood,priceFood,saleFood,imageFood,blPopular,imagebanner,imageOther);
        assert id != null;
        reference.child(id).setValue(foodModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddFoodActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                edName.setText("");
                edDesscription.setText("");
                edPrice.setText("");
                edSale.setText("");
                imgFood.setText("");
                imgBanner.setText("");
                imgOther.setText("");
            }
        });
    }

    private void anhXa() {
        imgback=findViewById(R.id.imgback);
        edName=findViewById(R.id.nameFood);
        edDesscription=findViewById(R.id.descriptionFood);
        edPrice=findViewById(R.id.priceFood);
        edSale=findViewById(R.id.saleFood);
        imgFood=findViewById(R.id.imageFood);
        imgBanner=findViewById(R.id.imageBanner);
        imgOther=findViewById(R.id.imageOther);
        ckPopular=findViewById(R.id.ckPopular);
        btnAddFood=findViewById(R.id.btnAddFood);
    }

}