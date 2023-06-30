package com.example.orderfood.allFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.orderfood.R;
import com.example.orderfood.allActivity.HistoryOrderActivity;
import com.example.orderfood.allActivity.LoginActivity;
import com.example.orderfood.allActivity.RevenueActivity;
import com.example.orderfood.allModel.UserModel;
import com.google.firebase.auth.FirebaseAuth;


public class AccountFragment extends Fragment {
    LinearLayout tvSignOut,LineHistory,LineChangePass;
    TextView tvDoanhThu;
    Intent intent1 ;
    public AccountFragment() {
        // Required empty public constructor
    }
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvSignOut=view.findViewById(R.id.tvSignOut);
        LineHistory=view.findViewById(R.id.LineHistory);
        tvDoanhThu=view.findViewById(R.id.tvDoanhThu);
        LineChangePass=view.findViewById(R.id.ChangePassword);
        Intent intent=getActivity().getIntent();
        UserModel userModel= (UserModel) intent.getSerializableExtra("userModel");
        LineChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(getActivity());
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setContentView(R.layout.changepass_dialog);
                Button btnHuyM=dialog.findViewById(R.id.btnHuyM);
                Button btnDoi=dialog.findViewById(R.id.btnDoi);
                Window window=dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                btnHuyM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btnDoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                dialog.show();
            }
        });
        if (userModel.isActive()){
            intent1=new Intent(getActivity(), RevenueActivity.class);
            intent1.putExtra("userModel",userModel);
            tvDoanhThu.setText("Báo cáo doanh thu");
        }else {
            intent1=new Intent(getActivity(), HistoryOrderActivity.class);
            intent1.putExtra("userModel",userModel);
            tvDoanhThu.setText("Lịch sử đặt hàng");
        }
        LineHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent1);
            }
        });
        tvSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("Đăng xuất");
                builder.setMessage("Bạn có chắc ?");
                builder.setNegativeButton("NO",null);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }
                });
                builder.show();
            }
        });
    }
}