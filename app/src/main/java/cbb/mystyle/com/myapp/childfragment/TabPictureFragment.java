package cbb.mystyle.com.myapp.childfragment;

import android.view.View;

import cbb.mystyle.com.myapp.R;
import cbb.mystyle.com.myapp.base.BaseFragment;

/**
 * Created by BoBo on 2015/11/5.
 */
public class TabPictureFragment extends BaseFragment {
    @Override
    public View initView() {
        view = View.inflate(mContext, R.layout.tab_picture_fragment,null);
        return view;
    }

    @Override
    public void initData() {

    }
}
