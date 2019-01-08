package com.example.namtn.punchclock.View;

import com.example.namtn.punchclock.Adapter.SpinnerAdapter;
import com.example.namtn.punchclock.CustomWidget.CustomCalendar.AdapterCalendar;

public interface AssignLeaveView {
    void configSpinnerTypeDays(SpinnerAdapter adapter);

    void showDialogDay();

    void hideDialogDay();

    void configDataCalendar(AdapterCalendar adapterCalendar);

    void setTextMonth(String s);

    void showProgressBar();

    void hideProgressBar();

    void pushDataAssignLeaveSuccess(String message);

    void pushDataAssignLeaveError(String error);

    void setDateLive(String date);
}
