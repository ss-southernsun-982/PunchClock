package com.example.namtn.punchclock.View;

import com.example.namtn.punchclock.CustomWidget.CustomCalendar.AdapterCalendarLeaves;

public interface LeavesView {

    void showProgressBar();

    void hideProgressBar();

    void fetchDataLeavesSuccess(AdapterCalendarLeaves mAdapterCalendarLeaves);

    void fetchDataLeavesError(String error);

    void setTitleCalendar(String prevMonth, String currentMonth, String nextMonth);
}
