package cbb.mystyle.com.myapp.childfragment;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cbb.mystyle.com.myapp.R;
import cbb.mystyle.com.myapp.adapter.BaseAdapterHelper;
import cbb.mystyle.com.myapp.adapter.QuickAdapter;
import cbb.mystyle.com.myapp.base.BaseFragment;
import cbb.mystyle.com.myapp.bean.HomeBean;
import cbb.mystyle.com.myapp.data.DefaultDataBean;
import cbb.mystyle.com.myapp.utils.ACache;
import cbb.mystyle.com.myapp.utils.GsonUtil;
import cbb.mystyle.com.myapp.utils.HttpUrl;
import cbb.mystyle.com.myapp.utils.MyToastUitl;
import cbb.mystyle.com.myapp.view.PullToZoomListView;
import cbb.mystyle.com.myapp.view.RoundImageView;
import cbb.mystyle.com.myapp.view.WaterDropListView;

/**
 * Created by BoBo on 2015/11/5.
 */
public class TabHomeFragment extends BaseFragment{


    @Override
    public View initView() {
        view = View.inflate(mContext, R.layout.tab_home_fragment, null);
        return view;
    }

    @Override
    public void initData() {
    }
}
