package com.example.namtn.punchclock.Model.AttendanceModel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.StrictMode;
import android.text.format.Formatter;
import android.util.Log;

import com.example.namtn.punchclock.Adapter.Attendance.AttendanceDateAdapter;
import com.example.namtn.punchclock.Adapter.Attendance.AttendanceMonthAdapter;
import com.example.namtn.punchclock.Adapter.Attendance.AttendanceMonthModel;
import com.example.namtn.punchclock.Retrofit.RetrofitConfig.RetrofitAPIs;
import com.example.namtn.punchclock.Retrofit.RetrofitConfig.RetrofitUtils;
import com.example.namtn.punchclock.Retrofit.RetrofitResponse.AttendanceResult.AttendanceData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.WIFI_SERVICE;

public class AttendanceModelImpl implements AttendanceModel {

    private Context context;
    private Activity activity;
    private RetrofitAPIs retrofitAPIs;
    private CompositeDisposable compositeDisposable;
    private String TAG = "SSSSSSSS";
    private String emp_id, checkin_ip;
    private SharedPreferences preferences, preferencesUser;
    SharedPreferences.Editor editor;
    private Long mDistance;
    private ArrayList<AttendanceMonthModel> listMonth;
    private AttendanceDateAdapter adapter;
    private List<AttendanceData> listAttendanceData;
    private AttendanceMonthAdapter adapterMonth;
    private Calendar calendar;
    private long millisecond1, millisecond2, millisecond3;
    private Handler handler;
    private Runnable runnable;
    private String toDay;
    private SimpleDateFormat simpleDateFormat;

    public AttendanceModelImpl(Context context) {
        this.context = context;
        this.activity = (Activity) context;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll()
                    .build();
            StrictMode.setThreadPolicy(policy);
        }
        preferences = context.getSharedPreferences("data_map", Context.MODE_PRIVATE);
        preferencesUser = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        calendar = Calendar.getInstance();
        editor = preferencesUser.edit();
        listMonth = new ArrayList<>();
        listAttendanceData = new ArrayList<>();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    private String getIpAddress() {
        WifiManager wm = (WifiManager) context.getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        return ip;
    }

    @Override
    public void configDataMonthAttendance(onPushDataAttendanceListener listener) {
        int position = 0;
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        int month = c.get(Calendar.MONTH) + 1;
        listMonth.clear();
        listMonth.add(new AttendanceMonthModel(1, "JAN"));
        listMonth.add(new AttendanceMonthModel(2, "FEB"));
        listMonth.add(new AttendanceMonthModel(3, "MAR"));
        listMonth.add(new AttendanceMonthModel(4, "APR"));
        listMonth.add(new AttendanceMonthModel(5, "MAY"));
        listMonth.add(new AttendanceMonthModel(6, "JUNE"));
        listMonth.add(new AttendanceMonthModel(7, "JULY"));
        listMonth.add(new AttendanceMonthModel(8, "AUG"));
        listMonth.add(new AttendanceMonthModel(9, "SEPT"));
        listMonth.add(new AttendanceMonthModel(10, "OCT"));
        listMonth.add(new AttendanceMonthModel(11, "NOV"));
        listMonth.add(new AttendanceMonthModel(12, "DEC"));
        adapterMonth = new AttendanceMonthAdapter(listMonth, context);
        adapterMonth.notifyDataSetChanged();
        for (int i = 0; i < listMonth.size(); i++) {
            if (listMonth.get(i).getId() == month) {
                position = i;
            }
        }
        listener.displayDataMonthAttendance(adapterMonth, position);
    }

