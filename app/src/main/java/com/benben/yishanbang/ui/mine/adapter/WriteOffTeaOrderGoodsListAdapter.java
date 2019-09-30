package com.benben.yishanbang.ui.mine.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.mine.bean.WriteOffTeaOrderDetailsBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:核销奶茶订单商品列表adapter
 */
public class WriteOffTeaOrderGoodsListAdapter extends AFinalRecyclerViewAdapter<WriteOffTeaOrderDetailsBean.MilkTeaOrderVoBean.MilkTeaOrderGoodsVosBean> {

    public WriteOffTeaOrderGoodsListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_write_off_tea_order_goods_list, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_goods_info)
        TextView tvGoodsInfo;
        @BindView(R.id.tv_goods_counts)
        TextView tvGoodsCounts;
        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(WriteOffTeaOrderDetailsBean.MilkTeaOrderVoBean.MilkTeaOrderGoodsVosBean afterMyTeaBean, int position) {
            tvGoodsInfo.setText(afterMyTeaBean.getGoodsName());//商品名称
            tvGoodsCounts.setText("x"+afterMyTeaBean.getGoodsCount());//商品数量
        }
    }
}