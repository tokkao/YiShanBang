package com.benben.yishanbang.ui.mine.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.discount.bean.CardShopCateBean;
import com.benben.yishanbang.ui.mine.bean.StoreManageShopInfoBean;
import com.kongzue.dialog.interfaces.OnMenuItemClickListener;
import com.kongzue.dialog.v3.BottomMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: 店铺管理
 */
public class StoreManageActivity extends BaseActivity {

    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.edt_introduction)
    EditText edtIntroduction;
    @BindView(R.id.edt_service_info)
    EditText edtServiceInfo;
    @BindView(R.id.tv_store_category)
    TextView tvStoreCategory;
    //选择的分类
    private CardShopCateBean mShopCateBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_manage;
    }

    @Override
    protected void initData() {
        centerTitle.setText("店铺管理");
        getShopInfo();
    }


    @OnClick({R.id.rl_back, R.id.llyt_cate,R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.llyt_cate://店铺分类
                getShopCateList();
                break;
            case R.id.btn_confirm:
                saveShopInfo();
                break;
        }
    }
    //获取店铺信息
    private void getShopInfo() {
        BaseOkHttpClient.newBuilder()
                .addParam("token", MyApplication.mPreferenceProvider.getToken())
                .url(NetUrlUtils.SHOP_MANAGE_INFO)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                StoreManageShopInfoBean storeManageShopInfoBean = JSONUtils.jsonString2Bean(json, StoreManageShopInfoBean.class);
                if(storeManageShopInfoBean != null){
                    mShopCateBean = new CardShopCateBean();
                    mShopCateBean.setCategoryId(storeManageShopInfoBean.getCategoryId());
                    mShopCateBean.setCategoryName(storeManageShopInfoBean.getCategoryName());

                    tvStoreCategory.setText(storeManageShopInfoBean.getCategoryName());
                    edtServiceInfo.setText(storeManageShopInfoBean.getShopCenter());
                    edtIntroduction.setText(storeManageShopInfoBean.getShopMessage());
                }else {
                    //toast(msg);
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
    //提交店铺信息
    private void saveShopInfo() {
        String introduction = edtIntroduction.getText().toString().trim();
        String serviceInfo = edtServiceInfo.getText().toString().trim();
        if(StringUtils.isEmpty(introduction)){
           toast("请输入店铺介绍");
           return;
        }
        if(StringUtils.isEmpty(serviceInfo)){
           toast("请输入客服信息");
           return;
        }
        if(mShopCateBean == null){
            toast("请选择店铺分类");
            return;
        }

        BaseOkHttpClient.newBuilder()
                .addParam("id",mShopCateBean.getCategoryId())
                .addParam("message",introduction)
                .addParam("center",serviceInfo)
                .url(NetUrlUtils.UPDATE_SHOP_MANAGE)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
                finish();
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

    //店铺分类
    private void getShopCateList() {

        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.GET_STORE_CATEGORY)
                .get()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<CardShopCateBean> cardShopCateBeans = JSONUtils.jsonString2Beans(json, CardShopCateBean.class);
                if (cardShopCateBeans != null && cardShopCateBeans.size() > 0) {
                    ArrayList<String> strings = new ArrayList<>();
                    for (int i = 0; i < cardShopCateBeans.size(); i++) {
                        strings.add(cardShopCateBeans.get(i).getCategoryName());
                    }
                    BottomMenu.show((AppCompatActivity) mContext, "选择店铺分类", strings, new OnMenuItemClickListener() {
                        @Override
                        public void onClick(String text, int index) {
                            mShopCateBean = cardShopCateBeans.get(index);
                            tvStoreCategory.setText(mShopCateBean.getCategoryName());
                        }
                    });

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


}
