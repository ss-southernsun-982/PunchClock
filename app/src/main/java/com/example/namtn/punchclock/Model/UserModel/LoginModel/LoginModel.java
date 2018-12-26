package com.example.namtn.punchclock.Model.UserModel.LoginModel;

public interface LoginModel {

    interface onLoginListener {
        void showProgressDialog();

        void hideProgressDialog();

        void setErrorEmail(String s);

        void setErrorPassword(String s);

        void setLoginFailure(String error);

        void setLoginSuccess(String message);
    }

    void loginUser(String email, String password, onLoginListener onLoginListener);

    void getUserInfo();

    void IntentClass(Class c);
}
