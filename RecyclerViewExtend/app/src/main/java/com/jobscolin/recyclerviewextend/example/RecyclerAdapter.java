package com.jobscolin.recyclerviewextend.example;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jobscolin.recyclerviewextend.R;

import java.util.ArrayList;

/**
 * @author : XuDonglin
 * @time : 2019/05/30
 * @describe :
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> data;

    public RecyclerAdapter(Context mContext, ArrayList<String>list) {
        this.mContext = mContext;
        this.data = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.recycler_item_title);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_layout, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.title.setText("这是第"+i);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}
