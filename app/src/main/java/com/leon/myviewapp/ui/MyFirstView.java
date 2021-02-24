package com.leon.myviewapp.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyFirstView extends View {
    public MyFirstView(Context context) {
        super(context);
    }

    public MyFirstView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFirstView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyFirstView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
