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
import com.benben.yishanbang.ui.mine.activity.MyCouponCardDetailsActivity;
import com.benben.yishanbang.ui.mine.adapter.MyCouponCardListAdapter;
import com.benben.yishanbang.ui.mine.bean.WalletCouponCardBean;
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
 * Describe: 钱包 - 我的优惠卡
 */
public class MyCouponCardFragment extends LazyBaseFragments {
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private MyCouponCardListAdapter mAdapter;
    private int mPage = 1;
    private int mPageSize = 15;
    private boolean isRefresh = true;
    //优惠卡类型
    private String mCardType;

    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.frag_shopping_order, null);
        return mRootView;
    }

    @Override
    public void initView() {
        mCardType = getArguments().getString("mCouponCardType");
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
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new MyCouponCardListAdapter(mContext);
        rlvList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<WalletCouponCardBean>() {
            @Override
            public void onItemClick(View view, int position, WalletCouponCardBean model) {
                Intent intent = new Intent(mContext, MyCouponCardDetailsActivity.class);
                intent.putExtra("img_url", model.getImgUrl());
                intent.putExtra("card_name", model.getName());
                intent.putExtra("shop_name", model.getShopName());
                intent.putExtra("card_price", model.getPrice() + "");
                intent.putExtra("qr_code_img", model.getCodeImage());
                intent.putExtra(Constants.EXTRA_KEY_COMMON_TYPE, mCardType);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, WalletCouponCardBean model) {

            }
        });
    }

    @Override
    public void initData() {
        getDataList();
    }

    //获取服务列表
    private void getDataList() {

        BaseOkHttpClient.newBuilder()
                .addParam("goodsCategoryId", mCardType)//商店种类id
                .url(NetUrlUtils.WALLET_QUERY_MY_CARD_LIST)
                .get()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<WalletCouponCardBean> walletCouponCardBeans = JSONUtils.jsonString2Beans(json, WalletCouponCardBean.class);
                mAdapter.refreshList(walletCouponCardBeans);

                if (mAdapter.getItemCount() <= 0) {
                    llytNoData.setVisibility(View.VISIBLE);
                    rlvList.setVisibility(View.GONE);
                } else {
                    llytNoData.setVisibility(View.GONE);
                    rlvList.setVisibility(View.VISIBLE);
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
                refreshLayout.finishRefresh(false);
                refreshLayout.finishLoadMore(false);
            }
        });


    }
}
