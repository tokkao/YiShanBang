package com.benben.yishanbang.ui.discount.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.ui.discount.activity.SearchDiscountActivity;
import com.benben.yishanbang.ui.discount.bean.HotSearchListBean;
import com.benben.yishanbang.ui.mine.activity.GoodsManageActivity;

import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/6
 * Time: 10:20
 */
public class SearchHotAdapter extends RecyclerView.Adapter<SearchHotAdapter.ViewHolder> {

    private Context mContext;
    private List<HotSearchListBean> mBean;
    private int mEnterType = 0;

    public void setEnterType(int mEnterType) {
        this.mEnterType = mEnterType;
    }

    public SearchHotAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBean(List<HotSearchListBean> mBean) {
        this.mBean = mBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(View.inflate(mContext, R.layout.item_search_hot, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEnterType == 4) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.EXTRA_KEY_ENTER_TYPE, 11);
                    bundle.putString("key_word", mBean.get(position).getSearchName());
                    MyApplication.openActivity(mContext, GoodsManageActivity.class, bundle);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.EXTRA_KEY_ENTER_TYPE, mEnterType);
                    bundle.putString("key_word", mBean.get(position).getSearchName());
                    MyApplication.openActivity(mContext, SearchDiscountActivity.class, bundle);
                }
            }
        });
        holder.tvType.setText("" + mBean.get(position).getSearchName());
    }

    @Override
    public int getItemCount() {
        return mBean == null ? 0 : mBean.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.tv_type);
        }
    }
}
