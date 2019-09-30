package com.benben.yishanbang.ui.discount;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.benben.yishanbang.ui.discount.adapter.DiscountAdapter;
import com.benben.yishanbang.ui.discount.bean.SearchCardListBean;
import com.benben.yishanbang.ui.discount.bean.SelectedAddressInfoBean;
import com.benben.yishanbang.utils.ShowListUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
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
 * Time: 9:17
 * 优惠卡
 */
public class DiscountFragment extends LazyBaseFragments {
    private static final String TAG = "DiscountFragment";
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.ll_area)
    LinearLayout llArea;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.ll_type)
    LinearLayout llType;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_sale)
    TextView tvSale;
    @BindView(R.id.rv_discount)
    RecyclerView rvDiscount;
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private DiscountAdapter mAdapter;//优惠券适配器

    private List<String> mShowList = new ArrayList<>();//商店类型数据集合

    private String mCateId = Constants.JUMP_TO_KTV;//首页跳转过来的分类id
    private int mSortType = 1;//排序类型  1距离 2销量
    private String mProvince;
    private String mCity;
    private String mDistrict;

    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.frag_discount, null);
        return mRootView;
    }

    @Override
    public void initView() {
        //获取到定位的地址
        mProvince = MyApplication.mPreferenceProvider.getProvince();
        mCity = MyApplication.mPreferenceProvider.getCity();
        mDistrict = MyApplication.mPreferenceProvider.getDistrict();
        tvArea.setText(StringUtils.isEmpty(MyApplication.mPreferenceProvider.getCity()) ? "未知" : MyApplication.mPreferenceProvider.getCity());
        initRefreshLayout();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        rvDiscount.setLayoutManager(linearLayoutManager);

        mAdapter = new DiscountAdapter(mContext);
        rvDiscount.setAdapter(mAdapter);

        RxBus.getInstance().toObservable(SelectedAddressInfoBean.class)
                .subscribe(new Observer<SelectedAddressInfoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SelectedAddressInfoBean selectedAddressInfoBean) {
                        LogUtils.e(TAG, "   next  ==== ======" + selectedAddressInfoBean);
                        mProvince = selectedAddressInfoBean.getProvince();
                        mCity = selectedAddressInfoBean.getCity();
                        mDistrict = selectedAddressInfoBean.getDistrict();
                        tvArea.setText(mCity);
                        mSortType = 2;
                        getDataList();

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        getDataList();
    }

    private void initRefreshLayout() {
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            getDataList();
        });
    }

    private void getDataList() {
        BaseOkHttpClient.Builder builder = BaseOkHttpClient.newBuilder();
        builder.addParam("sign", mSortType)//标记 1：默认距离排序 2 ：销量排序(需要判段是否是当前城市)
                .addParam("province", mProvince)//省
                .addParam("city", mCity)//市
                .addParam("county", mDistrict)//区
                .addParam("categoryId", mCateId)//分类
                //经纬度
                .addParam("latitude", MyApplication.mPreferenceProvider.getLatitude())
                .addParam("longitude", MyApplication.mPreferenceProvider.getLongitude())
                .url(NetUrlUtils.GET_HOME_CARD_LIST)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<SearchCardListBean> mList = JSONUtils.jsonString2Beans(json, SearchCardListBean.class);

                if (mList == null || mList.size() <= 0) {
                    llytNoData.setVisibility(View.VISIBLE);
                    rvDiscount.setVisibility(View.GONE);
                } else {
                    mAdapter.setList(mList);
                    mAdapter.notifyDataSetChanged();
                    llytNoData.setVisibility(View.GONE);
                    rvDiscount.setVisibility(View.VISIBLE);
                }

                /*//改变排序类型状态
                if (mSortType == 1) {
                    tvDistance.setTextColor(getResources().getColor(R.color.theme));
                    tvSale.setTextColor(getResources().getColor(R.color.color_666666));
                } else {
                    tvSale.setTextColor(getResources().getColor(R.color.theme));
                    tvDistance.setTextColor(getResources().getColor(R.color.color_666666));

                }*/
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onError(int code, String msg) {
                ToastUtils.show(mContext, msg);
                llytNoData.setVisibility(View.VISIBLE);
                rvDiscount.setVisibility(View.GONE);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();

                //改变排序类型状态
               /* if (mSortType == 1) {
                    tvDistance.setTextColor(getResources().getColor(R.color.theme));
                    tvSale.setTextColor(getResources().getColor(R.color.color_666666));
                } else {
                    tvSale.setTextColor(getResources().getColor(R.color.theme));
                    tvDistance.setTextColor(getResources().getColor(R.color.color_666666));

                }*/
            }

            @Override
            public void onFailure(Call call, IOException e) {
                llytNoData.setVisibility(View.VISIBLE);
                rvDiscount.setVisibility(View.GONE);
                refreshLayout.finishRefresh(false);
                refreshLayout.finishLoadMore(false);
            }
        });


    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            //首页第一次点击类型不显示对应类型的问题
            mCateId = getArguments().getString("cate_id");
            if (StringUtils.isEmpty(mCateId)) return;
            if (mCateId.equals(Constants.JUMP_TO_KTV)) {
                tvType.setText("KTV");
            } else if (mCateId.equals(Constants.JUMP_TO_FOOD)) {
                tvType.setText("美食");
            } else if (mCateId.equals(Constants.JUMP_TO_SUPERMARKET)) {
                tvType.setText("超市");
            } else if (mCateId.equals(Constants.JUMP_TO_ENTERTAINMENT)) {
                tvType.setText("休闲娱乐");
            } else if (mCateId.equals(Constants.JUMP_TO_CINEMA)) {
                tvType.setText("电影院");
            }
        }
        RxBus.getInstance().toObservable(String.class)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String cate) {
                        //防止充值页的参数传过来  执行以下代码
                        if ("recharge".equals(cate)) return;
                        if ("venture_shop".equals(cate)) return;
                        if (Constants.JUMP_TO_DISCOUNT_FRAGMENT.equals(cate)) return;
                        mCateId = cate;
                        if (cate.equals(Constants.JUMP_TO_KTV)) {
                            tvType.setText("KTV");
                        } else if (cate.equals(Constants.JUMP_TO_FOOD)) {
                            tvType.setText("美食");
                        } else if (cate.equals(Constants.JUMP_TO_SUPERMARKET)) {
                            tvType.setText("超市");
                        } else if (cate.equals(Constants.JUMP_TO_ENTERTAINMENT)) {
                            tvType.setText("休闲娱乐");
                        } else if (cate.equals(Constants.JUMP_TO_CINEMA)) {
                            tvType.setText("电影院");
                        }
                        getDataList();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick({R.id.ll_area, R.id.ll_type, R.id.tv_search, R.id.tv_sale, R.id.tv_distance})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //地区
            case R.id.ll_area:
                MyApplication.openActivityForResult(mContext, SelectAreaActivity.class, 101);
                break;
            //类型
            case R.id.ll_type:
                mShowList.clear();
                // mShowList.add("全部");
                mShowList.add("KTV");
                mShowList.add("美食");
                mShowList.add("超市");
                mShowList.add("休闲娱乐");
                mShowList.add("电影院");
                ShowListUtils.show(mContext, "选择店铺类型", mShowList, new ShowListUtils.OnSelectItem() {
                    @Override
                    public void onCallback(String item, int position) {
                        tvType.setText("" + item);
                        if (position == 0) {
                            mCateId = Constants.JUMP_TO_KTV;
                        } else if (position == 1) {
                            mCateId = Constants.JUMP_TO_FOOD;
                        } else if (position == 2) {
                            mCateId = Constants.JUMP_TO_SUPERMARKET;
                        } else if (position == 3) {
                            mCateId = Constants.JUMP_TO_ENTERTAINMENT;
                        } else {
                            mCateId = Constants.JUMP_TO_CINEMA;
                        }
                        tvType.setTextColor(getResources().getColor(R.color.theme));
                        Drawable drawable = getResources().getDrawable(R.mipmap.discount_down_theme);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        tvType.setCompoundDrawables(null, null, drawable, null);
                        getDataList();
                    }
                });
                break;
            //距离
            case R.id.tv_distance:
                mSortType = 1;
                tvDistance.setTextColor(getResources().getColor(R.color.theme));
                tvSale.setTextColor(getResources().getColor(R.color.color_666666));
                getDataList();
                break;
            //销量
            case R.id.tv_sale:
                mSortType = 2;
                tvSale.setTextColor(getResources().getColor(R.color.theme));
                tvDistance.setTextColor(getResources().getColor(R.color.color_666666));
                getDataList();
                break;
            //搜索
            case R.id.tv_search:
                startActivity(new Intent(mContext, SearchActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 1));
                break;
        }


    }

}
