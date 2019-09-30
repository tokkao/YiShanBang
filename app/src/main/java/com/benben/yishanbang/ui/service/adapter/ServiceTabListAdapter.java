package com.benben.yishanbang.ui.service.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.service.bean.TabEntityBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:服务分类Tab adapter
 */
public class ServiceTabListAdapter extends AFinalRecyclerViewAdapter<TabEntityBean> {


    public ServiceTabListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_service_tab_list, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.tv_tab_name)
        TextView tvTabName;
        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(TabEntityBean mTabEntityBean, int position) {
            tvTabName.setTextColor(mTabEntityBean.isSelected() ? m_Context.getResources().getColor(R.color.theme) : m_Context.getResources().getColor(R.color.color_666666));
            tvTabName.setText(mTabEntityBean.getTab_name());
            tvTabName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    singleSelect(position);
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(tvTabName, position, mTabEntityBean);
                    }
                }
            });
        }

    }
    //单选
    public void singleSelect(int position) {
        for (int i = 0; i < getList().size(); i++) {
            getList().get(i).setSelected(i == position);
        }
        notifyDataSetChanged();
    }
}