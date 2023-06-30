package com.example.orderfood.allActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.orderfood.R;
import com.example.orderfood.allFragment.AccountFragment;
import com.example.orderfood.allFragment.CartFragment;
import com.example.orderfood.allFragment.ContactFragment;
import com.example.orderfood.allFragment.FeedbackFragment;
import com.example.orderfood.allFragment.HomeFragment;
import com.example.orderfood.allFragment.adminFragment.AdminFeedbackFragment;
import com.example.orderfood.allFragment.adminFragment.AdminHomeFragment;
import com.example.orderfood.allFragment.adminFragment.AdminOrderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView BottomHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        Intent intent =getIntent();
        boolean isActive= intent.getBooleanExtra("IDUser",false);
//        Log.d("tag", "onCreate: "+isActive);
        if (isActive){
            replaceFragment(new AdminHomeFragment());
            BottomHome.getMenu().findItem(R.id.id_Contact).setVisible(false);
        }else {
            replaceFragment(new HomeFragment());
        }
        BottomHome.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.id_Home:
                        if (isActive){
                            replaceFragment(new AdminHomeFragment());
                        }else {
                            replaceFragment(new HomeFragment());
                        }
                        break;
                    case R.id.id_Cart:
                        if (!isActive){
                            replaceFragment(new CartFragment());
                        }else {
                            replaceFragment(new AdminOrderFragment());
                        }
                        break;
                    case R.id.id_Feedback:
                        if (isActive){
                            replaceFragment(new AdminFeedbackFragment());
                        }else {
                            replaceFragment(new FeedbackFragment());
                        }
                        break;
                    case R.id.id_Contact:
                        replaceFragment(new ContactFragment());
                        break;
                    case R.id.id_Account:
//                        FirebaseAuth.getInstance().signOut();
//                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        replaceFragment(new AccountFragment());
                        break;

                }
                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.id_FrameHome,fragment);
        transaction.commit();
    }

    private void anhXa() {
        BottomHome=findViewById(R.id.id_BottomHome);
    }

}