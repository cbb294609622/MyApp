package cbb.mystyle.com.myapp.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import cbb.mystyle.com.myapp.GestureActivity;
import cbb.mystyle.com.myapp.MainActivity;
import cbb.mystyle.com.myapp.R;
import cbb.mystyle.com.myapp.utils.MyToastUitl;
import cbb.mystyle.com.myapp.utils.SharedPreferencesUitl;

/**
 * 作用：手势密码的九宫格
 * 作者：ufnind
 * 时间：2013年10月29日 09:34:52
 * */
public class NinePointLineView extends View {

    Paint linePaint = new Paint();

    Paint whiteLinePaint = new Paint();

    Paint textPaint = new Paint();
    /**
     * 原图片
     */
    Bitmap defaultBitmap = BitmapFactory.decodeResource(getResources(),
            R.mipmap.lock);

    int defaultBitmapRadius = defaultBitmap.getWidth() / 2;

    Bitmap selectedBitmap = BitmapFactory.decodeResource(getResources(),
            R.mipmap.indicator_lock_area);

    int selectedBitmapDiameter = selectedBitmap.getWidth();

    int selectedBitmapRadius = selectedBitmapDiameter / 2;

    PointInfo[] points = new PointInfo[9];

    PointInfo startPoint = null;

    int width, height;

    int moveX, moveY;

    boolean isUp = false;

    Context mContext;

    StringBuffer lockString = new StringBuffer();

    public NinePointLineView(Context context) {

        super(context);

        mContext = context;
        initPaint();
    }

    public NinePointLineView(Context context, AttributeSet attrs) {

        super(context, attrs);
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        width = getWidth();

        height = getHeight();

        if (width != 0 && height != 0) {

            initPoints(points);

        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {

        super.onLayout(changed, left, top, right, bottom);

    }

    private int startX = 0, startY = 0;

    @Override
    protected void onDraw(Canvas canvas) {


        if (moveX != 0 && moveY != 0 && startX != 0 && startY != 0) {
        }

        drawNinePoint(canvas);

        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean flag = true;

        if (isUp) {

            finishDraw();

            flag = false;

        } else {
            handlingEvent(event);

            flag = true;

        }
        return flag;
    }

    private void handlingEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE:

                moveX = (int) event.getX();

                moveY = (int) event.getY();

                for (PointInfo temp : points) {

                    if (temp.isInMyPlace(moveX, moveY)
                            && temp.isSelected() == false) {

                        temp.setSelected(true);

                        startX = temp.getCenterX();

                        startY = temp.getCenterY();

                        int len = lockString.length();

                        if (len != 0) {

                            int preId = lockString.charAt(len - 1) - 48;

                            points[preId].setNextId(temp.getId());

                        }

                        lockString.append(temp.getId());

                        break;
                    }
                }

                invalidate(0, height - width, width, height);

                break;

            case MotionEvent.ACTION_DOWN:

                int downX = (int) event.getX();

                int downY = (int) event.getY();

                for (PointInfo temp : points) {

                    if (temp.isInMyPlace(downX, downY)) {

                        temp.setSelected(true);

                        startPoint = temp;

                        startX = temp.getCenterX();

                        startY = temp.getCenterY();

                        lockString.append(temp.getId());

                        break;
                    }
                }

                invalidate(0, height - width, width, height);

                break;

            case MotionEvent.ACTION_UP:
                startX = startY = moveX = moveY = 0;
                isUp = true;
                invalidate();
                savePwd();
                break;

            default:
                break;
        }
    }

    private void finishDraw() {

        for (PointInfo temp : points) {

            temp.setSelected(false);

            temp.setNextId(temp.getId());

        }

        lockString.delete(0, lockString.length());

        isUp = false;

        invalidate();
    }

