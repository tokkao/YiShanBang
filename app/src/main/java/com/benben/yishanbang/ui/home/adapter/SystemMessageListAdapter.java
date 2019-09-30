package com.benben.yishanbang.ui.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benben.commoncore.widget.CircleImageView;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.home.bean.MessageListBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:zhn
 * Time:2019/5/13 0013 16:36
 * <p>
 * 消息列表Adapter
 */
public class SystemMessageListAdapter extends AFinalRecyclerViewAdapter<MessageListBean.AnnouncementListBean> {


    public SystemMessageListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new MessageListViewHolder(m_Inflater.inflate(R.layout.item_system_message_list, parent,
                false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((MessageListViewHolder) holder).setContent(position, getItem(position));
    }

    class MessageListViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.civ_avatar)
        CircleImageView civAvatar;
        @BindView(R.id.tv_content)
        TextView tvContent;

        public MessageListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setContent(int position, MessageListBean.AnnouncementListBean data) {
            tvTime.setText(data.getSend_time());
            tvContent.setText(data.getMsg_content());
        }
    }

}

