package cbb.mystyle.com.myapp.fragment;

import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;

import cbb.mystyle.com.myapp.R;
import cbb.mystyle.com.myapp.base.BaseFragment;

public class LightRouteFragment extends BaseFragment {

	@Override
	public View initView() {
		view = View.inflate(mContext, R.layout.lightroute_fragment, null);
		ViewUtils.inject(mContext, view);
		return view;
	}

	@Override
	public void initData() {
	}

}
