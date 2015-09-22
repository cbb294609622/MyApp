package cbb.mystyle.com.myapp.fragment;

import android.view.View;
import android.widget.TextView;

import cbb.mystyle.com.myapp.base.BaseFragment;

public class SettingFragment extends BaseFragment {

	@Override
	public View initView() {
		TextView tv = new TextView(mContext);
		tv.setText("设置中心");
		return tv;
	}

	@Override
	public void initData() {
	}

}
