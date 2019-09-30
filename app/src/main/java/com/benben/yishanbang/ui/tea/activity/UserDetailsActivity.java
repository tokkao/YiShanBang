package com.benben.yishanbang.ui.tea.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.DateUtils;
import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.RxBus;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.commoncore.widget.CircleImageView;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.pop.PayPopupWindow;
import com.benben.yishanbang.ui.tea.adapter.UserAdvantageAdapter;
import com.benben.yishanbang.ui.tea.adapter.UserDynamicAdapter;
import com.benben.yishanbang.ui.tea.adapter.UserOffLinePriceAdapter;
import com.benben.yishanbang.ui.tea.adapter.UserSkillAdapter;
import com.benben.yishanbang.ui.tea.bean.UserDetailsInfoBean;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;

/**
 * Created by Administrator on 2019/8/13 0013
 * Describe:用户详情页面
 */
public class UserDetailsActivity extends BaseActivity {


    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_age)
    TextView tvUserAge;
    @BindView(R.id.tv_user_address)
    TextView tvUserAddress;
    @BindView(R.id.tv_user_km_for_me)
    TextView tvUserKmForMe;
    @BindView(R.id.tv_user_constellation)
    TextView tvUserConstellation;
    @BindView(R.id.tv_user_height)
    TextView tvUserHeight;
    @BindView(R.id.tv_user_weight)
    TextView tvUserWeight;
    @BindView(R.id.iv_user_head)
    CircleImageView ivUserHead;
    @BindView(R.id.tv_dynamic)
    TextView tvDynamic;
    @BindView(R.id.llyt_user_dynamic)
    LinearLayout llytUserDynamic;
    @BindView(R.id.rlv_user_detail_dynamic)
    RecyclerView rlvUserDetailDynamic;
    @BindView(R.id.tv_advantage)
    TextView tvAdvantage;
    @BindView(R.id.iv_user_update_advantage)
    ImageView ivUserUpdateAdvantage;
    @BindView(R.id.tv_advantage_title)
    TextView tvAdvantageTitle;
    @BindView(R.id.tv_advantage_des)
    TextView tvAdvantageDes;
    @BindView(R.id.rlv_user_detail_advantage)
    RecyclerView rlvUserDetailAdvantage;
    @BindView(R.id.llyt_user_advantage)
    LinearLayout llytUserAdvantage;
    @BindView(R.id.tv_skill)
    TextView tvSkill;
    @BindView(R.id.iv_user_update_skill)
    ImageView ivUserUpdateSkill;
    @BindView(R.id.rlv_user_detail_skill)
    RecyclerView rlvUserDetailSkill;
    @BindView(R.id.llyt_user_skill)
    LinearLayout llytUserSkill;
    @BindView(R.id.tv_work_time)
    TextView tvWorkTime;
    @BindView(R.id.iv_user_update_work_time)
    ImageView ivUserUpdateWorkTime;
    @BindView(R.id.tv_user_work_day)
    TextView tvUserWorkDay;
    @BindView(R.id.tv_user_week_day)
    TextView tvUserWeekDay;
    @BindView(R.id.llyt_user_work_time)
    LinearLayout llytUserWorkTime;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.iv_user_update_price)
    ImageView ivUserUpdatePrice;
    @BindView(R.id.rlv_user_detail_off_line_price)
    RecyclerView rlvUserDetailOffLinePrice;
    @BindView(R.id.llyt_user_service_price)
    LinearLayout llytUserServicePrice;
    @BindView(R.id.llyt_choose_user)
    LinearLayout llytChooseUser;
    @BindView(R.id.tv_user_price)
    TextView tvUserPrice;
    @BindView(R.id.btn_user_detail_choose_he)
    Button btnUserDetailChooseHe;
    @BindView(R.id.view_holder_line)
    View viewHolderLine;
    private UserDynamicAdapter mUserDynamicAdapter;//他的动态（预览动态适配器）
    private UserAdvantageAdapter mUserAdvantageAdapter;//他的优势
    private UserSkillAdapter mUserSkillAdapter;//他的技能
    private UserOffLinePriceAdapter mUserOffLinePriceAdapter;//线下服务价格
    private PayPopupWindow mPayPopupWindow;//支付弹窗
    private boolean isMine;
    //0 自己查看自己的资料  1 别人查看自己的资料
    private String mEnterType;
    private UserDetailsInfoBean mUserInfoBean;
    private String mWorkStartTime;
    private String mWorkEndTime;
    private String mSaturdayStartTime;
    private String mSaturdayEndTime;
    private String mSundayStartTime;
    private String mSundayEndTime;
    private boolean isIM;
    private String mUserId;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_details;
    }

    @Override
    protected void initData() {
        mEnterType = getIntent().getStringExtra(Constants.EXTRA_KEY_ENTER_TYPE);
        refreshUI();
        isMine = "0".equals(mEnterType);
        isIM = getIntent().getBooleanExtra("isIM", false);
        mUserId = getIntent().getStringExtra("user_id");
        //不是聊天订单 隐藏消息按钮
        rightTitle.setVisibility(isIM ? View.VISIBLE : View.GONE);
        /**
         * 他的动态
         */
        mUserDynamicAdapter = new UserDynamicAdapter(mContext);
        rlvUserDetailDynamic.setAdapter(mUserDynamicAdapter);
        mUserDynamicAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<UserDetailsInfoBean.DynamicBean>() {
            @Override
            public void onItemClick(View view, int position, UserDetailsInfoBean.DynamicBean model) {
                Intent intent = new Intent(UserDetailsActivity.this, UserDynamicActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_ENTER_TYPE, mEnterType);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, UserDetailsInfoBean.DynamicBean model) {

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlvUserDetailDynamic.setLayoutManager(layoutManager);

        /**
         * 他的优势
         */
        mUserAdvantageAdapter = new UserAdvantageAdapter(mContext);
        rlvUserDetailAdvantage.setAdapter(mUserAdvantageAdapter);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(mContext);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlvUserDetailAdvantage.setLayoutManager(layoutManager1);

        /**
         * 他的技能
         */
        mUserSkillAdapter = new UserSkillAdapter(mContext);


        rlvUserDetailSkill.setAdapter(mUserSkillAdapter);
        rlvUserDetailSkill.setLayoutManager(new LinearLayoutManager(mContext));

        /**
         * 线下服务价格
         */
        mUserOffLinePriceAdapter = new UserOffLinePriceAdapter(mContext);
        rlvUserDetailOffLinePrice.setAdapter(mUserOffLinePriceAdapter);
        rlvUserDetailOffLinePrice.setLayoutManager(new LinearLayoutManager(mContext));

        getUserHomePageInfo();

        //刷新用户信息
        RxBus.getInstance().toObservable(Integer.class)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        if (integer == Constants.REFRESH_USER_DETAILS_INFO) {
                            getUserHomePageInfo();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //获取用户主页信息
    private void getUserHomePageInfo() {
        BaseOkHttpClient.newBuilder()
                .addParam("userId", "0".equals(mEnterType) ? MyApplication.mPreferenceProvider.getUId() : mUserId)
                .addParam("longitude", MyApplication.mPreferenceProvider.getLongitude())
//                .addParam("longitude", "113.64964385")
//                .addParam("latitude", "34.75661006")
                .addParam("latitude", MyApplication.mPreferenceProvider.getLatitude())
                //.addParam("type", 1)
                .url(NetUrlUtils.GET_USER_HOME_PAGE_INFO)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                mUserInfoBean = JSONUtils.jsonString2Bean(json, UserDetailsInfoBean.class);
                if (mUserInfoBean == null) return;
                ImageUtils.getPic(Constants.IMAGE_BASE_URL + mUserInfoBean.getUserEntity().getAvatar(), ivUserHead, mContext, R.mipmap.icon_default_avatar);
                tvUserName.setText(mUserInfoBean.getUserEntity().getNickname());
                centerTitle.setText(mUserInfoBean.getUserEntity().getNickname());
                tvUserAddress.setText(mUserInfoBean.getUserEntity().getCitizenNo());
                tvUserKmForMe.setText("距离您" + mUserInfoBean.getDistance() + "km");
                tvUserConstellation.setText(mUserInfoBean.getUserEntity().getMessageConstellation());
                tvUserHeight.setText(mUserInfoBean.getUserEntity().getMessageHight() + "cm");
                tvUserWeight.setText(mUserInfoBean.getUserEntity().getMessageWeight() + "kg");
                tvAdvantageTitle.setText(mUserInfoBean.getUserEntity().getAdvantageTitle());
                String sex = mUserInfoBean.getUserEntity().getSex();
                tvUserAge.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable("1".equals(sex) ? R.mipmap.ic_women_logo : R.mipmap.ic_man_logo), null, null, null);
                tvUserAge.setText(mUserInfoBean.getUserEntity().getAge());
                tvAdvantageDes.setText(mUserInfoBean.getUserEntity().getAdvantageMessage());
                //动态
                mUserDynamicAdapter.refreshList(mUserInfoBean.getDynamic());
                //优势标签
                mUserAdvantageAdapter.refreshList(mUserInfoBean.getAdvantageLabelEnties());
                //技能
                mUserSkillAdapter.refreshList(mUserInfoBean.getSkills());
                //服务价格
                mUserOffLinePriceAdapter.refreshList(mUserInfoBean.getServeType());
                //设置标题

                formatWorkTime(mUserInfoBean);

            }

            @Override
            public void onError(int code, String msg) {
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });


    }

    //格式化时间
    private void formatWorkTime(UserDetailsInfoBean mUserInfoBean) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date workStartDate = simpleDateFormat.parse(mUserInfoBean.getUserEntity().getJobStartTimer());
            Date workEndDate = simpleDateFormat.parse(mUserInfoBean.getUserEntity().getJobEndTime());
            Date saturdayStartDate = simpleDateFormat.parse(mUserInfoBean.getUserEntity().getSixStartTime());
            Date saturdayEndDate = simpleDateFormat.parse(mUserInfoBean.getUserEntity().getSixSendTime());
            Date sundayStartDate = simpleDateFormat.parse(mUserInfoBean.getUserEntity().getSevenStartTime());
            Date sundayEndDate = simpleDateFormat.parse(mUserInfoBean.getUserEntity().getSevenEndTime());

            mWorkStartTime = DateUtils.HourOf(workStartDate) + ":" + DateUtils.MinuteOf(workStartDate);
            mWorkEndTime = DateUtils.HourOf(workEndDate) + ":" + DateUtils.MinuteOf(workEndDate);
            mSaturdayStartTime = DateUtils.HourOf(saturdayStartDate) + ":" + DateUtils.MinuteOf(saturdayStartDate);
            mSaturdayEndTime = DateUtils.HourOf(saturdayEndDate) + ":" + DateUtils.MinuteOf(saturdayEndDate);
            mSundayStartTime = DateUtils.HourOf(sundayStartDate) + ":" + DateUtils.MinuteOf(sundayStartDate);
            mSundayEndTime = DateUtils.HourOf(sundayEndDate) + ":" + DateUtils.MinuteOf(sundayEndDate);

            tvUserWorkDay.setText("工作日：" + mWorkStartTime + "-" + mWorkEndTime);
            tvUserWeekDay.setText("周六/周日：" + mSundayStartTime + "-" + mSundayEndTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void refreshUI() {
        llytChooseUser.setVisibility(isMine ? View.GONE : View.VISIBLE);
        viewHolderLine.setVisibility(isMine ? View.GONE : View.VISIBLE);
        tvUserKmForMe.setVisibility(isMine ? View.GONE : View.VISIBLE);
        ivUserUpdateAdvantage.setVisibility(isMine ? View.VISIBLE : View.GONE);
        ivUserUpdateSkill.setVisibility(isMine ? View.VISIBLE : View.GONE);
        ivUserUpdateWorkTime.setVisibility(isMine ? View.VISIBLE : View.GONE);
        ivUserUpdatePrice.setVisibility(isMine ? View.VISIBLE : View.GONE);
        tvDynamic.setText(isMine ? "我的动态" : "她的动态");
        tvAdvantage.setText(isMine ? "我的优势" : "她的优势");
        tvSkill.setText(isMine ? "我的技能" : "她的技能");

        //设置右边消息logo
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_message_white);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        rightTitle.setCompoundDrawables(null, null, isMine ? null : drawable, null);
    }


    @OnClick({R.id.rl_back, R.id.right_title, R.id.llyt_user_dynamic, R.id.iv_user_update_advantage, R.id.iv_user_update_skill, R.id.iv_user_update_work_time, R.id.iv_user_update_price, R.id.btn_user_detail_choose_he})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.right_title://聊天
                break;
            case R.id.llyt_user_dynamic://动态
                Intent intent = new Intent(UserDetailsActivity.this, UserDynamicActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_ENTER_TYPE, mEnterType);
                startActivity(intent);
                break;
            case R.id.iv_user_update_advantage://编辑优势
                if (mUserInfoBean == null) return;
                Intent intent1 = new Intent(UserDetailsActivity.this, UpdateAdvantageActivity.class);
                intent1.putExtra("title", mUserInfoBean.getUserEntity().getAdvantageTitle());
                intent1.putExtra("content", mUserInfoBean.getUserEntity().getAdvantageMessage());
                startActivity(intent1);
                break;
            case R.id.iv_user_update_skill://编辑技能
                Intent intent2 = new Intent(UserDetailsActivity.this, UpdateSkillActivity.class);
                startActivity(intent2);
                break;
            case R.id.iv_user_update_work_time://编辑工作时间
                if (mUserInfoBean == null) return;
                Intent intent3 = new Intent(UserDetailsActivity.this, UpdateInvitedTimeActivity.class);
                intent3.putExtra("saturday_start_time", mSaturdayStartTime);
                intent3.putExtra("saturday_end_time", mSaturdayEndTime);
                intent3.putExtra("sunday_start_time", mSundayStartTime);
                intent3.putExtra("sunday_end_time", mSundayEndTime);
                intent3.putExtra("work_start_time", mWorkStartTime);
                intent3.putExtra("work_end_time", mWorkEndTime);
                startActivity(intent3);
                break;
            case R.id.iv_user_update_price://编辑服务价格
                if (mUserInfoBean == null) return;
                Intent intent4 = new Intent(UserDetailsActivity.this, UpdateServicePriceActivity.class);
                intent4.putExtra("info_bean", mUserInfoBean);
                startActivity(intent4);
                break;
            case R.id.btn_user_detail_choose_he://选TA
                break;
        }
    }

}
