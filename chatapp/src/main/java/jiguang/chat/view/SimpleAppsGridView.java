package jiguang.chat.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import jiguang.chat.R;
import jiguang.chat.adapter.AppsAdapter;
import jiguang.chat.application.JGApplication;
import jiguang.chat.model.AppBean;
import jiguang.chat.utils.event.ImageEvent;

public class SimpleAppsGridView extends RelativeLayout implements View.OnClickListener {

    protected View view;
    private LinearLayout llytGift;
    private GridView gv_apps;
    private Activity mActivity;
    private EventBus mEventBus;

    public SimpleAppsGridView(Context context) {
        this(context, null);
        mActivity = (Activity) context;
    }

    public SimpleAppsGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.view_apps, this);
        init();
    }

    protected void init() {
        mEventBus = EventBus.getDefault();
        mEventBus.register(this);
        gv_apps = (GridView) view.findViewById(R.id.gv_apps);
        llytGift = (LinearLayout) view.findViewById(R.id.llyt_gift);
        ImageView ivCake = (ImageView) view.findViewById(R.id.iv_cake);
        ImageView ivFlower = (ImageView) view.findViewById(R.id.iv_flower);
        ImageView ivBear = (ImageView) view.findViewById(R.id.iv_bear);
        ImageView ivRing = (ImageView) view.findViewById(R.id.iv_ring);
        ArrayList<AppBean> mAppBeanList = new ArrayList<>();
        mAppBeanList.add(new AppBean(R.mipmap.ic_photo, "图片"));
        //mAppBeanList.add(new AppBean(R.mipmap.icon_camera, "拍摄"));
        // mAppBeanList.add(new AppBean(R.mipmap.icon_file, "文件"));
        // mAppBeanList.add(new AppBean(R.mipmap.icon_loaction, "位置"));
        // mAppBeanList.add(new AppBean(R.mipmap.businesscard, "名片"));
        mAppBeanList.add(new AppBean(R.mipmap.ic_video, "视频"));
        mAppBeanList.add(new AppBean(R.mipmap.ic_gift, "礼物"));
//        mAppBeanList.add(new AppBean(R.mipmap.icon_voice, "语音"));
        AppsAdapter adapter = new AppsAdapter(getContext(), mAppBeanList);
        gv_apps.setAdapter(adapter);

        ivCake.setOnClickListener(this);
        ivFlower.setOnClickListener(this);
        ivBear.setOnClickListener(this);
        ivRing.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.iv_cake) {
            mEventBus.post(new ImageEvent(JGApplication.BUSINESS_CAKE));
        } else if (i == R.id.iv_flower) {
            mEventBus.post(new ImageEvent(JGApplication.BUSINESS_FLOWER));
        } else if (i == R.id.iv_bear) {
            mEventBus.post(new ImageEvent(JGApplication.BUSINESS_BEAR));
        } else if (i == R.id.iv_ring) {
            mEventBus.post(new ImageEvent(JGApplication.BUSINESS_RING));
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ImageEvent event) {
        switch (event.getFlag()) {
            case JGApplication.BUSINESS_GIFT://礼物
                llytGift.setVisibility(VISIBLE);
                gv_apps.setVisibility(GONE);
                break;
            default:
                break;
        }

    }

}
