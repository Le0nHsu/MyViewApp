package com.leon.myviewapp.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * 绘制流程 measure  -> layout  -> draw (测量，放置，绘画)
 * onMeasure - onLayout - onDraw(View 没有onLayout  ViewGroup 有)
 * onMeasure() 测量自身view的宽高和子view的宽高
 * onLayout() 摆放所有子view的位置
 * onDraw() 绘制自己的内容
 */
public class MyToggleView extends View {
    private boolean state = false;
    private int choosedColor;
    private int unChoosedColor;
    private Drawable stateDrawable;
    private boolean isChoosed = false;
    private Paint paint;

    /**
     * 代码里new的时候调用
     *
     * @param context
     */
    public MyToggleView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint();
    }

    /**
     * xml使用时调用
     *
     * @param context
     * @param attrs
     */
    public MyToggleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        String namespace = "http://schemas.android.com/apk/res/com.leon.myviewapp";
//        attrs.getAttributeResourceValue(namespace, );
    }

    /**
     * xml中有style的时候调用
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public MyToggleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MyToggleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setChoosedColor(int color) {
        choosedColor = color;
    }

    public void setUnChoosedColor(int color) {
        unChoosedColor = color;
    }

    public void setStateBackground(Drawable drawable) {
//        stateDrawable = drawable;
    }

    public void setDefaultState(boolean isChoosed) {
        this.isChoosed = isChoosed;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(100, 40);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//canvas.drawBitmap(new int[]{choosedColor, unChoosedColor},);
//        canvas.drawBitmap(stateDrawable, 0, 0, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        state = !state;
        onStateChangeListener.onStateChange(state);
        invalidate();//重新调用onDraw()
        return true;
    }

    private OnStateChangeListener onStateChangeListener = null;

    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }

    public interface OnStateChangeListener {
        void onStateChange(boolean state);
    }


}
