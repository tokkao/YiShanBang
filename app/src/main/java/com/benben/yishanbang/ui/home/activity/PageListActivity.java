package com.benben.yishanbang.ui.home.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.mine.bean.QRPageListBean;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/7
 * Time: 11:40
 * 纸巾机商品列表
 */
public class PageListActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_sale_num)
    TextView tvSaleNum;
    @BindView(R.id.tv_label)
    TextView tvLabel;
    @BindView(R.id.llyt_page)
    LinearLayout llytPage;
    @BindView(R.id.tv_free)
    TextView tvFree;
    private QRPageListBean qrPageListBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_page_list;
    }

    @Override
    protected void initData() {
        initTitle("纸巾机");

        getDataList();

    }

    /**
     * 获取列表
     */
    private void getDataList() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.GET_TISSUE_MACHINE)
                .addParam("number", getIntent().getStringExtra("number"))
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                qrPageListBean = JSONUtils.jsonString2Bean(json, QRPageListBean.class);

                ImageUtils.getPic(Constants.IMAGE_BASE_URL +qrPageListBean.getImg(), ivImg, mContext, R.mipmap.ic_default_pic);//纸巾图片
                tvTitle.setText(qrPageListBean.getName());//纸巾名称;
                tvPrice.setText(qrPageListBean.getPrice() + "");//纸巾价格
                tvSaleNum.setText(qrPageListBean.getInventory() + "库存");//库存
                if ("1".equals(qrPageListBean.getIsMoney())) {
                    tvType.setVisibility(View.VISIBLE);
                } else if("0".equals(qrPageListBean.getIsMoney())) {
                    tvType.setVisibility(View.GONE);
                    tvFree.setVisibility(View.VISIBLE);
                }else{
                    tvFree.setVisibility(View.GONE);
                    tvType.setVisibility(View.GONE);//两个图标头隐藏
                }
                toast(msg);
            }

            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });

    }

    @OnClick(R.id.llyt_page)
    public void onViewClicked() {
        Intent intent = new Intent(mContext, PageDetailActivity.class);
        intent.putExtra("is_money", qrPageListBean.getIsMoney());
        startActivity(intent);
    }

}
