package com.benben.yishanbang.ui.tea;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.LazyBaseFragments;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.discount.activity.SearchActivity;
import com.benben.yishanbang.ui.discount.activity.SelectAreaActivity;
import com.benben.yishanbang.ui.tea.activity.StoresDetailsActivity;
import com.benben.yishanbang.ui.tea.adapter.TeaShopListAdapter;
import com.benben.yishanbang.ui.tea.bean.TeaBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/5
 * Time: 9:18
 * 奶茶
 */
public class TeaFragment extends LazyBaseFragments {

    private static final String TAG = "TeaFragment";

    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.rlv_tea)
    RecyclerView rlvShopList;
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private TeaShopListAdapter mTeaAdapter;//列表适配器
    private int mPage = 1;
    private int mPageSize = 10;
    private boolean isRefresh = true;

    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.frag_tea, null);
        return mRootView;
    }

    @Override
    public void initView() {
        tvCity.setText(MyApplication.mPreferenceProvider.getCity());

        rlvShopList.setLayoutManager(new LinearLayoutManager(mContext));
        mTeaAdapter = new TeaShopListAdapter(mContext);
        rlvShopList.setAdapter(mTeaAdapter);
        initRefreshLayout();

        //适配器条目点击跳转监听
        mTeaAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<TeaBean>() {
            @Override
            public void onItemClick(View view, int position, TeaBean model) {
                //点击跳转
                Intent intent = new Intent(mContext, StoresDetailsActivity.class);
                intent.putExtra("shopid", model.getShopId());
                mContext.startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, TeaBean model) {

            }
        });
    }

    private void initRefreshLayout() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = true;
                mPage = 1;
                getShopList();
            }
        });
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRefresh = false;
                mPage++;
                getShopList();
            }
        });
    }

    @Override
    public void initData() {
        getShopList();
    }

    @OnClick({R.id.tv_city, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_city://切换城市
                Intent intent1 = new Intent(mContext, SelectAreaActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_search: //点击搜索
                Intent intent = new Intent(mContext, SearchActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 2);
                startActivity(intent);
                break;
        }
    }

    //获取店铺列表
    private void getShopList() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.STORES_LIST)
//                .addParam("province","河南省")//没有这个参数
//                .addParam("longitude", "113.64964385")
//                .addParam("latitude", "34.75661006")
//                .addParam("city", "郑州市")
//                //.addParam("sign", 1)//1 距离  2销量
               // .addParam("province", MyApplication.mPreferenceProvider.getProvince())//没有这个参数
                .addParam("longitude", MyApplication.mPreferenceProvider.getLongitude())
                .addParam("latitude", MyApplication.mPreferenceProvider.getLatitude())
                .addParam("city", MyApplication.mPreferenceProvider.getCity())
                //.addParam("sign", 1)//1 距离  2销量
               // .addParam("county", MyApplication.mPreferenceProvider.getDistrict())
              //  .addParam("shopPlace", )//搜索时传地址
               // .addParam("pageNo", mPage)//页数
                //.addParam("pageSize", mPageSize)//数量
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<TeaBean> teaBeans = JSONUtils.jsonString2Beans(json, TeaBean.class);
                if (isRefresh) {
                    mTeaAdapter.refreshList(teaBeans);
                } else {
                    mTeaAdapter.appendToList(teaBeans);
                }

                if (mTeaAdapter.getItemCount() > 0) {
                    llytNoData.setVisibility(View.GONE);
                    rlvShopList.setVisibility(View.VISIBLE);
                } else {
                    llytNoData.setVisibility(View.VISIBLE);
                    rlvShopList.setVisibility(View.GONE);
                }

                refreshLayout.finishRefresh(true);
                refreshLayout.finishLoadMore(true);
            }

            @Override
            public void onError(int code, String msg) {
                ToastUtils.show(mContext,msg);

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
