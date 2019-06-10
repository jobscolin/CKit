package com.jobscolin.recyclerviewextend.adapters;

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
 * @time : 2019/06/10
 * @describe :
 */
public class ItemAnimatorExampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mList;

    public ItemAnimatorExampleAdapter(Context context, @NonNull ArrayList<String> mData) {
        this.mContext = context;
        this.mList = mData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.animator_recycler_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ViewHolder) viewHolder).msg.setText(mList.get(i));
//        Log.i("Test", "onBindViewHolder: "+viewHolder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView msg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.animator_msg);
        }
    }
}

