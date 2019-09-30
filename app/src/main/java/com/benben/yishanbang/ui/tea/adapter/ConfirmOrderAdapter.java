package com.benben.yishanbang.ui.tea.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.tea.bean.ConfirmBean;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/12 0012
 * Describe:确认订单页面适配器
 */
public class ConfirmOrderAdapter extends AFinalRecyclerViewAdapter<ConfirmBean> {


    public ConfirmOrderAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_confirm_order, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder{
        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            itemView = view;
            ButterKnife.bind(this,view);
        }

        private void setContent(ConfirmBean confirmBean, int position) {

        }
    }
}
