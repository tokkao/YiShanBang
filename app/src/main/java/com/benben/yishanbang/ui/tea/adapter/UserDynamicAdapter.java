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
import com.benben.yishanbang.ui.tea.bean.UserDetailsInfoBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/14 0014
 * Describe:用户详情（预览他的动态适配器）
 */
public class UserDynamicAdapter extends AFinalRecyclerViewAdapter<UserDetailsInfoBean.DynamicBean> {


    public UserDynamicAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(m_Inflater.inflate(R.layout.item_user_details_dynamic, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((ViewHolder) holder).setContent(getItem(position), position);
    }

    public class ViewHolder extends BaseRecyclerViewHolder {


        @BindView(R.id.iv_dynamic)
        ImageView ivDynamic;
        @BindView(R.id.iv_play)
        ImageView ivPlay;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(UserDetailsInfoBean.DynamicBean mDynamicBean, int position) {
            //0 图片  1视频  隐藏播放按钮
            if ("0".equals(mDynamicBean.getType())) {
                ivPlay.setVisibility(View.GONE);
                String[] split = mDynamicBean.getImgUrl().split(",");
                if (split.length > 0) {
                    ImageUtils.getPic(Constants.IMAGE_BASE_URL + split[0], ivDynamic, m_Context, R.mipmap.ic_default_pic);
                }

            } else {
                ivPlay.setVisibility(View.VISIBLE);
                ImageUtils.loadCover(ivDynamic, mDynamicBean.getVideo(), m_Context);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, mDynamicBean);
                    }
                }
            });
        }
    }
}
