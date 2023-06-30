package com.example.orderfood.allActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
     TextView tvDK;
     EditText edEmail,edPassword;
     Button btnLogin,btnQuen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();
        tvDK.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnQuen.setOnClickListener(this);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.tvDK:
                startActivity(new Intent(this,SIgnUpActivity.class));
                break;
            case R.id.btnLogin:
                LoginAccouct();
                break;
            case R.id.btnQuen:
                startActivity(new Intent(this,ChangePassActivity.class));
                break;
        }
    }

    private void LoginAccouct() {
        String email=edEmail.getText().toString();
        String password=edPassword.getText().toString();
        if (email.isEmpty()||password.isEmpty()){
            return;
        }
        FirebaseAuth auth =FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = auth.getCurrentUser();
                            if (user==null){
                                return;
                            }
                            getDataUser(user.getUid());
                        } else {
                            Toast.makeText(LoginActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getDataUser(String IDUser) {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("UserModel");
        Query findUser=reference.orderByChild("id_User").equalTo(IDUser);
        findUser.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UserModel userModel =snapshot.getValue(UserModel.class);
                if (userModel==null){
                    return;
                }
                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("IDUser",userModel.isActive());
                intent.putExtra("userModel",userModel);
                startActivity(intent);
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

    }
    private void anhXa() {
        tvDK=findViewById(R.id.tvDK);
        edEmail=findViewById(R.id.id_EmailLogin);
        edPassword=findViewById(R.id.id_PasswordLogin);
        btnLogin=findViewById(R.id.btnLogin);
        btnQuen=findViewById(R.id.btnQuen);
    }

}