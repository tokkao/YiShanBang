package com.benben.yishanbang.ui.tea.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.RxBus;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.bean.PayBean;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.pop.EvaluateStarPopuWindow;
import com.benben.yishanbang.pop.PayPopupWindow;
import com.benben.yishanbang.ui.mine.adapter.MilkTeaOrderDetailsAdapter;
import com.benben.yishanbang.ui.mine.adapter.MilkTeaOrderDetailsGoodsListAdapter;
import com.benben.yishanbang.ui.mine.bean.MilkTeaDetailsBean;
import com.benben.yishanbang.ui.mine.bean.TeaOrderDetailsBottomBean;
import com.benben.yishanbang.ui.tea.adapter.ConfirmOrderAdapter;
import com.benben.yishanbang.utils.PayListenerUtils;
import com.benben.yishanbang.utils.PayResultListener;
import com.benben.yishanbang.utils.PayUtils;
import com.google.gson.Gson;
import com.kongzue.dialog.v3.MessageDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.benben.yishanbang.MyApplication.mPreferenceProvider;

/**
 * Created by Administrator on 2019/8/12 0012
 * Describe:订单详情页面
 */
public class OrderDetailsActivity extends BaseActivity {

    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.rlyt_cancel_order)
    RelativeLayout rlytCancelOrder;
    @BindView(R.id.rlyt_take_food_code)
    RelativeLayout rlytTakeFoodCode;
    @BindView(R.id.tv_order_details_name)
    TextView tvOrderDetailsName;
    @BindView(R.id.tv_order_details_address)
    TextView tvOrderDetailsAddress;
    @BindView(R.id.rlv_order_details)
    RecyclerView rlvOrderDetails;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.tv_left_option)
    TextView tvLeftOption;
    @BindView(R.id.tv_right_option)
    TextView tvRightOption;
    @BindView(R.id.llyt_options)
    LinearLayout llytOptions;
    private ConfirmOrderAdapter mConfirmOrderAdapter;//订单适配器

    private String mShopid, mOrderId;

    private MilkTeaOrderDetailsAdapter mMilkTeaOrderDetailsAdapter;

    List<MilkTeaDetailsBean.MilkTeaOrderVoBean.MilkTeaOrderGoodsVosBean> mMilkTeaOrderGoodsVosBeans = new ArrayList<>();

    String mFoodCode, mCodeImage;
    private String mGoodsIds;
    //1从订单列表进入 0从奶茶店铺下单成功进入
    private String mEnterType;
    private String mOrderStatus;
    private MilkTeaOrderDetailsGoodsListAdapter mGoodsListAdapter;
    private PayPopupWindow mPayPopupWindow;
    private double mTotalPrice;
    //支付工具类
    private PayUtils mPayUtils;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initData() {

        //设置标题
        centerTitle.setText("订单详情");
        mPayUtils = new PayUtils(mContext);
        Intent intent = getIntent();
        mOrderId = intent.getStringExtra("id");//订单id
        mShopid = intent.getStringExtra("mShopId");//店铺id
        mEnterType = intent.getStringExtra(Constants.EXTRA_KEY_ENTER_TYPE);

        rlvOrderDetails.setLayoutManager(new LinearLayoutManager(mContext));

        if ("0".equals(mEnterType)) {
            mMilkTeaOrderDetailsAdapter = new MilkTeaOrderDetailsAdapter(mContext);
            rlvOrderDetails.setAdapter(mMilkTeaOrderDetailsAdapter);
            getPayOrderMsg();
        } else {
            mGoodsListAdapter = new MilkTeaOrderDetailsGoodsListAdapter(mContext);
            rlvOrderDetails.setAdapter(mGoodsListAdapter);
            getOrderDetails();
        }
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

        PayListenerUtils.getInstance(mContext).addListener(new PayResultListener() {
            @Override
            public void onPaySuccess() {
                if ("0".equals(mEnterType)) {
                    getPayOrderMsg();
                } else {
                    getOrderDetails();
                }
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

    //支付宝支付
    private void aliPay() {
        BaseOkHttpClient.newBuilder()
                .addParam("orderId", mOrderId)//
                .addParam("orderMoney", StringUtils.isEmpty(String.valueOf(mTotalPrice)) ? "0" : String.valueOf(mTotalPrice))//
                .addParam("orderName", "naicha")//body 随便写
                .addParam("body", "test")//body 随便写
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
    private void wxPay() {
        BaseOkHttpClient.newBuilder()
                .addParam("orderId", mOrderId)//订单id
                .addParam("orderMoney", StringUtils.isEmpty(String.valueOf(mTotalPrice)) ? "0" : String.valueOf(mTotalPrice))//订单价格
                .addParam("body", "naicha")//body体内容随便传
                .url(NetUrlUtils.MILK_TEA_WECHAT_PAY)
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


    //获取订单信息
    private void getOrderDetails() {

        BaseOkHttpClient.newBuilder()
                .addParam("id", mOrderId)//订单Id
                .addParam("shopId", mShopid)//店铺id
                .url(NetUrlUtils.SHOP_CENTER_TEA_ORDER_DETAILS_BOTTOM)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                TeaOrderDetailsBottomBean orderDetailsBottomBean = JSONUtils.jsonString2Bean(json, TeaOrderDetailsBottomBean.class);
                if (orderDetailsBottomBean == null) return;
                //倒计时
//                    countDownTime(orderDetailsBottomBean.getMilkTeaOrderVo().getOrderTime());

                tvOrderDetailsAddress.setText(orderDetailsBottomBean.getShopNameAndAddress().getShopAddress());
                tvOrderDetailsName.setText(orderDetailsBottomBean.getShopNameAndAddress().getShopName());
                //0全部；1未支付3待取餐；4待评价；5已完成（已评价）；8已取消
                mOrderStatus = orderDetailsBottomBean.getMilkTeaOrderVo().getStatus();
                switch (mOrderStatus) {
                    case "1":
                        tvLeftOption.setText("取消订单");
                        tvRightOption.setText("去支付");
                        tvTips.setText("请尽快完成支付");
                        break;
                    case "3":
                        tvLeftOption.setText("取消订单");
                        tvRightOption.setText("取餐码");
                        tvTips.setText("请在40分钟内完成取餐");
                        break;
                    case "4":
                        tvLeftOption.setText("再来一单");
                        tvRightOption.setText("去评价");
                        tvOrderStatus.setText("已完成");
                        tvTips.setText("");
                        break;
                    case "5":
                        llytOptions.setVisibility(View.INVISIBLE);
                        tvOrderStatus.setText("已完成");
                        tvTips.setText("");
                        break;
                    case "8":
                        llytOptions.setVisibility(View.INVISIBLE);
                        tvOrderStatus.setText("已完成");
                        tvTips.setText("");
                        break;
                }
                List<TeaOrderDetailsBottomBean.MilkTeaOrderVoBean.MilkTeaOrderGoodsVosBean> milkTeaOrderGoodsVos = orderDetailsBottomBean.getMilkTeaOrderVo().getMilkTeaOrderGoodsVos();
                mGoodsListAdapter.refreshList(milkTeaOrderGoodsVos);
                mTotalPrice = 0;
                for (int i = 0; i < milkTeaOrderGoodsVos.size(); i++) {
                    int goodsCount = milkTeaOrderGoodsVos.get(i).getGoodsCount();
                    double goodsPrice = milkTeaOrderGoodsVos.get(i).getPrice();
                    mTotalPrice += (goodsCount * goodsPrice);
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

    //检查是否有抽奖
    private void checkLotteryExist() {
        BaseOkHttpClient.newBuilder()
                .addParam("goodsIds", mGoodsIds)//商品Id
                .addParam("shopId", mShopid)//店铺id
                .url(NetUrlUtils.QUERY_ACTIVATY_IS_EXIST)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                showLotteryDialog();
            }

            @Override
            public void onError(int code, String msg) {
                toast(msg);
                showLotteryDialog();
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });
    }

    //抽奖资格弹窗
    private void showLotteryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = View.inflate(mContext, R.layout.dialog_lottery_is_exist, null);
        AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnLottery = view.findViewById(R.id.btn_confirm);
        //抽奖确认
        btnLottery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    @OnClick({R.id.rl_back, R.id.rlyt_cancel_order, R.id.rlyt_take_food_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back://返回上一页
                onBackPressed();
                break;
            case R.id.rlyt_cancel_order://取消订单
                if ("0".equals(mEnterType)) {
                    MessageDialog.show((AppCompatActivity) mContext, "提示", "是否确认取消订单？", "确定", "取消")
                            .setOnOkButtonClickListener((baseDialog, v) -> {
                                cancelOrder();
                                return false;
                            });

                } else {
                    //0全部；1未支付3待取餐；4待评价；5已完成（已评价）；8已取消
                    switch (mOrderStatus) {
                        case "1":
                        case "3"://取消订单
                            MessageDialog.show((AppCompatActivity) mContext, "提示", "是否确认取消订单？", "确定", "取消")
                                    .setOnOkButtonClickListener((baseDialog, v) -> {
                                        cancelOrder();
                                        return false;
                                    });
                            break;
                        case "4"://再来一单
                            startActivity(new Intent(mContext, ChooseGoodsActivity.class).putExtra("shopid", mShopid));
                            finish();
                            break;
                    }
                }

                break;
            case R.id.rlyt_take_food_code://取餐码
                if ("0".equals(mEnterType)) {
                    Intent intent = new Intent(OrderDetailsActivity.this, TakeFoodCodeActivity.class);
                    intent.putExtra("mFoodCode", mFoodCode);
                    intent.putExtra("mCodeImage", mCodeImage);
                    startActivity(intent);
                } else {
                    switch (mOrderStatus) {
                        // 1未支付3待取餐；4待评价
                        case "1": //去支付
                            mPayPopupWindow.setTypePrice("", mTotalPrice + "");
                            mPayPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                            break;
                        case "3"://取餐码
                            Intent intent = new Intent(mContext, TakeFoodCodeActivity.class);
                            intent.putExtra("mFoodCode", mFoodCode);
                            intent.putExtra("mCodeImage", mCodeImage);
                            startActivity(intent);
                            break;
                        case "4"://去评价
                            EvaluateStarPopuWindow mEvaluateStarPopuWindow = new EvaluateStarPopuWindow(mContext, mOrderId);
                            mEvaluateStarPopuWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                            break;
                    }
                }

                break;
        }
    }

    //刷新选择商品页面数据
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if ("0".equals(mEnterType)) {
            RxBus.getInstance().post(Constants.REFRESH_TEA_SHOP_GOODS_INFO);
        }

    }

    //获取订单已支付数据
    private void getPayOrderMsg() {

        BaseOkHttpClient.newBuilder()
                .addParam("id", mOrderId)//订单Id
                .addParam("shopId", mShopid)//店铺id
                .addParam("userId", mPreferenceProvider.getUId())//用户id
                .url(NetUrlUtils.PAYMENT_GOOD_ORDER)
                .get()
                .build().enqueue((Activity) mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                MilkTeaDetailsBean mMilkTeaDetailsBean = JSONUtils.jsonString2Bean(json, MilkTeaDetailsBean.class);
                //milkTeaDetailsBean.getShopNameAndAddress().getShopAddress();
                tvOrderDetailsName.setText(mMilkTeaDetailsBean.getShopNameAndAddress().getShopName());
                tvOrderDetailsAddress.setText(mMilkTeaDetailsBean.getShopNameAndAddress().getShopAddress());

                mFoodCode = mMilkTeaDetailsBean.getMilkTeaOrderVo().getFoodcode();
                mCodeImage = mMilkTeaDetailsBean.getMilkTeaOrderVo().getCodeImage();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mMilkTeaDetailsBean.getMilkTeaOrderVo().getMilkTeaOrderGoodsVos().size(); i++) {
                    //拼接商品id
                    sb.append(i == (mMilkTeaDetailsBean.getMilkTeaOrderVo().getMilkTeaOrderGoodsVos().size() - 1) ? mMilkTeaDetailsBean.getMilkTeaOrderVo().getMilkTeaOrderGoodsVos().get(i).getId() : mMilkTeaDetailsBean.getMilkTeaOrderVo().getMilkTeaOrderGoodsVos().get(i).getId() + ",");

                    MilkTeaDetailsBean.MilkTeaOrderVoBean.MilkTeaOrderGoodsVosBean mMilkTeaOrderGoodsVosBean =
                            new MilkTeaDetailsBean().new MilkTeaOrderVoBean().new MilkTeaOrderGoodsVosBean();

                    mMilkTeaOrderGoodsVosBean.setDelPrice(mMilkTeaDetailsBean.getMilkTeaOrderVo()
                            .getMilkTeaOrderGoodsVos().get(i).getDelPrice());

                    mMilkTeaOrderGoodsVosBean.setGoodsCount(mMilkTeaDetailsBean.getMilkTeaOrderVo()
                            .getMilkTeaOrderGoodsVos().get(i).getGoodsCount());

                    mMilkTeaOrderGoodsVosBean.setGoodsName(mMilkTeaDetailsBean.getMilkTeaOrderVo()
                            .getMilkTeaOrderGoodsVos().get(i).getGoodsName());

                    mMilkTeaOrderGoodsVosBean.setImgUrl(mMilkTeaDetailsBean.getMilkTeaOrderVo()
                            .getMilkTeaOrderGoodsVos().get(i).getImgUrl());

                    mMilkTeaOrderGoodsVosBean.setPrice(mMilkTeaDetailsBean.getMilkTeaOrderVo()
                            .getMilkTeaOrderGoodsVos().get(i).getPrice());

                    mMilkTeaOrderGoodsVosBean.setSpec(mMilkTeaDetailsBean.getMilkTeaOrderVo()
                            .getMilkTeaOrderGoodsVos().get(i).getSpec());

                    mMilkTeaOrderGoodsVosBean.setSugar(mMilkTeaDetailsBean.getMilkTeaOrderVo()
                            .getMilkTeaOrderGoodsVos().get(i).getSugar());

                    mMilkTeaOrderGoodsVosBean.setTemp(mMilkTeaDetailsBean.getMilkTeaOrderVo()
                            .getMilkTeaOrderGoodsVos().get(i).getTemp());

                    mMilkTeaOrderGoodsVosBeans.add(mMilkTeaOrderGoodsVosBean);
                }

                mGoodsIds = sb.toString();

                //支付成功有抽奖弹窗
                if ("0".equals(mEnterType)) {
                    checkLotteryExist();
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

    //取消订单
    private void cancelOrder() {

        BaseOkHttpClient.newBuilder()
                .addParam("id", mOrderId)//订单Id
                .url(NetUrlUtils.CANCLE_GOODS_ORDER)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtils.show(mContext, "订单取消成功");
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
}
