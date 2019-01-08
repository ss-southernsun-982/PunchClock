package com.example.namtn.punchclock.Model.LeavesModel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.namtn.punchclock.Retrofit.RetrofitConfig.RetrofitAPIs;
import com.example.namtn.punchclock.Retrofit.RetrofitConfig.RetrofitUtils;
import com.example.namtn.punchclock.Retrofit.RetrofitResponse.LeavesResult.LeavesData;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LeavesModelImpl implements LeavesModel {

    private Context context;
    private Activity activity;
    private RetrofitAPIs retrofit;
    private CompositeDisposable compositeDisposable;
    private SharedPreferences preferences;
    private String TAG = "LEAVES_MODEL";

    public LeavesModelImpl(Context context) {
        this.context = context;
        this.activity = (Activity) context;
        retrofit = RetrofitUtils.apiLeaves();
        compositeDisposable = new CompositeDisposable();
        preferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
    }

    @Override
    public void fetchDataLeaves(onLeavesListener listener) {
        String emp_id = preferences.getString("userId", "0");
        compositeDisposable.add(retrofit.leaves(emp_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        s -> {
                            ArrayList<LeavesData> listData = new ArrayList<>();
                            listData.addAll(s.getData());
                            listener.fetchDataLeavesSuccess(listData);
                        },
                        throwable -> {
                            Log.d(TAG, "fetchDataLeaves error: " + throwable.getMessage());
                        }
                )
        );
    }

    public void convertStringToArray(String s){

    }
}