    private void initPoints(PointInfo[] points) {

        int len = points.length;

        int seletedSpacing = (width - selectedBitmapDiameter * 3) / 4;

        int seletedX = seletedSpacing;

        int seletedY = height - width + seletedSpacing;

        int defaultX = seletedX + selectedBitmapRadius - defaultBitmapRadius;

        int defaultY = seletedY + selectedBitmapRadius - defaultBitmapRadius;

        for (int i = 0; i < len; i++) {

            if (i == 3 || i == 6) {

                seletedX = seletedSpacing;

                seletedY += selectedBitmapDiameter + seletedSpacing;

                defaultX = seletedX + selectedBitmapRadius
                        - defaultBitmapRadius;

                defaultY += selectedBitmapDiameter + seletedSpacing;

            }

            points[i] = new PointInfo(i, defaultX, defaultY, seletedX, seletedY);

            seletedX += selectedBitmapDiameter + seletedSpacing;

            defaultX += selectedBitmapDiameter + seletedSpacing;

        }
    }

    private void initPaint() {

        initLinePaint(linePaint);

        initTextPaint(textPaint);

        initWhiteLinePaint(whiteLinePaint);

    }

    /**
     * @param paint
     */
    private void initTextPaint(Paint paint) {

        textPaint.setTextSize(30);

        textPaint.setAntiAlias(true);

        textPaint.setTypeface(Typeface.MONOSPACE);

    }

    /**
     * @param paint
     */
    private void initLinePaint(Paint paint) {

        paint.setColor(Color.GRAY);

        paint.setStrokeWidth(30);//设置两个点之间的线的背景的宽度

        paint.setAntiAlias(true);

        paint.setStrokeCap(Cap.ROUND);

    }

    /**
     * @param paint
     */
    private void initWhiteLinePaint(Paint paint) {

        paint.setColor(Color.CYAN);

        paint.setStrokeWidth(25);//设置两个点之间的线的宽度

        paint.setAntiAlias(true);

        paint.setStrokeCap(Cap.ROUND);

    }

    /**
     *
     * @param canvas
     */
    private void drawNinePoint(Canvas canvas) {

        if (startPoint != null) {

            drawEachLine(canvas, startPoint);

        }

        for (PointInfo pointInfo : points) {

            if (pointInfo != null) {

                if (pointInfo.isSelected()) {

                    canvas.drawBitmap(selectedBitmap, pointInfo.getSeletedX(),
                            pointInfo.getSeletedY(), null);

                }

                canvas.drawBitmap(defaultBitmap, pointInfo.getDefaultX(),
                        pointInfo.getDefaultY(), null);

            }
        }

    }

    /**
     * @param canvas
     * @param point
     */
    private void drawEachLine(Canvas canvas, PointInfo point) {
        if (point.hasNextId()) {
            int n = point.getNextId();
            drawLine(canvas, point.getCenterX(), point.getCenterY(),
                    points[n].getCenterX(), points[n].getCenterY());
            drawEachLine(canvas, points[n]);
        }
    }

    /**
     *
     * @param canvas
     * @param startX
     * @param startY
     * @param stopX
     * @param stopY
     */
    private void drawLine(Canvas canvas, float startX, float startY,
                          float stopX, float stopY) {

        canvas.drawLine(startX, startY, stopX, stopY, linePaint);

        canvas.drawLine(startX, startY, stopX, stopY, whiteLinePaint);

    }

    /**
     * @author zkwlx
     *
     */
    private class PointInfo {

        private int id;

        private int nextId;

        private boolean selected;

        private int defaultX;

        private int defaultY;

        private int seletedX;

        private int seletedY;

