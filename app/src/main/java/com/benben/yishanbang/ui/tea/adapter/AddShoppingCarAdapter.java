package com.benben.yishanbang.ui.tea.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.tea.bean.ShoppingCarGoodsBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/15 0015
 * Describe:添加购物车（选择规格）
 */
public class AddShoppingCarAdapter extends AFinalRecyclerViewAdapter<ShoppingCarGoodsBean> {

    public AddShoppingCarAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_add_shopping_car, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {

        View itemView;
        @BindView(R.id.btn_normal_temp)
        Button btnNormalTemp;
        public CommonViewHolder(View view) {
            super(view);
            itemView = view;
            ButterKnife.bind(this,view);
        }

        private void setContent(ShoppingCarGoodsBean addShoppingCarBean, int position) {

        }
    }
}
