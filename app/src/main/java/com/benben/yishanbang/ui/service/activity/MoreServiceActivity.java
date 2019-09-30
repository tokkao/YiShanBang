package com.benben.yishanbang.ui.service.activity;

import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.service.adapter.ServeTypeListAdapter;
import com.benben.yishanbang.ui.service.adapter.ServiceTabListAdapter;
import com.benben.yishanbang.ui.service.bean.ServeTypeInfoBean;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

//更多服务
public class MoreServiceActivity extends BaseActivity {
    private static final String TAG = "MoreServiceActivity";
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    private ServeTypeListAdapter mAdapter;
    private ServiceTabListAdapter mTabListAdapter;
    //判读是否是recyclerView主动引起的滑动，true- 是，false- 否，由tablayout引起的
    private boolean isRecyclerScroll;
    //记录上一次位置，防止在同一内容块里滑动 重复定位到tablayout
    private int mLastPos;
    //用于recyclerView滑动到指定的位置
    private boolean isCanScroll;
    private int mScrollToPosition;
    private LinearLayoutManager mLayoutManager;
    private int mEnterType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_more_service;
    }

    @Override
    protected void initData() {

        centerTitle.setText("全部分类");
        mEnterType = getIntent().getIntExtra(Constants.EXTRA_KEY_ENTER_TYPE, 1);
        mLayoutManager = new LinearLayoutManager(mContext);
        rlvList.setLayoutManager(mLayoutManager);
        mAdapter = new ServeTypeListAdapter(mContext, mEnterType == 0);
        rlvList.setAdapter(mAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //点击标签，使recyclerView滑动，isRecyclerScroll置false
                int pos = tab.getPosition();
                isRecyclerScroll = false;
                moveToPosition(mLayoutManager, rlvList, pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        rlvList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //当滑动由recyclerView触发时，isRecyclerScroll 置true
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    isRecyclerScroll = true;
                }
                return false;
            }
        });

        rlvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isCanScroll) {
                    isCanScroll = false;
                    moveToPosition(mLayoutManager, recyclerView, mScrollToPosition);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isRecyclerScroll) {
                    //第一个可见的view的位置，即tablayou需定位的位置
                    int position = mLayoutManager.findFirstVisibleItemPosition();
                    if (mLastPos != position) {
                        tabLayout.setScrollPosition(position, 0, true);
                    }
                    mLastPos = position;
                }
            }
        });

        getDataList();
    }

    private void getDataList() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.SERVE_ALL_CLASS)
                .json()
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                List<ServeTypeInfoBean> list = JSONUtils.jsonString2Beans(s, ServeTypeInfoBean.class);
                if (list != null) {

                    //tablayout设置标签
                    for (int i = 0; i < list.size(); i++) {
                        tabLayout.addTab(tabLayout.newTab().setText(list.get(i).getName()));
                    }

                    mAdapter.refreshList(list);
                }

            }

            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    @OnClick({R.id.rl_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;

        }
    }

    //滑动到指定位置
    public void moveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int position) {
        // 第一个可见的view的位置
        int firstItem = manager.findFirstVisibleItemPosition();
        // 最后一个可见的view的位置
        int lastItem = manager.findLastVisibleItemPosition();
        if (position <= firstItem) {
            // 如果跳转位置firstItem 之前(滑出屏幕的情况)，就smoothScrollToPosition可以直接跳转，
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 跳转位置在firstItem 之后，lastItem 之间（显示在当前屏幕），smoothScrollBy来滑动到指定位置
            int top = mRecyclerView.getChildAt(position - firstItem).getTop();
            mRecyclerView.smoothScrollBy(0, top);
        } else {
            // 如果要跳转的位置在lastItem 之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用当前moveToPosition方法，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
            mScrollToPosition = position;
            isCanScroll = true;
        }
    }

}
