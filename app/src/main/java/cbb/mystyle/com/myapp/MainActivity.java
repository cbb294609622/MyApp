package cbb.mystyle.com.myapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.CycleInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import cbb.mystyle.com.myapp.adapter.LeftSetAdapter;
import cbb.mystyle.com.myapp.data.DefaultDataBean;
import cbb.mystyle.com.myapp.fragment.AboutFragment;
import cbb.mystyle.com.myapp.fragment.ExpectFragment;
import cbb.mystyle.com.myapp.fragment.HomeFragment;
import cbb.mystyle.com.myapp.fragment.LightRouteFragment;
import cbb.mystyle.com.myapp.fragment.PersonalFragment;
import cbb.mystyle.com.myapp.fragment.PictureFragment;
import cbb.mystyle.com.myapp.fragment.SettingFragment;
import cbb.mystyle.com.myapp.utils.MyToastUitl;
import cbb.mystyle.com.myapp.view.DragLayout;
import cbb.mystyle.com.myapp.view.MyLinearLayout;

public class MainActivity extends FragmentActivity implements View.OnClickListener{
    /**
     * 回调的一个标识
     */
    private final static int SCANNIN_GREQUEST_CODE = 1;
    /**
     * 左侧侧拉ListView
     */
    private ListView mLeftList;
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
    private FrameLayout fl_main;

