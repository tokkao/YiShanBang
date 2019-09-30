package com.benben.yishanbang.ui.mine.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.LazyBaseFragments;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.discount.activity.DiscountGoodsDetailActivity;
import com.benben.yishanbang.ui.mine.adapter.MyCollectionGoodsListAdapter;
import com.benben.yishanbang.ui.mine.bean.CollectionGoodsListBean;
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
 * Describe: 我收藏的商品页面
 */
public class MyCollectionGoodsFragment extends LazyBaseFragments {

    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private MyCollectionGoodsListAdapter mAdapter;
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
                getGoodsList();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRefresh = false;
                mPage++;
                getGoodsList();
            }
        });
        rlvList.setLayoutManager(new GridLayoutManager(mContext, 2));
        mAdapter = new MyCollectionGoodsListAdapter(mContext);
        rlvList.setAdapter(mAdapter);

        //取消收藏
        mAdapter.setOnDeleteListener((view, position, model) -> showDeleteDialog(model));
        //item  点击
        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<CollectionGoodsListBean>() {
            @Override
            public void onItemClick(View view, int position, CollectionGoodsListBean model) {
                Bundle bundle = new Bundle();
                bundle.putString("goods_id", model.getId());
                bundle.putString("sale_count", String.valueOf(model.getNumber()));
                bundle.putString("question_type", model.getQuestionType());
                MyApplication.openActivity(mContext, DiscountGoodsDetailActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position, CollectionGoodsListBean model) {

            }
        });
    }

    //取消收藏弹窗
    private void showDeleteDialog(CollectionGoodsListBean model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = View.inflate(mContext, R.layout.dialog_confirm_uncollection, null);
        AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tvConfirm = view.findViewById(R.id.tv_confirm);
        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                cancelCollection(model);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    //取消收藏
    private void cancelCollection(CollectionGoodsListBean model) {
        BaseOkHttpClient.newBuilder()
                .addParam("id", model.getId())
                .url(NetUrlUtils.DELETE_COLLECTION_GOODS)
                .get()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtils.show(mContext, msg);
                getGoodsList();
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

    @Override
    public void initData() {
        getGoodsList();
    }

    //获取收藏的商品
    private void getGoodsList() {

        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.COLLECTION_GOODS_LIST)
                //  .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<CollectionGoodsListBean> listBeans = JSONUtils.jsonString2Beans(json, CollectionGoodsListBean.class);
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
                //ToastUtils.show(mContext, msg);
                llytNoData.setVisibility(View.VISIBLE);
                rlvList.setVisibility(View.GONE);
                refreshLayout.finishRefresh(true);
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });


        // refreshLayout.finishLoadMore(true);
    }
}
