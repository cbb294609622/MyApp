package cbb.mystyle.com.myapp.homefragment;

import android.view.View;

import cbb.mystyle.com.myapp.R;
import cbb.mystyle.com.myapp.base.BaseFragment;

/**
 * Created by BoBo on 2015/11/5.
 */
public class TabHomeFragment extends BaseFragment {
    @Override
    public View initView() {
        view = View.inflate(mContext, R.layout.tab_home_fragment,null);
        return view;
    }

    @Override
    public void initData() {

    }
}
