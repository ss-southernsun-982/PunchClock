package com.example.namtn.punchclock.Activity.Leaves;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.namtn.punchclock.Activity.BaseActivity;
import com.example.namtn.punchclock.CustomWidget.CustomCalendar.AdapterCalendarLeaves;
import com.example.namtn.punchclock.CustomWidget.CustomCalendar.CalendarSateLeaves;
import com.example.namtn.punchclock.Model.LeavesModel.LeavesModelImpl;
import com.example.namtn.punchclock.Presenter.LeavesPresenter.LeavesPresenter;
import com.example.namtn.punchclock.Presenter.LeavesPresenter.LeavesPresenterImpl;
import com.example.namtn.punchclock.R;
import com.example.namtn.punchclock.View.LeavesView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LeavesActivity extends BaseActivity implements LeavesView, View.OnClickListener {

    private String TAG = "LEAVES_ACTIVITY";
    private TextView mTextViewToolBarLeaves, mTextViewPrevMonth, mTextViewCurrentMonth,
            mTextViewNextMonth;
    private ImageView mImageViewBackLeaves;
    private LeavesPresenter mLeavesPresenter;
    private Calendar currentCalendar;
    private FloatingActionButton mFloatButtonAddLeave;
    private LinearLayout mLinearLeavesAdmin;
    private GridView mGridViewLeaves;
    private int month = 0, year = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_leaves;
    }

    @Override
    public void setAdminLayout() {
        super.setAdminLayout();
        mLinearLeavesAdmin.setVisibility(View.VISIBLE);
    }

    @Override
    public void setGuestLayout() {
        super.setGuestLayout();
        mLinearLeavesAdmin.setVisibility(View.GONE);
    }

    @Override
    protected void initView() {
        mTextViewToolBarLeaves = findViewById(R.id.txt_title_toolbar_attendance);
        mImageViewBackLeaves = findViewById(R.id.btn_back_toolbar_attendance);
        mFloatButtonAddLeave = findViewById(R.id.fab_add_leavers);
        mLinearLeavesAdmin = findViewById(R.id.layout_leaves_admin);
        mGridViewLeaves = findViewById(R.id.grid_view_calendar_leaves);
        mTextViewPrevMonth = findViewById(R.id.prev_month_leaves);
        mTextViewNextMonth = findViewById(R.id.next_month_leaves);
        mTextViewCurrentMonth = findViewById(R.id.current_month_leaves);
    }

    @Override
    protected void initEventControl() {
//Handling custom calendar events

        mTextViewPrevMonth.setOnClickListener(this);
        mTextViewNextMonth.setOnClickListener(this);
        mTextViewCurrentMonth.setOnClickListener(this);

        mGridViewLeaves.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CalendarSateLeaves sateLeaves = (CalendarSateLeaves) parent.getItemAtPosition
                        (position);
                Toast.makeText(LeavesActivity.this, "" + sateLeaves.getDate(), Toast
                        .LENGTH_SHORT).show();
            }
        });

        mFloatButtonAddLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeavesActivity.this, AssignLeaveActivity.class);
                startActivity(intent);
            }
        });

        mImageViewBackLeaves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.initData();
        month = (currentCalendar.get(Calendar.MONTH) + 1);
        year = currentCalendar.get(Calendar.YEAR);
        mLeavesPresenter.onInitDataCalendar(month, year);
        Log.d(TAG, "onStart: " + currentCalendar.get(Calendar.MONTH));
    }

    @Override
    protected void initData() {
        currentCalendar = Calendar.getInstance(Locale.getDefault());
        mLeavesPresenter = new LeavesPresenterImpl(new LeavesModelImpl(this), this);
        currentCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        mTextViewToolBarLeaves.setText("Lịch nghỉ " + dateFormat.format(currentCalendar.getTime()));
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void fetchDataLeavesSuccess(AdapterCalendarLeaves mAdapterCalendarLeaves) {
        mGridViewLeaves.setAdapter(mAdapterCalendarLeaves);
    }

    @Override
    public void fetchDataLeavesError(String error) {

    }

    @Override
    public void setTitleCalendar(String prevMonth, String currentMonth, String nextMonth) {
        mTextViewCurrentMonth.setText(currentMonth);
        mTextViewNextMonth.setText(nextMonth);
        mTextViewPrevMonth.setText(prevMonth);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLeavesPresenter.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_month_leaves:
                month = month + 1;
                mLeavesPresenter.onInitDataCalendar(month, year);
                break;
            case R.id.current_month_leaves:
                break;
            case R.id.prev_month_leaves:
                month = month -1;
                mLeavesPresenter.onInitDataCalendar(month, year);
                break;
        }
    }
}
