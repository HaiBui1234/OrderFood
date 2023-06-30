package com.example.orderfood.allActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orderfood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassActivity extends AppCompatActivity {
    EditText edtEmail;
    Button btnSend,btnHuyS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        edtEmail=findViewById(R.id.id_EmailPass);
        btnSend=findViewById(R.id.btnSendemail);
        btnHuyS=findViewById(R.id.btnHuyS);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtEmail.getText().toString().isEmpty()){
                    Toast.makeText(ChangePassActivity.this, "Hãy nhập email", Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(edtEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ChangePassActivity.this, "Kiểm tra email của bạn", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(ChangePassActivity.this, "Faill", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
        btnHuyS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangePassActivity.this,LoginActivity.class));
            }
        });
    }
}