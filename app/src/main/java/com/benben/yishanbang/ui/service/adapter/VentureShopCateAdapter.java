package com.benben.yishanbang.ui.service.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.service.bean.VentureShopCateListBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:wanghk
 * Time:2019/8/26 0026 9:33
 * 创业商城分类
 */
public class VentureShopCateAdapter extends AFinalRecyclerViewAdapter<VentureShopCateListBean> {

    public VentureShopCateAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected CommonViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_tea_cate, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(position, getItem(position));
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.tv_choose_goods_left)
        TextView tvChooseGoodsLeft;
        @BindView(R.id.vw_icon)
        View vwIcon;
        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        public void setContent(int position, VentureShopCateListBean data) {
            tvChooseGoodsLeft.setText(data.getCategoryName());

            if (data.isSelected()) {
                tvChooseGoodsLeft.setTextColor(Color.parseColor("#7DD33C"));
                tvChooseGoodsLeft.setBackgroundResource(R.color.white);
                vwIcon.setVisibility(View.VISIBLE);
            } else {
                tvChooseGoodsLeft.setTextColor(Color.parseColor("#000000"));
                tvChooseGoodsLeft.setBackgroundResource(R.color.line_gray);
                vwIcon.setVisibility(View.GONE);
            }

            //分类点击
            itemView.setOnClickListener(v -> {
                singleSelect(position);
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, position, data);
                }
            });
        }
    }

    //单选
    private void singleSelect(int position) {
        for (int i = 0; i < getList().size(); i++) {
            getList().get(i).setSelected(i == position);
        }
        notifyDataSetChanged();
    }
}
