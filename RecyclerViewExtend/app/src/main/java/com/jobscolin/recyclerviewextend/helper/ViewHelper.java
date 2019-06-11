package com.jobscolin.recyclerviewextend.helper;

import android.view.View;

/**
 * @author : Xdl
 * @time : 2019/06/06
 * @describe :
 */
public class ViewHelper {
    public static void clear(View view) {
        view.setAlpha(1.0f);
        view.setTranslationX(0);
        view.setTranslationY(0);
        view.setScaleX(1);
        view.setScaleY(1);
        view.setRotation(0);
        view.setRotationX(0);
        view.setRotationY(0);
        view.setPivotX(view.getMeasuredWidth() / 2f);
        view.setPivotY(view.getMeasuredHeight() / 2f);
        view.animate().setInterpolator(null).setStartDelay(0);
    }
}
