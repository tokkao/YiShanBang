package com.benben.yishanbang.ui.mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.ui.mine.bean.WindowManageGoodsListBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/6
 * Time: 12:02
 * 商品列表
 */
public class WindowManageGoodsListAdapter extends AFinalRecyclerViewAdapter<WindowManageGoodsListBean> {


    private int enterType = 0;

    public void setEnterType(int enterType) {
        this.enterType = enterType;
    }

    public WindowManageGoodsListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_window_manage_goods_list, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }


    public class CommonViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_favor_sales)
        TextView tvFavorSales;
        @BindView(R.id.tv_commission)
        TextView tvCommission;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_option)
        TextView tvOption;
        @BindView(R.id.rlyt_option)
        RelativeLayout rlytOption;
        @BindView(R.id.tv_profit_commission)
        TextView tvProfitCommission;
        @BindView(R.id.iv_window_img)
        ImageView ivWindowImg;

        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(WindowManageGoodsListBean mWindowManageGoodsListBean, int position) {
            tvName.setText(mWindowManageGoodsListBean.getName());
            tvFavorSales.setText(mWindowManageGoodsListBean.getNice() + "好评 月销量：" + mWindowManageGoodsListBean.getNumber());
//            tvCommission.setText();
            tvMoney.setText(" ¥" + mWindowManageGoodsListBean.getPrice());
            tvProfitCommission.setText("");
            ImageUtils.getPic(Constants.IMAGE_BASE_URL + mWindowManageGoodsListBean.getImgUrl(), ivWindowImg, m_Context);
            if (enterType == 0) {
                rlytOption.setVisibility(View.VISIBLE);
                rlytOption.setBackgroundResource(R.drawable.shape_radius4_grey_e8e8e8);
                tvOption.setTextColor(m_Context.getResources().getColor(R.color.color_999999));
                tvOption.setText("删除");
                tvProfitCommission.setVisibility(View.GONE);
                tvCommission.setVisibility(View.VISIBLE);
            } else if (enterType == 11 || enterType == 10) {
                rlytOption.setBackgroundResource(R.drawable.shape_radius4_orage_fbd9bf);
                tvOption.setTextColor(m_Context.getResources().getColor(R.color.color_F67617));
                tvOption.setText("选定");
                rlytOption.setVisibility(View.VISIBLE);
                tvProfitCommission.setVisibility(View.GONE);
                tvCommission.setVisibility(View.VISIBLE);
            } else {
                rlytOption.setVisibility(View.GONE);
                tvProfitCommission.setVisibility(View.VISIBLE);
                tvCommission.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, mWindowManageGoodsListBean);
                    }
                }
            });

        }

    }
}