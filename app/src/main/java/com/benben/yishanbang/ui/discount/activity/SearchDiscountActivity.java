package com.benben.yishanbang.ui.discount.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.discount.adapter.DiscountAdapter;
import com.benben.yishanbang.ui.discount.bean.SearchCardListBean;
import com.benben.yishanbang.ui.mine.adapter.WalletCouponCardSearchAdapter;
import com.benben.yishanbang.ui.mine.bean.WalletCouponCardSearchBean;
import com.benben.yishanbang.ui.tea.activity.StoresDetailsActivity;
import com.benben.yishanbang.ui.tea.adapter.TeaShopListAdapter;
import com.benben.yishanbang.ui.tea.bean.TeaBean;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/6
 * Time: 10:38
 * 搜索结果页
 */
public class SearchDiscountActivity extends BaseActivity {
    @BindView(R.id.rv_search)
    RecyclerView rlvSearch;
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;

    private DiscountAdapter mAdapter;//优惠券列表适配器
    private WalletCouponCardSearchAdapter mWalletCouponCardSearchAdapter;//优惠卡列表适配器
    private String mKeyWord;
    //0 首页 1 优惠卡 2奶茶 3钱包--优惠卡
    private int mEnterType;
    private TeaShopListAdapter mTeaAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_discount;
    }

    @Override
    protected void initData() {
        initTitle("搜索结果");
        mKeyWord = getIntent().getStringExtra("key_word");
        mEnterType = getIntent().getIntExtra(Constants.EXTRA_KEY_ENTER_TYPE, 0);
        rlvSearch.setLayoutManager(new LinearLayoutManager(mContext));

        switch (mEnterType) {
            case 0://首页（也是搜索优惠卡）
            case 1://优惠卡
                mAdapter = new DiscountAdapter(mContext);
                rlvSearch.setAdapter(mAdapter);
                getCardShopList();
                break;
            case 2://奶茶
                mTeaAdapter = new TeaShopListAdapter(mContext);
                rlvSearch.setAdapter(mTeaAdapter);
                getTeaShopList();
                mTeaAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<TeaBean>() {
                    @Override
                    public void onItemClick(View view, int position, TeaBean model) {
                        Intent intent = new Intent(mContext, StoresDetailsActivity.class);
                        intent.putExtra("shopid", model.getShopId());
                        mContext.startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position, TeaBean model) {

                    }
                });
                break;
            case 3:
                mWalletCouponCardSearchAdapter = new WalletCouponCardSearchAdapter(mContext);
                rlvSearch.setAdapter(mWalletCouponCardSearchAdapter);
                getCouponCardList();
                break;
            default:
                mAdapter = new DiscountAdapter(mContext);
                rlvSearch.setAdapter(mAdapter);
                getCardShopList();
                break;
        }

    }

    //搜索奶茶店铺
    private void getTeaShopList() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.STORES_LIST)
                .addParam("longitude", MyApplication.mPreferenceProvider.getLongitude())
                .addParam("latitude", MyApplication.mPreferenceProvider.getLatitude())
                .addParam("city", MyApplication.mPreferenceProvider.getCity())
                .addParam("county", MyApplication.mPreferenceProvider.getDistrict())
                .addParam("shopPlace", mKeyWord)//搜索时传地址
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                if(StringUtils.isEmpty(json)){
                    return;
                }
                List<TeaBean> teaBeans = JSONUtils.jsonString2Beans(json, TeaBean.class);
                mTeaAdapter.refreshList(teaBeans);

                if (mTeaAdapter.getItemCount() > 0) {
                    llytNoData.setVisibility(View.GONE);
                    rlvSearch.setVisibility(View.VISIBLE);
                } else {
                    llytNoData.setVisibility(View.VISIBLE);
                    rlvSearch.setVisibility(View.GONE);
                }

            }

            @Override
            public void onError(int code, String msg) {
                ToastUtils.show(mContext, msg);
                llytNoData.setVisibility(View.VISIBLE);
                rlvSearch.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    /**
     * 个人中心----钱包----优惠卡列表
     */
    private void getCouponCardList() {
        BaseOkHttpClient.newBuilder()
                .addParam("cardName", mKeyWord)
                .url(NetUrlUtils.WALLET_QUERY_MY_CARD_SEARCH_LIST)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<WalletCouponCardSearchBean> walletCouponCardSearchBeans = JSONUtils.jsonString2Beans(json, WalletCouponCardSearchBean.class);
                mWalletCouponCardSearchAdapter.setWalletCouponCardSearchBeans(walletCouponCardSearchBeans);
                mWalletCouponCardSearchAdapter.notifyDataSetChanged();
                if (mWalletCouponCardSearchAdapter.getItemCount() > 0) {
                    llytNoData.setVisibility(View.GONE);
                    rlvSearch.setVisibility(View.VISIBLE);
                } else {
                    llytNoData.setVisibility(View.VISIBLE);
                    rlvSearch.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(int code, String msg) {
                llytNoData.setVisibility(View.VISIBLE);
                rlvSearch.setVisibility(View.GONE);
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    /**
     * 优惠卡
     */
    private void getCardShopList() {
        BaseOkHttpClient.newBuilder()
                .addParam("province", MyApplication.mPreferenceProvider.getProvince())
                .addParam("city", MyApplication.mPreferenceProvider.getCity())
                .addParam("county", MyApplication.mPreferenceProvider.getDistrict())
                .addParam("longitude", MyApplication.mPreferenceProvider.getLongitude())
                .addParam("latitude", MyApplication.mPreferenceProvider.getLatitude())
                .addParam("searchName", mKeyWord)
                .url(NetUrlUtils.SEARCH_CARD_SHOP_LIST)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<SearchCardListBean> mList = JSONUtils.jsonString2Beans(json, SearchCardListBean.class);
                if (mList == null || mList.size() <= 0) {
                    llytNoData.setVisibility(View.VISIBLE);
                    rlvSearch.setVisibility(View.GONE);
                } else {
                    mAdapter.setList(mList);
                    mAdapter.notifyDataSetChanged();
                    llytNoData.setVisibility(View.GONE);
                    rlvSearch.setVisibility(View.VISIBLE);
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
