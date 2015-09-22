package cbb.mystyle.com.myapp.fragment;

import android.view.View;
import android.widget.TextView;

import cbb.mystyle.com.myapp.base.BaseFragment;

public class AboutFragment extends BaseFragment {

	@Override
	public View initView() {
		TextView tv = new TextView(context);
		tv.setText("关于我们");
		return tv;
	}

	@Override
	public void initData() {
	}

}
