package com.example.orderfood.allFragment.adminFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.orderfood.R;
import com.example.orderfood.allActivity.AddFoodActivity;
import com.example.orderfood.allAdapter.AdminHomeAdapter;
import com.example.orderfood.allModel.FoodModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminHomeFragment extends Fragment {
    SearchView SVFood;
    RecyclerView recyFood;
    FloatingActionButton fl_AddFood;
    AdminHomeAdapter adapter;
    ArrayList<FoodModel> foodModelArrayList;
    public AdminHomeFragment() {
        // Required empty public constructor
    }

    public static AdminHomeFragment newInstance(String param1, String param2) {
        AdminHomeFragment fragment = new AdminHomeFragment();
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
        return inflater.inflate(R.layout.fragment_admin_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SVFood=view.findViewById(R.id.id_SearchAdmin);
        recyFood=view.findViewById(R.id.id_recyAdminHome);
        fl_AddFood=view.findViewById(R.id.fl_AddFood);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyFood.setLayoutManager(manager);
        fl_AddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(getActivity(),AddFoodActivity.class));
            }
        });
        SVFood.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        getDataFood();
        adapter=new AdminHomeAdapter(getActivity());
        adapter.setDataAdmin(foodModelArrayList);
        recyFood.setAdapter(adapter);
    }

    private void getDataFood() {
        foodModelArrayList=new ArrayList<>();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("FoodModel");
        reference.addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                FoodModel foodModel=snapshot.getValue(FoodModel.class);
                if (foodModel==null){
                    return;
                }
                foodModelArrayList.add(foodModel);
                Log.d("TAG", "onChildAdded: "+foodModel.getNameFood());
                adapter.notifyDataSetChanged();
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                FoodModel foodModel=snapshot.getValue(FoodModel.class);
                if (foodModel==null){
                    return;
                }
                for (int i=0;i<foodModelArrayList.size();i++
                     ) {
                    if (foodModel.getIdFood().equals(foodModelArrayList.get(i).getIdFood())){
                        foodModelArrayList.set(i,foodModel);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                FoodModel foodModel=snapshot.getValue(FoodModel.class);
                if (foodModel==null){
                    return;
                }
                for (int i=0;i<foodModelArrayList.size();i++
                ) {
                    if (foodModel.getIdFood().equals(foodModelArrayList.get(i).getIdFood())){
                        foodModelArrayList.remove(i);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
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