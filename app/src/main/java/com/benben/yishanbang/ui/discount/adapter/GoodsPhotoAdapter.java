package com.benben.yishanbang.ui.discount.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.config.Constants;

import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/6
 * Time: 15:34
 */
public class GoodsPhotoAdapter extends RecyclerView.Adapter<GoodsPhotoAdapter.ViewHolder> {

    private Context mContext;
    private List<String> list;

    public GoodsPhotoAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(View.inflate(mContext, R.layout.item_goods_photo, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageView ivImg = holder.itemView.findViewById(R.id.iv_img);
        ImageUtils.getPic(Constants.IMAGE_BASE_URL +list.get(position), ivImg, mContext, R.mipmap.ic_default_pic);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
