package com.example.orderfood.allActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.orderfood.R;
import com.example.orderfood.allModel.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class SpScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_screen);
        autoLogin();
    }

    private void autoLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseAuth auth=FirebaseAuth.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    auth.fetchSignInMethodsForEmail(Objects.requireNonNull(user.getEmail()))
                            .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                    if (task.isSuccessful()) {
                                        SignInMethodQueryResult result = task.getResult();
                                        List<String> signInMethods = result.getSignInMethods();
                                        if (signInMethods != null && !signInMethods.isEmpty()) {
                                            getDataUser(user.getUid());
                                            Log.d("TAG", "Người dùng tồn tại.");
                                        } else {
                                            // Người dùng không tồn tại
                                            startActivity(new Intent(SpScreenActivity.this, LoginActivity.class));
                                            Log.d("TAG", "Người dùng không tồn tại.");
                                        }
                                    } else {
                                        // Xảy ra lỗi
                                        Log.e("TAG", "Lỗi khi kiểm tra người dùng", task.getException());
                                    }
                                }
                            });
                } else {
                    startActivity(new Intent(SpScreenActivity.this, LoginActivity.class));
                }
            }
        }, 2000);
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
                Intent intent=new Intent(SpScreenActivity.this, MainActivity.class);
                intent.putExtra("IDUser",userModel.isActive());
                intent.putExtra("userModel",userModel);
                startActivity(intent);
                finish();
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
}