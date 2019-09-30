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
import com.benben.yishanbang.ui.mine.activity.ServicesDetailsActivity;
import com.benben.yishanbang.ui.mine.adapter.MyServicesListAdapter;
import com.benben.yishanbang.ui.mine.bean.MyServicesListBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.Call;

import static com.benben.yishanbang.config.Constants.DATA_KEY;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: 我的服务
 */
public class MyServicesFragment extends LazyBaseFragments {
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private MyServicesListAdapter mAdapter;
    private int mPage = 1;
    private int mPageSize = 15;
    private boolean isRefresh = true;
    //服务类型
    private int mServicesType;

    private String mType;
    private String mOrderType;
    private String mTakeType;

    public static final String KEY_TYPE = "key_type";//查询类型
    public static final String KEY_TYPE_ORDER = "key_type_order";//服务类型
    public static final String KEY_TYPE_TAKE = "key_type_take";//  1我发布的服务2我接受的服务

    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.frag_shopping_order, null);
        return mRootView;
    }

    @Override
    public void initView() {
        mServicesType = getArguments().getInt("ServicesType");
        mType = getArguments().getString(KEY_TYPE);
        mOrderType = getArguments().getString(KEY_TYPE_ORDER);
        mTakeType = getArguments().getString(KEY_TYPE_TAKE);

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
        mAdapter = new MyServicesListAdapter(mContext);
        rlvList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<MyServicesListBean.OrderServerBean>() {
            @Override
            public void onItemClick(View view, int position, MyServicesListBean.OrderServerBean model) {
                Intent intent = new Intent(mContext, ServicesDetailsActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_COMMON_TYPE, mTakeType);
                intent.putExtra(DATA_KEY, model);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, MyServicesListBean.OrderServerBean model) {

            }
        });
    }

    @Override
    public void initData() {
        getDataList();
    }

    //获取服务列表
    private void getDataList() {
//        ArrayList<MyServicesListBean.OrderServerBean> shoppingOrderListBeans = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            MyServicesListBean.OrderServerBean bean = new MyServicesListBean.OrderServerBean();
//            bean.setId("1231");
//            bean.setTakeType(mTakeType);
//            bean.setStatus(i % 4 + "");
//            shoppingOrderListBeans.add(bean);
//        }

//        mAdapter.refreshList(shoppingOrderListBeans);
//        llytNoData.setVisibility(View.GONE);
//        rlvList.setVisibility(View.VISIBLE);
        BaseOkHttpClient.newBuilder()
                .addParam("type", mType)//查询类型1全部2未接单3进行中4已完成
                .addParam("orderType", mOrderType)//服务类型1社交2家政
                .addParam("takeType", mTakeType)//  1我发布的服务2我接受的服务
                .post()
                .url(NetUrlUtils.SERVER_ORDER_LIST_)
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                refreshLayout.finishRefresh(true);
                refreshLayout.finishLoadMore(true);
                MyServicesListBean myServicesListBean = JSONUtils.jsonString2Bean(s, MyServicesListBean.class);
                if (myServicesListBean != null && myServicesListBean.getOrderServer() != null && myServicesListBean.getUser() != null) {
                    for (int i = 0; i < myServicesListBean.getOrderServer().size(); i++) {
                        if (!myServicesListBean.getOrderServer().get(i).equals("1")) {
                            myServicesListBean.getOrderServer().get(i).setTakeType(myServicesListBean.getUser().get(i).getTakeType());
                            myServicesListBean.getOrderServer().get(i).setNickname(myServicesListBean.getUser().get(i).getNickname());
                            myServicesListBean.getOrderServer().get(i).setMobile(myServicesListBean.getUser().get(i).getMobile());
                        }
                    }
                    if (isRefresh) {
                        mAdapter.refreshList(myServicesListBean.getOrderServer());
                    } else {
                        mAdapter.appendToList(myServicesListBean.getOrderServer());
                    }
                    llytNoData.setVisibility(View.GONE);
                    rlvList.setVisibility(View.VISIBLE);
                } else {
                    llytNoData.setVisibility(View.VISIBLE);
                    rlvList.setVisibility(View.GONE);
                }

            }

            @Override
            public void onError(int code, String msg) {
                refreshLayout.finishRefresh(true);
                refreshLayout.finishLoadMore(true);
                llytNoData.setVisibility(View.VISIBLE);
                rlvList.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }
}
