package com.benben.yishanbang.ui.service.activity;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.bean.PayBean;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.pop.PayPopupWindow;
import com.benben.yishanbang.ui.service.adapter.RechargeListAdapter;
import com.benben.yishanbang.ui.service.bean.RechargeListBean;
import com.benben.yishanbang.utils.PayListenerUtils;
import com.benben.yishanbang.utils.PayResultListener;
import com.benben.yishanbang.utils.PayUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class RechargeIMActivity extends BaseActivity {
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.btn_pay)
    Button btnPay;
    private RechargeListAdapter mAdapter;
    private PayPopupWindow mPayPopupWindow;
    private String mPrice = "1";
    private PayUtils mPayUtils;
    //是否是新用户
    private boolean isNew = true;
    private int mGiftId = 1;
    //对方的极光账号id
    private String mTargetId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_im;
    }

    @Override
    protected void initData() {
        centerTitle.setText("聊天充值");
        mTargetId = getIntent().getStringExtra("targetId");
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new RechargeListAdapter(mContext);
        rlvList.setAdapter(mAdapter);
        buildData();

        mPrice = isNew ? "1" : "5";
        mPayUtils = new PayUtils(mContext);
        mPayPopupWindow = new PayPopupWindow(mContext, new PayPopupWindow.OnPayCallback() {
            @Override
            public void alipay() {
                IMAlipay();
            }

            @Override
            public void wxpay() {
                IMWxPay();
            }
        });


        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<RechargeListBean>() {
            @Override
            public void onItemClick(View view, int position, RechargeListBean model) {
                mGiftId = position+1;
                switch (position){
                    case 0:
                        mPrice = "1";
                        break;
                    case 1:
                        mPrice = "5";
                        break;
                    case 2:
                        mPrice = "20";
                        break;
                    case 3:
                        mPrice = "50";
                        break;
                    case 4:
                        mPrice = "520";
                        break;
                }

            }

            @Override
            public void onItemLongClick(View view, int position, RechargeListBean model) {

            }
        });


        PayListenerUtils.getInstance(mContext).addListener(new PayResultListener() {
            @Override
            public void onPaySuccess() {
                toast("支付成功");
            }

            @Override
            public void onPayError() {

            }

            @Override
            public void onPayCancel() {

            }
        });
    }
    //创建数据
    private void buildData() {
        ArrayList<RechargeListBean> list = new ArrayList<>();
        int totalCount = isNew ? 5 : 4;
        if (isNew) {
            RechargeListBean rechargeListBean0 = new RechargeListBean();
            rechargeListBean0.setSelected(isNew);
            rechargeListBean0.setId(0);
            RechargeListBean rechargeListBean1 = new RechargeListBean();
            rechargeListBean1.setSelected(false);
            rechargeListBean1.setId(1);
            RechargeListBean rechargeListBean2 = new RechargeListBean();
            rechargeListBean2.setSelected(false);
            rechargeListBean2.setId(2);
            RechargeListBean rechargeListBean3 = new RechargeListBean();
            rechargeListBean3.setSelected(false);
            rechargeListBean3.setId(3);
            RechargeListBean rechargeListBean4 = new RechargeListBean();
            rechargeListBean4.setSelected(false);
            rechargeListBean4.setId(4);
            list.add(rechargeListBean0);
            list.add(rechargeListBean1);
            list.add(rechargeListBean2);
            list.add(rechargeListBean3);
            list.add(rechargeListBean4);
        } else {
            RechargeListBean rechargeListBean1 = new RechargeListBean();
            rechargeListBean1.setSelected(true);
            rechargeListBean1.setId(1);
            RechargeListBean rechargeListBean2 = new RechargeListBean();
            rechargeListBean2.setSelected(false);
            rechargeListBean2.setId(2);
            RechargeListBean rechargeListBean3 = new RechargeListBean();
            rechargeListBean3.setSelected(false);
            rechargeListBean3.setId(3);
            RechargeListBean rechargeListBean4 = new RechargeListBean();
            rechargeListBean4.setSelected(false);
            rechargeListBean4.setId(4);
            list.add(rechargeListBean1);
            list.add(rechargeListBean2);
            list.add(rechargeListBean3);
            list.add(rechargeListBean4);
        }

        mAdapter.refreshList(list);
    }
    //支付宝支付
    private void IMAlipay() {
        BaseOkHttpClient.newBuilder()
               // .addParam("orderId", mIMOrderId)//订单id
               // .addParam("orderMoney", StringUtils.isEmpty(mIMOrdeMoney) ? "0" : mIMOrdeMoney)//
                .addParam("orderName", "im")
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


    //微信支付
    private void IMWxPay() {
        BaseOkHttpClient.newBuilder()
               // .addParam("orderId", mIMOrderId)//订单id
               // .addParam("orderMoney", StringUtils.isEmpty(mIMOrdeMoney) ? "0" : mIMOrdeMoney)//
                .addParam("body", "im")//body 随便写
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
    //购买礼物
    private void buyGift() {
        BaseOkHttpClient.newBuilder()
                .addParam("mobile", MyApplication.mPreferenceProvider.getIMUserName())//极光账号
                .addParam("mobileTwo",mTargetId )//目标极光账号
                .addParam("id", mGiftId)//礼物id
                .addParam("moeny", mPrice)//金额
                .addParam("password", "")//支付密码
                .url(NetUrlUtils.RECHARGE_GIFT)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                
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


    @OnClick({R.id.rl_back, R.id.btn_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.btn_pay:
                buyGift();
                //mPayPopupWindow.setTitle("聊天充值");
                //mPayPopupWindow.setTypePrice("", mPrice);
                //mPayPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
        }
    }
}
