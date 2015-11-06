package cbb.mystyle.com.myapp.childfragment;

import android.view.View;

import cbb.mystyle.com.myapp.R;
import cbb.mystyle.com.myapp.base.BaseFragment;

/**
 * Created by BoBo on 2015/11/6.
 */
public class TabAboutFragment extends BaseFragment {
    @Override
    public View initView() {
        view = View.inflate(mContext, R.layout.tab_about_fragment, null);
        return view;
    }

    @Override
    public void initData() {

    }
}
