package com.benben.yishanbang.ui.service.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.ui.service.activity.ReleaseNormalServiceActivity;
import com.benben.yishanbang.ui.service.bean.ServeTypeInfoBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:服务分类adapter
 */
public class ServeTypeListAdapter extends AFinalRecyclerViewAdapter<ServeTypeInfoBean> {

    Activity mActivity;
    //true 从发布页面进来  选择分类在返回数据 false 从分类页面进入发布页面
    private boolean isRelease;

    public ServeTypeListAdapter(Context ctx, boolean isRelease) {
        super(ctx);
        mActivity = (Activity) ctx;
        this.isRelease = isRelease;
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

        private void setContent(ServeTypeInfoBean bean, int position) {
            tvCateName.setText(bean.getName());
            rlvList.setLayoutManager(new GridLayoutManager(m_Context, 4));
            ServeTypeDetailsListAdapter adapter = new ServeTypeDetailsListAdapter(m_Context);
            rlvList.setAdapter(adapter);
            rlvList.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return itemView.onTouchEvent(motionEvent);
                }
            });
            adapter.refreshList(bean.getServerType());

            adapter.setOnItemClickListener(new OnItemClickListener<ServeTypeInfoBean.ServeTypeBean>() {
                @Override
                public void onItemClick(View view, int position, ServeTypeInfoBean.ServeTypeBean model) {
                    if (isRelease) {
                        Intent intent = new Intent();
                        intent.putExtra("type_id", model.getId());
                        intent.putExtra("type_name", model.getName());
                        mActivity.setResult(202, intent);
                        mActivity.finish();
                    } else {
                        mActivity.startActivity(new Intent(mActivity, ReleaseNormalServiceActivity.class).putExtra("type_id", model.getId()).putExtra("type_name", model.getName()).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 5));
                    }

                }

                @Override
                public void onItemLongClick(View view, int position, ServeTypeInfoBean.ServeTypeBean model) {

                }
            });
        }
    }
}