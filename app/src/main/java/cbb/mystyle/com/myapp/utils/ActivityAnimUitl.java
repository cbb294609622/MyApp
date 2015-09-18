package cbb.mystyle.com.myapp.utils;

import android.app.Activity;

import cbb.mystyle.com.myapp.R;


/**
 * Created by BoBo on 2015/9/7.
 */
public class ActivityAnimUitl {
    /**
     * 由右向左 动画
     */
    public static void isRightLeft(Activity mActivity){
        mActivity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }
    /**
     * 由左向右 动画
     */
    public static void isLeftRight(Activity mActivity){
        mActivity.overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }
}
