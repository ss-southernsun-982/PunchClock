package com.example.namtn.punchclock.Model.MainModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.example.namtn.punchclock.Adapter.MenuMainAdapter;
import com.example.namtn.punchclock.R;

import java.util.ArrayList;
import java.util.Calendar;

public class MainModelImpl implements MainModel {

    private Context context;
    private Activity activity;
    private ArrayList<MenuModel> listMenu;
    private MenuMainAdapter adapter;
    private String TAG = "TIMEEEEw";
    private int date, month, year, hour, minute, second;
    Runnable runnable;
    Handler handler;

    public MainModelImpl(Context context) {
        this.context = context;
        this.activity = (Activity) context;
        listMenu = new ArrayList<>();
    }

    @Override
    public void menuConfig(onMainListener listener) {
        listMenu.add(new MenuModel(R.drawable.ic_attendance, "Attendance"));
        listMenu.add(new MenuModel(R.drawable.ic_leave, "Attendance"));
        listMenu.add(new MenuModel(R.drawable.ic_salary, "Attendance"));
        listMenu.add(new MenuModel(R.drawable.ic_report, "Attendance"));
        adapter = new MenuMainAdapter(context, listMenu);
        adapter.notifyDataSetChanged();
        listener.menuInitSuccess(adapter);
    }

    @Override
    public void getDateTime(onMainListener listener) {
        runnable = new Runnable() {
            @Override
            public void run() {
                Calendar c = Calendar.getInstance();
                date = c.get(Calendar.DATE);
                month = c.get(Calendar.MONTH);
                year = c.get(Calendar.YEAR);
                hour = c.get(Calendar.HOUR);
                minute = c.get(Calendar.MINUTE);
                second = c.get(Calendar.SECOND);
                listener.dateTimeData(date, month + 1, year, hour, minute, second);
                handler.postDelayed(runnable, 1000);
            }
        };
        handler = new Handler();
        handler.postDelayed(runnable, 1000);
    }

    @Override
    public void IntentClass(Class c) {
        Intent intent = new Intent(context, c);
        activity.startActivity(intent);
        handler.removeCallbacks(runnable);
    }
}
