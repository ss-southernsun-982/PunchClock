package com.example.namtn.punchclock.Model.UserModel.LoginModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.namtn.punchclock.Retrofit.RetrofitConfig.RetrofitAPIs;
import com.example.namtn.punchclock.Retrofit.RetrofitConfig.RetrofitUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginModelImpl implements LoginModel {

    private Context context;
    private Activity activity;
    private RetrofitAPIs retrofit;
    private CompositeDisposable compositeDisposable;
    private String TAG = "LOGIN_USER";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public LoginModelImpl(Context context) {
        this.context = context;
        this.activity = (Activity) context;
        preferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        editor = preferences.edit();
        retrofit = RetrofitUtils.apiUserLogin();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loginUser(String email, String password, onLoginListener listener) {
        listener.showProgressDialog();
        int errorCode = 0;
        String grant_type = "password";
        String client_id = "2";
        String client_secret = "rOU4FPWpj36XDlWvNZBn1S39BZaxFpGyAEtBHBLH";
        Log.d(TAG, "loginUser: " + email);
        if (email.isEmpty()) {
            listener.hideProgressDialog();
            listener.setErrorEmail("Vui lòng nhập email");
            errorCode++;
        }
        if (password.isEmpty()) {
            listener.hideProgressDialog();
            listener.setErrorPassword("Vui lòng nhập password");
            errorCode++;
        }
        if (errorCode == 0) {
            compositeDisposable.add(retrofit.uerLogin(email, password, grant_type, client_id,
                    client_secret)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            s -> {
                                listener.setLoginSuccess("Đăng nhập thành công");
                                listener.hideProgressDialog();
                                editor.putString("token", s.getAccessToken());
                                editor.putString("refreshToken", s.getRefreshToken());
                                editor.commit();
                                Log.d(TAG, "loginUser: " + s.getAccessToken());
                            },
                            throwable -> {
                                listener.setLoginFailure("Vui lòng kiểm tra email hoặc password");
                                listener.hideProgressDialog();
                                Log.d(TAG, "loginUser: " + throwable.getMessage());
                            }
                    )
            );
        }
    }

    @Override
    public void IntentClass(Class c) {
        Intent intent = new Intent(context, c);
        activity.startActivity(intent);
        activity.finish();
    }
}
