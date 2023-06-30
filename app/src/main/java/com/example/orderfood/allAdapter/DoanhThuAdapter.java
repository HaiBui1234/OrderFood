package com.example.orderfood.allAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.R;
import com.example.orderfood.allModel.RevenueModel;

import java.util.ArrayList;

public class DoanhThuAdapter extends RecyclerView.Adapter<DoanhThuAdapter.ViewHodelD> {
    private Context mContext;
    private ArrayList<RevenueModel> revenueModelArrayList;

    public DoanhThuAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(ArrayList<RevenueModel> revenueModelArrayList){
        this.revenueModelArrayList=revenueModelArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHodelD onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.doanhthu_item, parent, false);
        return new ViewHodelD(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodelD holder, int position) {
        RevenueModel revenueModel = revenueModelArrayList.get(position);
        if (revenueModel == null) {
            return;
        }
        holder.tvMaDon.setText(revenueModel.getMaDon());
        holder.tvDate.setText(revenueModel.getDateOrder());
        holder.tvSumMoney.setText(String.valueOf(revenueModel.getPriceSum()));
    }

    @Override
    public int getItemCount() {
        if (revenueModelArrayList == null) {
            return 0;
        }
        return revenueModelArrayList.size();

    }

    public static class ViewHodelD extends RecyclerView.ViewHolder {
        TextView tvMaDon, tvDate, tvSumMoney;

        public ViewHodelD(@NonNull View itemView) {
            super(itemView);
            tvMaDon = itemView.findViewById(R.id.MaDon);
            tvDate = itemView.findViewById(R.id.dateOrder);
            tvSumMoney = itemView.findViewById(R.id.DoanhThu);
        }
    }
}
