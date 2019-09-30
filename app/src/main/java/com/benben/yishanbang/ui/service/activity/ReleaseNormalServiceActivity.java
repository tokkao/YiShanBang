package com.benben.yishanbang.ui.service.activity;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.benben.commoncore.utils.InputCheckUtil;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.bean.PayBean;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.pop.PayPopupWindow;
import com.benben.yishanbang.pop.SelectAppointmentTimePopupWindow;
import com.benben.yishanbang.pop.SelectConditionPopupWindow;
import com.benben.yishanbang.ui.service.bean.IntentionMoneyListBean;
import com.benben.yishanbang.ui.service.bean.ReleaseServiceOrderBean;
import com.benben.yishanbang.utils.PayListenerUtils;
import com.benben.yishanbang.utils.PayResultListener;
import com.benben.yishanbang.utils.PayUtils;
import com.benben.yishanbang.utils.ShowListUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Author:wanghk
 * Time:2019/5/13 0013 16:50
 * <p>
 * 发布普通服务页面
 */
public class ReleaseNormalServiceActivity extends BaseActivity {

    @BindView(R.id.iv_top_bg)
    ImageView ivTopBg;
    @BindView(R.id.tv_service_type)
    TextView tvServiceType;
    @BindView(R.id.iv_select_service_type)
    ImageView ivSelectServiceType;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.edt_remark)
    EditText edtRemark;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_require)
    TextView tvRequire;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.rlyt_release)
    RelativeLayout rlytRelease;

    private PayPopupWindow mPayPopupWindow;
    //选择预约时间的弹窗
    private SelectAppointmentTimePopupWindow mSelectTimePopupWindow;
    //条件选择的弹窗
    private SelectConditionPopupWindow mSelectConditionPopupWindow;
    // 0 吃美食 1 喝一杯 2看电影 3k歌
    private int mEnterType;
    //服务类型数据集合
    private List<String> mTypeList = new ArrayList<>();
    //
    private List<String> mPriceList = new ArrayList<>();
    //服务类型id
    private String mTypeId;
    //价格
    private String mPrice;
    //性别
    private String mSex;
    //人数
    private int mCount;
    //是否匿名
    private boolean isAnonymous;
    //体重
    private String mWeight;
    //身高
    private String mHeight;
    //支付工具类
    private PayUtils mPayUtils;
    //订单编号
    private String mOrderNum;
    //开始时间
    private String mBeginTime;
    //持续时间
    private String mDurationTime;
    //预约地址
    private String mAddress;
    //类型
    private String mTypeName;
    //服务id
    private String mServiceId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release_normal_service;
    }

    @Override
    protected void initData() {
        mEnterType = getIntent().getIntExtra(Constants.EXTRA_KEY_ENTER_TYPE, 0);
        mTypeId = getIntent().getStringExtra("type_id");
        mTypeName = getIntent().getStringExtra("type_name");
        switch (mEnterType) {
            case 0:
                tvServiceType.setText("吃美食");
                break;
            case 1:
                tvServiceType.setText("喝一杯");
                break;
            case 2:
                tvServiceType.setText("看电影");
                break;
            case 3:
                tvServiceType.setText("K歌");
                break;
            case 5:
                tvServiceType.setText(mTypeName);
                break;
        }
        InputCheckUtil.filterEmoji(edtRemark, mContext);
        mPayUtils = new PayUtils(mContext);
        mPayPopupWindow = new PayPopupWindow(mContext, new PayPopupWindow.OnPayCallback() {
            @Override
            public void alipay() {
                aliPay();
            }

            @Override
            public void wxpay() {
                wxPay();
            }
        });

        mSelectTimePopupWindow = new SelectAppointmentTimePopupWindow(mContext, new SelectAppointmentTimePopupWindow.OnTimeCallback() {

            @Override
            public void timeCallBack(String beginTime, String durationTime) {
                mBeginTime = beginTime;
                mDurationTime = durationTime;
                tvTime.setText(beginTime + "," + durationTime + "小时");
            }
        });
        mSelectConditionPopupWindow = new SelectConditionPopupWindow(mContext, new SelectConditionPopupWindow.OnConditionCallback() {

            @Override
            public void onCondition(int sex, int count, boolean isAnonymou, String height, String weight) {
                mCount = count;
                isAnonymous = isAnonymou;
                mHeight = height;
                mWeight = weight;
                mSex = "女";
                if (sex == 0) {
                    mSex = "男";
                    tvRequire.setText(count + "位男生");
                } else if (sex == 1) {
                    mSex = "女";
                    tvRequire.setText(count + "位女生");
                } else {
                    mSex = "不限";
                    tvRequire.setText(count + "个人，性别不限");
                }
                //   String anonymous = isAnonymous ? "匿名" : "不匿名";
//                tvRequire.setText("性别：" + mSex + "\t人数：" + count + "\t" + anonymous + "\t身高：" + height + "\t体重：" + weight);

            }
        });

        PayListenerUtils.getInstance(mContext).addListener(new PayResultListener() {
            @Override
            public void onPaySuccess() {
                Intent intent = new Intent(mContext, TransitionPageActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 0);
                intent.putExtra("cg_id", mTypeId);
                intent.putExtra("service_id", mServiceId);
                intent.putExtra("order_num", mOrderNum);
                startActivity(intent);
            }

            @Override
            public void onPayError() {

            }

            @Override
            public void onPayCancel() {

            }
        });
    }


    //意向金支付宝支付
    private void aliPay() {
        BaseOkHttpClient.newBuilder()
                .addParam("orderId", mOrderNum)//订单id
                .addParam("orderMoney", StringUtils.isEmpty(mPrice) ? "0" : mPrice)//
                .addParam("orderName", "jinengbangfu")
                .addParam("body", "test")
                .url(NetUrlUtils.ALIPAY)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                if (StringUtils.isEmpty(json)) {
                    ToastUtils.show(mContext, "获取订单信息失败，请重试");
                    return;
                }
                mPayUtils.aliPay(json, mContext);
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


    //意向金微信支付
    private void wxPay() {
        BaseOkHttpClient.newBuilder()
                .addParam("orderId", mOrderNum)//订单id
                .addParam("orderMoney", StringUtils.isEmpty(mPrice) ? "0" : mPrice)//
                .addParam("body", "jinengbangfu")//body 随便写
                .url(NetUrlUtils.WX_PAY)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                PayBean payBean = new Gson().fromJson(json, PayBean.class);
                mPayUtils.wxPay(payBean);
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


    //获取价格列表
    private void getPriceList() {

        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.CHOOSE_INTENTION_MONEY)
                .addParam("token", MyApplication.mPreferenceProvider.getToken())
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                List<IntentionMoneyListBean> priceList = JSONUtils.jsonString2Beans(s, IntentionMoneyListBean.class);
                mPriceList.clear();
                for (int i = 0; i < priceList.size(); i++) {
                    mPriceList.add(priceList.get(i).getConfigValue());
                }
                ShowListUtils.show(mContext, "选择价格", mPriceList, new ShowListUtils.OnSelectItem() {
                    @Override
                    public void onCallback(String item, int position) {
                        mPrice = item;
                        tvPrice.setText("每人" + item + "元");
                    }
                });
            }

            @Override
            public void onError(int code, String msg) {
                toast(msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });
    }


    //发布服务
    private void releaseService() {
        if (StringUtils.isEmpty(mTypeId)) {
            toast("请选择服务类型");
            return;
        }
        if (StringUtils.isEmpty(mBeginTime)) {
            toast("请选择预约时间");
            return;
        }
        if (StringUtils.isEmpty(mAddress)) {
            toast("请选择预约地址");
            return;
        }
        if (mCount == 0) {
            toast("请选择要求");
            return;
        }
        if (StringUtils.isEmpty(mPrice)) {
            toast("请选择价格");
            return;
        }

        int sex = 1;
        if ("男".equals(mSex)) {
            sex = 0;
        } else if ("女".equals(mSex)) {
            sex = 1;
        } else {
            sex = 2;
        }


        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.RELEASE_SERVICE)
                .addParam("userId", MyApplication.mPreferenceProvider.getUId())
                .addParam("categoryId", mTypeId)//服务类型
                .addParam("address", tvAddress.getText().toString().trim())//预约地址
                .addParam("mag", edtRemark.getText().toString().trim())//备注
                .addParam("startTime", mBeginTime)//开始时间
                .addParam("money", mPrice)//金额
                //  .addParam("askId", )//要求id
                .addParam("type", 1)//'任务类型'1社交2家政
                .addParam("sex", sex)//性别
                .addParam("number", mCount)//人数
                .addParam("hight", mHeight)//身高
                .addParam("weight", mWeight)//体重
                .addParam("endTime", mDurationTime)//结束时间 格式yyyy-MM-dd HH:mm:ss
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                mServiceId = s;
                createOrder();
            }

            @Override
            public void onError(int code, String msg) {
                toast(msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });
    }

    //生成订单
    private void createOrder() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.PAY_INTENTION_MONEY)
                .addParam("money", mPrice)//意向金金额
                .addParam("type", 1)//订单类型1社交订单2家政订单
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                ReleaseServiceOrderBean releaseServiceOrderBean = JSONUtils.jsonString2Bean(s, ReleaseServiceOrderBean.class);
                mOrderNum = releaseServiceOrderBean.getOrder().getOrderNo();
                mPrice = releaseServiceOrderBean.getPrice();
                mPayPopupWindow.setTitle("支付诚意金");
                mPayPopupWindow.setTypePrice("诚意金", mPrice);
                mPayPopupWindow.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.BOTTOM, 0, 0);
            }

            @Override
            public void onError(int code, String msg) {
                toast(msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });
    }


    @OnClick({R.id.iv_select_service_type, R.id.tv_address, R.id.tv_time, R.id.tv_require, R.id.tv_price, R.id.iv_close, R.id.rlyt_release})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_select_service_type://选择服务类型
                startActivityForResult(new Intent(mContext, MoreServiceActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE,0), 100);
                break;
            case R.id.tv_address://预约地址
                startActivityForResult(new Intent(mContext, ReservationAddressActivity.class), 100);
                break;
            case R.id.tv_time://预约时间
                ArrayList<String> beginTimeList = new ArrayList<>();
                beginTimeList.add("今天\t\t\t\t\t\t\t\t\t\t10时\t\t\t\t\t\t\t\t\t\t10分");
                beginTimeList.add("明天\t\t\t\t\t\t\t\t\t\t11时\t\t\t\t\t\t\t\t\t\t20分");
                beginTimeList.add("后天\t\t\t\t\t\t\t\t\t\t12时\t\t\t\t\t\t\t\t\t\t30分");
                ArrayList<String> durationList = new ArrayList<>();
                durationList.add("3");
                durationList.add("3.5");
                durationList.add("4");
                durationList.add("4.5");
                durationList.add("5");
                mSelectTimePopupWindow.setTimeList(beginTimeList, durationList);
                mSelectTimePopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_require://要求
                mSelectConditionPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_price://价格
                getPriceList();
                break;
            case R.id.iv_close://关闭页面
                onBackPressed();
                break;
            case R.id.rlyt_release://发布服务
                releaseService();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        if (requestCode == 100) {
            if (resultCode == 201) {
                mAddress = data.getStringExtra("address");
                String name = data.getStringExtra("name");
                tvAddress.setText(mAddress);
            } else if (resultCode == 202) {
                mTypeId = data.getStringExtra("type_id");
                mTypeName = data.getStringExtra("type_name");
                tvServiceType.setText(mTypeName);
            }

        }
    }

    @Override
    protected int getStatusBarColor() {
        return R.color.transparent;
    }


}
