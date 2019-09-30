package com.benben.yishanbang.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.benben.commoncore.utils.LogUtils;
import com.benben.commoncore.utils.RxBus;
import com.benben.commoncore.utils.StatusBarUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.MainViewPagerAdapter;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.base.LazyBaseFragments;
import com.benben.yishanbang.ui.discount.DiscountFragment;
import com.benben.yishanbang.ui.home.HomeFragment;
import com.benben.yishanbang.ui.mine.MineFragment;
import com.benben.yishanbang.ui.service.ServiceFragment;
import com.benben.yishanbang.ui.tea.TeaFragment;
import com.benben.yishanbang.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.rb_main_home)
    RadioButton rbMainHome;
    @BindView(R.id.rb_main_discount)
    RadioButton rbMainDiscount;
    @BindView(R.id.rb_main_tea)
    RadioButton rbMainTea;
    @BindView(R.id.rb_main_service)
    RadioButton rbMainService;
    @BindView(R.id.rb_main_mine)
    RadioButton rbMainMine;
    @BindView(R.id.vp_main)
    NoScrollViewPager vpMain;
    @BindView(R.id.status_bar_view)
    View statusBarView;

    private FragmentManager mFragmentManager;

    private long mPressedTime = 0;//退出程序使用
    private int mStatusBarHeight;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mFragmentManager = getSupportFragmentManager();
        //一期 隐藏技能帮扶
//        rbMainService.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.ACCESS_FINE_LOCATION
                    , Manifest.permission.CALL_PHONE
                    , Manifest.permission.CAMERA
                    , Manifest.permission.READ_LOGS
                    , Manifest.permission.READ_PHONE_STATE
                    , Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.SET_DEBUG_APP
                    , Manifest.permission.SYSTEM_ALERT_WINDOW
                    , Manifest.permission.GET_ACCOUNTS
                    , Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
        RxBus.getInstance().toObservable(String.class)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String cate) {

                        switch (cate) {
                            case "yhksp10000":
                            case "yhksp10001":
                            case "yhksp10002":
                            case "yhksp10003":
                            case "yhksp10004":
                            case "jump_discount_fragment":
                                vpMain.setCurrentItem(1);
                                rbMainDiscount.setChecked(true);
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        ArrayList<LazyBaseFragments> lazyBaseFragments = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        DiscountFragment discountFragment = new DiscountFragment();
        TeaFragment teaFragment = new TeaFragment();
        ServiceFragment serviceFragment = new ServiceFragment();
        MineFragment mineFragment = new MineFragment();
        lazyBaseFragments.add(homeFragment);
        lazyBaseFragments.add(discountFragment);
        lazyBaseFragments.add(teaFragment);
        lazyBaseFragments.add(serviceFragment);
        lazyBaseFragments.add(mineFragment);
        vpMain.setAdapter(new MainViewPagerAdapter(mFragmentManager, lazyBaseFragments));
        //设置状态栏view的高度
        mStatusBarHeight = StatusBarUtils.getStatusBarHeight(mContext);
        statusBarView.getLayoutParams().height = mStatusBarHeight;
    }

    @OnClick({R.id.rb_main_home, R.id.rb_main_discount, R.id.rb_main_tea, R.id.rb_main_service, R.id.rb_main_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_main_home:
                vpMain.setCurrentItem(0);
                statusBarView.getLayoutParams().height = mStatusBarHeight;
                break;
            case R.id.rb_main_discount:
                vpMain.setCurrentItem(1);
                statusBarView.getLayoutParams().height = mStatusBarHeight;
                break;
            case R.id.rb_main_tea:
                vpMain.setCurrentItem(2);
                statusBarView.getLayoutParams().height = mStatusBarHeight;
                break;
            case R.id.rb_main_service:
                vpMain.setCurrentItem(3);
                statusBarView.getLayoutParams().height = mStatusBarHeight;
                break;
            case R.id.rb_main_mine:
                vpMain.setCurrentItem(4);
                statusBarView.getLayoutParams().height = 0;
                break;
        }
    }


    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        } else {//退出程序
            this.finish();
            System.exit(0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    //定位权限授权后 再次请求定位
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtils.e(TAG, Arrays.toString(grantResults) + "*******" + grantResults.length);
        if (requestCode == 123) {
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        RxBus.getInstance().post(123);
                    }
                }
            }
        }
    }

    @Override
    protected int getStatusBarColor() {
        return R.color.transparent;
    }

}
