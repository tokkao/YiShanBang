package com.benben.yishanbang.ui.mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.discount.bean.CardShopCateBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/6
 * Time: 12:02
 * 商品类型
 */
public class WindowManageGoodsTypeListAdapter extends AFinalRecyclerViewAdapter<CardShopCateBean> {


    public WindowManageGoodsTypeListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_window_manage_goods_type_list, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }


    public class CommonViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.tv_type)
        TextView tvType;

        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(CardShopCateBean mMyGoodsManageListBean, int position) {
            tvType.setText(mMyGoodsManageListBean.getCategoryName());
            tvType.setBackground(mMyGoodsManageListBean.isSelected() ? m_Context.getResources().getDrawable(R.drawable.shape_border_radius3_green_theme) : m_Context.getResources().getDrawable(R.drawable.shape_border_radius3_grey_99999));
            tvType.setTextColor(mMyGoodsManageListBean.isSelected() ? m_Context.getResources().getColor(R.color.theme) : m_Context.getResources().getColor(R.color.color_666666));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    singleSelect(position);
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, mMyGoodsManageListBean);
                    }
                }
            });
        }


    }

    //单选
    private void singleSelect(int position) {
        for (int i = 0; i < getList().size(); i++) {
            getList().get(i).setSelected(i == position);
        }
        notifyDataSetChanged();
    }

}
