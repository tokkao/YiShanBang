package com.benben.yishanbang.ui.mine.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.ui.mine.activity.ApplySaleActivity;
import com.benben.yishanbang.ui.mine.bean.ShoppingOrderListBean;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.benben.yishanbang.config.Constants.DATA_KEY;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:购物订单adapter
 */
public class ShoppingOrderListAdapter extends AFinalRecyclerViewAdapter<ShoppingOrderListBean> {

    public ShoppingOrderListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_shopping_order_list, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.iv_goods_img)
        ImageView ivGoodsImg;
        @BindView(R.id.tv_total_price)
        TextView tvTotalPrice;
        @BindView(R.id.tv_goods_count)
        TextView tvGoodsCount;
        @BindView(R.id.tv_first_options)
        TextView tvFirstOptions;
        @BindView(R.id.tv_second_options)
        TextView tvSecondOptions;
        @BindView(R.id.tv_third_options)
        TextView tvThirdOptions;
        @BindView(R.id.tv_good_name)
        TextView tvGoodName;
        @BindView(R.id.tv_good_num)
        TextView tvGoodNum;
        @BindView(R.id.tv_good_price)
        TextView tvGoodPrice;
        @BindView(R.id.tv_good_count)
        TextView tvGoodCount;
        @BindView(R.id.llyt_event_layout)
        View llytEventLayout;

        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(ShoppingOrderListBean mShoppingOrderListBean, int position) {
            tvGoodsCount.setText("共" + mShoppingOrderListBean.getGoodCount() + "件商品 应付金额：");
            tvTotalPrice.setText("¥ " + mShoppingOrderListBean.getOrderMoney());
            tvGoodName.setText(mShoppingOrderListBean.getGood().getName());
            tvGoodNum.setText("商品单号 " + mShoppingOrderListBean.getGood().getNumber());
            tvGoodPrice.setText("¥ " + mShoppingOrderListBean.getGood().getPrice());
            tvGoodCount.setText("x" + mShoppingOrderListBean.getGood().getCount());

            ImageUtils.getPic(Constants.IMAGE_BASE_URL + mShoppingOrderListBean.getGood().getImgUrl(), ivGoodsImg, m_Context);
            switch (mShoppingOrderListBean.getStauts()) {
                case "0"://已完成     订单状态1待付款；2待发货；3待收货（待取餐）；4待评价；5已完成（已评价）；6售后处理中（退款&退货）；7售后已完成（退款&退货）；8已取消',
//                    ivDelete.setVisibility(View.VISIBLE);
//                    tvFirstOptions.setText("申请售后");
//                    tvSecondOptions.setText("再次购买");
//                    tvFirstOptions.setVisibility(View.VISIBLE);
//                    tvSecondOptions.setVisibility(View.VISIBLE);
//                    tvThirdOptions.setVisibility(View.GONE);
                    break;
                case "1"://待付款
                    tvState.setText("待付款");
                    tvDate.setText(mShoppingOrderListBean.getOrderTime());
                    ivDelete.setVisibility(View.INVISIBLE);
                    tvSecondOptions.setText("取消订单");
                    tvThirdOptions.setText("立即付款");
                    llytEventLayout.setVisibility(View.VISIBLE);
                    tvFirstOptions.setVisibility(View.GONE);
                    tvSecondOptions.setVisibility(View.VISIBLE);
                    tvThirdOptions.setVisibility(View.VISIBLE);
                    break;
                case "2"://待发货
                    tvState.setText("待发货");
                    tvDate.setText(mShoppingOrderListBean.getOrderTime());
                    ivDelete.setVisibility(View.INVISIBLE);
                    tvFirstOptions.setText("提醒发货");
                    tvSecondOptions.setText("取消订单");
                    llytEventLayout.setVisibility(View.VISIBLE);
                    tvFirstOptions.setVisibility(View.VISIBLE);
                    tvSecondOptions.setVisibility(View.VISIBLE);
                    tvThirdOptions.setVisibility(View.GONE);
                    break;
                case "3"://已发货
                    tvState.setText("已发货");
                    tvDate.setText(mShoppingOrderListBean.getOrderTime());
                    ivDelete.setVisibility(View.INVISIBLE);
                    tvFirstOptions.setText("查看物流");
                    tvSecondOptions.setText("取消订单");
                    tvThirdOptions.setText("确认收货");
                    llytEventLayout.setVisibility(View.VISIBLE);
                    tvFirstOptions.setVisibility(View.VISIBLE);
                    tvSecondOptions.setVisibility(View.VISIBLE);
                    tvThirdOptions.setVisibility(View.VISIBLE);
                    break;
                case "5"://已完成
                    tvState.setText("交易完成");
                    tvDate.setText(mShoppingOrderListBean.getOrderTime());
                    ivDelete.setVisibility(View.VISIBLE);
                    tvFirstOptions.setText("申请售后");
                    tvThirdOptions.setText("再次购买");
                    llytEventLayout.setVisibility(View.VISIBLE);
                    tvFirstOptions.setVisibility(View.VISIBLE);
                    tvSecondOptions.setVisibility(View.GONE);
                    tvThirdOptions.setVisibility(View.VISIBLE);
                    break;
                case "4"://待评价
                    tvState.setText("待评价");
                    llytEventLayout.setVisibility(View.GONE);
                    break;
                case "6"://售后处理中（退款&退货）
                    tvState.setText("售后处理中");
                    llytEventLayout.setVisibility(View.GONE);
                    break;
                case "7"://售后已完成（退款&退货）
                    tvState.setText("售后已完成");
                    llytEventLayout.setVisibility(View.GONE);
                    break;
                case "8"://已取消
                    tvState.setText("已取消");
                    llytEventLayout.setVisibility(View.GONE);
                    break;
            }
            tvFirstOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null && mOnItemClickListener instanceof ShoppingOrderCallback) {
                        switch (mShoppingOrderListBean.getStauts()) {
                            case "2"://提醒发货
                                ((ShoppingOrderCallback) mOnItemClickListener).orderRemindCallback(mShoppingOrderListBean.getOrderNo());
                                break;
                            case "3"://查看物流
                                ((ShoppingOrderCallback) mOnItemClickListener).orderLookCallback(mShoppingOrderListBean.getOrderNo());
                                break;
                            case "5"://申请售后
                                Intent intent = new Intent(m_Context, ApplySaleActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra(DATA_KEY, mShoppingOrderListBean.getOrderNo());
                                m_Context.startActivity(intent);
                                break;
                        }
                    }
                }
            });
            tvSecondOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //取消订单
                    if (mOnItemClickListener != null && mOnItemClickListener instanceof ShoppingOrderCallback) {
                        ((ShoppingOrderCallback) mOnItemClickListener).orderCancelCallback(mShoppingOrderListBean.getOrderNo());
                    }
                }
            });
            tvThirdOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null && mOnItemClickListener instanceof ShoppingOrderCallback) {
                        switch (mShoppingOrderListBean.getStauts()) {
                            case "1"://立即付款
                                ((ShoppingOrderCallback) mOnItemClickListener).orderPayCallback(mShoppingOrderListBean.getOrderMoney(), mShoppingOrderListBean.getOrderNo());
                                break;
                            case "3"://确认收货
                                ((ShoppingOrderCallback) mOnItemClickListener).orderConfirmCallback(mShoppingOrderListBean.getOrderNo());
                                break;
                            case "5"://再次购买
                                ((ShoppingOrderCallback) mOnItemClickListener).orderAgainCallback(mShoppingOrderListBean.getOrderNo());
                                break;
                        }
                    }
                }
            });
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //删除订单
                    if (mOnItemClickListener != null && mOnItemClickListener instanceof ShoppingOrderCallback) {
                        ((ShoppingOrderCallback) mOnItemClickListener).orderDeleteCallback(mShoppingOrderListBean.getOrderNo());
                    }
                }
            });
        }
    }


    public interface ShoppingOrderCallback extends OnItemClickListener {
        /**
         * 取消订单
         *
         * @param orderNo
         */
        void orderCancelCallback(String orderNo);

        /**
         * 提醒订单发货
         *
         * @param orderNo
         */
        void orderRemindCallback(String orderNo);

        /**
         * 查看订单
         *
         * @param orderNo
         */
        void orderLookCallback(String orderNo);

        /**
         * 确认订单
         *
         * @param orderNo
         */
        void orderConfirmCallback(String orderNo);

        /**
         * 删除订单
         *
         * @param orderNo
         */
        void orderDeleteCallback(String orderNo);

        /**
         * 再来一订单
         *
         * @param orderNo
         */
        void orderAgainCallback(String orderNo);

        /**
         * 支付订单
         *
         * @param amount
         * @param orderNo
         */
        void orderPayCallback(String amount, String orderNo);
    }
}