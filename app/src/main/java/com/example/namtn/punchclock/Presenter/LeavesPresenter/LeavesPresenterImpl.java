package com.example.namtn.punchclock.Presenter.LeavesPresenter;

import com.example.namtn.punchclock.Model.LeavesModel.LeavesModel;
import com.example.namtn.punchclock.Retrofit.RetrofitResponse.LeavesResult.LeavesData;
import com.example.namtn.punchclock.View.LeavesView;

import java.util.ArrayList;

public class LeavesPresenterImpl implements LeavesPresenter, LeavesModel.onLeavesListener {

    private LeavesModel leavesModel;
    private LeavesView leavesView;

    public LeavesPresenterImpl(LeavesModel leavesModel, LeavesView leavesView) {
        this.leavesModel = leavesModel;
        this.leavesView = leavesView;
    }

    @Override
    public void showProgressBar() {
        if (leavesView != null){
            leavesView.showProgressBar();
        }
    }

    @Override
    public void hideProgressBar() {
        if (leavesView != null){
            leavesView.hideProgressBar();
        }
    }

    @Override
    public void fetchDataLeavesSuccess(ArrayList<LeavesData> leavesData) {
        if (leavesView != null){
            leavesView.fetchDataLeavesSuccess(leavesData);
        }
    }

    @Override
    public void fetchDataLeavesError(String error) {
        if (leavesView != null){
            leavesView.fetchDataLeavesError(error);
        }
    }

    @Override
    public void fetchDataLeaves() {
        if (leavesModel != null){
            leavesModel.fetchDataLeaves(this);
        }
    }

    @Override
    public void onDestroy() {
        leavesView = null;
    }
}
