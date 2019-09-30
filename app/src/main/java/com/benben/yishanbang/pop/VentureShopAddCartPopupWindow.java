package com.benben.yishanbang.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.RxBus;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.tea.adapter.AddShoppingCarAdapter;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Administrator on 2019/8/12 0012
 * Describe:创业商城加入购物车弹窗
 */
public class VentureShopAddCartPopupWindow extends PopupWindow implements View.OnClickListener {


    @BindView(R.id.iv_shopping_car_icon)
    ImageView ivShoppingCarIcon;
    @BindView(R.id.tv_shopping_car_name)
    TextView tvShoppingCarName;
    @BindView(R.id.tv_shopping_car_price)
    TextView tvShoppingCarPrice;
    @BindView(R.id.tv_choose_goods_priced)
    TextView tvChooseGoodsPriced;
    @BindView(R.id.iv_shopping_car_minus)
    ImageView ivShoppingCarMinus;
    @BindView(R.id.tv_shopping_car_count)
    TextView tvShoppingCarCount;
    @BindView(R.id.iv_shopping_car_add)
    ImageView ivShoppingCarAdd;
    @BindView(R.id.tv_goods_des)
    TextView tvGoodsDes;
    @BindView(R.id.tv_goods_describe)
    TextView tvGoodsDescribe;
    @BindView(R.id.btn_gdd_shopping_car)
    Button btnGddShoppingCar;
    @BindView(R.id.llyt_shopping_car_add)
    LinearLayout llytShoppingCarAdd;
    private Context mContext;
    private View mContentView;
    private AddShoppingCarAdapter mAddShoppingCarAdapter;
    private String mGoodsName, mDescription, mImgUrl, mPrice, mOldPrice, mGoodId, mShopId, mGoodCount = "1";

    private ShoppingCarPopupWindow mShoppingCarPopupWindow;//购物车弹窗


    public VentureShopAddCartPopupWindow(Context context, String mDescription, String mImgUrl, String mPrice,
                                         String mGoodsName, String mOldPrice, String mGoodId, String mShopId) {
        super(context);
        this.mContext = context;
        this.mDescription = mDescription;
        this.mImgUrl = mImgUrl;
        this.mPrice = mPrice;
        this.mGoodsName = mGoodsName;
        this.mOldPrice = mOldPrice;
        this.mShopId = mShopId;
        this.mGoodId = mGoodId;
        init();

    }

    private void init() {

        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContentView = inflater.inflate(R.layout.pop_venture_add_cart, null);
        ButterKnife.bind(this, mContentView);

        // 导入布局
        this.setContentView(mContentView);
        // 设置动画效果
        setAnimationStyle(R.style.popwindow_anim_style);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        // 设置可触
        setFocusable(true);
        final ColorDrawable dw = new ColorDrawable(0x0000000);
        setBackgroundDrawable(dw);
        /// 单击屏幕关闭弹窗
        mContentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int height = view.findViewById(R.id.llyt_shopping_car_add).getTop();
                int bottom = view.findViewById(R.id.llyt_shopping_car_add).getBottom();
                int y = (int) motionEvent.getY();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height || y > bottom) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        tvGoodsDescribe.setText(mDescription);
        tvShoppingCarPrice.setText("¥ " + mPrice);
        tvShoppingCarName.setText(mGoodsName);
        ImageUtils.getPic(Constants.IMAGE_BASE_URL + mImgUrl, ivShoppingCarIcon, mContext, R.mipmap.ic_default_pic);


        ivShoppingCarMinus.setOnClickListener(this);
        ivShoppingCarAdd.setOnClickListener(this);
        btnGddShoppingCar.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_shopping_car_minus:
                int count = Integer.parseInt(tvShoppingCarCount.getText().toString().trim());
                if (count <= 1) {
                    return;
                }
                --count;
                tvShoppingCarCount.setText(count + "");

                mGoodCount = count + "";
                break;
            case R.id.iv_shopping_car_add:
                int count1 = Integer.parseInt(tvShoppingCarCount.getText().toString().trim());
                ++count1;
                tvShoppingCarCount.setText(count1 + "");
                if (count1 > 0) {
                    ivShoppingCarMinus.setEnabled(true);
                }
                mGoodCount = count1 + "";
                break;
            case R.id.btn_gdd_shopping_car:
                addShoppingCar();
                break;
        }
    }

    //添加到购物车
    private void addShoppingCar() {
        if (StringUtils.isEmpty(mGoodId)) {
            ToastUtils.show(mContext, "商品不能为空");
            return;
        }
        if (StringUtils.isEmpty(mGoodCount) || ("0").equals(mGoodCount)) {
            ToastUtils.show(mContext, "请添加商品");
            return;
        }
        BaseOkHttpClient.newBuilder()
                .addParam("id", mGoodId)//商品Id
                .addParam("goodsNum", mGoodCount)//商品数量
                .addParam("shopId", mShopId)//店铺id
                .url(NetUrlUtils.ADD_SHOPPING_CAR)
                .post()
                .build().enqueue((Activity) mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                dismiss();
                ToastUtils.show(mContext, "添加购物车成功");
                //传到activity  刷新店铺商品信息
                RxBus.getInstance().post(Constants.REFRESH_TEA_SHOP_GOODS_INFO);
                //mShoppingCarPopupWindow = new ShoppingCarPopupWindow((Activity) mContext, mShopId);
                //mShoppingCarPopupWindow.showAtLocation(mContentView, Gravity.BOTTOM, 0, 0);
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


