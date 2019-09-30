package com.benben.yishanbang.ui.tea.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.tea.bean.UpdateSkillBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/16 0016
 * Describe:修改用户要展示技能
 */
public class UpdateUserSkillAdapter extends AFinalRecyclerViewAdapter<UpdateSkillBean> {

    public UpdateUserSkillAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(m_Inflater.inflate(R.layout.item_update_skill, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((ViewHolder) holder).setContent(getItem(position), position);
    }

    public class ViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.tv_user_skill_name)
        TextView tvUserSkillName;
        @BindView(R.id.tv_user_skill_level)
        TextView tvUserSkillLevel;
        @BindView(R.id.tv_user_auth_status)
        TextView tvUserAuthStatus;
        View itemView;

        public ViewHolder(View view) {
            super(view);
            itemView = view;
            ButterKnife.bind(this, view);
        }

        private void setContent(UpdateSkillBean updateSkillBean, int position) {
            //1显示0不显示
            tvUserSkillName.setSelected(1 == updateSkillBean.getStatus());
            tvUserSkillLevel.setText(updateSkillBean.getSkillGrade());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateSkillBean.setSelected(!updateSkillBean.isSelected());
                    notifyDataSetChanged();
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, updateSkillBean);
                    }
                }
            });
        }
    }
}
