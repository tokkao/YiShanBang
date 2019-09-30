package com.benben.yishanbang.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.ScreenUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.ui.login.LoginCodeActivity;
import com.benben.yishanbang.widget.progressbar.CircleProgressbar;

/**
 * 闪屏页面
 */
public class SplashActivity extends BaseActivity {

    private ImageView mIvSplash;

    private CircleProgressbar mCircleProgressbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        mIvSplash = findViewById(R.id.iv_splash);
        mCircleProgressbar = findViewById(R.id.circle_progress_bar);

        initCircleProgressBar();

        //TODO 获取广告图片地址 和 时长
        String imgPath = "";
        long timeLength = 3000;
        if (!StringUtils.isEmpty(imgPath)) {
            //显示广告图片
            Bitmap imageThumbnail = ImageUtils.getImageThumbnail(imgPath,
                    ScreenUtils.getScreenWidth(mContext), ScreenUtils.getScreenHeight(mContext));
            if (imageThumbnail != null && mIvSplash != null) {
                mIvSplash.setImageBitmap(imageThumbnail);
            }
            //显示倒计时
            mCircleProgressbar.setVisibility(View.VISIBLE);
            mCircleProgressbar.setTimeMillis(timeLength);
            mCircleProgressbar.reStart();
        } else {
            //没有广告直接跳走
            new Handler().postDelayed(this::toMainPager, 2000);
        }
    }

    //跳转首页
    private void toMainPager() {
        if (StringUtils.isEmpty(MyApplication.mPreferenceProvider.getUId()) || StringUtils.isEmpty(MyApplication.mPreferenceProvider.getToken())) {
            Intent intent = new Intent(mContext, LoginCodeActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }

    //初始化圆形进度条
    private void initCircleProgressBar() {
        mCircleProgressbar.setOutLineColor(Color.WHITE);
        mCircleProgressbar.setInCircleColor(Color.parseColor("#505559"));
        mCircleProgressbar.setProgressColor(Color.parseColor("#1BB079"));
        mCircleProgressbar.setProgressLineWidth(4);
        mCircleProgressbar.setProgressType(CircleProgressbar.ProgressType.COUNT);

        //倒计时结束
        mCircleProgressbar.setCountdownProgressListener(1, new CircleProgressbar.OnCountdownProgressListener() {
            @Override
            public void onProgress(int what, int progress) {
                if (what == 1 && progress == 100) {
                    toMainPager();
                }
            }
        });

        //跳过
        mCircleProgressbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCircleProgressbar.stop();
                toMainPager();
            }
        });
    }


}
