package cbb.mystyle.com.myapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cbb.mystyle.com.myapp.R;
import cbb.mystyle.com.myapp.base.BaseListViewAdapter;

/**
 * Created by BoBo on 2015/9/18.
 */

public class HomeSetAdapter extends BaseListViewAdapter<String> {
    /**
     * 因为自己是父类，不知道子类传入的是什么 所以就用泛型规定
     *
     * @param list
     */
    public Context mContext;
    public HomeSetAdapter(List<String> list, Context context) {
        super(list);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.home_item, null);
            holder = new ViewHolder();

            holder.tv_slide_name = (TextView) convertView.findViewById(R.id.tv_slide_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_slide_name.setText(list.get(position));
        return convertView;
    }

    public class ViewHolder {
        private TextView tv_slide_name;
    }
}
