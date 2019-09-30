package com.benben.yishanbang.ui.mine.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.benben.commoncore.utils.InputCheckUtil;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.LogUtils;
import com.benben.commoncore.utils.ScreenUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.mine.bean.AuthStatusBean;
import com.benben.yishanbang.ui.mine.bean.VerifiedBean;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by: wanghk 2019-07-04.
 * Describe:实名认证
 */
public class VerifiedActivity extends BaseActivity {
    private static final String TAG = "RealNameAuthenticationActivity";
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_id_number)
    EditText edtIdNumber;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    private AuthStatusBean authStatusBean;
    //认证状态
    private boolean mAuthStatus = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_verified;
    }

    @Override
    protected void initData() {

//        authStatusBean = (AuthStatusBean) getIntent().getSerializableExtra("auth_info");
//        if (authStatusBean != null) {
//            switch (authStatusBean.getType()) {//-1失败，0待审核，1成功
//                case 0://待审核
//                    initTitle("待审核");
//                    btnConfirm.setVisibility(View.GONE);
//                    edtName.setEnabled(false);
//                    edtIdNumber.setEnabled(false);
//                    break;
//                case -1://失败
//                    initTitle("认证失败");
//                    break;
//                case 1://成功
//                    initTitle("认证成功");
//                    btnConfirm.setVisibility(View.GONE);
//                    edtName.setEnabled(false);
//                    edtIdNumber.setEnabled(false);
//                    break;
//            }
//        } else {
        initTitle("实名认证");
//        }
        //过滤表情 符号
        InputCheckUtil.filterEmoji(edtName, mContext);
        //不为空说明已经认证成功 不能修改
        if (!StringUtils.isEmpty(MyApplication.mPreferenceProvider.getRealName()) && !StringUtils.isEmpty(MyApplication.mPreferenceProvider.getIdNumber())) {
            mAuthStatus = true;
            edtName.setText(MyApplication.mPreferenceProvider.getRealName());
            edtIdNumber.setText(MyApplication.mPreferenceProvider.getIdNumber());
            edtName.setEnabled(false);
            edtIdNumber.setEnabled(false);
        }

        //refreshData();
    }

    private void refreshData() {
        if (authStatusBean != null) {
            edtIdNumber.setText(authStatusBean.getSfz());
            edtName.setText(authStatusBean.getName());
        }
    }

    @OnClick({R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm://点击提交
                if (mAuthStatus) {
                    toast("您已认证成功，无需再次提交");
                    return;
                }
                confirmInfo();
                break;
        }
    }

    //提交信息
    private void confirmInfo() {
        String name = edtName.getText().toString().trim();
        String idNumber = edtIdNumber.getText().toString().trim();

        if (StringUtils.isEmpty(name)) {
            toast("姓名不能为空");
            return;
        }
        if (StringUtils.isEmpty(idNumber)) {
            toast("身份证号不能为空");
            return;
        }
        if (!InputCheckUtil.checkIdCard(idNumber)) {
            toast("请输入正确的身份证号");
            return;
        }

        BaseOkHttpClient.newBuilder()
                .addParam("name", name)
                .addParam("citizenNo", idNumber)
                .url(NetUrlUtils.USER_ADD_APPROVE)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                VerifiedBean verifiedBean = JSONUtils.jsonString2Bean(json, VerifiedBean.class);
                toast(msg);
                finish();

            }

            @Override
            public void onError(int code, String msg) {
                LogUtils.e(TAG, "实名认证----onError:" + msg);
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e(TAG, "实名认证----onFailure:" + e.getMessage());
            }
        });


    }


    @Override
    protected void onPause() {
        super.onPause();
        if (ScreenUtils.isShowKeyboard(mContext, edtIdNumber))
            ScreenUtils.closeKeybord(edtIdNumber, mContext);
    }

}
