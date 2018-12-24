package com.example.namtn.punchclock.Activity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.namtn.punchclock.R;

public class MainActivity extends BaseActivity {

    private GridView mGridViewMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mGridViewMain = findViewById(R.id.grid_menu_main);
    }

    @Override
    protected void initEventControl() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
