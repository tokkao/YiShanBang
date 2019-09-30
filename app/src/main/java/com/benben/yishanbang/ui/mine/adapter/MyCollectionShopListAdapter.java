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
import com.benben.yishanbang.ui.mine.bean.CollectionShopListBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:收藏店铺列表adapter
 */
public class MyCollectionShopListAdapter extends AFinalRecyclerViewAdapter<CollectionShopListBean> {

    public MyCollectionShopListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_my_collection_shop, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }


    public class CommonViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_distance)
        TextView tvDistance;
        @BindView(R.id.tv_des)
        TextView tvDes;
        @BindView(R.id.tv_sales_count)
        TextView tvSalesCount;

        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(CollectionShopListBean mMyCollectionListBean, int position) {

            ImageUtils.getPic(Constants.IMAGE_BASE_URL +mMyCollectionListBean.getShoptImg(), ivImage, m_Context, R.mipmap.ic_default_pic);
            tvName.setText(mMyCollectionListBean.getShopName());
            tvDes.setText(mMyCollectionListBean.getShopMessage());
            tvDistance.setText(mMyCollectionListBean.getDistance() + "km");
            tvSalesCount.setText("月销量：" + mMyCollectionListBean.getSalesCountMonth() + "单");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, mMyCollectionListBean);
                    }
                }
            });
        }
    }
}