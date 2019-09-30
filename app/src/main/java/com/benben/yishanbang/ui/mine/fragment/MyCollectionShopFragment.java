package com.benben.yishanbang.ui.mine.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.LazyBaseFragments;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.discount.activity.BusinessDetailActivity;
import com.benben.yishanbang.ui.mine.adapter.MyCollectionShopListAdapter;
import com.benben.yishanbang.ui.mine.bean.CollectionShopListBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: 我收藏的店铺页面
 */
public class MyCollectionShopFragment extends LazyBaseFragments {
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private MyCollectionShopListAdapter mAdapter;
    private int mPage = 1;
    private int mPageSize = 15;
    private boolean isRefresh = true;


    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.frag_shopping_order, null);
        return mRootView;
    }

    @Override
    public void initView() {
        refreshLayout.setEnableLoadMore(false);
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
                mPage ++;
                getDataList();
            }
        });
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new MyCollectionShopListAdapter(mContext);
        rlvList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<CollectionShopListBean>() {
            @Override
            public void onItemClick(View view, int position, CollectionShopListBean model) {
                Bundle bundle = new Bundle();
                bundle.putString("store_id",model.getShopId());
                bundle.putString("store_distance",model.getDistance()+"");
                MyApplication.openActivity(mContext, BusinessDetailActivity.class,bundle);

            }

            @Override
            public void onItemLongClick(View view, int position, CollectionShopListBean model) {

            }
        });
    }

    @Override
    public void initData() {
        getDataList();
    }

    //获取收藏的店铺列表
    private void getDataList() {

        BaseOkHttpClient.newBuilder()
                .addParam("longitude", MyApplication.mPreferenceProvider.getLongitude())
                .addParam("latitude", MyApplication.mPreferenceProvider.getLatitude())
                .url(NetUrlUtils.COLLECTION_SHOP_LIST)
                .get()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<CollectionShopListBean> listBeans = JSONUtils.jsonString2Beans(json, CollectionShopListBean.class);
                mAdapter.refreshList(listBeans);
                if (mAdapter.getItemCount() > 0) {
                    llytNoData.setVisibility(View.GONE);
                    rlvList.setVisibility(View.VISIBLE);
                } else {
                    llytNoData.setVisibility(View.VISIBLE);
                    rlvList.setVisibility(View.GONE);
                }
                refreshLayout.finishRefresh(true);
            }

            @Override
            public void onError(int code, String msg) {
              //  ToastUtils.show(mContext, msg);
                llytNoData.setVisibility(View.VISIBLE);
                rlvList.setVisibility(View.GONE);
                refreshLayout.finishRefresh(true);
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });

    }
}
