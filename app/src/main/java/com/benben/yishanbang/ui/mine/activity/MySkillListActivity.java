package com.benben.yishanbang.ui.mine.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.mine.adapter.MySkillListAdapter;
import com.benben.yishanbang.ui.tea.bean.UpdateSkillBean;
import com.kongzue.dialog.v3.CustomDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: //我的技能
 */
public class MySkillListActivity extends BaseActivity {
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

    private List<UpdateSkillBean> mSkillList;

    private MySkillListAdapter mAdapter;

    private int mPage = 1;
    private int mPageSize = 15;
    private boolean isRefresh = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_lottery;
    }

    @Override
    protected void initData() {
        centerTitle.setText("我的技能");
        rightTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.icon_add_goods), null);
        rightTitle.setPadding(100, 0, 0, 0);
        initRefreshLayout();

        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new MySkillListAdapter(mContext);
        rlvList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<UpdateSkillBean>() {
            @Override
            public void onItemClick(View view, int position, UpdateSkillBean model) {
                //对于未实例化的布局
                CustomDialog.show(MySkillListActivity.this, R.layout.dialog_confirm_window, new CustomDialog.OnBindView() {
                    @Override
                    public void onBind(final CustomDialog dialog, View v) {
                        TextView tv_right_ok = v.findViewById(R.id.tv_right_ok);
                        TextView tv_left_cancel = v.findViewById(R.id.tv_left_cancel);

                        //确定
                        tv_right_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                skillDelete(model.getId());
                                dialog.doDismiss();
                            }
                        });
                        //取消
                        tv_left_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.doDismiss();
                            }
                        });
                    }
                });
            }

            @Override
            public void onItemLongClick(View view, int position, UpdateSkillBean model) {

            }
        });
    }

    /**
     * 技能删除
     *
     * @param id
     */
    private void skillDelete(String id) {
        BaseOkHttpClient.newBuilder()
                .addParam("token", MyApplication.mPreferenceProvider.getToken())
                .addParam("id", id)
                .url(NetUrlUtils.SKILL_DEL)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                getSkillList();
                toast(msg);
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

    @Override
    protected void onResume() {
        super.onResume();
        getSkillList();
    }

    private void initRefreshLayout() {
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            isRefresh = true;
            mPage = 1;
            getSkillList();
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            isRefresh = false;
            mPage++;
            getSkillList();
        });
    }

    private void getSkillList() {

        BaseOkHttpClient.newBuilder()
                .addParam("token", MyApplication.mPreferenceProvider.getToken())
                .url(NetUrlUtils.GET_USER_SKILL_LIST)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                refreshLayout.finishRefresh(true);
                refreshLayout.finishLoadMore(true);
                mSkillList = JSONUtils.jsonString2Beans(json, UpdateSkillBean.class);

                if (mSkillList != null && mSkillList.size() > 0) {
                    llytNoData.setVisibility(View.GONE);
                    rlvList.setVisibility(View.VISIBLE);
                    mAdapter.refreshList(mSkillList);
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
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });
    }

    @OnClick({R.id.rl_back, R.id.right_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.right_title://添加
                startActivity(new Intent(mContext, AddSkillActivity.class));
                break;
        }
    }
}