        public PointInfo(int id, int defaultX, int defaultY, int seletedX,
                         int seletedY) {
            this.id = id;
            this.nextId = id;
            this.defaultX = defaultX;
            this.defaultY = defaultY;
            this.seletedX = seletedX;
            this.seletedY = seletedY;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public int getId() {
            return id;
        }

        public int getDefaultX() {
            return defaultX;
        }

        public int getDefaultY() {
            return defaultY;
        }

        public int getSeletedX() {
            return seletedX;
        }

        public int getSeletedY() {
            return seletedY;
        }

        public int getCenterX() {
            return seletedX + selectedBitmapRadius;
        }

        public int getCenterY() {
            return seletedY + selectedBitmapRadius;
        }

        public boolean hasNextId() {
            return nextId != id;
        }

        public int getNextId() {
            return nextId;
        }

        public void setNextId(int nextId) {
            this.nextId = nextId;
        }

        /**
         * @param x
         * @param y
         */
        public boolean isInMyPlace(int x, int y) {
            boolean inX = x > seletedX
                    && x < (seletedX + selectedBitmapDiameter);
            boolean inY = y > seletedY
                    && y < (seletedY + selectedBitmapDiameter);

            return (inX && inY);
        }

    }

    public String getPwd() {//获取本次的密码

        return lockString.toString();

    }

    /**
     * 作用：保存密码并且判断界面的跳转
     * 作者：unfind
     * 时间：2013年10月29日 14:47:47
     * */
    public void savePwd(){
        //跳转
        Intent intent = new Intent();
        /**
         * isFirst   它的作用是判断用户是不是第一次启动，要第一次设置手势密码。
         * 	两个值:
         * 		true	当为true时，是设置手势已经完成，目前是正在正常验证手势进入APP
         * 		false   设置手势密码,内部包括验证手势，验证手势失败，正确等...
         */
        boolean isFirst = SharedPreferencesUitl.getBooleanData(mContext, "isFirst", false);

        if(isFirst){
            //第一次手势密码
            String savePwd = SharedPreferencesUitl.getStringData(mContext, "savePwd", "");
            //当前手势密码
            String savepwds = this.getPwd();
            if (savePwd.equals(savepwds)) {
                //手势验证一致
                intent.setClass(mContext, MainActivity.class);
            }else {
                //手势验证不一致，可以加限制
                //手势验证一致
                MyToastUitl.showToast(mContext, "手势错误,请重试", MyToastUitl.SHORT_TOAST);
                intent.setClass(mContext, GestureActivity.class);
            }
        }else {
            /**
             * isFirstGest   它的作用是二次判断 根据isFirst的第一次验证后，需要用户再次确认手势密码
             * 	两个值:
             * 		true	开始与第一次密码进行校对
             * 		false   保存第一次的密码
             */
            boolean isFirstGest = SharedPreferencesUitl.getBooleanData(mContext, "isFirstGest", false);
            if (isFirstGest) {
                //第一次手势密码
                String savePwd = SharedPreferencesUitl.getStringData(mContext, "savePwd", "");
                //当前手势密码
                String savepwds = this.getPwd();
                if (savePwd.equals(savepwds)) {
                    //一致。设置完成
                    MyToastUitl.showToast(mContext, "手势设置成功", MyToastUitl.SHORT_TOAST);
                    //正确的流程，对于设置手势验证已经测底完成。
                    SharedPreferencesUitl.saveBooleanData(mContext, "isFirst", true);
                    SharedPreferencesUitl.saveBooleanData(mContext, "isOk", true);
                    intent.setClass(mContext, MainActivity.class);

                }else {
                    //不一致，设置错误  需要初始化重新设置
                    MyToastUitl.showToast(mContext, "两次手势不一致，请重新设置", MyToastUitl.LONG_TOAST);
                    SharedPreferencesUitl.saveBooleanData(mContext, "isFirstGest", false);
                    intent.setClass(mContext, GestureActivity.class);
                }

            }else {
                SharedPreferencesUitl.saveStringData(mContext, "savePwd", getPwd());
                SharedPreferencesUitl.saveBooleanData(mContext, "isFirstGest", true);
                intent.setClass(mContext, GestureActivity.class);
            }
        }
        mContext.startActivity(intent);
        ((Activity)mContext).finish();
    }

}
