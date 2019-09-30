package com.benben.yishanbang.ui.home.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.LogUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.home.adapter.SystemMessageListAdapter;
import com.benben.yishanbang.ui.home.bean.MessageListBean;
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
 * Author:zhn
 * Time:2019/5/13 0013 16:50
 * <p>
 * 系统消息页面
 */
public class MessageSystemActivity extends BaseActivity {

    private static final String TAG = "MessageSystemActivity";
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;


    //是否正在刷新
    private boolean mIsRefreshing = false;
    //是否正在加载更多
    private boolean mIsLoadMore = false;

    //页号，默认是1
    private int pageIndex = 1;
    //消息类型,默认是0
    private int msgType = 0;

    //消息列表
    private SystemMessageListAdapter mSystemMessageListAdapter;

    @Override
    protected void initData() {
        centerTitle.setText("系统消息");
        //初始化下拉刷新
        initRefreshLayout();
        //初始化消息列表
        initMessageList();
        //获取消息列表
        getMessageList();
    }


    private void initMessageList() {
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mSystemMessageListAdapter = new SystemMessageListAdapter(mContext);
        rlvList.setAdapter(mSystemMessageListAdapter);
    }

    private void initRefreshLayout() {
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mIsRefreshing = true;
                pageIndex = 1;
                getMessageList();
            }
        });
        refreshLayout.setEnableLoadMore(false);
        //上拉加载
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex++;
                mIsLoadMore = true;
                getMessageList();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_lottery;
    }

    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        onBackPressed();
    }

    private void getMessageList() {

        BaseOkHttpClient.newBuilder()
                .addParam("type", 1)//1 未读 2 已读
                .url(NetUrlUtils.SYSTEM_MSG_LIST)
                .post().build()
                .enqueue(mContext, new BaseCallBack<String>() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        //获取数据
                        MessageListBean bean = JSONUtils.jsonString2Bean(json, MessageListBean.class);

                        List<MessageListBean.AnnouncementListBean> lstMsg = bean.getAnnouncementList();
                        if (mIsRefreshing) {
                            mIsRefreshing = false;
                            refreshLayout.finishRefresh();
                            //清空列表
                            mSystemMessageListAdapter.clear();
                        }
                        if (mIsLoadMore) {
                            mIsLoadMore = false;
                            //判断是否加载完成
                            if (lstMsg == null || lstMsg.size() == 0) {
                                refreshLayout.finishLoadMoreWithNoMoreData();
                            } else {
                                refreshLayout.finishLoadMore();
                            }
                        }

                        if (lstMsg != null) {
                            mSystemMessageListAdapter.appendToList(lstMsg);
                        }

                        if (mSystemMessageListAdapter.getItemCount() == 0) {
                            llytNoData.setVisibility(View.VISIBLE);
                            rlvList.setVisibility(View.GONE);
                        } else {
                            llytNoData.setVisibility(View.GONE);
                            rlvList.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogUtils.e(TAG, msg);
                        toast(msg);
                        if (mIsRefreshing) {
                            mIsRefreshing = false;
                            refreshLayout.finishRefresh(false);
                        }
                        if (mIsLoadMore) {
                            mIsLoadMore = false;
                            refreshLayout.finishLoadMore(false);
                        }
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtils.e(TAG, e.getLocalizedMessage());
                        if (mIsRefreshing) {
                            mIsRefreshing = false;
                            refreshLayout.finishRefresh(false);
                        }
                        if (mIsLoadMore) {
                            mIsLoadMore = false;
                            refreshLayout.finishLoadMore(false);
                        }
                    }
                });
    }

}
