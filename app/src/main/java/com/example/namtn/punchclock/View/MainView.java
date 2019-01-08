package com.example.namtn.punchclock.View;

import com.example.namtn.punchclock.Adapter.MenuMainAdapter;

public interface MainView {
    void initMenu(MenuMainAdapter adapter);

    void pushDataCheckInSuccess(String message);

    void pushDataCheckInError(String error);

    void pushDataCheckOutSuccess(String message);

    void pushDataCheckOutError(String error);

    void displayTimeWorking(String time);

    void visibleCheckIn(boolean b);

    void dateTimeData(int date, int month, int year, int hour, int minute, int second);
}
