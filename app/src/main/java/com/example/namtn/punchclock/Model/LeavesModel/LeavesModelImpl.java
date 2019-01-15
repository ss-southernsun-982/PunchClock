package com.example.namtn.punchclock.Model.LeavesModel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.namtn.punchclock.CustomWidget.CustomCalendar.AdapterCalendarLeaves;
import com.example.namtn.punchclock.CustomWidget.CustomCalendar.CalendarSateLeaves;
import com.example.namtn.punchclock.Retrofit.RetrofitConfig.RetrofitAPIs;
import com.example.namtn.punchclock.Retrofit.RetrofitConfig.RetrofitUtils;
import com.example.namtn.punchclock.Retrofit.RetrofitResponse.LeavesResult.LeavesData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LeavesModelImpl implements LeavesModel {

    private Context context;
    private Activity activity;
    private RetrofitAPIs retrofit;
    private CompositeDisposable compositeDisposable;
    private SharedPreferences preferences;
    private String TAG = "LEAVES_MODEL";
    private ArrayList<CalendarSateLeaves> mListCalendarState;
    private ArrayList<LeavesData> listData;
    private AdapterCalendarLeaves mAdapterCalendarLeaves;
    private String currentMonth = "", prevMonth = "", nextMonth = "";
    private String date = "";
    private SimpleDateFormat simpleDateFormat, formatTitle;
    private int position = 0;
    private SharedPreferences.Editor editor;
    private Calendar calendar;
    private int p = 0, month = 0;
    private int positionMonth = 0;
    private ArrayAdapter<String> adapterMonth;
    private ArrayList<String> arrayListMonth;
    private ArrayAdapter<String> adapterYear;
    private ArrayList<String> arrayListYear;

    public LeavesModelImpl(Context context) {
        this.context = context;
        this.activity = (Activity) context;
        retrofit = RetrofitUtils.apiLeaves();
        compositeDisposable = new CompositeDisposable();
        preferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        editor = preferences.edit();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        formatTitle = new SimpleDateFormat("MMM-yyyy");
        listData = new ArrayList<>();
        mListCalendarState = new ArrayList<>();
        arrayListMonth = new ArrayList<>();
        arrayListYear = new ArrayList<>();
        adapterMonth = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayListMonth);
        adapterYear = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayListYear);
    }

    @Override
    public void initCalendarLeave(onLeavesListener listener, int ofMonth, int year) {
        String emp_id = preferences.getString("userId", "0");
        mListCalendarState.clear();
        compositeDisposable.add(retrofit.leaves(emp_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        s -> {
                            addData(ofMonth, year, listener, (ArrayList<LeavesData>) s.getData());
                        },
                        throwable -> {
                            Log.d(TAG, "fetchDataLeaves error: " + throwable.getMessage());
                        }
                )
        );

    }

    @Override
    public void configDialog(onLeavesListener listener) {
        calendar = Calendar.getInstance();
        for (int i = 1; i < 13; i++) {
            arrayListMonth.add("Tháng " + String.valueOf(i));
        }
        for (int i = 2016; i <= calendar.get(Calendar.YEAR); i++) {
            arrayListYear.add(String.valueOf(i));
        }
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterMonth.notifyDataSetChanged();
        adapterYear.notifyDataSetChanged();
        listener.onShowDialog(adapterMonth, adapterYear);
    }

    public void addData(int ofMonth, int year, onLeavesListener listener, ArrayList<LeavesData> mLeavesData) {
        mListCalendarState.clear();
        mListCalendarState.addAll(addDate(ofMonth, year, listener));
        mAdapterCalendarLeaves = new AdapterCalendarLeaves(context, mListCalendarState, mLeavesData);
        mAdapterCalendarLeaves.notifyDataSetChanged();
        Log.d(TAG, "initCalendarLeave: " + listData.size());
        listener.initCalendarSuccess(mAdapterCalendarLeaves);
    }

    private ArrayList<CalendarSateLeaves> addDate(int ofMonth1, int year, onLeavesListener listener) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(cal.getTime());
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.YEAR, year);
        cal.add(Calendar.MONTH, ofMonth1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        getFirstDay(1, cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
//        positionMonth
        int maxDate = cal.get(Calendar.DAY_OF_MONTH);
        currentMonth = formatTitle.format(cal.getTime());
        month = cal.get(Calendar.MONTH);
        Log.d(TAG, "addDate current month: " + currentMonth);
        ArrayList<CalendarSateLeaves> listDate = new ArrayList<>();
        cal.add(Calendar.DATE, -1);
        for (int j = 0; j < positionMonth; j++) {
            int date = cal.get(Calendar.DATE);
            listDate.add(j, new CalendarSateLeaves(j, false, (date - j), 0, 0));
        }
        Collections.reverse(listDate);
        for (int i = 1; i < (maxDate + 1); i++) {
            listDate.add(positionMonth, new CalendarSateLeaves(positionMonth, true, i, month, cal
                    .get(Calendar.YEAR)));
            positionMonth++;
        }
        for (int i = 0; i < (37 - listDate.size()); i++) {
            listDate.add(positionMonth, new CalendarSateLeaves(positionMonth, false, (i + 1), cal
                    .get(Calendar.MONTH), 0));
            positionMonth++;
        }

        //next month
        nextMonth = getNextMonth(ofMonth1, cal);
        Log.d(TAG, "addDate: next month: " + nextMonth);
        prevMonth = getPrevMonth(ofMonth1, cal);
        Log.d(TAG, "addDate: prev month: " + prevMonth);
        listener.setTitleMonth(prevMonth, currentMonth, nextMonth);
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

    public String getNextMonth(int ofMonth, Calendar cal) {
        cal.add(Calendar.MONTH, 1);
        return formatTitle.format(cal.getTime());
    }

    public String getPrevMonth(int ofMonth, Calendar cal) {
        cal.add(Calendar.MONTH, -2);
        return formatTitle.format(cal.getTime());
    }
}
