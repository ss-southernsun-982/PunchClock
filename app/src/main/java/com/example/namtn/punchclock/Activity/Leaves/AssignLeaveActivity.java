package com.example.namtn.punchclock.Activity.Leaves;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.namtn.punchclock.Activity.BaseActivity;
import com.example.namtn.punchclock.Adapter.SpinnerAdapter;
import com.example.namtn.punchclock.CustomWidget.CustomCalendar.AdapterCalendar;
import com.example.namtn.punchclock.Model.AssignLeaves.AssignLeavesModelImpl;
import com.example.namtn.punchclock.Presenter.AssignPresenter.AssignPresenter;
import com.example.namtn.punchclock.Presenter.AssignPresenter.AssignPresenterImpl;
import com.example.namtn.punchclock.R;
import com.example.namtn.punchclock.View.AssignLeaveView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AssignLeaveActivity extends BaseActivity implements AssignLeaveView, View
        .OnClickListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener {

    private Button mButtonSaveLeave, mButtonCancelLeave, mButtonSaveDialog;
    private Spinner mSpinnerTypeDayLeave;
    private RadioGroup mRadioGroupDays;
    private RadioButton mRadioHalfDay;
    private TextView mTextViewToolBarLeaves, mTextViewMonth;
    private ImageView mImageViewBackLeaves;
    private EditText mEditTextReasonLeave;
    private String date = "", reason = "", halfDay = "", type_id = "";
    private Dialog mDialogDay;
    private AssignPresenter mAssignPresenter;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private GridView mGridViewCalendar;
    private ImageView mButtonPrevMonth, mButtonNextMonth;
    private String TAG = "ASSIGN_LEAVE";
    private int checkCode = 0;
    private int ofMonth = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAssignPresenter.configSpinnerTypeDay();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_assign_leave;
    }

    @Override
    protected void initView() {
        mRadioGroupDays = findViewById(R.id.radioGr_leave);
        mSpinnerTypeDayLeave = findViewById(R.id.spinner_type_leave);
        mEditTextReasonLeave = findViewById(R.id.edt_reson_leave);
        mButtonCancelLeave = findViewById(R.id.btn_cancel_leave);
        mButtonSaveLeave = findViewById(R.id.btn_save_leave);
        mTextViewToolBarLeaves = findViewById(R.id.txt_title_toolbar_attendance);
        mTextViewToolBarLeaves.setText("Đơn xin nghỉ phép");
        mImageViewBackLeaves = findViewById(R.id.btn_back_toolbar_attendance);
        mRadioHalfDay = findViewById(R.id.radioBtn_halfDay_leave);
        //dialog
        mDialogDay = new Dialog(this);
        mDialogDay.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialogDay.setContentView(R.layout.custom_show_select_days);
        mTextViewMonth = mDialogDay.findViewById(R.id.month_dialog_select);
        mGridViewCalendar = mDialogDay.findViewById(R.id.gird_select_day_assign);
        mButtonNextMonth = mDialogDay.findViewById(R.id.btn_right_dialog_select);
        mButtonPrevMonth = mDialogDay.findViewById(R.id.btn_left_dialog_select);
        mButtonSaveDialog= mDialogDay.findViewById(R.id.btn_save_dialog);
    }

    @Override
    protected void initEventControl() {
        mButtonCancelLeave.setOnClickListener(this);
        mButtonSaveLeave.setOnClickListener(this);
        mRadioGroupDays.setOnCheckedChangeListener(this);
        mSpinnerTypeDayLeave.setOnItemSelectedListener(this);
        mImageViewBackLeaves.setOnClickListener(this);
        mGridViewCalendar.setOnItemClickListener(this);
        mButtonNextMonth.setOnClickListener(this);
        mButtonPrevMonth.setOnClickListener(this);
        mButtonSaveDialog.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mAssignPresenter = new AssignPresenterImpl(new AssignLeavesModelImpl(this), this);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Override
    public void configSpinnerTypeDays(SpinnerAdapter adapter) {
        mSpinnerTypeDayLeave.setAdapter(adapter);
    }

    @Override
    public void showDialogDay() {
        mDialogDay.show();
        mAssignPresenter.configDataCalender(ofMonth);
    }

    @Override
    public void hideDialogDay() {
        mDialogDay.dismiss();
        mDialogDay.cancel();
    }

    @Override
    public void configDataCalendar(AdapterCalendar adapterCalendar) {
        mGridViewCalendar.setAdapter(adapterCalendar);
    }

    @Override
    public void setTextMonth(String s) {
        mTextViewMonth.setText(s);
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void pushDataAssignLeaveSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    public void pushDataAssignLeaveError(String error) {

    }

    @Override
    public void setDateLive(String date) {
        mRadioHalfDay.setText(date
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel_leave:
                onBackPressed();
                break;
            case R.id.btn_save_leave:
                pushDataLeave();
                break;
            case R.id.btn_left_dialog_select:
                ofMonth--;
                mAssignPresenter.configDataCalender(ofMonth);
                break;
            case R.id.btn_right_dialog_select:
                ofMonth++;
                mAssignPresenter.configDataCalender(ofMonth);
                break;
            case R.id.btn_save_dialog:
                mAssignPresenter.hideDialogDay();
                break;
            case R.id.btn_back_toolbar_attendance:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type_id = String.valueOf(parent.getItemIdAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioBtn_halfDay_leave:
                calendar = Calendar.getInstance(Locale.getDefault());
                calendar.add(Calendar.DATE, 1);
                mAssignPresenter.setDateLive();
                halfDay = "true";
                break;
            case R.id.radioBtn_more_days_leave:
                halfDay = "false";
                mAssignPresenter.showDialogDay();
                break;
        }
    }

    private void pushDataLeave() {
        reason = mEditTextReasonLeave.getText().toString().trim();
        int errorCode = 0;
        if (reason.isEmpty()) {
            mEditTextReasonLeave.setHint("Nhập lý do (...)");
            mEditTextReasonLeave.setHintTextColor(Color.RED);
            errorCode++;
        }
        if (halfDay.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ngày nghỉ", Toast.LENGTH_SHORT).show();
            errorCode++;
        }
        if (errorCode == 0) {
            Log.d(TAG, "pushDataLeave: " + reason + " = " + halfDay + " = " +
                    type_id);
            mAssignPresenter.pushDataAssignLeave(reason, halfDay, type_id);
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
        mAssignPresenter.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mAssignPresenter.selectDays(position);
    }
}
