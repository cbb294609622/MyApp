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

import cbb.mystyle.com.myapp.utils.ActivityAnimUitl;
import cbb.mystyle.com.myapp.utils.MyToastUitl;


/**
 * 广告界面
 * Created by BoBo on 2015/9/2.
 */
public class SplashActivity extends Activity {
    private Context mContext;
    private TextView splash_time;
    private TextView splash_version;
    private static final int AD_show = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_splash);
        mContext = SplashActivity.this;
        MyToastUitl.showToastFlag(mContext, "广告界面");

        initView();
        initData();

    }

    private void initData() {
        showAD();
        splash_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAD();
            }
        });
//        Timer timer = new Timer();
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                startActivity(new Intent(mContext,MainActivity.class));
//                ActivityAnimUitl.isRightLeft(SplashActivity.this);
//                finish();
//            }
//        };
//        timer.schedule(timerTask,1000*4);
    }

    private void initView() {
        splash_time = (TextView) findViewById(R.id.splash_time);
        splash_version = (TextView) findViewById(R.id.splash_version);

        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            splash_version.setText("版本号: v"+info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
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
            closeAD();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            splash_time.setText("" + (millisUntilFinished / 1000) + "s");
        }
    }

    /**
     * 关闭页面
     */
    private void closeAD() {
        startActivity(new Intent(mContext, MainActivity.class));
        ActivityAnimUitl.isRightLeft(SplashActivity.this);
        finish();
    }

    private void showAD() {
        MyCountDownTimer mc = new MyCountDownTimer(AD_show * 1000, 1000);
        mc.start();
    }

//    @Override
//    public void onBackPressed() {
//        finish();
//        ActivityAnimUitl.isLeftRight(SplashActivity.this);
//        super.onBackPressed();
//    }
}
