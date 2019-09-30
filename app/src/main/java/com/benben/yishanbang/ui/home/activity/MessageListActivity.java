package com.benben.yishanbang.ui.home.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.widget.CircleImageView;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.home.adapter.MessageListAdapter;
import com.benben.yishanbang.ui.home.bean.MessageListBean;
import com.benben.yishanbang.ui.service.activity.ChatActivity;
import com.benben.yishanbang.ui.service.activity.VentureShopActivity;
import com.kongzue.dialog.v3.WaitDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;
import jiguang.chat.application.JGApplication;
import jiguang.chat.utils.SortConvList;
import jiguang.chat.utils.SortTopConvList;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/7
 * Time: 14:12
 * 消息列表
 */
public class MessageListActivity extends BaseActivity {

    private static final String TAG = "MessageListActivity";
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.rlv_list)
    RecyclerView rlvMessageList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.civ_message_avatar)
    CircleImageView civMessageAvatar;
    @BindView(R.id.tv_message_title)
    TextView tvMessageTitle;
    @BindView(R.id.tv_message_info)
    TextView tvMessageInfo;
    @BindView(R.id.tv_message_count)
    TextView tvMessageCount;
    @BindView(R.id.rlyt_system_message)
    RelativeLayout rlytSystemMessage;
    //消息列表adapter
    private MessageListAdapter mMessageListAdapter;
    //消息列表
    private List<MessageListBean> mMessageInfoList = new ArrayList<>();
    private String mTargetId;
   // private RxBus mRxBus;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_list;
    }

    @Override
    protected void initData() {
        initTitle("消息");
        initRefreshLayout();
        //初始化消息列表
        initMessageList();
        buildData();
//        mRxBus = RxBus.getInstance();
//        mRxBus.toObservable(String.class)
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        LogUtils.e(TAG, "messageList  s= " + s);
//                        if ("recharge".equals(s)) {
//                            startActivity(new Intent(mContext, RechargeIMActivity.class));
//                        } else if ("venture_shop".equals(s)) {
//                            getShopId();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }

    //根据极光账号的手机号 查找店铺id
    private void getShopId() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.QUERY_SHOP_BY_MOBILE)
                .addParam("mobile", mTargetId)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                startActivity(new Intent(mContext, VentureShopActivity.class).putExtra("shop_id", s));
            }

            @Override
            public void onError(int code, String msg) {
                toast(msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });
    }

    private void initRefreshLayout() {
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                buildData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    //初始化消息列表
    private void initMessageList() {
        rlvMessageList.setLayoutManager(new LinearLayoutManager(mContext));
        mMessageListAdapter = new MessageListAdapter(mContext);
        rlvMessageList.setAdapter(mMessageListAdapter);
        mMessageListAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<Conversation>() {
            @Override
            public void onItemClick(View view, int position, Conversation conv) {
                //  if (position == 0) {
                //    startActivity(new Intent(mContext, MessageSystemActivity.class));
                //    } else {
                //开始聊天
                //点击会话条目
                Intent intent = new Intent();
                intent.putExtra(JGApplication.CONV_TITLE, conv.getTitle());
                mTargetId = ((UserInfo) conv.getTargetInfo()).getUserName();
                intent.putExtra(JGApplication.TARGET_ID, mTargetId);
                intent.putExtra(JGApplication.TARGET_APP_KEY, conv.getTargetAppKey());
                intent.setClass(mContext, ChatActivity.class);
                startActivity(intent);
                // }
            }

            @Override
            public void onItemLongClick(View view, int position, Conversation model) {

            }
        });


    }

    //获取系统消息列表
    private void getSystemMsg() {


    }

    private void buildData() {

        WaitDialog.show((AppCompatActivity) mContext, "");
        List<Conversation> topConv = new ArrayList<>();
        List<Conversation> forCurrent = new ArrayList<>();
        List<Conversation> delFeedBack = new ArrayList<>();
        List<Conversation> mDatas = JMessageClient.getConversationList();
        if (mDatas != null && mDatas.size() > 0) {
            Collections.sort(mDatas, new SortConvList());
            for (Conversation con : mDatas) {
                //如果会话有聊天室会话就把这会话删除
                if (con.getTargetId().equals("feedback_Android") || con.getType().equals(ConversationType.chatroom)) {
                    delFeedBack.add(con);
                }
                if (!TextUtils.isEmpty(con.getExtra())) {
                    forCurrent.add(con);
                }
            }
            topConv.addAll(forCurrent);
            mDatas.removeAll(forCurrent);
            mDatas.removeAll(delFeedBack);
        }
        if (topConv.size() > 0) {
            Collections.sort(topConv, new SortTopConvList());
            int i = 0;
            for (Conversation conv : topConv) {
                mDatas.add(i, conv);
                i++;
            }
        }
        WaitDialog.dismiss();
        refreshLayout.finishRefresh();

     /*   //初始化列表
        MessageListBean articleBean = new MessageListBean();
        articleBean.setArticle_title("系统消息");
        articleBean.setUnreadMessageCount(2);
        articleBean.setArticle_img(String.valueOf(R.mipmap.icon_system_message));
        articleBean.setArticle_description("测试消息~~~");
        articleBean.setArticle_time("");
        mMessageInfoList.add(articleBean);
        MessageListBean customServiceBean = new MessageListBean();
        customServiceBean.setArticle_title("客服");
        customServiceBean.setUnreadMessageCount(10);
        customServiceBean.setArticle_img(String.valueOf(R.mipmap.icon_customer_service));
        customServiceBean.setArticle_description("测试消息******");
        customServiceBean.setArticle_time("");
        mMessageInfoList.add(customServiceBean);
        */
        if (mDatas != null && !mDatas.isEmpty()) {
            rlvMessageList.setVisibility(View.VISIBLE);
            mMessageListAdapter.refreshList(mDatas);
        } else {
            rlvMessageList.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.rl_back, R.id.rlyt_system_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.rlyt_system_message:
                startActivity(new Intent(mContext, MessageSystemActivity.class));
                break;
        }
    }
}