    @Override
    public void fetchDataAttendance(onPushDataAttendanceListener listener) {
        listener.showProgressBar();
        listAttendanceData.clear();
        calendar = Calendar.getInstance();
        Date c = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat monthYear = new SimpleDateFormat("MM-yyyy");
        emp_id = preferencesUser.getString("userId", "0");
        retrofitAPIs = RetrofitUtils.apiAttendance();
        compositeDisposable = new CompositeDisposable(retrofitAPIs.getAttendance(emp_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        s -> {
                            editor.putBoolean("isCheckIn", false);
                            editor.putBoolean("isCheckOut", false);
                            editor.putLong("timeStart", 0);
                            editor.putLong("timeEnd", 0);
                            editor.commit();
                            AttendanceData data;
                            Date checkIn = null;
                            Date checkOut = null;
                            Date date = null;
                            Date date2 = null;
                            data = s.getData().get(s.getData().size() - 1);
                            if (!data.getDate().equalsIgnoreCase(String.valueOf
                                    (df.format(c)))) {
                                data = new AttendanceData();
                                data.setDate(df.format(c));
                                listAttendanceData.add(data);
                            } else {
                                checkIn = dateFormat.parse(data.getCheckIn());
                                checkOut = dateFormat.parse(data.getCheckOut());
                                Log.d(TAG, "fetchDataAttendance: " + data.getCheckOut());
                                if (data.getCheckIn().equalsIgnoreCase
                                        ("0000-00-00 00:00:00")) {
                                    editor.putBoolean("isCheckIn", false);
                                    editor.putLong("timeStart", 0);
                                    editor.commit();
                                } else if (!data.getCheckIn().equalsIgnoreCase
                                        ("0000-00-00 00:00:00")) {
                                    editor.putBoolean("isCheckIn", true);
                                    editor.putLong("timeStart", checkIn.getTime());
                                    Log.d(TAG, "time start: " + checkIn.getTime());
                                    editor.commit();
                                }
                                if (data.getCheckOut().equalsIgnoreCase
                                        ("0000-00-00 00:00:00")) {
                                    editor.putBoolean("isCheckOut", false);
                                    editor.putLong("timeEnd", 0);
                                    Log.d(TAG, "time end: " + checkOut.getTime());
                                    editor.commit();
                                } else if (!data.getCheckOut().equalsIgnoreCase
                                        ("0000-00-00 00:00:00")) {
                                    editor.putBoolean("isCheckOut", true);
                                    editor.putLong("timeEnd", checkOut.getTime());
                                    editor.commit();
                                }
                            }
                            for (int i = 0; i < s.getData().size(); i++) {
                                date = df.parse(s.getData().get(i).getDate());
                                if (monthYear.format(c).equalsIgnoreCase(monthYear.format(date))) {
                                    if (i > 0) {
                                        date2 = df.parse(s.getData().get(i - 1).getDate());
                                        if (!df.format(date).equalsIgnoreCase(df.format(date2))) {
                                            listAttendanceData.add(s.getData().get(i));
                                        }
                                    }
                                }
                            }
                            Collections.sort(listAttendanceData, new Comparator<AttendanceData>() {
                                @Override
                                public int compare(AttendanceData o1, AttendanceData o2) {
                                    Date date1 = null;
                                    Date date2 = null;
                                    long time1 = 0;
                                    long time2 = 0;
                                    try {
                                        date1 = df.parse(o1.getDate());
                                        date2 = df.parse(o2.getDate());

                                        time1 = date1.getTime();
                                        time2 = date2.getTime();
                                    } catch (Exception e) {
                                        Log.d(TAG, "compare: error " + e.getMessage());
                                    }
                                    return Long.compare(time2, time1);
                                }
                            });

                            if (preferencesUser.getBoolean("isCheckIn", false) == true) {
                                listener.onPushDataCheckInSuccess(null);
                            } else if (preferencesUser.getBoolean("isCheckIn", true) == false) {
                                listener.onPushDataCheckInError(null);
                            }
                            if (preferencesUser.getBoolean("isCheckOut", false) == true) {
                                listener.onPushDataCheckOutSuccess(null);
                            } else if (preferencesUser.getBoolean("isCheckOut", true) == false) {
                                listener.onPushDataCheckOutError(null);
                            }

                            adapter = new AttendanceDateAdapter(context, listAttendanceData);
                            adapter.notifyDataSetChanged();
                            listener.fetchDataAttendanceSuccess(adapter);
                            listener.hideProgressBar();
                        },
                        throwable -> {
                            Log.d(TAG, "fetchDataAttendanceError: " + throwable.getMessage());
                            listener.fetchDataAttendanceError(throwable.getMessage());
                            listener.hideProgressBar();
                        }
                )
        );
    }

