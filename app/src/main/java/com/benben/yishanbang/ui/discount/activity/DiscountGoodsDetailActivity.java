package com.benben.yishanbang.ui.discount.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.LogUtils;
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
import com.benben.yishanbang.ui.NormalWebViewActivity;
import com.benben.yishanbang.ui.discount.adapter.GoodsPhotoAdapter;
import com.benben.yishanbang.ui.discount.bean.CardGoodsDetailsBean;
import com.benben.yishanbang.utils.PayListenerUtils;
import com.benben.yishanbang.utils.PayResultListener;
import com.benben.yishanbang.utils.PayUtils;
import com.benben.yishanbang.widget.CustomImageViewFive;
import com.benben.yishanbang.widget.CustomRecyclerView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/6
 * Time: 14:33
 * 优惠卡的商品详情
 */
public class DiscountGoodsDetailActivity extends BaseActivity {
    private static final String TAG = "DiscountGoodsDetailActivity";
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.tv_buy)
    TextView tvBuy;
    @BindView(R.id.iv_img)
    CustomImageViewFive ivImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_sale_num)
    TextView tvSaleNum;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.rv_photo)
    CustomRecyclerView rvPhoto;
    @BindView(R.id.tv_label)
    TextView tvLabel;
    @BindView(R.id.iv_collection)
    ImageView ivCollection;

    private GoodsPhotoAdapter mPhotoAdapter;//图片列表集合适配器

    private PayPopupWindow mPayPopupWindow;//支付弹窗
    //商品id
    private String mGoodsId;
    //销量
    private String mSaleCount;
    //是否需要答题
    private String mQuestionType;
    //订单编号
    private String mOrderNo;
    //订单金额
    private String mOrderMoney;
    //支付工具类
    private PayUtils mPayUtils;
    //是否已收藏
    private boolean isCollection = false;
    private String mCollectionId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_discount_goods_detail;
    }

    @Override
    protected void initData() {
        initTitle("商品详情");
        mPayUtils = new PayUtils(mContext);
        mGoodsId = getIntent().getStringExtra("goods_id");
        mSaleCount = getIntent().getStringExtra("sale_count");
        mQuestionType = getIntent().getStringExtra("question_type");
        tvBuy.setText("0".equals(mQuestionType) ? "立即抢购" : "答题抢购");
        tvLabel.setVisibility("0".equals(mQuestionType) ? View.GONE : View.VISIBLE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvPhoto.setLayoutManager(linearLayoutManager);
        rvPhoto.setFocusable(false);

        Drawable drawable = getResources().getDrawable(R.mipmap.ic_question_white);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        rightTitle.setCompoundDrawables(null, null, drawable, null);

        mPhotoAdapter = new GoodsPhotoAdapter(mContext);
        rvPhoto.setAdapter(mPhotoAdapter);

        mPayPopupWindow = new PayPopupWindow(mContext, new PayPopupWindow.OnPayCallback() {
            @Override
            public void alipay() {
                //支付宝
                cardAliPay();
            }

            @Override
            public void wxpay() {
                //微信支付
                cardWxPay();
            }
        });

        //支付的回调监听
        PayListenerUtils.getInstance(this).addListener(new PayResultListener() {
            @Override
            public void onPaySuccess() {
                //支付成功
                toast("支付成功");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onPayError() {
                //支付失败
                toast("支付失败");
            }

            @Override
            public void onPayCancel() {
                //取消支付
                toast("支付取消");
            }
        });
        getGoodsDetails();
    }

    //微信支付
    private void cardWxPay() {
        BaseOkHttpClient.newBuilder()
                .addParam("orderId", mOrderNo)//优惠卡id
                .addParam("orderMoney", StringUtils.isEmpty(mOrderMoney) ? "0" : mOrderMoney)//
                .addParam("body", "youhuika")//body 随便写
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

    //支付宝支付
    private void cardAliPay() {
        BaseOkHttpClient.newBuilder()
                .addParam("orderId", mOrderNo)//优惠卡id
                .addParam("orderMoney", StringUtils.isEmpty(mOrderMoney) ? "0" : mOrderMoney)//
                .addParam("orderName", "youhuika")//body 随便写
                .addParam("body", "test")//body 随便写
                .url(NetUrlUtils.ALIPAY)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {

                LogUtils.e(TAG, "onSuccess: 支付宝：" + json);
                if (StringUtils.isEmpty(json)) {
                    toast("获取订单信息失败，请重试");
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

    //优惠卡生成订单
    private void cardCreateOrder() {
        BaseOkHttpClient.newBuilder()
                .addParam("id", mGoodsId)//优惠卡id
                //    .addParam("shopId", )//店铺id
                .url(NetUrlUtils.CARD_CREATE_ORDER)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                mOrderNo = JSONUtils.getNoteJson(json, "orderNo");
                mOrderMoney = JSONUtils.getNoteJson(json, "orderMoney");
                //直接购买
                if (mPayPopupWindow != null) {
                    mPayPopupWindow.showAtLocation(tvBuy, Gravity.BOTTOM, 0, 0);
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

    //获取优惠卡详情
    private void getGoodsDetails() {
        BaseOkHttpClient.newBuilder()
                .addParam("id", mGoodsId)//优惠卡id
                .addParam("number", mSaleCount)//销量(从店铺列表获取)
                .url(NetUrlUtils.CARD_GOODS_DETAILS)
                .get()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {

                CardGoodsDetailsBean detailsBean = JSONUtils.jsonString2Bean(json, CardGoodsDetailsBean.class);
                ImageUtils.getPic(Constants.IMAGE_BASE_URL+detailsBean.getImgUrl(),ivImg,mContext,R.mipmap.ic_default_pic);
                isCollection = "1".equals(detailsBean.getIsCollection());
                mCollectionId = detailsBean.getCollectionId();
                ivCollection.setImageResource(isCollection ? R.mipmap.ic_goods_collectioned : R.mipmap.ic_goods_collection);
                tvSaleNum.setText(detailsBean.getNumber() + "已售");
                tvContent.setText(detailsBean.getMessage());
                tvName.setText(detailsBean.getName());
                tvPrice.setText(detailsBean.getPrice() + "");
                mPayPopupWindow.setTypePrice(detailsBean.getName(), "" + detailsBean.getPrice());

                String bottomImgs = detailsBean.getBottomImgs();
                //底部图片
                if (!StringUtils.isEmpty(bottomImgs)) {
                    String[] split = bottomImgs.split(",");
                    List<String> list = Arrays.asList(split);
                    mPhotoAdapter.setList(list);
                    mPhotoAdapter.notifyDataSetChanged();
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

    @OnClick({R.id.right_title, R.id.tv_buy, R.id.iv_collection})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.right_title://答题规则
                NormalWebViewActivity.startWithData(mContext, "http://ysbh5.zjxtaq.com/useRule.html", "使用规则", true, false);
                break;
            //去购买
            case R.id.tv_buy:
                if ("立即抢购".equals(tvBuy.getText().toString().trim())) {
                    cardCreateOrder();
                } else {
                    //答题抢购
                    Bundle answerBundle = new Bundle();
                    answerBundle.putString("card_id", mGoodsId);
                    MyApplication.openActivity(mContext, AnswerActivity.class, answerBundle);
                }
                break;
            //收藏
            case R.id.iv_collection:
                if (isCollection) {
                    //取消收藏
                    cancelCollection();
                } else {
                    //收藏
                    collectionGoods();
                }
                break;
        }
    }

    //取消收藏
    private void cancelCollection() {
        BaseOkHttpClient.newBuilder()
                .addParam("id", mCollectionId)
                .addParam("type", 0)
                .url(NetUrlUtils.DELETE_COLLECTION_GOODS)
                .get()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtils.show(mContext, "取消收藏");
                getGoodsDetails();
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

    //收藏商品
    private void collectionGoods() {
        BaseOkHttpClient.newBuilder()
                .addParam("id", mGoodsId)
                .addParam("type", 0)// 收藏类型 1 收藏店铺  0  收藏商品
                .url(NetUrlUtils.COLLECTION_SHOP_GOODS)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                toast("收藏成功");
                getGoodsDetails();
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
