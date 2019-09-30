package com.benben.yishanbang.ui.tea.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.benben.commoncore.utils.InputCheckUtil;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.tea.bean.UserDetailsInfoBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/16 0016
 * Describe:修改线下服务价格适配器
 */
public class UpdateServicePriceAdapter extends AFinalRecyclerViewAdapter<UserDetailsInfoBean.ServeTypeBean> {


    public UpdateServicePriceAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new MViewHolder(m_Inflater.inflate(R.layout.item_update_service_price, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((MViewHolder) holder).setContent(getItem(position), position);
    }

    public class MViewHolder extends BaseRecyclerViewHolder {
        View itemView;

        @BindView(R.id.tv_service_name)
        TextView tvServiceName;
        @BindView(R.id.et_service_price)
        EditText etServicePrice;

        public MViewHolder(View view) {
            super(view);
            itemView = view;
            ButterKnife.bind(this, view);
        }

        private void setContent(UserDetailsInfoBean.ServeTypeBean updateServicePriceBean, int position) {
            InputCheckUtil.filterEmoji(etServicePrice, m_Activity);
            tvServiceName.setText(updateServicePriceBean.getServeTypeEntity().getName());
            etServicePrice.setText(updateServicePriceBean.getPrice());
            etServicePrice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    updateServicePriceBean.setPrice(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
            etServicePrice.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    etServicePrice.setFocusable(true);
                    etServicePrice.setFocusableInTouchMode(true);
                    return false;
                }
            });
        }
    }
}
