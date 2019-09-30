package com.benben.yishanbang.ui.service.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.service.adapter.IMServiceUserListAdapter;
import com.benben.yishanbang.ui.service.adapter.ServiceUserListAdapter;
import com.benben.yishanbang.ui.service.bean.IMNotifyUserListBean;
import com.benben.yishanbang.ui.service.bean.NotifyUserListBean;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

//选择赴约用户
public class ChooseServiceUserActivity extends BaseActivity {

    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    private ServiceUserListAdapter mAdapter;
    private IMServiceUserListAdapter mIMAdapter;
    private int mEnterType;
    private int mPageNum = 1;
    //服务id
    private String mServiceId;
    //订单编号
    private String mOrderNum;
    private String mCgId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_service_user;
    }

    @Override
    protected void initData() {
        centerTitle.setText("选择赴约用户");
        rightTitle.setText("重新匹配");
        mEnterType = getIntent().getIntExtra(Constants.EXTRA_KEY_ENTER_TYPE, 0);
        mServiceId = getIntent().getStringExtra("service_id");
        mOrderNum = getIntent().getStringExtra("order_num");
        mCgId = getIntent().getStringExtra("cg_id");
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new ServiceUserListAdapter(mContext);
        mIMAdapter = new IMServiceUserListAdapter(mContext);
        rlvList.setAdapter(mEnterType == 0 ? mAdapter : mIMAdapter);

        switch (mEnterType) {
            case 0://社交 /家政
                getNotifyUserNumber();
                break;
            case 1://聊天
                getIMNotifyUserNumber();
                break;
        }
        onShowMap();
        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<NotifyUserListBean>() {
            @Override
            public void onItemClick(View view, int position, NotifyUserListBean model) {
                Intent intent = new Intent(mContext, UserShortVideoActivity.class);
                intent.putExtra("user_name",model.getUser().getNickname());
                intent.putExtra("service_id",mServiceId);
                intent.putExtra("user_id",model.getUser().getId());
                intent.putExtra("order_num",mOrderNum);
                intent.putExtra("isIM",mEnterType == 1);

                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, NotifyUserListBean model) {

            }
        });
        mIMAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<IMNotifyUserListBean>() {
            @Override
            public void onItemClick(View view, int position, IMNotifyUserListBean model) {
                Intent intent = new Intent(mContext, UserShortVideoActivity.class);
                intent.putExtra("user_name",model.getUser().getNickname());
                intent.putExtra("service_id",mServiceId);
                intent.putExtra("user_id",model.getUser().getId());
                intent.putExtra("order_num",model.getUser().getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, IMNotifyUserListBean model) {

            }
        });
    }

    //获取聊天通知人数
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
                    //移除最后一个元素
                    imNotifyUserListBeans.remove(imNotifyUserListBeans.get(imNotifyUserListBeans.size() - 1));
                    mIMAdapter.refreshList(imNotifyUserListBeans);
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
                    //移除最后一个元素
                    imNotifyUserListBeans.remove(imNotifyUserListBeans.get(imNotifyUserListBeans.size() - 1));
                    mAdapter.refreshList(imNotifyUserListBeans);
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

        LatLng latLng = null;
        try {
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


    @OnClick({R.id.rl_back, R.id.right_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.right_title://重新匹配
                mPageNum ++;
                switch (mEnterType) {
                    case 0://社交 /家政
                        getNotifyUserNumber();
                        break;
                    case 1://聊天
                        getIMNotifyUserNumber();
                        break;
                }
                break;
        }
    }
}
