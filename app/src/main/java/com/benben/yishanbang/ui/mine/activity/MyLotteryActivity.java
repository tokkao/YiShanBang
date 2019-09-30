package com.benben.yishanbang.ui.mine.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.mine.adapter.MyLotteryListAdapter;
import com.benben.yishanbang.ui.mine.bean.MyLotteryListBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: //我的抽奖
 */
public class MyLotteryActivity extends BaseActivity {
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private MyLotteryListAdapter mAdapter;
    private int mPage = 1;
    private int mPageSize = 15;
    private boolean isRefresh = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_lottery;
    }

    @Override
    protected void initData() {
        centerTitle.setText("我的抽奖");

        initRefreshLayout();
        mAdapter = new MyLotteryListAdapter(mContext);
        rlvList.setAdapter(mAdapter);
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));

        getLotteryList();

        /**
         * 条目点击跳转到抽奖详情
         */
        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<MyLotteryListBean>() {
            @Override
            public void onItemClick(View view, int position, MyLotteryListBean model) {
//                startActivity(new Intent());
            }

            @Override
            public void onItemLongClick(View view, int position, MyLotteryListBean model) {

            }
        });
    }

    private void initRefreshLayout() {
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            isRefresh = true;
            mPage = 1;
            getLotteryList();
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            isRefresh = false;
            mPage++;
            getLotteryList();
        });
    }

    private void getLotteryList() {

        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.USER_DRAW)
                .get()
                .build()
                .enqueue(mContext, new BaseCallBack<String>() {
                    @Override
                    public void onSuccess(String json, String msg) {

                        List<MyLotteryListBean> myLotteryListBeans = JSONUtils.jsonString2Beans(json, MyLotteryListBean.class);
                        mAdapter.refreshList(myLotteryListBeans);
                        if (mAdapter.getItemCount() > 0) {
                            rlvList.setVisibility(View.VISIBLE);
                            llytNoData.setVisibility(View.GONE);
                        } else {
                            rlvList.setVisibility(View.GONE);
                            llytNoData.setVisibility(View.VISIBLE);
                        }

                        refreshLayout.finishRefresh(true);

                    }

                    @Override
                    public void onError(int code, String msg) {
                        toast(msg);
                        llytNoData.setVisibility(View.VISIBLE);
                        rlvList.setVisibility(View.GONE);
                        refreshLayout.finishRefresh(true);

                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        refreshLayout.finishRefresh(false);
                    }
                });
    }

    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
