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
import com.benben.yishanbang.ui.mine.bean.TeaOrderDetailsBottomBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:zhn
 * Time:2019/8/27 0027 17:23
 */
public class MilkTeaOrderDetailsGoodsListAdapter extends AFinalRecyclerViewAdapter<TeaOrderDetailsBottomBean.MilkTeaOrderVoBean.MilkTeaOrderGoodsVosBean> {
    private Context mContext;


    public MilkTeaOrderDetailsGoodsListAdapter(Context ctx) {
        super(ctx);
        mContext = ctx.getApplicationContext();
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_confirm_order, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {
        View itemView;
        @BindView(R.id.tv_confirm_order)
        TextView tvConfirmOrder;
        @BindView(R.id.tv_confirm_spec)
        TextView tvConfirmSpec;
        @BindView(R.id.tv_confirm_order_count)
        TextView tvConfirmCount;
        @BindView(R.id.tv_confirm_order_price)
        TextView tvConfirmPrice;
        @BindView(R.id.iv_confirm_order)
        ImageView ivConfirmOrder;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(TeaOrderDetailsBottomBean.MilkTeaOrderVoBean.MilkTeaOrderGoodsVosBean milkTeaOrderGoodsVosBean, int position) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, milkTeaOrderGoodsVosBean);
                    }
                }
            });

            tvConfirmOrder.setText(milkTeaOrderGoodsVosBean.getGoodsName());
            tvConfirmSpec.setText(milkTeaOrderGoodsVosBean.getSpec() + "/" +
                    milkTeaOrderGoodsVosBean.getSugar() + "/" + milkTeaOrderGoodsVosBean.getTemp());
            tvConfirmCount.setText("x" + milkTeaOrderGoodsVosBean.getGoodsCount());
            tvConfirmPrice.setText("¥ " + Double.toString(milkTeaOrderGoodsVosBean.getPrice()));

            if (milkTeaOrderGoodsVosBean.getImgUrl() != null) {
                //ImageUtils.loadCover(ivConfirmOrder,milkTeaOrderGoodsVosBean.getImgUrl(),mContext);
                ImageUtils.getPic(Constants.IMAGE_BASE_URL + milkTeaOrderGoodsVosBean.getImgUrl(), ivConfirmOrder, mContext, R.mipmap.ic_default_pic);
            }

        }
    }
}
