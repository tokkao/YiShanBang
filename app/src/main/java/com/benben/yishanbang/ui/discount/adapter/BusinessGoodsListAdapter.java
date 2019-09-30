package com.benben.yishanbang.ui.discount.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.ui.discount.activity.DiscountGoodsDetailActivity;
import com.benben.yishanbang.ui.discount.bean.ShopGoodsListBean;
import com.benben.yishanbang.widget.CustomImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/6
 * Time: 14:05
 */
public class BusinessGoodsListAdapter extends AFinalRecyclerViewAdapter<ShopGoodsListBean> {

    private Activity mActivity;

    public BusinessGoodsListAdapter(Context ctx) {
        super(ctx);
        mActivity = (Activity) ctx;
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_business_goods_list, parent,
                false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(position, getList().get(position));
    }

    class CommonViewHolder extends BaseRecyclerViewHolder {


        @BindView(R.id.iv_img)
        CustomImageView ivImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.ll_price)
        LinearLayout llPrice;
        @BindView(R.id.tv_sale_num)
        TextView tvSaleNum;
        @BindView(R.id.tv_label)
        TextView tvLabel;
        @BindView(R.id.ll_label)
        LinearLayout llLabel;

        public CommonViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setContent(int position, ShopGoodsListBean mShopGoodsListBean) {

            ImageUtils.getPic(Constants.IMAGE_BASE_URL + mShopGoodsListBean.getImgUrl(), ivImg, mActivity, R.mipmap.ic_default_pic);
            tvName.setText(mShopGoodsListBean.getName());
            tvLabel.setVisibility("1".equals(mShopGoodsListBean.getQuestionType()) ? View.VISIBLE : View.INVISIBLE);
            tvPrice.setText(mShopGoodsListBean.getPrice() + "");
            tvSaleNum.setText(mShopGoodsListBean.getNumber() + "已售");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("goods_id", mShopGoodsListBean.getId());
                    bundle.putString("sale_count", String.valueOf(mShopGoodsListBean.getNumber()));
                    bundle.putString("question_type", mShopGoodsListBean.getQuestionType());
                    MyApplication.openActivity(mActivity, DiscountGoodsDetailActivity.class, bundle);
                }
            });
        }
    }

}
