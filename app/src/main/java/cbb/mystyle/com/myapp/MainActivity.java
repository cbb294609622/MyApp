package cbb.mystyle.com.myapp;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.CycleInterpolator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import java.util.Random;

import cbb.mystyle.com.myapp.adapter.HomeSetAdapter;
import cbb.mystyle.com.myapp.adapter.LeftSetAdapter;
import cbb.mystyle.com.myapp.data.DefaultDataBean;
import cbb.mystyle.com.myapp.utils.UtilTools;
import cbb.mystyle.com.myapp.view.DragLayout;
import cbb.mystyle.com.myapp.view.MyLinearLayout;

public class MainActivity extends Activity implements View.OnClickListener{
    /**
     * 左侧侧拉ListView
     */
    private ListView mLeftList;
    /**
     * 主界面ListView
     */
    private ListView mMainList;
    /**
     * 头像
     */
    private ImageView iv_header;
    /**
     * 查找Draglayout, 设置监听
     */
    private DragLayout mDragLayout;
    /**
     * 头布局文件
     */
    private MyLinearLayout mLinearLayout;
    /**
     * 头标题
     */
    private TextView tv_title;
    /**
     * 右侧功能键
     */
    private ImageView iv_function;
    /**
     * 利用标记判断图标
     */
    private boolean rightFlag = true;
    private Context mContext;
    private PopupWindow popupWindow;


    private RelativeLayout rl_actionbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_main);
        mContext = this;

        initView();
        initData();
        leftLinster();
    }

    /**
     * 数据的填充
     */
    private void initData() {
        //左面板侧拉，内部数据填充
        mLeftList.setAdapter(new LeftSetAdapter(DefaultDataBean.leftItemData(), mContext));
        //home页数据展示
        mMainList.setAdapter(new HomeSetAdapter(DefaultDataBean.homeItemData(),mContext));
        iv_header.setOnClickListener(this);
        iv_function.setOnClickListener(this);
    }

    /**
     * 滑动左侧的监听
     *  1.左面板的一个随机值
     *  2.show左面板时，隐藏home页head和title
     *  3.返回home页，head是否来回晃动(动画)
     */
    private void leftLinster() {
        // 设置引用
        mLinearLayout.setDraglayout(mDragLayout);
        mDragLayout.setDragStatusListener(new DragLayout.OnDragStatusChangeListener() {
            @Override
            public void onOpen() {
                // 左面板ListView随机设置一个条目
//                Random random = new Random();
//
//                int nextInt = random.nextInt(50);
//                mLeftList.smoothScrollToPosition(nextInt);

            }

            @Override
            public void onDraging(float percent) {
                // 更新图标的透明度
                // 1.0 -> 0.0
                ViewHelper.setAlpha(iv_header, 1 - percent);
                ViewHelper.setAlpha(tv_title, 1 - percent);
            }

            @Override
            public void onClose() {
                // 让图标晃动
                ObjectAnimator mAnim = ObjectAnimator.ofFloat(iv_header,
                        "translationX", 15.0f);
                mAnim.setInterpolator(new CycleInterpolator(4));
                mAnim.setDuration(500);
                mAnim.start();
            }
        });
    }

    /**
     * 初始化布局文件
     */
    private void initView() {
        mLeftList = (ListView) findViewById(R.id.lv_left);
        mMainList = (ListView) findViewById(R.id.lv_main);
        iv_header = (ImageView) findViewById(R.id.iv_header);
        mLinearLayout = (MyLinearLayout) findViewById(R.id.mll);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_function = (ImageView) findViewById(R.id.iv_function);
        rl_actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
        mDragLayout = (DragLayout) findViewById(R.id.dl);
    }

    /**
     * popuwindow
     * @param v 属于那个控件 可以说是父窗体
     */
    protected void showPopupWindow(View v) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(mContext).inflate(
                R.layout.popuwindow_layout, null);
        popupWindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, true);
        // 自定义view添加触摸事件
        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    iv_function.setSelected(false);
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });

        final TextView tv_call = (TextView) contentView
                .findViewById(R.id.tv_call);
        final TextView tv_phone = (TextView) contentView
                .findViewById(R.id.tv_phone);
        final TextView tv_group = (TextView) contentView
                .findViewById(R.id.tv_group);
        final TextView tv_qrcode = (TextView) contentView
                .findViewById(R.id.tv_qrcode);
        final TextView tv_computer = (TextView) contentView
                .findViewById(R.id.tv_computer);

        tv_call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                iv_function.setSelected(false);
                popupWindow.dismiss();
                popupWindow = null;
                Toast.makeText(mContext, tv_call.getText().toString() + "", Toast.LENGTH_SHORT)
                        .show();

            }
        });
        tv_phone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                iv_function.setSelected(false);
                popupWindow.dismiss();
                popupWindow = null;
                Toast.makeText(mContext, tv_phone.getText().toString() + "", Toast.LENGTH_SHORT)
                        .show();

            }
        });
        tv_group.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                iv_function.setSelected(false);
                popupWindow.dismiss();
                popupWindow = null;
                Toast.makeText(mContext, tv_group.getText().toString() + "", Toast.LENGTH_SHORT)
                        .show();

            }
        });
        tv_qrcode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                iv_function.setSelected(false);
                popupWindow.dismiss();
                popupWindow = null;
                Toast.makeText(mContext, tv_qrcode.getText().toString() + "", Toast.LENGTH_SHORT)
                        .show();

            }
        });
        tv_computer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                iv_function.setSelected(false);
                popupWindow.dismiss();
                popupWindow = null;
                Toast.makeText(mContext, tv_computer.getText().toString() + "",
                        Toast.LENGTH_SHORT).show();

            }
        });
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        popupWindow.showAtLocation(v, Gravity.RIGHT | Gravity.TOP, 0, rl_actionbar.getHeight());
        popupWindow.showAsDropDown(v);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_header:
                mDragLayout.open(true);
                break;
            case R.id.iv_function:
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    return;
                } else {
                    showPopupWindow(v);
                    iv_function.setSelected(true);
                }
                break;
        }
    }
}

