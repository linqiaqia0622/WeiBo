package com.lqh.miao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.lqh.miao.net.Constants;


/**
 * Created by wenmingvs on 16/5/9.
 */
public class UnLoginActivity extends FragmentActivity {

    private static final int HOME_FRAGMENT = 0X001;
    private static final int MESSAGE_FRAGMENT = 0X002;
    private static final int DISCOVERY_FRAGMENT = 0X004;
    private static final int PROFILE_FRAGMENT = 0X005;

    private int mCurrentIndex;
    private Context mContext;



    private FragmentManager mFragmentManager;

    private RelativeLayout mHomeTab, mMessageTab, mDiscoeryTab, mProfile;
    private FrameLayout mPostTab;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unlogin_mainactivity_layout);
        mContext = this;


    }



    public void openLoginWebView(View view) {
        String authurl = "https://open.weibo.cn/oauth2/authorize" + "?" + "client_id=" + Constants.APP_KEY
                + "&response_type=token&redirect_uri=" + Constants.REDIRECT_URL
                + "&key_hash=" + Constants.AppSecret + (TextUtils.isEmpty(Constants.PackageName) ? "" : "&packagename=" + Constants.PackageName)
                + "&display=mobile" + "&scope=" + Constants.SCOPE;

        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra("url", authurl);
        startActivity(intent);
        finish();
    }

}
