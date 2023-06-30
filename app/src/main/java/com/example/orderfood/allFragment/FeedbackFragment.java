package com.example.orderfood.allFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.allModel.FeedbackModel;
import com.example.orderfood.allModel.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackFragment extends Fragment {
    EditText edtName,edtSDT,edtEmail,edtComment;
    Button btnSend;
    public static FeedbackFragment newInstance(String param1, String param2) {
        FeedbackFragment fragment = new FeedbackFragment();
        return fragment;
    }

    public FeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtName=view.findViewById(R.id.HotenF);
        edtSDT=view.findViewById(R.id.sdtF);
        edtEmail=view.findViewById(R.id.emailF);
        edtComment=view.findViewById(R.id.commentF);
        btnSend=view.findViewById(R.id.btnSend);
        Intent intent=getActivity().getIntent();
        UserModel userModel= (UserModel) intent.getSerializableExtra("userModel");
        edtEmail.setText(userModel.getEmail_User());
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendFeedBack();
            }
        });
    }

    private void SendFeedBack() {
        String name=edtName.getText().toString();
        int sdt=Integer.parseInt(edtSDT.getText().toString());
        String email=edtEmail.getText().toString();
        String comment=edtComment.getText().toString();
        if (name.isEmpty() || email.isEmpty() || comment.isEmpty() || String.valueOf(sdt).isEmpty()){
            return;
        }
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("FeedbackModel");
        String id=reference.push().getKey();
        if (id!=null){
            FeedbackModel feedbackModel=new FeedbackModel(id,name,sdt,email,comment);
            reference.child(id).setValue(feedbackModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getActivity(), "Đã gửi phản hồi", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}