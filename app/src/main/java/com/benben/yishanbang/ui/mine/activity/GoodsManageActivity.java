package com.benben.yishanbang.ui.mine.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.LogUtils;
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
import com.benben.yishanbang.ui.discount.activity.SearchActivity;
import com.benben.yishanbang.ui.discount.bean.CardShopCateBean;
import com.benben.yishanbang.ui.mine.adapter.WindowManageGoodsListAdapter;
import com.benben.yishanbang.ui.mine.adapter.WindowManageGoodsTypeListAdapter;
import com.benben.yishanbang.ui.mine.bean.TeaLookCateListBean;
import com.benben.yishanbang.ui.mine.bean.WindowManageGoodsListBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: 我的商品管理
 */
public class GoodsManageActivity extends BaseActivity {

    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.rlv_goods_type)
    RecyclerView rlvGoodsType;
    @BindView(R.id.llyt_favor_rate)
    LinearLayout llytFavorRate;
    @BindView(R.id.llyt_price)
    LinearLayout llytPrice;
    @BindView(R.id.llyt_sales_count)
    LinearLayout llytSalesCount;
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_favor_rate)
    TextView tvFavorRate;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_sales_count)
    TextView tvSalesCount;
    //排序类型
    private int mSortType = 0;
    private WindowManageGoodsTypeListAdapter mTypeAdapter;
    private int mPage = 1;
    private int mPageSize = 15;
    private boolean isRefresh = true;
    private WindowManageGoodsListAdapter mAdapter;
    //0 橱窗管理-我的橱窗  1 橱窗管理-数据报表  2 商户中心-优惠卡查看  3 商户中心-优惠卡 数据报表 4 商户中心-奶茶查看  5 商户中心-奶茶 数据报表
    private int mEnterType;
    //分类id
    private String mCateId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_goods_manage;
    }

    @Override
    protected void initData() {
        mEnterType = getIntent().getIntExtra(Constants.EXTRA_KEY_ENTER_TYPE, 0);
        initView();
        initRefreshLayout();
        initRecyclerView();
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        mTypeAdapter = new WindowManageGoodsTypeListAdapter(mContext);
        mAdapter = new WindowManageGoodsListAdapter(mContext);

        rlvGoodsType.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));

        mAdapter.setEnterType(mEnterType);
        rlvList.setAdapter(mAdapter);
        rlvGoodsType.setAdapter(mTypeAdapter);

        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<WindowManageGoodsListBean>() {
            @Override
            public void onItemClick(View view, int position, WindowManageGoodsListBean model) {
                if (mEnterType == 1) return;
                Intent intent = new Intent(mContext, WindowManageGoodsDetailActivity.class);
                switch (mEnterType) {
                    case 0:
                        intent.putExtra("add_or_del", "del");
                        intent.putExtra("goods", model);
                        startActivity(intent);
                        break;
                    case 10:
                    case 11:
                        intent.putExtra("add_or_del", "add");
                        intent.putExtra("goods", model);
                        startActivity(intent);
                        break;
                    default:
                        startActivity(intent);
                        break;
                }
            }

            @Override
            public void onItemLongClick(View view, int position, WindowManageGoodsListBean model) {

            }
        });
        mTypeAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<CardShopCateBean>() {
            @Override
            public void onItemClick(View view, int position, CardShopCateBean model) {
                mCateId = model.getCategoryId();
                getDataList();
            }

            @Override
            public void onItemLongClick(View view, int position, CardShopCateBean model) {

            }
        });

    }

    /**
     * 搜索商品列表
     */
    private void getSearchWindowList() {
        String name = getIntent().getStringExtra("key_word");

        BaseOkHttpClient.newBuilder()
                .addParam("name", name)
                .url(NetUrlUtils.WINDOW_SEARCH_GOODS_LIST)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                List<WindowManageGoodsListBean> mGoodsListBeans = null;
                try {
                    mGoodsListBeans = JSONUtils.jsonString2Beans(s, WindowManageGoodsListBean.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mAdapter.refreshList(mGoodsListBeans);
                if (mAdapter.getItemCount() > 0) {
                    llytNoData.setVisibility(View.GONE);
                    rlvList.setVisibility(View.VISIBLE);
                } else {
                    llytNoData.setVisibility(View.VISIBLE);
                    rlvList.setVisibility(View.GONE);
                }
                refreshLayout.finishRefresh(true);
                refreshLayout.finishLoadMore(true);
            }

            @Override
            public void onError(int code, String msg) {
                //  ToastUtils.show(mContext, msg);
                llytNoData.setVisibility(View.VISIBLE);
                rlvList.setVisibility(View.GONE);
                refreshLayout.finishRefresh(true);
                refreshLayout.finishLoadMore(true);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                refreshLayout.finishRefresh(false);
                refreshLayout.finishLoadMore(false);
            }
        });
    }

    /**
     * 获取 我的橱窗商品列表
     */
    private void getMyWindowList() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.WINDOW_MY_GOODS_STYLE)
                .post()
                .json()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<CardShopCateBean> cardShopCateBeans = JSONUtils.jsonString2Beans(json, CardShopCateBean.class);
                if (cardShopCateBeans != null && cardShopCateBeans.size() > 0) {
                    mTypeAdapter.refreshList(cardShopCateBeans);
                    cardShopCateBeans.get(0).setSelected(true);
                    mCateId = cardShopCateBeans.get(0).getCategoryId();
                    getDataList();
                }

            }

            @Override
            public void onError(int code, String msg) {
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e("", "");
            }
        });
    }

    //获取优惠卡分类
    private void getCardCateList() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.GET_STORE_CATEGORY)
                .get()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<CardShopCateBean> cardShopCateBeans = JSONUtils.jsonString2Beans(json, CardShopCateBean.class);
                if (cardShopCateBeans != null && cardShopCateBeans.size() > 0) {
                    mTypeAdapter.refreshList(cardShopCateBeans);
                    cardShopCateBeans.get(0).setSelected(true);
                    mCateId = cardShopCateBeans.get(0).getCategoryId();
                    getDataList();
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

    //获取奶茶分类
    private void getTeaCateList() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.LOOK_TEA)
                .addParam("token", MyApplication.mPreferenceProvider.getToken())
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<TeaLookCateListBean> shopCateInfoBeans = JSONUtils.jsonString2Beans(json, TeaLookCateListBean.class);

                if (shopCateInfoBeans != null && shopCateInfoBeans.size() > 0) {
                    ArrayList<CardShopCateBean> cateList = new ArrayList<>();
                    for (int i = 0; i < shopCateInfoBeans.size(); i++) {
                        CardShopCateBean cardShopCateBean = new CardShopCateBean();
                        cardShopCateBean.setCategoryName(shopCateInfoBeans.get(i).getCategoryName());
                        cardShopCateBean.setCategoryId(shopCateInfoBeans.get(i).getId());
                        cardShopCateBean.setSortId(shopCateInfoBeans.get(i).getSortId());
                        cateList.add(cardShopCateBean);
                    }
                    mTypeAdapter.refreshList(cateList);
                    cateList.get(0).setSelected(true);
                    mCateId = cateList.get(0).getCategoryId();
                    getDataList();
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

    private void initView() {

        switch (mEnterType) {
            case 11://搜索 llyt_favor_raterlv_goods_type
                ((View) llytFavorRate.getParent()).setVisibility(View.GONE);
                ((View) rlvGoodsType.getParent()).setVisibility(View.GONE);
                centerTitle.setText("搜索结果");//橱窗管理-数据报表
                rightTitle.setVisibility(View.GONE);
                getSearchWindowList();
                break;
            case 10:///所有商品
                rightTitle.setVisibility(View.VISIBLE);//橱窗管理-所有商品
                centerTitle.setText("创业商城");
                rightTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_search_white), null);
                getMyWindowList();
                break;
            case 0:
                rightTitle.setVisibility(View.VISIBLE);//橱窗管理-我的商品
                centerTitle.setText("我的商品");
                rightTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.icon_add_goods), null);
                getMyWindowList();
                break;
            case 1:
                centerTitle.setText("数据报表");//橱窗管理-数据报表
                rightTitle.setVisibility(View.GONE);
                getMyWindowList();
                break;
            case 2:
                centerTitle.setText("我的优惠卡");//商户中心 -我的优惠卡
                rightTitle.setVisibility(View.GONE);
                getCardCateList();
                break;
            case 3:
                centerTitle.setText("数据报表");//商户中心 -优惠卡 数据报表
                rightTitle.setVisibility(View.GONE);
                getCardCateList();
                break;
            case 4:
                centerTitle.setText("我的奶茶");//商户中心 -我的奶茶
                rightTitle.setVisibility(View.GONE);
                getTeaCateList();
                break;
            case 5:
                centerTitle.setText("数据报表");//商户中心 -奶茶 数据报表
                rightTitle.setVisibility(View.GONE);
                getTeaCateList();
                break;
        }
        rightTitle.setPadding(10, 0, 10, 0);
    }

    private void initRefreshLayout() {
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            isRefresh = true;
            mPage = 1;
            getDataList();
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            isRefresh = false;
            mPage++;
            getDataList();
        });
    }

    //获取商品列表
    private void getDataList() {
        if (StringUtils.isEmpty(mCateId)) {
            toast("服务器异常，请稍后再试");
            return;
        }
        BaseOkHttpClient.Builder builder = BaseOkHttpClient.newBuilder();
        builder.addParam("categoryId", mCateId);
        if (mEnterType != 0 && mEnterType != 1 && mEnterType != 10 && mEnterType != 11) {
            builder.addParam("type", MyApplication.mPreferenceProvider.getUserType());
            switch (mSortType) {
                case 0:
                    builder.addParam("nice", "nice");
                    break;
                case 1:
                    builder.addParam("price", "price");
                    break;
                case 2:
                    builder.addParam("number", "number");
                    break;
            }
        } else {
            switch (mSortType) {
                case 0:
                    builder.addParam("type", "1");
                    break;
                case 1:
                    builder.addParam("type", "2");
                    break;
                case 2:
                    builder.addParam("type", "3");
                    break;
            }
        }
        switch (mEnterType) {
            case 0:
                //我的商品
                builder.url(NetUrlUtils.WINDOW_MY_GOODS_SHOW);
                break;
            case 1:
                //我的商品
                builder.url(NetUrlUtils.WINDOW_DATA_LIST);
                break;
            case 10:
                //所有商品
                builder.url(NetUrlUtils.WINDOW_ALL_GOODS_SHOW);
                break;
            case 11:
                //搜索商品
                builder.url(NetUrlUtils.WINDOW_SEARCH_GOODS_LIST);
                break;
            case 2:
            case 4:
                //优惠卡/奶茶查看
                builder.url(NetUrlUtils.LOOK_COUPON_CARD);
                break;
            case 3:
            case 5:
                //数据报表
                builder.url(NetUrlUtils.SHOP_CENTER_DATA_REPORT);
                break;
        }
        builder.post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {

                List<WindowManageGoodsListBean> mGoodsListBeans = null;
                try {
                    mGoodsListBeans = JSONUtils.jsonString2Beans(json, WindowManageGoodsListBean.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mAdapter.refreshList(mGoodsListBeans);
                if (mAdapter.getItemCount() > 0) {
                    llytNoData.setVisibility(View.GONE);
                    rlvList.setVisibility(View.VISIBLE);
                } else {
                    llytNoData.setVisibility(View.VISIBLE);
                    rlvList.setVisibility(View.GONE);
                }
                refreshLayout.finishRefresh(true);
                refreshLayout.finishLoadMore(true);
            }

            @Override
            public void onError(int code, String msg) {
                //  ToastUtils.show(mContext, msg);
                llytNoData.setVisibility(View.VISIBLE);
                rlvList.setVisibility(View.GONE);
                refreshLayout.finishRefresh(true);
                refreshLayout.finishLoadMore(true);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                refreshLayout.finishRefresh(false);
                refreshLayout.finishLoadMore(false);
            }
        });


    }

    @OnClick({R.id.rl_back, R.id.right_title, R.id.llyt_favor_rate, R.id.llyt_price, R.id.llyt_sales_count})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.right_title://创业商城
            case R.id.tv_my_window://我的橱窗
                if (mEnterType == 10) {
                    Intent intent = new Intent(mContext, SearchActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 4);
                    startActivity(intent);
                } else if (mEnterType == 0) {
                    startActivity(new Intent(mContext, GoodsManageActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 10));
                }
                break;
            case R.id.llyt_favor_rate:
                mSortType = 0;
                switchSortType(mSortType, tvFavorRate);
                getDataList();
                break;
            case R.id.llyt_price:
                mSortType = 1;
                switchSortType(mSortType, tvPrice);
                getDataList();
                break;
            case R.id.llyt_sales_count:
                mSortType = 2;
                switchSortType(mSortType, tvSalesCount);
                getDataList();
                break;
        }
    }

    //排序类型
    private void switchSortType(int type, TextView textView) {
        Drawable greyArrow = getResources().getDrawable(R.mipmap.icon_pick_bottom_grey);
        switch (type) {
            case 0:
                tvPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, greyArrow, null);
                tvSalesCount.setCompoundDrawablesWithIntrinsicBounds(null, null, greyArrow, null);
                tvPrice.setTextColor(getResources().getColor(R.color.color_666666));
                tvSalesCount.setTextColor(getResources().getColor(R.color.color_666666));
                break;
            case 1:
                tvFavorRate.setCompoundDrawablesWithIntrinsicBounds(null, null, greyArrow, null);
                tvSalesCount.setCompoundDrawablesWithIntrinsicBounds(null, null, greyArrow, null);
                tvFavorRate.setTextColor(getResources().getColor(R.color.color_666666));
                tvSalesCount.setTextColor(getResources().getColor(R.color.color_666666));
                break;
            case 2:
                tvFavorRate.setCompoundDrawablesWithIntrinsicBounds(null, null, greyArrow, null);
                tvPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, greyArrow, null);
                tvFavorRate.setTextColor(getResources().getColor(R.color.color_666666));
                tvPrice.setTextColor(getResources().getColor(R.color.color_666666));
                break;
        }
        textView.setTextColor(getResources().getColor(R.color.theme));
        Drawable greenArrow = getResources().getDrawable(R.mipmap.icon_pick_bottom_green);
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, greenArrow, null);

    }

}
