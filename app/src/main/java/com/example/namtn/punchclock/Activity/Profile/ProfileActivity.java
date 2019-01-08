package com.example.namtn.punchclock.Activity.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.namtn.punchclock.Activity.BaseActivity;
import com.example.namtn.punchclock.Activity.Leaves.LeavesActivity;
import com.example.namtn.punchclock.R;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    TextView mTextViewUserNameProfile, mTextViewLeavesProfile;
    ImageView mImageAvatarProfile, mImageCancelProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_profile;
    }

    @Override
    protected void initView() {
        mTextViewUserNameProfile = findViewById(R.id.txt_user_name_profile);
        mTextViewLeavesProfile = findViewById(R.id.txt_leaves_profile);
        mImageCancelProfile = findViewById(R.id.image_cancel_profile);
    }

    @Override
    protected void initEventControl() {
        mTextViewLeavesProfile.setOnClickListener(this);
        mImageCancelProfile.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_leaves_profile:
                IntentActivity(LeavesActivity.class);
                break;
            case R.id.image_cancel_profile:
                this.onBackPressed();
                break;
        }
    }

    public void IntentActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
        overridePendingTransition(R.anim.left_to_right, 0);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }
}
