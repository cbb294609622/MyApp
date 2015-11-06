package cbb.mystyle.com.myapp.fragment;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import cbb.mystyle.com.myapp.R;
import cbb.mystyle.com.myapp.base.BaseFragment;
import cbb.mystyle.com.myapp.homefragment.TabHomeFragment;
import cbb.mystyle.com.myapp.homefragment.TabPictureFragment;
import cbb.mystyle.com.myapp.homefragment.TabTravlFragment;
import cbb.mystyle.com.myapp.view.ChildViewPager;
import cbb.mystyle.com.myapp.view.PagerSlidingTabStrip;

public class HomeFragment extends BaseFragment {
    /**
     * 标题
     */
    private PagerSlidingTabStrip home_tabs;

    /**
     * 主题
     */
    private ChildViewPager home_vpager;

    private TabTravlFragment tabTravlFragment;
    private TabHomeFragment tabHomeFragment;
    private TabPictureFragment tabPictureFragment;
    /**
     * 获取当前屏幕的密度
     */
    private DisplayMetrics dm;

    private final String[] titles = {"主页", "图片", "旅游"};


    @Override
    public View initView() {
        view = View.inflate(mContext, R.layout.home_fragment, null);
        ViewUtils.inject(mContext, view);
        return view;
    }

    @Override
    public void initData() {
        dm = getResources().getDisplayMetrics();

        home_vpager = (ChildViewPager) view.findViewById(R.id.home_vpager);
        home_tabs = (PagerSlidingTabStrip) view.findViewById(R.id.home_tabs);
        home_vpager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        home_tabs.setViewPager(home_vpager);
        setTabsValue();
    }
    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (tabHomeFragment == null) {
                        tabHomeFragment = new TabHomeFragment();
                    }
                    return tabHomeFragment;
                case 1:
                    if (tabPictureFragment == null) {
                        tabPictureFragment = new TabPictureFragment();
                    }
                    return tabPictureFragment;
                case 2:
                    if (tabTravlFragment == null) {
                        tabTravlFragment = new TabTravlFragment();
                    }
                    return tabTravlFragment;
            }
            return null;
        }
    }
    private void setTabsValue() {
        // 设置Tab是自动填充满屏幕的
        home_tabs.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        home_tabs.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        home_tabs.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab Indicator的高度
        home_tabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, dm));
        // 设置Tab标题文字的大小
        home_tabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, dm));
        // 设置Tab Indicator的颜色
        home_tabs.setIndicatorColor(Color.parseColor("#18B6EF"));
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        home_tabs.setSelectedTextColor(Color.parseColor("#18B6EF"));
        // 取消点击Tab时的背景色
        home_tabs.setTabBackground(0);
    }
}
