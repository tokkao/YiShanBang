package com.benben.yishanbang.ui.service.activity;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.PreciseCompute;
import com.benben.commoncore.utils.RxBus;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.commoncore.widget.badgeview.BGABadgeImageView;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.bean.PayBean;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.pop.PayPopupWindow;
import com.benben.yishanbang.pop.VentureShopCartPopupWindow;
import com.benben.yishanbang.ui.service.adapter.VentureShopCateAdapter;
import com.benben.yishanbang.ui.service.adapter.VentureShopGoodsListAdapter;
import com.benben.yishanbang.ui.service.bean.VentureShopCartOrderBean;
import com.benben.yishanbang.ui.service.bean.VentureShopCateListBean;
import com.benben.yishanbang.ui.service.bean.VentureShopGoodsListBean;
import com.benben.yishanbang.ui.tea.adapter.TeaCateAdapter;
import com.benben.yishanbang.ui.tea.bean.MilkTeaBean;
import com.benben.yishanbang.ui.tea.bean.ShopCartInfoBean;
import com.benben.yishanbang.ui.tea.bean.ShoppingCarGoodsBean;
import com.benben.yishanbang.utils.PayListenerUtils;
import com.benben.yishanbang.utils.PayResultListener;
import com.benben.yishanbang.utils.PayUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;

