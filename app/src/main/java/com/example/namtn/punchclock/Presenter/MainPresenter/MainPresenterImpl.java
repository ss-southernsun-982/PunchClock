package com.example.namtn.punchclock.Presenter.MainPresenter;

import com.example.namtn.punchclock.Adapter.Attendance.AttendanceDateAdapter;
import com.example.namtn.punchclock.Adapter.Attendance.AttendanceMonthAdapter;
import com.example.namtn.punchclock.Adapter.MenuMainAdapter;
import com.example.namtn.punchclock.Model.AttendanceModel.AttendanceModel;
import com.example.namtn.punchclock.Model.MainModel.MainModel;
import com.example.namtn.punchclock.View.MainView;

public class MainPresenterImpl implements MainPresenter, MainModel.onMainListener,
        AttendanceModel.onPushDataAttendanceListener {

    private MainView mainView;
    private MainModel mainModel;
    private AttendanceModel attendanceModel;

    public MainPresenterImpl(MainView mainView, MainModel mainModel, AttendanceModel attendanceModel) {
        this.mainView = mainView;
        this.mainModel = mainModel;
        this.attendanceModel = attendanceModel;
    }

    @Override
    public void menuInitSuccess(MenuMainAdapter adapter) {
        if (mainView != null) {
            mainView.initMenu(adapter);
        }
    }

    @Override
    public void dateTimeData(int date, int month, int year, int hour, int minute, int second) {
        if (mainView != null) {
            mainView.dateTimeData(date, month, year, hour, minute, second);
        }
    }

    @Override
    public void menuConfig() {
        if (mainModel != null) {
            mainModel.menuConfig(this);
        }
    }

    @Override
    public void getDateTime() {
        if (mainModel != null) {
            mainModel.getDateTime(this);
        }
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
    public void calculatorTimeWorking() {
        if (attendanceModel != null){
            attendanceModel.calculatorTimeWorking(this);
        }
    }

    @Override
    public void onDestroy() {
        mainView = null;
        mainModel.destroyHandle();
    }

    @Override
    public void checkVisibleCheckIn() {
        if (attendanceModel != null){
            attendanceModel.checkVisibleCheckIn(this);
        }
    }

    @Override
    public void IntentClass(Class c) {
        if (mainModel != null) {
            mainModel.IntentClass(c);
        }
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void displayDataMonthAttendance(AttendanceMonthAdapter adapter, int position) {

    }

    @Override
    public void fetchDataAttendanceSuccess(AttendanceDateAdapter adapter) {

    }

    @Override
    public void fetchDataAttendanceError(String error) {

    }

    @Override
    public void onPushDataCheckInSuccess(String s) {
        if (mainView != null){
            mainView.pushDataCheckInSuccess(s);
        }
    }

    @Override
    public void onPushDataCheckInError(String s) {
        if (mainView != null){
            mainView.pushDataCheckInError(s);
        }
    }

    @Override
    public void onPushDataCheckOutSuccess(String s) {
        if (mainView != null){
            mainView.pushDataCheckOutSuccess(s);
        }
    }

    @Override
    public void onPushDataCheckOutError(String s) {
        if (mainView != null){
            mainView.pushDataCheckOutError(s);
        }
    }

    @Override
    public void displayTimeWorking(String time) {
        if (mainView != null){
            mainView.displayTimeWorking(time);
        }
    }

    @Override
    public void visibleCheckIn(boolean b) {
        if (mainView != null){
            mainView.visibleCheckIn(b);
        }
    }
}
