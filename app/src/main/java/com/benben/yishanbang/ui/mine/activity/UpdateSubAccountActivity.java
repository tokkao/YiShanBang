package com.benben.yishanbang.ui.mine.activity;


import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.benben.commoncore.utils.InputCheckUtil;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.widget.VerifyCodeButton;
import com.kongzue.dialog.v3.MessageDialog;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 添加/编辑 子账号
 */
public class UpdateSubAccountActivity extends BaseActivity {


    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.edt_current_phone)
    EditText edtCurrentPhone;
    @BindView(R.id.edt_verify_code)
    EditText edtVerifyCode;
    @BindView(R.id.btn_get_verify_code)
    VerifyCodeButton btnGetVerifyCode;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.tv_store_manage)
    TextView tvStoreManage;
    @BindView(R.id.tv_coupon_card)
    TextView tvCouponCard;
    @BindView(R.id.tv_order_manage)
    TextView tvOrderManage;
    @BindView(R.id.tv_user_manage)
    TextView tvUserManage;
    @BindView(R.id.tv_data_report)
    TextView tvDataReport;
    @BindView(R.id.tv_write_off_order)
    TextView tvWriteOffOrder;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.view_holder)
    View viewHolder;
    // 0 新增子账号  1 修改子账号
    private int mEnterType;
    //验证码
    private String mVerifyCode;
    //小号id
    private String mSubUserId;
    //权限编号
    private String mPermissionNum;
    private String mPhone;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_subaccount;
    }

    @Override
    protected void initData() {
        mEnterType = getIntent().getIntExtra(Constants.EXTRA_KEY_ENTER_TYPE, 0);
        mSubUserId = getIntent().getStringExtra(Constants.EXTRA_KEY_USER_ID);
        mPhone = getIntent().getStringExtra("phone");
        mPermissionNum = getIntent().getStringExtra("control");

        centerTitle.setText(mEnterType == 0 ? "新增子账号" : "修改子账号");
        rightTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.icon_delete_subaccount), null);
        rightTitle.setVisibility(mEnterType == 0 ? View.GONE : View.VISIBLE);
        btnConfirm.setText(mEnterType == 0 ? "确认增加子账户" : "确认修改");

        if (mEnterType == 1) {
            refreshUI();
        }

    }

    private void refreshUI() {
        edtCurrentPhone.setText(mPhone);
        String[] split = mPermissionNum.split(",");
        //设置选中状态
        for (String s : split) {
            if ("1".equals(s)) {
                tvStoreManage.setSelected(true);
            } else if ("2".equals(s)) {
                tvCouponCard.setSelected(true);
            } else if ("3".equals(s)) {
                tvOrderManage.setSelected(true);
            } else if ("4".equals(s)) {
                tvUserManage.setSelected(true);
            } else if ("5".equals(s)) {
                tvDataReport.setSelected(true);
            } else if ("6".equals(s)) {
                tvWriteOffOrder.setSelected(true);
            }

        }

    }


    @OnClick({R.id.rl_back, R.id.right_title, R.id.btn_get_verify_code, R.id.tv_store_manage, R.id.tv_coupon_card, R.id.tv_order_manage, R.id.tv_user_manage, R.id.tv_data_report, R.id.tv_write_off_order, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.right_title:
                MessageDialog.show((AppCompatActivity) mContext, "提示", "确定删除吗？", "确定", "取消")
                        .setOnOkButtonClickListener((baseDialog, v) -> {
                            //删除
                            editOrDeleteAccount(0);
                            return false;
                        });
                break;
            case R.id.btn_get_verify_code://获取验证码
                getVerifyCode();
                break;
            case R.id.tv_store_manage:
                tvStoreManage.setSelected(!tvStoreManage.isSelected());
                break;
            case R.id.tv_coupon_card:
                tvCouponCard.setSelected(!tvCouponCard.isSelected());
                break;
            case R.id.tv_order_manage:
                tvOrderManage.setSelected(!tvOrderManage.isSelected());
                break;
            case R.id.tv_user_manage:
                tvUserManage.setSelected(!tvUserManage.isSelected());
                break;
            case R.id.tv_data_report:
                tvDataReport.setSelected(!tvDataReport.isSelected());
                break;
            case R.id.tv_write_off_order:
                tvWriteOffOrder.setSelected(!tvWriteOffOrder.isSelected());
                break;
            case R.id.btn_confirm:

                if (mEnterType == 0) {
                    //添加子账户
                    addAccount();
                } else {
                    //确认修改或删除
                    editOrDeleteAccount(1);
                }

                break;
        }
    }

    //新增子账号
    private void addAccount() {
        String phone = edtCurrentPhone.getText().toString().trim();
        String code = edtVerifyCode.getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {
            toast("请输入手机号！");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(mContext, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!InputCheckUtil.checkPhoneNum(phone)) {
            toast("请输入正确的手机号");
            return;
        }

        if (TextUtils.isEmpty(mVerifyCode)) {
            Toast.makeText(mContext, "请先获取验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        getPermissionNum();
        BaseOkHttpClient.newBuilder()
                .addParam("iphone", edtCurrentPhone.getText().toString().trim())
                .addParam("code", code)
                .addParam("control", mPermissionNum)
                .addParam("event", 6)
                .url(NetUrlUtils.ADD_SUBACCOUNT_LIST)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
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

    //编辑/删除
    private void editOrDeleteAccount(int type) {
        String phone = edtCurrentPhone.getText().toString().trim();
        String code = edtVerifyCode.getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {
            toast("请输入手机号！");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(mContext, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!InputCheckUtil.checkPhoneNum(phone)) {
            toast("请输入正确的手机号");
            return;
        }

        if (TextUtils.isEmpty(mVerifyCode)) {
            Toast.makeText(mContext, "请先获取验证码", Toast.LENGTH_SHORT).show();
            return;
        }


        getPermissionNum();
        BaseOkHttpClient.newBuilder()
                .addParam("iphone", edtCurrentPhone.getText().toString().trim())
                .addParam("code", code)
                .addParam("control", mPermissionNum)
                .addParam("type", type) //0删除子账号 1修改子账号
                .addParam("userId", mSubUserId)
                .addParam("event", 6)
                .url(NetUrlUtils.SET_SUBACCOUNT_LIST)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
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

    //获取当前权限编号
    private void getPermissionNum() {
        StringBuilder sb = new StringBuilder();
        if (tvStoreManage.isSelected()) {
            sb.append("1");
        }
        if (tvCouponCard.isSelected()) {
            sb.append(StringUtils.isEmpty(sb.toString()) ? "2" : ",2");
        }
        if (tvOrderManage.isSelected()) {
            sb.append(StringUtils.isEmpty(sb.toString()) ? "3" : ",3");
        }
        if (tvUserManage.isSelected()) {
            sb.append(StringUtils.isEmpty(sb.toString()) ? "4" : ",4");
        }
        if (tvDataReport.isSelected()) {
            sb.append(StringUtils.isEmpty(sb.toString()) ? "5" : ",5");
        }
        if (tvWriteOffOrder.isSelected()) {
            sb.append(StringUtils.isEmpty(sb.toString()) ? "6" : ",6");
        }

        mPermissionNum = sb.toString();
    }

    //获取验证码
    private void getVerifyCode() {
        String phone = edtCurrentPhone.getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {
            toast("请输入手机号！");
            return;
        }
        if (!InputCheckUtil.checkPhoneNum(phone)) {
            toast("请输入正确的手机号");
            return;
        }

        btnGetVerifyCode.startTimer();
        BaseOkHttpClient.newBuilder()
                .addParam("mobile", phone)
                .addParam("event", "6")//传1~5  1:注册/短信登录、2:忘记密码、3:修改密码、4:修改支付密码、5:换绑手机
                .url(NetUrlUtils.GET_VERIFY_CODE)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                toast("发送验证码成功");
                mVerifyCode = json;
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
