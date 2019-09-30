package com.benben.yishanbang.ui.tea.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.benben.commoncore.utils.AppUtils;
import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.tea.bean.StoresDetailsBean;
import com.benben.yishanbang.utils.MapUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2019/8/12 0012
 * Describe:商店详情页面
 */
public class StoresDetailsActivity extends BaseActivity {


    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.tea_details_shopName)
    TextView teaDetailsShopName;
    @BindView(R.id.tea_details_time_start)
    TextView teaDetailsTimeStart;
    @BindView(R.id.tea_details_time_end)
    TextView teaDetailsTimeEnd;
    @BindView(R.id.tea_details_weekend_start)
    TextView teaDetailsWeekendStart;
    @BindView(R.id.tea_details_weekend_end)
    TextView teaDetailsWeekendEnd;
    @BindView(R.id.tea_details_address)
    TextView teaDetailsAddress;
    @BindView(R.id.iv_tea_details)
    ImageView ivTeaDetails;
    @BindView(R.id.iv_tea_shop_one)
    ImageView ivTeaShopOne;
    @BindView(R.id.iv_tea_shop_two)
    ImageView ivTeaShopTwo;
    @BindView(R.id.iv_tea_shop_three)
    ImageView ivTeaShopThree;
    @BindView(R.id.tea_details_goto_drink)
    Button teaDetailsGotoDrink;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.mv_mapView)
    MapView mapView;
    //店铺id
    private String mShopId;


    private static final int OPEN_ID = 0;
    private static final int CLOSE_ID = 1;

    FrameLayout layout;
    //经纬度
    private double mShopLongitude;
    private double mShopLatitude;
    private String mShopName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_stores_details;
    }

    @Override
    protected void initData() {
        //设置标题
        centerTitle.setText("门店详情");
        //设置右边客服logo
       /* Drawable drawable = getResources().getDrawable(R.mipmap.custmoer_service);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        rightTitle.setCompoundDrawables(null, null, drawable, null);
*/
        Intent intent = getIntent();
        mShopId = intent.getStringExtra("shopid");//商店id

        getDataList();
    }

    @OnClick({R.id.right_title, R.id.tea_details_goto_drink, R.id.rl_back, R.id.tea_details_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.right_title://点击客服
                break;
            case R.id.tea_details_goto_drink://点击去喝一杯
                Intent intent = new Intent(StoresDetailsActivity.this, ChooseGoodsActivity.class);
                intent.putExtra("shopid", mShopId);
                startActivity(intent);
                break;
            case R.id.tea_details_address: //导航
                navigation();
                break;
            case R.id.rl_back: //返回上一页
                onBackPressed();
                break;
        }
    }

    //获取数据列表
    private void getDataList() {

        BaseOkHttpClient.newBuilder()
                .addParam("shopId", mShopId)//id
                .url(NetUrlUtils.STORES_DETAILS)
                .get()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                StoresDetailsBean storesDetailsBean = JSONUtils.jsonString2Bean(json, StoresDetailsBean.class);
                if (storesDetailsBean != null) {
                    //设置商店名字
                    if (storesDetailsBean.getShopName() != null) {
                        teaDetailsShopName.setText(storesDetailsBean.getShopName());
                    }
                    //设置商店营业时间
                    teaDetailsTimeStart.setText(storesDetailsBean.getShopStarttime());
                    teaDetailsTimeEnd.setText(storesDetailsBean.getShopEndtime());
                    //设置周六周日营业时间
                    teaDetailsWeekendStart.setText(storesDetailsBean.getWeekStarttime());
                    teaDetailsWeekendEnd.setText(storesDetailsBean.getWeekEndtime());
                    teaDetailsAddress.setText(storesDetailsBean.getShopPlace());
                    //设置商店图片
                    ImageUtils.getPic(Constants.IMAGE_BASE_URL + storesDetailsBean.getShoptImg(), ivTeaDetails, mContext, R.mipmap.ic_default_pic);
                    //设置门店图片
                    if (storesDetailsBean.getShopImgs() != null && storesDetailsBean.getShopImgs().size() > 0) {
                        switch (storesDetailsBean.getShopImgs().size()) {
                            case 1:
                                ImageUtils.getPic(Constants.IMAGE_BASE_URL + storesDetailsBean.getShopImgs().get(0), ivTeaShopOne, mContext, R.mipmap.ic_default_pic);
                                break;
                            case 2:
                                ImageUtils.getPic(Constants.IMAGE_BASE_URL + storesDetailsBean.getShopImgs().get(0), ivTeaShopOne, mContext, R.mipmap.ic_default_pic);
                                ImageUtils.getPic(Constants.IMAGE_BASE_URL + storesDetailsBean.getShopImgs().get(1), ivTeaShopTwo, mContext, R.mipmap.ic_default_pic);
                                break;
                            case 3:
                                ImageUtils.getPic(Constants.IMAGE_BASE_URL + storesDetailsBean.getShopImgs().get(0), ivTeaShopOne, mContext, R.mipmap.ic_default_pic);
                                ImageUtils.getPic(Constants.IMAGE_BASE_URL + storesDetailsBean.getShopImgs().get(1), ivTeaShopTwo, mContext, R.mipmap.ic_default_pic);
                                ImageUtils.getPic(Constants.IMAGE_BASE_URL + storesDetailsBean.getShopImgs().get(2), ivTeaShopThree, mContext, R.mipmap.ic_default_pic);
                                break;
                        }
                    }
                    mShopLongitude = storesDetailsBean.getLongitude();
                    mShopLatitude = storesDetailsBean.getLatitude();
                    mShopName = storesDetailsBean.getShopName();
                    onShowMap();
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

    //展示地图
    private void onShowMap() {
        //设置初始化比例  500米左右   zoom越大则地图越详细
        //参数1 纬度  参数 2  经度
        LatLng latLng = new LatLng(mShopLatitude, mShopLongitude);
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(latLng)
                .zoom(16)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);        //改变地图状态
        BaiduMap baiduMap = mapView.getMap();
        BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_shop_address));
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
//                View view = View.inflate(context, R.layout.activity_map_info, null);
//                TextView xiugai = view.findViewById(R.id.map_tv_xiugai);
//                TextView yidong = view.findViewById(R.id.map_tv_yidong);
//                TextView shanchu = view.findViewById(R.id.map_tv_shanchu);
//                TextView quxiao = view.findViewById(R.id.map_tv_quxiao);
//                TextView kance = view.findViewById(R.id.map_tv_kance);
//                final InfoWindow mInfoWindow = new InfoWindow(view, marker.getPosition(), -47);
                return false;
            }
        });

        MarkerOptions markerOption = new MarkerOptions()
                .position(latLng)
                .draggable(false)
                .icon(markerIcon)
                .alpha(0.8f);
        baiduMap.addOverlay(markerOption);
        baiduMap.setMapStatus(mMapStatusUpdate);
    }

    //去导航
    private void navigation() {
        if (mShopLatitude == 0 || mShopLongitude == 0) return;

        //百度地图
        if (AppUtils.checkApkExist(mContext, "com.baidu.BaiduMap")) {
            MapUtils.openBaiDuNavi(mContext, Double.parseDouble(MyApplication.mPreferenceProvider.getLatitude()), Double.parseDouble(MyApplication.mPreferenceProvider.getLongitude()), MyApplication.mPreferenceProvider.getCity(), mShopLatitude, mShopLongitude, mShopName);
            return;
        }
        //高德地图
        if (AppUtils.checkApkExist(mContext, "com.autonavi.minimap")) {
            MapUtils.openGaoDeNavi(mContext, Double.parseDouble(MyApplication.mPreferenceProvider.getLatitude()), Double.parseDouble(MyApplication.mPreferenceProvider.getLongitude()), MyApplication.mPreferenceProvider.getCity(), mShopLatitude, mShopLongitude, mShopName);
            return;
        }
        //腾讯地图
        if (AppUtils.checkApkExist(mContext, "com.tencent.map")) {
            MapUtils.openTencentMap(mContext, Double.parseDouble(MyApplication.mPreferenceProvider.getLatitude()), Double.parseDouble(MyApplication.mPreferenceProvider.getLongitude()), MyApplication.mPreferenceProvider.getCity(), mShopLatitude, mShopLongitude, mShopName);
            return;
        }
        toast("您手机上没有检测到百度地图或高德地图，腾讯地图等应用");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        //toast("onDestroy");
        super.onDestroy();
        mapView.onDestroy();
    }
}
