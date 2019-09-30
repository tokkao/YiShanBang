package com.benben.yishanbang.ui.service;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.RxBus;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.LazyBaseFragments;
import com.benben.yishanbang.bean.PayBean;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.pop.PayPopupWindow;
import com.benben.yishanbang.ui.home.activity.MessageListActivity;
import com.benben.yishanbang.ui.service.activity.JoinServiceActivity;
import com.benben.yishanbang.ui.service.activity.MoreServiceActivity;
import com.benben.yishanbang.ui.service.activity.ReleaseDomesticServiceActivity;
import com.benben.yishanbang.ui.service.activity.ReleaseNormalServiceActivity;
import com.benben.yishanbang.ui.service.activity.TransitionPageActivity;
import com.benben.yishanbang.ui.service.bean.IMOrderBean;
import com.benben.yishanbang.utils.PayListenerUtils;
import com.benben.yishanbang.utils.PayResultListener;
import com.benben.yishanbang.utils.PayUtils;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/5
 * Time: 9:16
 * 技能帮扶（发布服务选择页）
 */
public class ServiceFragment extends LazyBaseFragments {
    private static final String TAG = "ServiceFragment";
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;

    @BindView(R.id.tv_other)
    TextView tvOther;
    private PayPopupWindow mPayPopupWindow;
    //意向金订单id
    private String mIMOrderId;
    //订单金额
    private String mIMOrdeMoney;
    //支付工具类
    private PayUtils mPayUtils;

    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.frag_service, null);
        return mRootView;
    }

    @Override
    public void initView() {
        centerTitle.setText("技能帮扶");
        rightTitle.setText("加入服务");
        rlBack.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
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

        RxBus.getInstance().toObservable(Integer.class)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        //家政服务发布成功  弹窗
                        if (integer == 3006) {
                            showReleaseSuccessDialog();
                        } else if (integer == 3007) {//新消息提醒
                            showNewMessageDialog();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        PayListenerUtils.getInstance(mContext).addListener(new PayResultListener() {
            @Override
            public void onPaySuccess() {
                ToastUtils.show(mContext, "支付成功");
                startActivity(new Intent(mContext, TransitionPageActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 1));
            }

            @Override
            public void onPayError() {
                ToastUtils.show(mContext, "支付失败");
            }

            @Override
            public void onPayCancel() {
                ToastUtils.show(mContext, "支付取消");
            }
        });
    }


    @OnClick({R.id.right_title, R.id.cv_eat_food, R.id.cv_drink, R.id.cv_look_movie, R.id.cv_k_song, R.id.cv_service_online, R.id.cv_service_home, R.id.civ_more, R.id.iv_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.right_title://加入服务
                startActivity(new Intent(mContext, JoinServiceActivity.class));
                break;
            case R.id.cv_eat_food://吃美食
                startActivity(new Intent(mContext, ReleaseNormalServiceActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 0).putExtra("type_id", "chimeishi"));
                break;
            case R.id.cv_drink://喝一杯
                startActivity(new Intent(mContext, ReleaseNormalServiceActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 1).putExtra("type_id", "heyibei"));
                break;
            case R.id.cv_look_movie://看电影
                startActivity(new Intent(mContext, ReleaseNormalServiceActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 2).putExtra("type_id", "kandianying"));
                break;
            case R.id.cv_k_song://k歌
                startActivity(new Intent(mContext, ReleaseNormalServiceActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 3).putExtra("type_id", "kge"));
                break;
            case R.id.cv_service_online://在线指导-支付意向金
                if ("1".equals(MyApplication.mPreferenceProvider.getIMJurisdiction())) {
                    startActivity(new Intent(mContext, TransitionPageActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 1));
                } else {
                    createIMOrder();
                }

                break;
            case R.id.cv_service_home://家政服务
                startActivity(new Intent(mContext, ReleaseDomesticServiceActivity.class).putExtra("type_id", "jiazhengfuwu"));
                break;
            case R.id.civ_more://更多
                startActivity(new Intent(mContext, MoreServiceActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 1));
                break;
            case R.id.iv_message://会话
                startActivity(new Intent(mContext, MessageListActivity.class));
                break;
        }
    }

    //意向金支付宝支付
    private void IMAlipay() {
        BaseOkHttpClient.newBuilder()
                .addParam("orderId", mIMOrderId)//订单id
                .addParam("orderMoney", StringUtils.isEmpty(mIMOrdeMoney) ? "0" : mIMOrdeMoney)//
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


    //意向金微信支付
    private void IMWxPay() {
        BaseOkHttpClient.newBuilder()
                .addParam("orderId", mIMOrderId)//订单id
                .addParam("orderMoney", StringUtils.isEmpty(mIMOrdeMoney) ? "0" : mIMOrdeMoney)//
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

    //生成在线指导订单
    private void createIMOrder() {
        BaseOkHttpClient.newBuilder()
                .addParam("token", MyApplication.mPreferenceProvider.getToken())
                .url(NetUrlUtils.CREATE_IM_ORDER)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                IMOrderBean mIMOrderBean = JSONUtils.jsonString2Bean(json, IMOrderBean.class);
                mIMOrderId = mIMOrderBean.getId();
                mIMOrdeMoney = mIMOrderBean.getOrderMoney();
                showIntentionMoneyDialog(mIMOrdeMoney);
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


    //新消息弹窗
    private void showNewMessageDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = View.inflate(mContext, R.layout.dialog_new_message, null);
        AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tvPositive = view.findViewById(R.id.tv_positive);
        //马上查看
        tvPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    //家政服务  发布成功弹窗
    private void showReleaseSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = View.inflate(mContext, R.layout.dialog_release_success, null);
        AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tvContent = view.findViewById(R.id.tv_content);
        RelativeLayout rlytConfirm = view.findViewById(R.id.rlyt_confirm);

        rlytConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    //意向金弹窗
    private void showIntentionMoneyDialog(String orderMoney) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = View.inflate(mContext, R.layout.dialog_intention_money, null);
        AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tvMoney = view.findViewById(R.id.tv_money);
        RelativeLayout rlytConfirm = view.findViewById(R.id.rlyt_confirm);


        rlytConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                mPayPopupWindow.setTitle("支付意向金");
                mPayPopupWindow.setTypePrice("", orderMoney);
                mPayPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            }
        });
    }
}
