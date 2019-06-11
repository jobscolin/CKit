package com.jobscolin.recyclerviewextend.itemanimators;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;

/**
 * @author : Xdl
 * @time : 2019/06/11
 * @describe : 左滑进入
 */
public class SlideInLeftAnimator extends BaseAnimator {

    public SlideInLeftAnimator() {

    }

    public SlideInLeftAnimator(Interpolator interpolator) {
        mInterpolator = interpolator;
    }

    /**
     * 动画移除之前
     *
     * @param viewHolder
     */
    @Override
    protected void preAnimateRemove(RecyclerView.ViewHolder viewHolder) {

    }

    /**
     * 动画添加之前
     *
     * @param viewHolder
     */
    @Override
    protected void preAnimateAdd(RecyclerView.ViewHolder viewHolder) {
        View view=viewHolder.itemView;
        view.setTranslationX(-view.getRootView().getMeasuredWidth() );
    }

    /**
     * 移除item动画实现
     *
     * @param viewHolder
     */
    @Override
    protected void animateRemoveImpl(RecyclerView.ViewHolder viewHolder) {
        View view = viewHolder.itemView;
        ViewPropertyAnimator animate = view.animate();
        animate.translationX(-view.getRootView().getMeasuredWidth())
                .setDuration(getRemoveDuration())
                .setListener(new DefaultRemoveAnimatorListenerAdpater(viewHolder,animate))
                .start();
    }

    /**
     * 添加item动画实现
     *
     * @param viewHolder
     */
    @Override
    protected void animateAddImpl(RecyclerView.ViewHolder viewHolder) {
        View view = viewHolder.itemView;
        ViewPropertyAnimator animate = view.animate();
        animate.translationX(0)
                .setDuration(getAddDuration())
                .setInterpolator(mInterpolator)
                .setListener(new DefaultAddAnimatorListenerAdpater(viewHolder, animate))
                .start();
    }
}
