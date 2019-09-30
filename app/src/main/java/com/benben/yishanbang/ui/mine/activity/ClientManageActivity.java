package com.benben.yishanbang.ui.mine.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.DateUtils;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.mine.adapter.ClientManageListAdapter;
import com.benben.yishanbang.ui.mine.bean.OrderManageCardListBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: //客户管理
 */
public class ClientManageActivity extends BaseActivity {
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.llyt_date)
    LinearLayout llytDate;

    private ClientManageListAdapter mAdapter;
    private int mPage = 1;
    private int mPageSize = 15;
    private boolean isRefresh = true;

    private String mCurrentTime;
    //现在的日期
    private Calendar mCurrentCalendar;
    //显示的日期
    private Calendar mDisplayCalendar;
    //当前时间
    private Date mCurrentDate;
    SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected int getLayoutId() {
        return R.layout.activity_client_manage;
    }

    @Override
    protected void initData() {
        centerTitle.setText("订单管理");
        //选择日期 暂时隐藏
        llytDate.setVisibility(View.GONE);
        initRefreshLayout();

        mCurrentDate = new Date();
        mCurrentTime = mDateFormat.format(mCurrentDate);
        tvDate.setText(mCurrentTime);

        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new ClientManageListAdapter(mContext);
        rlvList.setAdapter(mAdapter);


        getDataList();
    }

    private void initRefreshLayout() {
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            isRefresh = true;
            mPage = 1;
            getDataList();
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            isRefresh = false;
            mPage++;
            getDataList();
        });
    }

    private void getDataList() {
        BaseOkHttpClient.newBuilder()
                .addParam("shopId", MyApplication.mPreferenceProvider.getShopId())
                .url(NetUrlUtils.SHOP_CENTER_CARD_ORDER_MANAGE)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<OrderManageCardListBean> orderManageCardListBeans = JSONUtils.jsonString2Beans(json, OrderManageCardListBean.class);
                mAdapter.refreshList(orderManageCardListBeans);
                if (mAdapter.getItemCount() > 0) {
                    llytNoData.setVisibility(View.GONE);
                    rlvList.setVisibility(View.VISIBLE);
                } else {
                    llytNoData.setVisibility(View.VISIBLE);
                    rlvList.setVisibility(View.GONE);
                }
                refreshLayout.finishRefresh(true);
                refreshLayout.finishLoadMore(true);
            }

            @Override
            public void onError(int code, String msg) {
                llytNoData.setVisibility(View.VISIBLE);
                rlvList.setVisibility(View.GONE);
                ToastUtils.show(mContext, msg);
                refreshLayout.finishRefresh(true);
                refreshLayout.finishLoadMore(true);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                llytNoData.setVisibility(View.VISIBLE);
                rlvList.setVisibility(View.GONE);
                refreshLayout.finishRefresh(false);
                refreshLayout.finishLoadMore(false);
            }
        });

    }

    @OnClick({R.id.rl_back, R.id.iv_back_date, R.id.iv_front_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.iv_back_date://后一天
                selectDate(-1);
                break;
            case R.id.iv_front_date://前一天
                selectDate(1);
                break;
        }
    }


    //选择日期
    private void selectDate(int type) {
        mPage = 1;
        Date beforeDate = DateUtils.incDay(mCurrentDate, type);
        mCurrentDate = beforeDate;
        mCurrentTime = mDateFormat.format(beforeDate);
        tvDate.setText(mCurrentTime);
        getDataList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
