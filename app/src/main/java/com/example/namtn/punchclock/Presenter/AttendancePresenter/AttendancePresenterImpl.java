package com.example.namtn.punchclock.Presenter.AttendancePresenter;

import com.example.namtn.punchclock.Adapter.Attendance.AttendanceDateAdapter;
import com.example.namtn.punchclock.Adapter.Attendance.AttendanceMonthAdapter;
import com.example.namtn.punchclock.Model.AttendanceModel.AttendanceModel;
import com.example.namtn.punchclock.View.AttendanceView;

public class AttendancePresenterImpl implements AttendancePresenter, AttendanceModel
        .onPushDataAttendanceListener {

    AttendanceView attendanceView;
    AttendanceModel attendanceModel;

    public AttendancePresenterImpl(AttendanceView attendanceView, AttendanceModel attendanceModel) {
        this.attendanceView = attendanceView;
        this.attendanceModel = attendanceModel;
    }

    @Override
    public void showProgressBar() {
        if (attendanceView != null) {
            attendanceView.showProgressBar();
        }
    }

    @Override
    public void hideProgressBar() {
        if (attendanceView != null) {
            attendanceView.hideProgressBar();
        }
    }

    @Override
    public void fetchDataAttendanceSuccess(AttendanceDateAdapter adapter) {
        if (attendanceView != null){
            attendanceView.displayDataDateOfMonth(adapter);
        }
    }

    @Override
    public void fetchDataAttendanceError(String error) {
        if (attendanceView != null){

        }
    }

    @Override
    public void onPushDataCheckInSuccess(String s) {
        if (attendanceView != null) {
            attendanceView.pushDataCheckInSuccess(s);
        }
    }

    @Override
    public void onPushDataCheckInError(String s) {
        if (attendanceView != null) {
            attendanceView.pushDataCheckInError(s);
        }
    }

    @Override
    public void onPushDataCheckOutSuccess(String s) {
        if (attendanceView != null) {
            attendanceView.pushDataCheckOutSuccess(s);
        }
    }

    @Override
    public void onPushDataCheckOutError(String s) {
        if (attendanceView != null) {
            attendanceView.pushDataCheckOutError(s);
        }
    }

    @Override
    public void displayDataMonthAttendance(AttendanceMonthAdapter adapter, int position) {
        if (attendanceView != null){
            attendanceView.displayDataMonth(adapter, position);
        }
    }

    @Override
    public void displayTimeWorking(String time) {
        if (attendanceModel != null){
            attendanceView.displayTimeWorking(time);
        }
    }

    @Override
    public void visibleCheckIn(boolean b) {

    }

    @Override
    public void fetchDataDate() {
        if (attendanceModel != null){
            attendanceModel.fetchDataAttendance(this);
        }
    }

    @Override
    public void pushDataCheckInAttendance() {
        if (attendanceModel != null){
            attendanceModel.pushDataCheckInAttendance(this);
        }
    }

    @Override
    public void pushDataCheckOutAttendance() {
        if (attendanceModel != null){
            attendanceModel.pushDataCheckOutAttendance(this);
        }
    }

    @Override
    public void configDataMonth() {
        if (attendanceModel != null){
            attendanceModel.configDataMonthAttendance(this);
        }
    }

    @Override
    public void calculatorTimeWorking() {
        if (attendanceModel != null){
            attendanceModel.calculatorTimeWorking(this);
        }
    }

    @Override
    public void onDestroy() {
        attendanceView = null;
        attendanceModel.onDestroyHandle();
    }
}
