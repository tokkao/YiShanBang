package com.benben.yishanbang.ui.mine.activity;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.mine.adapter.LotteryActiveAdapter;
import com.benben.yishanbang.ui.mine.bean.LotteryActiveBean;
import com.lijiankun24.shadowlayout.ShadowLayout;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 抽奖活动
 */
public class LotteryActiveActivity extends BaseActivity {


    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_active_start_period)
    TextView tvActivieStartPeriod;
    @BindView(R.id.tv_active_end_period)
    TextView tvActiveEndPeriod;
    @BindView(R.id.tv_user_count)
    TextView tvUserCount;
    @BindView(R.id.tv_active_start_time)
    TextView tvActiveStartTime;
    @BindView(R.id.btn_start_lottery)
    Button btnStartLottery;
    @BindView(R.id.rlv_price_goods)
    RecyclerView rlvPriceGoods;
    @BindView(R.id.shadow_layout)
    ShadowLayout shadowLayout;
    private LotteryActiveAdapter lotteryActiveAdapter;
    //活动id
    private String mActiveId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lottery_active;
    }

    @Override
    protected void initData() {
        centerTitle.setText("抽奖活动");

        rlvPriceGoods.setLayoutManager(new LinearLayoutManager(mContext));
        lotteryActiveAdapter = new LotteryActiveAdapter(mContext);
        rlvPriceGoods.setAdapter(lotteryActiveAdapter);
        getLotteryActive();
    }

    /**
     * 获取抽奖活动内容数据
     */
    private void getLotteryActive() {

        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.QUERY_ACTIVITY)
                .get()
                .build()
                .enqueue(mContext, new BaseCallBack<String>() {

                    @Override
                    public void onSuccess(String json, String msg) {

                        LotteryActiveBean lotteryActiveBean = null;
                        try {
                            lotteryActiveBean = JSONUtils.jsonString2Bean(json, LotteryActiveBean.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (lotteryActiveBean != null) {
                            mActiveId = lotteryActiveBean.getActivityVo().getId();
                            tvActivieStartPeriod.setText(lotteryActiveBean.getActivityVo().getStartPeriod());//开始周期
                            tvActiveEndPeriod.setText(lotteryActiveBean.getActivityVo().getEndPeriod());//结束周期
                            tvActiveStartTime.setText(lotteryActiveBean.getActivityVo().getStartTime());//开始时间
                            lotteryActiveAdapter.refreshList(lotteryActiveBean.getPrizeVos());
                            btnStartLottery.setEnabled(true);
                        } else {
                            // toast(msg);
                            btnStartLottery.setEnabled(false);
                            shadowLayout.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onError(int code, String msg) {
                        toast(msg);
                        btnStartLottery.setEnabled(false);
                        shadowLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        toast(e.getMessage());
                    }
                });
    }


    @OnClick({R.id.rl_back, R.id.btn_start_lottery})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.btn_start_lottery://开始抽奖
                startActivity(new Intent(mContext, LotteryListActivity.class).putExtra("active_id", StringUtils.isEmpty(mActiveId) ? "" : mActiveId));
                break;
        }
    }
}
