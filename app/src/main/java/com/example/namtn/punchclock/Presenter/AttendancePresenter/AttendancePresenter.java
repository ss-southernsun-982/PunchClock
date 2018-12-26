package com.example.namtn.punchclock.Presenter.AttendancePresenter;

public interface AttendancePresenter {
    void configDataMonth();

    void fetchDataDate();

    void pushDataCheckInAttendance();

    void pushDataCheckOutAttendance();

    void calculatorTimeWorking();

    void onDestroy();
}
