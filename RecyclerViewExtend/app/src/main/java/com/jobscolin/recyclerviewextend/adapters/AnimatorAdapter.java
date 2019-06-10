package com.jobscolin.recyclerviewextend.adapters;

import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.jobscolin.recyclerviewextend.helper.ViewHelper;

/**
 * @author : XuDonglin
 * @time : 2019/06/06
 * @describe :
 */
public  abstract class AnimatorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RecyclerView.Adapter mAdapter;
    private int lastPosition=-1;

    public AnimatorAdapter(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return mAdapter.onCreateViewHolder(viewGroup, i);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        mAdapter.onBindViewHolder(viewHolder, i);

        int adapterPosition = viewHolder.getAdapterPosition();
        Log.i("Test", "onBindViewHolder: "+adapterPosition+": "+lastPosition);
        if (adapterPosition > lastPosition) {
            ObjectAnimator objectAnimator = setObjectAnimator(viewHolder);
            objectAnimator.start();
            lastPosition = adapterPosition;
        } else {
            ViewHelper.clear(viewHolder.itemView);
        }
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount();
    }

    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
        mAdapter.registerAdapterDataObserver(observer);
    }

    @Override
    public void unregisterAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
        mAdapter.unregisterAdapterDataObserver(observer);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mAdapter.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mAdapter.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        mAdapter.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        mAdapter.onViewDetachedFromWindow(holder);
    }

    /**
     * 设置item的动画
     *
     * @param viewHolder
     * @return
     */
    public abstract ObjectAnimator setObjectAnimator(RecyclerView.ViewHolder viewHolder);

}
