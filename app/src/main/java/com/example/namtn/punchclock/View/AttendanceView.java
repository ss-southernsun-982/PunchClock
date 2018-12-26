package com.example.namtn.punchclock.View;

import com.example.namtn.punchclock.Adapter.Attendance.AttendanceDateAdapter;
import com.example.namtn.punchclock.Adapter.Attendance.AttendanceMonthAdapter;

public interface AttendanceView {
    void showProgressBar();

    void hideProgressBar();

    void displayDataMonth(AttendanceMonthAdapter adapter, int position);

    void displayDataDateOfMonth(AttendanceDateAdapter adapter);

    void pushDataCheckInSuccess(String message);

    void pushDataCheckInError(String error);

    void pushDataCheckOutSuccess(String message);

    void pushDataCheckOutError(String error);

    void displayTimeWorking(String time);
}
