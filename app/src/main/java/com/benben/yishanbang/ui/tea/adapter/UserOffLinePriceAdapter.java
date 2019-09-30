package com.benben.yishanbang.ui.tea.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.tea.bean.UserDetailsInfoBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/14 0014
 * Describe:他的技能
 */
public class UserOffLinePriceAdapter extends AFinalRecyclerViewAdapter<UserDetailsInfoBean.ServeTypeBean> {

    public UserOffLinePriceAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(m_Inflater.inflate(R.layout.item_user_details_off_line_price, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((ViewHolder) holder).setContent(getItem(position), position);
    }

    public class ViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.tv_user_details_service_name)
        TextView tvUserDetailsServiceName;
        @BindView(R.id.tv_user_details_service_time)
        TextView tvUserDetailsServiceTime;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }

        private void setContent(UserDetailsInfoBean.ServeTypeBean userOffLinePriceBean, int position) {
            tvUserDetailsServiceName.setText(userOffLinePriceBean.getServeTypeEntity().getName());
            tvUserDetailsServiceTime.setText("¥" + userOffLinePriceBean.getPrice() + "/小时");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, userOffLinePriceBean);
                    }
                }
            });
        }
    }
}
