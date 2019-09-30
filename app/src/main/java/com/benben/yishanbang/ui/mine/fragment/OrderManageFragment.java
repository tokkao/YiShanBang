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
import com.benben.yishanbang.ui.mine.adapter.OrderManageTeaListAdapter;
import com.benben.yishanbang.ui.mine.bean.OrderManageTeaListBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

import static com.benben.yishanbang.config.Constants.ENTRY_TYPE_SHOP;
import static com.benben.yishanbang.config.Constants.EXTRA_KEY_ENTER_TYPE;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: 商户中心-奶茶-订单管理
 */
public class OrderManageFragment extends LazyBaseFragments {
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private OrderManageTeaListAdapter mAdapter;
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
        mAdapter = new OrderManageTeaListAdapter(mContext);
        mAdapter.setOrderType(mOrderType);
        rlvList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<OrderManageTeaListBean>() {
            @Override
            public void onItemClick(View view, int position, OrderManageTeaListBean model) {
                if (mOrderType == 2) {
                    Intent intent = new Intent(mContext, OrderManageOrderDetailsActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_COMMON_TYPE, mOrderType);
                    intent.putExtra(Constants.EXTRA_KEY_USER_ID, model.getUserId());
                    intent.putExtra(Constants.EXTRA_KEY_ORDER_ID, model.getOrderId());
                    startActivity(intent);
                }

            }

            @Override
            public void onItemLongClick(View view, int position, OrderManageTeaListBean model) {

            }
        });
//        Intent intent = new Intent(mContext, OrderManageOrderDetailsActivity.class);
//        intent.putExtra(Constants.EXTRA_KEY_COMMON_TYPE, mOrderType);
//        startActivity(intent);
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
        if (mEntryType == ENTRY_TYPE_SHOP) {
            url = NetUrlUtils.SHOP_CENTER_TEA_ORDER_MANAGE;
        }

        BaseOkHttpClient.newBuilder()
                .addParam("type", mOrderType)
                .url(url)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<OrderManageTeaListBean> orderManageTeaListBeans = JSONUtils.jsonString2Beans(json, OrderManageTeaListBean.class);
//                mAdapter.refreshList(orderManageTeaListBeans);
//                if (mAdapter.getItemCount() > 0) {
//                    llytNoData.setVisibility(View.GONE);
//                    rlvList.setVisibility(View.VISIBLE);
//                } else {
//                    llytNoData.setVisibility(View.VISIBLE);
//                    rlvList.setVisibility(View.GONE);
//                }
//                refreshLayout.finishRefresh(true);
//                refreshLayout.finishLoadMore(true);
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
