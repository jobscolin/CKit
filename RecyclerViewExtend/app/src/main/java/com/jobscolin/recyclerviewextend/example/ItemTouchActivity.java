package com.jobscolin.recyclerviewextend.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;

import com.jobscolin.recyclerviewextend.R;
import com.jobscolin.recyclerviewextend.itemdecoration.CustomItemDecoration;
import com.jobscolin.recyclerviewextend.itemmove.ItemMoveCallback;
import com.wya.uikit.imagepicker.ImagePickerCreator;
import com.wya.uikit.imagepicker.PickerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xudonglin
 * 拖拽item更换位置，彷微信发布朋友圈
 *
 *
 * 注意：这边如果想让item在整个屏幕可以拖动，那么需要在recyclerview的父布局中设置android:clipChildren="false"
 */
public class ItemTouchActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ItemMoveAdapter mAdapter;
    private List<String> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_touch);
        mRecyclerView = findViewById(R.id.item_touch_recycler);
        initRecycler();
    }

    private void initRecycler() {

        CustomItemDecoration gridDecoration = new CustomItemDecoration.Builder(this)
                .setMod(CustomItemDecoration.GRID)
                .showLeft(true)
                .showRight(true)
                .showBottom(true)
                .showTop(true)
                .setPaddingLeft(10)
                .setPaddingRight(10)
                .setPaddingTop(10)
                .setPaddingBottom(10)
                .setColor(R.color.primary_color)
                .create();

        images.add("");
        mAdapter = new ItemMoveAdapter(images, this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.addItemDecoration(gridDecoration);
        mRecyclerView.setAdapter(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemMoveCallback<>(images,
                mAdapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mAdapter.setAddImageListener(new ItemMoveAdapter.AddImageListener() {
            @Override
            public void click() {
                ImagePickerCreator.create(ItemTouchActivity.this).maxImages(9).forResult(1000);
            }
        });

        mAdapter.setDeleteImageListener(new ItemMoveAdapter.DeleteImageListener() {
            @Override
            public void click() {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra(PickerConfig.IMAGE_SELECTED)) {
                if (TextUtils.isEmpty(images.get(images.size() - 1))) {
                    images.remove(images.size() - 1);

                }
                Bundle extras = data.getExtras();
                ArrayList<String> list =
                        extras.getStringArrayList(PickerConfig.IMAGE_SELECTED);
                images.addAll(list);
                if (images.size() < 9) {
                    images.add("");
                }
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
