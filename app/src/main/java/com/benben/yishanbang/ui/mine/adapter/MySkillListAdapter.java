package com.benben.yishanbang.ui.mine.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.StringUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.tea.bean.UpdateSkillBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:技能列表adapter
 */
public class MySkillListAdapter extends AFinalRecyclerViewAdapter<UpdateSkillBean> {

    public MySkillListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(m_Inflater.inflate(R.layout.item_my_skill_list, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommonViewHolder) holder).setContent(getItem(position), position);
    }

    public class CommonViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_skill_name)
        TextView tvSkillName;
        @BindView(R.id.tv_skill_level)
        TextView tvSkillLevel;
        @BindView(R.id.tv_auth_status)
        TextView tvAuthStatus;
        @BindView(R.id.rlv_list)
        RecyclerView rlvList;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        View itemView;

        public CommonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void setContent(UpdateSkillBean bean, int position) {
            tvSkillName.setText(bean.getSkillName());
            tvSkillLevel.setText(bean.getSkillGrade());
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(m_Context, 3);
            rlvList.setLayoutManager(layoutManager);

            RecyclerView.Adapter tempAdapter = new CommonPhotoAdapter(m_Context);
            rlvList.setAdapter(tempAdapter);

            if (!StringUtils.isEmpty(bean.getSkillImgUrl())) {
                rlvList.setVisibility(View.VISIBLE);
                List<String> tempList = new ArrayList<>();
                String[] split = bean.getSkillImgUrl().split(",");
                for (int i = 0; i < split.length; i++) {
                    if (!StringUtils.isEmpty(split[i])) {
                        tempList.add(split[i]);
                    }
                }
                ((CommonPhotoAdapter) tempAdapter).refreshList(tempList);
            } else {
                rlvList.setVisibility(View.GONE);
            }

            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, bean);
                    }
                }
            });
        }
    }
}