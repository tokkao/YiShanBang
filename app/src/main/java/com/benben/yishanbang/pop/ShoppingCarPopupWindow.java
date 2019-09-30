package com.benben.yishanbang.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.LogUtils;
import com.benben.commoncore.utils.PreciseCompute;
import com.benben.commoncore.utils.RxBus;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.tea.adapter.ShoppingCarGoodsListAdapter;
import com.benben.yishanbang.ui.tea.bean.ShopCartInfoBean;
import com.benben.yishanbang.ui.tea.bean.ShoppingCarGoodsBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2019/8/12 0012
 * Describe:查看购物车弹窗
 */
public class ShoppingCarPopupWindow extends PopupWindow {

    @BindView(R.id.tv_clear_shopping_car)
    TextView clearShoppingCar;
    @BindView(R.id.rlv_shopping_car)
    RecyclerView rlvShoppingCar;
    public Activity mContext;
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    private String mShopId;

    public List<String> mShopCarIdList;

    //购物车商品列表
    private ShoppingCarGoodsListAdapter mShoppingCarAdapter;
    //总价
    private double mTotalPrice = 0f;
    //总数量
    private int mTotalGoodNum = 0;

    public ShoppingCarPopupWindow(Activity activity, String mShopId) {
        super(activity);
        this.mContext = activity;
        this.mShopId = mShopId;
        init();
    }

    private void init() {
        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.pop_shopping_car, null);
        ButterKnife.bind(this, contentView);
        mShoppingCarAdapter = new ShoppingCarGoodsListAdapter(mContext);

        // 导入布局
        this.setContentView(contentView);
        // 设置动画效果
        setAnimationStyle(R.style.popwindow_anim_style);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        // 设置可触
        setFocusable(true);
        final ColorDrawable dw = new ColorDrawable(0x0000000);
        setBackgroundDrawable(dw);
        /// 单击屏幕关闭弹窗
        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int height = view.findViewById(R.id.llyt_shoppingcar).getTop();
                int bottom = view.findViewById(R.id.llyt_shoppingcar).getBottom();
                int y = (int) motionEvent.getY();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height || y > bottom) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        rlvShoppingCar.setLayoutManager(new LinearLayoutManager(mContext));
        rlvShoppingCar.setAdapter(mShoppingCarAdapter);

        mShoppingCarAdapter.setOnGoodsCountChangeListener(new ShoppingCarGoodsListAdapter.OnGoodsCountChangeListener() {
            @Override
            public void onReduceListener(int position, ShoppingCarGoodsBean mGoodsBean) {
                if (mGoodsBean.getGoodsNum() == 0) {
                    return;
                }
                int reduce = mGoodsBean.getGoodsNum() - 1;
                updateShoppingCar(mGoodsBean.getId(), reduce);
            }

            @Override
            public void onAddListener(int position, ShoppingCarGoodsBean mGoodsBean) {
                int add = mGoodsBean.getGoodsNum() + 1;
                updateShoppingCar(mGoodsBean.getId(), add);
            }
        });

    }

    //显示popwindow
    public void showPop(View view) {
        showAtLocation(view, Gravity.BOTTOM, 0, 0);
        //获取数据
        getShoppingCar();
    }

    @OnClick({R.id.tv_clear_shopping_car})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_clear_shopping_car://清空购物车
                clearShoppingCar();
                break;
        }
    }

    //清空购物车
    private void clearShoppingCar() {
        if (mShopCarIdList == null || mShopCarIdList.size() <= 0) {
            return;
        }
        String mJSONCarId = mShopCarIdList.toString();
        LogUtils.e("mJSONCarId", mJSONCarId);
        String mShopCarId = mJSONCarId.replace("[", "");
        String mShopCarId2 = mShopCarId.replace("]", "");
        String mShopCarId3 = mShopCarId2.replace(" ", "");
        LogUtils.e("mShopCarId3", mShopCarId3);
        BaseOkHttpClient.newBuilder()
                .addParam("ids", mShopCarId3)//购物车Id
                .url(NetUrlUtils.CLEAR_SHOPPING_CAR)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                // ToastUtils.show(mContext, msg);
                getShoppingCar();
                RxBus.getInstance().post(Constants.REFRESH_TEA_SHOP_GOODS_INFO);
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

    //获取购物车列表数据
    private void getShoppingCar() {
        BaseOkHttpClient.newBuilder()
                .addParam("shopId", mShopId)//店铺Id
                .url(NetUrlUtils.SHOPPING_CAR)
                .get()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<ShoppingCarGoodsBean> addShoppingCarBeans = JSONUtils.jsonString2Beans(json, ShoppingCarGoodsBean.class);
                if (addShoppingCarBeans == null || addShoppingCarBeans.size() <= 0) {
                    llytNoData.setVisibility(View.VISIBLE);
                    rlvShoppingCar.setVisibility(View.GONE);
                    return;
                }

                //删除商品数量为0的数据
                Iterator<ShoppingCarGoodsBean> iter = addShoppingCarBeans.iterator();
                while (iter.hasNext()) {
                    int num = iter.next().getGoodsNum();
                    if (num <= 0) {
                        iter.remove();
                    }
                }
                mTotalPrice = 0f;
                mTotalGoodNum = 0;
                llytNoData.setVisibility(View.GONE);
                rlvShoppingCar.setVisibility(View.VISIBLE);
                mShoppingCarAdapter.refreshList(addShoppingCarBeans);
                mShopCarIdList = new ArrayList<>();
                for (int i = 0; i < addShoppingCarBeans.size(); i++) {
                    mShopCarIdList.add(addShoppingCarBeans.get(i).getId());
                    //单价
                    double mGoodPrice = addShoppingCarBeans.get(i).getPrice();
                    //数量
                    double mGoodNum = addShoppingCarBeans.get(i).getGoodsNum();
                    double mGoodMoney = PreciseCompute.mul(mGoodPrice, mGoodNum);
                    //
                    mTotalPrice = PreciseCompute.add(mTotalPrice, mGoodMoney);
                    mTotalGoodNum = (int) PreciseCompute.add(mTotalGoodNum, mGoodNum);
                }

                //更新底部价格
                RxBus.getInstance().post(new ShopCartInfoBean(mTotalGoodNum, mTotalPrice));
            }

            @Override
            public void onError(int code, String msg) {
                //ToastUtils.show(mContext, msg);
                llytNoData.setVisibility(View.VISIBLE);
                rlvShoppingCar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                llytNoData.setVisibility(View.VISIBLE);
                rlvShoppingCar.setVisibility(View.GONE);
            }
        });
    }

    //更新购物车
    private void updateShoppingCar(String cartId, int goodsNum) {
        BaseOkHttpClient.newBuilder()
                .addParam("id", cartId)//购物车Id
                .addParam("goodsNum", goodsNum)//购物车数量
                .url(NetUrlUtils.UPDATE_GOODS_CART)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                getShoppingCar();
                RxBus.getInstance().post(Constants.REFRESH_TEA_SHOP_GOODS_INFO);
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
}



