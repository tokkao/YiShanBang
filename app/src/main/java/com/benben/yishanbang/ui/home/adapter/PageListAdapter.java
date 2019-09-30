package com.benben.yishanbang.ui.home.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.ui.home.activity.PageDetailActivity;
import com.benben.yishanbang.ui.mine.bean.QRPageListBean;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/7
 * Time: 11:44
 */
public class PageListAdapter extends RecyclerView.Adapter<PageListAdapter.ViewHolder> {

    private Context mContext;
    private QRPageListBean qrPageListBean;

    public PageListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setQrPageList(QRPageListBean qrPageListBean) {
        this.qrPageListBean = qrPageListBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(View.inflate(mContext, R.layout.item_page_list, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("free", true);
                MyApplication.openActivity(mContext, PageDetailActivity.class, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
