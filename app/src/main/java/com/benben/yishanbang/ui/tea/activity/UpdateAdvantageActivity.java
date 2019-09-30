package com.benben.yishanbang.ui.tea.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.InputCheckUtil;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.RxBus;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.tea.adapter.UpdateAdvantageAdapter;
import com.benben.yishanbang.ui.tea.bean.AdvantageLabelBean;
import com.benben.yishanbang.widget.FlowLayoutManager;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2019/8/16 0016
 * Describe:修改他的优势
 */
public class UpdateAdvantageActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.et_advantage_title)
    EditText etAdvantageTitle;
    @BindView(R.id.et_advantage_content)
    EditText etAdvantageContent;
    @BindView(R.id.rlv_advantage_choose)
    RecyclerView rlvAdvantageChoose;
    private UpdateAdvantageAdapter mUpdateAdvantageAdapter;//选择优势适配器

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_user_advantage;
    }

    @Override
    protected void initData() {
        rightTitle.setText("保存");
        centerTitle.setText("我的优势");

        etAdvantageTitle.setText(getIntent().getStringExtra("title"));
        etAdvantageContent.setText(getIntent().getStringExtra("content"));

        mUpdateAdvantageAdapter = new UpdateAdvantageAdapter(mContext);
        FlowLayoutManager mFlowLayoutManager = new FlowLayoutManager();
        rlvAdvantageChoose.setLayoutManager(mFlowLayoutManager);
        rlvAdvantageChoose.setAdapter(mUpdateAdvantageAdapter);

        //过滤表情
        InputCheckUtil.filterEmoji(etAdvantageContent, mContext);
        InputCheckUtil.filterEmoji(etAdvantageTitle, mContext);

        getLabelList();
    }

    //获取标签列表
    private void getLabelList() {
        BaseOkHttpClient.newBuilder()
                .addParam("token", MyApplication.mPreferenceProvider.getToken())
                .url(NetUrlUtils.GET_USER_ADVANTAGE_LABEL_LIST)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<AdvantageLabelBean> advantageLabelBeans = JSONUtils.jsonString2Beans(json, AdvantageLabelBean.class);
                mUpdateAdvantageAdapter.refreshList(advantageLabelBeans);
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

    @OnClick({R.id.rl_back, R.id.right_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back://返回
                finish();
                break;
            case R.id.right_title://保存
                saveAdvantageInfo();
                break;
        }
    }

    //保存优势信息
    private void saveAdvantageInfo() {

        if (StringUtils.isEmpty(etAdvantageTitle.getText().toString().trim())) {
            toast("请输入优势标题");
            return;
        }
        if (StringUtils.isEmpty(etAdvantageContent.getText().toString().trim())) {
            toast("请输入优势内容");
            return;
        }
        if (StringUtils.isEmpty(mUpdateAdvantageAdapter.getLabelIds())) {
            toast("请选择优势标签");
            return;
        }
        BaseOkHttpClient.newBuilder()
                .addParam("title", etAdvantageTitle.getText().toString())
                .addParam("mage", etAdvantageContent.getText().toString())
                .addParam("labelId", mUpdateAdvantageAdapter.getLabelIds())
                .url(NetUrlUtils.UPDATE_USER_ADVANTAGE)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
                RxBus.getInstance().post(Constants.REFRESH_USER_DETAILS_INFO);
                finish();
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
