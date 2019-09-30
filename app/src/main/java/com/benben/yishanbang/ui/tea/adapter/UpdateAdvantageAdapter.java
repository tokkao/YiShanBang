package com.benben.yishanbang.ui.tea.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.tea.bean.AdvantageLabelBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/16 0016
 * Describe:他的优势
 */
public class UpdateAdvantageAdapter extends AFinalRecyclerViewAdapter<AdvantageLabelBean> {

    public UpdateAdvantageAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(m_Inflater.inflate(R.layout.item_advantage, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((ViewHolder) holder).setContent(getItem(position), position);
    }

    public class ViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_label)
        TextView tvLabel;
        @BindView(R.id.rlyt_parent)
        RelativeLayout rlytParent;
        View itemView;

        public ViewHolder(View view) {
            super(view);
            itemView = view;
            ButterKnife.bind(this, view);
        }

        private void setContent(AdvantageLabelBean advantageBean, int position) {
            if (advantageBean.isSelected()) {
                tvLabel.setTextColor(m_Context.getResources().getColor(R.color.white));
                rlytParent.setBackgroundResource(R.drawable.shape_3radius_orange_f67617);
            } else {
                tvLabel.setTextColor(m_Context.getResources().getColor(R.color.money_color));
                rlytParent.setBackgroundResource(R.drawable.shape_3radius_border);
            }
            tvLabel.setText(advantageBean.getMage());
            //点击item
            rlytParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int count = 0;
                    for (int i = 0; i < getList().size(); i++) {
                        if (advantageBean.isSelected()) {
                            count++;
                        }
                    }
                    //最多选择四个
                    if (count >= 4) return;
                    advantageBean.setSelected(!advantageBean.isSelected());
                    notifyDataSetChanged();
                }
            });

        }
    }
    //拼接选中的标签id   以逗号隔开
    public String getLabelIds() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < getList().size(); i++) {
            if (getList().get(i).isSelected()) {
                if (i == (getList().size() - 1)) {
                    stringBuilder.append(getList().get(i).getId());
                } else {
                    stringBuilder.append(getList().get(i).getId()).append(",");

                }
            }

        }
        return stringBuilder.toString();
    }
}
