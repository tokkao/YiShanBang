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
import com.benben.yishanbang.ui.mine.bean.WriteOffCardOrderListBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:核销优惠卡订单adapter
 */
public class WriteOffCardOrderListAdapter extends AFinalRecyclerViewAdapter<WriteOffCardOrderListBean> {

    public WriteOffCardOrderListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_write_off_card_order, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.iv_goods_img)
        ImageView ivGoodsImg;
        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(WriteOffCardOrderListBean mWriteOffOrderListBean, int position) {
            //优惠卡金额
            String money = "<font color='#666666'>金额：</font><font color='#F67617'> ¥" + mWriteOffOrderListBean.getCard().getPrice() + "</font>";
            tvMoney.setText(Html.fromHtml(money));
            //优惠卡名称
            tvName.setText(mWriteOffOrderListBean.getCard().getName());
            //优惠卡用户昵称
            tvShopName.setText("用户昵称:" + mWriteOffOrderListBean.getUser().getNickname());
            //优惠卡购买日期
            tvDate.setText("购买日期:" + mWriteOffOrderListBean.getCard().getCreateDate());
            //优惠卡图片
            ImageUtils.getPic(Constants.IMAGE_BASE_URL + mWriteOffOrderListBean.getCard().getImgUrl(), ivGoodsImg, m_Context, R.mipmap.ic_default_pic);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, mWriteOffOrderListBean);
                    }
                }
            });*/
        }
    }
}