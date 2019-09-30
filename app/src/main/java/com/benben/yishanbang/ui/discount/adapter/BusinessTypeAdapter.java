package com.benben.yishanbang.ui.discount.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.ui.discount.bean.ShopCateInfoBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/6
 * Time: 12:02
 */
public class BusinessTypeAdapter extends RecyclerView.Adapter<BusinessTypeAdapter.ViewHolder> {

    public OnItemClickListener mOnItemClickListener;
    private Context mContext;
    private List<ShopCateInfoBean> list;


    // 点击事件接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position, ShopCateInfoBean model);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public BusinessTypeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public List<ShopCateInfoBean> getList() {
        return list;
    }

    public void setList(List<ShopCateInfoBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(View.inflate(mContext, R.layout.item_business_type, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setContent(position, list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_type)
        TextView tvType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void setContent(int position, ShopCateInfoBean shopCateInfoBean) {
            tvType.setText(shopCateInfoBean.getCategoryName());
            if (shopCateInfoBean.isSelect()) {
                tvType.setBackground(mContext.getResources().getDrawable(R.drawable.shape_3radius_theme_bg));
                tvType.setTextColor(mContext.getResources().getColor(R.color.white));
            } else {
                tvType.setBackground(mContext.getResources().getDrawable(R.drawable.shape_3radius_black_bg));
                tvType.setTextColor(mContext.getResources().getColor(R.color.color_666666));
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    singleSelect(position);
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, shopCateInfoBean);
                    }
                }
            });
        }
    }

    //单选
    private void singleSelect(int position) {
        for (int i = 0; i < getList().size(); i++) {
            getList().get(i).setSelect(i == position);
        }
        notifyDataSetChanged();
    }


}
