package com.example.namtn.punchclock.Model.MainModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.example.namtn.punchclock.Adapter.MenuMainAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainModelImpl implements MainModel {

    private Context context;
    private Activity activity;
    private ArrayList<MenuModel> listMenu;
    private MenuMainAdapter adapter;
    private String TAG = "TIMEEEEw";
    private int date, month, year, hour, minute, second;
    Runnable runnable;
    Handler handler;
    private DateFormat dateFormat;

    public MainModelImpl(Context context) {
        this.context = context;
        this.activity = (Activity) context;
        listMenu = new ArrayList<>();
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }

    @Override
    public void menuConfig(onMainListener listener) {
//        listMenu.add(new MenuModel(R.drawable.ic_attendance, "Attendance"));
//        listMenu.add(new MenuModel(R.drawable.ic_leave, "Attendance"));
//        listMenu.add(new MenuModel(R.drawable.ic_salary, "Attendance"));
//        listMenu.add(new MenuModel(R.drawable.ic_report, "Attendance"));
//        adapter = new MenuMainAdapter(context, listMenu);
//        adapter.notifyDataSetChanged();
//        listener.menuInitSuccess(adapter);
    }

    @Override
    public void getDateTime(onMainListener listener) {
//        runnable = new Runnable() {
//            @Override
//            public void run() {
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        date = c.get(Calendar.DATE);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        second = c.get(Calendar.SECOND);
        listener.dateTimeData(date, month + 1, year, hour, minute, second);
//                handler.postDelayed(runnable, 1000);
//            }
//        };
//        handler = new Handler();
//        handler.postDelayed(runnable, 0);
    }

    @Override
    public void IntentClass(Class c) {
        Intent intent = new Intent(context, c);
        activity.startActivity(intent);
//        this.destroyHandle();
    }

    @Override
    public void destroyHandle() {
//        if (handler != null && runnable != null){
//            handler.removeCallbacks(runnable);
//        }
    }
}
