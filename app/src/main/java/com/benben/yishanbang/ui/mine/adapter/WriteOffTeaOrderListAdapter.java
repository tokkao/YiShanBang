package com.benben.yishanbang.ui.mine.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.widget.CircleImageView;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.ui.mine.bean.WriteOffTeaOrderDetailsBean;
import com.benben.yishanbang.ui.mine.bean.WriteOffTeaOrderListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:核销奶茶订单adapter
 */
public class WriteOffTeaOrderListAdapter extends AFinalRecyclerViewAdapter<WriteOffTeaOrderListBean> {

    Activity mActivity;

    public WriteOffTeaOrderListAdapter(Context ctx) {
        super(ctx);
        mActivity = (Activity) ctx;
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_write_off_tea_order, parent, false));
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
        @BindView(R.id.tv_count_down)
        TextView tvCountDown;
        @BindView(R.id.rlv_list)
        RecyclerView rlvList;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_date)
        TextView tvDate;

        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(WriteOffTeaOrderListBean mWriteOffTeaOrderListBean, int position) {

            //昵称
            tvName.setText(mWriteOffTeaOrderListBean.getUser().getNickname());
            //倒计时

            //金额
            tvMoney.setText(mWriteOffTeaOrderListBean.getMilkTeaOrderVo().getGoodsMoney());
            //购买日期
            tvDate.setText("购买日期:"+mWriteOffTeaOrderListBean.getMilkTeaOrderVo().getOrderTime());
            //头像
            ImageUtils.getPic(Constants.IMAGE_BASE_URL + mWriteOffTeaOrderListBean.getUser().getAvatar(), civAvatar, m_Context, R.mipmap.icon_default_avatar);

            rlvList.setLayoutManager(new LinearLayoutManager(m_Context));
            WriteOffTeaOrderGoodsListAdapter orderAdapter = new WriteOffTeaOrderGoodsListAdapter(m_Context);
            rlvList.setAdapter(orderAdapter);
            List<WriteOffTeaOrderDetailsBean.MilkTeaOrderVoBean.MilkTeaOrderGoodsVosBean> milkTeaOrderGoodsVosBeans = new ArrayList<>();
            List<WriteOffTeaOrderListBean.MilkTeaOrderVoBean.MilkTeaOrderGoodsVosBean> milkTeaOrderGoodsVos = mWriteOffTeaOrderListBean.getMilkTeaOrderVo().getMilkTeaOrderGoodsVos();
            //格式化商品bean
            for (int i = 0; i < milkTeaOrderGoodsVos.size(); i++) {
                WriteOffTeaOrderDetailsBean.MilkTeaOrderVoBean.MilkTeaOrderGoodsVosBean milkTeaOrderGoodsVosBean = new WriteOffTeaOrderDetailsBean.MilkTeaOrderVoBean.MilkTeaOrderGoodsVosBean();
                milkTeaOrderGoodsVosBean.setGoodsName(milkTeaOrderGoodsVos.get(i).getGoodsName());
                milkTeaOrderGoodsVosBean.setGoodsCount(milkTeaOrderGoodsVos.get(i).getGoodsCount());
                milkTeaOrderGoodsVosBean.setId(milkTeaOrderGoodsVos.get(i).getId());
                milkTeaOrderGoodsVosBeans.add(milkTeaOrderGoodsVosBean);
            }
            orderAdapter.refreshList(milkTeaOrderGoodsVosBeans);
           /* rlvList.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return itemView.onTouchEvent(motionEvent);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, afterMyTeaBean);
                    }
                }
            });*/
        }
    }
}