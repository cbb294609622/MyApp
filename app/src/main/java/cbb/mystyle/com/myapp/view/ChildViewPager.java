package cbb.mystyle.com.myapp.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 好用，哈哈~~~~
 */
public class ChildViewPager extends ViewPager {

    /** 最后一个位置 */
    private float   lastX;

    /** 从左到右滑动 */
    private boolean slidingLeft;

    /** 从右到左滑动 */
    private boolean slidingRight;

    public ChildViewPager(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildViewPager(final Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                // 不允许上层View拦截触摸事件。
                this.getParent().requestDisallowInterceptTouchEvent(true);

                // 保存当前位置
                this.lastX = ev.getX();

                break;

            case MotionEvent.ACTION_UP:
                // 让上层View拦截触摸事件。
                this.getParent().requestDisallowInterceptTouchEvent(false);

                //保存当前位置
                this.lastX = ev.getX();

                // 重置操作
                this.slidingLeft = false;
                this.slidingRight = false;

                break;

            case MotionEvent.ACTION_MOVE:
                /*
                 * 如果这是第一项，滚动从左至右
                 */
                if (this.getCurrentItem() == 0) {
                    // 从左到右（->）？
                    if (this.lastX <= ev.getX() && !this.slidingRight) {
                        // 让上层View拦截
                        this.getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        this.slidingRight = true;

                        this.lastX = ev.getX();
                        this.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                } else
                    if (this.getCurrentItem() == this.getAdapter().getCount() - 1) {
                        //从右到左（<-）？
                        if (this.lastX >= ev.getX() && !this.slidingLeft) {
                            // 让上层View拦截
                            this.getParent().requestDisallowInterceptTouchEvent(false);
                        } else {
                            this.slidingLeft = true;

                            this.lastX = ev.getX();
                            this.getParent().requestDisallowInterceptTouchEvent(true);
                        }
                    }
                break;
        }
        super.onTouchEvent(ev);
        return true;
    }

}