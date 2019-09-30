package com.benben.yishanbang.ui.mine.activity;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.mine.adapter.LotteryUserListAdapter;
import com.benben.yishanbang.ui.mine.adapter.WinningUserListAdapter;
import com.benben.yishanbang.ui.mine.bean.LotteryLuckyDrawListBean;
import com.benben.yishanbang.ui.mine.bean.LotteryLuckyListBean;
import com.benben.yishanbang.ui.mine.bean.LotteryPopupListBean;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 开始抽奖活动
 */
public class LotteryListActivity extends BaseActivity {

    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.rlv_first_prize)
    RecyclerView rlvFirstPrize;
    @BindView(R.id.rlv_second_prize)
    RecyclerView rlvSecondPrize;
    @BindView(R.id.rlv_three_prize)
    RecyclerView rlvThreePrize;
    @BindView(R.id.rlv_four_prize)
    RecyclerView rlvFourPrize;
    @BindView(R.id.rlv_user_list)
    RecyclerView rlvUserList;
    @BindView(R.id.btn_start_lottery)
    Button btnStartLottery;
    private LotteryUserListAdapter mUserListAdapter;
    private TextView tvPrize;
    private TextView tvCurrentCount;
    private TextView tvRemainCount;
    private TextView tvWiningUserInfo;
    private String mActiveId;
    private WinningUserListAdapter mFourthPrizeListAdapter;
    private WinningUserListAdapter mThirdPrizeListAdapter;
    private WinningUserListAdapter mSecondPrizeListAdapter;
    private WinningUserListAdapter mFirstPrizeListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lottery_list;
    }

    @Override
    protected void initData() {
        centerTitle.setText("抽奖活动");
        mActiveId = getIntent().getStringExtra("active_id");
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rlvUserList.setLayoutManager(layoutManager);//用户池
        rlvFirstPrize.setLayoutManager(layoutManager);//一等奖中奖名单
        rlvSecondPrize.setLayoutManager(layoutManager);//二等奖中奖名单
        rlvThreePrize.setLayoutManager(layoutManager);//三等奖中奖名单
        rlvFourPrize.setLayoutManager(layoutManager);//四等奖中奖名单

        mUserListAdapter = new LotteryUserListAdapter(mContext);//用户池
        rlvUserList.setAdapter(mUserListAdapter);

        //1等奖名单
        mFirstPrizeListAdapter = new WinningUserListAdapter(mContext);
        //2等奖名单
        mSecondPrizeListAdapter = new WinningUserListAdapter(mContext);
        //3等奖名单
        mThirdPrizeListAdapter = new WinningUserListAdapter(mContext);
        //4等奖名单
        mFourthPrizeListAdapter = new WinningUserListAdapter(mContext);

        rlvSecondPrize.setAdapter(mSecondPrizeListAdapter);
        rlvFirstPrize.setAdapter(mFirstPrizeListAdapter);
        rlvThreePrize.setAdapter(mThirdPrizeListAdapter);
        rlvFourPrize.setAdapter(mFourthPrizeListAdapter);

        getUserList();

    }

    /**
     * 中奖名单数据
     */
    private void getUserList() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.QUERY_LUCKY_USER)
                .get()
                .addParam("activityId", mActiveId)
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<LotteryLuckyListBean> lotteryLuckyListBeans = JSONUtils.jsonString2Beans(json, LotteryLuckyListBean.class);
                if (lotteryLuckyListBeans != null && lotteryLuckyListBeans.size() > 3) {
                    for (int i = 0; i < lotteryLuckyListBeans.size(); i++) {
                        switch (lotteryLuckyListBeans.get(i).getPrizeGarde()) {
                            case "1":
                                mFirstPrizeListAdapter.refreshList(lotteryLuckyListBeans.get(i).getLuckyUser());
                                break;
                            case "2":
                                mSecondPrizeListAdapter.refreshList(lotteryLuckyListBeans.get(i).getLuckyUser());
                                break;
                            case "3":
                                mThirdPrizeListAdapter.refreshList(lotteryLuckyListBeans.get(i).getLuckyUser());
                                break;
                            default:
                                mFourthPrizeListAdapter.refreshList(lotteryLuckyListBeans.get(i).getLuckyUser());
                                break;
                        }

                    }
                }
            }

            @Override
            public void onError(int code, String msg) {
                toast(msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });
        /**
         * 用户池数据
         */

        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.QUERY_USER_DRAW_LIST)
                .get()
                .addParam("pageNo", 1)
                .addParam("pageSize", 20)
                .addParam("activityId", 1)
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<LotteryLuckyDrawListBean> lotteryLuckyDrawListBeans = JSONUtils.jsonString2Beans(json, LotteryLuckyDrawListBean.class);

                /**
                 * 设置适配器数据
                 */
                mUserListAdapter.setDrawListBeanList(lotteryLuckyDrawListBeans);

                toast(msg);
            }

            @Override
            public void onError(int code, String msg) {
                toast(msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                toast(e.getMessage());
            }
        });

    }


    @OnClick({R.id.rl_back, R.id.btn_start_lottery})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back://返回
                onBackPressed();
                break;
            case R.id.btn_start_lottery://开始抽奖
                showLotteryConfirmDialog();
                break;
        }
    }

    //确认抽奖弹窗
    private void showLotteryConfirmDialog() {
        getLotteryPopInfo();

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = View.inflate(mContext, R.layout.dialog_lottery_confirm, null);
        AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tvPrize = view.findViewById(R.id.tv_prize);
        tvCurrentCount = view.findViewById(R.id.tv_current_count);
        tvRemainCount = view.findViewById(R.id.tv_remain_count);
        Button btnLottery = view.findViewById(R.id.btn_lottery);
        ImageView ivCloseDialog = view.findViewById(R.id.iv_close_dialog);
        //抽奖
        btnLottery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                showLotteryResultDialog();
            }
        });
        ivCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    //抽奖结果
    private void showLotteryResultDialog() {
        getWinningUserInfo();

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = View.inflate(mContext, R.layout.dialog_lottery_result, null);
        AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tvPrize = view.findViewById(R.id.tv_prize);
        tvCurrentCount = view.findViewById(R.id.tv_current_count);
        tvRemainCount = view.findViewById(R.id.tv_remain_count);
        tvWiningUserInfo = view.findViewById(R.id.tv_wining_user_info);

        Button btnLottery = view.findViewById(R.id.btn_lottery);
        ImageView ivCloseDialog = view.findViewById(R.id.iv_close_dialog);


        btnLottery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                getLotteryPopInfo();
                getWinningUserInfo();
            }
        });
        ivCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    //获取弹窗信息
    private void getLotteryPopInfo() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.LUCK_DRAW_POP)
                .addParam("id", mActiveId)
                .get()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                LotteryPopupListBean lotteryPopupListBean = JSONUtils.jsonString2Bean(json, LotteryPopupListBean.class);
                tvPrize.setText("抽取奖项为：" + lotteryPopupListBean.getMessage());
                tvCurrentCount.setText("已抽取：" + lotteryPopupListBean.getPrizeNumber() + "名");
                tvRemainCount.setText("本奖项剩余：" + lotteryPopupListBean.getOverCount() + "名");
            }

            @Override
            public void onError(int code, String msg) {
                toast(msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });

    }

    //抽奖-获取中奖用户信息
    private void getWinningUserInfo() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.LUCK_DRAW_ADD)
                .addParam("id", mActiveId)
                .addParam("prizeId", mActiveId)//奖品id
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
                String mobile = JSONUtils.getNoteJson(json, "mobile");
                String nickname = JSONUtils.getNoteJson(json, "nickname");
                tvWiningUserInfo.setText(nickname + "   " + mobile);
            }

            @Override
            public void onError(int code, String msg) {
                toast(msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });

    }

}
