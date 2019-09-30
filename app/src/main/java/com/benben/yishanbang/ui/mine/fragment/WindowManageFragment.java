package com.benben.yishanbang.ui.mine.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.LazyBaseFragments;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.mine.activity.OrderManageOrderDetailsActivity;
import com.benben.yishanbang.ui.mine.adapter.OrderManageWindowListAdapter;
import com.benben.yishanbang.ui.mine.bean.OrderManageWindowListBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

import static com.benben.yishanbang.config.Constants.EXTRA_KEY_ENTER_TYPE;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: 橱窗模块-订单管理
 */
public class WindowManageFragment extends LazyBaseFragments {
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private OrderManageWindowListAdapter mAdapter;
    private int mPage = 1;
    private int mPageSize = 15;
    private boolean isRefresh = true;
    //订单类型 1待发货 2待取货 3 已完成
    private int mOrderType;

    private int mEntryType; //0  s橱窗订单管理   1  商户中心订单管理

    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.frag_shopping_order, null);
        return mRootView;
    }

    @Override
    public void initView() {
        mOrderType = getArguments().getInt("orderType");
        mEntryType = getArguments().getInt(EXTRA_KEY_ENTER_TYPE);

        initRefreshLayout();

        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new OrderManageWindowListAdapter(mContext);
        mAdapter.setOrderType(mOrderType);
        rlvList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<OrderManageWindowListBean>() {
            @Override
            public void onItemClick(View view, int position, OrderManageWindowListBean model) {
                if (mOrderType <= 3) {
                    Intent intent = new Intent(mContext, OrderManageOrderDetailsActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_USER_ID, model.getUserId());
                    intent.putExtra(Constants.EXTRA_KEY_ORDER_ID, model.getOrderId());
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view, int position, OrderManageWindowListBean model) {

            }
        });
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
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRefresh = false;
                mPage++;
                getDataList();
            }
        });
    }

    @Override
    public void initData() {
        getDataList();
    }

    //获取订单列表
    private void getDataList() {
        String url = NetUrlUtils.WINDOW_ORDER_MANAGE;

        BaseOkHttpClient.newBuilder()
                .addParam("type", mOrderType >= 3 ? 3 : mOrderType)
                .url(url)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<OrderManageWindowListBean> orderManageTeaListBeans = JSONUtils.jsonString2Beans(json, OrderManageWindowListBean.class);
                mAdapter.refreshList(orderManageTeaListBeans);
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
                // ToastUtils.show(mContext, msg);
                llytNoData.setVisibility(View.VISIBLE);
                rlvList.setVisibility(View.GONE);
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
}
