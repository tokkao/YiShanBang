package com.benben.yishanbang.ui.home.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.ui.discount.activity.BusinessDetailActivity;
import com.benben.yishanbang.ui.home.bean.HomeRecommendGoodsBean;
import com.benben.yishanbang.widget.CustomImageViewFive;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/7
 * Time: 10:20
 */
public class HomeBusinessAdapter extends RecyclerView.Adapter<HomeBusinessAdapter.ViewHolder> {

    private Context mContext;
    private List<HomeRecommendGoodsBean.CardShopVoBean> list;

    public HomeBusinessAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<HomeRecommendGoodsBean.CardShopVoBean> list) {
        this.list = list;
    }

    public List<HomeRecommendGoodsBean.CardShopVoBean> getList() {
        return list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(View.inflate(mContext, R.layout.item_home_business, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setContent(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_img)
        CustomImageViewFive ivImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        View mItemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mItemView = itemView;
        }

        public void setContent(HomeRecommendGoodsBean.CardShopVoBean cardShopVoBean) {

            ImageUtils.getCircularPic(Constants.IMAGE_BASE_URL + cardShopVoBean.getShoptImg(), ivImg, mContext, 10,R.mipmap.ic_default_pic);
            tvName.setText(cardShopVoBean.getShopName());
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("store_id", cardShopVoBean.getId());
                    MyApplication.openActivity(mContext, BusinessDetailActivity.class, bundle);
                }
            });
        }
    }
}
