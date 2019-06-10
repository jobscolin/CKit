package com.jobscolin.recyclerviewextend.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jobscolin.recyclerviewextend.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.decoration_btn).setOnClickListener(this);
        findViewById(R.id.animator_btn).setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.decoration_btn:
                intent.setClass(this, DividerActivity.class);
                startActivity(intent);

                break;
            case R.id.animator_btn:
                intent.setClass(this, ItemAnimatorActivity.class);
                startActivity(intent);

                break;
            default:
                break;
        }
    }
}
