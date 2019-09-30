package com.benben.yishanbang.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.benben.yishanbang.R;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择预约时间
 */

public class SelectConditionPopupWindow extends PopupWindow {

    private View view;
    private Activity mContext;
    //选择时间的回调
    private OnConditionCallback mOnConditionCallback;
    //数据集合
    private List<String> mStartTimeList = new ArrayList<>();
    private List<String> mDurationTimeList = new ArrayList<>();

    //开始时间
    private String mBeginTime;
    //持续时间
    private String mDurationTime;
    public TextView tvCancel;
    public TextView tvConfirm;
    public TextView tvWoman;
    public TextView tvMan;
    public TextView tvUnlimited;
    public TextView tvCount;
    public TextView tvWeight;
    public TextView tvHeight;
    public ImageView ivReduce;
    public ImageView ivAdd;
    public LinearLayout llytRootView;
    public SwitchButton sbIsAnonymous;
    //是否匿名
    private boolean isAnonymous = false;
    //性别 0 男 1女 2不限
    private int mSex = 1;
    private String mHeight = "";
    private String mWeight = "";

    public SelectConditionPopupWindow(Activity activity, OnConditionCallback mOnPayCallback) {
        super(activity);
        this.mOnConditionCallback = mOnPayCallback;
        this.mContext = activity;
        init();
    }

    private void init() {
        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pop_select_condition, null);
        this.tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        this.llytRootView = (LinearLayout) view.findViewById(R.id.llyt_root_view);
        this.tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        this.tvWoman = (TextView) view.findViewById(R.id.tv_woman);
        this.tvMan = (TextView) view.findViewById(R.id.tv_man);
        this.tvUnlimited = (TextView) view.findViewById(R.id.tv_unlimited);
        this.tvCount = (TextView) view.findViewById(R.id.tv_count);
        this.tvWeight = (TextView) view.findViewById(R.id.tv_weight);
        this.tvHeight = (TextView) view.findViewById(R.id.tv_height);
        this.ivAdd = (ImageView) view.findViewById(R.id.iv_add);
        this.ivReduce = (ImageView) view.findViewById(R.id.iv_reduce);
        this.sbIsAnonymous = (SwitchButton) view.findViewById(R.id.sb_is_anonymous);


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
                int height = view.findViewById(R.id.llyt_root_view).getTop();
                int bottom = view.findViewById(R.id.llyt_root_view).getBottom();
                int y = (int) motionEvent.getY();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height || y > bottom) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        tvWoman.setSelected(true);
        tvWoman.setTextColor(mContext.getResources().getColor(R.color.white));
        //取消
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //确定
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (mOnConditionCallback != null) {
                    mOnConditionCallback.onCondition(mSex, Integer.parseInt(tvCount.getText().toString()), isAnonymous, mHeight, mWeight);
                }
            }
        });
        //女
        tvWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSex = 1;
                tvWoman.setSelected(true);
                tvMan.setSelected(false);
                tvUnlimited.setSelected(false);
                tvWoman.setTextColor(mContext.getResources().getColor(R.color.white));
                tvUnlimited.setTextColor(mContext.getResources().getColor(R.color.color_333333));
                tvMan.setTextColor(mContext.getResources().getColor(R.color.color_333333));
            }
        });
        //男
        tvMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSex = 0;
                tvMan.setSelected(true);
                tvWoman.setSelected(false);
                tvUnlimited.setSelected(false);
                tvMan.setTextColor(mContext.getResources().getColor(R.color.white));
                tvUnlimited.setTextColor(mContext.getResources().getColor(R.color.color_333333));
                tvWoman.setTextColor(mContext.getResources().getColor(R.color.color_333333));
            }
        });
        //不限
        tvUnlimited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSex = 2;
                tvMan.setSelected(false);
                tvWoman.setSelected(false);
                tvUnlimited.setSelected(true);
                tvUnlimited.setTextColor(mContext.getResources().getColor(R.color.white));
                tvMan.setTextColor(mContext.getResources().getColor(R.color.color_333333));
                tvWoman.setTextColor(mContext.getResources().getColor(R.color.color_333333));
            }
        });
        //减
        ivReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(tvCount.getText().toString());
                if (count == 1) return;
                tvCount.setText((count - 1) + "");
            }
        });
        //加
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(tvCount.getText().toString());
                tvCount.setText((count + 1) + "");
            }
        });
        //是否匿名
        sbIsAnonymous.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isAnonymous = isChecked;
            }
        });
        tvHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tvWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    //条件数据回调
    public interface OnConditionCallback {

        void onCondition(int sex, int count, boolean isAnonymous, String height, String weight);

    }


}
