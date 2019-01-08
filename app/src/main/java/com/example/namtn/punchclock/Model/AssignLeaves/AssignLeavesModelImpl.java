package com.example.namtn.punchclock.Model.AssignLeaves;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.namtn.punchclock.Adapter.SpinnerAdapter;
import com.example.namtn.punchclock.CustomWidget.CustomCalendar.AdapterCalendar;
import com.example.namtn.punchclock.CustomWidget.CustomCalendar.CalendarState;
import com.example.namtn.punchclock.R;
import com.example.namtn.punchclock.Retrofit.RetrofitConfig.RetrofitAPIs;
import com.example.namtn.punchclock.Retrofit.RetrofitConfig.RetrofitUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AssignLeavesModelImpl implements AssignLeavesModel {

    private Context context;
    private Activity activity;
    private RetrofitAPIs mRetrofit;
    private CompositeDisposable mCompositeDisposable;
    private String TAG = "ASSIGN_ADD";
    private SharedPreferences mPreferencesUser;
    private SpinnerAdapter mAdapterTypeDays;
    private AdapterCalendar mAdapterCalendar;
    private ArrayList<CalendarState> mListCalendar, mListDateSelected;
    private String titleCalendar = "";
    private String date = "";
    private SimpleDateFormat simpleDateFormat;
    Calendar calendar;
    int p = 0;
    int positionMonth = 0;

    public AssignLeavesModelImpl(Context context) {
        this.context = context;
        this.activity = (Activity) context;
        mRetrofit = RetrofitUtils.apiLeavesAdd();
        mCompositeDisposable = new CompositeDisposable();
        mPreferencesUser = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        mListCalendar = new ArrayList<>();
        mListDateSelected = new ArrayList<>();
        mAdapterCalendar = new AdapterCalendar(context, mListCalendar);
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Override
    public void configSpinnerTypeDays(onAssignLeaveListener listener) {
        mAdapterTypeDays = new SpinnerAdapter(context,
                android.R.layout.simple_spinner_dropdown_item,
                Arrays.asList(context.getResources().getStringArray(R.array.spinner_type_day)));
        listener.configSpinnerTypeDaySuccess(mAdapterTypeDays);
    }

    @Override
    public void pushDataAssignLeave(onAssignLeaveListener listener, String reason,
                                    String halfDay, String type_id) {
        String emp_id = mPreferencesUser.getString("userId", "0");
        if (halfDay.equalsIgnoreCase("true")) {
            long time = calendar.getTimeInMillis() * 24 * 60 * 60 * 1000;
            calendar.setTimeInMillis(time);
            date = simpleDateFormat.format(calendar.getTime());
        } else {
            for (int i = 0; i < mListDateSelected.size(); i++) {
                CalendarState calendarState = mListDateSelected.get(i);
                date = calendarState.getDate() + "/" + (calendarState.getMonth()
                        + 1) + "/" + calendarState.getYear() + "," + date;
            }
        }
        if (date.isEmpty()) {
            listener.pushDataAssignLeaveError("Vui lòng chọn ngày nghỉ!");
        } else {
            mCompositeDisposable.add(mRetrofit.leavesAdd(emp_id, date, reason, halfDay, type_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            s -> {
                                listener.pushDataAssignLeaveSuccess(s.getMessage());
                            },
                            throwable -> {
                                Log.d(TAG, "pushDataAssignLeave error: " + throwable.getMessage());
                                listener.pushDataAssignLeaveError("Vui lòng kiểm tra dữ liệu");
                            }
                    )
            );
        }
        Log.d(TAG, "pushDataAssignLeave push: " + date + emp_id + type_id + reason);
    }

    @Override
    public void configCalendar(onAssignLeaveListener listener, int ofMonth) {
        initDataCalendar(ofMonth);
        listener.setTextMonth(titleCalendar);
        listener.configCalendarSuccess(mAdapterCalendar);
    }

    @Override
    public void daysSelected(int position) {
        CalendarState calendarState = mListCalendar.get(position);
        calendarState.setSelected(!mListCalendar.get(position).isSelected());
        mListCalendar.set(position, calendarState);
        mAdapterCalendar.notifyDataSetChanged();
        if (calendarState.isSelected()) {
            mListDateSelected.add(calendarState);
            Log.d(TAG, "daysSelected: add");
        } else {
            for (int j = 0; j < mListDateSelected.size(); j++) {
                CalendarState calendarState2 = mListDateSelected.get(j);
                if (calendarState.getDate() == calendarState2.getDate() && calendarState.getMonth
                        () == calendarState2.getMonth() && calendarState.getYear() ==
                        calendarState2.getYear()) {
                    mListDateSelected.remove(j);
                    Log.d(TAG, "daysSelected: remove");
                }
            }
        }
    }

    @Override
    public void setDateLive(onAssignLeaveListener listener) {
        calendar = Calendar.getInstance();
        long time = calendar.getTimeInMillis() + 24 * 60 * 60 * 1000;
        calendar.setTimeInMillis(time);
        Log.d(TAG, "setDateLive: " + time);
        date = simpleDateFormat.format(calendar.getTime());
        listener.setDateLeave(date);
    }

    public void initDataCalendar(int ofMonth2) {
        mListCalendar.clear();
        mListCalendar.addAll(addDate(ofMonth2));
        date = "";
        for (int i = 0; i < mListDateSelected.size(); i++) {
            CalendarState calendarState = mListDateSelected.get(i);
            for (int j = 0; j < mListCalendar.size(); j++) {
                CalendarState calendarState2 = mListCalendar.get(j);
                if (calendarState.getDate() == calendarState2.getDate() && calendarState.getMonth
                        () == calendarState2.getMonth() && calendarState.getYear() ==
                        calendarState2.getYear() && calendarState.isSelected()) {
                    calendarState2.setSelected(!calendarState2.isSelected());
                }
            }
        }
        Log.d(TAG, "initDataCalendar: " + date);
        mAdapterCalendar.notifyDataSetChanged();
    }

    private ArrayList<CalendarState> addDate(int ofMonth1) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(cal.getTime());
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, ofMonth1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        getFirstDay(1, cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
//        positionMonth
        int maxDate = cal.get(Calendar.DAY_OF_MONTH);
        ArrayList<CalendarState> listDate = new ArrayList<>();
        for (int j = 0; j < (positionMonth + 1); j++) {
            listDate.add(j, new CalendarState(j, false, 0, 0, 0));
        }
        for (int i = 1; i < (maxDate + 1); i++) {
            listDate.add(positionMonth, new CalendarState(positionMonth, false, i, cal.get
                    (Calendar.MONTH), cal
                    .get(Calendar
                            .YEAR)));
            positionMonth++;
        }
        titleCalendar = "TH" + String.valueOf(cal.get(Calendar.MONTH) + 1) + " " + cal.get
                (Calendar.YEAR);
        return listDate;
    }

    public void getFirstDay(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, day);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                positionMonth = 6;
                break;
            case Calendar.MONDAY:
                positionMonth = 0;
                break;
            case Calendar.TUESDAY:
                positionMonth = 1;
                break;
            case Calendar.WEDNESDAY:
                positionMonth = 2;
                break;
            case Calendar.THURSDAY:
                positionMonth = 3;
                break;
            case Calendar.FRIDAY:
                positionMonth = 4;
                break;
            case Calendar.SATURDAY:
                positionMonth = 5;
                break;
        }
    }
}
