package com.benben.yishanbang.ui.service.adapter;


import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.service.bean.ServiceCateDetailsListBean;
import com.benben.yishanbang.ui.service.bean.ServiceCateListBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:服务分类adapter
 */
public class ServiceCateListAdapter extends AFinalRecyclerViewAdapter<ServiceCateListBean> {

    public ServiceCateListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_service_cate_list, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.tv_cate_name)
        TextView tvCateName;
        @BindView(R.id.rlv_list)
        RecyclerView rlvList;

        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(ServiceCateListBean mServiceCateListBean, int position) {
            tvCateName.setText(mServiceCateListBean.getCate_name());
            rlvList.setLayoutManager(new GridLayoutManager(m_Context, 4));
            ServiceCateDetailsListAdapter adapter = new ServiceCateDetailsListAdapter(m_Context);
            rlvList.setAdapter(adapter);
            rlvList.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return itemView.onTouchEvent(motionEvent);
                }
            });
            ArrayList<ServiceCateDetailsListBean> list = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                list.add(new ServiceCateDetailsListBean());
            }
            adapter.refreshList(list);
        }
    }
}