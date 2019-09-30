package com.benben.yishanbang.ui.mine.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;

import butterknife.BindView;
import butterknife.OnClick;

import static com.benben.yishanbang.config.Constants.ENTRY_TYPE_WINDOW_CUSTOMER_MANAGE;
import static com.benben.yishanbang.config.Constants.ENTRY_TYPE_WINDOW_ORDER_MANAGE;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: 橱窗管理
 */
public class WindowManageActivity extends BaseActivity {

    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.tv_my_window)
    TextView tvMyWindow;
    @BindView(R.id.tv_order_manage)
    TextView tvOrderManage;
    @BindView(R.id.tv_data_report)
    TextView tvDataReport;
    @BindView(R.id.tv_user_manage)
    TextView tvUserManage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_window_manage;
    }

    @Override
    protected void initData() {
        centerTitle.setText("橱窗管理");
    }


    @OnClick({R.id.rl_back, R.id.tv_my_window, R.id.tv_order_manage, R.id.tv_data_report, R.id.tv_user_manage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.tv_my_window://我的橱窗
                startActivity(new Intent(mContext, GoodsManageActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 0));
                break;
            case R.id.tv_order_manage://订单管理
                startActivity(new Intent(mContext, OrderManageWindowActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, ENTRY_TYPE_WINDOW_ORDER_MANAGE));
                break;
            case R.id.tv_data_report://数据报表
                startActivity(new Intent(mContext, GoodsManageActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 1));
                break;
            case R.id.tv_user_manage://客户管理
                startActivity(new Intent(mContext, OrderManageWindowActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, ENTRY_TYPE_WINDOW_CUSTOMER_MANAGE));
                break;
        }
    }
}
