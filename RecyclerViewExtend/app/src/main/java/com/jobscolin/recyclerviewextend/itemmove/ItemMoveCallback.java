package com.jobscolin.recyclerviewextend.itemmove;

import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Property;
import android.view.View;

import java.util.Collections;
import java.util.List;

/**
 * @author : xdl
 * @time : 2019/11/22
 * @describe :
 */
public class ItemMoveCallback<T> extends ItemTouchHelper.Callback {

    private List<T>data;
    private RecyclerView.Adapter mAdapter;

    public ItemMoveCallback(List<T> data, RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
        this.data = data;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags;
        if (viewHolder instanceof FixedViewHolder) {
            return 0;
        }
        dragFlags =
                ItemTouchHelper.LEFT | ItemTouchHelper.UP | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN;
        int swipeFlags=0;
        return makeMovementFlags(dragFlags,swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }

        if (target instanceof FixedViewHolder) {
            return false;
        }

        if (data == null || data.size() < 2) {
            return false;
        }

        int from = viewHolder.getAdapterPosition();
        int to = target.getAdapterPosition();
        Collections.swap(data, from, to);
        mAdapter.notifyItemMoved(from,to);
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            clearActivatingAnim(viewHolder.itemView);
            startActivatingAnim(viewHolder.itemView,1f,1.2f,200);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder) {
        clearActivatingAnim(viewHolder.itemView);
        startActivatingAnim(viewHolder.itemView,1.2f,1f,200);
        super.clearView(recyclerView, viewHolder);
    }

    private void startActivatingAnim(View view, float from, float to, long duration) {
        Object tag = view.getTag();
        if (tag instanceof ObjectAnimator) {
            return;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, scaleProperty, from, to);
        animator.setDuration(duration);
        animator.start();
        view.setTag(animator);
    }

    private void clearActivatingAnim(View view) {
        Object tag = view.getTag();
        if (tag instanceof ObjectAnimator) {
            ObjectAnimator animator = (ObjectAnimator) tag;
            animator.cancel();
            view.setTag(null);
        }
    }

    private ScaleProperty scaleProperty = new ScaleProperty("scale");

    public static class ScaleProperty extends Property<View, Float> {
        public ScaleProperty(String name) {
            super(Float.class, name);
        }

        @Override
        public Float get(View object) {
            return object.getScaleX();
        }

        @Override
        public void set(View object, Float value) {
            object.setScaleX(value);
            object.setScaleY(value);
        }
    }


}
