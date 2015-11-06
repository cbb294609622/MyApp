package cbb.mystyle.com.myapp.childfragment;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cbb.mystyle.com.myapp.R;
import cbb.mystyle.com.myapp.base.BaseFragment;
import cbb.mystyle.com.myapp.data.DefaultDataBean;
import cbb.mystyle.com.myapp.view.PullToZoomListView;
import cbb.mystyle.com.myapp.view.WaterDropListView;

/**
 * Created by BoBo on 2015/11/5.
 */
public class TabHomeFragment extends BaseFragment implements WaterDropListView.IWaterDropListViewListener{

    private WaterDropListView waterDropListView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    waterDropListView.stopRefresh();
                    break;
                case 2:
                    waterDropListView.stopLoadMore();
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
        waterDropListView.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_expandable_list_item_1, DefaultDataBean.homeItemData()));
        waterDropListView.setWaterDropListViewListener(this);
        waterDropListView.setPullLoadEnable(true);

    }

    private List<String> getData(){

        List<String> data = new ArrayList<String>();
        data.add("To see a world in a grain of sand,");
        data.add("And a heaven in a wild flower,");
        data.add("Hold infinity in the palm of your hand,");
        data.add("And eternity in an hour.");
        data.add("And eternity in an hour.");
        data.add("And eternity in an hour.");
        data.add("And eternity in an hour.");
        data.add("And eternity in an hour.");
        data.add("And eternity in an hour.");
        data.add("And eternity in an hour.");
        data.add("And eternity in an hour.");
        data.add("And eternity in an hour.");
        data.add("And eternity in an hour.");
        data.add("And eternity in an hour.");
        return data;
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
