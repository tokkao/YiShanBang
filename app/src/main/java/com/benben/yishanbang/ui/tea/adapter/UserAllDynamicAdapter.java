package com.benben.yishanbang.ui.tea.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.ui.home.activity.VideoPlayActivity;
import com.benben.yishanbang.ui.tea.bean.UserDynamicBean;
import com.kongzue.dialog.v3.MessageDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/13 0013
 * Describe:他的动态（所有动态）适配器
 */
public class UserAllDynamicAdapter extends AFinalRecyclerViewAdapter<UserDynamicBean> {

    private boolean isMine;
    private int mType;
    private Activity mActivity;

    public UserAllDynamicAdapter(Context ctx, boolean isMine, int type) {
        super(ctx);
        mActivity = (Activity) ctx;
        this.isMine = isMine;
        this.mType = type;
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        VideoViewHolder videoViewHolder = new VideoViewHolder(m_Inflater.inflate(R.layout.item_user_video_dynamic, parent, false));
        ImageViewHolder imageViewHolder = new ImageViewHolder(m_Inflater.inflate(R.layout.item_user_image_dynamic, parent, false));
        if (mType == 0) {
            return viewType == 1 ? videoViewHolder : imageViewHolder;
        } else if (mType == 1) {
            return videoViewHolder;
        } else {
            return imageViewHolder;
        }

    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        if (mType == 0) {
            if (!"0".equals(getList().get(position).getType())) {
                ((VideoViewHolder) holder).setContent(getItem(position), position);
            } else {
                ((ImageViewHolder) holder).setContent(getItem(position), position);
            }
        } else if (mType == 1) {
            ((VideoViewHolder) holder).setContent(getItem(position), position);
        } else {
            ((ImageViewHolder) holder).setContent(getItem(position), position);
        }


    }

    @Override
    public int getItemViewType(int position) {
        //动态类型0为图片1为视频
        return "0".equals(getList().get(position).getType()) ? 0 : 1;
    }

    public class ImageViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_user_all_dynamic_day)
        TextView tvUserAllDynamicDay;
        @BindView(R.id.tv_user_all_dynamic_month)
        TextView tvUserAllDynamicMonth;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_delete)
        TextView tvDelete;
        @BindView(R.id.rlv_list)
        RecyclerView rlvList;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setContent(UserDynamicBean userDynamicBean, int position) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = formatter.parse(userDynamicBean.getTime());
                Calendar instance = Calendar.getInstance();
                instance.setTime(date);
                int month = instance.get(Calendar.MONTH);
                int day = instance.get(Calendar.DAY_OF_MONTH);
                tvUserAllDynamicMonth.setText((month + 1) + "月");
                tvUserAllDynamicDay.setText(day + "");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            tvContent.setText(userDynamicBean.getMessage());

            rlvList.setLayoutManager(new GridLayoutManager(mActivity, 3));
            UserImageDynamicImgListAdapter mImgAdapter = new UserImageDynamicImgListAdapter(mActivity);
            rlvList.setAdapter(mImgAdapter);
            String[] split = userDynamicBean.getImgUrl().split(",");
            List<String> list = Arrays.asList(split);
            mImgAdapter.refreshList(list);
            tvDelete.setVisibility(isMine ? View.VISIBLE : View.GONE);
            //删除
            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MessageDialog.show((AppCompatActivity) mActivity, "提示", "确定删除吗？", "确定", "取消")
                            .setOnOkButtonClickListener((baseDialog, v) -> {
                                //删除
                                if (mOnDeleteDynamicListener != null) {
                                    mOnDeleteDynamicListener.deleteDynamicListener(position, userDynamicBean);
                                }
                                return false;
                            });
                }
            });
        }
    }

    OnDeleteDynamicListener mOnDeleteDynamicListener;

    public void setOnDeleteDynamicListener(OnDeleteDynamicListener mOnDeleteDynamicListener) {
        this.mOnDeleteDynamicListener = mOnDeleteDynamicListener;
    }

    //删除动态回调
    public interface OnDeleteDynamicListener {

        void deleteDynamicListener(int position, UserDynamicBean mUserDynamicBean);

    }


    public class VideoViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_user_all_dynamic_day)
        TextView tvUserAllDynamicDay;
        @BindView(R.id.tv_user_all_dynamic_month)
        TextView tvUserAllDynamicMonth;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_delete)
        TextView tvDelete;
        @BindView(R.id.iv_user_dynamic_all)
        ImageView ivUserDynamicAll;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setContent(UserDynamicBean userDynamicBean, int position) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = formatter.parse(userDynamicBean.getTime());
                Calendar instance = Calendar.getInstance();
                instance.setTime(date);
                int month = instance.get(Calendar.MONTH);
                int day = instance.get(Calendar.DAY_OF_MONTH);
                tvUserAllDynamicMonth.setText((month + 1) + "月");
                tvUserAllDynamicDay.setText(day + "");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            tvContent.setText(userDynamicBean.getMessage());
            ImageUtils.loadCover(ivUserDynamicAll,userDynamicBean.getVideo(),mActivity);
            //播放视频动态
            ivUserDynamicAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    //bundle.putString("shop_id",videoBean.getShopId());
                    //bundle.putString("title",videoBean.getTitle());
                    bundle.putString("video",userDynamicBean.getVideo());

                    MyApplication.openActivity(mActivity, VideoPlayActivity.class, bundle);
                }
            });
            tvDelete.setVisibility(isMine ? View.VISIBLE : View.GONE);
            //删除
            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MessageDialog.show((AppCompatActivity) mActivity, "提示", "确定删除吗？", "确定", "取消")
                            .setOnOkButtonClickListener((baseDialog, v) -> {
                                //删除
                                if (mOnDeleteDynamicListener != null) {
                                    mOnDeleteDynamicListener.deleteDynamicListener(position, userDynamicBean);
                                }
                                return false;
                            });
                }
            });
        }
    }
}
