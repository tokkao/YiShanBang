package com.benben.yishanbang.ui.tea.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.benben.commoncore.utils.StringUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.tea.bean.ShoppingCarGoodsBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/15 0015
 * Describe:购物车商品列表
 */
public class ShoppingCarGoodsListAdapter extends AFinalRecyclerViewAdapter<ShoppingCarGoodsBean> {

    public ShoppingCarGoodsListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_shopping_car, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_good_name)
        TextView tvGoodName;
        @BindView(R.id.tv_good_norms)
        TextView tvGoodNorms;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_old_price)
        TextView tvOldPrice;
        @BindView(R.id.iv_count_cut)
        ImageView ivCountCut;
        @BindView(R.id.tv_count_number)
        TextView tvCountNumber;
        @BindView(R.id.iv_count_add)
        ImageView ivCountAdd;
        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            itemView = view;
            ButterKnife.bind(this, view);
        }

        private void setContent(ShoppingCarGoodsBean mGoodsBean, int position) {

            tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG |Paint.ANTI_ALIAS_FLAG );
            tvGoodName.setText(mGoodsBean.getGoodsName());
            if(StringUtils.isEmpty(mGoodsBean.getSpec())){
                tvGoodNorms.setVisibility(View.GONE);
            }
            if(mGoodsBean.getDelPrice() == 0){
                tvOldPrice.setVisibility(View.GONE);
            }
            tvGoodNorms.setText(mGoodsBean.getSpec() + "/" + mGoodsBean.getSuger() + "/" + mGoodsBean.getTemp());
            tvPrice.setText("¥ " + mGoodsBean.getPrice() + "");
            tvOldPrice.setText("¥" + mGoodsBean.getDelPrice() + "");
            tvCountNumber.setText(mGoodsBean.getGoodsNum() + "");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(itemView, position, mGoodsBean);
                    }
                }
            });

            //减少
            ivCountCut.setOnClickListener(view -> {
               if(mOnGoodsCountChangeListener != null){
                   mOnGoodsCountChangeListener.onReduceListener(position,mGoodsBean);
               }
            });
            //增加
            ivCountAdd.setOnClickListener(view -> {
                if(mOnGoodsCountChangeListener != null){
                    mOnGoodsCountChangeListener.onAddListener(position,mGoodsBean);
                }
            });
        }
    }

    OnGoodsCountChangeListener mOnGoodsCountChangeListener;
    //加减控件监听
    public interface OnGoodsCountChangeListener {
        //减少
        void onReduceListener(int position, ShoppingCarGoodsBean mGoodsBean);
        //增加
        void onAddListener( int position, ShoppingCarGoodsBean mGoodsBean);
    }

    public void setOnGoodsCountChangeListener(OnGoodsCountChangeListener mOnGoodsCountChangeListener) {
        this.mOnGoodsCountChangeListener = mOnGoodsCountChangeListener;
    }
}
