package com.benben.yishanbang.ui.tea.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.tea.bean.UserDetailsInfoBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/14 0014
 * Describe:他的技能
 */
public class UserSkillAdapter extends AFinalRecyclerViewAdapter<UserDetailsInfoBean.SkillsBean> {



    public UserSkillAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(m_Inflater.inflate(R.layout.item_user_details_skill, parent, false));
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
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(UserDetailsInfoBean.SkillsBean userSkillBean, int position) {
            tvUserSkillName.setText(userSkillBean.getSkillName());
            tvUserSkillLevel.setText(userSkillBean.getSkillGrade());
            //显示1显示0不显示
//            tvUserAuthStatus.setText();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, userSkillBean);
                    }
                }
            });
        }
    }
}
