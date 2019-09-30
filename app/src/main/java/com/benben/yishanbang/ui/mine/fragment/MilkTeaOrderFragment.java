package com.benben.yishanbang.ui.mine.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.DateUtils;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.LazyBaseFragments;
import com.benben.yishanbang.bean.PayBean;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.pop.EvaluateStarPopuWindow;
import com.benben.yishanbang.pop.PayPopupWindow;
import com.benben.yishanbang.ui.mine.bean.MilkTeaOrderListBean;
import com.benben.yishanbang.ui.tea.activity.ChooseGoodsActivity;
import com.benben.yishanbang.ui.tea.activity.OrderDetailsActivity;
import com.benben.yishanbang.ui.tea.activity.TakeFoodCodeActivity;
import com.benben.yishanbang.utils.PayListenerUtils;
import com.benben.yishanbang.utils.PayResultListener;
import com.benben.yishanbang.utils.PayUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: 奶茶订单
 */
public class MilkTeaOrderFragment extends LazyBaseFragments {

    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private MilkTeaOrderListAdapter mAdapter;
    private int mPage = 1;
    private int mPageSize = 10;
    private boolean isRefresh = true;

    int mSign;
    //支付弹窗
    private PayPopupWindow mPayPopupWindow;
    //支付工具类
    private PayUtils mPayUtils;
    //支付的订单id
    private String mOrderId;
    //支付的总价
    private String mTotalMoney;

    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.frag_shopping_order, null);
        return mRootView;
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        //判断 0.全部 1.未完成  2.已完成
        mSign = bundle.getInt("type");

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = true;
                mPage = 1;
                getOrderList();
            }
        });
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRefresh = false;
                mPage++;
                getOrderList();
            }
        });
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new MilkTeaOrderListAdapter(mContext, Integer.toString(mSign));
        rlvList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<MilkTeaOrderListBean>() {
            @Override
            public void onItemClick(View view, int position, MilkTeaOrderListBean model) {
                Intent intent = new Intent(mContext, OrderDetailsActivity.class);
                intent.putExtra("id", model.getOrderId());//订单id
                intent.putExtra("mShopId", model.getShopId());//店铺id
                intent.putExtra(Constants.EXTRA_KEY_ENTER_TYPE, "1");
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, MilkTeaOrderListBean model) {

            }
        });
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


        PayListenerUtils.getInstance(mContext).addListener(new PayResultListener() {
            @Override
            public void onPaySuccess() {
                mPage = 1;
                getOrderList();
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
                .addParam("orderMoney", StringUtils.isEmpty(String.valueOf(mTotalMoney)) ? "0" : String.valueOf(mTotalMoney))//
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
                .addParam("orderMoney", StringUtils.isEmpty(String.valueOf(mTotalMoney)) ? "0" : String.valueOf(mTotalMoney))//订单价格
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


    @Override
    public void initData() {
        getOrderList();
    }

    //获取订单列表
    private void getOrderList() {

        BaseOkHttpClient.newBuilder()
                .addParam("pageNo", mPage)//购物车Id
                .addParam("pageSize", mPageSize)//购物车Id
                .addParam("sign", mSign + "")//订单类型
                .url(NetUrlUtils.MILK_TEA_ORDER)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<MilkTeaOrderListBean> shoppingOrderListBeans = JSONUtils.jsonString2Beans(json, MilkTeaOrderListBean.class);

                if (isRefresh) {
                    mAdapter.refreshList(shoppingOrderListBeans);
                    refreshLayout.finishRefresh(true);
                } else {
                    mAdapter.appendToList(shoppingOrderListBeans);
                    refreshLayout.finishLoadMore(true);
                }
                if (mAdapter.getItemCount() > 0) {
                    llytNoData.setVisibility(View.GONE);
                    rlvList.setVisibility(View.VISIBLE);
                } else {
                    llytNoData.setVisibility(View.VISIBLE);
                    rlvList.setVisibility(View.GONE);
                }

            }

            @Override
            public void onError(int code, String msg) {
                llytNoData.setVisibility(View.VISIBLE);
                rlvList.setVisibility(View.GONE);
                refreshLayout.finishRefresh(true);
                refreshLayout.finishLoadMore(true);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                refreshLayout.finishRefresh(false);
                refreshLayout.finishLoadMore(false);
            }
        });
    }

    public class MilkTeaOrderListAdapter extends AFinalRecyclerViewAdapter<MilkTeaOrderListBean> {

        private String mSign, mShopid;
        private long mDownTime;

        private Activity mContext;

        public MilkTeaOrderListAdapter(Context ctx, String sign) {
            super(ctx);
            this.mContext = (Activity) ctx;
            this.mSign = sign;
        }

        @Override
        protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
            return new CommonViewHolder(m_Inflater.inflate(R.layout.item_milk_tea_order_list, parent, false));
        }

        @Override
        protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
            ((CommonViewHolder) holder).setContent(getItem(position), position);
        }


        public class CommonViewHolder extends BaseRecyclerViewHolder {

            @BindView(R.id.tv_order_num)
            TextView rlytOneMoreNum;
            @BindView(R.id.tv_time)
            TextView tvTime;
            @BindView(R.id.tv_shop_name)
            TextView tvShopName;
            @BindView(R.id.tv_goods_count)
            TextView tvGoodsCount;
            @BindView(R.id.tv_total_price)
            TextView tvTotalPrice;
            @BindView(R.id.rlyt_one_more)
            RelativeLayout rlytOneMore;
            @BindView(R.id.rlyt_delete)
            RelativeLayout rlytDelete;
            @BindView(R.id.rlyt_evaluate)
            RelativeLayout rlytEvaluate;
            @BindView(R.id.rlyt_dining_code)
            RelativeLayout rlytDiningCode;
            @BindView(R.id.rlyt_pay)
            RelativeLayout rlytPay;

            View itemView;

            public CommonViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                itemView = view;
            }

            private void setContent(MilkTeaOrderListBean mMilkTeaOrderListBean, int position) {

                itemView.setOnClickListener(view -> {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, mMilkTeaOrderListBean);
                    }
                });
                //订单号
                rlytOneMoreNum.setText("自体订单 :" + mMilkTeaOrderListBean.getOrderNo());
                //倒计时
                // countDownTime(mMilkTeaOrderListBean.getOrderTime());
                //店铺名称
                tvShopName.setText(mMilkTeaOrderListBean.getShopName());
                //商品名称+商品数量
                tvGoodsCount.setText(mMilkTeaOrderListBean.getFristGoodsName().getGoodsName() + "  等共计" + mMilkTeaOrderListBean.getGoodsCount() + "件商品");
                tvTotalPrice.setText("合计：¥" + mMilkTeaOrderListBean.getGoodsMoney());
                //评价
                rlytEvaluate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        EvaluateStarPopuWindow mEvaluateStarPopuWindow = new EvaluateStarPopuWindow(mContext, mMilkTeaOrderListBean.getOrderId());
                        mEvaluateStarPopuWindow.showAtLocation(rlvList, Gravity.CENTER, 0, 0);

                    }
                });
                //取餐码
                rlytDiningCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(mContext, TakeFoodCodeActivity.class);
                        intent.putExtra("mFoodCode", mMilkTeaOrderListBean.getFoodcode());
                        intent.putExtra("mCodeImage", mMilkTeaOrderListBean.getCodeImage());
                        startActivity(intent);

                    }
                });
                //删除订单
                rlytDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteOrder(mMilkTeaOrderListBean.getOrderId());
                    }
                });
                //再来一单
                rlytOneMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getAgainOneOrder(mMilkTeaOrderListBean.getOrderId(), mMilkTeaOrderListBean.getShopId());
                    }
                });
                //去支付
                rlytPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOrderId = mMilkTeaOrderListBean.getOrderId();
                        mTotalMoney = mMilkTeaOrderListBean.getGoodsMoney();
                        mPayPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                        mPayPopupWindow.setTypePrice("共计" + mMilkTeaOrderListBean.getGoodsCount() + "件商品", mMilkTeaOrderListBean.getGoodsMoney());
                    }
                });

                //  0全部；1未支付3待取餐；4待评价；5已完成（已评价）；8已取消

                //订单状态-按钮展示情况
                /*待支付：去支付
                待取餐：取餐码
                待评价：删除，再来一单，评价
                已评价：删除，再来一单
                已取消：删除，再来一单*/

                switch (mMilkTeaOrderListBean.getStatus()) {
                    case "1"://1.未支付
                        rlytDelete.setVisibility(View.GONE);
                        rlytOneMore.setVisibility(View.GONE);
                        rlytEvaluate.setVisibility(View.GONE);
                        rlytDiningCode.setVisibility(View.GONE);
                        rlytPay.setVisibility(View.VISIBLE);
                        break;
                    case "3"://3.待取餐
                        rlytDelete.setVisibility(View.GONE);
                        rlytOneMore.setVisibility(View.GONE);
                        rlytEvaluate.setVisibility(View.GONE);
                        rlytPay.setVisibility(View.GONE);
                        rlytDiningCode.setVisibility(View.VISIBLE);
                        break;
                    case "4"://4.待评价
                        rlytDelete.setVisibility(View.VISIBLE);
                        rlytOneMore.setVisibility(View.VISIBLE);
                        rlytEvaluate.setVisibility(View.VISIBLE);
                        rlytDiningCode.setVisibility(View.GONE);
                        rlytPay.setVisibility(View.GONE);
                        break;
                    case "5"://5.已完成（已评价)
                        rlytDelete.setVisibility(View.VISIBLE);
                        rlytOneMore.setVisibility(View.VISIBLE);
                        rlytEvaluate.setVisibility(View.GONE);
                        rlytDiningCode.setVisibility(View.GONE);
                        rlytPay.setVisibility(View.GONE);
                        break;
                    case "8"://8.已取消
                        rlytDelete.setVisibility(View.VISIBLE);
                        rlytOneMore.setVisibility(View.VISIBLE);
                        rlytEvaluate.setVisibility(View.GONE);
                        rlytDiningCode.setVisibility(View.GONE);
                        rlytPay.setVisibility(View.GONE);
                        break;
                }


            }

            //取餐倒计时
            private void countDownTime(String orderTime) {
                Date orderDate = DateUtils.stringToDate(orderTime, "yyyy-MM-dd HH:mm:ss");
                Date currentDate = DateUtils.longToDate(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
                long min = DateUtils.minutesBetween(orderDate, currentDate);

                if (min >= 40) {
                    tvTime.setText("已完成");
                } else {
                    mDownTime = (40 - min) * 60;
                    CountDownTimer mCountDownTimer = new CountDownTimer(mDownTime * 1000, 1000) {
                        @Override
                        public void onTick(long l) {
                            mDownTime--;
                            long min = (long) mDownTime / 60;
                            long second = mDownTime - min * 60;
                            tvTime.setText("倒计时：" + (min < 10 ? "0" + min : min) + ":" + (second < 10 ? "0" + second : second));
                        }

                        @Override
                        public void onFinish() {
                            tvTime.setText("已完成");
                        }
                    }.start();
                }

            }
        }

        //删除订单
        private void deleteOrder(String orderId) {
            BaseOkHttpClient.newBuilder()
                    .addParam("id", orderId)//订单Id
                    .url(NetUrlUtils.DELETE_GOODS_ORDER)
                    .post()
                    .build().enqueue((Activity) mContext, new BaseCallBack<String>() {
                @Override
                public void onSuccess(String json, String msg) {
                    ToastUtils.show(mContext, "删除订单成功");
                    mPage = 1;
                    getOrderList();
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

        //再来一单
        private void getAgainOneOrder(String orderId, String shopId) {
            BaseOkHttpClient.newBuilder()
                    .addParam("id", orderId)//订单Id
                    .url(NetUrlUtils.AGAIN_ONE_ORDER)
                    .post()
                    .build().enqueue((Activity) mContext, new BaseCallBack<String>() {
                @Override
                public void onSuccess(String json, String msg) {
                    startActivity(new Intent(mContext, ChooseGoodsActivity.class).putExtra("shopid", shopId));
                    mContext.finish();
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


}
