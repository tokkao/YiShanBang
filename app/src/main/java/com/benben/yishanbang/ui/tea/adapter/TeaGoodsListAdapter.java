package com.benben.yishanbang.ui.tea.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.pop.AddShoppingCarPopupWindow;
import com.benben.yishanbang.ui.tea.bean.TeaShopGoodsInfoBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/7 0012
 * Describe:选择商品右边条目二级适配器
 */
public class TeaGoodsListAdapter extends AFinalRecyclerViewAdapter<TeaShopGoodsInfoBean.MilkTeaGoodsVosBeanX.MilkTeaGoodsVosBean> {
    private Activity mContext;

    public TeaGoodsListAdapter(Context ctx) {
        super(ctx);
        mContext = (Activity) ctx;
    }

    private AddShoppingCarPopupWindow mAddShoppingCarPopupWindow;//加入购物车弹窗


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

        public void setContent(int position, TeaShopGoodsInfoBean.MilkTeaGoodsVosBeanX.MilkTeaGoodsVosBean data) {
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

            //点击加号
            ivChooseGoodsAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String mShopId = data.getShopId();
                    String mGoodId = data.getGoodsId();//商品id
                    String mDescription = data.getDescription();//商品描述
                    String mImgUrl = data.getImgUrl();//图片的url
                    String mPrice = Double.toString(data.getPrice());//现在的价格
                    String mGoodsName = data.getGoodsName();//商品名称
                    String mOldPrice = Double.toString(data.getDelPrice());
                    //调起选择商品规格界面
                    mAddShoppingCarPopupWindow = new AddShoppingCarPopupWindow(mContext,
                            mDescription, mImgUrl, mPrice, mGoodsName, mOldPrice, mGoodId, mShopId);
                    mAddShoppingCarPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);


                    Intent intent = new Intent("SecondPosition");
                    intent.putExtra("mSecondPosition", "mSecondPosition");
                    intent.putExtra("mItemPosition", position + "");
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                }
            });

            ivChooseGoodsMinus.setOnClickListener(view -> ToastUtils.show(mContext, "多规格商品只能去购物车删除"));

            //设置横线
            tvChooseGoodsPriced.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            //商品图片
            ImageUtils.getPic(Constants.IMAGE_BASE_URL + data.getImgUrl(), ivChooseGoodsIcon, mContext, R.mipmap.ic_default_pic);
            //商品名称
            tvChooseGoodsName.setText(data.getGoodsName());
            //商品价格
            tvShooseGoodsPrice.setText(data.getPrice() + "");
            //商品原价
            tvChooseGoodsPriced.setText("¥" + data.getDelPrice());


        }
    }
}
