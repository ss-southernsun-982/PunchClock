package com.example.namtn.punchclock.Presenter.MainPresenter;

public interface MainPresenter {

    void menuConfig();

    void getDateTime();

    void fetchDataDate();

    void pushDataCheckInAttendance();

    void pushDataCheckOutAttendance();

    void calculatorTimeWorking();

    void onDestroy();

    void checkVisibleCheckIn();

    void IntentClass(Class c);
}
