package com.benben.yishanbang.ui.tea.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.benben.commoncore.utils.InputCheckUtil;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.LogUtils;
import com.benben.commoncore.utils.RxBus;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.GridImageListAdapter;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.utils.FullyGridLayoutManager;
import com.benben.yishanbang.widget.CustomRecyclerView;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2019/8/13 0013
 * Describe:发布动态页面
 */
public class AddDynamicActivity extends BaseActivity {

    private static final String TAG = "AddDynamicActivity";
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.edt_content)
    EditText edtContent;
    @BindView(R.id.iv_video_preview)
    ImageView ivVideoPreview;
    @BindView(R.id.rlv_list)
    CustomRecyclerView rlvList;
    @BindView(R.id.tv_video)
    TextView tvVideo;
    @BindView(R.id.tv_image)
    TextView tvImage;
    private GridImageListAdapter mImageAdapter;
    //当前选择的 是否是视频
    private boolean isVideo = true;
    //选中的图片列表
    private List<LocalMedia> mSelectList;
    //图片数组
    private File[] mFiles;
    //图片/视频 id
    private StringBuilder mIds = new StringBuilder();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_dynamic;
    }

    @Override
    protected void initData() {
        rightTitle.setText("发布");
        centerTitle.setText("发布动态");
        //默认选中视频
        tvVideo.setSelected(true);
        rlvList.setLayoutManager(new FullyGridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        rlvList.canScrollVertically(0);
        mImageAdapter = new GridImageListAdapter(this, onAddPicClickListener);
        rlvList.setAdapter(mImageAdapter);
        InputCheckUtil.filterEmoji(edtContent, mContext);
    }

    /**
     * 删除图片回调接口
     */
    private GridImageListAdapter.onAddPicClickListener onAddPicClickListener = (type, position) -> {
        switch (type) {
            case 0:
                initPictureSelector(PictureSelector.create(mContext).openGallery(PictureMimeType.ofImage()));
                break;
            case 1:
                // 删除图片
                if (mSelectList != null) {
                    mSelectList.remove(position);
                    mImageAdapter.notifyItemRemoved(position);
                }
                break;
        }
    };

    //初始化PictureSelector
    private void initPictureSelector(PictureSelectionModel pictureSelectionModel) {
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
                .isCamera(false)// 是否显示拍照按钮 true or false
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
//                .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
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

    //初始化PictureSelector
    private void initVideoSelector(PictureSelectionModel pictureSelectionModel) {
        // 进入相册 以下是例子：用不到的api可以不写
        //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()

//                .theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
        pictureSelectionModel.maxSelectNum(1)// 最大图片选择数量 int
//                .minSelectNum()// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
//                .previewImage(false)// 是否可预览图片 true or false
                .previewVideo(true)// 是否可预览视频 true or false
//                .enablePreviewAudio() // 是否可播放音频 true or false
                .isCamera(false)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
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
//                .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
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

    @OnClick({R.id.rl_back, R.id.right_title, R.id.iv_video_preview, R.id.tv_video, R.id.tv_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.right_title://发布动态
                if (StringUtils.isEmpty(edtContent.getText().toString().trim())) {
                    toast("内容不能为空");
                    return;
                }
                if (isVideo) {
                    uploadVideo();
                } else {
                    uploadImage();
                }
                break;
            case R.id.iv_video_preview://重新选择视频
                initVideoSelector(PictureSelector.create(mContext).openGallery(PictureMimeType.ofVideo()));
                break;
            case R.id.tv_video://选择视频
                if (isVideo) {
                    return;
                }
                isVideo = true;
                tvVideo.setSelected(isVideo);
                tvImage.setSelected(!isVideo);
                ivVideoPreview.setVisibility(View.VISIBLE);
                rlvList.setVisibility(View.GONE);
                // 删除图片
                if (mSelectList != null) {
                    mSelectList.clear();
                    mImageAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.tv_image://选择图片
                if (!isVideo) {
                    return;
                }
                isVideo = false;
                tvVideo.setSelected(isVideo);
                tvImage.setSelected(!isVideo);
                ivVideoPreview.setVisibility(View.GONE);
                rlvList.setVisibility(View.VISIBLE);
                // 删除视频
                if (mSelectList != null) {
                    mSelectList.clear();
                    mImageAdapter.notifyDataSetChanged();
                }
                ivVideoPreview.setImageResource(R.mipmap.icon_add_photo);
                break;
        }
    }

    //上传图片
    private void uploadImage() {
        if (mSelectList == null || mSelectList.size() <= 0) {
            toast("请先选择图片");
            return;
        }
        mFiles = new File[mSelectList.size()];
        BaseOkHttpClient.Builder builder = BaseOkHttpClient.newBuilder();
        for (int i = 0; i < mSelectList.size(); i++) {
            mFiles[i] = new File(mSelectList.get(i).getCompressPath());
            builder.addFile("files", "" + mFiles[i].getName(), mFiles[i]);
        }
        builder.url(NetUrlUtils.UPLOAD_IMAGE_LOCAL)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<String> results = JSONUtils.jsonString2Beans(json, String.class);
                toast(msg);
                if (results == null || results.size() == 0) {
                    toast("上传图片失败！");
                    return;
                }
                //拼接图片id 逗号分隔
                for (int i = 0; i < results.size(); i++) {
                    if (results.size() > 1) {
                        if (i == (results.size() - 1)) {
                            mIds.append(results.get(i));
                        } else {
                            mIds.append(results.get(i)).append(",");
                        }

                    } else {
                        mIds.append(results.get(i));
                    }


                }
                releaseDynamic();
            }


            @Override
            public void onError(int code, String msg) {
                toast(msg);
                LogUtils.e(TAG, msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e(TAG, e.getLocalizedMessage());
            }
        });
    }

    //上传视频
    private void uploadVideo() {
        if (mSelectList == null || mSelectList.size() <= 0) {
            toast("请先选择视频");
            return;
        }
        File file = new File(mSelectList.get(0).getPath());
        BaseOkHttpClient.newBuilder()
                .addFile("file", file.getName(), file)
                .url(NetUrlUtils.UPLOAD_VIDEO)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                mIds.append(json);
                releaseDynamic();
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

    //发布动态
    private void releaseDynamic() {
        BaseOkHttpClient.newBuilder()
                .addParam("type", isVideo ? 1 : 0)//动态类型0为图片1为视频
                .addParam("message", edtContent.getText().toString())
                .addParam("url", mIds.toString())//图片/视频路径
                .url(NetUrlUtils.ADD_USER_DYNAMIC)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
                RxBus.getInstance().post(Constants.REFRESH_USER_DETAILS_INFO);
                finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    if (mSelectList != null && mSelectList.size() > 0) {
                        LogUtils.e(TAG, mSelectList.get(0).getPath());
                        if (isVideo) {
                            Glide.with(mContext).load(new File(mSelectList.get(0).getPath())).into(isVideo ? ivVideoPreview : ivVideoPreview);
                        } else {
                            mImageAdapter.setList(mSelectList);
                            mImageAdapter.notifyDataSetChanged();
                        }
                    }
                    break;
            }
        }
    }
}
