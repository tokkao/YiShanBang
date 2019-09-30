package com.benben.yishanbang.ui.mine.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.widget.CircleImageView;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.ui.home.activity.QRCodeActivity;
import com.benben.yishanbang.ui.mine.bean.OrderManageTeaListBean;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:订单管理adapter
 */
public class OrderManageTeaListAdapter extends AFinalRecyclerViewAdapter<OrderManageTeaListBean> {

    private int mOrderType;
    Activity mActivity;

    public OrderManageTeaListAdapter(Context ctx) {
        super(ctx);
        mActivity = (Activity) ctx;
    }


    public void setOrderType(int mOrderType) {
        this.mOrderType = mOrderType;
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_tea_order_manage_list, parent, false));
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
        @BindView(R.id.tv_order_num)
        TextView tvOrderNum;
        @BindView(R.id.rlv_list)
        RecyclerView rlvList;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;
        @BindView(R.id.tv_write_off)
        TextView tvWriteOff;
        @BindView(R.id.llyt_write_off)
        RelativeLayout llytWriteOff;

        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(OrderManageTeaListBean mOrderManageListBean, int position) {
            //头像
            ImageUtils.getPic(Constants.IMAGE_BASE_URL +mOrderManageListBean.getHeadImg(), civAvatar, mActivity, R.mipmap.icon_default_avatar);
            //昵称
            tvName.setText(mOrderManageListBean.getUserNick());
            //日期
            tvDate.setText(mOrderManageListBean.getTime());
            //订单号
            tvOrderNum.setText("订单号：" + mOrderManageListBean.getOrderNo());
            //价格
            tvMoney.setText(" ¥" + mOrderManageListBean.getMoney());
            //订单状态
            if (mOrderType == 1) {
                tvOrderStatus.setText("新订单");
            } else if (mOrderType == 2) {
                tvOrderStatus.setText("进行中");
            } else {
                tvOrderStatus.setText("已完成");
            }
            //核销？
            llytWriteOff.setVisibility(mOrderType == 2 ? View.VISIBLE : View.GONE);

            rlvList.setLayoutManager(new GridLayoutManager(m_Context, 3));
            OrderManageGoodsImageAdapter orderManageGoodsImageAdapter = new OrderManageGoodsImageAdapter(mActivity);
            rlvList.setAdapter(orderManageGoodsImageAdapter);
            //解决嵌套recyclerview点击事件冲突
            rlvList.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return itemView.onTouchEvent(motionEvent);
                }
            });
            //获取图片数组转为集合
            String[] imgArray = mOrderManageListBean.getGoodsImg().split(",");
            List<String> imgList = Arrays.asList(imgArray);
            orderManageGoodsImageAdapter.refreshList(imgList);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, mOrderManageListBean);
                    }
                }
            });

            llytWriteOff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.startActivity(new Intent(mActivity,QRCodeActivity.class));
                }
            });
        }
    }
}