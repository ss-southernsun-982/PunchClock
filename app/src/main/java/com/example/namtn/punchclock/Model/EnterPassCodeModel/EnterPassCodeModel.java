package com.example.namtn.punchclock.Model.EnterPassCodeModel;

public interface EnterPassCodeModel {

    interface onEnterPassCodeListener{

        void onSuccessPass(String message);

        void onErrorPass(String error);
    }

    void checkPassCode(onEnterPassCodeListener listener, String code);
}
