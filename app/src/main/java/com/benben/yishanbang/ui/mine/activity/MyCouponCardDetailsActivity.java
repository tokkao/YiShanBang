package com.benben.yishanbang.ui.mine.activity;

import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: 优惠卡详情
 */
public class MyCouponCardDetailsActivity extends BaseActivity {

    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.iv_qr_code)
    ImageView ivQrCode;
    @BindView(R.id.iv_card_img)
    ImageView ivCardImg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_coupon_card_details;
    }

    @Override
    protected void initData() {
        centerTitle.setText("优惠卡详情");
        String img_url = getIntent().getStringExtra("img_url");
        String cardName = getIntent().getStringExtra("card_name");
        String shopName = getIntent().getStringExtra("shop_name");
        String cardPrice = getIntent().getStringExtra("card_price");
        String qrCodeImg = getIntent().getStringExtra("qr_code_img");
        String mCardType = getIntent().getStringExtra(Constants.EXTRA_KEY_COMMON_TYPE);
        tvName.setText(cardName);
        tvShopName.setText("商家名称"+shopName);
        ImageUtils.getPic(Constants.IMAGE_BASE_URL + img_url, ivCardImg, mContext, R.mipmap.ic_default_pic);
        ImageUtils.getPic(Constants.IMAGE_BASE_URL + qrCodeImg, ivQrCode, mContext, R.mipmap.ic_default_pic);
        String money = "<font color='#666666'>金额：</font><font color='#F67617'> ¥" + cardPrice + "</font>";
        tvMoney.setText(Html.fromHtml(money));
    }

    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
