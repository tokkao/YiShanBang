package com.benben.yishanbang.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import androidx.annotation.Nullable;

/**
 * Created by Administrator on 2018/3/16 0016.
 */

public class CustomGridView extends GridView {
    public CustomGridView(Context context) {
        super(context);
    }

    public CustomGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomGridView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }
}
