package com.benben.yishanbang.ui.mine.activity;

import android.view.View;
import android.widget.TextView;

import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.base.BaseActivity;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.suke.widget.SwitchButton;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: 控制中心
 */
public class ControlCenterActivity extends BaseActivity {


    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.sb_sound)
    SwitchButton sbSound;
    @BindView(R.id.sb_shake)
    SwitchButton sbShake;
    @BindView(R.id.tv_begin_time)
    TextView tvBeginTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    private String hourTime;
    private String minTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_control_center;
    }

    @Override
    protected void initData() {
        centerTitle.setText("控制中心");

        sbShake.setChecked(MyApplication.mPreferenceProvider.getNotifyVibrate());
        sbSound.setChecked(MyApplication.mPreferenceProvider.getNotifySound());
        sbShake.setOnCheckedChangeListener((view, isChecked) -> {
            MyApplication.mPreferenceProvider.setNotifyVibrate(isChecked);
            toast(isChecked ? "震动开启" : "震动关闭");
        });
        sbSound.setOnCheckedChangeListener((view, isChecked) -> {
            MyApplication.mPreferenceProvider.setNotifySound(isChecked);
            toast(isChecked ? "声音开启" : "声音关闭");
        });
    }

    @OnClick({R.id.rl_back, R.id.tv_begin_time, R.id.tv_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.tv_begin_time://开始时间
                selectTime(0);
                break;
            case R.id.tv_end_time://结束时间
                selectTime(1);
                break;
        }
    }

    private void selectTime(int type) {
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Calendar instance = Calendar.getInstance();
                instance.setTime(date);
                int year = instance.get(Calendar.YEAR);
                int month = instance.get(Calendar.MONTH) + 1;
                int day = instance.get(Calendar.DAY_OF_MONTH);
                int hour = instance.get(Calendar.HOUR_OF_DAY);
                int min = instance.get(Calendar.MINUTE);
                hourTime = hour + "";
                minTime = min + "";

                if (type == 0) {
                    tvBeginTime.setText(hourTime + ":"+minTime);
                } else {
                    tvEndTime.setText(hourTime + ":"+minTime);
                }
            }
        })
                .setLabel("", "", "", "", "", "")
                .setType(new boolean[]{false, false, false, true, true, false})
                .isDialog(true).build();
        pvTime.show();


    }
}
