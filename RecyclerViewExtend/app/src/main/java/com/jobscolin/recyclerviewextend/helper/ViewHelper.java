package com.jobscolin.recyclerviewextend.helper;

import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * @author : XuDonglin
 * @time : 2019/06/06
 * @describe :
 */
public class ViewHelper {
    public static void clear(View view) {
        ViewCompat.setAlpha(view,1.0f);
        ViewCompat.setTranslationX(view, 0);
        ViewCompat.setTranslationY(view, 0);
        ViewCompat.setScaleX(view, 1);
        ViewCompat.setScaleY(view, 1);
        ViewCompat.setRotation(view, 0);
        ViewCompat.setRotationX(view, 0);
        ViewCompat.setRotationY(view, 0);
        ViewCompat.setPivotX(view, view.getMeasuredWidth() / 2f);
        ViewCompat.setPivotY(view, view.getMeasuredHeight() / 2f);
        ViewCompat.animate(view).setInterpolator(null).setStartDelay(0);
    }
}
