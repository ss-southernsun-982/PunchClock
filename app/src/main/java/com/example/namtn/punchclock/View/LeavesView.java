package com.example.namtn.punchclock.View;

import com.example.namtn.punchclock.Retrofit.RetrofitResponse.LeavesResult.LeavesData;

import java.util.ArrayList;

public interface LeavesView {

    void showProgressBar();

    void hideProgressBar();

    void fetchDataLeavesSuccess(ArrayList<LeavesData> dataLeaves);

    void fetchDataLeavesError(String error);
}
