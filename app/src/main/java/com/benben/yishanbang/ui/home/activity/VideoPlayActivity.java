package com.benben.yishanbang.ui.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.benben.commoncore.utils.LogUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.discount.activity.BusinessDetailActivity;
import com.superplayer.library.SuperPlayer;
import com.superplayer.library.utils.VideoLogoUtils;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/8
 * Time: 10:10
 * 视频播放
 */
public class VideoPlayActivity extends BaseActivity implements SuperPlayer.OnNetChangeListener {
    @BindView(R.id.super_player)
    SuperPlayer player;

    private ImageView ivBack;//返回点击事件
    private VideoLogoUtils ivLogo;//logo点击事件
    private TextView tvTitle;//视频标题

    private String mVideoUrl = "";
    private String mShopId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void initData() {
        mShopId = getIntent().getStringExtra("shop_id");
        mVideoUrl = getIntent().getStringExtra("video");
        LogUtils.e("video===========","mVideoUrl******************"+mVideoUrl);
        String title = getIntent().getStringExtra("title");

        ivBack = player.findViewById(R.id.app_video_finish);
        ivLogo = player.findViewById(R.id.iv_logo);
        tvTitle = player.findViewById(R.id.app_video_title);
        tvTitle.setText(title);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ivLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(StringUtils.isEmpty(mShopId)) return;
                Bundle bundle = new Bundle();
                bundle.putString("store_id",mShopId);
                MyApplication.openActivity(mContext, BusinessDetailActivity.class, bundle);
            }
        });
        //初始化播放器
        initPlayer();

        player.play("" + mVideoUrl);

        // getShopInfo();
    }

    //获取店铺信息
    private void getShopInfo() {
        BaseOkHttpClient.newBuilder()
                .addParam("id", mShopId)
                .url(NetUrlUtils.VIDEO_GET_SHOP_INFO)
                .get()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {

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

    /**
     * 初始化播放器
     */
    private void initPlayer() {
        player.setNetChangeListener(false)//设置监听手机网络的变化
                .onPrepared(new SuperPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared() {
                        /**
                         * 监听视频是否已经准备完成开始播放。（可以在这里处理视频封面的显示跟隐藏）
                         */
                    }
                }).onComplete(new Runnable() {
            @Override
            public void run() {
                /**
                 * 监听视频是否已经播放完成了。（可以在这里处理视频播放完成进行的操作）
                 */
//                Toast.makeText(VideoDetailActivity.this, "视频播放完成", Toast.LENGTH_SHORT).show();
            }
        }).onInfo(new SuperPlayer.OnInfoListener() {
            @Override
            public void onInfo(int what, int extra) {
                /**
                 *
                 * 监听视频的相关信息。
                 */
            }
        }).onError(new SuperPlayer.OnErrorListener() {
            @Override
            public void onError(int what, int extra) {
                /**
                 * 监听视频播放失败的回调
                 */
            }
        });
        //开始播放视频
        player.setScaleType(SuperPlayer.SCALETYPE_FITXY);
        player.setPlayerWH(MyApplication.getWidth(), (int) (MyApplication.getWidth() * 0.7));//设置竖屏的时候屏幕的高度，如果不设置会切换后按照16:9的高度重置
    }

    @Override
    public void onWifi() {

    }

    @Override
    public void onMobile() {

    }

    @Override
    public void onDisConnect() {

    }

    @Override
    public void onNoAvailable() {

    }

    /**
     * 下面的这几个Activity的生命状态很重要
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected int getStatusBarColor() {
        return R.color.transparent;
    }
}
