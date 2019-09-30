package com.benben.yishanbang.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.NormalWebViewConfig;
import com.benben.yishanbang.widget.TopProgressWebView;

import butterknife.BindView;
import butterknife.OnClick;

public class NormalWebViewActivity extends BaseActivity {

    /**
     * WebView 启动
     * @param context
     * @param url         地址
     * @param title       标题
     * @param isShowTitle 是否显示标题
     * @param isHtmlText  是否是html文本
     */
    public static void startWithData(Context context, String url, String title,
                                     boolean isShowTitle, boolean isHtmlText) {
        Intent starter = new Intent(context, NormalWebViewActivity.class);
        starter.putExtra(NormalWebViewConfig.URL, url);
        starter.putExtra(NormalWebViewConfig.TITLE, title);
        starter.putExtra(NormalWebViewConfig.IS_SHOW_TITLE, isShowTitle);
        starter.putExtra(NormalWebViewConfig.IS_HTML_TEXT, isHtmlText);
        context.startActivity(starter);
    }

    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.webVi_message_details)
    TopProgressWebView webViMessageDetails;
    @BindView(R.id.view_title)
    View view_title;
    private String strTitle;

    private String strUrl;
    //是否显示title
    private boolean isShowTitle = true;
    private boolean isHtmlText = false;

    @OnClick(R.id.rl_back)
    public void onClick() {
        finish();
    }

    @Override
    protected void initData() {
        strUrl = getIntent().getStringExtra(NormalWebViewConfig.URL);
        strTitle = getIntent().getStringExtra(NormalWebViewConfig.TITLE);
        isShowTitle = getIntent().getBooleanExtra(NormalWebViewConfig.IS_SHOW_TITLE,true);
        isHtmlText = getIntent().getBooleanExtra(NormalWebViewConfig.IS_HTML_TEXT,false);

        if(isShowTitle){
            view_title.setVisibility(View.VISIBLE);
            centerTitle.setText(strTitle+"");
        }else{
            view_title.setVisibility(View.GONE);
        }
        if (!isHtmlText) {
            webViMessageDetails.loadUrl(strUrl);
        } else {
            webViMessageDetails.loadTextContent(strUrl);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_normal_webview;
    }
}

