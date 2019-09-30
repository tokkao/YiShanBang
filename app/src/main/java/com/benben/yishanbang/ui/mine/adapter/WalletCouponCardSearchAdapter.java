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
import com.benben.yishanbang.ui.mine.bean.WalletCouponCardSearchBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/22 0022
 * Describe:钱包---搜索我的优惠卡
 */
public class WalletCouponCardSearchAdapter extends AFinalRecyclerViewAdapter<WalletCouponCardSearchBean> {

    private List<WalletCouponCardSearchBean> walletCouponCardSearchBeans;

    public WalletCouponCardSearchAdapter(Context ctx) {
        super(ctx);
    }

    public void setWalletCouponCardSearchBeans(List<WalletCouponCardSearchBean> walletCouponCardSearchBeans) {
        this.walletCouponCardSearchBeans = walletCouponCardSearchBeans;
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new DateViewHolder(m_Inflater.inflate(R.layout.item_wallet_coupon_card_search, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((DateViewHolder) holder).setContent(getItem(position), position);
    }

    public class DateViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.iv_wallet_coupon_card_search)
        ImageView ivWalletCouponCardSearch;
        @BindView(R.id.tv_wallet_card_search_name)
        TextView tvWalletCardSearchName;
        @BindView(R.id.tv_wallet_card_search_shop_name)
        TextView tvWalletCardSearchShopName;
        @BindView(R.id.tv_wallet_card_search_data)
        TextView tvWalletCardSearchData;
        @BindView(R.id.tv_wallet_card_search_price)
        TextView tvWalletCardSearchPrice;
        View itemView;
        public DateViewHolder(View view) {
            super(view);
            itemView = view;
            ButterKnife.bind(this, view);
        }

        private void setContent(WalletCouponCardSearchBean walletCouponCardSearchBean, int position) {

            //设置图片
            ImageUtils.getPic(Constants.IMAGE_BASE_URL+walletCouponCardSearchBeans.get(position).getImgUrl(), ivWalletCouponCardSearch, m_Context, R.mipmap.ic_default_pic);
            //设置优惠卡名称
            tvWalletCardSearchName.setText(walletCouponCardSearchBeans.get(position).getName());
            //设置商店名称
            tvWalletCardSearchShopName.setText(walletCouponCardSearchBeans.get(position).getShopName());
            //设置价格
            tvWalletCardSearchPrice.setText(walletCouponCardSearchBeans.get(position).getPrice()+"");

        }
    }
}
