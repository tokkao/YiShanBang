package com.benben.yishanbang.ui.mine.adapter;


import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.ui.mine.bean.CollectionGoodsListBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:收藏商品列表adapter
 */
public class MyCollectionGoodsListAdapter extends AFinalRecyclerViewAdapter<CollectionGoodsListBean> {


    public OnDeleteListener mOnDeleteListener;

    // 点击事件接口
    public interface OnDeleteListener {
        void onDelete(View view, int position, CollectionGoodsListBean model);
    }

    public void setOnDeleteListener(OnDeleteListener listener) {
        this.mOnDeleteListener = listener;
    }

    public MyCollectionGoodsListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_my_collection_goods, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }


    public class CommonViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_old_price)
        TextView tvOldPrice;
        @BindView(R.id.tv_sales_count)
        TextView tvSalesCount;
        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(CollectionGoodsListBean mMyCollectionListBean, int position) {

            ImageUtils.getPic(Constants.IMAGE_BASE_URL +mMyCollectionListBean.getImgUrl(),ivImage,m_Context,R.mipmap.ic_default_pic);
            tvName.setText(mMyCollectionListBean.getName());
            tvPrice.setText(" ¥"+mMyCollectionListBean.getPrice());
//            tvOldPrice.setText(" ¥"+mMyCollectionListBean.get);
            tvOldPrice.setVisibility(View.INVISIBLE);
            tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG |Paint.ANTI_ALIAS_FLAG );
            tvSalesCount.setText(mMyCollectionListBean.getNumber()+"已售");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, mMyCollectionListBean);
                    }
                }
            });

            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnDeleteListener != null) {
                        mOnDeleteListener.onDelete(view, position, mMyCollectionListBean);
                    }
                }
            });
        }
    }
}