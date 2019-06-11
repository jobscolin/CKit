package com.jobscolin.recyclerviewextend.example;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.OvershootInterpolator;

import com.jobscolin.recyclerviewextend.R;
import com.jobscolin.recyclerviewextend.adapters.AnimatorAdapter;
import com.jobscolin.recyclerviewextend.adapters.ItemAnimatorExampleAdapter;
import com.jobscolin.recyclerviewextend.itemanimators.SlideInLeftAnimator;

import java.util.ArrayList;

/**
 *  @author : Xdl
 *  @time   : 2019-06-11
 *  @describe  : item动画
 */
public class ItemAnimatorActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<String> mData = new ArrayList<>();
    private ItemAnimatorExampleAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_animator);

        mRecyclerView = findViewById(R.id.animator_recycler);

        for (int i = 0; i < 20; i++) {
            mData.add("This is " + i + " msg");
        }

        mRecyclerAdapter = new ItemAnimatorExampleAdapter(this, mData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new AnimatorAdapter(mRecyclerAdapter) {
            @Override
            public ObjectAnimator setObjectAnimator(RecyclerView.ViewHolder viewHolder) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(viewHolder.itemView, "translationX",
                        -viewHolder.itemView.getRootView().getWidth() * 2, -1);
                objectAnimator.setDuration(2000)
                        .setInterpolator(new OvershootInterpolator(0));
                return objectAnimator;
            }
        });

        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(this,
                R.anim.layout_animator);
        mRecyclerView.setLayoutAnimation(layoutAnimationController);
        mRecyclerView.setItemAnimator(new SlideInLeftAnimator());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                mData.add(1, " add item ");
                mRecyclerAdapter.notifyItemInserted(1);
                return true;
            case R.id.delete:
                mData.remove(1);
                mRecyclerAdapter.notifyItemRemoved(1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
