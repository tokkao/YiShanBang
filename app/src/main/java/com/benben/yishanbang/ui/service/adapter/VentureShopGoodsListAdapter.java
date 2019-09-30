package com.benben.yishanbang.ui.service.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.pop.VentureShopAddCartPopupWindow;
import com.benben.yishanbang.ui.service.activity.VentureShopGoodsDetailActivity;
import com.benben.yishanbang.ui.service.bean.VentureShopGoodsListBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/7 0012
 * Describe:创业商城商品列表
 */
public class VentureShopGoodsListAdapter extends AFinalRecyclerViewAdapter<VentureShopGoodsListBean> {
    private Activity mContext;
    private  String mShopId;
    public VentureShopGoodsListAdapter(Context ctx,String shopId) {
        super(ctx);
        mContext = (Activity) ctx;
        this.mShopId =shopId;
    }

    private VentureShopAddCartPopupWindow mAddShoppingCarPopupWindow;//加入购物车弹窗


    @Override
    protected CommonViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_choose_goods_right, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(position, getItem(position));
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.iv_choose_goods_icon)
        ImageView ivChooseGoodsIcon;
        @BindView(R.id.tv_choose_goods_name)
        TextView tvChooseGoodsName;
        @BindView(R.id.tv_shoose_goods_price)
        TextView tvShooseGoodsPrice;
        @BindView(R.id.tv_choose_goods_priced)
        TextView tvChooseGoodsPriced;
        @BindView(R.id.iv_choose_goods_minus)
        ImageView ivChooseGoodsMinus;
        @BindView(R.id.tv_choose_goods_count)
        TextView tvChooseGoodsCount;
        @BindView(R.id.iv_choose_goods_add)
        ImageView ivChooseGoodsAdd;
        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        public void setContent(int position, VentureShopGoodsListBean data) {
            //表示此商品没有选中
            if (StringUtils.isEmpty(data.getSelectNum()) || "0".equals(data.getSelectNum())) {
                tvChooseGoodsCount.setVisibility(View.GONE);
                ivChooseGoodsMinus.setVisibility(View.GONE);
                tvChooseGoodsPriced.setVisibility(View.VISIBLE);
            } else {
                tvChooseGoodsCount.setText(data.getSelectNum());
                tvChooseGoodsCount.setVisibility(View.VISIBLE);
                ivChooseGoodsMinus.setVisibility(View.VISIBLE);
                tvChooseGoodsPriced.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //进入商品详情
                    mContext.startActivity(new Intent(mContext, VentureShopGoodsDetailActivity.class).putExtra("goods_id", data.getId()).putExtra("shop_id",mShopId));

                }
            });
            //点击加号
            ivChooseGoodsAdd.setOnClickListener(view -> {
                //调起选择商品规格界面
                mAddShoppingCarPopupWindow = new VentureShopAddCartPopupWindow(mContext, data.getMegssage(), data.getImgUrl(), data.getPrice(), data.getName(), "", data.getId(), mShopId);
                mAddShoppingCarPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

            });

            ivChooseGoodsMinus.setOnClickListener(view -> {
                //调起选择商品规格界面
                mAddShoppingCarPopupWindow = new VentureShopAddCartPopupWindow(mContext, data.getMegssage(), data.getImgUrl(), data.getPrice(), data.getName(), "", data.getId(), mShopId);
                mAddShoppingCarPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

            });

            //设置横线
            tvChooseGoodsPriced.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            //商品图片
            ImageUtils.getPic(Constants.IMAGE_BASE_URL + data.getImgUrl(), ivChooseGoodsIcon, mContext, R.mipmap.ic_default_pic);
            //商品名称
            tvChooseGoodsName.setText(data.getName());
            //商品价格
            tvShooseGoodsPrice.setText(data.getPrice() + "");
            //商品原价
            tvChooseGoodsPriced.setText("");


        }


    }


}
