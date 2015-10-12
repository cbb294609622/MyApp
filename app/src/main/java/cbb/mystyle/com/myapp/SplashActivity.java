package cbb.mystyle.com.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import cbb.mystyle.com.myapp.base.BaseActivity;
import cbb.mystyle.com.myapp.utils.ActivityAnimUitl;
import cbb.mystyle.com.myapp.utils.MyToastUitl;
import cbb.mystyle.com.myapp.utils.SharedPreferencesUitl;


/**
 * 广告界面
 * Created by BoBo on 2015/9/2.
 */
public class SplashActivity extends BaseActivity {
    private TextView splash_time;
    private TextView splash_version;
    private static final int AD_show = 6;
    private boolean CLOSE_TIMER = true;

    public void initView() {
        mContext = SplashActivity.this;
        setContentView(R.layout.activity_splash);
        MyToastUitl.showToastFlag(mContext, "广告界面");

        splash_time = (TextView) findViewById(R.id.splash_time);
        splash_version = (TextView) findViewById(R.id.splash_version);

        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            splash_version.setText("版本号: v" + info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initData() {
        showAD();
        splash_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAD();
                CLOSE_TIMER = false;
            }
        });
    }


    /**
     * 继承 CountDownTimer 防范
     * 重写 父类的方法 onTick() 、 onFinish()
     */
    class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以毫秒为单位 倒计时的总数
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            /**
             * 为true 说明 用户没有跳过广告页面
             * 为false 说明 用户跳过了广告界面
             */
            if (CLOSE_TIMER) {
                closeAD();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            splash_time.setText("" + (millisUntilFinished / 1000) + "s");
        }
    }

    /**
     * 关闭页面  有四种可能
     * 1.安全锁全部开启，需要一一跳转
     * 2.数字锁关闭，
     * 3.手势锁关闭
     * 4.全部关闭
     */
    private void closeAD() {
        Boolean isSettingNumber = SharedPreferencesUitl.getBooleanData(mContext,
                "isSettingNumber", false);
        Boolean isSettingGesture = SharedPreferencesUitl.getBooleanData(mContext,
                "isSettingGesture", false);

        if (isSettingNumber && isSettingGesture){
            //第一个流程全为True开启状态
            startActivity(new Intent(mContext, PassWordActivity.class));
            ActivityAnimUitl.isRightLeft(SplashActivity.this);
            finish();
        }else if (!isSettingNumber && !isSettingGesture){
            //第二个流程全为False关闭状态
            startActivity(new Intent(mContext, MainActivity.class));
            ActivityAnimUitl.isRightLeft(SplashActivity.this);
            finish();
        }else if (isSettingNumber && !isSettingGesture){
            //第三个流程，数字锁开启，手势锁关闭，跳过手势锁
            startActivity(new Intent(mContext, PassWordActivity.class));
            ActivityAnimUitl.isRightLeft(SplashActivity.this);
            finish();
        }else if (!isSettingNumber && isSettingGesture){
            //第四个流程，数字锁关闭，手势锁开启，跳过数字锁
            startActivity(new Intent(mContext, GestureActivity.class));
            ActivityAnimUitl.isRightLeft(SplashActivity.this);
            finish();
        }



    }

    private void showAD() {
        MyCountDownTimer mc = new MyCountDownTimer(AD_show * 1000, 1000);
        mc.start();
    }
}
