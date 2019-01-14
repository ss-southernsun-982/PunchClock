package com.example.namtn.punchclock.Presenter.EnterPassCodePresenter;

import com.example.namtn.punchclock.Model.EnterPassCodeModel.EnterPassCodeModel;
import com.example.namtn.punchclock.View.EnterPasscodeView;

public class EnterPassCodePresenterImpl implements EnterPassCodePresenter, EnterPassCodeModel
        .onEnterPassCodeListener {

    EnterPasscodeView mEnterPasscodeView;
    EnterPassCodeModel mEnterPassCodeModel;

    public EnterPassCodePresenterImpl(EnterPasscodeView mEnterPasscodeView, EnterPassCodeModel
            mEnterPassCodeModel) {
        this.mEnterPasscodeView = mEnterPasscodeView;
        this.mEnterPassCodeModel = mEnterPassCodeModel;
    }

    @Override
    public void onSuccessPass(String message) {
        if (mEnterPasscodeView != null){
            mEnterPasscodeView.onSuccessPassCode(message);
        }
    }

    @Override
    public void onErrorPass(String error) {
        if (mEnterPasscodeView != null){
            mEnterPasscodeView.onErrorPassCode(error);
        }
    }

    @Override
    public void checkPassCode(String code) {
        if (mEnterPassCodeModel != null){
            mEnterPassCodeModel.checkPassCode(this, code);
        }
    }

    @Override
    public void onDestroy() {
        mEnterPasscodeView = null;
    }
}
