package com.benben.yishanbang.ui.service.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.benben.commoncore.utils.InputCheckUtil;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.ScreenUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.discount.bean.CardShopCateBean;
import com.benben.yishanbang.ui.service.bean.ServeTypeInfoBean;
import com.benben.yishanbang.ui.tea.activity.UserDetailsActivity;
import com.benben.yishanbang.utils.ShowListUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: 加入服务
 */
public class JoinServiceActivity extends BaseActivity {

    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.tv_mode)
    TextView tvMode;
    @BindView(R.id.edt_introduction)
    EditText edtIntroduction;
    @BindView(R.id.right_title)
    TextView rightTitle;
    //选择的分类
    private CardShopCateBean mShopCateBean;
    private List<String> mThemeList = new ArrayList<>();//主题类型数据集合
    private List<String> mModeList = new ArrayList<>();//服务方式类型数据集合
    //主题id
    private String mCateId;
    //服务方式
    private String mModeId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_join_service;
    }

    @Override
    protected void initData() {
        centerTitle.setText("加入服务");
        rightTitle.setText("提交");
        InputCheckUtil.filterEmoji(edtIntroduction, mContext);
        //  InputCheckUtil.filterSymbol(edtIntroduction, mContext);
    }


    //提交信息
    private void confirmShopInfo() {
        String introduction = edtIntroduction.getText().toString().trim();
        String mode = tvMode.getText().toString().trim();
        String theme = tvTheme.getText().toString().trim();

        if (StringUtils.isEmpty(theme)) {
            toast("请选择主题");
            return;
        }
        if (StringUtils.isEmpty(mode)) {
            toast("请选择服务方式");
            return;
        }
        if (StringUtils.isEmpty(introduction)) {
            toast("请输入服务介绍");
            return;
        }

        BaseOkHttpClient.newBuilder()
                .addParam("cgId", mCateId)
                .addParam("type", mModeId)
                .addParam("mag", introduction)
                .url(NetUrlUtils.JOIN_SERVICE)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                ScreenUtils.closeKeybord(edtIntroduction, mContext);
                showConfirmSuccessDialog();

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

    //提交成功弹窗
    private void showConfirmSuccessDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = View.inflate(mContext, R.layout.dialog_join_service_confirm_success, null);
        AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tvPositive = view.findViewById(R.id.tv_positive);

        tvPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

    }


    @OnClick({R.id.rl_back, R.id.right_title, R.id.llyt_theme, R.id.llyt_service_mode, R.id.llyt_perfect_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
            case R.id.right_title://提交信息
                confirmShopInfo();
                break;
            case R.id.llyt_theme://主题
                selectTheme();
                break;
            case R.id.llyt_service_mode://服务方式
                selectMode();
                break;
            case R.id.llyt_perfect_info://完善信息
                startActivity(new Intent(mContext, UserDetailsActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, "0"));
                break;
        }
    }

    //选择服务方式
    private void selectMode() {
        mModeList.clear();
        mModeList.add("个人");
        mModeList.add("机构");
        ShowListUtils.show(mContext, "选择服务方式", mModeList, new ShowListUtils.OnSelectItem() {
            @Override
            public void onCallback(String item, int position) {
                mModeId = String.valueOf(position);
                tvMode.setText(item);
            }
        });
    }

    //选择主题
    private void selectTheme() {

        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.SERVE_ALL_CLASS)
                .addParam("token", MyApplication.mPreferenceProvider.getToken())
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                List<ServeTypeInfoBean> list = JSONUtils.jsonString2Beans(s, ServeTypeInfoBean.class);
                mThemeList.clear();
                for (int i = 0; i < list.size(); i++) {
                    mThemeList.add(list.get(i).getName());
                }
                ShowListUtils.show(mContext, "选择主题", mThemeList, new ShowListUtils.OnSelectItem() {
                    @Override
                    public void onCallback(String item, int position) {
                        mCateId = list.get(position).getId();
                        tvTheme.setText(item);
                    }
                });
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
