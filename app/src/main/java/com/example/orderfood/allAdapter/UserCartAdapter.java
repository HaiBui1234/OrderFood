package com.example.orderfood.allAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderfood.R;
import com.example.orderfood.allModel.CartModel;

import java.util.ArrayList;

public class UserCartAdapter extends RecyclerView.Adapter<UserCartAdapter.ViewHodelUser> {
    private Context mContext;
    private ArrayList<CartModel> cartModelArrayList;
    private InterCart interCart;
    public interface InterCart{
    void UpdateCart(CartModel cartModel);
    void RemoveCart(CartModel cartModel);
    void DeleteCart(CartModel cartModel);
    }

    public UserCartAdapter(Context mContext, InterCart interCart) {
        this.mContext = mContext;
        this.interCart = interCart;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<CartModel> cartModelArrayList){
        this.cartModelArrayList=cartModelArrayList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHodelUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.usercart_item,parent,false);
        return new ViewHodelUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodelUser holder, int position) {
    CartModel cartModel=cartModelArrayList.get(position);
    if (cartModel==null){
        return;
    }
        Glide.with(mContext).load(cartModel.getImgFood())
                .placeholder(R.drawable.ic_action_placehodel)
                .error(R.drawable.ic_action_err).into(holder.imgFood);
    holder.tvName.setText(cartModel.getNameFood());
    holder.tvPrice.setText(String.valueOf(cartModel.getPriceFood()*cartModel.getQuantityFood()));
    holder.btnQuantity.setText(String.valueOf(cartModel.getQuantityFood()));
    holder.btnRemove.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            interCart.RemoveCart(cartModel);
        }
    });
    holder.btnAddQ.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        interCart.UpdateCart(cartModel);
        }
    });
    holder.btnDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           interCart.DeleteCart(cartModel);
        }
    });
    }

    @Override
    public int getItemCount() {
        if (cartModelArrayList==null){
            return 0;
        }
        return cartModelArrayList.size();
    }

    public static class ViewHodelUser extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView tvName,tvPrice;
        Button btnQuantity,btnDelete;
        ImageButton btnRemove,btnAddQ;
        public ViewHodelUser(@NonNull View itemView) {
            super(itemView);
            imgFood=itemView.findViewById(R.id.imgFoodC);
            tvName=itemView.findViewById(R.id.tvNameC);
            tvPrice=itemView.findViewById(R.id.tvPriceC);
            btnQuantity=itemView.findViewById(R.id.btnQuantity);
            btnRemove=itemView.findViewById(R.id.btnRemoveQuantity);
            btnAddQ=itemView.findViewById(R.id.btnAddQuantity);
            btnDelete=itemView.findViewById(R.id.btnDeleteC);
        }
    }
}
