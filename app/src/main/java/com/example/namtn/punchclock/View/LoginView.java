package com.example.namtn.punchclock.View;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

public interface LoginView {
    void showProgressDialog();

    void hideProgressDialog();

    void setErrorEmail(String s);

    void setErrorPass(String s);

    void loginFailure(String error);

    void loginSuccess(String message);

    void getInfoFailure(String s);

    void getInfoSuccess(String s);

    void loginFacebookCallBack(CallbackManager callbackManager);

    void loginFacebookSuccess(String message);
}
