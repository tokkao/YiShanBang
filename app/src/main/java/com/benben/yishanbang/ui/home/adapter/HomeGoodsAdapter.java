package com.benben.yishanbang.ui.home.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.ui.discount.activity.DiscountGoodsDetailActivity;
import com.benben.yishanbang.ui.home.bean.HomeRecommendGoodsBean;
import com.benben.yishanbang.widget.CustomImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/7
 * Time: 10:32
 */
public class HomeGoodsAdapter extends RecyclerView.Adapter<HomeGoodsAdapter.ViewHolder> {

    private Context mContext;
    private List<HomeRecommendGoodsBean.CardVoBean> list;
    public HomeGoodsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<HomeRecommendGoodsBean.CardVoBean> list) {
        this.list = list;
    }

    public List<HomeRecommendGoodsBean.CardVoBean> getList() {
        return list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(View.inflate(mContext, R.layout.item_home_goods, null));
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
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.ll_price)
        LinearLayout llPrice;
        @BindView(R.id.tv_sale_num)
        TextView tvSaleNum;
        @BindView(R.id.tv_old_price)
        TextView tvOldPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setContent(HomeRecommendGoodsBean.CardVoBean cardVoBean) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("goods_id",cardVoBean.getId());
                    bundle.putString("sale_count",cardVoBean.getNumber()+"");
                    bundle.putString("question_type",cardVoBean.getQuestionType());
                    MyApplication.openActivity(mContext, DiscountGoodsDetailActivity.class,bundle);
                }
            });
            ImageUtils.getPic(Constants.IMAGE_BASE_URL +cardVoBean.getImgUrl(),ivImg,mContext,R.mipmap.ic_default_pic);
            tvName.setText(cardVoBean.getName());
            tvPrice.setText(cardVoBean.getPrice()+"");
            tvSaleNum.setText(cardVoBean.getNumber()+"已售");
            //暂时不显示
            tvOldPrice.setVisibility(View.GONE);
            tvOldPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰

        }
    }
}
