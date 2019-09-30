package com.benben.yishanbang.ui.mine.activity;


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
 * 添加收货地址
 */
public class AddAddressActivity extends BaseActivity {


    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_area)
    EditText edtArea;
    @BindView(R.id.edt_details_address)
    EditText edtDetailsAddress;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void initData() {
        centerTitle.setText("新增收货地址");
    }


    @OnClick({R.id.rl_back, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.btn_confirm:
                saveAddress();
                break;
        }
    }

    private void saveAddress() {
        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String area = edtArea.getText().toString().trim();
        String detailsAddress = edtDetailsAddress.getText().toString().trim();

        if (StringUtils.isEmpty(name)) {
            toast("姓名不能为空");
            return;
        }
        if (StringUtils.isEmpty(phone)) {
            toast("手机号不能为空");
            return;
        }
        if (!InputCheckUtil.checkPhoneNum(phone)) {
            toast("请输入正确的手机号");
            return;
        }
        if (StringUtils.isEmpty(area)) {
            toast("所在地区不能为空");
            return;
        }
        if (StringUtils.isEmpty(detailsAddress)) {
            toast("详细地址不能为空");
            return;
        }

        BaseOkHttpClient.newBuilder()
                .addParam("mobile", phone)
                .addParam("name", name)
                .addParam("address", area)
                .addParam("adressMag", detailsAddress)
                .post()
                .url(NetUrlUtils.ADDRESS_ADD)
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                toast("添加成功");
                finish();
            }

            @Override
            public void onError(int code, String msg) {
                toast("请确认您的信息");
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }
}
