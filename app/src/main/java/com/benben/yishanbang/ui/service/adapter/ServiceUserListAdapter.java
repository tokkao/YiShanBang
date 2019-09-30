package com.benben.yishanbang.ui.service.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.widget.CircleImageView;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.ui.service.bean.NotifyUserListBean;
import com.benben.yishanbang.ui.service.bean.ServiceUserListBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:赴约用户列表adapter
 */
public class ServiceUserListAdapter extends AFinalRecyclerViewAdapter<NotifyUserListBean> {

    public ServiceUserListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_service_user_list, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.iv_cate_img)
        CircleImageView ivCateImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_sex_age)
        TextView tvSexAge;
        @BindView(R.id.tv_height)
        TextView tvHeight;
        @BindView(R.id.tv_weight)
        TextView tvWeight;
        @BindView(R.id.rlv_list)
        RecyclerView rlvList;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(NotifyUserListBean mNotifyUserListBean, int position) {
            ImageUtils.getPic(Constants.IMAGE_BASE_URL + mNotifyUserListBean.getUser().getAvatar(), ivCateImg, m_Context, R.mipmap.icon_default_avatar);
            tvName.setText(mNotifyUserListBean.getUser().getNickname());
            tvSexAge.setText(mNotifyUserListBean.getUser().getAge());
            tvSexAge.setCompoundDrawablesWithIntrinsicBounds(m_Context.getResources().getDrawable("0".equals(mNotifyUserListBean.getUser().getSex()) ? R.mipmap.ic_man_logo : R.mipmap.ic_women_logo), null, null, null);
            tvHeight.setText(mNotifyUserListBean.getUser().getMessageHight() + "cm");
            tvWeight.setText(mNotifyUserListBean.getUser().getMessageWeight() + "kg");

            tvStatus.setSelected(mNotifyUserListBean.isSelected());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    singleSelect(position);
                }
            });

            ivCateImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, mNotifyUserListBean);
                    }
                }
            });
        }
    }


    //单选
    private void singleSelect(int position) {
        for (int i = 0; i < getList().size(); i++) {
            getList().get(i).setSelected(i == position);
        }
        notifyDataSetChanged();
    }
}