package com.benben.yishanbang.ui.mine.fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.LazyBaseFragments;
import com.benben.yishanbang.bean.PayBean;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.pop.PayPopupWindow;
import com.benben.yishanbang.ui.mine.adapter.ShoppingOrderListAdapter;
import com.benben.yishanbang.ui.mine.bean.ShoppingOrderListBean;
import com.benben.yishanbang.utils.PayUtils;
import com.google.gson.Gson;
import com.kongzue.dialog.v3.CustomDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

import static com.benben.yishanbang.config.Constants.DATA_KEY;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: 购物订单页面
 */
public class MyShoppingOrderFragment extends LazyBaseFragments {
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private ShoppingOrderListAdapter mAdapter;
    private int mPage = 1;
    private int mPageSize = 15;
    private boolean isRefresh = true;

    private String mType;
    private PayPopupWindow mPayPopupWindow;
    //支付工具类
    private PayUtils mPayUtils;
    private String mOrderId;
    private String mTotalPrice;

    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.frag_shopping_order, null);
        return mRootView;
    }

    @Override
    public void initView() {
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
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRefresh = false;
                mPage++;
                getOrderList();
            }
        });
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new ShoppingOrderListAdapter(mContext);
        mAdapter.setOnItemClickListener(new ShoppingOrderListAdapter.ShoppingOrderCallback() {
            @Override
            public void orderCancelCallback(String orderNo) {
                if (mContext instanceof AppCompatActivity) {
                    CustomDialog.show((AppCompatActivity) mContext, R.layout.dialog_confirm_window, new CustomDialog.OnBindView() {
                        @Override
                        public void onBind(final CustomDialog dialog, View v) {
                            TextView tv_dialog_one_msg = v.findViewById(R.id.tv_dialog_one_msg);
                            tv_dialog_one_msg.setText("确定取消订单吗");
                            TextView tv_right_ok = v.findViewById(R.id.tv_right_ok);
                            TextView tv_left_cancel = v.findViewById(R.id.tv_left_cancel);

                            //确定
                            tv_right_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    orderCancel(orderNo);
                                }
                            });
                            //取消
                            tv_left_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.doDismiss();
                                }
                            });
                        }
                    });
                } else {
                    orderCancel(orderNo);
                }
            }

            @Override
            public void orderRemindCallback(String orderNo) {
                orderRemind(orderNo);
            }

            @Override
            public void orderLookCallback(String orderNo) {
                orderLook(orderNo);
            }

            @Override
            public void orderConfirmCallback(String orderNo) {
                orderConfirm(orderNo);
            }

            @Override
            public void orderDeleteCallback(String orderNo) {
                if (mContext instanceof AppCompatActivity) {
                    CustomDialog.show((AppCompatActivity) mContext, R.layout.dialog_confirm_window, new CustomDialog.OnBindView() {
                        @Override
                        public void onBind(final CustomDialog dialog, View v) {
                            TextView tv_right_ok = v.findViewById(R.id.tv_right_ok);
                            TextView tv_left_cancel = v.findViewById(R.id.tv_left_cancel);

                            //确定
                            tv_right_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    orderDelete(orderNo);
                                }
                            });
                            //取消
                            tv_left_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.doDismiss();
                                }
                            });
                        }
                    });
                } else {
                    orderDelete(orderNo);
                }
            }

            @Override
            public void orderAgainCallback(String orderNo) {
                orderAgain(orderNo);
            }

            @Override
            public void orderPayCallback(String amount, String orderNo) {
                orderPay(amount, orderNo);
            }

            @Override
            public void onItemClick(View view, int position, Object model) {
            }

            @Override
            public void onItemLongClick(View view, int position, Object model) {
            }
        });
        rlvList.setAdapter(mAdapter);
    }

    /**
     * 刷新
     */
    private void refreshData() {
        isRefresh = true;
        mPage = 1;
        getOrderList();
    }

    @Override
    public void initData() {
        mType = getArguments().getString(DATA_KEY);
        getOrderList();
    }

    //获取订单列表
    private void getOrderList() {

        BaseOkHttpClient.newBuilder()
                .addParam("type", mType)
                .url(NetUrlUtils.SHOPPING_ORDER_LIST)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                refreshLayout.finishRefresh(true);
                List<ShoppingOrderListBean> orderListBeans = JSONUtils.jsonString2Beans(s, ShoppingOrderListBean.class);
                if (orderListBeans != null && orderListBeans.size() > 0) {
                    llytNoData.setVisibility(View.GONE);
                    rlvList.setVisibility(View.VISIBLE);
                    mAdapter.refreshList(orderListBeans);
                    refreshLayout.finishLoadMore(true);
                } else {
                    llytNoData.setVisibility(View.VISIBLE);
                    rlvList.setVisibility(View.GONE);
                    refreshLayout.finishLoadMore(false);
                }
                toast(msg);
            }

            @Override
            public void onError(int code, String msg) {
                llytNoData.setVisibility(View.VISIBLE);
                rlvList.setVisibility(View.GONE);
                refreshLayout.finishRefresh(true);
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    private void orderCancel(String orderNo) {
        BaseOkHttpClient.newBuilder()
                .addParam("orderNo", orderNo)
                .url(NetUrlUtils.SHOPPING_ORDER_CANCEL)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                refreshData();
                toast(msg);
            }

            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    private void orderRemind(String orderNo) {
        BaseOkHttpClient.newBuilder()
                .addParam("orderNo", orderNo)
                .url(NetUrlUtils.SHOPPING_ORDER_REMIND)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                toast(msg);
            }

            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    private void orderLook(String orderNo) {
        BaseOkHttpClient.newBuilder()
                .addParam("orderNo", orderNo)
                .url(NetUrlUtils.SHOPPING_ORDER_LOOK)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                toast(msg);
            }

            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });

    }

    private void orderConfirm(String orderNo) {
        BaseOkHttpClient.newBuilder()
                .addParam("orderNo", orderNo)
                .url(NetUrlUtils.SHOPPING_ORDER_CONFIRM)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                refreshData();
                toast(msg);
            }

            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    private void orderDelete(String orderNo) {
        BaseOkHttpClient.newBuilder()
                .addParam("orderNo", orderNo)
                .url(NetUrlUtils.SHOPPING_ORDER_DELETE)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                refreshData();
                toast(msg);
            }

            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    private void orderAgain(String orderNo) {
        BaseOkHttpClient.newBuilder()
                .addParam("orderNo", orderNo)
                .url(NetUrlUtils.SHOPPING_ORDER_AGAIN)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                refreshData();
                toast(msg);
            }

            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    private void orderPay(String amount, String orderNo) {
//        String money = edtMoney.getText().toString().trim();
//        if (StringUtils.isEmpty(money)) {
//            toast("你还没有输入充值金额");
//            return;
//        }
//        if (Integer.parseInt(money) <= 0) {
//            toast("充值金额不能小于1元");
//            return;
//        }
//        if (mPayWay == -1) {
//            toast("你还没有选择支付方式");
//            return;
//        }
        mOrderId = orderNo;
        mTotalPrice = amount;
        mPayPopupWindow.setTypePrice("", amount);
        mPayPopupWindow.showAtLocation(rlvList, Gravity.BOTTOM, 0, 0);
    }

    //支付宝支付
    private void aliPay() {
        if (StringUtils.isEmpty(mOrderId)) {
            return;
        }
        BaseOkHttpClient.newBuilder()
                .addParam("orderId", mOrderId)//
                .addParam("orderMoney", StringUtils.isEmpty(String.valueOf(mTotalPrice)) ? "0" : String.valueOf(mTotalPrice))//
                .addParam("orderName", "shangping")//body 随便写
                .addParam("body", "test")//body 随便写
                .url(NetUrlUtils.ALIPAY)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                if (StringUtils.isEmpty(json)) {
                    toast("获取订单信息失败，请重试");
                    return;
                }
                mPayUtils.aliPay(json, mContext);
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

    //微信支付
    private void wxPay() {
        if (StringUtils.isEmpty(mOrderId)) {
            return;
        }
        BaseOkHttpClient.newBuilder()
                .addParam("orderId", mOrderId)//订单id
                .addParam("orderMoney", StringUtils.isEmpty(String.valueOf(mTotalPrice)) ? "0" : String.valueOf(mTotalPrice))//订单价格
                .addParam("body", "shangping")//body体内容随便传
                .url(NetUrlUtils.MILK_TEA_WECHAT_PAY)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                if (StringUtils.isEmpty(json)) {
                    toast("获取订单信息失败，请重试");
                    return;
                }
                PayBean payBean = new Gson().fromJson(json, PayBean.class);
                mPayUtils.wxPay(payBean);
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
}
