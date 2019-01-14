package com.example.namtn.punchclock.Model.EnterPassCodeModel;

import android.app.Activity;
import android.content.Context;

public class EnterPassCodeModelImpl implements EnterPassCodeModel{

    private Context context;
    private Activity activity;

    public EnterPassCodeModelImpl(Context context) {
        this.context = context;
        this.activity = (Activity) context;
    }

    @Override
    public void checkPassCode(onEnterPassCodeListener listener, String code) {
        if (code.equals("3333")){
            listener.onSuccessPass("Đăng nhập thành công");
        } else {
            listener.onErrorPass("Vui lòng kiểm tra tin nhắn");
        }
    }
}
