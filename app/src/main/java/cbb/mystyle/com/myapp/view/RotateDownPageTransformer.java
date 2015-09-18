package cbb.mystyle.com.myapp.view;

import com.nineoldandroids.view.ViewHelper;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 *
 * 自定义 菱形切换动画
 */
public class RotateDownPageTransformer implements ViewPager.PageTransformer {
	private static final float MIN_SCALE = 0.85f;
	private static final float MIN_ALPHA = 0.5f;
	private static final float ROT_MAX = 20.0f;
	private float mRot;
	private float mTrans;

	@SuppressLint("NewApi")
	public void transformPage(View view, float position) {

		Log.e("TAG", view + " , " + position + "");

		if (position < -1) {
			ViewHelper.setRotation(view, 0);
		} else if (position <= 1) {
			if (position < 0) {

				mRot = (ROT_MAX * position);
				ViewHelper.setPivotX(view, view.getMeasuredWidth() * 0.5f);
				ViewHelper.setPivotY(view, view.getMeasuredHeight());
				ViewHelper.setRotation(view, mRot);
			} else {

				mRot = (ROT_MAX * position);
				ViewHelper.setPivotX(view, view.getMeasuredWidth() * 0.5f);
				ViewHelper.setPivotY(view, view.getMeasuredHeight());
				ViewHelper.setRotation(view, mRot);
			}

		} else {
			ViewHelper.setRotation(view, 0);
		}
	}
}