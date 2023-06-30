package com.example.orderfood.allAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.R;
import com.example.orderfood.allModel.BillModel;

import java.util.ArrayList;

public class SheetAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<BillModel> billModelArrayList;
    private int type;

    public SheetAdapter(Context mContext, ArrayList<BillModel> billModelArrayList, int type) {
        this.mContext = mContext;
        this.billModelArrayList = billModelArrayList;
        this.type = type;
    }

    @Override
    public int getCount() {
        if (billModelArrayList!=null){
            return billModelArrayList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            convertView =inflater.inflate(type,null);

        }

        return null;
    }

}
