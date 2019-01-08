package com.example.namtn.punchclock.Presenter.UserPresenter.LoginPresenter;

import com.example.namtn.punchclock.Model.UserModel.LoginModel.LoginModel;
import com.example.namtn.punchclock.View.LoginView;

public class LoginPresenterImpl implements LoginPresenter, LoginModel.onLoginListener {

    private LoginView mLoginView;
    private LoginModel mLoginMode;

    public LoginPresenterImpl(LoginView mLoginView, LoginModel mLoginMode) {
        this.mLoginView = mLoginView;
        this.mLoginMode = mLoginMode;
    }

    @Override
    public void showProgressDialog() {
        if (mLoginView != null) {
            mLoginView.showProgressDialog();
        }
    }

    @Override
    public void hideProgressDialog() {
        if (mLoginView != null) {
            mLoginView.hideProgressDialog();
        }
    }

    @Override
    public void setErrorEmail(String s) {
        if (mLoginView != null) {
            mLoginView.setErrorEmail(s);
        }
    }

    @Override
    public void setErrorPassword(String s) {
        if (mLoginView != null) {
            mLoginView.setErrorPass(s);
        }
    }

    @Override
    public void setLoginFailure(String error) {
        if (mLoginView != null) {
            mLoginView.loginFailure(error);
        }
    }

    @Override
    public void setLoginSuccess(String message) {
        if (mLoginView != null) {
            mLoginView.loginSuccess(message);
        }
    }

    @Override
    public void getInfoFailure(String s) {
        if (mLoginView != null) {
            mLoginView.getInfoFailure(s);
        }
    }

    @Override
    public void getInfoSuccess(String s) {
        if (mLoginView != null) {
            mLoginView.getInfoSuccess(s);
        }
    }

    @Override
    public void loginUser(String email, String password) {
        if (mLoginMode != null) {
            mLoginMode.loginUser(email, password, this);
        }
    }

    @Override
    public void getUserInfo() {
        if (mLoginMode != null) {
            mLoginMode.getUserInfo(this);
        }
    }

    @Override
    public void IntentClass(Class c) {
        if (mLoginMode != null) {
            mLoginMode.IntentClass(c);
        }
    }

    @Override
    public void onDestroy() {
        mLoginView = null;
    }
}
