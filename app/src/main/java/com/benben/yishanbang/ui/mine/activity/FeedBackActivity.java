package com.benben.yishanbang.ui.mine.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.benben.commoncore.utils.InputCheckUtil;
import com.benben.commoncore.utils.StringUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: 投诉建议
 */
public class FeedBackActivity extends BaseActivity {

    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_mobile)
    EditText edtMobile;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_content)
    EditText edtContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void initData() {
        centerTitle.setText("投诉建议");
        //过滤表情 符号
        InputCheckUtil.filterEmoji(edtName, mContext);
        InputCheckUtil.filterEmoji(edtContent, mContext);
    }

    @OnClick({R.id.rl_back, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();//返回上一页
                break;
            case R.id.btn_confirm://提交
                submitData();
                break;
        }
    }

    /**
     * 获取输入框输入的数据
     */
    private void submitData() {
        String name = edtName.getText().toString().trim();//姓名
        String mobile = edtMobile.getText().toString().trim();//电话
        String email = edtEmail.getText().toString().trim();//邮箱
        String content = edtContent.getText().toString().trim();//投诉建议

        if (TextUtils.isEmpty(name)) {
            toast("请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(mobile)) {
            toast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            toast("请输入邮箱地址");
            return;
        }
        if (!InputCheckUtil.checkPhoneNum(mobile)) {
            toast("请输入正确的手机号");
            return;
        }
        if (!InputCheckUtil.isEmail(email)) {
            toast("请输入正确的邮箱地址");
            return;
        }
        if (StringUtils.isEmpty(content)) {
            toast("请输入意见或建议");
            return;
        }

        getDataList(name, mobile, email, content);
    }

    /**
     * 获取接口返回的数据
     *
     * @param name
     * @param mobile
     * @param email
     * @param content
     */
    private void getDataList(String name, String mobile, String email, String content) {

        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.FEED_BACK)
                .addParam("name", name)
                .addParam("iphone", mobile)
                .addParam("email", email)
                .addParam("content", content)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String result, String msg) {
                toast(msg);
                finish();
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
