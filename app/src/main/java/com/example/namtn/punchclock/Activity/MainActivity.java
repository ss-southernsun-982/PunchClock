package com.example.namtn.punchclock.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.namtn.punchclock.Activity.Attendance.AttendanceActivity;
import com.example.namtn.punchclock.Adapter.MenuMainAdapter;
import com.example.namtn.punchclock.Model.MainModel.MainModelImpl;
import com.example.namtn.punchclock.Presenter.MainPresenter.MainPresenter;
import com.example.namtn.punchclock.Presenter.MainPresenter.MainPresenterImpl;
import com.example.namtn.punchclock.R;
import com.example.namtn.punchclock.View.MainView;

public class MainActivity extends BaseActivity implements MainView, AdapterView
        .OnItemClickListener {

    private GridView mGridViewMain;
    private MainPresenter mMainPresenter;
    private TextView mTextViewHour, mTextViewMinute, mTextViewSecond, mTextViewDate,
            mTextViewLocation;
    private Runnable r;
    private SharedPreferences preferences;

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
        mTextViewHour = findViewById(R.id.txt_hour_main);
        mTextViewMinute = findViewById(R.id.txt_minute_main);
        mTextViewSecond = findViewById(R.id.txt_second_main);
        mTextViewDate = findViewById(R.id.txt_date_main);
        mTextViewLocation = findViewById(R.id.current_location_main);
    }

    @Override
    protected void initEventControl() {
        mGridViewMain.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        preferences = getSharedPreferences("data_map", Context.MODE_PRIVATE);
        mMainPresenter = new MainPresenterImpl(this, new MainModelImpl(this));
        mMainPresenter.menuConfig();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMainPresenter.getDateTime();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void initMenu(MenuMainAdapter adapter) {
        mGridViewMain.setAdapter(adapter);
    }

    @Override
    public void dateTimeData(int date, int month, int year, int hour, int minute, int second) {
        mTextViewHour.setText(String.valueOf(hour));
        mTextViewMinute.setText(String.valueOf(minute));
        mTextViewSecond.setText(String.valueOf(second));
        mTextViewDate.setText(date + "/" + month + "/" + year);
        mTextViewLocation.setText(preferences.getString("mAddress", "Not address found"));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                mMainPresenter.IntentClass(AttendanceActivity.class);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.onDestroy();
    }
}
