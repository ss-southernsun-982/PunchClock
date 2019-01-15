package com.example.namtn.punchclock.Model.LeavesModel;

import android.widget.ArrayAdapter;

import com.example.namtn.punchclock.CustomWidget.CustomCalendar.AdapterCalendarLeaves;

public interface LeavesModel {

    interface onLeavesListener {
        void showProgressBar();

        void hideProgressBar();

        void initCalendarSuccess(AdapterCalendarLeaves mAdapterCalendarLeaves);

        void setTitleMonth(String prevMonth, String currentMonth, String nextMonth);

        void onShowDialog(ArrayAdapter<String> adapterMonth, ArrayAdapter<String> adapterYear);
    }

    void initCalendarLeave(onLeavesListener listener, int ofMonth, int year);

    void configDialog(onLeavesListener listener);
}
