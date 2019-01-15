package com.example.namtn.punchclock.View;

import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.example.namtn.punchclock.CustomWidget.CustomCalendar.AdapterCalendarLeaves;

public interface LeavesView {

    void showProgressBar();

    void hideProgressBar();

    void fetchDataLeavesSuccess(AdapterCalendarLeaves mAdapterCalendarLeaves);

    void fetchDataLeavesError(String error);

    void setTitleCalendar(String prevMonth, String currentMonth, String nextMonth);

    void showDialogSelect(ArrayAdapter<String> adapterMonth, ArrayAdapter<String> adapterYear);

    void hideDialogSelect();
}
