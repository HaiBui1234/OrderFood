package com.example.orderfood.allFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.orderfood.R;
import com.example.orderfood.allAdapter.SlideAdapter;
import com.example.orderfood.allAdapter.UserHomeAdapter;
import com.example.orderfood.allModel.FoodModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {
    ViewPager VpSlide;
    CircleIndicator CrSlide;
    SlideAdapter slideAdapter;
    UserHomeAdapter userHomeAdapter;
    ArrayList<FoodModel> modelArrayListSlide;
    ArrayList<FoodModel> foodModelArrayList;
    Timer mTimer;
    SearchView SearchFood;
    RecyclerView RecyHome;
    public HomeFragment() {
        // Required empty public constructor
    }
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VpSlide=view.findViewById(R.id.VpSlide);
        CrSlide=view.findViewById(R.id.CrSlide);
        RecyHome=view.findViewById(R.id.RecyHome);
        SearchFood=view.findViewById(R.id.SearchFood);
        GridLayoutManager manager=new GridLayoutManager(getActivity(),2,RecyclerView.VERTICAL,false);
        RecyHome.setLayoutManager(manager);
        getDataSlide();
        VpSlide.setAdapter(slideAdapter);
        CrSlide.setViewPager(VpSlide);
        slideAdapter.registerDataSetObserver(CrSlide.getDataSetObserver());
        autoSlide();
        getDataFood();
        RecyHome.setAdapter(userHomeAdapter);
        SearchFood.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                userHomeAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                userHomeAdapter.getFilter().filter(s);
                return false;
            }
        });

    }

    private void getDataFood() {
        foodModelArrayList=new ArrayList<>();
        userHomeAdapter=new UserHomeAdapter(getActivity());
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("FoodModel");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                FoodModel foodModel=snapshot.getValue(FoodModel.class);
                if (foodModel==null){
                    return;
                }
                foodModelArrayList.add(foodModel);
                userHomeAdapter.notifyDataSetChanged();
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
        userHomeAdapter.setData(foodModelArrayList);
    }

    private void getDataSlide() {
        slideAdapter=new SlideAdapter(getActivity());
        modelArrayListSlide=new ArrayList<>();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("FoodModel");
        Query query=reference.orderByChild("popularFood").equalTo(true);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                FoodModel foodModel=snapshot.getValue(FoodModel.class);
                Log.d("TAG", "onChildAdded: aaaa");
                if (foodModel==null){
                    return;
                }
                Log.d("TAG", "onChildAdded: "+foodModel.getImageFood());
                modelArrayListSlide.add(foodModel);
                slideAdapter.notifyDataSetChanged();
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
        slideAdapter.setData(modelArrayListSlide);
    }
    private void autoSlide() {
        if (mTimer==null){
            mTimer=new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                    if (VpSlide.getCurrentItem()==modelArrayListSlide.size()-1){
                    VpSlide.setCurrentItem(0);
                    }else {
                        VpSlide.setCurrentItem(VpSlide.getCurrentItem()+1);
                    }
                    }
                });
            }
        },1000,3000);
    }
}