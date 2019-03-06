package com.example.namtn.punchclock.Model.UserModel.LoginModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.telecom.Call;
import android.util.Log;

import com.example.namtn.punchclock.Retrofit.RetrofitConfig.RetrofitAPIs;
import com.example.namtn.punchclock.Retrofit.RetrofitConfig.RetrofitUtils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

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
    private CallbackManager callbackManager;

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
                                listener.hideProgressDialog();
                                editor.putString("token", "Bearer " + s.getAccessToken());
                                editor.putString("refreshToken", s.getRefreshToken());
                                editor.putString("email", email);
                                editor.commit();
                                listener.setLoginSuccess("");
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

    @Override
    public void loginWithFaceBook(onLoginListener listener, LoginButton mLoginButton) {
        callbackManager = CallbackManager.Factory.create();
        // Callback registration
        mLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d(TAG, "onSuccess: " + loginResult.getAccessToken());
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v(TAG, response.toString());
                                // Application code
                                try {
                                    String email = object.getString("email");
                                    String name = object.getString("name");
                                    listener.loginFacebookSuccess("Welcome " + name);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.d(TAG, "onCompleted: " + e.getMessage());
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d(TAG, "onError: " + exception);
            }
        });
        listener.loginFacebookCallBackResult(callbackManager);
    }

    @Override
    public void getUserInfo(onLoginListener listener) {
        token = preferences.getString("token", "0");
        email = preferences.getString("email", "0");
        retrofitInfo = RetrofitUtils.apiUserInfo(token);
        CompositeDisposable compositeDisposable1 = new CompositeDisposable();
        compositeDisposable1.add(retrofitInfo.userInfo(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    editor.putString("userId", String.valueOf(s.getData().getId()));
                    editor.putString("userRole", String.valueOf(s.getData().getId()));
                    editor.commit();
                    listener.getInfoSuccess("Đăng nhập thành công");
                    Log.d(TAG, "getUserInfo: " + s.getData().getId());
                    Log.d(TAG, "getUserInfo: " + s.getData().getRole());
                }, throwable -> {
                    Log.d(TAG, "getUserInfo: " + throwable.getMessage());
                    listener.getInfoFailure("Serve error");
                })
        );
    }
}
