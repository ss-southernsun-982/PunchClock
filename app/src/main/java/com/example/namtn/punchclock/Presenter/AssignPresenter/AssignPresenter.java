package com.example.namtn.punchclock.Presenter.AssignPresenter;

public interface AssignPresenter {

    void configSpinnerTypeDay();

    void showDialogDay();

    void hideDialogDay();

    void configDataCalender(int ofMonth);

    void selectDays(int position);

    void pushDataAssignLeave(String reason, String halfDay, String
            type_id);

    void setDateLive();

    void onDestroy();
}
