package com.benben.yishanbang.ui.service.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.service.bean.IMNotifyUserListBean;
import com.benben.yishanbang.ui.service.bean.NotifyUserListBean;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

//过渡页
public class TransitionPageActivity extends BaseActivity {


    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_user_count)
    TextView tvUserCount;
    private int mPageNum = 1;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                startActivity(new Intent(mContext, ChooseServiceUserActivity.class)
                        .putExtra(Constants.EXTRA_KEY_ENTER_TYPE, mEnterType)
                        .putExtra("service_id", mServiceId)
                        .putExtra("order_num", mOrderNum)
                        .putExtra("cg_id", mCgId));
                mHandler =null;
                finish();
            }
        }
    };
    private int mEnterType;
    //服务类型id
    private String mCgId;
    //服务id
    private String mServiceId;
    //订单编号
    private String mOrderNum;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transition_page;
    }

    @Override
    protected void initData() {
        mCgId = getIntent().getStringExtra("cg_id");
        mServiceId = getIntent().getStringExtra("service_id");
        mOrderNum = getIntent().getStringExtra("order_num");
        mEnterType = getIntent().getIntExtra(Constants.EXTRA_KEY_ENTER_TYPE, 0);
        switch (mEnterType) {
            case 0://社交 /家政
                getNotifyUserNumber();
                break;
            case 1://聊天
                getIMNotifyUserNumber();
                break;
        }
        onShowMap();
    }

    //获取通知人数
    private void getIMNotifyUserNumber() {
        BaseOkHttpClient.newBuilder()
                .addParam("pageNo", mPageNum)
                .addParam("cgId", mCgId)
                .addParam("pageSize", 4)
                .url(NetUrlUtils.GET_IM_SERVICE_USER_LIST)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<IMNotifyUserListBean> imNotifyUserListBeans = JSONUtils.jsonString2Beans(json, IMNotifyUserListBean.class);
                if (imNotifyUserListBeans != null && imNotifyUserListBeans.size() > 0) {
                    tvUserCount.setText(imNotifyUserListBeans.get(imNotifyUserListBeans.size() - 1).getNumber() + "位用户");
                }
                //三秒后跳转到选择用户页面
                mHandler.sendEmptyMessageDelayed(0, 1000 * 3);
            }

            @Override
            public void onError(int code, String msg) {
                ToastUtils.show(mContext, msg);
                getIMNotifyUserNumber();
            }

            @Override
            public void onFailure(Call call, IOException e) {
                getIMNotifyUserNumber();
            }
        });
    }

    //获取通知人数
    private void getNotifyUserNumber() {
        BaseOkHttpClient.newBuilder()
                .addParam("pageNo", mPageNum)
                .addParam("cgId", mCgId)
                .addParam("pageSize", 4)
                .addParam("taskId", mServiceId)
                .addParam("orderNo", mOrderNum)
                .url(NetUrlUtils.GET_SERVICE_USER_LIST)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<NotifyUserListBean> imNotifyUserListBeans = JSONUtils.jsonString2Beans(json, NotifyUserListBean.class);
                if (imNotifyUserListBeans != null && imNotifyUserListBeans.size() > 0) {
                    tvUserCount.setText(imNotifyUserListBeans.get(imNotifyUserListBeans.size() - 1).getNumber() + "位用户");
                }
                //三秒后跳转到选择用户页面
                mHandler.sendEmptyMessageDelayed(0, 1000 * 3);
            }

            @Override
            public void onError(int code, String msg) {
                ToastUtils.show(mContext, msg);
                getNotifyUserNumber();
            }

            @Override
            public void onFailure(Call call, IOException e) {
                getNotifyUserNumber();
            }
        });
    }

    //展示地图
    private void onShowMap() {
        //设置初始化比例  500米左右   zoom越大则地图越详细
        //参数1 纬度  参数 2  经度
        LatLng latLng = null;
        try {
//            latLng = new LatLng(34.75661006,113.64964385);
            latLng = new LatLng(Double.parseDouble(MyApplication.mPreferenceProvider.getLatitude()), Double.parseDouble(MyApplication.mPreferenceProvider.getLongitude()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(latLng)
                .zoom(16)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);        //改变地图状态
        BaiduMap baiduMap = mapView.getMap();
        baiduMap.setMapStatus(mMapStatusUpdate);
    }

    @OnClick({R.id.iv_back, R.id.tv_user_count})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    protected int getStatusBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean needStatusBarDarkText() {
        return true;
    }
}
