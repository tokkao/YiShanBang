package com.benben.yishanbang.pop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alipay.sdk.app.PayTask;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.ui.mine.activity.AddressManageActivity;
import com.benben.yishanbang.utils.PayListenerUtils;
import com.benben.yishanbang.utils.PayResultListener;
import com.kongzue.dialog.v3.CustomDialog;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.util.Map;

/**
 * 底部弹出
 * 支付弹窗
 */

public class PayPopupWindow extends PopupWindow {

    private View view;
    private Activity mContext;
    private OnPayCallback mOnPayCallback;

    private TextView tvType;
    private TextView tvPrice;
    private TextView tvAlipay;
    private TextView tvWxpay;
    private ImageView ivCancel;
    private String mUnpaidOrderId, mShopId;
    private TextView tvTitle;
    private TextView tvAddress;

    public PayPopupWindow(Activity activity, OnPayCallback mOnPayCallback) {
        super(activity);
        this.mOnPayCallback = mOnPayCallback;
        this.mContext = activity;
        init();
    }

    /* @Override
     public void showAtLocation(View parent, int gravity, int x, int y) {
         super.showAtLocation(parent, gravity, x, y);

         View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_input, null);
         TextView textView = view.findViewById(R.id.tv_title);
         textView.setText("确认支付");
         EditText editView = view.findViewById(R.id.et_receive_phone);
         editView.setHint("请输入支付密码");
         editView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);

         if (mContext instanceof AppCompatActivity) {
             CustomDialog.show((AppCompatActivity) mContext, view, new CustomDialog.OnBindView() {
                 @Override
                 public void onBind(final CustomDialog dialog, View v) {
                     TextView tv_dialog_ok = v.findViewById(R.id.tv_dialog_ok);
                     TextView tv_dialog_cancel = v.findViewById(R.id.tv_dialog_cancel);
                     //确定
                     tv_dialog_ok.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {

                             String nickname = editView.getText().toString().trim();
 //                        if (!StringUtils.isEmpty(nickname)) {
 //                            editView.setText("");
 //                            mNickname = nickname;
 //                            updateUserInfo();
 //                            tvNickName.setText(nickname);
 //                        }
                             dialog.doDismiss();
                         }
                     });
                     //取消
                     tv_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             dialog.doDismiss();
                             PayPopupWindow.this.dismiss();
                         }
                     });
                 }
             });
         }
     }
 */
    private void init() {
        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pop_pay, null);
        tvType = view.findViewById(R.id.tv_type);
        tvAddress = view.findViewById(R.id.tv_address);
        tvPrice = view.findViewById(R.id.tv_price);
        tvWxpay = view.findViewById(R.id.tv_wxpay);
        tvAlipay = view.findViewById(R.id.tv_alipay);
        tvTitle = view.findViewById(R.id.tv_title);
        ivCancel = view.findViewById(R.id.iv_cancel);

        //微信支付的回调
        PayListenerUtils.getInstance(mContext).addListener(new PayResultListener() {
            @Override
            public void onPaySuccess() {
                //成功
                ToastUtils.show(mContext, "支付成功");
            }

            @Override
            public void onPayError() {
                //失败
            }

            @Override
            public void onPayCancel() {
                //取消
            }
        });

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //支付宝支付
        tvAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mOnPayCallback != null) {
                    mOnPayCallback.alipay();
                }
            }
        });

        //微信支付
        tvWxpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mOnPayCallback != null) {
                    mOnPayCallback.wxpay();
                }
            }
        });
        //选择地址
        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivityForResult(new Intent(mContext, AddressManageActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 1), 100);
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
     * 购买优惠卡的类型和价格
     */
    public void setTypePrice(String type, String price) {
        tvType.setText("" + type);
        tvPrice.setText("" + price);
    }

    /**
     * 显示收货地址
     */
    public void setAddressVisible(boolean b) {
        tvAddress.setVisibility(View.VISIBLE);
    }

    /**
     * 显示收货地址
     */
    public void setAddress(String address) {
        tvAddress.setText(address);
    }

    /**
     * 标题
     */
    public void setTitle(String title) {
        tvTitle.setText("" + title);
    }


    public interface OnPayCallback {
        //支付宝支付
        void alipay();

        //微信支付
        void wxpay();
    }

    IWXAPI api;
//
//    /**
//     * 微信支付
//     */
//    private void wxpay(WXPayBean bean) {
//
//        api = WXAPIFactory.createWXAPI(mContext, null);
//        api.registerApp(Constants.WXKEY);
//
//        PayReq request = new PayReq();
//        request.appId = bean.getAppid();
//        request.partnerId = bean.getPartnerid();
//        request.prepayId = bean.getPrepayid();
//        request.packageValue = bean.getPackageX();
//        request.nonceStr = bean.getNoncestr();
//        request.timeStamp = bean.getTimestamp();
//        request.sign = bean.getSign();
//        api.sendReq(request);
//    }

    /**
     * 支付宝支付
     */
    private void alipay(String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝支付的回调
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Map<String, String> result = (Map<String, String>) msg.obj;

                String resultStatus = result.get("resultStatus");

                if (resultStatus.equals("6001")) {
                    //失败
                } else if (resultStatus.equals("9000")) {
                    //成功
                } else {
                    //其他
                }
            }
        }
    };


}
