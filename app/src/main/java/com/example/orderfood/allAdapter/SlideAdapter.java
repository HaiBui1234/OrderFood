package com.example.orderfood.allAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.example.orderfood.R;
import com.example.orderfood.allModel.FoodModel;

import java.util.ArrayList;

public class SlideAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<FoodModel> foodModelArrayList;

    public SlideAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(ArrayList<FoodModel> foodModelArrayList){
        this.foodModelArrayList=foodModelArrayList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        if (foodModelArrayList==null){
            return 0;
        }
        return foodModelArrayList.size();
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.slide_item,container,false);
        FoodModel foodModel=foodModelArrayList.get(position);
        ImageView imageView =view.findViewById(R.id.imgsilde);
        TextView tvSaleS=view.findViewById(R.id.tvSaleS);
        if (foodModel!=null){
            Glide.with(mContext).load(foodModel.getImageFood()).placeholder(R.drawable.ic_action_placehodel).error(R.drawable.ic_action_err).into(imageView);
            tvSaleS.setText(String.valueOf("Giảm "+foodModel.getSaleFood())+"%");
        }
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
