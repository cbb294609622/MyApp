package cbb.mystyle.com.myapp.view;

import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 侧滑面板
 * @author poplar
 *
 */
public class DragLayout extends FrameLayout {

	private static final String TAG = "TAG";
	private ViewDragHelper mDragHelper;
	private ViewGroup mLeftContent;
	private ViewGroup mMainContent;
	private OnDragStatusChangeListener mListener;
	private Status mStatus = Status.Close;
	
	/**
	 * 状�?枚举
	 */
	public static enum Status {
		Close, Open, Draging;
	}
	public interface OnDragStatusChangeListener{
		void onClose();
		void onOpen();
		void onDraging(float percent);
	}
	
	public Status getStatus() {
		return mStatus;
	}

	public void setStatus(Status mStatus) {
		this.mStatus = mStatus;
	}

	public void setDragStatusListener(OnDragStatusChangeListener mListener){
		this.mListener = mListener;
	}
	
	public DragLayout(Context context) {
		this(context, null);
	}

	public DragLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DragLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		// a.初始�?(通过静�?方法) 
		mDragHelper = ViewDragHelper.create(this , mCallback);
		
	}
	
	ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
		// c. 重写事件
		
		// 1. 根据返回结果决定当前child是否可以拖拽
		// child 当前被拖拽的View
		// pointerId 区分多点触摸的id
		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			Log.d(TAG, "tryCaptureView: " + child);
			return true;
		};
		
		@Override
		public void onViewCaptured(View capturedChild, int activePointerId) {
			Log.d(TAG, "onViewCaptured: " + capturedChild);
			// 当capturedChild被捕获时,调用.
			super.onViewCaptured(capturedChild, activePointerId);
		}

		@Override
		public int getViewHorizontalDragRange(View child) {
			// 返回拖拽的范�? 不对拖拽进行真正的限�? 仅仅决定了动画执行�?�?
			return mRange;
		}
		
		// 2. 根据建议�?修正将要移动到的(横向)位置   (重要)
		// 此时没有发生真正的移�?
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			// child: 当前拖拽的View
			// left 新的位置的建议�?, dx 位置变化�?
			// left = oldLeft + dx;
			Log.d(TAG, "clampViewPositionHorizontal: " 
					+ "oldLeft: " + child.getLeft() + " dx: " + dx + " left: " +left);
			
			if(child == mMainContent){
				left = fixLeft(left);
			}
			return left;
		}

		// 3. 当View位置改变的时�? 处理要做的事�?(更新状�?, 伴随动画, 重绘界面)
		// 此时,View已经发生了位置的改变
		@Override
		public void onViewPositionChanged(View changedView, int left, int top,
				int dx, int dy) {
			// changedView 改变位置的View
			// left 新的左边�?
			// dx 水平方向变化�?
			super.onViewPositionChanged(changedView, left, top, dx, dy);
			Log.d(TAG, "onViewPositionChanged: " + "left: " + left + " dx: " + dx);
			
			int newLeft = left;
			if(changedView == mLeftContent){
				// 把当前变化量传�?给mMainContent
				newLeft = mMainContent.getLeft() + dx;
			}
			
			// 进行修正
			newLeft = fixLeft(newLeft);
			
			if(changedView == mLeftContent) {
				// 当左面板移动之后, 再强制放回去.
				mLeftContent.layout(0, 0, 0 + mWidth, 0 + mHeight);
				mMainContent.layout(newLeft, 0, newLeft + mWidth, 0 + mHeight);
			}
			// 更新状�?,执行动画
			dispatchDragEvent(newLeft);
			
			// 为了兼容低版�? 每次修改值之�? 进行重绘
			invalidate();
		}

		// 4. 当View被释放的时�?, 处理的事�?执行动画)
		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			// View releasedChild 被释放的子View 
			// float xvel 水平方向的�?�? 向右�?
			// float yvel 竖直方向的�?�? 向下�?
			Log.d(TAG, "onViewReleased: " + "xvel: " + xvel + " yvel: " + yvel);
			super.onViewReleased(releasedChild, xvel, yvel);
			
			// 判断执行 关闭/�?��
			// 先�?虑所有开启的情况,剩下的就都是关闭的情�?
			if(xvel == 0 && mMainContent.getLeft() > mRange / 2.0f){
				open();
			}else if (xvel > 0) {
				open();
			}else {
				close();
			}
			
		}

		@Override
		public void onViewDragStateChanged(int state) {
			// TODO Auto-generated method stub
			super.onViewDragStateChanged(state);
		}

	};
	
	/**
	 * 根据范围修正左边�?
	 * @param left
	 * @return
	 */
	private int fixLeft(int left) {
		if(left < 0){
			return 0;
		}else if (left > mRange) {
			return mRange;
		}
		return left;
	}
	
	protected void dispatchDragEvent(int newLeft) {
		float percent = newLeft * 1.0f/ mRange;
		//0.0f -> 1.0f
		Log.d(TAG, "percent: " + percent);
		
		if(mListener != null){
			mListener.onDraging(percent);
		}
		
		// 更新状�?, 执行回调
		Status preStatus = mStatus;
		mStatus = updateStatus(percent);
		if(mStatus != preStatus){
			// 状�?发生变化
			if(mStatus == Status.Close){
				// 当前变为关闭状�?
				if(mListener != null){
					mListener.onClose();
				}
			}else if (mStatus == Status.Open) {
				if(mListener != null){
					mListener.onOpen();
				}
			}
		}
		
//		* 伴随动画:
		animViews(percent);
		
	}

	private Status updateStatus(float percent) {
		if(percent == 0f){
			return Status.Close;
		}else if (percent == 1.0f) {
			return Status.Open;
		}
		return Status.Draging;
	}

	private void animViews(float percent) {
		//		> 1. 左面�? 缩放动画, 平移动画, 透明度动�?
					// 缩放动画 0.0 -> 1.0 >>> 0.5f -> 1.0f  >>> 0.5f * percent + 0.5f
			//		mLeftContent.setScaleX(0.5f + 0.5f * percent);
			//		mLeftContent.setScaleY(0.5f + 0.5f * percent);
					ViewHelper.setScaleX(mLeftContent, evaluate(percent, 0.5f, 1.0f));
					ViewHelper.setScaleY(mLeftContent, 0.5f + 0.5f * percent);
					// 平移动画: -mWidth / 2.0f -> 0.0f
					ViewHelper.setTranslationX(mLeftContent, evaluate(percent, -mWidth / 2.0f, 0));
					// 透明�? 0.5 -> 1.0f
					ViewHelper.setAlpha(mLeftContent, evaluate(percent, 0.5f, 1.0f));
				
		//		> 2. 主面�? 缩放动画
					// 1.0f -> 0.8f
					ViewHelper.setScaleX(mMainContent, evaluate(percent, 1.0f, 0.8f));
					ViewHelper.setScaleY(mMainContent, evaluate(percent, 1.0f, 0.8f));
					
		//		> 3. 背景动画: 亮度变化 (颜色变化)
					getBackground().setColorFilter((Integer)evaluateColor(percent, Color.BLACK, Color.TRANSPARENT), Mode.SRC_OVER);
	}
	
    /**
     * 估�?�?
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     */
    public Float evaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }
    /**
     * 颜色变化过度
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     */
    public Object evaluateColor(float fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (int)((startA + (int)(fraction * (endA - startA))) << 24) |
                (int)((startR + (int)(fraction * (endR - startR))) << 16) |
                (int)((startG + (int)(fraction * (endG - startG))) << 8) |
                (int)((startB + (int)(fraction * (endB - startB))));
    }

	@Override
	public void computeScroll() {
		super.computeScroll();
		
		// 2. 持续平滑动画 (高频率调�?
		if(mDragHelper.continueSettling(true)){
			//  如果返回true, 动画还需要继续执�?
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}
	
	public void close(){
		close(true);
	}
	/**
	 * 关闭
	 */
	public void close(boolean isSmooth) {
		int finalLeft = 0;
		if(isSmooth){
			// 1. 触发�?��平滑动画
			if(mDragHelper.smoothSlideViewTo(mMainContent, finalLeft, 0)){
				// 返回true代表还没有移动到指定位置, �?��刷新界面.
				// 参数传this(child�?��的ViewGroup)
				ViewCompat.postInvalidateOnAnimation(this);
			}
		}else {
			mMainContent.layout(finalLeft, 0, finalLeft + mWidth, 0 + mHeight);
		}
	}
	
	public void open(){
		open(true);
	}
	/**
	 * �?��
	 */
	public void open(boolean isSmooth) {
		int finalLeft = mRange;
		if(isSmooth){
			// 1. 触发�?��平滑动画
			if(mDragHelper.smoothSlideViewTo(mMainContent, finalLeft, 0)){
				// 返回true代表还没有移动到指定位置, �?��刷新界面.
				// 参数传this(child�?��的ViewGroup)
				ViewCompat.postInvalidateOnAnimation(this);
			}
		}else {
			mMainContent.layout(finalLeft, 0, finalLeft + mWidth, 0 + mHeight);
		}
	}

	private int mHeight;
	private int mWidth;
	private int mRange;
	
	// b.传�?触摸事件
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// 传�?给mDragHelper
		return mDragHelper.shouldInterceptTouchEvent(ev);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		try {
			mDragHelper.processTouchEvent(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回true, 持续接受事件
		return true;
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		// Github
		// 写注�?
		// 容错性检�?(至少有俩子View, 子View必须是ViewGroup的子�?
		
		if(getChildCount() < 2){
			throw new IllegalStateException("布局至少有俩孩子. Your ViewGroup must have 2 children at least.");
		}
		if(!(getChildAt(0) instanceof ViewGroup && getChildAt(1) instanceof ViewGroup)){
			throw new IllegalArgumentException("子View必须是ViewGroup的子�? Your children must be an instance of ViewGroup");
		}
		
		mLeftContent = (ViewGroup) getChildAt(0);
		mMainContent = (ViewGroup) getChildAt(1);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// 当尺寸有变化的时候调�?
		
		mHeight = getMeasuredHeight();
		mWidth = getMeasuredWidth();
		
		// 移动的范�?
		mRange = (int) (mWidth * 0.6f);
		
	}
	
}
