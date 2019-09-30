package com.benben.yishanbang.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.benben.yishanbang.R;

/**
 * 中间弹出
 * 答题完成
 */

public class AnswerPopupWindow extends PopupWindow {

    private View view;
    private Activity mContext;
    private OnAnswerCallback mOnAnswerCallback;

    private TextView tvCancel;
    private TextView tvSubmit;
    private TextView tvPercentage;
    private TextView tvContent;

    public AnswerPopupWindow(Activity activity, OnAnswerCallback mOnAnswerCallback) {
        super(activity);
        this.mOnAnswerCallback = mOnAnswerCallback;
        this.mContext = activity;
        init();
    }

    private void init() {
        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pop_answer, null);
        tvCancel = view.findViewById(R.id.tv_cancel);
        tvSubmit = view.findViewById(R.id.tv_submit);
        tvContent = view.findViewById(R.id.tv_content);
        tvPercentage = view.findViewById(R.id.tv_percentage);

        //取消
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mOnAnswerCallback != null) {
                    mOnAnswerCallback.cancel();
                }
            }
        });

        //确定
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mOnAnswerCallback != null) {
                    mOnAnswerCallback.submit();
                }
            }
        });

        // 导入布局
        this.setContentView(view);
        // 设置动画效果
        setAnimationStyle(R.style.popwindow_anim_style);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        // 设置可触
        setFocusable(true);
        final ColorDrawable dw = new ColorDrawable(0x0000000);
        setBackgroundDrawable(dw);
        // 单击屏幕关闭弹窗
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int height = view.findViewById(R.id.ll_pop).getTop();
                int bottom = view.findViewById(R.id.ll_pop).getBottom();
                int y = (int) motionEvent.getY();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height || y > bottom) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    public interface OnAnswerCallback {
        //取消
        void cancel();

        //确定
        void submit();
    }

}
