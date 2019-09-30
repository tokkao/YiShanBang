package com.benben.yishanbang.ui.discount.activity;

import android.view.Gravity;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.bean.PayBean;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.pop.AnswerPopupWindow;
import com.benben.yishanbang.pop.PayPopupWindow;
import com.benben.yishanbang.ui.discount.adapter.AnswerAdapter;
import com.benben.yishanbang.ui.discount.bean.AnswerResultBean;
import com.benben.yishanbang.ui.discount.bean.QuestionBean;
import com.benben.yishanbang.utils.PayListenerUtils;
import com.benben.yishanbang.utils.PayResultListener;
import com.benben.yishanbang.utils.PayUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/6
 * Time: 16:07
 * 答题
 */
public class AnswerActivity extends BaseActivity {
    private static final String TAG = "AnswerActivity";
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.rv_answer)
    RecyclerView rvAnswer;

    private AnswerAdapter mAdapter;//答题适配器

    private AnswerPopupWindow mAnswerPopupWindow;//完成答题的提示框

    private PayPopupWindow mPayPopupWindow;//支付弹窗
    //优惠卡id
    private String mCardId;
    //订单id
    private String mOrderNo;
    //订单金额
    private String mOrderMoney;
    //题库id
    private String mTitlebankid;
    //支付工具类
    private PayUtils mPayUtils;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_answer;
    }

    @Override
    protected void initData() {
        initTitle("答题");
        mCardId = getIntent().getStringExtra("card_id");
        rightTitle.setText("完成");

        rvAnswer.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new AnswerAdapter(mContext);
        rvAnswer.setAdapter(mAdapter);

        mPayUtils = new PayUtils(mContext);
        mAnswerPopupWindow = new AnswerPopupWindow(mContext, new AnswerPopupWindow.OnAnswerCallback() {
            @Override
            public void cancel() {
                //取消购买
            }

            @Override
            public void submit() {
                //生成订单
                cardCreateOrder();
            }
        });

        mPayPopupWindow = new PayPopupWindow(mContext, new PayPopupWindow.OnPayCallback() {
            @Override
            public void alipay() {
                cardAlipay();
            }

            @Override
            public void wxpay() {
                cardWxPay();
            }
        });

        getAnswerList();

        PayListenerUtils.getInstance(mContext).addListener(new PayResultListener() {
            @Override
            public void onPaySuccess() {
                toast("支付成功");
                finish();
            }

            @Override
            public void onPayError() {
                toast("支付失败");
            }

            @Override
            public void onPayCancel() {
                toast("支付取消");
            }
        });
    }

    private void cardAlipay() {
        BaseOkHttpClient.newBuilder()
                .addParam("orderId", mOrderNo)//优惠卡id
                .addParam("orderMoney", StringUtils.isEmpty(mOrderMoney) ? "0" : mOrderMoney)//
                .addParam("orderName", "youhuika")//body 随便写
                .addParam("body", "test")//body 随便写
                .url(NetUrlUtils.ALIPAY)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                if(StringUtils.isEmpty(json)){
                    toast("获取订单信息失败，请重试");
                    return;
                }
                mPayUtils.aliPay(json,mContext);
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


    //微信支付
    private void cardWxPay() {
        BaseOkHttpClient.newBuilder()
                .addParam("orderId", mOrderNo)//优惠卡id
                .addParam("orderMoney", StringUtils.isEmpty(mOrderMoney) ? "0" : mOrderMoney)//
                .addParam("body", "youhuika")//body 随便写
                .url(NetUrlUtils.WX_PAY)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                PayBean payBean = new Gson().fromJson(json, PayBean.class);
                mPayUtils.wxPay(payBean);
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


    //优惠卡生成订单
    private void cardCreateOrder() {
        BaseOkHttpClient.newBuilder()
                .addParam("id", mCardId)//优惠卡id
                .url(NetUrlUtils.CARD_CREATE_ORDER)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                mOrderNo = JSONUtils.getNoteJson(json, "orderNo");
                mOrderMoney = JSONUtils.getNoteJson(json, "orderMoney");
                //直接购买
                if (mPayPopupWindow != null) {
                    mPayPopupWindow.setTypePrice("", mOrderMoney);
                    mPayPopupWindow.showAtLocation(rvAnswer, Gravity.BOTTOM, 0, 0);
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


    //获取答题信息
    private void getAnswerList() {
        BaseOkHttpClient.newBuilder()
                .addParam("cardId", mCardId)
                .url(NetUrlUtils.GET_ANSWER_LIST)
                .get()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<QuestionBean> questionBeans = JSONUtils.jsonString2Beans(json, QuestionBean.class);
                if (questionBeans != null && questionBeans.size() > 0) {
                    mTitlebankid = questionBeans.get(0).getTitlebankid();
                    mAdapter.setList(questionBeans);
                    mAdapter.notifyDataSetChanged();
                } else {
                    toast("获取题库信息失败，请退出重试");
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

    @OnClick(R.id.right_title)
    public void onViewClicked() {
        if (mAdapter != null) {
            if (mAdapter.isSelectedAll()) {
                getAnswerResult();
            } else {
                toast("部分题目还未作答");
            }
        }
    }

    //验证答案是否正确
    private void getAnswerResult() {

        List<String> selectedAnswer = mAdapter.getSelectedAnswer();
        StringBuilder sb = new StringBuilder();
        if (selectedAnswer != null && selectedAnswer.size() > 0) {
            for (int i = 0; i < selectedAnswer.size(); i++) {
                //最后一个不用加逗号
                sb.append(i == selectedAnswer.size() - 1 ? selectedAnswer.get(i) : selectedAnswer.get(i) + ",");
            }
        }
        String answer = sb.toString();
        BaseOkHttpClient.newBuilder()
                .addParam("titlebankid", mTitlebankid)//题库id
                .addParam("bank", answer)//答案字符串
                .addParam("cardId", mCardId)//优惠卡id
                .url(NetUrlUtils.GET_ANSWER)
                .get()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                AnswerResultBean answerResultBean = JSONUtils.jsonString2Bean(json, AnswerResultBean.class);
                if ("1".equals(answerResultBean.getQualification())) {
                    if (mAnswerPopupWindow != null) {
                        mAnswerPopupWindow.showAtLocation(rightTitle, Gravity.CENTER, 0, 0);
                    }
                } else {
                    toast("很遗憾，部分题目错误，您未获取抢购资格，请再接再厉");
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
}
