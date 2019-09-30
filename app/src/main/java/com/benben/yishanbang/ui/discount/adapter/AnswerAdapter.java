package com.benben.yishanbang.ui.discount.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.ui.discount.bean.QuestionBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/6
 * Time: 16:10
 */
public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private List<QuestionBean> list;
    private Context mContext;

    public AnswerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public List<QuestionBean> getList() {
        return list;
    }

    public void setList(List<QuestionBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(View.inflate(mContext, R.layout.item_answer, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setContent(position, list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_a)
        TextView tvA;
        @BindView(R.id.ll_a)
        LinearLayout llA;
        @BindView(R.id.tv_b)
        TextView tvB;
        @BindView(R.id.ll_b)
        LinearLayout llB;
        @BindView(R.id.tv_c)
        TextView tvC;
        @BindView(R.id.ll_c)
        LinearLayout llC;
        @BindView(R.id.tv_d)
        TextView tvD;
        @BindView(R.id.ll_d)
        LinearLayout llD;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setContent(int position, QuestionBean questionBean) {

            tvTitle.setText(questionBean.getTitle());
            tvContent.setText(questionBean.getMags());
            tvA.setText("A."+questionBean.getMsgA());
            tvB.setText("B."+questionBean.getMsgB());
            tvC.setText("C."+questionBean.getMsgC());
            tvD.setText("D."+questionBean.getMsgD());

            llA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    questionBean.setA_status(!tvA.isSelected());
                    tvA.setSelected(!tvA.isSelected());

                }
            });
            llB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    questionBean.setB_status(!tvB.isSelected());
                    tvB.setSelected(!tvB.isSelected());
                }
            });
            llC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    questionBean.setC_status(!tvC.isSelected());
                    tvC.setSelected(!tvC.isSelected());
                }
            });
            llD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    questionBean.setD_status(!tvD.isSelected());
                    tvD.setSelected(!tvD.isSelected());
                }
            });
        }
    }

    //获取选中的答案 以字符串拼接 逗号隔开
    public List<String> getSelectedAnswer() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < getList().size(); i++) {
            StringBuilder sb = new StringBuilder();
            if (getList().get(i).isA_status()) {
                sb.append("a");
            }
            if (getList().get(i).isB_status()) {
                sb.append("b");
            }
            if (getList().get(i).isC_status()) {
                sb.append("c");
            }
            if (getList().get(i).isD_status()) {
                sb.append("d");
            }
            list.add(sb.toString());
        }
        return list;

    }

    //每个题目都做了
    public boolean isSelectedAll(){
        for (int i = 0; i < getList().size(); i++) {
            if(getList().get(i).isA_status() ||getList().get(i).isB_status() ||getList().get(i).isC_status() ||getList().get(i).isD_status()){
                continue;
            }
            return false;
        }
        return true;
    }
}
