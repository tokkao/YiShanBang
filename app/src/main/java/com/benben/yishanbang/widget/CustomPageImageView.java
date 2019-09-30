package com.benben.yishanbang.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/6/24
 * Time: 18:00
 * <p>
 * 宽高比例1:0.9
 */
public class CustomPageImageView extends AppCompatImageView {
    public CustomPageImageView(Context context) {
        super(context);
    }

    public CustomPageImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPageImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) Math.round(width * 0.9);
        setMeasuredDimension(width, height);
    }
}
