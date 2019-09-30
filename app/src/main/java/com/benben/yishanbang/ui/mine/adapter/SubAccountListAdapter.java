package com.benben.yishanbang.ui.mine.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.ui.mine.activity.UpdateSubAccountActivity;
import com.benben.yishanbang.ui.mine.bean.SubAccountListBean;
import com.benben.yishanbang.ui.mine.bean.SubAccountPermissionDesListBean;
import com.benben.yishanbang.widget.FlowLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:子账号adapter
 */
public class SubAccountListAdapter extends AFinalRecyclerViewAdapter<SubAccountListBean> {

    Activity mActivity;

    public SubAccountListAdapter(Context ctx) {
        super(ctx);
        mActivity = (Activity) ctx;
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_subaccount_manage_list, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_edit)
        ImageView ivEdit;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.rlv_list)
        RecyclerView rlvList;
        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(SubAccountListBean mSubAccountListBean, int position) {
            tvName.setText(mSubAccountListBean.getNickName());
            tvPhone.setText(mSubAccountListBean.getIphone());
            String control = mSubAccountListBean.getControl();
            String[] split = control.split(",");

            FlowLayoutManager mFlowLayoutManager = new FlowLayoutManager();
            rlvList.setLayoutManager(mFlowLayoutManager);
            SubAccountPermissionDesAdapter subAccountPermissionDesAdapter = new SubAccountPermissionDesAdapter(m_Context);
            rlvList.setAdapter(subAccountPermissionDesAdapter);
            ArrayList<SubAccountPermissionDesListBean> desListBeans = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                if ("1".equals(split[i])) {
                    desListBeans.add(new SubAccountPermissionDesListBean("店铺管理"));
                } else if ("2".equals(split[i])) {
                    desListBeans.add(new SubAccountPermissionDesListBean("优惠卡查看"));
                } else if ("3".equals(split[i])) {
                    desListBeans.add(new SubAccountPermissionDesListBean("订单管理"));
                } else if ("4".equals(split[i])) {
                    desListBeans.add(new SubAccountPermissionDesListBean("店铺管理"));
                } else if ("5".equals(split[i])) {
                    desListBeans.add(new SubAccountPermissionDesListBean("数据报表"));
                } else if ("6".equals(split[i])) {
                    desListBeans.add(new SubAccountPermissionDesListBean("核销订单"));
                }

            }
           /* desListBeans.add(new SubAccountPermissionDesListBean("店铺管理"));
            desListBeans.add(new SubAccountPermissionDesListBean("优惠卡查看"));
            desListBeans.add(new SubAccountPermissionDesListBean("订单管理"));
            desListBeans.add(new SubAccountPermissionDesListBean("店铺管理"));
            desListBeans.add(new SubAccountPermissionDesListBean("数据报表"));
            desListBeans.add(new SubAccountPermissionDesListBean("核销订单"));*/
            subAccountPermissionDesAdapter.refreshList(desListBeans);

            ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mActivity, UpdateSubAccountActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 1);
                    intent.putExtra("phone", mSubAccountListBean.getIphone());
                    intent.putExtra(Constants.EXTRA_KEY_USER_ID, mSubAccountListBean.getUserId());
                    intent.putExtra("control", mSubAccountListBean.getControl());
                    mActivity.startActivity(intent);

                }
            });


        }
    }
}