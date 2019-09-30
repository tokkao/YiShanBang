package com.benben.yishanbang.ui.mine.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.ui.NormalWebViewActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: 服务管理
 */
public class ServiceManageActivity extends BaseActivity {

    private static final String TAG = "ServiceManageActivity";
    @BindView(R.id.center_title)
    TextView centerTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_manage;
    }

    @Override
    protected void initData() {
        centerTitle.setText("服务管理");
    }

    @OnClick({R.id.rl_back, R.id.tv_my_skills, R.id.tv_release_order, R.id.tv_accept_order, R.id.tv_release_task, R.id.tv_accept_task, R.id.tv_make_money})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.tv_my_skills://我的技能
                startActivity(new Intent(mContext,MySkillListActivity.class));
                break;
            case R.id.tv_release_order://发布的家政订单
                startActivity(new Intent(mContext,MyServicesActivity.class).putExtra("enter_type",0));
                break;
            case R.id.tv_accept_order://接受的家政订单
                startActivity(new Intent(mContext,MyServicesActivity.class).putExtra("enter_type",1));
                break;
            case R.id.tv_release_task://发布的任务
                startActivity(new Intent(mContext,MyServicesActivity.class).putExtra("enter_type",2));
                break;
            case R.id.tv_accept_task://接受的任务
                startActivity(new Intent(mContext,MyServicesActivity.class).putExtra("enter_type",3));
                break;
            case R.id.tv_make_money://赚钱攻略
                NormalWebViewActivity.startWithData(mContext,"http://ysbh5.zjxtaq.com/gonglue.html","赚钱攻略",true,false);
                break;
        }
    }
}
