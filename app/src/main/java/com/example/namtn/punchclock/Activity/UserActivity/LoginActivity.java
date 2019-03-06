package com.example.namtn.punchclock.Activity.UserActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.namtn.punchclock.Activity.BaseActivity;
import com.example.namtn.punchclock.Activity.EnterPassCodeActivity;
import com.example.namtn.punchclock.Activity.MainActivity;
import com.example.namtn.punchclock.Model.UserModel.LoginModel.LoginModelImpl;
import com.example.namtn.punchclock.Presenter.UserPresenter.LoginPresenter.LoginPresenter;
import com.example.namtn.punchclock.Presenter.UserPresenter.LoginPresenter.LoginPresenterImpl;
import com.example.namtn.punchclock.R;
import com.example.namtn.punchclock.View.LoginView;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener {

    private EditText mUserEmail, mUserPassword;
    private Button mButtonLogin;
    private String strUser, strPass;
    private LoginPresenter mPresenterLogin;
    private ProgressBar mProgressBarLogin;
    private String TAG = "LOGIN_MAIN";
    private CallbackManager callback;
    private LoginButton mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "initView: create UI" );
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mUserEmail = findViewById(R.id.edit_user_email_login);
        mUserPassword = findViewById(R.id.edit_user_password_login);
        mButtonLogin = findViewById(R.id.btn_access_login);
        mProgressBarLogin = findViewById(R.id.progress_login);
        mProgressBarLogin.setVisibility(View.GONE);
        mLoginButton = findViewById(R.id.login_button_facebook);
    }

    @Override
    protected void initEventControl() {
        mButtonLogin.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mPresenterLogin = new LoginPresenterImpl(this, new LoginModelImpl(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_access_login:
                loginUser();
                break;
            case R.id.login_button_facebook:
                mLoginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
                mPresenterLogin.loginFacebook(mLoginButton);
                break;
        }
    }

    @Override
    public void showProgressDialog() {
        mProgressBarLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        mProgressBarLogin.setVisibility(View.GONE);
    }

    @Override
    public void setErrorEmail(String s) {
        mUserEmail.setHintTextColor(getResources().getColor(R.color.colorRed));
        mUserEmail.setHint(s);
    }

    @Override
    public void setErrorPass(String s) {
        mUserPassword.setHintTextColor(getResources().getColor(R.color.colorRed));
        mUserPassword.setHint(s);
    }

    @Override
    public void loginFailure(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess(String message) {
        mPresenterLogin.getUserInfo();
    }

    @Override
    public void getInfoFailure(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getInfoSuccess(String s) {
        mPresenterLogin.IntentClass(EnterPassCodeActivity.class);
    }

    @Override
    public void loginFacebookCallBack(CallbackManager callbackManager) {
        callback = callbackManager;
    }

    @Override
    public void loginFacebookSuccess(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                mPresenterLogin.IntentClass(MainActivity.class);
            }
        });
    }

    private void loginUser(){
        strUser = mUserEmail.getText().toString().trim();
        strPass= mUserPassword.getText().toString().trim();
        mPresenterLogin.loginUser(strUser, strPass);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenterLogin.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callback.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
