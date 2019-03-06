package com.example.namtn.punchclock.Model.UserModel.LoginModel;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

public interface LoginModel {

    interface onLoginListener {
        void showProgressDialog();

        void hideProgressDialog();

        void setErrorEmail(String s);

        void setErrorPassword(String s);

        void setLoginFailure(String error);

        void setLoginSuccess(String message);

        void getInfoFailure(String s);

        void getInfoSuccess(String s);

        void loginFacebookCallBackResult(CallbackManager manager);

        void loginFacebookSuccess(String message);
    }

    void loginUser(String email, String password, onLoginListener onLoginListener);

    void getUserInfo(onLoginListener listener);

    void IntentClass(Class c);

    void loginWithFaceBook(onLoginListener listener, LoginButton mLoginButton);
}
