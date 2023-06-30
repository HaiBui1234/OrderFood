package com.example.orderfood.allAdapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderfood.R;
import com.example.orderfood.allActivity.UpdateFoodActivity;
import com.example.orderfood.allModel.FoodModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminHomeAdapter extends RecyclerView.Adapter<AdminHomeAdapter.ViewHodelAdmin> implements Filterable {
    private Context mContext;
    private ArrayList<FoodModel> foodModelArrayList;
    private ArrayList<FoodModel> foodModelArrayListOld;
    public AdminHomeAdapter(Context mContext) {
        this.mContext = mContext;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setDataAdmin(ArrayList<FoodModel> foodModelArrayList){
        this.foodModelArrayList=foodModelArrayList;
        this.foodModelArrayListOld=foodModelArrayList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHodelAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.adminhome_item,parent,false);
        return new ViewHodelAdmin(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodelAdmin holder, int position) {
    FoodModel foodModel=foodModelArrayList.get(position);
    if (foodModel==null){
        return;
    }
    float priceSale= Math.round(foodModel.getPriceFood()-((foodModel.getPriceFood()*foodModel.getSaleFood())/100));
     String popular="";
     if (foodModel.isPopularFood()){
         popular="Có";
     }else {
         popular="Không";
     }
    holder.tvName.setText(foodModel.getNameFood());
    holder.tvSale.setText(String.valueOf("Giam"+foodModel.getSaleFood()));
    holder.tvPriceOld.setText(String.valueOf(foodModel.getPriceFood()));
    holder.tvDescription.setText(foodModel.getDescriptionFood());
    holder.tvPrice.setText(String.valueOf(priceSale+" VNĐ"));
    holder.tvPriceOld.setPaintFlags(holder.tvPriceOld.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
    holder.tvPopular.setText("Phổ biến: "+popular);
    Glide.with(mContext).load(foodModel.getImageFood()).placeholder(R.drawable.ic_action_placehodel).error(R.drawable.ic_action_err).into(holder.imgFood);

    holder.imgEdit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(mContext, UpdateFoodActivity.class);
            intent.putExtra("FoodModel",foodModel);
            mContext.startActivity(intent);
        }
    });
    holder.imgDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FirebaseDatabase database=FirebaseDatabase.getInstance();
            DatabaseReference reference=database.getReference("FoodModel");
            AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
            builder.setTitle("DELETE");
            builder.setMessage("Do you want delete?");
            builder.setNegativeButton("NO",null);
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    reference.child(foodModel.getIdFood()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(mContext, "Thanh cong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            builder.show();
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


    public static class ViewHodelAdmin extends RecyclerView.ViewHolder {
        ImageView imgFood;
        ImageButton imgEdit,imgDelete;
        TextView tvName,tvPrice,tvSale,tvPriceOld,tvPopular,tvDescription;
        public ViewHodelAdmin(@NonNull View itemView) {
            super(itemView);
            imgFood=itemView.findViewById(R.id.imgFooditem);
            tvName=itemView.findViewById(R.id.tvnameFoodItem);
            tvPrice=itemView.findViewById(R.id.tvPriceitem);
            tvPriceOld=itemView.findViewById(R.id.tvPriceOle);
            tvSale=itemView.findViewById(R.id.tvSaleItem);
            tvPopular=itemView.findViewById(R.id.tvPopular);
            tvDescription=itemView.findViewById(R.id.tvDescriptionitem);
            imgEdit=itemView.findViewById(R.id.imgEdit);
            imgDelete=itemView.findViewById(R.id.imgDelete);
        }
    }

}
