package com.example.namtn.punchclock.Presenter.LeavesPresenter;

import android.widget.ArrayAdapter;

import com.example.namtn.punchclock.CustomWidget.CustomCalendar.AdapterCalendarLeaves;
import com.example.namtn.punchclock.Model.LeavesModel.LeavesModel;
import com.example.namtn.punchclock.View.LeavesView;

public class LeavesPresenterImpl implements LeavesPresenter, LeavesModel.onLeavesListener {

    private LeavesModel leavesModel;
    private LeavesView leavesView;

    public LeavesPresenterImpl(LeavesModel leavesModel, LeavesView leavesView) {
        this.leavesModel = leavesModel;
        this.leavesView = leavesView;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void initCalendarSuccess(AdapterCalendarLeaves mAdapterCalendarLeaves) {
        if (leavesView != null){
            leavesView.fetchDataLeavesSuccess(mAdapterCalendarLeaves);
        }
    }

    @Override
    public void setTitleMonth(String prevMonth, String currentMonth, String nextMonth) {
        if (leavesView != null){
            leavesView.setTitleCalendar(prevMonth, currentMonth, nextMonth);
        }
    }

    @Override
    public void onShowDialog(ArrayAdapter<String> adapterMonth, ArrayAdapter<String> adapterYear) {
        if (leavesView != null){
            leavesView.showDialogSelect(adapterMonth, adapterYear);
        }
    }

    @Override
    public void onInitDataCalendar(int ofMonth, int year) {
        if (leavesModel != null) {
            leavesModel.initCalendarLeave(this, ofMonth, year);
        }
    }

    @Override
    public void showDialogSelectLeave() {
        if (leavesModel != null){
            leavesModel.configDialog(this);
        }
    }

    @Override
    public void hideDialogSelectLeave() {
        if (leavesView != null){
            leavesView.hideDialogSelect();
        }
    }

    @Override
    public void onDestroy() {
        leavesView = null;
    }
}
