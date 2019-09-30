package com.benben.yishanbang.ui.mine.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.ui.mine.bean.TeaOrderDetailsBottomBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:奶茶订单详情商品列表adapter
 */
public class OrderDetailsGoodsListAdapter extends AFinalRecyclerViewAdapter<TeaOrderDetailsBottomBean.MilkTeaOrderVoBean.MilkTeaOrderGoodsVosBean> {

    public OrderDetailsGoodsListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_order_details_goods_list, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(TeaOrderDetailsBottomBean.MilkTeaOrderVoBean.MilkTeaOrderGoodsVosBean mOrderDetailsGoodsListBean, int position) {
            tvCount.setText("x"+mOrderDetailsGoodsListBean.getGoodsCount());
            tvName.setText(mOrderDetailsGoodsListBean.getGoodsName());
            tvPrice.setText(" ¥"+mOrderDetailsGoodsListBean.getPrice());
            ImageUtils.getPic(Constants.IMAGE_BASE_URL +mOrderDetailsGoodsListBean.getImgUrl(),ivImg,m_Context,R.mipmap.ic_default_pic);
        }
    }
}