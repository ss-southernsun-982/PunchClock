package com.example.namtn.punchclock.View;

import com.example.namtn.punchclock.Adapter.MenuMainAdapter;

public interface MainView {
    void initMenu(MenuMainAdapter adapter);

    void dateTimeData(int date, int month, int year, int hour, int minute, int second);
}
