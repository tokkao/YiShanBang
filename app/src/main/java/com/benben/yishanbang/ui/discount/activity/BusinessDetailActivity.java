package com.benben.yishanbang.ui.discount.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.AppUtils;
import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.LogUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.discount.adapter.BusinessGoodsListAdapter;
import com.benben.yishanbang.ui.discount.adapter.BusinessTypeAdapter;
import com.benben.yishanbang.ui.discount.bean.ShopCateInfoBean;
import com.benben.yishanbang.ui.discount.bean.ShopGoodsListBean;
import com.benben.yishanbang.ui.discount.bean.ShopTopInfoBean;
import com.benben.yishanbang.utils.MapUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/6
 * Time: 11:15
 * 店铺详情页
 */
public class BusinessDetailActivity extends BaseActivity {
    private static final String TAG = "BusinessDetailActivity";
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time_phone)
    TextView tvTimePhone;
    @BindView(R.id.iv_navigation)
    ImageView ivNavigation;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.rv_type)
    RecyclerView rvType;
    @BindView(R.id.rv_goods_list)
    RecyclerView rvGoodsList;
    @BindView(R.id.iv_collection)
    ImageView ivCollection;
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private BusinessTypeAdapter mTypeAdapter;//商品类型适配器

    private BusinessGoodsListAdapter mGoodsListAdapter;//商品列表适配器
    //店铺id
    private String mStoreId;
    //距离
    private String mDistance;
    //商品分类id
    private String mCateId;

    private int mPage = 1;
    private int mPageSize = 15;
    private boolean isRefresh = true;
    //店铺经纬度
    private String mShopLatitude;
    private String mShopLongitude;
    //店铺名称
    private String mShopName;
    //是否收藏
    private boolean isCollection;
    private String mCollectionId;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_business_detail;
    }

    @Override
    protected void initData() {
        mStoreId = getIntent().getStringExtra("store_id");
        mDistance = getIntent().getStringExtra("store_distance");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        rvType.setLayoutManager(linearLayoutManager);

        rvGoodsList.setLayoutManager(new GridLayoutManager(mContext, 2));

        mTypeAdapter = new BusinessTypeAdapter(mContext);
        rvType.setAdapter(mTypeAdapter);

        mGoodsListAdapter = new BusinessGoodsListAdapter(mContext);
        rvGoodsList.setAdapter(mGoodsListAdapter);

        mTypeAdapter.setOnItemClickListener((view, position, model) -> {
            //切换分类
            mCateId = model.getCategoryId();
            getGoodsList();
        });

        initRefreshLayout();
        getStoreInfo();
        getGoodsCateList();
    }


    private void initRefreshLayout() {
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            isRefresh = true;
            mPage = 1;
            getGoodsList();
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            isRefresh = false;
            mPage++;
            getGoodsList();
        });
    }

    //店铺商品分类
    private void getGoodsCateList() {
        BaseOkHttpClient.newBuilder()
                .addParam("shopId", mStoreId)
                .url(NetUrlUtils.GET_STORE_CATE_INFO)
                .get()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<ShopCateInfoBean> shopCateInfoBeans = JSONUtils.jsonString2Beans(json, ShopCateInfoBean.class);
                if (shopCateInfoBeans != null && shopCateInfoBeans.size() > 0) {
                    //默认选中第一个分类
                    shopCateInfoBeans.get(0).setSelect(true);
                    mCateId = shopCateInfoBeans.get(0).getCategoryId();
                    mTypeAdapter.setList(shopCateInfoBeans);
                    mTypeAdapter.notifyDataSetChanged();
                    getGoodsList();
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

    //获取商品列表
    private void getGoodsList() {
        BaseOkHttpClient.newBuilder()
                .addParam("pageNo", mPage)
                .addParam("pageSize", mPageSize)
                .addParam("goodsCategoryId", mCateId)
                .addParam("shopId", mStoreId)
                .url(NetUrlUtils.GET_STORE_GOODS_LIST)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<ShopGoodsListBean> shopCateInfoBeans = JSONUtils.jsonString2Beans(json, ShopGoodsListBean.class);
                if (isRefresh) {
                    mGoodsListAdapter.refreshList(shopCateInfoBeans);
                    refreshLayout.finishRefresh();
                } else {
                    mGoodsListAdapter.appendToList(shopCateInfoBeans);
                    refreshLayout.finishLoadMore();
                }

                if (mGoodsListAdapter.getItemCount() > 0) {
                    llytNoData.setVisibility(View.GONE);
                    rvGoodsList.setVisibility(View.VISIBLE);
                } else {
                    llytNoData.setVisibility(View.VISIBLE);
                    rvGoodsList.setVisibility(View.GONE);
                }



            }

            @Override
            public void onError(int code, String msg) {
                ToastUtils.show(mContext, msg);
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
            }

            @Override
            public void onFailure(Call call, IOException e) {
                refreshLayout.finishLoadMore(false);
                refreshLayout.finishRefresh(false);
            }
        });
    }

    //店铺信息
    private void getStoreInfo() {

        BaseOkHttpClient.newBuilder()
                .addParam("id", mStoreId)//店铺id
                //   .addParam("shopDistance", mDistance)//
                //经纬度
                .addParam("longitude", MyApplication.mPreferenceProvider.getLongitude())
                .addParam("latitude", MyApplication.mPreferenceProvider.getLatitude())
                .url(NetUrlUtils.GET_STORE_INFO)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                ShopTopInfoBean shopTopInfoBean = JSONUtils.jsonString2Bean(json, ShopTopInfoBean.class);
                mShopLatitude = shopTopInfoBean.getLatitude();
                mShopName = shopTopInfoBean.getShopName();
                mShopLongitude = shopTopInfoBean.getLongitude();
                tvTitle.setText(shopTopInfoBean.getShopName());
                tvDistance.setText(StringUtils.isEmpty(shopTopInfoBean.getDistance()) ? "" : shopTopInfoBean.getDistance() + "km");
                tvTimePhone.setText("营业时间：" + shopTopInfoBean.getShopStarttime() + "-" + shopTopInfoBean.getShopEndtime() + "\t\t\t\t电话：" + shopTopInfoBean.getShopPhone());
                tvAddress.setText(shopTopInfoBean.getShopPlace());
                isCollection = "1".equals(shopTopInfoBean.getIsCollection());
                mCollectionId = shopTopInfoBean.getCollectionId();
                ivCollection.setImageResource(isCollection ? R.mipmap.ic_business_collectioned : R.mipmap.ic_business_collection);
                ImageUtils.getPic(Constants.IMAGE_BASE_URL + shopTopInfoBean.getShoptImg(), ivImg, mContext, R.mipmap.ic_default_pic);
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

    @OnClick({R.id.iv_back, R.id.iv_navigation, R.id.tv_address, R.id.iv_collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_navigation://导航
            case R.id.tv_address:
                navigation();
                break;
            //收藏
            case R.id.iv_collection:
                LogUtils.e(TAG, "onViewClicked: isCollection = " + isCollection);
                if (isCollection) {
                    cancelCollection();
                } else {
                    collectionStore();
                }
                break;
        }
    }


    //去导航
    private void navigation() {
        if(StringUtils.isEmpty(mShopLatitude) ||StringUtils.isEmpty(mShopLongitude))return;
        //百度地图
        if (AppUtils.checkApkExist(mContext, "com.baidu.BaiduMap")) {
            MapUtils.openBaiDuNavi(mContext, Double.parseDouble(MyApplication.mPreferenceProvider.getLatitude()), Double.parseDouble(MyApplication.mPreferenceProvider.getLongitude()), MyApplication.mPreferenceProvider.getCity(), Double.parseDouble(mShopLatitude), Double.parseDouble(mShopLongitude), mShopName);
            return;
        }
        //高德地图
        if (AppUtils.checkApkExist(mContext, "com.autonavi.minimap")) {
            MapUtils.openGaoDeNavi(mContext, Double.parseDouble(MyApplication.mPreferenceProvider.getLatitude()), Double.parseDouble(MyApplication.mPreferenceProvider.getLongitude()), MyApplication.mPreferenceProvider.getCity(), Double.parseDouble(mShopLatitude), Double.parseDouble(mShopLongitude), mShopName);
            return;
        }
        //腾讯地图
        if (AppUtils.checkApkExist(mContext, "com.tencent.map")) {
            MapUtils.openTencentMap(mContext, Double.parseDouble(MyApplication.mPreferenceProvider.getLatitude()), Double.parseDouble(MyApplication.mPreferenceProvider.getLongitude()), MyApplication.mPreferenceProvider.getCity(), Double.parseDouble(mShopLatitude), Double.parseDouble(mShopLongitude), mShopName);
            return;
        }
        toast("您手机上没有检测到百度地图或高德地图，腾讯地图等");
    }

    //收藏店铺
    private void collectionStore() {

        BaseOkHttpClient.newBuilder()
                .addParam("id", mStoreId)
                .addParam("type", 1)// 收藏类型 1 收藏店铺  0  收藏商品
                .url(NetUrlUtils.COLLECTION_SHOP_GOODS)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                toast("收藏成功");
                getStoreInfo();
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

    //取消收藏
    private void cancelCollection() {
        BaseOkHttpClient.newBuilder()
                .addParam("id", mCollectionId)
                .addParam("type", 1)
                .url(NetUrlUtils.DELETE_COLLECTION_GOODS)
                .get()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtils.show(mContext, "取消收藏");
                getStoreInfo();
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

    @Override
    protected int getStatusBarColor() {
        return R.color.transparent;
    }

}
