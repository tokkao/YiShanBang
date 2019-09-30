package com.benben.yishanbang.ui.mine.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.mine.bean.MyServicesListBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:我的服务列表adapter
 */
public class MyServicesListAdapter extends AFinalRecyclerViewAdapter<MyServicesListBean.OrderServerBean> {

    public MyServicesListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_my_services_list, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_des)
        TextView tvDes;
        @BindView(R.id.tv_location)
        TextView tvLocation;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_action)
        TextView tvAction;
        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(MyServicesListBean.OrderServerBean bean, int position) {

            tvName.setText(bean.getName());
            tvType.setText(bean.getType());
            tvTime.setText(bean.getTime());
            tvDes.setText(bean.getMessage());
            tvLocation.setText(bean.getAddress());
            tvPrice.setText("¥ " + bean.getPrice());
            switch (bean.getStatus()) {
                case "0":
                    tvAction.setText("已完成");
                    break;
                case "1":
                    tvAction.setText("未接单");
                    break;
                case "2":
                    tvAction.setText("进行中");
                    break;
                case "3":
                    tvAction.setText("评价");
                    break;
                case "4":
                    tvAction.setText("已完成");
                    break;

            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, bean);
                    }
                }
            });
        }
    }
}