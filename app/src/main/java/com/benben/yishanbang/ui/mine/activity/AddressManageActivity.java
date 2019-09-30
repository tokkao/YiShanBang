package com.benben.yishanbang.ui.mine.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.LogUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.mine.adapter.AddressListAdapter;
import com.benben.yishanbang.ui.mine.bean.AddressBean;
import com.kongzue.dialog.v3.MessageDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 收获地址管理
 */
public class AddressManageActivity extends BaseActivity {
    private static final String TAG = "AddressManagerActivity";

    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    //是否正在刷新
    private boolean mIsRefreshing = false;

    //地址列表
    private AddressListAdapter mAddressListAdapter;
    private int mEnterType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_lottery;
    }

    @Override
    protected void initData() {
        centerTitle.setText("收获地址");
        mEnterType = getIntent().getIntExtra(Constants.EXTRA_KEY_ENTER_TYPE, 0);
        rightTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.icon_add_goods), null);

        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mIsRefreshing = true;
                getAddressList();
            }
        });

        refreshLayout.setEnableLoadMore(false);

        //收获地址列表
        mAddressListAdapter = new AddressListAdapter(mContext);
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        rlvList.setAdapter(mAddressListAdapter);
        //列表点击事件
        mAddressListAdapter.setListener(new AddressListAdapter.AddressListListener() {
            @Override
            public void changeAddressDefault(AddressBean data) {
                //修改默认地址
                doChangeAddressDefault(data);
            }

            @Override
            public void deleteAddress(AddressBean data) {
                //删除地址
                MessageDialog.show((AppCompatActivity) mContext, "提示", "确定要删除该地址吗", "确定", "取消")
                        .setOnOkButtonClickListener((baseDialog, v) -> {
                            delAddress(data);
                            return false;
                        });

            }
        });
        //选择地址并返回数据
        mAddressListAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<AddressBean>() {
            @Override
            public void onItemClick(View view, int position, AddressBean model) {

                if (mEnterType == 1) {
                    Intent addressIntent = new Intent();
                    addressIntent.putExtra("address_name", model.getDetailedAddress());
                    addressIntent.putExtra("address_id", model.getId());
                    setResult(Constants.RESULT_CODE_OK, addressIntent);
                    finish();
                }

            }

            @Override
            public void onItemLongClick(View view, int position, AddressBean model) {

            }
        });
    }

    //删除地址
    private void delAddress(AddressBean data) {

        BaseOkHttpClient.newBuilder()
                .addParam("id", data.getId())
                .url(NetUrlUtils.ADDRESS_DELETE)
                .post().build()
                .enqueue(mContext, new BaseCallBack<String>() {
                    @Override
                    public void onSuccess(String result, String msg) {
                        getAddressList();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogUtils.e(TAG, msg);
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtils.e(TAG, e.getMessage());
                    }
                });

    }

    //修改默认地址
    private void doChangeAddressDefault(AddressBean data) {
        BaseOkHttpClient.newBuilder()
                .addParam("id", data.getId())
                .url(NetUrlUtils.ADDRESS_SETUP_DEFAULT)
                .post().build()
                .enqueue(mContext, new BaseCallBack<String>() {
                    @Override
                    public void onSuccess(String result, String msg) {
                        getAddressList();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogUtils.e(TAG, msg);
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtils.e(TAG, e.getLocalizedMessage());
                    }
                });

    }

    //获取收获地址列表

    private void getAddressList() {
        llytNoData.setVisibility(View.GONE);
        rlvList.setVisibility(View.VISIBLE);
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.ADDRESS_LIST)
                .json().post().build()
                .enqueue(mContext, new BaseCallBack<String>() {
                    @Override
                    public void onSuccess(String result, String msg) {
                        List<AddressBean> lstAddress = JSONUtils.jsonString2Beans(result, AddressBean.class);
                        if (lstAddress == null) {
                            lstAddress = new ArrayList<>();
                        }
                        mAddressListAdapter.refreshList(lstAddress);
                        if (mAddressListAdapter.getItemCount() == 0) {
                            llytNoData.setVisibility(View.VISIBLE);
                            rlvList.setVisibility(View.GONE);
                        } else {
                            llytNoData.setVisibility(View.GONE);
                            rlvList.setVisibility(View.VISIBLE);
                        }
                        if (mIsRefreshing) {
                            refreshLayout.finishRefresh();
                        }

                    }

                    @Override
                    public void onError(int code, String msg) {
                        LogUtils.e(TAG, msg);
                        if (mIsRefreshing) {
                            refreshLayout.finishRefresh(false);
                        }
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtils.e(TAG, e.getMessage());
                        if (mIsRefreshing) {
                            refreshLayout.finishRefresh(false);
                        }
                    }
                });

    }

    @OnClick({R.id.right_title, R.id.rl_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.right_title:
                startActivity(new Intent(mContext, AddAddressActivity.class));
                break;
            case R.id.rl_back:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddressList();
    }

}
