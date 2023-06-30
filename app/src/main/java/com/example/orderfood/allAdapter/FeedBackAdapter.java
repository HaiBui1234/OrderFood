package com.example.orderfood.allAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.R;
import com.example.orderfood.allModel.FeedbackModel;

import java.util.ArrayList;

public class FeedBackAdapter extends RecyclerView.Adapter<FeedBackAdapter.ViewHodelF> {
    private Context mContext;
    private ArrayList<FeedbackModel> feedbackModelArrayList;

    public FeedBackAdapter(Context mContext, ArrayList<FeedbackModel> feedbackModelArrayList) {
        this.mContext = mContext;
        this.feedbackModelArrayList = feedbackModelArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHodelF onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.feedback_item,parent,false);

        return new ViewHodelF(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodelF holder, int position) {
        FeedbackModel feedbackModel=feedbackModelArrayList.get(position);
        if (feedbackModel==null){
            return;
        }
        holder.tvEmail.setText(feedbackModel.getEmailFeedBack());
        holder.tvPhanHoi.setText(feedbackModel.getComment());
    }

    @Override
    public int getItemCount() {
        if (feedbackModelArrayList==null){
            return 0;
        }
        return feedbackModelArrayList.size();

    }

    public static class ViewHodelF extends RecyclerView.ViewHolder {
        TextView tvEmail,tvPhanHoi;
        public ViewHodelF(@NonNull View itemView) {
            super(itemView);
            tvEmail=itemView.findViewById(R.id.EmailFeed);
            tvPhanHoi=itemView.findViewById(R.id.CommentFeed);
        }
    }
}
