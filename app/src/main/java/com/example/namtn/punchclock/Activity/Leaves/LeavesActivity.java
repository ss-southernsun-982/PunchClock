package com.example.namtn.punchclock.Activity.Leaves;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.namtn.punchclock.Activity.BaseActivity;
import com.example.namtn.punchclock.Model.LeavesModel.LeavesModelImpl;
import com.example.namtn.punchclock.Presenter.LeavesPresenter.LeavesPresenter;
import com.example.namtn.punchclock.Presenter.LeavesPresenter.LeavesPresenterImpl;
import com.example.namtn.punchclock.R;
import com.example.namtn.punchclock.Retrofit.RetrofitResponse.LeavesResult.LeavesData;
import com.example.namtn.punchclock.View.LeavesView;
import com.stacktips.view.CalendarListener;
import com.stacktips.view.CustomCalendarView;
import com.stacktips.view.DayDecorator;
import com.stacktips.view.DayView;
import com.stacktips.view.utils.CalendarUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LeavesActivity extends BaseActivity implements LeavesView {

    private CustomCalendarView mCustomCalendarView;
    private String TAG = "LEAVES_ACTIVITY";
    private TextView mTextViewToolBarLeaves;
    private ImageView mImageViewBackLeaves;
    private LeavesPresenter mLeavesPresenter;
    private List decorators;
    private Calendar currentCalendar;
    private FloatingActionButton mFloatButtonAddLeave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_leaves;
    }

    @Override
    protected void initView() {
        mCustomCalendarView = findViewById(R.id.calendar_view);
        mTextViewToolBarLeaves = findViewById(R.id.txt_title_toolbar_attendance);
        mImageViewBackLeaves = findViewById(R.id.btn_back_toolbar_attendance);
        mFloatButtonAddLeave = findViewById(R.id.fab_add_leavers);
    }

    @Override
    protected void initEventControl() {
//Handling custom calendar events
        mCustomCalendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {
            }

            @Override
            public void onMonthChanged(Date date) {

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
        mLeavesPresenter.fetchDataLeaves();
    }

    @Override
    protected void initData() {
        currentCalendar = Calendar.getInstance(Locale.getDefault());
//Show Monday as first date of week
        mCustomCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
//Show/hide overflow days of a month
        mCustomCalendarView.setShowOverflowDate(false);
//call refreshCalendar to update calendar the view
        Typeface typeface = Typeface.createFromAsset(getAssets(), "roboto_regular.ttf");
        if (null != typeface) {
            mCustomCalendarView.setCustomTypeface(typeface);
            mCustomCalendarView.refreshCalendar(currentCalendar);
        }
        mCustomCalendarView.refreshCalendar(currentCalendar);
//        List decorators = new ArrayList<>();
        mLeavesPresenter = new LeavesPresenterImpl(new LeavesModelImpl(this), this);

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
    public void fetchDataLeavesSuccess(ArrayList<LeavesData> dataLeaves) {
        decorators = new ArrayList();
        if (dataLeaves.size() > 0) {
            for (int i = 0; i < dataLeaves.size(); i++) {
                decorators.add(new DisabledColorDecorator(dataLeaves.get(i).getDate()));
                convertStringToArray(dataLeaves.get(i).getDate());
            }
        } else {
            decorators.add(new DisabledColorDecorator("0"));
        }
    }

    public void convertStringToArray(String s){
        String []aString = s.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try{
            for (int i = 0; i < aString.length; i++) {
                date = dateFormat.parse(aString[i]);
                String dateTime = dateFormat.format(date.getTime());
                decorators.add(new DisabledColorDecorator(dateTime));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        mCustomCalendarView.setDecorators(decorators);
        mCustomCalendarView.refreshCalendar(currentCalendar);
    }

    @Override
    public void fetchDataLeavesError(String error) {

    }

    private class DisabledColorDecorator implements DayDecorator {

        private String dayOff;
        private String TAG = "decorate";

        public DisabledColorDecorator(String dayOff) {
            this.dayOff = dayOff;
        }

        public DisabledColorDecorator() {
        }

        @Override
        public void decorate(DayView dayView) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            if (dayOff != null) {
                if (dateFormat.format(dayView.getDate().getTime()).equalsIgnoreCase(dayOff)) {
                    int color = Color.parseColor("#e60000");
                    dayView.setBackgroundColor(color);
                } else {
                    if (CalendarUtils.isPastDay(dayView.getDate())) {
                        int color = Color.parseColor("#0077bb");
                        dayView.setBackgroundColor(color);
                    }
                }
            }

            if (dateFormat.format(dayView.getDate().getTime()).equalsIgnoreCase(dateFormat.format
                    (currentCalendar.getTime()))) {
                if (CalendarUtils.isPastDay(dayView.getDate())) {
                    int color = Color.parseColor("#f4e400");
                    dayView.setBackgroundColor(color);
                }
            }
        }
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
}
