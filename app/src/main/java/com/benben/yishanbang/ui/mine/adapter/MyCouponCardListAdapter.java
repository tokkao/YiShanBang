package com.benben.yishanbang.ui.mine.adapter;


import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.ui.mine.bean.WalletCouponCardBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:我的优惠卡adapter
 */
public class MyCouponCardListAdapter extends AFinalRecyclerViewAdapter<WalletCouponCardBean> {

    public MyCouponCardListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_my_coupon_card, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.iv_wallet_coupon_card_name)
        ImageView ivWalletCouponCardName;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(WalletCouponCardBean walletCouponCardBean, int position) {
            String money = "<font color='#666666'>金额：</font><font color='#F67617'> ¥" + walletCouponCardBean.getPrice() + "</font>";
            tvMoney.setText(Html.fromHtml(money));
            tvName.setText(walletCouponCardBean.getName());//设置优惠卡名称
            tvShopName.setText("商家名称:" + walletCouponCardBean.getShopName());//商店名称
            ImageUtils.getPic(Constants.IMAGE_BASE_URL + walletCouponCardBean.getImgUrl(), ivWalletCouponCardName, m_Context, R.mipmap.ic_default_pic);//设置图片

            //条目点击
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, walletCouponCardBean);
                    }
                }
            });
        }
    }
}