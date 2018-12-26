package com.example.namtn.punchclock.Model.MainModel;

import com.example.namtn.punchclock.Adapter.MenuMainAdapter;

public interface MainModel {
    interface onMainListener{
        void menuInitSuccess(MenuMainAdapter adapter);

        void dateTimeData(int date, int month, int year, int hour, int minute, int second);
    }

    void menuConfig(onMainListener listener);

    void getDateTime(onMainListener listener);

    void IntentClass(Class c);

    void destroyHandle();
}
