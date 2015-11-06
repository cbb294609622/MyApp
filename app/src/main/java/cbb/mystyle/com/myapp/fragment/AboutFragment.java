package cbb.mystyle.com.myapp.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cbb.mystyle.com.myapp.R;
import cbb.mystyle.com.myapp.base.BaseFragment;
import cbb.mystyle.com.myapp.childfragment.TabAboutFragment;
import cbb.mystyle.com.myapp.childfragment.TabSabotageFragment;
import cbb.mystyle.com.myapp.view.ChildViewPager;

public class AboutFragment extends BaseFragment {

	private ChildViewPager about_fl;
	private TextView about_tvone,about_tvtwo;
	private TabAboutFragment tabAboutFragment;
	private TabSabotageFragment tabSabotageFragment;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mFragment;

	@Override
	public View initView() {
		view = View.inflate(mContext, R.layout.about_fragment, null);
		ViewUtils.inject(mContext, view);
		return view;
	}

	@Override
	public void initData() {
		about_fl = (ChildViewPager) view.findViewById(R.id.about_fl);
		about_tvone = (TextView) view.findViewById(R.id.about_tvone);
		about_tvtwo = (TextView) view.findViewById(R.id.about_tvtwo);

		mFragment = new ArrayList<Fragment>();

		tabAboutFragment = new TabAboutFragment();
		tabSabotageFragment = new TabSabotageFragment();
		mFragment.add(tabAboutFragment);
		mFragment.add(tabSabotageFragment);

		initViewPager();
		initEvent();
		setSelect(0);
	}

	private void initViewPager() {
		mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {


			@Override
			public int getCount() {
				return mFragment.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return mFragment.get(arg0);
			}
		};
		about_fl.setAdapter(mAdapter);

		about_fl.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				int currentItem = about_fl.getCurrentItem();
				setSelect(currentItem);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	private void initEvent() {
		about_tvone.setOnClickListener(new MyOnClickListener(mContext));
		about_tvtwo.setOnClickListener(new MyOnClickListener(mContext));
	}

	class MyOnClickListener implements View.OnClickListener {
		private Context context;
		public MyOnClickListener(Context mContext) {
			this.context = mContext;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.about_tvone:
					setSelect(0);
					break;
				case R.id.about_tvtwo:
					setSelect(1);
					break;
			}

		}
	}

	public void setSelect(int i) {
		resetImgText();
		switch (i){
			case 0:
				about_tvone.setTextColor(android.graphics.Color.parseColor("#18B6EF"));
				break;
			case 1:
				about_tvtwo.setTextColor(android.graphics.Color.parseColor("#18B6EF"));
				break;
		}
		about_fl.setCurrentItem(i);

	}


	/**
	 * 全部黑色未选中
	 */
	private void resetImgText() {
		about_tvone.setTextColor(android.graphics.Color.BLACK);
		about_tvtwo.setTextColor(android.graphics.Color.BLACK);
	}

}
