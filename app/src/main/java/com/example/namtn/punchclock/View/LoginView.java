package com.example.namtn.punchclock.View;

public interface LoginView {
    void showProgressDialog();

    void hideProgressDialog();

    void setErrorEmail(String s);

    void setErrorPass(String s);

    void loginFailure(String error);

    void loginSuccess(String message);
}
