package com.benben.yishanbang.ui.discount.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.InputCheckUtil;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.discount.adapter.SearchHotAdapter;
import com.benben.yishanbang.ui.discount.bean.HotSearchListBean;
import com.benben.yishanbang.ui.mine.activity.GoodsManageActivity;
import com.benben.yishanbang.utils.SpSaveListUtils;
import com.benben.yishanbang.widget.FlowLayoutManager;
import com.kongzue.dialog.v3.MessageDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/6
 * Time: 10:03
 * 搜索过渡页
 */
public class SearchActivity extends BaseActivity {
    private static final String TAG = "SearchActivity";
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_hot_search_title)
    TextView tvHotSearchTitle;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.rv_hot)
    RecyclerView rvHot;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.rv_history)
    RecyclerView rvHistory;
    //表示从哪个页面进入，0 首页 1优惠卡 2奶茶 3钱包--优惠卡  4创业商城橱窗
    private int mEnterType;
    private SearchHotAdapter mHotAdapter;//热门搜索
    private SearchHotAdapter mHistoryAdapter;//历史搜索
    //保存历史记录工具类
    private SpSaveListUtils mSpUtils;
    //历史记录类型
    private String mSpTag;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initData() {
        mEnterType = getIntent().getIntExtra(Constants.EXTRA_KEY_ENTER_TYPE, 0);
        switch (mEnterType) {
            case 0:
                mSpTag = "home_history";
                tvHotSearchTitle.setVisibility(View.VISIBLE);
                rvHot.setVisibility(View.VISIBLE);
                break;
            case 1:
                mSpTag = "card_history";
                break;
            case 2:
                mSpTag = "tea_history";
                break;
            case 3:
                mSpTag = "wallet_history";
                break;
            case 4:
                mSpTag = "window_history";
                break;
        }
        //过滤表情
        InputCheckUtil.filterEmoji(edtSearch, mContext);
        mSpUtils = new SpSaveListUtils(mContext, mSpTag);
        FlowLayoutManager manager = new FlowLayoutManager();
        rvHot.setLayoutManager(manager);

        FlowLayoutManager staggeredGridLayoutManager = new FlowLayoutManager();
        rvHistory.setLayoutManager(staggeredGridLayoutManager);

        mHotAdapter = new SearchHotAdapter(mContext);
        mHistoryAdapter = new SearchHotAdapter(mContext);
        rvHistory.setAdapter(mHistoryAdapter);
        rvHot.setAdapter(mHotAdapter);

        edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (TextUtils.isEmpty(v.getText().toString().trim())) {
                    Toast.makeText(mContext, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                    return true;
                }
                //保存搜索记录
                saveSearchHistory(v.getText().toString().trim());
                if (mEnterType == 4) {
                    Intent intent = new Intent(mContext, GoodsManageActivity.class);
                    intent.putExtra("key_word", "" + v.getText().toString().trim());
                    intent.putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 11);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, SearchDiscountActivity.class);
                    intent.putExtra("key_word", "" + v.getText().toString().trim());
                    intent.putExtra(Constants.EXTRA_KEY_ENTER_TYPE, mEnterType);
                    startActivity(intent);
                }
                finish();
                return true;
            }
            return false;
        });

        //只有首页有热门搜索
        if (mEnterType == 0) {
            //热门搜索
            getHotSearchList();
        }
        //历史搜索
        getHistorySearch();
    }

    //获取历史搜索
    private void getHistorySearch() {
        List<String> lastList = mSpUtils.getDataList(mSpTag);
        if (lastList == null || lastList.size() <= 0) {
            return;
        }
        ArrayList<HotSearchListBean> hotSearchListBeans = new ArrayList<>();
        for (int i = 0; i < lastList.size(); i++) {
            HotSearchListBean hotSearchListBean = new HotSearchListBean();
            hotSearchListBean.setSearchName(lastList.get(i));
            hotSearchListBeans.add(hotSearchListBean);
        }
        mHistoryAdapter.setEnterType(mEnterType);
        mHistoryAdapter.setBean(hotSearchListBeans);
        mHistoryAdapter.notifyDataSetChanged();
    }

    //保存历史搜索记录
    private void saveSearchHistory(String keyWord) {
        List<String> lastList = mSpUtils.getDataList(mSpTag);
        if (lastList == null) {
            lastList = new ArrayList<>();
        }
        //过滤相同的关键词
        for (int i = 0; i < lastList.size(); i++) {
            if (keyWord.equals(lastList.get(i))) return;
        }
        //最多10条历史记录
        if (lastList.size() >= 10) {
            lastList.remove(lastList.size() - 1);
        }

        List<String> historyList = new ArrayList<>();
        historyList.add(keyWord);
        historyList.addAll(lastList);
        mSpUtils.setDataList(mSpTag, historyList);

    }

    //删除历史记录
    private void deleteHistory() {
        ArrayList<HotSearchListBean> hotSearchListBeans = new ArrayList<>();
        mSpUtils.clearList(mSpTag);
        mHistoryAdapter.setBean(hotSearchListBeans);
        mHistoryAdapter.notifyDataSetChanged();
    }

    //热门搜索记录
    private void getHotSearchList() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.GET_HOT_SEARCH_LIST)
                .get()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<HotSearchListBean> list = JSONUtils.jsonString2Beans(json, HotSearchListBean.class);
                if (list != null) {
                    mHotAdapter.setBean(list);
                    mHotAdapter.notifyDataSetChanged();
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

    @OnClick({R.id.tv_search, R.id.iv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //搜索
            case R.id.tv_search:
                finish();
                break;
            //删除
            case R.id.iv_delete:
                MessageDialog.show((AppCompatActivity) mContext, "提示", "确定删除吗？", "确定", "取消")
                        .setOnOkButtonClickListener((baseDialog, v) -> {
                            deleteHistory();
                            return false;
                        });

                break;
        }
    }


}
