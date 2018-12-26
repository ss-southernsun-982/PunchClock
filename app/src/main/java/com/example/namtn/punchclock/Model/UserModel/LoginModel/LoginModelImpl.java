package com.example.namtn.punchclock.Model.UserModel.LoginModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
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
    private RetrofitAPIs retrofitInfo;
    private CompositeDisposable compositeDisposable;
    private String TAG = "LOGIN_USER";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    String token = "";
    String email = "";

    public LoginModelImpl(Context context) {
        this.context = context;
        this.activity = (Activity) context;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll()
                    .build();
            StrictMode.setThreadPolicy(policy);
        }
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
        String client_secret = "s4ao1Y6WD7kxNUnkn0QeiJmJej7JckWVtPsC7Cqp";
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
            compositeDisposable.add(retrofit.userLogin(email, password, grant_type, client_id,
                    client_secret)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            s -> {
                                listener.setLoginSuccess("Đăng nhập thành công");
                                listener.hideProgressDialog();
                                editor.putString("token", "Bearer " + s.getAccessToken());
                                editor.putString("refreshToken", s.getRefreshToken());
                                editor.putString("email", email);
                                editor.commit();
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

    @Override
    public void getUserInfo() {
        token = preferences.getString("token", "");
        email = preferences.getString("email", "nks.trampnn@gamil.com");
        retrofitInfo = RetrofitUtils.apiUserInfo(token);
        CompositeDisposable compositeDisposable1 = new CompositeDisposable();
        compositeDisposable1.add(retrofitInfo.userInfo(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    editor.putString("userId", String.valueOf(s.getData().getId()));
                    editor.commit();
                    Log.d(TAG, "getUserInfo: " + s.getData().getId());
                }, throwable -> {
                    Log.d(TAG, "getUserInfo: " + token);
                })
        );
    }
}
