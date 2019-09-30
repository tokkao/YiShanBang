package com.benben.yishanbang.ui.tea.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.PreciseCompute;
import com.benben.commoncore.utils.RxBus;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.commoncore.widget.badgeview.BGABadgeImageView;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.pop.ShoppingCarPopupWindow;
import com.benben.yishanbang.ui.tea.adapter.TeaCateAdapter;
import com.benben.yishanbang.ui.tea.adapter.TeaGoodsListAdapter;
import com.benben.yishanbang.ui.tea.bean.MilkTeaBean;
import com.benben.yishanbang.ui.tea.bean.ShopCartInfoBean;
import com.benben.yishanbang.ui.tea.bean.ShoppingCarGoodsBean;
import com.benben.yishanbang.ui.tea.bean.TeaShopGoodsInfoBean;

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

/**
 * Created by Administrator on 2019/8/10 0012
 * Describe:选择商品页面
 */
public class ChooseGoodsActivity extends BaseActivity {

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
    @BindView(R.id.iv_shop_img)
    ImageView ivShopImg;
    @BindView(R.id.tv_tea_cate)
    TextView tvTeaCate;
    @BindView(R.id.view_holder)
    View viewHolder;

    //左边奶茶分类
    private TeaCateAdapter mTeaCateAdapter;
    //店铺id
    private String mShopId = "";
    //商品分类adapter
    private TeaCateAdapter mCateAdapter;
    private TeaGoodsListAdapter mGoodsListAdapter;
    //商品总数量
    private int mTotalGoodNum;
    //商品总价格
    private double mTotalPrice;
    private List<ShoppingCarGoodsBean> mShoppingCarList;
    //购物车弹窗
    private ShoppingCarPopupWindow mShoppingCarPopupWindow;
    //购物车id
    private String mCartIds;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_goods;
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
        centerTitle.setText("选择商品");
        mShopId = getIntent().getStringExtra("shopid");//商店id
        //左边数据适配器
        rlvChooseGoodLeft.setLayoutManager(new LinearLayoutManager(this));
        rlvChooseGoodRight.setLayoutManager(new LinearLayoutManager(this));
        //分类
        mCateAdapter = new TeaCateAdapter(mContext);
        rlvChooseGoodLeft.setAdapter(mCateAdapter);
        //商品列表
        mGoodsListAdapter = new TeaGoodsListAdapter(mContext);
        rlvChooseGoodRight.setAdapter(mGoodsListAdapter);

        mShoppingCarPopupWindow = new ShoppingCarPopupWindow(mContext, mShopId);

        //获取购物车信息
        getShoppingCartData();
        //获取商品分类以及商品列表
        getDataList();

        mCateAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<TeaShopGoodsInfoBean.MilkTeaGoodsVosBeanX>() {
            @Override
            public void onItemClick(View view, int position, TeaShopGoodsInfoBean.MilkTeaGoodsVosBeanX model) {
                //分类标题
                tvTeaCate.setText(model.getCategoryName());
                mGoodsListAdapter.refreshList(model.getMilkTeaGoodsVos());
            }

            @Override
            public void onItemLongClick(View view, int position, TeaShopGoodsInfoBean.MilkTeaGoodsVosBeanX model) {

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
        //刷新店铺商品和购物车商品
        RxBus.getInstance().toObservable(Integer.class)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        if (integer == Constants.REFRESH_TEA_SHOP_GOODS_INFO) {
                            getDataList();
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
                Intent intent = new Intent(ChooseGoodsActivity.this, ConfirmOrderActivity.class);
                intent.putExtra("mShopId", mShopId);
                intent.putExtra("goodsNum", Integer.toString(mTotalGoodNum));
                intent.putExtra("priceSum", Double.toString(mTotalPrice));
                intent.putExtra("ids", mCartIds);
                startActivity(intent);
                break;
            case R.id.iv_shopping_car://查看我的购物车（出现一个弹窗）
                mShoppingCarPopupWindow.showPop(view);
                viewHolder.setVisibility(View.VISIBLE);
                break;
        }
    }


    //获取商品信息
    private void getDataList() {
        BaseOkHttpClient.newBuilder()
                .addParam("shopId", mShopId)//id
                .url(NetUrlUtils.GOODS_LIST)
                .get()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                TeaShopGoodsInfoBean infoBean = JSONUtils.jsonString2Bean(json, TeaShopGoodsInfoBean.class);
                if (infoBean == null) return;
                //店铺图片
                ImageUtils.getPic(Constants.IMAGE_BASE_URL + infoBean.getImageUrl(), ivShopImg, getApplicationContext(), R.mipmap.ic_default_pic);
                List<TeaShopGoodsInfoBean.MilkTeaGoodsVosBeanX> goodsInfoList = infoBean.getMilkTeaGoodsVos();
                //默认显示第一个商品分类的信息
                goodsInfoList.get(0).setSelected(true);
                tvTeaCate.setText(goodsInfoList.get(0).getCategoryName());
                mCateAdapter.refreshList(goodsInfoList);
                mGoodsListAdapter.refreshList(goodsInfoList.get(0).getMilkTeaGoodsVos());
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
                .url(NetUrlUtils.SHOPPING_CAR)
                .get()
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
                    double mGoodMoney = PreciseCompute.mul( mGoodPrice , mGoodNum);
                    mTotalPrice = PreciseCompute.add(mTotalPrice , mGoodMoney);
                    mTotalGoodNum = (int)PreciseCompute.add(mTotalGoodNum , mGoodNum);
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

}
