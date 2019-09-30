package com.benben.yishanbang.ui.service.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.ui.service.bean.ServeTypeInfoBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:服务分类详情adapter
 */
public class ServeTypeDetailsListAdapter extends AFinalRecyclerViewAdapter<ServeTypeInfoBean.ServeTypeBean> {


    public ServeTypeDetailsListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_service_cate_details_list, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.iv_cate_img)
        ImageView ivCateImg;
        @BindView(R.id.tv_cate_name)
        TextView tvCateName;

        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(ServeTypeInfoBean.ServeTypeBean bean, int position) {
            tvCateName.setText(bean.getName());
            String imgUrl = bean.getUrl();
            if (!StringUtils.isEmpty(imgUrl)) {
                if (imgUrl.startsWith("http")) {
                    ImageUtils.getPic(bean.getUrl(), ivCateImg, m_Context, R.mipmap.icon_default_avatar);
                } else {
                    ImageUtils.getPic(Constants.IMAGE_BASE_URL + bean.getUrl(), ivCateImg, m_Context, R.mipmap.icon_default_avatar);
                }
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(view,position,bean);
                    }
                }
            });
        }
    }
}