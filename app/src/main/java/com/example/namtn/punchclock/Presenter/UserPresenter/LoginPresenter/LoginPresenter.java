package com.example.namtn.punchclock.Presenter.UserPresenter.LoginPresenter;

import com.facebook.login.widget.LoginButton;

public interface LoginPresenter {
    void loginUser(String email, String password);

    void loginFacebook(LoginButton mLoginButton);

    void getUserInfo();

    void IntentClass(Class s);

    void onDestroy();
}
