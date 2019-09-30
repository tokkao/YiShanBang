package com.benben.yishanbang.ui.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.mine.bean.LotteryActiveBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lff
 * on 2019/8/26 0026
 * Describe:抽奖活动  一到三等奖 的奖品
 */
public class LotteryActiveAdapter extends AFinalRecyclerViewAdapter<LotteryActiveBean.PrizeVosBean> {


    public LotteryActiveAdapter(Context ctx) {
        super(ctx);
    }


    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(m_Context).inflate(R.layout.item_lottery_active, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((DateViewHolder) holder).setContent(position, getList().get(position));
    }

    public class DateViewHolder extends BaseRecyclerViewHolder {

        View itemView;
        @BindView(R.id.tv_first_prize)
        TextView tvFirstPrize;

        public DateViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(int position, LotteryActiveBean.PrizeVosBean mLotteryActiveBean) {

            tvFirstPrize.setText(mLotteryActiveBean.getMessage());
        }
    }
}