    @Override
    public void pushDataCheckInAttendance(onPushDataAttendanceListener listener) {
        calendar = Calendar.getInstance();
        retrofitAPIs = RetrofitUtils.apiAttendance();
        compositeDisposable = new CompositeDisposable();
        listener.showProgressBar();
        checkin_ip = getIpAddress();
        emp_id = preferencesUser.getString("userId", "0");
        mDistance = preferences.getLong("mDistance", 1001);
        if (mDistance > 1000) {
            listener.onPushDataCheckInError("Bạn đang không có mặt ở công ty, vui lòng đến công " +
                    "ty và " +
                    "thực hiện " +
                    "lại nha");
            listener.hideProgressBar();
        } else {
            compositeDisposable.add(retrofitAPIs.checkIn(emp_id, checkin_ip)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            s -> {
                                listener.hideProgressBar();
                                this.calculatorTimeWorking(listener);
                                this.fetchDataAttendance(listener);
                            }, throwable -> {
                                listener.hideProgressBar();
                                listener.onPushDataCheckInError("Vui lòng kiểm tra dữ liệu!");
                                Log.d(TAG, "pushDataAttendance: " + throwable.getMessage());
                            })
            );
        }
    }

    @Override
    public void pushDataCheckOutAttendance(onPushDataAttendanceListener listener) {
        retrofitAPIs = RetrofitUtils.apiAttendance();
        compositeDisposable = new CompositeDisposable();
        listener.showProgressBar();
        calendar = Calendar.getInstance();
        Date c = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        toDay = preferences.getString("mLocationTime", "0");
        checkin_ip = getIpAddress();
        emp_id = preferencesUser.getString("userId", "0");
        compositeDisposable.add(retrofitAPIs.checkOut(df.format(c), emp_id, checkin_ip)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        s -> {
                            listener.onPushDataCheckOutSuccess(s.getMessage());
                            this.onDestroyHandle();
                            this.fetchDataAttendance(listener);
                        },
                        throwable -> {
                            listener.onPushDataCheckOutError(throwable.getMessage());
                            Log.d(TAG, "pushDataCheckOutAttendance: " + throwable.getMessage());
                        }
                )
        );
    }

    @Override
    public void calculatorTimeWorking(onPushDataAttendanceListener listener) {
        millisecond3 = preferencesUser.getLong("timeStart", 0);
        millisecond2 = preferencesUser.getLong("timeEnd", 0);
        if (millisecond3 != 0 && millisecond2 == 0) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    calendar = Calendar.getInstance();
                    millisecond2 = calendar.getTimeInMillis();
                    long millisecond = millisecond2 - millisecond3;
                    long seconds = millisecond / 1000;
                    long minutes = seconds / 60;
                    long hours = minutes / 60;
                    long days = hours / 24;
                    String time = hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;
                    listener.displayTimeWorking(time);
                    handler.postDelayed(runnable, 1000);
                }
            };
            handler = new Handler();
            handler.postDelayed(runnable, 0);
        } else {
            this.onDestroyHandle();
            long millisecond = millisecond2 - millisecond3;
            long seconds = millisecond / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            String time = hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;
            listener.displayTimeWorking(time);
            Log.d(TAG, "calculatorTimeWorking: Disable time working!!!");
        }
    }

    @Override
    public void checkVisibleCheckIn(onPushDataAttendanceListener listener) {
//        if (!preferencesUser.getString("checkin", "0").equalsIgnoreCase(simpleDateFormat.format
//                (calendar.getTime()))){
//            listener.visibleCheckIn(true);
//        } else {
//            listener.visibleCheckIn(false);
//        }
    }

    @Override
    public void onDestroyHandle() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
