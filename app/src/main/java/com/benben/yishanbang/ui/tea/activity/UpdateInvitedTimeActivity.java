package com.benben.yishanbang.ui.tea.activity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.benben.commoncore.utils.RxBus;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2019/8/16 0016
 * Describe:修改他的应邀时间
 */
public class UpdateInvitedTimeActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.tv_work_begin_time)
    TextView tvWorkBeginTime;
    @BindView(R.id.tv_work_end_time)
    TextView tvWorkEndTime;
    @BindView(R.id.tv_sat_begin_time)
    TextView tvSatBeginTime;
    @BindView(R.id.tv_sat_end_time)
    TextView tvSatEndTime;
    @BindView(R.id.tv_week_begin_time)
    TextView tvWeekBeginTime;
    @BindView(R.id.tv_week_end_time)
    TextView tvWeekEndTime;
    private String hourTime;
    private String minTime;
    //周六开始时间
    private String mSaturdayStartTime;
    //周六结束时间
    private String mSaturdayEndTime;
    //周日开始时间
    private String mSundayStartTime;
    //周日结束时间
    private String mSundayEndTime;
    //工作日开始时间
    private String mWorkStartTime;
    //工作日结束时间
    private String mWorkEndTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_user_invited_time;
    }

    @Override
    protected void initData() {
        centerTitle.setText("应邀时间");
        rightTitle.setText("保存");

        mSaturdayStartTime = getIntent().getStringExtra("saturday_start_time");
        mSaturdayEndTime = getIntent().getStringExtra("saturday_end_time");
        mSundayStartTime = getIntent().getStringExtra("sunday_start_time");
        mSundayEndTime = getIntent().getStringExtra("sunday_end_time");
        mWorkStartTime = getIntent().getStringExtra("work_start_time");
        mWorkEndTime = getIntent().getStringExtra("work_end_time");
        tvSatBeginTime.setText(mSaturdayStartTime);
        tvSatEndTime.setText(mSaturdayEndTime);
        tvWeekBeginTime.setText(mSundayStartTime);
        tvWeekEndTime.setText(mSundayEndTime);
        tvWorkBeginTime.setText(mWorkStartTime);
        tvWorkEndTime.setText(mWorkEndTime);
    }

    @OnClick({R.id.rl_back, R.id.right_title, R.id.tv_work_begin_time, R.id.tv_work_end_time, R.id.tv_sat_begin_time, R.id.tv_sat_end_time, R.id.tv_week_begin_time, R.id.tv_week_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back://返回
                finish();
                break;
            case R.id.right_title://保存
                saveWorkTimeInfo();
                break;
            case R.id.tv_work_begin_time://工作日-->开始时间
                chooseTime(0);
                break;
            case R.id.tv_work_end_time://工作日--->结束时间
                chooseTime(1);
                break;
            case R.id.tv_sat_begin_time://周六--->开始时间
                chooseTime(2);
                break;
            case R.id.tv_sat_end_time://周六---->结束时间
                chooseTime(3);
                break;
            case R.id.tv_week_begin_time://周日---->开始时间
                chooseTime(4);
                break;
            case R.id.tv_week_end_time://周日---->结束时间
                chooseTime(5);
                break;
        }
    }

    //保存工作时间信息
    private void saveWorkTimeInfo() {
        BaseOkHttpClient.newBuilder()
                .addParam("sixStartTime", tvSatBeginTime.getText().toString())
                .addParam("sixSendTime", tvSatEndTime.getText().toString())
                .addParam("sevenStartTime", tvWeekBeginTime.getText().toString())
                .addParam("sevenEndTime", tvWeekEndTime.getText().toString())
                .addParam("jobStartTimer", tvWorkBeginTime.getText().toString())
                .addParam("jobEndTime", tvWorkEndTime.getText().toString())
                .url(NetUrlUtils.UPDATE_USER_WORK_TIME)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
                RxBus.getInstance().post(Constants.REFRESH_USER_DETAILS_INFO);
                finish();
            }

            @Override
            public void onError(int code, String msg) {
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });


    }

    private void chooseTime(int type) {
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
                    tvWorkBeginTime.setText(hourTime + ":" + minTime);
                } else if (type == 1) {
                    tvWorkEndTime.setText(hourTime + ":" + minTime);
                } else if (type == 2) {
                    tvSatBeginTime.setText(hourTime + ":" + minTime);
                } else if (type == 3) {
                    tvSatEndTime.setText(hourTime + ":" + minTime);
                } else if (type == 4) {
                    tvWeekBeginTime.setText(hourTime + ":" + minTime);
                } else if (type == 5) {
                    tvWeekEndTime.setText(hourTime + ":" + minTime);
                }
            }
        })
                .setLabel("", "", "", "", "", "")
                .setType(new boolean[]{false, false, false, true, true, false})
                .isDialog(true).build();
        pvTime.show();


    }
}
