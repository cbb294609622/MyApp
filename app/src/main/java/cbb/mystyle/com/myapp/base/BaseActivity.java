package cbb.mystyle.com.myapp.base;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

import cbb.mystyle.com.myapp.utils.MyToastUitl;

/**
 * 管理APP中的Activity
 * Created by BoBo on 2015/9/24.
 */
public abstract class BaseActivity extends FragmentActivity{

    public static Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        mContext = getApplicationContext();

        initView();
        initData();
    }

    /**
     * 初始化布局文件
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    //------------------------------------------------------
    /**
     * 判断标记
     */
    private long mPressedTime = 0;
    /**
     * 退出应用
     */
    public void onBackPressed() {
        //获取第一次按键时间
        long mNowTime = System.currentTimeMillis();
        if ((mNowTime-mPressedTime) > 2000) {
            MyToastUitl.showToast(mContext, "再按一次退出程序", MyToastUitl.SHORT_TOAST);
            mPressedTime = mNowTime;
        }else {
            finish();
            System.exit(0);
        }
    }


}
