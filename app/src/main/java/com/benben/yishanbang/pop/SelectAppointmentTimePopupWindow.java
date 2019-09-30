package com.benben.yishanbang.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.benben.yishanbang.R;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择预约时间
 */

public class SelectAppointmentTimePopupWindow extends PopupWindow {

    private View view;
    private Activity mContext;
    //选择时间的回调
    private OnTimeCallback mOnTimeCallback;
    //数据集合
    private List<String> mStartTimeList = new ArrayList<>();
    private List<String> mDurationTimeList = new ArrayList<>();

    //开始时间
    private String mBeginTime;
    //持续时间
    private String mDurationTime;
    public TextView tvCancel;
    public TextView tvConfirm;
    public WheelView wvBeginTime;
    public WheelView wvEndTime;
    public TextView tvEndTime;
    public LinearLayout llytRootView;

    public SelectAppointmentTimePopupWindow(Activity activity, OnTimeCallback mOnPayCallback) {
        super(activity);
        this.mOnTimeCallback = mOnPayCallback;
        this.mContext = activity;
        init();
    }

    private void init() {
        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pop_select_appointment_time, null);
        this.tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        this.llytRootView = (LinearLayout) view.findViewById(R.id.llyt_root_view);
        this.tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        this.wvBeginTime = (WheelView) view.findViewById(R.id.wv_begin_time);
        this.wvEndTime = (WheelView) view.findViewById(R.id.wv_end_time);
        this.tvEndTime = (TextView) view.findViewById(R.id.tv_end_time);


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


        wvBeginTime.setCyclic(false);
        wvBeginTime.setLineSpacingMultiplier(1.2f);
        wvBeginTime.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mBeginTime = mStartTimeList.get(index);
            }
        });

        wvEndTime.setCyclic(false);
        wvEndTime.setLineSpacingMultiplier(1.0f);
        wvEndTime.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mDurationTime = mDurationTimeList.get(index);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (mOnTimeCallback != null) {
                    mOnTimeCallback.timeCallBack(mBeginTime,mDurationTime);
                }
            }
        });

    }

    /**
     * 添加数据
     */
    public void setTimeList(List<String> startTimeList, List<String> durationTimeList) {
        this.mStartTimeList = startTimeList;
        this.mDurationTimeList = durationTimeList;
        if (startTimeList != null && startTimeList.size() > 0) {
            mBeginTime = startTimeList.get(0);
        }
        if (mDurationTimeList != null && mDurationTimeList.size() > 0) {
            mDurationTime = mDurationTimeList.get(0);
        }
        ArrayWheelAdapter cityWheel = new ArrayWheelAdapter(mStartTimeList);
        ArrayWheelAdapter arrayWheelAdapter = new ArrayWheelAdapter(mDurationTimeList);
        wvBeginTime.setAdapter(cityWheel);
        wvEndTime.setAdapter(arrayWheelAdapter);
    }


    public interface OnTimeCallback {

        void timeCallBack(String beginTime,String durationTime);

    }


}
