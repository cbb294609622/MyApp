package cbb.mystyle.com.myapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.MotionEventCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class UtilTools {

	public static Toast mToast;

	public static void showToast(Context mContext, String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
		}
		mToast.setText(msg);
		mToast.show();
	}
	
	/**
	 * dip 转换成 px
	 * @param dip
	 * @param context
	 * @return
	 */
	public static float dip2Dimension(float dip, Context context) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, displayMetrics);
	}
	/**
	 * @param dip
	 * @param context
	 * @param complexUnit {@link TypedValue#COMPLEX_UNIT_DIP} {@link TypedValue#COMPLEX_UNIT_SP}}
	 * @return
	 */
	public static float toDimension(float dip, Context context, int complexUnit) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return TypedValue.applyDimension(complexUnit, dip, displayMetrics);
	}

	/** 获取状态栏高度
	 * @param v
	 * @return
	 */
	public static int getStatusBarHeight(View v) {
		if (v == null) {
			return 0;
		}
		Rect frame = new Rect();
		v.getWindowVisibleDisplayFrame(frame);
		return frame.top;
	}

	public static String getActionName(MotionEvent event) {
		String action = "unknow";
		switch (MotionEventCompat.getActionMasked(event)) {
		case MotionEvent.ACTION_DOWN:
			action = "ACTION_DOWN";
			break;
		case MotionEvent.ACTION_MOVE:
			action = "ACTION_MOVE";
			break;
		case MotionEvent.ACTION_UP:
			action = "ACTION_UP";
			break;
		case MotionEvent.ACTION_CANCEL:
			action = "ACTION_CANCEL";
			break;
		case MotionEvent.ACTION_SCROLL:
			action = "ACTION_SCROLL";
			break;
		case MotionEvent.ACTION_OUTSIDE:
			action = "ACTION_SCROLL";
			break;
		default:
			break;
		}
		return action;
	}

	/**
	 * Map a value within a given range to another range.
	 * @param value the value to map
	 * @param fromLow the low end of the range the value is within
	 * @param fromHigh the high end of the range the value is within
	 * @param toLow the low end of the range to map to
	 * @param toHigh the high end of the range to map to
	 * @return the mapped value
	 */
	public static double mapValueFromRangeToRange(
			double value,
			double fromLow,
			double fromHigh,
			double toLow,
			double toHigh) {
		double fromRangeSize = fromHigh - fromLow;
		double toRangeSize = toHigh - toLow;
		double valueScale = (value - fromLow) / fromRangeSize;
		return toLow + (valueScale * toRangeSize);
	}

	/**
	 * set margins of the specific view
	 * @param target
	 * @param l
	 * @param t
	 * @param r
	 * @param b
	 */
	public static void setMargin(View target, int l, int t, int r, int b){
		if (target.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) target.getLayoutParams();
			p.setMargins(l, t, r, b);
			target.requestLayout();
		}
	}

	/**
	 * convert drawable to bitmap
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;

	}


}
