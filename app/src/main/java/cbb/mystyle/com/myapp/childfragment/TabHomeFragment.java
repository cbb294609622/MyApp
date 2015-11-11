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
public class TabHomeFragment extends BaseFragment implements WaterDropListView.IWaterDropListViewListener {

    private WaterDropListView waterDropListView;

    private RequestQueue mQueue;
    private ACache mCache;
    private List<HomeBean.Data.Goods> listInfo;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    waterDropListView.stopRefresh();
                    MyToastUitl.showToast(mContext,"最新数据，请稍后更新",MyToastUitl.SHORT_TOAST);
                    break;
                case 2:
                    waterDropListView.stopLoadMore();
                    MyToastUitl.showToast(mContext, "没有更多数据了", MyToastUitl.SHORT_TOAST);
                    break;
            }

        }
    };

    @Override
    public View initView() {
        view = View.inflate(mContext, R.layout.tab_home_fragment, null);
        return view;
    }

    @Override
    public void initData() {
        waterDropListView = (WaterDropListView) view.findViewById(R.id.waterdrop_listview);
        //缓存机制
        mCache = ACache.get(mContext);
        //访问网络机制
        mQueue = Volley.newRequestQueue(mContext);

        //判断是否有缓存的方法
        caCheData();


    }

    private void caCheData() {
        String response = mCache.getAsString(HttpUrl.BASE_TABHOME);

        if (TextUtils.isEmpty(response)) {
            getData();
        }else {
//			getData();
            mProcessData(response);
        }
    }
    private void getData() {
        StringRequest stringRequest = new StringRequest(HttpUrl.BASE_TABHOME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        /**
                         * 1.缓存名称
                         * 2.缓存数据
                         * 3.缓存时间
                         */
                        mCache.put(HttpUrl.BASE_TABHOME, response, 1000);
                        mProcessData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyToastUitl.showToast(mContext,error.toString(),MyToastUitl.SHORT_TOAST);
            }
        });
        mQueue.add(stringRequest);
    }
    protected void mProcessData(String response) {
        HomeBean homeBean = GsonUtil.json2Bean(response, HomeBean.class);
        if("0".equals(homeBean.status)) {

            listInfo = new ArrayList<HomeBean.Data.Goods>();

            for (int i = 0; i <homeBean.data.goods.size(); i++) {
                listInfo.add(homeBean.data.goods.get(i));
            }
            setAdapters();
        }
    }

    private void setAdapters() {
        waterDropListView.setAdapter(new QuickAdapter<HomeBean.Data.Goods>(mContext,
                R.layout.tab_home_listview_item, listInfo) {
            @Override
            protected void convert(BaseAdapterHelper helper, HomeBean.Data.Goods item) {
                helper.setText(R.id.tab_tv_title, item.title);
                helper.setText(R.id.tab_tv_read, item.read+"：阅读量");
                helper.setImageUrl(R.id.tab_img_iv, item.photo);
            }
        });
        waterDropListView.setWaterDropListViewListener(this);
        waterDropListView.setPullLoadEnable(true);
    }
    @Override
    public void onRefresh() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    handler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onLoadMore() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    handler.sendEmptyMessage(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
