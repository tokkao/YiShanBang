package com.benben.yishanbang.ui.mine.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.mine.adapter.OrderManageCardListAdapter;
import com.benben.yishanbang.ui.mine.bean.OrderManageCardListBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: //优惠卡-订单管理
 */
public class OrderManageCardActivity extends BaseActivity {
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private OrderManageCardListAdapter mAdapter;
    private int mPage = 1;
    private int mPageSize = 15;
    private boolean isRefresh = true;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_lottery;
    }

    @Override
    protected void initData() {
        centerTitle.setText("订单管理");

        initRefreshLayout();

        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new OrderManageCardListAdapter(mContext);
        rlvList.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<OrderManageCardListBean>() {
            @Override
            public void onItemClick(View view, int position, OrderManageCardListBean model) {
//                startActivity(new Intent());
            }

            @Override
            public void onItemLongClick(View view, int position, OrderManageCardListBean model) {

            }
        });

        getDataList();
    }

    private void initRefreshLayout() {
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            isRefresh = true;
            mPage = 1;
            getDataList();
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            isRefresh = false;
            mPage ++;
            getDataList();
        });
    }
    //获取订单列表
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

    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
