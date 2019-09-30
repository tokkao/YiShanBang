package com.benben.yishanbang.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.hedgehog.ratingbar.RatingBar;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Author:zhn
 * Time:2019/8/28 0028 15:07
 */
public class EvaluateStarPopuWindow extends PopupWindow implements View.OnClickListener {

    private Context mContext;

    private View view;
    private String mOrderid;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    private int mAppriseValue = 0;

    private String mApiUrl;

    public EvaluateStarPopuWindow(Context context, String mOrderid) {
        this(context, mOrderid, NetUrlUtils.USER_EVALUATION_ORDER);

    }

    public EvaluateStarPopuWindow(Context context, String mOrderid, String apiUrl) {
        super(context);
        this.mContext = context;
        this.mOrderid = mOrderid;
        this.mApiUrl = apiUrl;

        init();

    }

    private void init() {

        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pop_evaluate_star, null);
        ButterKnife.bind(this, view);

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
        /// 单击屏幕关闭弹窗
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                /*int height = view.findViewById(R.id.llyt_shopping_car_add).getTop();
                int bottom = view.findViewById(R.id.llyt_shopping_car_add).getBottom();
                int y = (int) motionEvent.getY();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height || y > bottom) {
                        dismiss();
                    }
                }
                return true;*/
                dismiss();
                return true;
            }
        });

        btnConfirm.setOnClickListener(this);
        ratingBar.setOnRatingChangeListener(RatingCount -> mAppriseValue = (int) RatingCount);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                getUserEvaluate();
                break;
        }
    }

    //用户评价星级
    private void getUserEvaluate() {
        BaseOkHttpClient.newBuilder()
                .addParam("id", mOrderid)//订单Id
                .addParam("taskId", mOrderid)//订单Id
                .addParam("mark", mAppriseValue + "")//评分
                .url(mApiUrl)
                .post()
                .build().enqueue((Activity) mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                dismiss();
                ToastUtils.show(mContext, msg);

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
