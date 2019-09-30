package com.benben.yishanbang.ui.mine.activity;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.mine.adapter.WriteOffCardRecordListAdapter;
import com.benben.yishanbang.ui.mine.bean.AfterMyCardDetailsBean;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 核销优惠卡订单详情
 */
public class WriteOffCardOrderDetailsActivity extends BaseActivity {


    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.iv_card_img)
    ImageView ivCardImg;
    private WriteOffCardRecordListAdapter mAdapter;
    private EditText edtMoney;
    private String mCardId;
    private String mOrderId;
    private String mUserId;
    private double mCardPrice;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_off_card_order_details;
    }

    @Override
    protected void initData() {
        centerTitle.setText("订单详情");

        mCardId = getIntent().getStringExtra("cardId");
        mOrderId = getIntent().getStringExtra("orderId");
        mUserId = getIntent().getStringExtra("userId");


        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new WriteOffCardRecordListAdapter(mContext);
        rlvList.setAdapter(mAdapter);

        getDataList();//获取核销记录列表

        showWriteOffDialog();
    }

    //核销金额的弹窗
    private void showWriteOffDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = View.inflate(mContext, R.layout.dialog_write_off_order, null);
        AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        edtMoney = view.findViewById(R.id.edt_money);
        RelativeLayout rlytConfirm = view.findViewById(R.id.rlyt_confirm);
        TextView tvAll = view.findViewById(R.id.tv_all);

        tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtMoney.setText(mCardPrice+"");
            }
        });
        rlytConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeOffOrder(dialog, edtMoney);
            }
        });
    }

    //核销
    private void writeOffOrder(AlertDialog dialog, EditText edtMoney) {
        if (StringUtils.isEmpty(edtMoney.getText().toString().trim())) {
            toast("请输入核销金额");
            return;
        }
        if (Double.parseDouble(edtMoney.getText().toString().trim()) <= 0) {
            toast("核销金额不能为0");
            return;
        }

        dialog.dismiss();
        writeOffCouponCard();
    }

    /**
     * 核销优惠卡
     */
    private void writeOffCouponCard() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.AFTER_MY_CARD)
                .post()
                .addParam("cardId", mCardId)
                .addParam("userId", mUserId)
                .addParam("orderId", mOrderId)
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
                getDataList();
            }

            @Override
            public void onError(int code, String msg) {
                toast(msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }


    /**
     * 优惠卡核销记录数据请求
     */
    private void getDataList() {

        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.AFTER_MY_CARD)
                .post()
                .addParam("cardId", mCardId)
                .addParam("userId", mUserId)
                .addParam("orderId", mOrderId)
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                AfterMyCardDetailsBean afterMyCardDetailsBean = JSONUtils.jsonString2Bean(json, AfterMyCardDetailsBean.class);
                if (afterMyCardDetailsBean != null) {
                    mAdapter.refreshList(afterMyCardDetailsBean.getAccountBillEntities());
                    tvDate.setText("购买日期：" + afterMyCardDetailsBean.getOrderTime());
                    tvName.setText(afterMyCardDetailsBean.getCardName());
                    mCardPrice = afterMyCardDetailsBean.getPice();
                    String money = "<font color='#666666'>金额：</font><font color='#F67617'> ¥" + afterMyCardDetailsBean.getPice() + "</font>";
                    tvMoney.setText(Html.fromHtml(money));
                    tvShopName.setText("用户昵称:" + afterMyCardDetailsBean.getUserName());
                    ImageUtils.getPic(Constants.IMAGE_BASE_URL + afterMyCardDetailsBean.getCardImg(), ivCardImg, mContext, R.mipmap.ic_default_pic);
                }

            }

            @Override
            public void onError(int code, String msg) {
                toast(msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });

    }

    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
