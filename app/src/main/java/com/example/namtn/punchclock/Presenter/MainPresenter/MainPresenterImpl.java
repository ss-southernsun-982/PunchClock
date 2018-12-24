package com.example.namtn.punchclock.Presenter.MainPresenter;

import com.example.namtn.punchclock.Adapter.MenuMainAdapter;
import com.example.namtn.punchclock.Model.MainModel.MainModel;
import com.example.namtn.punchclock.View.MainView;

public class MainPresenterImpl implements MainPresenter, MainModel.onMainListener {

    private MainView mainView;
    private MainModel mainModel;

    public MainPresenterImpl(MainView mainView, MainModel mainModel) {
        this.mainView = mainView;
        this.mainModel = mainModel;
    }

    @Override
    public void menuInitSuccess(MenuMainAdapter adapter) {
        if (mainView != null){
            mainView.initMenu(adapter);
        }
    }

    @Override
    public void dateTimeData(int date, int month, int year, int hour, int minute, int second) {
        if (mainView != null){
            mainView.dateTimeData(date, month, year, hour, minute, second);
        }
    }

    @Override
    public void menuConfig() {
        if (mainModel != null){
            mainModel.menuConfig(this);
        }
    }

    @Override
    public void getDateTime() {
        if (mainModel != null){
            mainModel.getDateTime(this);
        }
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void IntentClass(Class c) {
        if (mainModel != null){
            mainModel.IntentClass(c);
        }
    }
}
