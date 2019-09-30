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
 * 宽高比例1:0.5
 */
public class CustomImageViewSquare extends AppCompatImageView {
    public CustomImageViewSquare(Context context) {
        super(context);
    }

    public CustomImageViewSquare(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageViewSquare(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width;
        setMeasuredDimension(width, height);
    }
}
