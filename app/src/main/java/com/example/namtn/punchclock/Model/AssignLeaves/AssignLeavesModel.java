package com.example.namtn.punchclock.Model.AssignLeaves;

import com.example.namtn.punchclock.Adapter.SpinnerAdapter;
import com.example.namtn.punchclock.CustomWidget.CustomCalendar.AdapterCalendar;

public interface AssignLeavesModel {

    interface onAssignLeaveListener {

        void configSpinnerTypeDaySuccess(SpinnerAdapter adapter);

        void configCalendarSuccess(AdapterCalendar adapterCalendar);

        void setTextMonth(String s);

        void showProgressBar();

        void hideProgressBar();

        void pushDataAssignLeaveSuccess(String message);

        void pushDataAssignLeaveError(String error);

        void setDateLeave(String s);
    }

    void configSpinnerTypeDays(onAssignLeaveListener listener);

    void pushDataAssignLeave(onAssignLeaveListener listener, String reason, String
            halfDay, String type_id);

    void configCalendar(onAssignLeaveListener listener, int ofMonth);

    void daysSelected(int position);

    void setDateLive(onAssignLeaveListener listener);
}
