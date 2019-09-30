package com.benben.yishanbang.ui.service.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.service.bean.RechargeListBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:充值列表adapter
 */
public class RechargeListAdapter extends AFinalRecyclerViewAdapter<RechargeListBean> {


    public RechargeListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_recharge_list, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_old_price)
        TextView tvOldPrice;
        @BindView(R.id.tv_new_user)
        TextView tvNewUser;
        @BindView(R.id.iv_letter)
        ImageView ivLetter;
        @BindView(R.id.tv_msg_count)
        TextView tvMsgCount;
        @BindView(R.id.iv_gift)
        ImageView ivGift;
        @BindView(R.id.tv_gift_count)
        TextView tvGiftCount;
        @BindView(R.id.tv_add)
        TextView tvAdd;
        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(RechargeListBean mRechargeListBean, int position) {

            if (mRechargeListBean.isSelected()) {
                tvMsgCount.setTextColor(m_Context.getResources().getColor(R.color.money_color));
                tvGiftCount.setTextColor(m_Context.getResources().getColor(R.color.money_color));
                tvAdd.setTextColor(m_Context.getResources().getColor(R.color.money_color));
                itemView.setBackgroundResource(R.drawable.shape_3radius_border);
            } else {
                tvMsgCount.setTextColor(m_Context.getResources().getColor(R.color.color_333333));
                tvGiftCount.setTextColor(m_Context.getResources().getColor(R.color.color_333333));
                tvAdd.setTextColor(m_Context.getResources().getColor(R.color.color_333333));
                itemView.setBackgroundResource(R.drawable.shadow_bg);

            }
            switch (mRechargeListBean.getId()) {
                case 0:
                    tvPrice.setText("1元 / ");
                    tvOldPrice.setVisibility(View.VISIBLE);
                    tvNewUser.setVisibility(View.VISIBLE);
                    ivLetter.setImageResource(R.mipmap.ic_letter);
                    ivGift.setImageResource(R.mipmap.ic_gift_cake);
                    tvMsgCount.setText("沟通条数66条");
                    tvGiftCount.setText("1元小礼物x1");
                    break;
                case 1:
                    tvPrice.setText("5元");
                    tvOldPrice.setVisibility(View.GONE);
                    tvNewUser.setVisibility(View.GONE);
                    ivLetter.setImageResource(R.mipmap.ic_letter);
                    ivGift.setImageResource(R.mipmap.ic_gift_flower);
                    tvMsgCount.setText("沟通条数30条");
                    tvGiftCount.setText("5元小礼物x1");
                    break;
                case 2:
                    tvPrice.setText("20元");
                    tvOldPrice.setVisibility(View.GONE);
                    tvNewUser.setVisibility(View.GONE);
                    ivLetter.setImageResource(R.mipmap.ic_letter);
                    ivGift.setImageResource(R.mipmap.ic_gift_bear);
                    tvMsgCount.setText("沟通条数136条");
                    tvGiftCount.setText("20元小礼物x1");
                    break;
                case 3:
                    tvPrice.setText("50元");
                    tvOldPrice.setVisibility(View.GONE);
                    tvNewUser.setVisibility(View.GONE);
                    ivLetter.setImageResource(R.mipmap.ic_letter);
                    ivGift.setImageResource(R.mipmap.ic_gift_ring);
                    tvMsgCount.setText("沟通条数356条");
                    tvGiftCount.setText("50元小礼物x1");
                    break;
                case 4:
                    tvPrice.setText("520元");
                    tvOldPrice.setVisibility(View.GONE);
                    tvNewUser.setVisibility(View.GONE);
                    ivLetter.setImageResource(R.mipmap.ic_gift_bear);
                    ivGift.setImageResource(R.mipmap.ic_gift_ring);
                    tvMsgCount.setText("20元小礼物x1");
                    tvGiftCount.setText("50元小礼物x4");
                    break;
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    singleSelect(position);
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, mRechargeListBean);
                    }
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