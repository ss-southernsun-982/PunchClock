package com.example.namtn.punchclock.Presenter.LeavesPresenter;

public interface LeavesPresenter {
    void onInitDataCalendar(int ofMonth, int year);

    void showDialogSelectLeave();

    void hideDialogSelectLeave();

    void onDestroy();
}
