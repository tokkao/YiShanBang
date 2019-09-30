package com.benben.yishanbang.ui.mine.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.mine.bean.UserBill;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:交易明细adapter
 */
public class AccountDetailsListAdapter extends AFinalRecyclerViewAdapter<UserBill> {

    public AccountDetailsListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_account_details_list, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_money)
        TextView tvMoney;

        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(UserBill bill, int position) {
            tvDate.setText(bill.getCreate_date());

            tvMoney.setTextColor(bill.getSign().equals("+") ? Color.rgb(0xf6, 0x76, 0x17) : Color.rgb(0x33, 0x33, 0x33));

            tvMoney.setText(bill.getSign() + bill.getChange_money());

            switch (bill.getBill_type()) {//流水类型 1 充值 2 消费 2 发布服务 4 完成服务 5 新团员注册  6 提现 7 佣金
                case "1":
                    tvName.setText("充值");
                    break;
                case "2":
                    tvName.setText("消费");
                    break;
                case "3":
                    tvName.setText("发布服务");
                    break;
                case "4":
                    tvName.setText("完成服务");
                    break;
                case "5":
                    tvName.setText("新团员注册");
                    break;
                case "6":
                    tvName.setText("提现");
                    break;
                case "7":
                    tvName.setText("佣金");
                    break;
            }
        }
    }
}