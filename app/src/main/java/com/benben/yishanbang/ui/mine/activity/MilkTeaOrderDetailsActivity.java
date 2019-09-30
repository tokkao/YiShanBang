package com.benben.yishanbang.ui.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.pop.AddShoppingCarPopupWindow;
import com.benben.yishanbang.pop.EvaluateStarPopuWindow;
import com.benben.yishanbang.ui.mine.adapter.MilkTeaOrderDetailsAdapter;
import com.benben.yishanbang.ui.mine.bean.MilkTeaDetailsBean;
import com.benben.yishanbang.ui.tea.activity.TakeFoodCodeActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: //奶茶订单详情
 */
public class MilkTeaOrderDetailsActivity extends BaseActivity {
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.rlyt_cancel_order)
    RelativeLayout rlytCancelOrder;
    @BindView(R.id.rlyt_dining_code)
    RelativeLayout rlytDiningCode;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.tv_shop_address)
    TextView tvShopAddress;
    @BindView(R.id.llyt_order_info)
    LinearLayout llytOrderInfo;
    @BindView(R.id.tv_goods_info_title)
    TextView tvGoodsInfoTitle;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;

    private String mShopid,mOrderId;

    private MilkTeaOrderDetailsAdapter mMilkTeaOrderDetailsAdapter;

    List<MilkTeaDetailsBean.MilkTeaOrderVoBean.MilkTeaOrderGoodsVosBean>  mMilkTeaOrderGoodsVosBeans=new ArrayList<>();

    String mFoodCode,mCodeImage,mPutType;

    private EvaluateStarPopuWindow mEvaluateStarPopuWindow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_milk_tea_order_details;
    }

    @Override
    protected void initData() {
        centerTitle.setText("订单详情");
        Intent intent=getIntent();
        mShopid=intent.getStringExtra("mShopid");
        mOrderId=intent.getStringExtra("mOrderId");
        mPutType=intent.getStringExtra("mPutType");
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));

       // rlvList.setAdapter();
        getOrderMsgDetails();

    }

    @OnClick({R.id.rl_back, R.id.rlyt_cancel_order, R.id.rlyt_dining_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.rlyt_cancel_order:
                getCancleOrder();
                break;
            case R.id.rlyt_dining_code:
                Intent intent=new Intent(this, TakeFoodCodeActivity.class);
                intent.putExtra("mFoodCode",mFoodCode);
                intent.putExtra("mCodeImage",mCodeImage);
                startActivity(intent);
                break;
        }
    }
    //获取订单详情数据
    private void getOrderMsgDetails(){
        BaseOkHttpClient.newBuilder()
                .addParam("id",mOrderId)//订单Id
                .addParam("shopId",mShopid)//店铺id
                .url(NetUrlUtils.MILK_TEA_ORDER_DETAILS)
                .post()
                .build().enqueue((Activity) mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                MilkTeaDetailsBean mMilkTeaDetailsBean= JSONUtils.jsonString2Bean(json,MilkTeaDetailsBean.class);
                //milkTeaDetailsBean.getShopNameAndAddress().getShopAddress();
                tvShopName.setText(mMilkTeaDetailsBean.getShopNameAndAddress().getShopName());
                tvShopAddress.setText(mMilkTeaDetailsBean.getShopNameAndAddress().getShopAddress());

                mFoodCode=mMilkTeaDetailsBean.getMilkTeaOrderVo().getFoodcode();
                mCodeImage=mMilkTeaDetailsBean.getMilkTeaOrderVo().getCodeImage();

                for (int i=0;i<mMilkTeaDetailsBean.getMilkTeaOrderVo().getMilkTeaOrderGoodsVos().size();i++){

                    MilkTeaDetailsBean.MilkTeaOrderVoBean.MilkTeaOrderGoodsVosBean mMilkTeaOrderGoodsVosBean=
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

                mMilkTeaOrderDetailsAdapter=new MilkTeaOrderDetailsAdapter(mContext);
                mMilkTeaOrderDetailsAdapter.appendToList(mMilkTeaOrderGoodsVosBeans);
                rlvList.setAdapter(mMilkTeaOrderDetailsAdapter);

                if (("1").equals(mPutType)){
                    mEvaluateStarPopuWindow = new EvaluateStarPopuWindow(mContext,mOrderId
                    );
                    mEvaluateStarPopuWindow.showAtLocation(rlvList, Gravity.CENTER, 0, 0);
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
    private void getCancleOrder(){

        BaseOkHttpClient.newBuilder()
                .addParam("id",mOrderId)//订单Id
                .url(NetUrlUtils.CANCLE_GOODS_ORDER)
                .post()
                .build().enqueue((Activity) mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtils.show(mContext,"取消订单成功");
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

    //弹出评价页面

}
