package com.benben.yishanbang.ui.tea.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.config.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/14 0014
 * Describe:照片动态 照片列表
 */
public class UserImageDynamicImgListAdapter extends AFinalRecyclerViewAdapter<String> {

    public UserImageDynamicImgListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(m_Inflater.inflate(R.layout.item_user_image_dynamic_list, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((ViewHolder) holder).setContent(getList().get(position), position);
    }

    public class ViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.iv_img)
        ImageView ivImg;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        private void setContent(String imgUrl, int position) {

            ImageUtils.getPic(Constants.IMAGE_BASE_URL+imgUrl, ivImg, m_Context, R.mipmap.ic_default_pic);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, imgUrl);
                    }
                }
            });
        }
    }
}
