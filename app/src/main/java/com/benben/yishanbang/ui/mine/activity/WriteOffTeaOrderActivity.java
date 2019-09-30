package com.benben.yishanbang.ui.mine.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.home.activity.QRCodeActivity;
import com.benben.yishanbang.ui.mine.adapter.WriteOffTeaOrderListAdapter;
import com.benben.yishanbang.ui.mine.bean.WriteOffTeaOrderListBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: //核销奶茶订单
 */
public class WriteOffTeaOrderActivity extends BaseActivity {
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.right_title)
    TextView rightTitle;

    private WriteOffTeaOrderListAdapter mAdapter;
    private int mPage = 1;
    private int mPageSize = 15;
    private boolean isRefresh = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_lottery;
    }

    @Override
    protected void initData() {
        centerTitle.setText("核销订单");
        rightTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.icon_scan_qrcode), null);
        initRefreshLayout();

        getDataList();

        mAdapter = new WriteOffTeaOrderListAdapter(mContext);
        rlvList.setAdapter(mAdapter);
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));


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
            mPage++;
            getDataList();
        });
    }

    private void getDataList() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.AFTER_ORDER)
                .post()
                .addParam("type", 2)//参数  type 传2 优惠卡列表 传3 奶茶列表
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<WriteOffTeaOrderListBean> list = JSONUtils.jsonString2Beans(json, WriteOffTeaOrderListBean.class);
                mAdapter.refreshList(list);

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
                toast(msg);
                llytNoData.setVisibility(View.GONE);
                rlvList.setVisibility(View.VISIBLE);
                refreshLayout.finishRefresh(true);
                refreshLayout.finishLoadMore(true);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                llytNoData.setVisibility(View.GONE);
                rlvList.setVisibility(View.VISIBLE);
                refreshLayout.finishRefresh(false);
                refreshLayout.finishLoadMore(false);

            }
        });

    }


    @OnClick({R.id.rl_back, R.id.right_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.right_title://扫一扫
                MyApplication.openActivity(mContext, QRCodeActivity.class);
                break;
        }
    }
}
