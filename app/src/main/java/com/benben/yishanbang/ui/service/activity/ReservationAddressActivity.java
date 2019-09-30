package com.benben.yishanbang.ui.service.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.benben.commoncore.utils.LogUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.ui.service.adapter.ReservationAddressListAdapter;
import com.benben.yishanbang.utils.MapHelper;
import com.benben.yishanbang.utils.ShowListUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//选择预约地址
public class ReservationAddressActivity extends BaseActivity {
    private static final String TAG = "ReservationAddressActivity";
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.tv_near)
    TextView tvNear;
    @BindView(R.id.ll_near)
    LinearLayout llNear;
    @BindView(R.id.tv_intelligent_sort)
    TextView tvIntelligentSort;
    @BindView(R.id.ll_intelligent_sort)
    LinearLayout llIntelligentSort;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    private ReservationAddressListAdapter mAdapter;
    //poi检索
    private PoiSearch mPoiSearch;
    //我的经纬度
    private LatLng mLatLng;
    //排序类型数据集合
    private List<String> mTypeList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reservation_address;
    }

    @Override
    protected void initData() {
        centerTitle.setText("地图选点");
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new ReservationAddressListAdapter(mContext);
        rlvList.setAdapter(mAdapter);

        showMap();
        getDataList();

        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<PoiInfo>() {
            @Override
            public void onItemClick(View view, int position, PoiInfo model) {
                Intent intent = new Intent();
                intent.putExtra("address", model.address);
                intent.putExtra("name", model.name);
                setResult(201, intent);
                finish();
            }

            @Override
            public void onItemLongClick(View view, int position, PoiInfo model) {

            }
        });
    }

    //获取地址列表
    private void getDataList() {
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                //获取POI检索结果
                List<PoiInfo> allAddr = poiResult.getAllPoi();
                if (allAddr != null && allAddr.size() > 0) {
                    for (PoiInfo p : allAddr) {

                        Log.e("MainActivity", "p.name--->" + p.name + "p.phoneNum" + p.phoneNum + " -->p.address:" + p.address + "p.location：" + p.location + "   距离 = " + p.distance);
                        double distance = MapHelper.distance(mLatLng.longitude, mLatLng.latitude, p.location.longitude, p.location.latitude);
                        // p.setDistance(distance);
                        LogUtils.e(TAG, "distance ====" + String.format("%.2f", distance));
                        p.setUid(String.format("%.2f", distance));
                    }
                }
                mAdapter.refreshList(allAddr);
              /*  if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    mapView.clear();

                    //创建PoiOverlay对象
                    PoiOverlay poiOverlay = new PoiOverlay(mBaiduMap);
                    //设置Poi检索数据
                    poiOverlay.setData(poiResult);

                    //将poiOverlay添加至地图并缩放至合适级别
                    poiOverlay.addToMap();
                    poiOverlay.zoomToSpan();
                }*/
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                //获取Place详情页检索结果
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });

        mPoiSearch.searchNearby((new PoiNearbySearchOption())
                .location(mLatLng)
                .radius(5000)
                .keyword("商场")
                .pageCapacity(10)
                .pageNum(1));


    }

    //展示地图
    private void showMap() {
        //设置初始化比例  500米左右   zoom越大则地图越详细
        //参数1 纬度  参数 2  经度

        try {
//            mLatLng = new LatLng(34.75661006,113.64964385);
            mLatLng = new LatLng(Double.parseDouble(MyApplication.mPreferenceProvider.getLatitude()), Double.parseDouble(MyApplication.mPreferenceProvider.getLongitude()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        MapStatus mMapStatus = new MapStatus.Builder()
                .target(mLatLng)
                .zoom(16)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);        //改变地图状态
        BaiduMap baiduMap = mapView.getMap();
        //添加定位图标
        BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_transition_page_mark));
        MarkerOptions markerOption = new MarkerOptions()
                .position(mLatLng)
                .draggable(false)
                .icon(markerIcon)
                .alpha(0.8f);
        baiduMap.addOverlay(markerOption);
        baiduMap.setMapStatus(mMapStatusUpdate);
    }

    @OnClick({R.id.rl_back, R.id.ll_near, R.id.ll_intelligent_sort})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.ll_near://附近
                tvNear.setTextColor(mContext.getResources().getColor(R.color.theme));
                tvIntelligentSort.setTextColor(mContext.getResources().getColor(R.color.color_666666));
                break;
            case R.id.ll_intelligent_sort://智能排序
                mTypeList.clear();
                mTypeList.add("智能排序");
                mTypeList.add("人均最高");
                mTypeList.add("离我最近");
                mTypeList.add("星级最高");
                ShowListUtils.show(mContext, "选择排序类型", mTypeList, new ShowListUtils.OnSelectItem() {
                    @Override
                    public void onCallback(String item, int position) {
                        tvIntelligentSort.setTextColor(mContext.getResources().getColor(R.color.theme));
                        tvNear.setTextColor(mContext.getResources().getColor(R.color.color_666666));
                        tvIntelligentSort.setText(item);
                    }
                });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPoiSearch.destroy();
    }
}
