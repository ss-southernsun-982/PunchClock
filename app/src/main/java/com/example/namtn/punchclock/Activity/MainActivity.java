package com.example.namtn.punchclock.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.namtn.punchclock.Activity.Attendance.AttendanceActivity;
import com.example.namtn.punchclock.Activity.Profile.ProfileActivity;
import com.example.namtn.punchclock.Adapter.MenuMainAdapter;
import com.example.namtn.punchclock.Model.AttendanceModel.AttendanceModelImpl;
import com.example.namtn.punchclock.Model.MainModel.MainModelImpl;
import com.example.namtn.punchclock.Presenter.MainPresenter.MainPresenter;
import com.example.namtn.punchclock.Presenter.MainPresenter.MainPresenterImpl;
import com.example.namtn.punchclock.R;
import com.example.namtn.punchclock.View.MainView;

public class MainActivity extends BaseActivity implements MainView, View.OnClickListener {

    //    private GridView mGridViewMain;
    private MainPresenter mMainPresenter;
    //    private TextView mTextViewHour, mTextViewMinute, mTextViewSecond, mTextViewDate,
//            mTextViewLocation;
    private Runnable r;
    private SharedPreferences preferences;
    //check in button
    private RelativeLayout mRelativeCheckInMain, mRelativeLayoutCheckInMain, mRelativeCheckOutMain;
    private TextView mTextViewCheckInMain, mTextViewCheckOutMain;
    //check out button
    //text show current date
    TextView mTextViewDateMain, mTextViewTimeWorkingMain;
    //hotline main
    TextView mTextViewHotlineMain, mTextViewNumberHotlineMain;
    private ImageView mImageViewPersonMain;

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
//        check in and check out
        mRelativeCheckInMain = findViewById(R.id.relative_check_in_main);
        mRelativeCheckOutMain = findViewById(R.id.relative_check_out_main);
        mRelativeLayoutCheckInMain = findViewById(R.id.layout_check_in_main);
        mTextViewCheckInMain = findViewById(R.id.txt_check_in_main);
        mTextViewCheckOutMain = findViewById(R.id.txt_check_out_main);
//        display time
        mTextViewDateMain = findViewById(R.id.date_main);
//        mTextViewDateMain = findViewById(R.id.txt_date_main);
        mTextViewTimeWorkingMain = findViewById(R.id.time_clock_main);
//        hotline
        mTextViewHotlineMain = findViewById(R.id.txt_hotline_main);
        mTextViewNumberHotlineMain = findViewById(R.id.txt_number_hotline_main);
        mImageViewPersonMain = findViewById(R.id.menu_personal_main);
//        mGridViewMain = findViewById(R.id.grid_menu_main);
//        mTextViewHour = findViewById(R.id.txt_hour_main);
//        mTextViewMinute = findViewById(R.id.txt_minute_main);
//        mTextViewSecond = findViewById(R.id.txt_second_main);
//        mTextViewDate = findViewById(R.id.txt_date_main);
//        mTextViewLocation = findViewById(R.id.current_location_main);
    }

    @Override
    protected void initEventControl() {
//        mGridViewMain.setOnItemClickListener(this);
        mRelativeCheckInMain.setOnClickListener(this);
        mTextViewCheckInMain.setOnClickListener(this);
        mRelativeCheckOutMain.setOnClickListener(this);
        mTextViewCheckOutMain.setOnClickListener(this);
        mRelativeLayoutCheckInMain.setOnClickListener(this);
        mImageViewPersonMain.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        preferences = getSharedPreferences("data_map", Context.MODE_PRIVATE);
        mMainPresenter = new MainPresenterImpl(this, new MainModelImpl(this), new
                AttendanceModelImpl(this));
//        mMainPresenter.menuConfig();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mMainPresenter.getDateTime();
        mMainPresenter.getDateTime();
        mMainPresenter.calculatorTimeWorking();
        mMainPresenter.fetchDataDate();
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
//        mGridViewMain.setAdapter(adapter);
    }

    @Override
    public void pushDataCheckInSuccess(String s) {
        if (s != null) {
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }
        mRelativeCheckInMain.setVisibility(View.GONE);
        mRelativeCheckInMain.setVisibility(View.GONE);
        mRelativeCheckOutMain.setVisibility(View.VISIBLE);
        mTextViewCheckOutMain.setVisibility(View.VISIBLE);
    }

    @Override
    public void pushDataCheckInError(String s) {
        if (s != null) {
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }
        mRelativeCheckInMain.setVisibility(View.VISIBLE);
        mRelativeCheckInMain.setVisibility(View.VISIBLE);
        mRelativeCheckOutMain.setVisibility(View.GONE);
        mTextViewCheckOutMain.setVisibility(View.GONE);
    }

    @Override
    public void pushDataCheckOutSuccess(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        mRelativeCheckOutMain.setVisibility(View.GONE);
        mTextViewCheckOutMain.setVisibility(View.GONE);
    }

    @Override
    public void pushDataCheckOutError(String error) {
        if (error != null) {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }
        mRelativeCheckOutMain.setVisibility(View.VISIBLE);
        mTextViewCheckOutMain.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayTimeWorking(String time) {
        mTextViewTimeWorkingMain.setText(time);
    }

    @Override
    public void visibleCheckIn(boolean b) {
//        if (b == true){
//            mRelativeCheckInMain.setVisibility(View.VISIBLE);
//            mRelativeCheckInMain.setVisibility(View.VISIBLE);
//            mRelativeCheckOutMain.setVisibility(View.GONE);
//            mTextViewCheckOutMain.setVisibility(View.GONE);
//        }
    }

    @Override
    public void dateTimeData(int date, int month, int year, int hour, int minute, int second) {
//        mTextViewHour.setText(String.valueOf(hour));
//        mTextViewMinute.setText(String.valueOf(minute));
//        mTextViewSecond.setText(String.valueOf(second));
//        mTextViewDate.setText(date + "/" + month + "/" + year);
//        mTextViewLocation.setText(preferences.getString("mAddress", "Not address found"));
        mTextViewDateMain.setText(date + "/" + month + "/" + year);
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        switch (position) {
//            case 0:
//                mMainPresenter.IntentClass(AttendanceActivity.class);
//                break;
//            case 1:
//                mMainPresenter.IntentClass(LeavesActivity.class);
//                break;
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_check_in_main:
                IntentActivity(AttendanceActivity.class);
                break;
            case R.id.relative_check_in_main:
            case R.id.txt_check_in_main:
                mMainPresenter.pushDataCheckInAttendance();
                break;
            case R.id.relative_check_out_main:
            case R.id.txt_check_out_main:
                mMainPresenter.pushDataCheckOutAttendance();
                break;
            case R.id.menu_personal_main:
                IntentActivity(ProfileActivity.class);
                break;
        }
    }

    private void IntentActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
        overridePendingTransition(R.anim.left_to_right, 0);
    }
}
