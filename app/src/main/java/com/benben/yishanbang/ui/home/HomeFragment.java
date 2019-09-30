package com.benben.yishanbang.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.baidu.location.Address;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.benben.commoncore.utils.DensityUtil;
import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.LogUtils;
import com.benben.commoncore.utils.RxBus;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.LazyBaseFragments;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.discount.activity.SearchActivity;
import com.benben.yishanbang.ui.discount.activity.SelectAreaActivity;
import com.benben.yishanbang.ui.home.activity.MessageListActivity;
import com.benben.yishanbang.ui.home.activity.QRCodeActivity;
import com.benben.yishanbang.ui.home.adapter.HomeBusinessAdapter;
import com.benben.yishanbang.ui.home.adapter.HomeGoodsAdapter;
import com.benben.yishanbang.ui.home.adapter.HomeVideoAdapter;
import com.benben.yishanbang.ui.home.bean.HomeBannerListBean;
import com.benben.yishanbang.ui.home.bean.HomeRecommendGoodsBean;
import com.benben.yishanbang.widget.CustomRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/5
 * Time: 9:13
 * 首页
 */
public class HomeFragment extends LazyBaseFragments {
    private static final String TAG = "HomeFragment";
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.iv_qr_code)
    ImageView ivQrCode;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_top_bg)
    TextView tvTopBg;
    @BindView(R.id.tv_ktv)
    TextView tvKtv;
    @BindView(R.id.tv_food)
    TextView tvFood;
    @BindView(R.id.tv_supermarket)
    TextView tvSupermarket;
    @BindView(R.id.tv_entertainment)
    TextView tvEntertainment;
    @BindView(R.id.tv_cinema)
    TextView tvCinema;
    @BindView(R.id.tv_more_business)
    TextView tvMoreBusiness;
    @BindView(R.id.rv_business)
    CustomRecyclerView rvBusiness;
    @BindView(R.id.rv_goods)
    CustomRecyclerView rvGoods;
    @BindView(R.id.rv_video)
    CustomRecyclerView rvVideo;
    @BindView(R.id.banner_home)
    Banner banner;
    @BindView(R.id.llyt_dot)
    LinearLayout llytDot;
    //选中时的指示器
    View mSelectDotView;
    //未选中时的指示器
    View mNoSelectDotView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    /*当前显示的是第几页*/
    private int curIndex = 0;


    private HomeBusinessAdapter mBusinessAdapter;//推荐商家适配器

    private HomeGoodsAdapter mGoodsAdapter;//推荐商品适配器

    private HomeVideoAdapter mVideoAdapter;//视频广告适配器

    private int mPageNo = 1;
    private boolean isRefresh = true;
    private int mPageSize = 8;
    //定位
    private LocationClient mLocationClient;
    private List<HomeBannerListBean> mBannerList;

    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.frag_home, null);
        return mRootView;
    }

    @Override
    public void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvBusiness.setLayoutManager(gridLayoutManager);
        rvBusiness.setFocusable(false);

        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(mContext, 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvGoods.setLayoutManager(gridLayoutManager1);
        rvGoods.setFocusable(false);

        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(mContext, 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvVideo.setLayoutManager(gridLayoutManager2);
        rvVideo.setFocusable(false);

        mBusinessAdapter = new HomeBusinessAdapter(mContext);
        rvBusiness.setAdapter(mBusinessAdapter);

        mGoodsAdapter = new HomeGoodsAdapter(mContext);
        rvGoods.setAdapter(mGoodsAdapter);

        mVideoAdapter = new HomeVideoAdapter(mContext);
        rvVideo.setAdapter(mVideoAdapter);

        tvCity.setText(StringUtils.isEmpty(MyApplication.mPreferenceProvider.getCity()) ? "定位中" : MyApplication.mPreferenceProvider.getCity());
        initLocation();
        //初始化轮播图
        initBanner();
        initRefreshLayout();

        RxBus.getInstance().toObservable(Integer.class)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        //授权后 再次请求定位
                        if (integer == 123) {
                            if (mLocationClient != null) {
                                mLocationClient.requestLocation();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //初始化定位
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        //可选，设置返回经纬度坐标类型，默认GCJ02
        //GCJ02：国测局坐标；
        //BD09ll：百度经纬度坐标；
        //BD09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标

        option.setScanSpan(60 * 1000);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(false);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明

        mLocationClient = new LocationClient(mContext);
        mLocationClient.setLocOption(option);
        //声明LocationClient类
        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                double latitude = bdLocation.getLatitude();    //获取纬度信息
                double longitude = bdLocation.getLongitude();    //获取经度信息
                float radius = bdLocation.getRadius();    //获取定位精度，默认值为0.0f
                Address address = bdLocation.getAddress();

                MyApplication.mPreferenceProvider.setLatitude("" + latitude);
                MyApplication.mPreferenceProvider.setLongitude("" + longitude);
                //保存定位信息到本地
                if (!StringUtils.isEmpty(address.province)) {
                    MyApplication.mPreferenceProvider.setProvince(address.province);
                }
                if (!StringUtils.isEmpty(address.city)) {
                    MyApplication.mPreferenceProvider.setCity(address.city);
                    tvCity.setText(address.city);
                    mLocationClient.stop();
                    setLatestCity(String.valueOf(longitude), String.valueOf(latitude), address.city);
                }
                if (!StringUtils.isEmpty(address.district)) {
                    MyApplication.mPreferenceProvider.setDistrict(address.district);
                }


                String coorType = bdLocation.getCoorType();
                //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

                int errorCode = bdLocation.getLocType();
                LogUtils.e(TAG, "定位信息：errorCode = " + errorCode + "****" + address.country + "****" + address.province + "****" + address.city + "****" + address.district + "****" + address.street);
            }
        });
        mLocationClient.start();
        //注册监听函数
    }

    //向服务器上传最新定位城市
    private void setLatestCity(String longitude, String latitude, String city) {

        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.SET_LATEST_CITY)
                .addParam("city", city)
                .addParam("longitude", longitude)
                .addParam("latitude", latitude)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
            }

            @Override
            public void onError(int code, String msg) {
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }


    private void initRefreshLayout() {
        //设置 Header 背景为透明
        refreshLayout.setPrimaryColorsId(R.color.transparent, R.color.text_white);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        //设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext));
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            isRefresh = true;
            mPageNo = 1;
            //刷新轮播图和商品，店铺数据
            getBannerList();
            getDataList();
            if (mLocationClient != null) {
                mLocationClient.requestLocation();

            }
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            isRefresh = false;
            mPageNo++;
            getDataList();
        });
    }

    @Override
    public void initData() {
        getDataList();
    }

    //获取推荐商品/商家/视频列表
    private void getDataList() {
        BaseOkHttpClient.newBuilder()
                .addParam("pageNo", mPageNo)
                .addParam("pageSize", mPageSize)
                .url(NetUrlUtils.GET_HOME_RECOMMEND_GOODS)
                .get()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                HomeRecommendGoodsBean goodsBean = JSONUtils.jsonString2Bean(json, HomeRecommendGoodsBean.class);
                if (isRefresh) {
                    mBusinessAdapter.setList(goodsBean.getCardShopVo());
                    mGoodsAdapter.setList(goodsBean.getCardVo());
                    mVideoAdapter.setList(goodsBean.getAdvertVo());
                } else {
                    if (mBusinessAdapter.getList() != null) {
                        mBusinessAdapter.getList().addAll(goodsBean.getCardShopVo());
                    } else {
                        mBusinessAdapter.setList(goodsBean.getCardShopVo());
                    }
                    if (mGoodsAdapter.getList() != null) {
                        mGoodsAdapter.getList().addAll(goodsBean.getCardVo());
                    } else {
                        mGoodsAdapter.setList(goodsBean.getCardVo());
                    }
                    if (mVideoAdapter.getList() != null) {
                        mVideoAdapter.getList().addAll(goodsBean.getAdvertVo());
                    } else {
                        mVideoAdapter.setList(goodsBean.getAdvertVo());
                    }
                }
                mBusinessAdapter.notifyDataSetChanged();
                mGoodsAdapter.notifyDataSetChanged();
                mVideoAdapter.notifyDataSetChanged();
                refreshLayout.finishLoadMore(true);
                refreshLayout.finishRefresh(true);
            }

            @Override
            public void onError(int code, String msg) {
                ToastUtils.show(mContext, msg);
                refreshLayout.finishLoadMore(true);
                refreshLayout.finishRefresh(true);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                refreshLayout.finishLoadMore(false);
                refreshLayout.finishRefresh(false);
            }
        });


    }

    //初始化轮播图
    private void initBanner() {
        getBannerList();
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object obj, ImageView imageView) {
                HomeBannerListBean mHomeBannerListBean = (HomeBannerListBean) obj;
                ImageUtils.getCircularPic(Constants.IMAGE_BASE_URL + mHomeBannerListBean.getImgUrl(), imageView, mContext, 10, R.mipmap.ic_default_pic);
            }
        });
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        banner.setDelayTime(3000);
        //banner设置方法全部调用完毕时最后调用
        banner.setOnBannerListener(position -> {
//            mBannerList.get(position);
        });
    }

    //获取轮播图
    private void getBannerList() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.GET_BANNER_LIST)
                .get()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                mBannerList = JSONUtils.jsonString2Beans(json, HomeBannerListBean.class);
                if (mBannerList != null && mBannerList.size() > 0) {
                    banner.setImages(mBannerList);
                    banner.start();
                    setOvalLayout();
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

    /**
     * 设置圆点
     */
    private void setOvalLayout() {
        llytDot.removeAllViews();
        for (int i = 0; i < mBannerList.size(); i++) {
            llytDot.addView(LayoutInflater.from(mContext).inflate(R.layout.layout_home_banner_dot, null));
        }
        // 默认显示第一页
        mSelectDotView = llytDot.getChildAt(0).findViewById(R.id.v_dot);
        mSelectDotView.setBackgroundResource(R.drawable.dot_selected);
        ViewGroup.LayoutParams layoutParams = mSelectDotView.getLayoutParams();
        layoutParams.width = DensityUtil.dip2px(mContext, 14);
        layoutParams.height = DensityUtil.dip2px(mContext, 6);
        mSelectDotView.setLayoutParams(layoutParams);
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
            /*    for (int i = 0; i < arr.length; i++) {
                    arr[i].notifyDataSetChanged();
                }
*/
                // 取消圆点选中
                mNoSelectDotView = llytDot.getChildAt(curIndex)
                        .findViewById(R.id.v_dot);
                ViewGroup.LayoutParams layoutParams = mNoSelectDotView.getLayoutParams();
                layoutParams.width = DensityUtil.dip2px(mContext, 6);
                layoutParams.height = DensityUtil.dip2px(mContext, 6);
                mNoSelectDotView.setLayoutParams(layoutParams);
                mNoSelectDotView.setBackgroundResource(R.drawable.dot_normal);
                // 圆点选中
                mSelectDotView = llytDot.getChildAt(position)
                        .findViewById(R.id.v_dot);
                layoutParams = mSelectDotView.getLayoutParams();
                layoutParams.width = DensityUtil.dip2px(mContext, 14);
                layoutParams.height = DensityUtil.dip2px(mContext, 6);
                mSelectDotView.setLayoutParams(layoutParams);
                mSelectDotView.setBackgroundResource(R.drawable.dot_selected);
                curIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }


    @OnClick({R.id.tv_city, R.id.iv_message, R.id.iv_qr_code, R.id.tv_search, R.id.tv_ktv, R.id.tv_food, R.id.tv_supermarket, R.id.tv_entertainment, R.id.tv_cinema, R.id.tv_more_business})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //选择地址
            case R.id.tv_city:
                MyApplication.openActivity(mContext, SelectAreaActivity.class);
                break;
            //消息
            case R.id.iv_message:
                MyApplication.openActivity(mContext, MessageListActivity.class);
                break;
            //扫码
            case R.id.iv_qr_code:
                MyApplication.openActivity(mContext, QRCodeActivity.class);
                break;
            //搜索
            case R.id.tv_search:
                startActivity(new Intent(mContext, SearchActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 0));
                break;
            //ktv
            case R.id.tv_ktv:
                RxBus.getInstance().post(Constants.JUMP_TO_KTV);
                break;
            //美食
            case R.id.tv_food:
                RxBus.getInstance().post(Constants.JUMP_TO_FOOD);
                break;
            //超市
            case R.id.tv_supermarket:
                RxBus.getInstance().post(Constants.JUMP_TO_SUPERMARKET);
                break;
            //休闲娱乐
            case R.id.tv_entertainment:
                RxBus.getInstance().post(Constants.JUMP_TO_ENTERTAINMENT);
                break;
            //电影院
            case R.id.tv_cinema:
                RxBus.getInstance().post(Constants.JUMP_TO_CINEMA);
                break;
            //更多推荐商家
            case R.id.tv_more_business:
                RxBus.getInstance().post(Constants.JUMP_TO_DISCOUNT_FRAGMENT);
                break;
        }
    }

}
