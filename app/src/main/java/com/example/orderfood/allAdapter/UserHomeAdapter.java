package com.example.orderfood.allAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderfood.R;
import com.example.orderfood.allActivity.DetailFood;
import com.example.orderfood.allModel.FoodModel;

import java.util.ArrayList;

public class UserHomeAdapter extends RecyclerView.Adapter<UserHomeAdapter.ViewHodelUser> implements Filterable {
    Context mContext;
    ArrayList<FoodModel> foodModelArrayList;
    ArrayList<FoodModel> foodModelArrayListOld;

    public UserHomeAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(ArrayList<FoodModel> foodModelArrayList){
        this.foodModelArrayList=foodModelArrayList;
        this.foodModelArrayListOld=foodModelArrayList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHodelUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.userhome_item,parent,false);
        return new ViewHodelUser(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHodelUser holder, int position) {
    FoodModel foodModel=foodModelArrayList.get(position);
    if (foodModel==null){
        return;
    }
        Glide.with(mContext).load(foodModel.getImageFood()).placeholder(R.drawable.ic_action_placehodel).error(R.drawable.ic_action_err).into(holder.imgFooditem);
        holder.tvnameFoodU.setText(foodModel.getNameFood());
        holder.tvSaleU.setText("Giam "+foodModel.getSaleFood()+"%");
        holder.tvpriceFoodOldU.setText(String.valueOf(foodModel.getPriceFood()+"VNĐ"));
        float pricesale=foodModel.getPriceFood()-((foodModel.getPriceFood()*foodModel.getSaleFood()))/100;
        holder.tvpriceFoodU.setText(String.valueOf(pricesale+"VNĐ"));
        holder.tvpriceFoodOldU.setPaintFlags(holder.tvpriceFoodOldU.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        holder.DetailLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, DetailFood.class);
                intent.putExtra("FoodModel",foodModel);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (foodModelArrayList==null){
            return 0;
        }
       return foodModelArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String Search=charSequence.toString();
                if (Search.isEmpty()){
                    foodModelArrayList=foodModelArrayListOld;
                }else {
                    ArrayList<FoodModel> foodModels=new ArrayList<>();
                    for (FoodModel model:foodModelArrayListOld) {
                        if (model.getNameFood().toLowerCase().contains(Search.toLowerCase())){
                            foodModels.add(model);
                        }
                    }
                    foodModelArrayList=foodModels;
                }
                FilterResults results =new FilterResults();
                results.values=foodModelArrayList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                foodModelArrayList= (ArrayList<FoodModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHodelUser extends RecyclerView.ViewHolder {
        ImageView imgFooditem;
        TextView tvnameFoodU, tvSaleU,tvpriceFoodOldU,tvpriceFoodU;
        LinearLayout DetailLine;
        public ViewHodelUser(@NonNull View itemView) {
            super(itemView);
            imgFooditem=itemView.findViewById(R.id.imgFooditem);
            tvSaleU=itemView.findViewById(R.id.tvSaleU);
            tvpriceFoodU=itemView.findViewById(R.id.tvpriceFoodU);
            tvpriceFoodOldU=itemView.findViewById(R.id.tvpriceFoodOldU);
            DetailLine=itemView.findViewById(R.id.DetailLine);
            tvnameFoodU=itemView.findViewById(R.id.tvnameFoodU);
        }
    }
}
