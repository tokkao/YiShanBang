package com.benben.yishanbang.ui.mine.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.widget.CircleImageView;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.ui.mine.bean.OrderManageCardListBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:订单管理adapter
 */
public class OrderManageCardListAdapter extends AFinalRecyclerViewAdapter<OrderManageCardListBean> {

    public OrderManageCardListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_order_manage_card_list, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.civ_avatar)
        CircleImageView civAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_card_name)
        TextView tvCardName;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_order_num)
        TextView tvOrderNum;
        @BindView(R.id.iv_card_img)
        ImageView ivCardImg;
        @BindView(R.id.tv_date)
        TextView tvDate;
        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(OrderManageCardListBean mOrderManageListBean, int position) {
            //头像
            ImageUtils.getPic(Constants.IMAGE_BASE_URL + mOrderManageListBean.getHeadImg(), civAvatar, m_Context, R.mipmap.icon_default_avatar);
            //商品图片
            ImageUtils.getPic(Constants.IMAGE_BASE_URL + mOrderManageListBean.getCardEntity().getImgUrl(), ivCardImg, m_Context, R.mipmap.ic_default_pic);
            //用户昵称
            tvName.setText(mOrderManageListBean.getNickName());
            //优惠卡名称
            tvCardName.setText(mOrderManageListBean.getCardEntity() == null ? "" : mOrderManageListBean.getCardEntity().getName());
            //日期
            tvDate.setText(StringUtils.isEmpty(mOrderManageListBean.getOrderTime()) ? "" : mOrderManageListBean.getOrderTime().split(" ")[0]);
            //订单号
            tvOrderNum.setText("订单号 : " + mOrderManageListBean.getOrderNo());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, mOrderManageListBean);
                    }
                }
            });
        }
    }
}