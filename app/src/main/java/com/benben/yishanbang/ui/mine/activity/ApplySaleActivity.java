package com.benben.yishanbang.ui.mine.activity;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.GridImageListAdapter;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.utils.FullyGridLayoutManager;
import com.benben.yishanbang.widget.CustomRecyclerView;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.benben.yishanbang.config.Constants.DATA_KEY;

/**
 * 申请售后
 */
public class ApplySaleActivity extends BaseActivity {


    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.rlv_goods_list)
    RecyclerView rlvGoodsList;
    @BindView(R.id.tv_goods_count)
    TextView tvGoodsCount;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.edt_reason)
    EditText edtReason;
    @BindView(R.id.rlv_image)
    CustomRecyclerView rlvImage;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    private GridImageListAdapter mImageAdapter;
    private List<LocalMedia> mImageList;

    private String mOrderNo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_sale;
    }

    @Override
    protected void initData() {
        centerTitle.setText("申请售后");

        mOrderNo = getIntent().getStringExtra(DATA_KEY);

        rlvGoodsList.setLayoutManager(new LinearLayoutManager(mContext));
        //  rlvGoodsList.setAdapter();
        FullyGridLayoutManager fullyGridLayoutManager = new FullyGridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        rlvImage.setLayoutManager(fullyGridLayoutManager);
        rlvImage.canScrollVertically(0);
        mImageAdapter = new GridImageListAdapter(this, onAddPicClickListener);
        rlvImage.setAdapter(mImageAdapter);

    }


    /**
     * 删除图片回调接口
     */
    private GridImageListAdapter.onAddPicClickListener onAddPicClickListener = (type, position) -> {
        switch (type) {
            case 0:
                SelectPicture(PictureSelector.create(mContext).openGallery(PictureMimeType.ofImage()));
                break;
            case 1:
                // 删除图片
                mImageList.remove(position);
                mImageAdapter.notifyItemRemoved(position);
                break;
        }
    };


    private void SelectPicture(PictureSelectionModel pictureSelectionModel) {
        // 进入相册 以下是例子：用不到的api可以不写
        //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()

//                .theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
        pictureSelectionModel.maxSelectNum(9)// 最大图片选择数量 int
//                .minSelectNum()// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(false)// 是否可预览图片 true or false
//                .previewVideo(false)// 是否可预览视频 true or false
//                .enablePreviewAudio() // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
//                .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
//                .compressSavePath(getPath())//压缩图片保存地址
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                .selectionMedia(mImageList)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//                .cropCompressQuality()// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(false) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(false)// 裁剪是否可放大缩小图片 true or false
//                .videoQuality()// 视频录制质量 0 or 1 int
//                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
//                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
//                .recordVideoSecond()//视频秒数录制 默认60s int
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


    @OnClick({R.id.rl_back, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.btn_confirm:
                orderServer();
                break;
        }
    }

    /**
     * 申请售后
     */
    private void orderServer() {
        String imgurl = "";
        if (mImageAdapter != null && mImageAdapter.getList() != null) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mImageAdapter.getList().size(); i++) {
                sb.append(mImageAdapter.getList().get(i).getCompressPath());
                sb.append(",");
            }
            imgurl = sb.toString();
        }

        BaseOkHttpClient.newBuilder()
                .addParam("mag", edtReason.getText().toString().trim())
                .addParam("imgurl", imgurl)
                .addParam("orderNo", mOrderNo)
                .url(NetUrlUtils.SHOPPING_ORDER_SERVER)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                toast("申请成功");
                finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    mImageList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    if (mImageList != null && mImageList.size() > 0) {
                        mImageAdapter.setList(mImageList);
                        mImageAdapter.notifyDataSetChanged();
                    }

                    break;
            }
        }
    }
}
