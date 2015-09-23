package cbb.mystyle.com.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import cbb.mystyle.com.myapp.data.DefaultDataBean;
import cbb.mystyle.com.myapp.pwd.GridPasswordView;
import cbb.mystyle.com.myapp.utils.ActivityAnimUitl;
import cbb.mystyle.com.myapp.utils.MD5Utils;
import cbb.mystyle.com.myapp.utils.MyToastUitl;

/**
 * Created by BoBo on 2015/9/23.
 */
public class PassWordActivity extends Activity  implements View.OnClickListener {
    private Context mContext;
    private GridPasswordView gridpassword;
    private String passwordStr = "";
    private Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //弹出键盘，并且键盘不会顶布局文件
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
        );

        setContentView(R.layout.activity_password);
        mContext = PassWordActivity.this;

        gridpassword = (GridPasswordView) findViewById(R.id.password);
        gridpassword.setOnPasswordChangedListener(passlistener);
        confirmBtn = (Button) findViewById(R.id.confirm_btn);
        // 判断密码长度是否满足6位， 如果不满足 确定按钮不能点击，文字颜色变灰色
        if (passwordStr.length() != 6) {
            confirmBtn.setEnabled(false);
            confirmBtn.setTextColor(Color.GRAY);
            confirmBtn.setBackgroundResource(R.drawable.shape_pwd_normal);
        }
        // 确定按钮点击事件
        confirmBtn.setOnClickListener(this);
    }

    /**
     * 监听输入的密码
     */
    GridPasswordView.OnPasswordChangedListener passlistener = new GridPasswordView.OnPasswordChangedListener() {

        // 密码
        @Override
        public void onMaxLength(String psw) {
            // 获取密码
            passwordStr = psw;
        }

        // 密码长度
        @Override
        public void onChanged(String psw) {
            if (psw.length() != 6) {
                confirmBtn.setEnabled(false);
                confirmBtn.setTextColor(Color.GRAY);
                confirmBtn.setBackgroundResource(R.drawable.shape_pwd_normal);
            } else {
                confirmBtn.setEnabled(true);
                confirmBtn.setBackgroundResource(R.drawable.shape_pwd_pressed);
                confirmBtn.setTextColor(Color.WHITE);
            }
        }

    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_btn:
                String pwd = MD5Utils.digesPassword(passwordStr + DefaultDataBean.BASE_PUBLIC_KEY);
                if (pwd.equals(DefaultDataBean.compareKey())) {
                    startActivity(new Intent(mContext, MainActivity.class));
                    ActivityAnimUitl.isRightLeft(PassWordActivity.this);
                    finish();
                } else {
                    MyToastUitl.showToast(mContext,"验证失败,请重试",MyToastUitl.SHORT_TOAST);
                }
                break;
        }
    }


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