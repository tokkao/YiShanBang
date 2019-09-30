package com.benben.yishanbang.ui.mine.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.mine.adapter.UploadPhotoAdapter;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: 添加技能
 */
public class AddSkillActivity extends BaseActivity {

    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.tv_skill_level)
    TextView tvSkillLevel;
    @BindView(R.id.iv_skill_level_logo)
    ImageView ivSkillLevelLogo;

    private int MAX_NUM = 3;//最多上传数量
    private UploadPhotoAdapter mUploadPhotoAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_skill;
    }

    @Override
    protected void initData() {
        centerTitle.setText("增加技能");
        rightTitle.setText("添加");

        mUploadPhotoAdapter = new UploadPhotoAdapter(mContext);
        rlvList.setLayoutManager(new GridLayoutManager(mContext, 3));
        rlvList.setAdapter(mUploadPhotoAdapter);
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

    @OnClick({R.id.rl_back, R.id.right_title, R.id.llyt_skill_level})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.right_title://添加
                addSkill();
                break;
            case R.id.llyt_skill_level://技能等级
                SelectLevel();
                break;
        }
    }

    /**
     * 添加技能
     */
    private void addSkill() {
        String skillName = edtName.getText().toString().trim();
        if (StringUtils.isEmpty(skillName)) {
            toast("请输入技能名称");
            return;
        }

        String skillGrade = tvSkillLevel.getText().toString().trim();
        if (StringUtils.isEmpty(skillGrade)) {
            toast("请选择技能等级");
            return;
        }
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
                .addParam("skillName", skillName)
                .addParam("skillGrade", skillGrade)
                .addParam("skillImgUrl", imgArr)
                .post()
                .url(NetUrlUtils.SKILL_ADD)
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                toast("添加成功");
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

    private void SelectLevel() {
        ArrayList<String> levelList = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            levelList.add("" + i);
        }
        OptionsPickerBuilder pickerBuilder = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvSkillLevel.setText("" + (options1 + 1));
                ivSkillLevelLogo.setVisibility(View.VISIBLE);
            }
        });
        OptionsPickerView<String> pickerView = pickerBuilder.isDialog(true).build();
        pickerView.setPicker(levelList);
        pickerView.show();
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
