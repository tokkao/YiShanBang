package com.benben.yishanbang.ui.service.activity;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.LogUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.bean.PayBean;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.pop.PayPopupWindow;
import com.benben.yishanbang.ui.discount.adapter.GoodsPhotoAdapter;
import com.benben.yishanbang.ui.service.bean.VentureShopGoodsDetailsBean;
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
 * 创业商城的商品详情
 */
public class VentureShopGoodsDetailActivity extends BaseActivity {
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
    private String mShopId;
    private String mAddressId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_venture_shop_goods_details;
    }

    @Override
    protected void initData() {
        initTitle("商品详情");
        mPayUtils = new PayUtils(mContext);
        mGoodsId = getIntent().getStringExtra("goods_id");
        mShopId = getIntent().getStringExtra("shop_id");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvPhoto.setLayoutManager(linearLayoutManager);
        rvPhoto.setFocusable(false);


        mPhotoAdapter = new GoodsPhotoAdapter(mContext);
        rvPhoto.setAdapter(mPhotoAdapter);

        mPayPopupWindow = new PayPopupWindow(mContext, new PayPopupWindow.OnPayCallback() {
            @Override
            public void alipay() {
                if (StringUtils.isEmpty(mAddressId)) {
                    toast("请先选择守护地址");
                    return;
                }
                cardCreateOrder(1);


            }

            @Override
            public void wxpay() {
                if (StringUtils.isEmpty(mAddressId)) {
                    toast("请先选择收获地址");
                    return;
                }
                cardCreateOrder(2);

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
                .addParam("body", "cysc")//body 随便写
                .url(NetUrlUtils.VENTURE_SHOP_WX_PAY)
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
                .addParam("orderName", "cysc")//body 随便写
                .addParam("body", "test")//body 随便写
                .url(NetUrlUtils.VENTURE_SHOP_ALI_PAY)
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

    //生成订单
    private void cardCreateOrder(int type) {
        BaseOkHttpClient.newBuilder()
                .addParam("id", mGoodsId)//商品id
                .addParam("shopId", mShopId)//店铺id
                .addParam("addressId", mAddressId)//地址id
                .url(NetUrlUtils.VENTURE_SHOP_GOODS_DETAILS_CREATE_ORDER)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                mOrderNo = JSONUtils.getNoteJson(json, "orderNo");
                mOrderMoney = JSONUtils.getNoteJson(json, "orderMoney");
                //直接购买
                if (type == 1) {
                    //支付宝
                    cardAliPay();
                } else {
                    //微信支付
                    cardWxPay();
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

    //获取商品详情
    private void getGoodsDetails() {
        BaseOkHttpClient.newBuilder()
                .addParam("id", mGoodsId)//商品id
                .url(NetUrlUtils.GET_VENTURE_SHOP_GOODS_DETAILS)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {

                VentureShopGoodsDetailsBean detailsBean = JSONUtils.jsonString2Bean(json, VentureShopGoodsDetailsBean.class);
                ImageUtils.getPic(Constants.IMAGE_BASE_URL + detailsBean.getImgUrl(), ivImg, mContext, R.mipmap.ic_default_pic);
                tvSaleNum.setText(detailsBean.getCount() + "已售");
                tvContent.setText(detailsBean.getMegssage());
                tvName.setText(detailsBean.getName());
                tvPrice.setText(detailsBean.getPrice() + "");
                mPayPopupWindow.setTypePrice(detailsBean.getName(), "" + detailsBean.getPrice());
                mPayPopupWindow.setAddressVisible(true);
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

    @OnClick({R.id.right_title, R.id.tv_buy})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.right_title:
                break;
            //去购买
            case R.id.tv_buy:
                mPayPopupWindow.showAtLocation(tvBuy, Gravity.BOTTOM, 0, 0);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        if (resultCode == Constants.RESULT_CODE_OK) {
            if (mPayPopupWindow != null) {
                mAddressId = data.getStringExtra("address_id");
                mPayPopupWindow.setAddress(data.getStringExtra("address_name"));
            }
        }
    }

}
