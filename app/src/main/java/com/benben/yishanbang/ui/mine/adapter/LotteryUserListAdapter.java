package com.benben.yishanbang.ui.mine.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benben.commoncore.widget.CircleImageView;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.mine.bean.LotteryLuckyDrawListBean;
import com.benben.yishanbang.ui.mine.bean.LotteryUserListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:用户池
 */
public class LotteryUserListAdapter extends AFinalRecyclerViewAdapter<LotteryUserListBean> {


    private List<LotteryLuckyDrawListBean> drawListBeanList;

    public LotteryUserListAdapter(Context ctx) {
        super(ctx);
    }

    public void setDrawListBeanList(List<LotteryLuckyDrawListBean> drawListBeanList) {
        this.drawListBeanList = drawListBeanList;
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_lottery_user_list, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.civ_avatar)
        CircleImageView civAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;

        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(LotteryUserListBean mLotteryUserListBean, int position) {

        }
    }
}