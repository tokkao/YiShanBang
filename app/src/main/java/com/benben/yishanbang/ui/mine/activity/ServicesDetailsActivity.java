package com.benben.yishanbang.ui.mine.activity;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.pop.EvaluateStarPopuWindow;
import com.benben.yishanbang.ui.mine.adapter.UploadPhotoAdapter;
import com.benben.yishanbang.ui.mine.bean.MyServicesListBean;
import com.benben.yishanbang.widget.CustomRecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hedgehog.ratingbar.RatingBar;
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

import static com.benben.yishanbang.config.Constants.DATA_KEY;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: 服务详情
 */
public class ServicesDetailsActivity extends BaseActivity {

    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.tv_service_name)
    TextView tvServiceName;
    @BindView(R.id.tv_service_type)
    TextView tvServiceType;
    @BindView(R.id.tv_service_time)
    TextView tvServiceTime;
    @BindView(R.id.tv_service_location)
    TextView tvServiceLocation;
    @BindView(R.id.tv_service_remarks)
    TextView tvServiceRemarks;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_mobile)
    TextView tvUserMobile;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.rlv_image)
    CustomRecyclerView rlvImage;
    @BindView(R.id.btn_finish)
    Button btnFinish;
    @BindView(R.id.btn_deny)
    Button btnDeny;
    @BindView(R.id.btn_allow)
    Button btnAllow;
    private int mAppriseValue = 0;

    private MyServicesListBean.OrderServerBean mOrderServerBean;
    private int MAX_NUM = 3;//最多上传数量
    private UploadPhotoAdapter mUploadPhotoAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_services_details;
    }

    @Override
    protected void initData() {
        centerTitle.setText("任务详情");
        ratingBar.setOnRatingChangeListener(RatingCount -> mAppriseValue = (int) RatingCount);
        mOrderServerBean = (MyServicesListBean.OrderServerBean) getIntent().getSerializableExtra(DATA_KEY);
        if (mOrderServerBean != null) {
            refreshUI();
        }
    }

    /**
     * 刷新页面
     */
    private void refreshUI() {

        tvServiceName.setText(mOrderServerBean.getName());
        tvServiceType.setText(mOrderServerBean.getType());
        tvServiceTime.setText(mOrderServerBean.getTime());
        tvServiceLocation.setText(mOrderServerBean.getAddress());
        tvServiceRemarks.setText(mOrderServerBean.getMessage());
        tvMoney.setText("¥ " + mOrderServerBean.getPrice());

        switch (mOrderServerBean.getStatus()) {//状态0已完成1为接单2进行中3未评价4已完成
            case "0":
                break;
            case "1":
                refreshUIOrderBefore();
                break;
            case "2":
                refreshUIOrder();
                break;
            case "3":
                refreshUIOrderComment();
                break;
            case "4":
                refreshUIOrderFinish();
                break;
        }


    }

    /**
     * 未接单
     */
    private void refreshUIOrderBefore() {
        if (mOrderServerBean.getTakeType().equals("2")) {
            btnDeny.setVisibility(View.VISIBLE);
            btnAllow.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 订单进行中
     */
    private void refreshUIOrder() {
        ((View) tvUserName.getParent()).setVisibility(View.VISIBLE);
        ((View) tvUserMobile.getParent()).setVisibility(View.VISIBLE);
        tvUserName.setText(mOrderServerBean.getNickname());
        tvUserMobile.setText(mOrderServerBean.getMobile());
        btnFinish.setVisibility(View.VISIBLE);
        btnFinish.setText("完成");
    }

    /**
     * 订单评价
     */
    private void refreshUIOrderComment() {
        ((View) tvUserName.getParent()).setVisibility(View.VISIBLE);
        ((View) tvUserMobile.getParent()).setVisibility(View.VISIBLE);
        tvUserName.setText(mOrderServerBean.getNickname());
        tvUserMobile.setText(mOrderServerBean.getMobile());
        btnFinish.setVisibility(View.VISIBLE);
        btnFinish.setText("评价");

        ((View) rlvImage.getParent()).setVisibility(View.VISIBLE);
        mUploadPhotoAdapter = new UploadPhotoAdapter(mContext);
        rlvImage.setLayoutManager(new GridLayoutManager(mContext, 3));
        rlvImage.setAdapter(mUploadPhotoAdapter);
        mUploadPhotoAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(View view, int position, String model) {
                if (position == mUploadPhotoAdapter.getItemCount() - 1) {
                    if (mUploadPhotoAdapter.getList().size() >= MAX_NUM) {
                        toast("已达到图片上限");
                    } else {
                        showSelectAvatarDialog();
                    }
                } else {

                }
            }

            @Override
            public void onItemLongClick(View view, int position, String model) {

            }
        });
    }

    /**
     * 订单完结
     */
    private void refreshUIOrderFinish() {
        ((View) tvUserName.getParent()).setVisibility(View.VISIBLE);
        ((View) tvUserMobile.getParent()).setVisibility(View.VISIBLE);
        ((View) ratingBar.getParent()).setVisibility(View.VISIBLE);
        tvUserName.setText(mOrderServerBean.getNickname());
        tvUserMobile.setText(mOrderServerBean.getMobile());
        float markId = 0;
        try {
            markId = Float.parseFloat(mOrderServerBean.getMark_id());
        } catch (Exception e) {
            markId = 0;
        }
        ratingBar.setStar(markId);
    }


    @OnClick({R.id.rl_back, R.id.btn_finish, R.id.btn_deny, R.id.btn_allow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back://返回
                onBackPressed();
                break;
            case R.id.btn_finish://完成
                if (btnFinish.getText().toString().trim().equals("完成")) {
                    orderFinish();
                } else {
                    orderComment();
                }
                break;
            case R.id.btn_deny://拒绝
                orderDeny();
                break;
            case R.id.btn_allow://接单
                orderAllow();
                break;
        }
    }

    /**
     * 同意接单
     */
    private void orderAllow() {
        BaseOkHttpClient.newBuilder()
                .addParam("id", mOrderServerBean.getId())
                .url(NetUrlUtils.SERVER_ORDER_ALLOW)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                toast(msg);
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
     * 拒绝订单
     */
    private void orderDeny() {
        BaseOkHttpClient.newBuilder()
                .addParam("id", mOrderServerBean.getId())
                .url(NetUrlUtils.SERVER_ORDER_DENY)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                toast(msg);
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
     * 完成订单
     */
    private void orderFinish() {
        BaseOkHttpClient.newBuilder()
                .addParam("id", mOrderServerBean.getId())
                .url(NetUrlUtils.SERVER_ORDER_FINISH)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                toast(msg);
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
     * 评价订单
     */
    private void orderComment() {
        if (mOrderServerBean.getTakeType().equals("2")) {
            EvaluateStarPopuWindow mEvaluateStarPopuWindow = new EvaluateStarPopuWindow(mContext, mOrderServerBean.getId(), NetUrlUtils.SERVER_ORDER_COMMENT);
            mEvaluateStarPopuWindow.showAtLocation(btnFinish, Gravity.CENTER, 0, 0);
        } else {

            StringBuffer sb = new StringBuffer();
            String imgArr = "";//图片
            if (mUploadPhotoAdapter.getList() != null) {
                for (int i = 0; i < mUploadPhotoAdapter.getList().size(); i++) {
                    sb.append(mUploadPhotoAdapter.getItem(i));
                    sb.append(",");
                }
                imgArr = sb.toString();
            }

            BaseOkHttpClient.newBuilder()
                    .addParam("id", mOrderServerBean.getId())
                    .addParam("taskId", mOrderServerBean.getId())
                    .addParam("mark", mAppriseValue)
                    .addParam("imgUrl", imgArr)
                    .url(NetUrlUtils.SERVER_ORDER_COMMENT)
                    .post()
                    .build().enqueue(mContext, new BaseCallBack<String>() {
                @Override
                public void onSuccess(String s, String msg) {
                    toast(msg);
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
    }

    private void showSelectAvatarDialog() {
        //选择头像dialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
        View view = View.inflate(mContext, R.layout.dialog_select_avatar, null);
        view.findViewById(R.id.tv_last_avatar).setVisibility(View.GONE);
        TextView tvTakePhoto = view.findViewById(R.id.tv_take_photo);
        TextView tvSelectPhoto = view.findViewById(R.id.tv_select_photo);
        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
        //拍照
        tvTakePhoto.setOnClickListener(view1 -> {
            bottomSheetDialog.dismiss();

            selectPicture(PictureSelector.create(mContext)
                    .openCamera(PictureMimeType.ofImage()), 1001);

        });
        //相册
        tvSelectPhoto.setOnClickListener(view1 -> {
            bottomSheetDialog.dismiss();
            selectPicture(PictureSelector.create(mContext)
                    .openGallery(PictureMimeType.ofImage()), 1001);

        });
        //取消
        tvCancel.setOnClickListener(view1 -> {
            bottomSheetDialog.dismiss();

        });
    }

    /*
     *图库，拍照
     */
    private void selectPicture(PictureSelectionModel pictureSelectionModel, int requestCode) {
        // 进入相册 以下是例子：用不到的api可以不写
        //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                .theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
        int maxCount = MAX_NUM;
        if (mUploadPhotoAdapter != null && mUploadPhotoAdapter.getList() != null) {
            maxCount -= mUploadPhotoAdapter.getList().size();
        }
        pictureSelectionModel.maxSelectNum(maxCount)// 最大图片选择数量 int
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
                .forResult(requestCode);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            showViewAndUpLoad(data);
        }
    }

    private List<LocalMedia> mSelectList;

    private void showViewAndUpLoad(Intent data) {
        // 图片、视频、音频选择结果回调
        mSelectList = PictureSelector.obtainMultipleResult(data);
        // 例如 LocalMedia 里面返回三种path
        // 1.media.getPath(); 为原图path
        // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
        // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的

        if (mSelectList != null && mSelectList.size() > 0) {
            for (int i = 0; i < mSelectList.size(); i++) {
                File file = new File(mSelectList.get(i).getCompressPath());
                uploadImage(file);
            }
        }
    }

    private void uploadImage(File file) {
        /**
         * 把当前选择的图片文件上传到服务器
         *
         */
        BaseOkHttpClient.newBuilder()
                .addFile("files", file.getName(), file)
                .url(NetUrlUtils.UPLOAD_IMAGE_LOCAL)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<String> results = JSONUtils.jsonString2Beans(json, String.class);
                if (results == null || results.size() == 0) {
                    toast("上传图片失败！");
                    return;
                }
                toast(msg);
                if (mUploadPhotoAdapter.getList().size() >= MAX_NUM) {
                    toast("已达到图片上限");
                } else {
                    mUploadPhotoAdapter.appendToList(results);
                }
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

}
