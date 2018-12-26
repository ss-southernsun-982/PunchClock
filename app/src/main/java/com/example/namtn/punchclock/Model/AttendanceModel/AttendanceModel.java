package com.example.namtn.punchclock.Model.AttendanceModel;

import com.example.namtn.punchclock.Adapter.Attendance.AttendanceDateAdapter;
import com.example.namtn.punchclock.Adapter.Attendance.AttendanceMonthAdapter;

public interface AttendanceModel {

    interface onPushDataAttendanceListener {
        void showProgressBar();

        void hideProgressBar();

        void displayDataMonthAttendance(AttendanceMonthAdapter adapter, int position);

        void fetchDataAttendanceSuccess(AttendanceDateAdapter adapter);

        void fetchDataAttendanceError(String error);

        void onPushDataCheckInSuccess(String s);

        void onPushDataCheckInError(String s);

        void onPushDataCheckOutSuccess(String s);

        void onPushDataCheckOutError(String s);

        void displayTimeWorking(String time);
    }

    void fetchDataAttendance(onPushDataAttendanceListener listener);

    void pushDataCheckInAttendance(onPushDataAttendanceListener listener);

    void pushDataCheckOutAttendance(onPushDataAttendanceListener listener);

    void configDataMonthAttendance(onPushDataAttendanceListener listener);

    void calculatorTimeWorking(onPushDataAttendanceListener listener);

    void onDestroyHandle();
}
