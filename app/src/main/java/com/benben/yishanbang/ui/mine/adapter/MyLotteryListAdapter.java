package com.benben.yishanbang.ui.mine.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.mine.bean.MyLotteryListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:抽奖adapter
 */
public class MyLotteryListAdapter extends AFinalRecyclerViewAdapter<MyLotteryListBean> {


    private List<MyLotteryListBean> listBeanList;

    public MyLotteryListAdapter(Context ctx) {
        super(ctx);

    }
    public void setListBeanList(List<MyLotteryListBean> listBeanList) {
        this.listBeanList = listBeanList;
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_my_lottery_list, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(MyLotteryListBean myLotteryListBean, int position) {


            //tvGoodsName.setText(listBeanList.get(position).getGoodsName().get(0));//商品名称
            tvTime.setText(listBeanList.get(position).getStartTime());//日期
            tvAddress.setText(listBeanList.get(position).getShopPlace());//地址

            if(tvStatus.getText().toString().equals("0")){//状态
                //0：已结束
                tvStatus.setText("已结束");
            }else if(tvStatus.getText().toString().equals("1")){
                //1：正在进行
                tvStatus.setText("正在进行");
            }else{
                //2：未开始
                tvStatus.setText("未开始");
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(view,position,myLotteryListBean);
                    }
                }
            });
        }
    }
}