package com.jobscolin.recyclerviewextend.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jobscolin.recyclerviewextend.R;
import com.jobscolin.recyclerviewextend.itemdecoration.CustomItemDecoration;

import java.util.ArrayList;

public class DividerActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divder);

        mRecyclerView = findViewById(R.id.recyclers);

        for (int i = 0; i < 26; i++) {
            mList.add("");
        }
        MainAdapter adapter = new MainAdapter(this, mList);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(adapter);

        CustomItemDecoration customItemDecoration = new CustomItemDecoration
                .Builder(this)
                .showFirst(true)
                .showLast(true)
                .create();
        CustomItemDecoration horizontalDecoration = new CustomItemDecoration
                .Builder(this)
                .showFirst(true)
                .showLast(true)
                .setPaddingTop(10)
                .setPaddingBottom(10)
                .setMod(CustomItemDecoration.HORIZONTAL)
                .create();
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
                .showLastColumn(false)
                .create();

//        mRecyclerView.addItemDecoration(customItemDecoration);
//        mRecyclerView.addItemDecoration(horizontalDecoration);
        mRecyclerView.addItemDecoration(gridDecoration);

    }
}
