package com.example.namtn.punchclock.Presenter.AssignPresenter;

import com.example.namtn.punchclock.Adapter.SpinnerAdapter;
import com.example.namtn.punchclock.CustomWidget.CustomCalendar.AdapterCalendar;
import com.example.namtn.punchclock.Model.AssignLeaves.AssignLeavesModel;
import com.example.namtn.punchclock.View.AssignLeaveView;

public class AssignPresenterImpl implements AssignPresenter, AssignLeavesModel
        .onAssignLeaveListener {

    private AssignLeavesModel mAssignLeavesModel;
    private AssignLeaveView mAssignLeaveView;

    public AssignPresenterImpl(AssignLeavesModel mAssignLeavesModel, AssignLeaveView
            mAssignLeaveView) {
        this.mAssignLeavesModel = mAssignLeavesModel;
        this.mAssignLeaveView = mAssignLeaveView;
    }

    @Override
    public void configSpinnerTypeDaySuccess(SpinnerAdapter adapter) {
        if (mAssignLeaveView != null){
            mAssignLeaveView.configSpinnerTypeDays(adapter);
        }
    }

    @Override
    public void configCalendarSuccess(AdapterCalendar adapterCalendar) {
        if (mAssignLeaveView != null){
            mAssignLeaveView.configDataCalendar(adapterCalendar);
        }
    }

    @Override
    public void setTextMonth(String s) {
        if (mAssignLeaveView != null){
            mAssignLeaveView.setTextMonth(s);
        }
    }

    @Override
    public void configSpinnerTypeDay() {
        if (mAssignLeavesModel != null){
            mAssignLeavesModel.configSpinnerTypeDays(this);
        }
    }

    @Override
    public void configDataCalender(int ofMonth) {
        if (mAssignLeavesModel != null){
            mAssignLeavesModel.configCalendar(this, ofMonth);
        }
    }

    @Override
    public void selectDays(int position) {
        if (mAssignLeavesModel != null){
            mAssignLeavesModel.daysSelected(position);
        }
    }

    @Override
    public void showDialogDay() {
        if (mAssignLeaveView != null){
            mAssignLeaveView.showDialogDay();
        }
    }

    @Override
    public void hideDialogDay() {
        if (mAssignLeaveView != null){
            mAssignLeaveView.hideDialogDay();
        }
    }

    @Override
    public void showProgressBar() {
        if (mAssignLeaveView != null) {
            mAssignLeaveView.showProgressBar();
        }
    }

    @Override
    public void hideProgressBar() {
        if (mAssignLeaveView != null) {
            mAssignLeaveView.hideProgressBar();
        }
    }

    @Override
    public void pushDataAssignLeaveSuccess(String message) {
        if (mAssignLeaveView != null) {
            mAssignLeaveView.pushDataAssignLeaveSuccess(message);
        }
    }

    @Override
    public void pushDataAssignLeaveError(String error) {
        if (mAssignLeaveView != null) {
            mAssignLeaveView.pushDataAssignLeaveError(error);
        }
    }

    @Override
    public void setDateLeave(String s) {
        if (mAssignLeaveView != null){
            mAssignLeaveView.setDateLive(s);
        }
    }

    @Override
    public void pushDataAssignLeave(String reason, String halfDay,
                                    String type_id) {
        if (mAssignLeavesModel != null) {
            mAssignLeavesModel.pushDataAssignLeave(this, reason, halfDay, type_id);
        }
    }

    @Override
    public void setDateLive() {
        if (mAssignLeavesModel != null){
            mAssignLeavesModel.setDateLive(this);
        }
    }

    @Override
    public void onDestroy() {
        mAssignLeaveView = null;
    }
}
