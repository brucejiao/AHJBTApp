package com.bruce.android.ui.viewpagerindicator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.bruce.android.R;

/**
 * Created by JiaoJianJun on 2017/11/17.
 */

public class SlipButton extends View implements OnTouchListener {

    private Bitmap bg_on, bg_off, slip_btn,buttonbg;
    private Rect canBeSee;//可视区。
    private float timelyX;// 实时X坐标
    private boolean isSlipping = false;// 按钮是否在滑动
    private boolean currentState = false;// 当前开关状态
    private boolean isSetChangedListener = false;
    private OnChangedListener onChangedListener;
    private boolean isSetState;

    public SlipButton(Context context) {
        super(context);
        init();

    }

    public SlipButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public SlipButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();

    }

    private void init() {
        bg_on = BitmapFactory.decodeResource(getResources(),
                R.drawable.slip_bg_on);
        bg_off = BitmapFactory.decodeResource(getResources(),
                R.drawable.slip_bg_off);
        slip_btn = BitmapFactory.decodeResource(getResources(),
                R.drawable.slip_btn);
        canBeSee=new Rect(0,0,bg_off.getWidth(),bg_off.getHeight());
        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.clipRect(canBeSee);
        Matrix matrix = new Matrix();
        Paint paint = new Paint();
        //实际绘图坐标
        float x;
        float y=5;
        if(isSlipping){//正在滑动
            if (timelyX >= bg_on.getWidth())// 是否划出指定范围,不能让游标跑到外头,必须做这个判断

                x = bg_on.getWidth() - slip_btn.getWidth() / 2;// 减去游标1/2的长度...

            else if (timelyX < 0) {
                x = 0;
            } else {
                x = timelyX - slip_btn.getWidth() / 2;
            }
        }else{//停止滑动
            if(currentState){
                x=bg_off.getWidth()-slip_btn.getWidth();
            }else{
                x=0;
            }
        }
        if(isSetState){//是否设置状态
            x=bg_on.getWidth()-slip_btn.getWidth();
            isSetState=!isSetState;
        }
        if(x < 0){//touch指针移出左边界
            x = 0;
        }else if(x > bg_on.getWidth() - slip_btn.getWidth()){
            x = bg_on.getWidth() - slip_btn.getWidth();
        }

        canvas.drawBitmap(bg_on, x - bg_on.getWidth() + slip_btn.getWidth(), 0,
                paint);
        canvas.drawBitmap(bg_off, x, y-5, paint);

        canvas.drawBitmap(slip_btn, x, y-2, paint);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                timelyX = event.getX();
                break;

            case MotionEvent.ACTION_DOWN:
                if (event.getX() > bg_on.getWidth()
                        || event.getY() > bg_on.getHeight())
                    return false;
                isSlipping = true;
                timelyX = event.getX();
                break;

            case MotionEvent.ACTION_UP:
                isSlipping = false;
                boolean preStateUp = currentState;
                if (event.getX() >= (bg_on.getWidth() / 2)) {
                    timelyX = bg_on.getWidth() - slip_btn.getWidth() / 2;
                    currentState = true;
                } else {
                    timelyX = slip_btn.getWidth() / 2;
                    currentState = false;
                }
                if (isSetChangedListener && (preStateUp != currentState)) {
                    onChangedListener.OnChanged(currentState);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                isSlipping = false;
                boolean preStateCancel = currentState;
                if (timelyX >= (bg_on.getWidth() / 2)) {
                    timelyX = bg_on.getWidth() - slip_btn.getWidth() / 2;
                    currentState = true;
                } else {
                    timelyX = slip_btn.getWidth() / 2;
                    currentState = false;
                }

                if (isSetChangedListener && (preStateCancel != currentState)) {
                    onChangedListener.OnChanged(currentState);
                }
                break;
        }
        invalidate();
        return true;
    }

    public interface OnChangedListener {
        abstract void OnChanged(boolean CheckState);
    }

    public void setOnChangedListener(OnChangedListener listener) {
        isSetChangedListener = true;
        onChangedListener = listener;
    }

    public void setState(boolean isSetState) {
        this.isSetState = isSetState;
        currentState = isSetState;
    }

}