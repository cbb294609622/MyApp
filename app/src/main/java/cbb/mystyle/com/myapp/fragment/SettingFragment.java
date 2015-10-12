package cbb.mystyle.com.myapp.fragment;

import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zcw.togglebutton.ToggleButton;
import com.zcw.togglebutton.ToggleButton.OnToggleChanged;
import cbb.mystyle.com.myapp.R;
import cbb.mystyle.com.myapp.base.BaseFragment;
import cbb.mystyle.com.myapp.utils.MyToastUitl;
import cbb.mystyle.com.myapp.utils.SharedPreferencesUitl;


public class SettingFragment extends BaseFragment{

	/**
	 * 数字锁
	 */
	@ViewInject(R.id.setting_toggle_number)
	private ToggleButton setting_toggle_number;
	/**
	 * 手势锁
	 */
	@ViewInject(R.id.setting_toggle_gesture)
	private ToggleButton setting_toggle_gesture;

	@Override
	public View initView() {
		view = View.inflate(mContext, R.layout.setting_fragment, null);
		ViewUtils.inject(this, view);
		return view;
	}
	@Override
	public void initData() {

		//对设置中心的数据进行初始化。
		Boolean isSettingNumber = SharedPreferencesUitl.getBooleanData(mContext,
				"isSettingNumber", false);
		Boolean isSettingGesture = SharedPreferencesUitl.getBooleanData(mContext,
				"isSettingGesture", false);
		if (isSettingNumber) {
			setting_toggle_number.setToggleOn();
		}
		if (isSettingGesture) {
			setting_toggle_gesture.setToggleOn();
		}

		initListener();

	}

	/**
	 * 控件的监听
	 */
	private void initListener() {
		//设置数字锁
		setting_toggle_number.setOnToggleChanged(new OnToggleChanged() {
			@Override
			public void onToggle(boolean on) {
				if (on) {
					// 开
					setting_toggle_number.setToggleOn();
					SharedPreferencesUitl.saveBooleanData(mContext, "isSettingNumber", true);
					MyToastUitl.showToast(mContext, "开启数字锁", MyToastUitl.SHORT_TOAST);
				} else {
					// 关
					setting_toggle_number.setToggleOff();
					SharedPreferencesUitl.saveBooleanData(mContext, "isSettingNumber", false);
					MyToastUitl.showToast(mContext, "关闭数字锁", MyToastUitl.SHORT_TOAST);
				}
			}
		});
		//设置手势锁
		setting_toggle_gesture.setOnToggleChanged(new OnToggleChanged() {
			@Override
			public void onToggle(boolean on) {
				if (on) {
					// 开
					setting_toggle_gesture.setToggleOn();
					SharedPreferencesUitl.saveBooleanData(mContext, "isSettingGesture", true);
					MyToastUitl.showToast(mContext, "开启手势锁", MyToastUitl.SHORT_TOAST);
				} else {
					// 关
					setting_toggle_gesture.setToggleOff();
					SharedPreferencesUitl.saveBooleanData(mContext, "isSettingGesture", false);
					MyToastUitl.showToast(mContext, "关闭手势锁", MyToastUitl.SHORT_TOAST);
				}
			}
		});
	}
}
