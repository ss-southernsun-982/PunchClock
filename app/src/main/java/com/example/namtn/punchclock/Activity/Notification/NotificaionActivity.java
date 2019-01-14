package com.example.namtn.punchclock.Activity.Notification;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.namtn.punchclock.Activity.BaseActivity;
import com.example.namtn.punchclock.Adapter.AdapterNotiicaion;
import com.example.namtn.punchclock.ModelView.NotificationModelVIew;
import com.example.namtn.punchclock.R;

import java.util.ArrayList;

public class NotificaionActivity extends BaseActivity {

    private ListView mListView;
    private ArrayList<Object> mArrayListNotification;
    private AdapterNotiicaion mAdapterNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_notificaion;
    }

    @Override
    protected void initView() {
        mListView = findViewById(R.id.list_view_notification);
    }

    @Override
    protected void initEventControl() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    protected void initData() {
        mArrayListNotification = new ArrayList<>();
        mArrayListNotification.add(new NotificationModelVIew("Our trip in Las Vegas", "This count...."));
        mArrayListNotification.add(new NotificationModelVIew("Our trip in Las Vegas2", "This count...."));
        mArrayListNotification.add(new NotificationModelVIew("Our trip in Las Vegas3", "This count...."));
        mArrayListNotification.add(new String("Previous"));
        mArrayListNotification.add(new NotificationModelVIew("Our trip in Las Vegas4", "This count...."));
        mArrayListNotification.add(new NotificationModelVIew("Our trip in Las Vegas5", "This count...."));
        mArrayListNotification.add(new NotificationModelVIew("Our trip in Las Vegas6", "This count...."));
        mArrayListNotification.add(new NotificationModelVIew("Our trip in Las Vegas7", "This count...."));
        mArrayListNotification.add(new NotificationModelVIew("Our trip in Las Vegas8", "This count...."));
        mAdapterNotification = new AdapterNotiicaion(this, mArrayListNotification);
        mListView.setAdapter(mAdapterNotification);
    }
}
