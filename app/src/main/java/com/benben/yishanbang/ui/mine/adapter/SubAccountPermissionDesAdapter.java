package com.benben.yishanbang.ui.mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.mine.bean.SubAccountPermissionDesListBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/6
 * Time: 10:20
 */
public class SubAccountPermissionDesAdapter  extends AFinalRecyclerViewAdapter<SubAccountPermissionDesListBean> {

    public SubAccountPermissionDesAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_subaccount_permission_des, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;


        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(SubAccountPermissionDesListBean mPermissionDesListBean, int position) {
            tvName.setText(mPermissionDesListBean.getName());
        }
    }
}
