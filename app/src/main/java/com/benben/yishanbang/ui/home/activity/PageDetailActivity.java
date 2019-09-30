package com.benben.yishanbang.ui.home.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.benben.commoncore.utils.AppManager;
import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.mine.bean.QRPageListBean;
import com.benben.yishanbang.widget.CustomImageViewFive;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/7
 * Time: 14:12
 * 纸巾详情
 */
public class PageDetailActivity extends BaseActivity {
    @BindView(R.id.iv_img)
    CustomImageViewFive ivImg;
    @BindView(R.id.ll_free)
    LinearLayout llFree;
    @BindView(R.id.ll_charge)
    LinearLayout llCharge;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_content)
    TextView tvContent;
    /* @BindView(R.id.rv_photo)
     CustomRecyclerView rvPhoto;*/
    @BindView(R.id.tv_receiver)
    TextView tvReceiver;
    @BindView(R.id.tv_alipay)
    TextView tvAlipay;
    @BindView(R.id.tv_wxpay)
    TextView tvWxpay;
    @BindView(R.id.ll_pay)
    LinearLayout llPay;
    @BindView(R.id.tv_QRPage_detail_name)
    TextView tvQRPageDetailName;
    @BindView(R.id.tv_QRPage_detail_number)
    TextView tvQRPageDetailNumber;
    @BindView(R.id.tv_QRPage_detail_element)
    TextView tvQRPageDetailElement;
    @BindView(R.id.tv_QRPage_detail_fragrance)
    TextView tvQRPageDetailFragrance;
    @BindView(R.id.tv_QRPage_detail_specification)
    TextView tvQRPageDetailSpecification;
    @BindView(R.id.tv_QRPage_detail_goods_no)
    TextView tvQRPageDetailGoodsNo;
    @BindView(R.id.tv_QRPage_detail_expiration_date)
    TextView tvQRPageDetailExpirationDate;
    @BindView(R.id.ic_QRPage_detail_buttom_icon)
    ImageView icQRPageDetailButtomIcon;
    @BindView(R.id.tv_QRPage_detail_price)
    TextView tvQRPageDetailPrice;


    //private GoodsPhotoAdapter mPhotoAdapter;//图片展示列表

    @Override
    protected int getLayoutId() {
        return R.layout.activity_page_detail;
    }

    @Override
    protected void initData() {
        initTitle("商品详情");

        String isFree = getIntent().getStringExtra("is_money");

        if ("0".equals(isFree)) {
            //免费
            llCharge.setVisibility(View.GONE);
            llPay.setVisibility(View.GONE);

            tvReceiver.setVisibility(View.VISIBLE);
            llFree.setVisibility(View.VISIBLE);
        } else if ("1".equals(isFree)) {
            //收费
            llCharge.setVisibility(View.VISIBLE);
            llPay.setVisibility(View.VISIBLE);

            tvReceiver.setVisibility(View.GONE);
            llFree.setVisibility(View.GONE);
        } else {
            llFree.setVisibility(View.GONE);
            llCharge.setVisibility(View.GONE);
            llPay.setVisibility(View.GONE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        // rvPhoto.setLayoutManager(linearLayoutManager);
        // rvPhoto.setFocusable(false);

        // mPhotoAdapter = new GoodsPhotoAdapter(mContext);
        // rvPhoto.setAdapter(mPhotoAdapter);

        getDataList();

    }

    /**
     * 获取网络数据
     */
    private void getDataList() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.GET_TISSUE_MACHINE)
                .addParam("number", 1)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                QRPageListBean qrPageListBean = JSONUtils.jsonString2Bean(json, QRPageListBean.class);
                tvQRPageDetailName.setText(qrPageListBean.getName());//底部的标题
                tvTitle.setText(qrPageListBean.getName());//顶部标题
                tvQRPageDetailPrice.setText(qrPageListBean.getPrice() + "");//设置顶部价格
                ImageUtils.getPic(Constants.IMAGE_BASE_URL + qrPageListBean.getBottomImg(), icQRPageDetailButtomIcon, mContext, R.mipmap.ic_default_pic);//底部图片
                ImageUtils.getPic(Constants.IMAGE_BASE_URL + qrPageListBean.getHeadImg(), ivImg, mContext, R.mipmap.ic_default_pic);//头部图片
                tvQRPageDetailElement.setText(qrPageListBean.getIsElement());//设置底部成分
                tvQRPageDetailExpirationDate.setText(qrPageListBean.getExpirationDate());//设置底部保质期
                tvQRPageDetailFragrance.setText(qrPageListBean.getFragrance());//设置底部香味花型
                tvQRPageDetailGoodsNo.setText(qrPageListBean.getGoodsNo());//设置底部商品数量
                tvNumber.setText(qrPageListBean.getGoodsNo());//设置顶部商品数量
                tvQRPageDetailNumber.setText(qrPageListBean.getNumber());//设置底部编号
                tvQRPageDetailSpecification.setText(qrPageListBean.getSpecification());//设置底部规格
            }

            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    @OnClick({R.id.tv_receiver, R.id.ll_alipay, R.id.ll_wxpay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //免费时领取
            case R.id.tv_receiver:
                AppManager.getInstance().finishActivity(PageListActivity.class);
                finish();
                break;
            //支付宝支付
            case R.id.ll_alipay:
                break;
            //微信支付
            case R.id.ll_wxpay:
                break;
        }
    }

}
