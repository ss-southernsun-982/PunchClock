package com.example.namtn.punchclock.Activity.Dairy;

import android.app.DatePickerDialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.namtn.punchclock.Activity.BaseActivity;
import com.example.namtn.punchclock.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateNewDairyActivity extends BaseActivity {

    private View view;
    private LinearLayout mLinearLayoutAddMore;
    private String TAG = "TAGGGGGGG";
    private ImageView mImageViewChooseDate;
    private DatePickerDialog mDatePickerDialogCreate;
    private Calendar mCalendar;
    private int day, month, year;
    private TextView mTextViewChooseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_create_new_dairy;
    }

    @Override
    protected void initView() {
        view = findViewById(R.id.root_layout_create_new_diary);
        mLinearLayoutAddMore = findViewById(R.id.layout_add_more_diary);
        mImageViewChooseDate = findViewById(R.id.img_choose_calendar);
        mTextViewChooseDate = findViewById(R.id.txt_choose_date_post_new_diary);
    }

    @Override
    protected void initEventControl() {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();

                view.getWindowVisibleDisplayFrame(r);

                int heightDiff = view.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > 200) {
                    //enter your code here
                    Log.d(TAG, "onGlobalLayout: open");
                    mLinearLayoutAddMore.setVisibility(View.VISIBLE);
                } else {
                    Log.d(TAG, "onGlobalLayout: hide");
                    //enter code for hid
                    mLinearLayoutAddMore.setVisibility(View.GONE);
                }
            }
        });
        mImageViewChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerDialogCreate.show();
            }
        });
    }

    @Override
    protected void initData() {
        mCalendar = Calendar.getInstance();
        day = mCalendar.get(Calendar.DATE);
        month = mCalendar.get(Calendar.MONTH);
        year = mCalendar.get(Calendar.YEAR);
        mDatePickerDialogCreate = new DatePickerDialog(this, new DatePickerDialog
                .OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalendar.set(year, month, dayOfMonth);
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd yyyy", Locale.ENGLISH);
                mTextViewChooseDate.setText(dateFormat.format(mCalendar.getTime()));

            }
        }, year, month, day);
        mDatePickerDialogCreate.getDatePicker().setMinDate(mCalendar.getTimeInMillis());
    }
}
