package com.benben.yishanbang.ui.mine.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.mine.adapter.AccountDetailsListAdapter;
import com.benben.yishanbang.ui.mine.bean.AccountDetailsListBean;
import com.benben.yishanbang.ui.mine.bean.AccountDetailsTypeListBean;
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
 * Describe: //支出和收益列表
 */
public class ProfitWithAppriseListActivity extends BaseActivity {

    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.center_title)
    TextView centerTitle;
    private int mPage = 1;
    private int mPageSize = 15;
    private boolean isRefresh = true;
    private AccountDetailsListAdapter mAdapter;
    private int mType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_lottery;
    }

    @Override
    protected void initData() {
        mType = getIntent().getIntExtra(Constants.EXTRA_KEY_COMMON_TYPE, 1);
        centerTitle.setText(mType == 1 ? "收益明细" : "支出明细");
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
                .addParam("type", mType)
                .addParam("page", mPage)
                .addParam("size", mPageSize)
                .post()
                .url(NetUrlUtils.WALLET_DETAIL_LIST)
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                refreshLayout.finishRefresh(true);
                AccountDetailsTypeListBean accountDetailsListBean = JSONUtils.jsonString2Bean(s, AccountDetailsTypeListBean.class);
                if (accountDetailsListBean != null) {
                    refreshLayout.finishLoadMore(true);
                    if (accountDetailsListBean.getSumMoney() != null && accountDetailsListBean.getSumMoney().size() > 0) {
                        llytNoData.setVisibility(View.GONE);
                        rlvList.setVisibility(View.VISIBLE);
                        if (isRefresh) {
                            mAdapter.refreshList(accountDetailsListBean.getSumMoney());
                        } else {
                            mAdapter.appendToList(accountDetailsListBean.getSumMoney());
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

    @OnClick({R.id.rl_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
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
