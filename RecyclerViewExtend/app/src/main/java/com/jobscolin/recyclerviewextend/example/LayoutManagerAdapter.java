package com.jobscolin.recyclerviewextend.example;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jobscolin.recyclerviewextend.R;

import java.util.List;

/**
 * @author : xdl
 * @time : 2019/06/12
 * @describe :
 */
public class LayoutManagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<String>data;

    public LayoutManagerAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.layout_manager_recycler_btn);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_manager_recycler_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ViewHolder)viewHolder).btn.setText(data.get(i));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
