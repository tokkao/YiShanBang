package com.benben.yishanbang.ui.tea.activity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/8/12 0012
 * Describe:取餐码
 */
public class TakeFoodCodeActivity extends BaseActivity {


    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.iv_Zxing)
    ImageView ivZxing;
    @BindView(R.id.tv_take_food_code)
    TextView tvTakeFoodCode;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    String mFoodCode,mCodeImage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_take_food_code;
    }

    @Override
    protected void initData() {

        //设置标题
        centerTitle.setText("取餐码");
        Intent intent=getIntent();
        mFoodCode=intent.getStringExtra("mFoodCode");
        if (mFoodCode!=null){
            tvTakeFoodCode.setText(mFoodCode);
        }
        mCodeImage=intent.getStringExtra("mCodeImage");
        if (mCodeImage!=null){
            //ImageUtils.loadCover(ivZxing,mCodeImage,mContext);
            ImageUtils.getPic(Constants.IMAGE_BASE_URL +mCodeImage,ivZxing,mContext,R.mipmap.ic_default_pic);
        }

    }
    //返回上一页
    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        finish();
    }
}
