package com.benben.yishanbang.ui.tea.activity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.RxBus;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.tea.adapter.UpdateServicePriceAdapter;
import com.benben.yishanbang.ui.tea.bean.UserDetailsInfoBean;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2019/8/16 0016
 * Describe:修改他的线下服务价格
 */
public class UpdateServicePriceActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.rlv_update_user_service_price)
    RecyclerView rlvUpdateUserServicePrice;
    private UpdateServicePriceAdapter mUpdateServicePriceAdapter;//修改服务价格适配器
    private List<UserDetailsInfoBean.ServeTypeBean> mPriceList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_user_service_price;
    }

    @Override
    protected void initData() {

        centerTitle.setText("线下服务价格");
        rightTitle.setText("保存");
        UserDetailsInfoBean mUserInfoBean = (UserDetailsInfoBean) getIntent().getSerializableExtra("info_bean");
        //实例化适配器
        mUpdateServicePriceAdapter = new UpdateServicePriceAdapter(mContext);
        rlvUpdateUserServicePrice.setAdapter(mUpdateServicePriceAdapter);
        rlvUpdateUserServicePrice.setLayoutManager(new LinearLayoutManager(mContext));
        mPriceList = mUserInfoBean.getServeType();
        mUpdateServicePriceAdapter.refreshList(mPriceList);
    }

    @OnClick({R.id.rl_back, R.id.right_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back://返回
                finish();
                break;
            case R.id.right_title://保存
                savePriceInfo();
                break;
        }
    }

    //修改服务价格
    private void savePriceInfo() {
        if (mPriceList == null || mPriceList.size() <= 0) {
            return;
        }
        StringBuffer idStringBuffer = new StringBuffer();
        StringBuffer priceStringBuffer = new StringBuffer();
        for (int i = 0; i < mPriceList.size(); i++) {
            idStringBuffer.append(mPriceList.get(i).getServeTypeEntity().getId() + (i == (mPriceList.size() - 1) ? "" : ","));
            priceStringBuffer.append(mPriceList.get(i).getPrice() + (i == (mPriceList.size() - 1) ? "" : ","));

        }
        BaseOkHttpClient.newBuilder()
                .addParam("id", idStringBuffer)//多个用，号隔开
                .addParam("price", priceStringBuffer)//价格多个用，号隔开
                .url(NetUrlUtils.UPDATE_SERVICE_PRICE)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
                RxBus.getInstance().post(Constants.REFRESH_USER_DETAILS_INFO);
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
}
