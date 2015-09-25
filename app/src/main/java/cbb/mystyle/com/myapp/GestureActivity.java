package cbb.mystyle.com.myapp;

import android.widget.LinearLayout;
import android.widget.TextView;

import cbb.mystyle.com.myapp.base.BaseActivity;
import cbb.mystyle.com.myapp.utils.SharedPreferencesUitl;
import cbb.mystyle.com.myapp.view.NinePointLineView;

/**
 * Created by BoBo on 2015/9/25.
 */
public class GestureActivity extends BaseActivity{

    private LinearLayout nine_con;//九宫格容器

    private NinePointLineView nv;//九宫格View

    private TextView showInfo;

    private TextView show_tv_pwd;

    @Override
    public void initView() {
        mContext = GestureActivity.this;
        setContentView(R.layout.activity_gesture);
        nv = new NinePointLineView(mContext);
        nine_con = (LinearLayout)findViewById(R.id.nine_con);
        nine_con.addView(nv);
        showInfo = (TextView)findViewById(R.id.show_set_info);
        show_tv_pwd = (TextView) findViewById(R.id.show_tv_pwd);
    }

    @Override
    public void initData() {
        boolean isOk = SharedPreferencesUitl.getBooleanData(mContext, "isOk", false);
        if (isOk){
            String savePwd = SharedPreferencesUitl.getStringData(mContext,"savePwd","");
            show_tv_pwd.setText("手势密码:"+savePwd);
        }
        getSetPwd();
    }

    /**
     * 提示进行密码的设置
     */
    private void getSetPwd() {
        boolean isFirst = SharedPreferencesUitl.getBooleanData(mContext, "isFirst", false);
        System.out.println(isFirst);
        if(isFirst){
            showInfo.setText("请验证手势密码");
        }else{
            boolean isFirstGest = SharedPreferencesUitl.getBooleanData(mContext, "isFirstGest", false);
            if (isFirstGest) {
                //第二次验证手势
                showInfo.setText("请再次确认手势密码");
            }else {
                //第一次开始设置手势密码
                showInfo.setText("请设置手势密码");
            }
        }
    }


}
