package com.benben.yishanbang.ui.mine.activity;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.discount.adapter.GoodsPhotoAdapter;
import com.benben.yishanbang.ui.mine.bean.WindowManageGoodsListBean;
import com.benben.yishanbang.widget.CustomImageViewFive;
import com.benben.yishanbang.widget.CustomRecyclerView;
import com.kongzue.dialog.v3.CustomDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/6
 * Time: 14:33
 * 橱窗管理的商品详情
 */
public class WindowManageGoodsDetailActivity extends BaseActivity {
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.iv_img)
    CustomImageViewFive ivImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.rv_photo)
    CustomRecyclerView rvPhoto;
    @BindView(R.id.tv_option)
    TextView tvOption;
    @BindView(R.id.tv_commission)
    TextView tvCommission;
    @BindView(R.id.tv_sale_num_with_postage)
    TextView tvSaleNumWithPostage;

    private GoodsPhotoAdapter mPhotoAdapter;//图片列表集合适配器
    private WindowManageGoodsListBean mGoodBean;//商品id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_window_manage_goods_detail;
    }

    @Override
    protected void initData() {
        initTitle("商品详情");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvPhoto.setLayoutManager(linearLayoutManager);
        rvPhoto.setFocusable(false);

        Drawable drawable = getResources().getDrawable(R.mipmap.icon_goods_details_share);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        rightTitle.setCompoundDrawables(null, null, drawable, null);

        mPhotoAdapter = new GoodsPhotoAdapter(mContext);
        rvPhoto.setAdapter(mPhotoAdapter);

        String add_or_del = getIntent().getStringExtra("add_or_del");
        mGoodBean = (WindowManageGoodsListBean) getIntent().getSerializableExtra("goods");

        tvOption.setText(add_or_del.equals("del") ? "删除" : "加入我的商品橱窗");

        initWindowDetailView();
//        getWindowDetail();
    }

    @OnClick({R.id.right_title, R.id.tv_option})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.right_title://分享
                break;
            case R.id.tv_option://删除
                addOrDel();
                break;
        }
    }

    private void addOrDel() {
        if (mGoodBean == null) {
            toast("商品已下架");
            finish();
            return;
        }
        String add_or_del = getIntent().getStringExtra("add_or_del");
        if (add_or_del.equals("del")) {
            delWindow();
        } else {
            addWindow();
        }
    }

    /**
     * 初始化页面信息
     */
    private void initWindowDetailView() {
        if (mGoodBean == null) {
            return;
        }
        tvName.setText(mGoodBean.getName());
        tvPrice.setText("¥ " + mGoodBean.getPrice());
        tvCommission.setText("佣金: ¥ " + mGoodBean.getCommission());
        tvSaleNumWithPostage.setText(mGoodBean.getCount() + "已售       是否包邮: " + (mGoodBean.getDelivery().equals("1") ? "是" : "否"));//560已售  是否包邮：否
        tvContent.setText(mGoodBean.getMegssage());
        ImageUtils.getPic(Constants.IMAGE_BASE_URL + mGoodBean.getImgUrl(), ivImg, mContext);

        List<String> list = new ArrayList<>();
        if (!StringUtils.isEmpty(mGoodBean.getBottomImgs())) {
            String[] split = mGoodBean.getBottomImgs().split(",");
            for (int i = 0; i < split.length; i++) {
                if (!StringUtils.isEmpty(split[i])) {
                    list.add(split[i]);
                }
            }
        }
        mPhotoAdapter.setList(list);
    }

    /**
     * 获取商品详细
     */
    private void getWindowDetail() {
        if (mGoodBean == null) {
            toast("商品已下架");
            finish();
            return;
        }
        BaseOkHttpClient.newBuilder()
                .addParam("id", mGoodBean.getId())
                .url(NetUrlUtils.WINDOW_MY_GOODS_DETAIL)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {

            }

            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    /**
     * 添加商品
     */
    private void addWindow() {
        BaseOkHttpClient.newBuilder()
                .addParam("goodsId", mGoodBean.getId())
                .url(NetUrlUtils.WINDOW_MY_GOODS_ADD)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                toast("添加成功");
                WindowManageGoodsDetailActivity.this.finish();
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

    /**
     * 删除商品
     */
    private void delWindow() {
        CustomDialog.show((AppCompatActivity) mContext, R.layout.dialog_confirm_window, new CustomDialog.OnBindView() {
            @Override
            public void onBind(final CustomDialog dialog, View v) {
                TextView tv_dialog_one_msg = v.findViewById(R.id.tv_dialog_one_msg);
                tv_dialog_one_msg.setText("确定删除商品吗");
                TextView tv_right_ok = v.findViewById(R.id.tv_right_ok);
                TextView tv_left_cancel = v.findViewById(R.id.tv_left_cancel);

                //确定
                tv_right_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BaseOkHttpClient.newBuilder()
                                .addParam("goodsId", mGoodBean.getId())
                                .url(NetUrlUtils.WINDOW_MY_GOODS_DEL)
                                .post()
                                .build().enqueue(mContext, new BaseCallBack<String>() {
                            @Override
                            public void onSuccess(String s, String msg) {
                                toast("删除成功");
                                WindowManageGoodsDetailActivity.this.finish();
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
                });
                //取消
                tv_left_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.doDismiss();
                    }
                });
            }
        });
    }

}
