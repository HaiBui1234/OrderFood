package com.example.orderfood.allActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.allModel.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class SIgnUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edEmailDK,edPassword;
    RadioButton RDQTV,RDUser;
    TextView tvDn;
    Button btnDK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        anhXa();
        btnDK.setOnClickListener(this);
        tvDn.setOnClickListener(this);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.tvDn:
               startActivity(new Intent(this,LoginActivity.class));
               break;
           case R.id.btnDK:
               registerAccout();
               break;
       }
    }

    private void registerAccout() {
        String email=edEmailDK.getText().toString().trim();
        String password=edPassword.getText().toString().trim();
        boolean isActive;
        isActive= !RDUser.isChecked();
        if (email.isEmpty()||password.isEmpty()){
            return;
        }
        FirebaseAuth auth =FirebaseAuth.getInstance();
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("UserModel");
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UserModel userModel=new UserModel(auth.getUid(),email,isActive,null);
                            FirebaseUser user =auth.getCurrentUser();
                            if (user==null){
                                return;
                            }
                            reference.child(user.getUid()).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   Toast.makeText(SIgnUpActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                                   Intent intent=new Intent(SIgnUpActivity.this,MainActivity.class);
                                   intent.putExtra("IDUser",isActive);
                                   intent.putExtra("userModel",userModel);
                                   startActivity(intent);
                               }
                           });

                        } else {
                            Toast.makeText(SIgnUpActivity.this, "fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void anhXa() {
        edEmailDK=findViewById(R.id.edEmailDK);
        edPassword=findViewById(R.id.edPassworDK);
        btnDK=findViewById(R.id.btnDK);
        tvDn=findViewById(R.id.tvDn);
        RDQTV=findViewById(R.id.RDQTV);
        RDUser=findViewById(R.id.RDUser);
    }


}