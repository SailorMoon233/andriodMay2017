package com.retaileragrsmb.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.retaileragrsmb.R;
import com.retaileragrsmb.common.Constant;
import com.retaileragrsmb.common.Utils;

/**
 * Created by hitesh on 5/6/2017.
 */

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(Utils.getPrefrence(SplashActivity.this, Constant.KEY_IS_LOGIN, false)) {
                    intent = new Intent(SplashActivity.this, DashboardActivity.class);
                }else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
