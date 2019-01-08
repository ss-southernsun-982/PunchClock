package com.example.namtn.punchclock.Activity.Attendance;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.namtn.punchclock.Activity.BaseActivity;
import com.example.namtn.punchclock.Adapter.Attendance.AttendanceDateAdapter;
import com.example.namtn.punchclock.Adapter.Attendance.AttendanceMonthAdapter;
import com.example.namtn.punchclock.Model.AttendanceModel.AttendanceModelImpl;
import com.example.namtn.punchclock.Model.AttendanceModel.CustomLinearLayoutManager;
import com.example.namtn.punchclock.Presenter.AttendancePresenter.AttendancePresenter;
import com.example.namtn.punchclock.Presenter.AttendancePresenter.AttendancePresenterImpl;
import com.example.namtn.punchclock.R;
import com.example.namtn.punchclock.View.AttendanceView;

public class AttendanceActivity extends BaseActivity implements AttendanceView, View
        .OnClickListener, AdapterView.OnItemClickListener {

    private Button mButtonCheckInAttendance;
    private Button mButtonCheckOutAttendance;
    private AttendancePresenter mAttendancePresenter;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerViewAttendance;
    private ListView mListViewAttendance;
    private TextView mTextViewTimeWorking;
    private LinearLayout mLinearCheckOut;
    private ImageView mImageViewBackAttendance;
    private RelativeLayout mRelativeLayoutCheckOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAttendancePresenter.configDataMonth();
        mAttendancePresenter.fetchDataDate();
        mAttendancePresenter.calculatorTimeWorking();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_attendance;
    }

    @Override
    protected void initView() {
        mButtonCheckInAttendance = findViewById(R.id.btn_push_attendance);
        mButtonCheckOutAttendance = findViewById(R.id.btn_check_out_attendance);
        mProgressBar = findViewById(R.id.progress_attendance);
        mRecyclerViewAttendance = findViewById(R.id.recycler_month_attendance);
        mProgressBar.setVisibility(View.GONE);
        mListViewAttendance = findViewById(R.id.listview_day_of_month_attendance);
        mTextViewTimeWorking = findViewById(R.id.txt_time_working_attendance);
        mLinearCheckOut = findViewById(R.id.layout_check_out_attendance);
        mImageViewBackAttendance = findViewById(R.id.btn_back_toolbar_attendance);
        mRelativeLayoutCheckOut = findViewById(R.id.layout_button_check);
    }

    @Override
    protected void initEventControl() {
        mButtonCheckInAttendance.setOnClickListener(this);
        mButtonCheckOutAttendance.setOnClickListener(this);
        mListViewAttendance.setOnItemClickListener(this);
        mImageViewBackAttendance.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mAttendancePresenter = new AttendancePresenterImpl(this, new AttendanceModelImpl(this));
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void displayDataMonth(AttendanceMonthAdapter adapter, int position) {
        CustomLinearLayoutManager layoutManager
                = new CustomLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManager.scrollToPositionWithOffset(position, 20);
        mRecyclerViewAttendance.setHasFixedSize(true);
        mRecyclerViewAttendance.setLayoutManager(layoutManager);
        mRecyclerViewAttendance.setAdapter(adapter);
    }

    @Override
    public void displayDataDateOfMonth(AttendanceDateAdapter adapter) {
        mListViewAttendance.setAdapter(adapter);
    }

    @Override
    public void pushDataCheckInSuccess(String s) {
        if (s != null) {
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }
        mButtonCheckInAttendance.setVisibility(View.GONE);
        mLinearCheckOut.setVisibility(View.VISIBLE);
    }

    @Override
    public void pushDataCheckInError(String s) {
        if (s != null) {
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }
        mButtonCheckInAttendance.setVisibility(View.VISIBLE);
        mLinearCheckOut.setVisibility(View.GONE);
        mRelativeLayoutCheckOut.setVisibility(View.VISIBLE);
    }

    @Override
    public void pushDataCheckOutSuccess(String message) {
        if (message != null){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        mRelativeLayoutCheckOut.setVisibility(View.GONE);
    }

    @Override
    public void pushDataCheckOutError(String error) {
        if (error != null){
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }
        mRelativeLayoutCheckOut.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayTimeWorking(String time) {
        mTextViewTimeWorking.setText(time);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_push_attendance:
                mAttendancePresenter.pushDataCheckInAttendance();
                break;
            case R.id.btn_check_out_attendance:
                mAttendancePresenter.pushDataCheckOutAttendance();
                break;
            case R.id.btn_back_toolbar_attendance:
                this.onBackPressed();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAttendancePresenter.onDestroy();
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }
}