//创业商城
public class VentureShopActivity extends BaseActivity {
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.rlv_choose_good_left)
    RecyclerView rlvChooseGoodLeft;
    @BindView(R.id.rlv_choose_good_right)
    RecyclerView rlvChooseGoodRight;
    @BindView(R.id.iv_shopping_car)
    BGABadgeImageView ivShoppingCar;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.btn_choose_good_goto_pay)
    Button btnChooseGoodGotoPay;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_tea_cate)
    TextView tvTeaCate;
    @BindView(R.id.view_holder)
    View viewHolder;

    //左边奶茶分类
    private TeaCateAdapter mTeaCateAdapter;
    //店铺id
    private String mShopId = "";
    //商品分类adapter
    private VentureShopCateAdapter mCateAdapter;
    private VentureShopGoodsListAdapter mGoodsListAdapter;
    //商品总数量
    private int mTotalGoodNum;
    //商品总价格
    private double mTotalPrice;
    private List<ShoppingCarGoodsBean> mShoppingCarList;
    //购物车弹窗
    private VentureShopCartPopupWindow mShoppingCarPopupWindow;
    //购物车id
    private String mCartIds;
    private String mCurrentCateId;
    private PayPopupWindow mPayPopupWindow;
    private PayUtils mPayUtils;
    private String mOrderNum;
    private String mOrderId;
    private String mOrderMoney;
    private String mAddressId;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_venture_shop;
    }

    private String mCategoryId = "";

    private String mTypeName;

    LocalBroadcastManager mBroadcastManager;

    LocalBroadcastManager mSecondBroadcastManager;

    private String mPosition = "0", mCount = "0";

    private String mGoodTotalCount;


    public List<MilkTeaBean.MilkTeaGoodsVosBeanX.MilkTeaGoodsVosBean> mMilkTeaGoodsVosBeanList = new ArrayList<>();

    int mDataPosition = 0;

    Map<String, Integer> mGoodsCount = new HashMap<>();//标记商品选择数量

    @Override
    protected void initData() {
        //设置标题
        centerTitle.setText("他的店铺");
        mShopId = getIntent().getStringExtra("shop_id");//商店id
        //左边数据适配器
        rlvChooseGoodLeft.setLayoutManager(new LinearLayoutManager(this));
        rlvChooseGoodRight.setLayoutManager(new LinearLayoutManager(this));
        //分类
        mCateAdapter = new VentureShopCateAdapter(mContext);
        rlvChooseGoodLeft.setAdapter(mCateAdapter);
        //商品列表
        mGoodsListAdapter = new VentureShopGoodsListAdapter(mContext, mShopId);
        rlvChooseGoodRight.setAdapter(mGoodsListAdapter);

        mPayUtils = new PayUtils(mContext);
        mShoppingCarPopupWindow = new VentureShopCartPopupWindow(mContext, mShopId);
        mPayPopupWindow = new PayPopupWindow(mContext, new PayPopupWindow.OnPayCallback() {
            @Override
            public void alipay() {
                if (StringUtils.isEmpty(mAddressId)) {
                    toast("请先选择收货地址");
                    return;
                }
                createOrder(1);

            }

            @Override
            public void wxpay() {
                if (StringUtils.isEmpty(mAddressId)) {
                    toast("请先选择收货地址");
                    return;
                }
                createOrder(2);

            }
        });
        //获取购物车信息
        getShoppingCartData();
        //获取商品分类以及商品列表
        getDataList();

        mCateAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<VentureShopCateListBean>() {
            @Override
            public void onItemClick(View view, int position, VentureShopCateListBean model) {
                //分类标题
                tvTeaCate.setText(model.getCategoryName());
                mCurrentCateId = model.getId();
                getGoodsList();
            }

            @Override
            public void onItemLongClick(View view, int position, VentureShopCateListBean model) {

            }
        });

        PayListenerUtils.getInstance(mContext).addListener(new PayResultListener() {
            @Override
            public void onPaySuccess() {
                toast("支付成功");
                finish();
            }

            @Override
            public void onPayError() {
                toast("支付失败");
            }

            @Override
            public void onPayCancel() {
                toast("支付取消");
            }
        });
        //接收数量和价格
        RxBus.getInstance().toObservable(ShopCartInfoBean.class)
                .subscribe(new Observer<ShopCartInfoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShopCartInfoBean shopCartInfoBean) {
                        tvGoodsPrice.setText(shopCartInfoBean.getPrice() + "");
                        if (shopCartInfoBean.getCount() > 0) {
                            ivShoppingCar.showTextBadge(shopCartInfoBean.getCount() + "");
                        } else {
                            ivShoppingCar.hiddenBadge();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }

                });
        //刷新购物车商品
        RxBus.getInstance().toObservable(Integer.class)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        if (integer == Constants.REFRESH_TEA_SHOP_GOODS_INFO) {
                            //getDataList();
                            getShoppingCartData();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }

                });
        //占位的白色view
        mShoppingCarPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                viewHolder.setVisibility(View.GONE);
            }
        });
    }


    private void cardAlipay() {
        BaseOkHttpClient.newBuilder()
                .addParam("orderId", mOrderId)//订单id
                .addParam("orderMoney", StringUtils.isEmpty(mOrderMoney) ? "0" : mOrderMoney + "")//
                .addParam("orderName", "cysc")
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
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });
    }


    //微信支付
    private void cardWxPay() {
        BaseOkHttpClient.newBuilder()
                .addParam("orderId", mOrderId)//订单id
                .addParam("orderMoney", StringUtils.isEmpty(mOrderMoney + "") ? "0" : mOrderMoney + "")//
                .addParam("body", "cysc")
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

    @OnClick({R.id.rl_back, R.id.btn_choose_good_goto_pay, R.id.iv_shopping_car})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back://返回上一页
                finish();
                break;
            case R.id.btn_choose_good_goto_pay: //立即支付
                if (mShoppingCarList == null || mShoppingCarList.size() <= 0) {
                    ToastUtils.show(mContext, "请先添加商品");
                    return;
                }
                mPayPopupWindow.setTypePrice("创业商城商品", mTotalPrice + "");
                mPayPopupWindow.setAddressVisible(true);
                mPayPopupWindow.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.iv_shopping_car://查看我的购物车（出现一个弹窗）
                mShoppingCarPopupWindow.showPop(view);
                viewHolder.setVisibility(View.VISIBLE);
                break;
        }
    }

    //创建订单
    private void createOrder(int payType) {

        BaseOkHttpClient.newBuilder()
                .addParam("shopId", mShopId)//店铺id
                .addParam("goodsNum", mTotalGoodNum)//数量
                .addParam("priceSum", mTotalPrice)//总金额
                .addParam("ids", mCartIds)//商品id
                .addParam("orderType", 6)//订单类型6为创业商城订单
                .addParam("addressId", mAddressId)//地址id
                .url(NetUrlUtils.VENTURE_SHOP_CREATE_ORDER)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                VentureShopCartOrderBean orderBean = JSONUtils.jsonString2Bean(json, VentureShopCartOrderBean.class);
                mOrderId = orderBean.getOrderId();
                mOrderNum = orderBean.getOrderNo();
                mOrderMoney = orderBean.getOrderMoney();
                if (payType == 1) {
                    cardAlipay();
                } else {
                    cardWxPay();
                }

            }

            @Override
            public void onError(int code, String msg) {
                //ToastUtils.show(mContext, msg);

            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });

    }


    //获取商品分类
    private void getDataList() {
        BaseOkHttpClient.newBuilder()
                .addParam("shopId", mShopId)//id
                .url(NetUrlUtils.VENTURE_SHOP_CATE_LIST)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<VentureShopCateListBean> cateList = JSONUtils.jsonString2Beans(json, VentureShopCateListBean.class);
                if (cateList != null && cateList.size() > 0) {
                    //默认显示第一个商品分类的信息
                    mCurrentCateId = cateList.get(0).getId();
                    cateList.get(0).setSelected(true);
                    tvTeaCate.setText(cateList.get(0).getCategoryName());
                    mCateAdapter.refreshList(cateList);
                    getGoodsList();
                }
            }

            @Override
            public void onError(int code, String msg) {
                //ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });
    }

    //获取商品列表
    private void getGoodsList() {
        BaseOkHttpClient.newBuilder()
                .addParam("categoryId", mCurrentCateId)//id
                .addParam("type", 3)//1好评率2价格3销量
                .url(NetUrlUtils.VENTURE_SHOP_GOODS_DETAILS)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<VentureShopGoodsListBean> goodsList = JSONUtils.jsonString2Beans(json, VentureShopGoodsListBean.class);
                mGoodsListAdapter.refreshList(goodsList);

            }

            @Override
            public void onError(int code, String msg) {
                //ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });
    }

    //获取购物车列表数据
    private void getShoppingCartData() {
        BaseOkHttpClient.newBuilder()
                .addParam("shopId", mShopId)//店铺Id
                .url(NetUrlUtils.VENTURE_SHOP_CART_POP)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                mShoppingCarList = JSONUtils.jsonString2Beans(json, ShoppingCarGoodsBean.class);
                if (mShoppingCarList == null) return;

                mTotalGoodNum = 0;
                mTotalPrice = 0f;
                StringBuilder cartSb = new StringBuilder();
                //拼接购物车id  以逗号隔开
                for (int i = 0; i < mShoppingCarList.size(); i++) {
                    cartSb.append(i == (mShoppingCarList.size() - 1) ? mShoppingCarList.get(i).getId() : mShoppingCarList.get(i).getId() + ",");
                    double mGoodPrice = mShoppingCarList.get(i).getPrice();
                    int mGoodNum = mShoppingCarList.get(i).getGoodsNum();
                    double mGoodMoney = PreciseCompute.mul(mGoodPrice, mGoodNum);
                    mTotalPrice = PreciseCompute.add(mTotalPrice, mGoodMoney);
                    mTotalGoodNum = (int) PreciseCompute.add(mTotalGoodNum, mGoodNum);
                }
                mCartIds = cartSb.toString();
                //设置底部商品数量和总价
                if (mTotalGoodNum > 0) {
                    ivShoppingCar.showTextBadge(mTotalGoodNum + "");
                } else {
                    ivShoppingCar.hiddenBadge();
                }
                tvGoodsPrice.setText(mTotalPrice + "");
            }

            @Override
            public void onError(int code, String msg) {
                mShoppingCarList = null;
                ivShoppingCar.hiddenBadge();
                tvGoodsPrice.setText("0");
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });
    }


    //订单绑定收货地址
    private void bindAddress() {
        BaseOkHttpClient.newBuilder()
                .addParam("addressId", mAddressId)//地址id
                .addParam("orderId", mOrderId)//订单id
                .url(NetUrlUtils.VENTURE_SHOP_CHOOSE_ADDRESS)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {

            }

            @Override
            public void onError(int code, String msg) {
                //ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        if (resultCode == Constants.RESULT_CODE_OK) {
            if (mPayPopupWindow != null) {
                mAddressId = data.getStringExtra("address_id");
                mPayPopupWindow.setAddress(data.getStringExtra("address_name"));
                bindAddress();
            }
        }
    }


}
