package com.benben.yishanbang.ui.mine.activity;


import android.widget.TextView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.ui.NormalWebViewActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 关于我们
 */
public class AboutUsActivity extends BaseActivity {


    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.tv_intro)
    TextView tvIntro;
    @BindView(R.id.tv_intro_content)
    TextView tvIntroContent;
    @BindView(R.id.tv_copyright)
    TextView tvCopyright;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initData() {
        centerTitle.setText("关于我们");

       // startWithData(mContext,"","关于我们",true,true);
    }

    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        onBackPressed();
    }

}
