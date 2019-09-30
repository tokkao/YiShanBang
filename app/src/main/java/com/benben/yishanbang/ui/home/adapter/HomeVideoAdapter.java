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
import com.benben.yishanbang.ui.home.activity.VideoPlayActivity;
import com.benben.yishanbang.ui.home.bean.HomeRecommendGoodsBean;
import com.benben.yishanbang.widget.CustomImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/7
 * Time: 10:42
 */
public class HomeVideoAdapter extends RecyclerView.Adapter<HomeVideoAdapter.ViewHolder> {

    private Context mContext;
    private List<HomeRecommendGoodsBean.AdvertVoBean> list;

    public HomeVideoAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<HomeRecommendGoodsBean.AdvertVoBean> list) {
        this.list = list;
    }

    public List<HomeRecommendGoodsBean.AdvertVoBean> getList() {
        return list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(View.inflate(mContext, R.layout.item_home_video, null));
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
        CustomImageView ivImg;
        @BindView(R.id.tv_advert)
        TextView tvAdvert;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_name)
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setContent(HomeRecommendGoodsBean.AdvertVoBean videoBean) {
//            ImageUtils.loadCover(ivImg,"http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",mContext);
            ImageUtils.loadCover(ivImg,videoBean.getAddress(),mContext);
            tvAdvert.setText(videoBean.getTitle());
            tvTime.setText(videoBean.getTime());
            tvName.setText(videoBean.getShopName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("shop_id",videoBean.getShopId());
                    bundle.putString("title",videoBean.getTitle());
                    bundle.putString("video",videoBean.getAddress());

                    MyApplication.openActivity(mContext, VideoPlayActivity.class, bundle);
                }
            });

        }
    }
}
