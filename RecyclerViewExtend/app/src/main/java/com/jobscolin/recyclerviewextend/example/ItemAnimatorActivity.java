package com.jobscolin.recyclerviewextend.example;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.OvershootInterpolator;

import com.jobscolin.recyclerviewextend.R;
import com.jobscolin.recyclerviewextend.adapters.AnimatorAdapter;
import com.jobscolin.recyclerviewextend.adapters.ItemAnimatorExampleAdapter;

import java.util.ArrayList;

public class ItemAnimatorActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_animator);

        mRecyclerView = findViewById(R.id.animator_recycler);

        for (int i = 0; i < 20; i++) {
            mData.add("This is " + i + " msg");
        }

        ItemAnimatorExampleAdapter recyclerAdapter = new ItemAnimatorExampleAdapter(this, mData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new AnimatorAdapter(recyclerAdapter) {
            @Override
            public ObjectAnimator setObjectAnimator(RecyclerView.ViewHolder viewHolder) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(viewHolder.itemView, "translationX",
                        -viewHolder.itemView.getRootView().getWidth() * 2, -1);
                objectAnimator.setDuration(2000)
                        .setInterpolator(new OvershootInterpolator(0.5f));
                return objectAnimator;
            }
        });

        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(this,
                R.anim.layout_animator);
        mRecyclerView.setLayoutAnimation(layoutAnimationController);
    }
}
