package com.benben.yishanbang.ui.tea.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.ui.tea.bean.TeaBean;
import com.shehuan.niv.NiceImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/12 0012
 * Describe:自营奶茶适配器
 */
public class TeaShopListAdapter extends AFinalRecyclerViewAdapter<TeaBean> {

    public TeaShopListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_tea, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.iv_tea_item)
        NiceImageView teaItemIcon;
        @BindView(R.id.tea_item_shop)
        TextView teaItemShop;
        @BindView(R.id.tea_item_km)
        TextView teaItemKm;
        @BindView(R.id.tea_item_time_start)
        TextView teaItemTimeStart;
        @BindView(R.id.tea_item_time_end)
        TextView teaItemTimeEnd;
        @BindView(R.id.tea_item_address)
        TextView teaItemAddress;
        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            itemView = view;
            ButterKnife.bind(this, view);
        }

        private void setContent(TeaBean teaBean, int position) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, teaBean);
                    }
                }
            });
            //设置商店标题
            teaItemShop.setText(getList().get(position).getShopName());
            //设置营业时间
            teaItemTimeStart.setText(getList().get(position).getShopStarttime());//开始时间
            teaItemTimeEnd.setText(getList().get(position).getShopEndtime());//结束时间
            //设置商店距离
            teaItemKm.setText(getList().get(position).getShopDistance() + "km");
            //设置商店地址
            teaItemAddress.setText(getList().get(position).getShopPlace());
            ImageUtils.getPic(Constants.IMAGE_BASE_URL + getList().get(position).getShoptImg(), teaItemIcon, m_Context);
        }
    }

}
