package com.example.namtn.punchclock.Model.LeavesModel;

import com.example.namtn.punchclock.Retrofit.RetrofitResponse.LeavesResult.LeavesData;

import java.util.ArrayList;

public interface LeavesModel {

    interface onLeavesListener {
        void showProgressBar();

        void hideProgressBar();

        void fetchDataLeavesSuccess(ArrayList<LeavesData> leavesData);

        void fetchDataLeavesError(String error);
    }

    void fetchDataLeaves(onLeavesListener listener);
}
