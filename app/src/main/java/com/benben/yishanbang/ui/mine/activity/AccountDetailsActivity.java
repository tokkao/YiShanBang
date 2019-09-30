package com.benben.yishanbang.ui.mine.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.mine.adapter.AccountDetailsListAdapter;
import com.benben.yishanbang.ui.mine.bean.AccountDetailsListBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: //交易明细
 */
public class AccountDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_profit)
    TextView tvProfit;
    @BindView(R.id.tv_expenses)
    TextView tvExpenses;
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.center_title)
    TextView centerTitle;
    //状态栏高度
    private int statusBarHeight;
    private int mPage = 1;
    private int mPageSize = 15;
    private boolean isRefresh = true;
    private AccountDetailsListAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_details;
    }

    @Override
    protected void initData() {
//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) llytTitleBar.getLayoutParams();
//        layoutParams.setMargins(0, statusBarHeight, 0, 0);
        centerTitle.setText("交易汇总");
        initRefreshLayout();
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new AccountDetailsListAdapter(mContext);
        rlvList.setAdapter(mAdapter);

        getDataList();
    }

    private void initRefreshLayout() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = true;
                mPage = 1;
                getDataList();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRefresh = false;
                mPage++;
                getDataList();
            }
        });
    }

    private void getDataList() {

        BaseOkHttpClient.newBuilder()
                .addParam("type", "0")
                .addParam("page", mPage)
                .addParam("size", mPageSize)
                .post()
                .url(NetUrlUtils.WALLET_DETAIL_LIST)
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                refreshLayout.finishRefresh(true);
                AccountDetailsListBean accountDetailsListBean = JSONUtils.jsonString2Bean(s, AccountDetailsListBean.class);
                if (accountDetailsListBean != null) {
                    refreshLayout.finishLoadMore(true);
                    tvProfit.setText("¥ " + (StringUtils.isEmpty(accountDetailsListBean.getSumMoney()) ? "0" : accountDetailsListBean.getSumMoney()));
                    tvExpenses.setText("¥ " + (StringUtils.isEmpty(accountDetailsListBean.getPayMoney()) ? "0" : accountDetailsListBean.getPayMoney()));
                    if (accountDetailsListBean.getUserBill() != null && accountDetailsListBean.getUserBill().size() > 0) {
                        llytNoData.setVisibility(View.GONE);
                        rlvList.setVisibility(View.VISIBLE);
                        if (isRefresh) {
                            mAdapter.refreshList(accountDetailsListBean.getUserBill());
                        } else {
                            mAdapter.appendToList(accountDetailsListBean.getUserBill());
                        }
                    } else {
                        llytNoData.setVisibility(View.VISIBLE);
                        rlvList.setVisibility(View.GONE);
                    }
                } else {
                    refreshLayout.finishLoadMore(false);
                }
            }

            @Override
            public void onError(int code, String msg) {
                refreshLayout.finishRefresh(true);
                refreshLayout.finishLoadMore(false);
                llytNoData.setVisibility(View.VISIBLE);
                rlvList.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    @OnClick({R.id.rl_back, R.id.llyt_profit, R.id.llyt_expenses})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.llyt_profit://收益
                startActivity(new Intent(mContext, ProfitWithAppriseListActivity.class).putExtra(Constants.EXTRA_KEY_COMMON_TYPE, 1));
                break;
            case R.id.llyt_expenses://支出
                startActivity(new Intent(mContext, ProfitWithAppriseListActivity.class).putExtra(Constants.EXTRA_KEY_COMMON_TYPE, 2));
                break;
        }
    }


   /* @Override
    protected void setStatusBar() {
        //透明状态栏
        StatusBarUtils.setTransparent(mContext);
        //获取状态栏高度 然后设置magin
        statusBarHeight = StatusBarUtils.getStatusBarHeight(mContext);
    }
*/
}
