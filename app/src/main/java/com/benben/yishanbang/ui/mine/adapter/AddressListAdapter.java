package com.benben.yishanbang.ui.mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.mine.bean.AddressBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressListAdapter extends AFinalRecyclerViewAdapter<AddressBean> {

    public AddressListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected AddressListViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new AddressListViewHolder(m_Inflater.inflate(R.layout.item_address, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((AddressListViewHolder) holder).setContent(position, getItem(position));
    }

    public class AddressListViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_set_default_address)
        TextView tvSetDefaultAddress;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        View itemView;

        public AddressListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        public void setContent(int position, AddressBean data) {
            tvName.setText(data.getName());
            tvPhone.setText(data.getMobile());
//            是否默认地址
            if (data.getDefaultFlag().equals("1")) {
                tvSetDefaultAddress.setSelected(true);
                tvSetDefaultAddress.setText("默认收货地址");
            } else {
                tvSetDefaultAddress.setSelected(false);
                tvSetDefaultAddress.setText("设为默认地址");
            }
            tvAddress.setText(data.getDetailedAddress());

            //修改默认地址
            tvSetDefaultAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvSetDefaultAddress.setSelected(true);

                    if (mListener != null) {
                        mListener.changeAddressDefault(data);
                    }
                }
            });

            //删除地址
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.deleteAddress(data);
                    }
                }
            });
            //选择地址
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, position, data);
                    }
                }
            });
        }
    }

    //事件回调接口
    public interface AddressListListener {
        //修改默认地址
        void changeAddressDefault(AddressBean data);

        //删除地址
        void deleteAddress(AddressBean data);
    }

    private AddressListListener mListener;

    public void setListener(AddressListListener listener) {
        this.mListener = listener;
    }

}
