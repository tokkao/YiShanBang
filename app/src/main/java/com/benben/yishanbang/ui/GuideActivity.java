package com.benben.yishanbang.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;


import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.GuideAdapter;
import com.benben.yishanbang.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 */
public class GuideActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private GuideAdapter mGuideAdapter;
    private List<Integer> mImgResIds = new ArrayList<>();

    private TextView tvSkip;
    private TextView tvEnter;

    private ImageView iv1, iv2, iv3;

    protected void initEvent() {
        tvSkip.setOnClickListener(this);
        tvEnter.setOnClickListener(this);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    tvEnter.setVisibility(View.VISIBLE);
                } else {
                    tvEnter.setVisibility(View.INVISIBLE);
                }
                if (position == 0) {
                    iv1.setImageResource(R.drawable.dot_selected);
                    iv2.setImageResource(R.drawable.dot_normal);
                    iv3.setImageResource(R.drawable.dot_normal);
                } else if (position == 1) {
                    iv1.setImageResource(R.drawable.dot_normal);
                    iv2.setImageResource(R.drawable.dot_selected);
                    iv3.setImageResource(R.drawable.dot_normal);
                } else if (position == 2) {
                    iv1.setImageResource(R.drawable.dot_normal);
                    iv2.setImageResource(R.drawable.dot_normal);
                    iv3.setImageResource(R.drawable.dot_selected);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_skip:
            case R.id.tv_enter:
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void initData() {
        mViewPager = findViewById(R.id.view_guid);
        tvSkip = findViewById(R.id.tv_skip);
        tvEnter =  findViewById(R.id.tv_enter);
        iv1 =  findViewById(R.id.iv1);
        iv2 =  findViewById(R.id.iv2);
        iv3 =  findViewById(R.id.iv3);

        mImgResIds.add(R.mipmap.img_start_page);
        mImgResIds.add(R.mipmap.img_start_page);
        mImgResIds.add(R.mipmap.img_start_page);
        mGuideAdapter = new GuideAdapter(mContext, mImgResIds);
        mViewPager.setAdapter(mGuideAdapter);

        initEvent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }
}
