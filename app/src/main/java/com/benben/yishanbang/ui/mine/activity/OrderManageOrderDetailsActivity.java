package com.benben.yishanbang.ui.mine.activity;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.DateUtils;
import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.commoncore.widget.CircleImageView;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.mine.adapter.OrderDetailsGoodsListAdapter;
import com.benben.yishanbang.ui.mine.bean.TeaOrderDetailsBottomBean;
import com.benben.yishanbang.ui.mine.bean.TeaOrderDetailsTopBean;

import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: 订单管理-奶茶订单-订单详情
 */
public class OrderManageOrderDetailsActivity extends BaseActivity {


    private static final String TAG = "OrderManageOrderDetails";
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.civ_avatar)
    CircleImageView civAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_consumption_count)
    TextView tvConsumptionCount;
    @BindView(R.id.tv_visit_count)
    TextView tvVisitCount;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;
    @BindView(R.id.tv_count_down)
    TextView tvCountDown;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    private int mAppriseValue = 0;
    private OrderDetailsGoodsListAdapter mAdapter;
    private CountDownTimer mCountDownTimer;
    private long mDownTime;
    //用户id
    private String mUserId;
    //订单id
    private String mOrderId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_manage_order_details;
    }

    @Override
    protected void initData() {
        centerTitle.setText("订单详情");
        mUserId = getIntent().getStringExtra(Constants.EXTRA_KEY_USER_ID);
        mOrderId = getIntent().getStringExtra(Constants.EXTRA_KEY_ORDER_ID);
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new OrderDetailsGoodsListAdapter(mContext);
        rlvList.setAdapter(mAdapter);
        tvCountDown.setVisibility(View.GONE);
        getTopDataList();
        getBottomDataList();
    }

    //头部信息
    private void getTopDataList() {
        BaseOkHttpClient.newBuilder()
                .addParam("userId", mUserId)
                .addParam("shopId", MyApplication.mPreferenceProvider.getShopId())
                .url(NetUrlUtils.SHOP_CENTER_TEA_ORDER_DETAILS_TOP)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                TeaOrderDetailsTopBean detailsTopBean = JSONUtils.jsonString2Bean(json, TeaOrderDetailsTopBean.class);
                if (detailsTopBean != null && detailsTopBean.getUserEntity() != null) {
                    ImageUtils.getPic(Constants.IMAGE_BASE_URL + detailsTopBean.getUserEntity().getAvatar(), civAvatar, mContext, R.mipmap.icon_default_avatar);
                    tvName.setText(detailsTopBean.getUserEntity().getNickname());
                    tvMobile.setText("(" + detailsTopBean.getUserEntity().getMobile() + ")");
                    tvConsumptionCount.setText("消费次数：" + (StringUtils.isEmpty(detailsTopBean.getPayCount()) ? "0" : detailsTopBean.getPayCount()));
                    tvVisitCount.setText("来访次数：" + (StringUtils.isEmpty(detailsTopBean.getVisitCount()) ? "0" : detailsTopBean.getVisitCount()));
                }

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

    //底部信息
    private void getBottomDataList() {

        BaseOkHttpClient.newBuilder()
                .addParam("id", mOrderId)
                .addParam("shopId", MyApplication.mPreferenceProvider.getShopId())
                .url(NetUrlUtils.SHOP_CENTER_TEA_ORDER_DETAILS_BOTTOM)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                TeaOrderDetailsBottomBean orderDetailsBottomBean = JSONUtils.jsonString2Bean(json, TeaOrderDetailsBottomBean.class);
                if (orderDetailsBottomBean != null) {
                    //倒计时
                    countDownTime(orderDetailsBottomBean.getMilkTeaOrderVo().getOrderTime());
                    tvOrderNum.setText("订单编号：" + orderDetailsBottomBean.getMilkTeaOrderVo().getOrderNo());
                    tvDate.setText(orderDetailsBottomBean.getMilkTeaOrderVo().getOrderTime());
                    tvTotalPrice.setText("合计:" + orderDetailsBottomBean.getMilkTeaOrderVo().getGoodsMoney());
                    mAdapter.refreshList(orderDetailsBottomBean.getMilkTeaOrderVo().getMilkTeaOrderGoodsVos());
                }
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

    private void countDownTime(String orderTime) {
        Date orderDate = DateUtils.stringToDate(orderTime, "yyyy-MM-dd HH:mm:ss");
        Date currentDate = DateUtils.longToDate(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
        long min = DateUtils.minutesBetween(orderDate, currentDate);

        if (min >= 40) {
            tvCountDown.setText("已完成");
        } else {
            mDownTime = (40 - min) * 60;
            mCountDownTimer = new CountDownTimer(mDownTime * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    mDownTime--;
                    long min = (long) mDownTime / 60;
                    long second = mDownTime - min * 60;
                    tvCountDown.setText("倒计时：" + (min < 10 ? "0" + min : min) + ":" + (second < 10 ? "0" + second : second));
                }

                @Override
                public void onFinish() {
                    tvCountDown.setText("已完成");
                }
            }.start();
        }

    }


    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        onBackPressed();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

}