    private RelativeLayout rl_actionbar;
    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;
    /**
     * Fragmnet事物的开启
     */
    private FragmentTransaction transaction;
    /**
     * 默认显示页
     * 主页Fragment
     */
    private HomeFragment homeFragment;
    /**
     * 个人中心
     */
    private PersonalFragment personalFragment;
    /**
     * 图片浏览
     */
    private PictureFragment pictureFragment;
    /**
     * 亮点行程
     */
    private LightRouteFragment lightRouteFragment;
    /**
     * 期待加入
     */
    private ExpectFragment expectFragment;
    /**
     * 关于我们
     */
    private AboutFragment aboutFragment;
    /**
     * 设置中心
     */
    private SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_main);
        mContext = this;
        fragmentManager = getSupportFragmentManager();

        initView();
        initData();
        leftLinster();
        setTabSelection(0);
    }

    /**
     * 数据的填充
     */
    private void initData() {
        //左面板侧拉，内部数据填充
        mLeftList.setAdapter(new LeftSetAdapter(DefaultDataBean.leftItemData(), mContext));

        mLeftList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        setTabSelection(0);
                        tv_title.setText("My APP");
                        break;
                    case 1:
                        setTabSelection(1);
                        tv_title.setText("个人中心");
                        break;
                    case 2:
                        setTabSelection(2);
                        tv_title.setText("图片浏览");
                        break;
                    case 3:
                        setTabSelection(3);
                        tv_title.setText("亮点行程");
                        break;
                    case 4:
                        setTabSelection(4);
                        tv_title.setText("期待加入");
                        break;
                    case 5:
                        setTabSelection(5);
                        tv_title.setText("关于我们");
                        break;
                    case 6:
                        setTabSelection(6);
                        tv_title.setText("设置中心");
                        break;
                }
                mDragLayout.close(true);
            }
        });

        //home页数据展示
        iv_header.setOnClickListener(this);
        iv_function.setOnClickListener(this);
    }

    /**
     * 根据index 进行选择显示的Framgnet
     * @param index 界面指引
     */
    private void setTabSelection(int index) {
        transaction = fragmentManager.beginTransaction();
        //显示之前全部隐藏 以避免重叠显示
        hideFragments(transaction);

        //判断选择的是哪一个Fragment
        switch (index) {
            case 0:
                if (homeFragment == null){
                    //为空 创建 并添加 然后显示
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fl_main, homeFragment);
                }else{
                    // 如果homeFragment不为空，则直接显示
                    transaction.show(homeFragment);
                }

                break;
            case 1:
                if (personalFragment == null){
                    //为空 创建 并添加 然后显示
                    personalFragment = new PersonalFragment();
                    transaction.add(R.id.fl_main, personalFragment);
                }else{
                    // 如果homeFragment不为空，则直接显示
                    transaction.show(personalFragment);
                }

                break;
            case 2:
                if (pictureFragment == null){
                    //为空 创建 并添加 然后显示
                    pictureFragment = new PictureFragment();
                    transaction.add(R.id.fl_main, pictureFragment);
                }else{
                    // 如果homeFragment不为空，则直接显示
                    transaction.show(pictureFragment);
                }

                break;
            case 3:
                if (lightRouteFragment == null){
                    //为空 创建 并添加 然后显示
                    lightRouteFragment = new LightRouteFragment();
                    transaction.add(R.id.fl_main, lightRouteFragment);
                }else{
                    // 如果homeFragment不为空，则直接显示
                    transaction.show(lightRouteFragment);
                }
                break;
            case 4:
                if (expectFragment == null){
                    //为空 创建 并添加 然后显示
                    expectFragment = new ExpectFragment();
                    transaction.add(R.id.fl_main, expectFragment);
                }else{
                    // 如果homeFragment不为空，则直接显示
                    transaction.show(expectFragment);
                }
                break;
            case 5:
                if (aboutFragment == null){
                    //为空 创建 并添加 然后显示
                    aboutFragment = new AboutFragment();
                    transaction.add(R.id.fl_main, aboutFragment);
                }else{
                    // 如果homeFragment不为空，则直接显示
                    transaction.show(aboutFragment);
                }
                break;
            case 6:
                if (settingFragment == null){
                    //为空 创建 并添加 然后显示
//                    settingFragment = new SettingFragment();
                    transaction.add(R.id.fl_main, settingFragment);
                }else{
                    // 如果homeFragment不为空，则直接显示
                    transaction.show(settingFragment);
                }
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏全部页面
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction){
        if (homeFragment != null){
            transaction.hide(homeFragment);
        }
        if (personalFragment != null){
            transaction.hide(personalFragment);
        }
        if (pictureFragment != null){
            transaction.hide(pictureFragment);
        }
        if (lightRouteFragment != null){
            transaction.hide(lightRouteFragment);
        }
        if (expectFragment != null){
            transaction.hide(expectFragment);
        }
        if (aboutFragment != null){
            transaction.hide(aboutFragment);
        }
        if (settingFragment != null){
            transaction.hide(settingFragment);
        }
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
        iv_header = (ImageView) findViewById(R.id.iv_header);
        mLinearLayout = (MyLinearLayout) findViewById(R.id.mll);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_function = (ImageView) findViewById(R.id.iv_function);
        rl_actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
        mDragLayout = (DragLayout) findViewById(R.id.dl);
        fl_main = (FrameLayout) findViewById(R.id.fl_main);
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
        final TextView tv_qrcode = (TextView) contentView
                .findViewById(R.id.tv_qrcode);



        tv_qrcode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                iv_function.setSelected(false);
                popupWindow.dismiss();
                popupWindow = null;
                initQrCode();

            }
        });

        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        popupWindow.showAtLocation(v, Gravity.RIGHT | Gravity.TOP, 0, rl_actionbar.getHeight());
        popupWindow.showAsDropDown(v);
    }
    protected void initQrCode() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, MipcaCaptureActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    //显示扫描到的内容
                    MyToastUitl.showToast(mContext,bundle.getString("result"), MyToastUitl.SHORT_TOAST);
                }
                break;
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeFragment();
    }

    /**
     * 关闭所以Fragment相关的代码  以便于管理内存泄漏
     */
    private void closeFragment() {
        if (transaction != null){
            transaction = null;
        }
        if (fragmentManager != null){
            fragmentManager = null;
        }
        if (homeFragment != null){
            homeFragment = null;
        }
        if (personalFragment != null){
            personalFragment = null;
        }
        if (pictureFragment != null){
            pictureFragment = null;
        }
        if (lightRouteFragment != null){
            lightRouteFragment = null;
        }
        if (expectFragment != null){
            expectFragment = null;
        }
        if (aboutFragment != null){
            aboutFragment = null;
        }
        if (settingFragment != null){
            settingFragment = null;
        }
    }
}

