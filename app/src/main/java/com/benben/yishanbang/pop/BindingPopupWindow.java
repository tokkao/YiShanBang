package com.benben.yishanbang.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.benben.commoncore.utils.InputCheckUtil;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.widget.VerifyCodeButton;

import java.io.IOException;

import okhttp3.Call;

/**
 * 中间弹出
 * 绑定手机号
 */

public class BindingPopupWindow extends PopupWindow {

    private View view;
    private Activity mContext;
    private OnBindingCallback mOnBindingCallback;

    private TextView tvCancel;
    private TextView tvSubmit;
    private TextView tvTitle;
    private TextView tvContent;
    private EditText edtPhone;
    private EditText edtCode;
    private String mVerifyCode;
    private VerifyCodeButton tvCode;

    public BindingPopupWindow(Activity activity, OnBindingCallback mOnBindingCallback) {
        super(activity);
        this.mOnBindingCallback = mOnBindingCallback;
        this.mContext = activity;
        init();
    }

    private void init() {
        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pop_binding, null);
        tvCancel = view.findViewById(R.id.tv_cancel);
        tvSubmit = view.findViewById(R.id.tv_submit);
        tvTitle = view.findViewById(R.id.tv_title);
        tvCode = view.findViewById(R.id.tv_code);
        edtPhone = view.findViewById(R.id.edt_phone);
        edtCode = view.findViewById(R.id.edt_code);

        //取消
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mOnBindingCallback != null) {
                    mOnBindingCallback.cancel();
                }
            }
        });

        //确定
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(edtPhone.getText().toString().trim())) {
                    ToastUtils.show(mContext, "请输入手机号");
                    return;
                }
                if (!InputCheckUtil.checkPhoneNum(edtPhone.getText().toString().trim())) {
                    ToastUtils.show(mContext, "请输入正确的手机号");
                    return;
                }
                if (StringUtils.isEmpty(edtCode.getText().toString().trim())) {
                    ToastUtils.show(mContext, "请输入验证码");
                    return;
                }
                if (StringUtils.isEmpty(mVerifyCode)) {
                    ToastUtils.show(mContext, "请先获取验证码");
                    return;
                }
                dismiss();
                if (mOnBindingCallback != null) {
                    mOnBindingCallback.submit();
                }
            }
        });
        tvCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
        // 导入布局
        this.setContentView(view);
        // 设置动画效果
        setAnimationStyle(R.style.popwindow_anim_style);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        // 设置可触
        setFocusable(true);
        final ColorDrawable dw = new ColorDrawable(0x0000000);
        setBackgroundDrawable(dw);
        // 单击屏幕关闭弹窗
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int height = view.findViewById(R.id.ll_pop).getTop();
                int bottom = view.findViewById(R.id.ll_pop).getBottom();
                int y = (int) motionEvent.getY();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height || y > bottom) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    /**
     * 设置两个按钮的文字
     *
     * @param firstName
     * @param secondName
     */
    public void setButtonName(String firstName, String secondName) {
        tvCancel.setText("" + firstName);
        tvSubmit.setText("" + secondName);
    }


    public String getPhoneNum() {
        return edtPhone.getText().toString().trim();
    }

    public String getVerifyCode() {
        return edtCode.getText().toString().trim();
    }

    /**
     * 设置标题和内容的描述
     *
     * @param title
     * @param content
     */
    public void setTitle(String title, String content) {
        tvTitle.setText("" + title);
        tvContent.setText("" + content);
    }


    public interface OnBindingCallback {
        //取消
        void cancel();

        //确定
        void submit();
    }

    /**
     * 发短信
     */
    private void sendMessage() {
        String phone = edtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(mContext, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!InputCheckUtil.checkPhoneNum(phone)) {
            Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        tvCode.startTimer();

        BaseOkHttpClient.newBuilder()
                .addParam("mobile", phone)
                .addParam("event", 7)//传1~5  1:注册/短信登录、2:忘记密码、3:修改密码、4:修改支付密码、5:换绑手机
                .url(NetUrlUtils.GET_VERIFY_CODE)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtils.show(mContext, "发送验证码成功");
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
