package com.example.namtn.punchclock.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bigbangbutton.editcodeview.EditCodeListener;
import com.bigbangbutton.editcodeview.EditCodeView;
import com.example.namtn.punchclock.Model.EnterPassCodeModel.EnterPassCodeModelImpl;
import com.example.namtn.punchclock.Presenter.EnterPassCodePresenter.EnterPassCodePresenter;
import com.example.namtn.punchclock.Presenter.EnterPassCodePresenter.EnterPassCodePresenterImpl;
import com.example.namtn.punchclock.R;
import com.example.namtn.punchclock.View.EnterPasscodeView;

public class EnterPassCodeActivity extends BaseActivity implements EnterPasscodeView {

    EditCodeView mEditCodeView;
    EnterPassCodePresenter mEnterPassCodePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_enter_pass_code;
    }

    @Override
    protected void initView() {
        mEditCodeView = findViewById(R.id.edit_code_enter_passcode);
    }

    @Override
    protected void initEventControl() {
        mEditCodeView.setEditCodeListener(new EditCodeListener() {
            @Override
            public void onCodeReady(String code) {
                mEnterPassCodePresenter.checkPassCode(code);
            }
        });
    }

    @Override
    protected void initData() {
        mEnterPassCodePresenter = new EnterPassCodePresenterImpl(this, new EnterPassCodeModelImpl
                (this));
    }

    @Override
    public void onSuccessPassCode(String message) {
        Intent intent = new Intent(EnterPassCodeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorPassCode(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
