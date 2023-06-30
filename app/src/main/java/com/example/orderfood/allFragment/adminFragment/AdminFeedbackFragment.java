package com.example.orderfood.allFragment.adminFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orderfood.R;
import com.example.orderfood.allAdapter.FeedBackAdapter;
import com.example.orderfood.allModel.FeedbackModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class AdminFeedbackFragment extends Fragment {
     RecyclerView RecyF;
     ArrayList<FeedbackModel> feedbackModelArrayList;
     FeedBackAdapter feedBackAdapter;
    public AdminFeedbackFragment() {
        // Required empty public constructor
    }

    public static AdminFeedbackFragment newInstance(String param1, String param2) {
        AdminFeedbackFragment fragment = new AdminFeedbackFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_feedback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyF=view.findViewById(R.id.RecyF);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        RecyF.setLayoutManager(manager);
        getDataF();
    }

    private void getDataF() {
        feedbackModelArrayList=new ArrayList<>();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("FeedbackModel");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                FeedbackModel feedbackModel=snapshot.getValue(FeedbackModel.class);
                feedbackModelArrayList.add(feedbackModel);
                feedBackAdapter.notifyDataSetChanged();
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
        feedBackAdapter=new FeedBackAdapter(getActivity(),feedbackModelArrayList);
        RecyF.setAdapter(feedBackAdapter);
    }

}