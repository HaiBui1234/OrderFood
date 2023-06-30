package com.example.orderfood.allFragment.adminFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.orderfood.R;
import com.example.orderfood.allAdapter.AdminOrderAdapter;
import com.example.orderfood.allModel.BillModel;
import com.example.orderfood.allModel.UserModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminOrderFragment extends Fragment {

    RecyclerView RecyOrder;
    AdminOrderAdapter adminOrderAdapter;
    ArrayList<BillModel> billModelArrayList;
    public AdminOrderFragment() {
        // Required empty public constructor
    }

    public static AdminOrderFragment newInstance(String param1, String param2) {
        AdminOrderFragment fragment = new AdminOrderFragment();
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
        return inflater.inflate(R.layout.fragment_admin_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyOrder=view.findViewById(R.id.RecyOrder);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        RecyOrder.setLayoutManager(manager);
        getDataOrder();
    }

    private void getDataOrder() {
        Intent intent=getActivity().getIntent();
        UserModel userModel= (UserModel) intent.getSerializableExtra("userModel");
        billModelArrayList=new ArrayList<>();
        adminOrderAdapter=new AdminOrderAdapter(getActivity(),userModel);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("BillModel");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BillModel billModel=snapshot.getValue(BillModel.class);
                if (billModel==null){
                    return;
                }
                billModelArrayList.add(billModel);
                adminOrderAdapter.notifyDataSetChanged();
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
        adminOrderAdapter.setData(billModelArrayList);
        RecyOrder.setAdapter(adminOrderAdapter);

    }
}