package com.benben.yishanbang.ui.discount.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.ui.discount.activity.BusinessDetailActivity;
import com.benben.yishanbang.ui.discount.bean.SearchCardListBean;
import com.shehuan.niv.NiceImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/6
 * Time: 9:38
 */
public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.ViewHolder> {

    private Context mContext;
    private List<SearchCardListBean> mList;

    public DiscountAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<SearchCardListBean> mList) {
        this.mList = mList;
    }

    public List<SearchCardListBean> getList() {
        return mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(View.inflate(mContext, R.layout.item_discount, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setContent(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_img)
        NiceImageView ivImg;
        @BindView(R.id.tv_distance)
        TextView tvDistance;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_explain)
        TextView tvExplain;
        @BindView(R.id.tv_sale_number)
        TextView tvSaleNumber;
        View mItemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mItemView = itemView;
        }


        public void setContent(SearchCardListBean searchCardListBean) {

            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("store_id", searchCardListBean.getShopId());
                    bundle.putString("store_distance", String.valueOf(searchCardListBean.getDistance()));
                    MyApplication.openActivity(mContext, BusinessDetailActivity.class, bundle);
                }
            });
            ImageUtils.getPic(Constants.IMAGE_BASE_URL + searchCardListBean.getShoptImg(), ivImg, mContext, R.mipmap.ic_default_pic);
            tvDistance.setText(StringUtils.isEmpty(searchCardListBean.getDistance()) ? "" : searchCardListBean.getDistance() + "km");


            tvSaleNumber.setText("月销量" + searchCardListBean.getSalesCountMonth() + "单");
            tvExplain.setText(searchCardListBean.getShopMessage());
            tvTitle.setText(searchCardListBean.getShopName());
        }
    }
}
