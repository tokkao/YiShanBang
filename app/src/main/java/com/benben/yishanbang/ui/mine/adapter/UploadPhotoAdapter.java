package com.benben.yishanbang.ui.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.config.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Jay
 * @email 1136189725@qq.com  有事来邮!!!
 * @date 2019/9/11 0011
 */
public class UploadPhotoAdapter extends AFinalRecyclerViewAdapter<String> {

    public UploadPhotoAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected ViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(m_Context).inflate(R.layout.item_upload_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((ViewHolder) holder).showData(position);
    }

    @Override
    public int getItemCount() {
        return getList().size() >= 3 ? 3 : getList().size() + 1;
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.iv_appear_exception)
        ImageView ivAppearException;
        @BindView(R.id.tv_tips)
        TextView tvTips;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void showData(int position) {
            if (position >= getList().size()) {
                ivAppearException.setImageResource(R.mipmap.ic_exception_8);
            } else {
                ImageUtils.getPic(Constants.IMAGE_BASE_URL + getItem(position), ivAppearException, m_Context);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(itemView, position, "");
                    }
                }
            });
        }
    }
}
