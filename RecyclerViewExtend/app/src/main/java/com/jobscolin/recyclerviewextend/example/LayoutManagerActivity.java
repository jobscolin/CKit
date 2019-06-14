package com.jobscolin.recyclerviewextend.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.jobscolin.recyclerviewextend.R;

import java.util.ArrayList;
import java.util.List;

public class LayoutManagerActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_manager);

        data.add("12312312312");
        data.add("1231212");
        data.add("1231dddadfad312312");
        data.add("12a312312312");
        data.add("123123dd12312");
        data.add("2312");

        mRecyclerView = findViewById(R.id.layout_manager_recycler);

        LayoutManagerAdapter adapter = new LayoutManagerAdapter(this, data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new FlexboxLayoutManager(this, FlexDirection.ROW));
    }
}
