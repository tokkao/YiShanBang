package com.benben.yishanbang.ui.mine.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.InputCheckUtil;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.LogUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.widget.CircleImageView;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.mine.bean.UserInfoBean;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kongzue.dialog.interfaces.OnMenuItemClickListener;
import com.kongzue.dialog.v3.BottomMenu;
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
 * Describe: 个人资料
 */
public class PersonalDataActivity extends BaseActivity {
    private static final String TAG = "PersonalDataActivity";
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.edt_nickname)
    EditText edtNickname;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_constellations)
    TextView tvConstellations;
    @BindView(R.id.edt_height)
    EditText edtHeight;
    @BindView(R.id.edt_weight)
    EditText edtWeight;
    @BindView(R.id.edt_age)
    EditText edtAge;
    @BindView(R.id.tv_auth)
    TextView tvAuth;
    @BindView(R.id.civ_avatar)
    CircleImageView civAvatar;
    //头像
    private String mAvatarUrl;
    private UserInfoBean mUserInfoBean;
    private List<LocalMedia> mSelectList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_data;
    }

    @Override
    protected void initData() {
        centerTitle.setText("个人资料");
        rightTitle.setText("保存");

//        //包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
//        PictureFileUtils.deleteCacheDirFile(mContext);

        //过滤表情 符号
        InputCheckUtil.filterEmoji(edtNickname, mContext);
        getUserInfo();//获取用户信息
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.GET_USER_INFO)
                .addParam("token", MyApplication.mPreferenceProvider.getToken())
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {

            @Override
            public void onSuccess(String json, String msg) {
                mUserInfoBean = JSONUtils.jsonString2Bean(json, UserInfoBean.class);
                mAvatarUrl = mUserInfoBean.getAvatar();
                //头像
                ImageUtils.getPic(Constants.IMAGE_BASE_URL + mUserInfoBean.getAvatar(), civAvatar, mContext, R.mipmap.icon_default_avatar); //头像
                //昵称
                edtNickname.setText(mUserInfoBean.getNickname());
                //性别
                tvSex.setText(mUserInfoBean.getSex().equals("0") ? "男" : "女");
                //星座
                tvConstellations.setText(mUserInfoBean.getMessageConstellation());
                //身高
                edtHeight.setText(mUserInfoBean.getMessageHight() + "");
                //体重
                edtWeight.setText(mUserInfoBean.getMessageWeight() + "");
                //年龄
                edtAge.setText(mUserInfoBean.getAge());
            }

            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    /*
     *图库，拍照
     */
    private void SelectPicture(PictureSelectionModel pictureSelectionModel) {
        // 进入相册 以下是例子：用不到的api可以不写
        //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()

//                .theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
        pictureSelectionModel.maxSelectNum(1)// 最大图片选择数量 int
//                .minSelectNum()// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(false)// 是否可预览图片 true or false
//                .previewVideo(false)// 是否可预览视频 true or false
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

    @OnClick({R.id.rl_back, R.id.right_title, R.id.cv_avatar, R.id.llyt_sex, R.id.llyt_constellations, R.id.tv_address, R.id.tv_modify_mobile, R.id.tv_auth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.right_title://保存
                saveData();
                break;
            case R.id.cv_avatar://头像
                showSelectAvatarDialog();
                break;
            case R.id.llyt_sex://性别
                ArrayList<String> sexList = new ArrayList<>();
                sexList.add("男");
                sexList.add("女");
                BottomMenu.show((AppCompatActivity) mContext, sexList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        tvSex.setText(sexList.get(index));
                    }
                });
                break;
            case R.id.llyt_constellations://星座
                SelectConstellations();
                break;
            case R.id.tv_address://收货地址
                startActivity(new Intent(mContext, AddressManageActivity.class));
                break;
            case R.id.tv_modify_mobile://更换手机
                startActivity(new Intent(mContext, BindMobileActivity.class));
                break;
            case R.id.tv_auth://认证
                startActivity(new Intent(mContext, VerifiedActivity.class));
                break;
        }
    }

    /**
     * 保存
     */
    private void saveData() {

        if (mSelectList != null && mSelectList.size() > 0) {//表示用户选择了头像，需重新上传
            uploadAvatar();//上传头像
        } else {
            updateUserInfo();
        }

    }

    /**
     * 修改个人信息
     */
    private void updateUserInfo() {
        String name = edtNickname.getText().toString().trim();//昵称
        String height = edtHeight.getText().toString().trim();//身高
        String weight = edtWeight.getText().toString().trim();//体重
        String age = edtWeight.getText().toString().trim();//年龄
        String sex = tvSex.getText().toString().trim();//性别
        String constellation = tvConstellations.getText().toString().trim();//星座

        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.UPDATE_USER_MAG)
                .addParam("nickName", name)
                .addParam("age", age)
                .addParam("avatar", mAvatarUrl)
                .addParam("sex", "男".equals(sex) ? 0 : 1)
                .addParam("messageConstellation", constellation)
                .addParam("messageWeight", weight)
                .addParam("messageHight", height)
                .post()
                .build()
                .enqueue(mContext, new BaseCallBack<String>() {
                    @Override
                    public void onSuccess(String result, String msg) {
                        //    String userData = JSONUtils.toJsonString(result);
                        toast(msg);
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

    /**
     * 获取头像
     */
    private void uploadAvatar() {

        /**
         * 把当前选择的图片文件上传到服务器
         *
         */
        File file = new File(mSelectList.get(0).getCompressPath());
        BaseOkHttpClient.newBuilder()
                .addFile("files", file.getName(), file)
                .url(NetUrlUtils.UPLOAD_IMAGE_LOCAL)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                List<String> results = JSONUtils.jsonString2Beans(json, String.class);
                updateUserInfo();//修改个人信息
                toast(msg);
                if (results == null || results.size() == 0) {
                    toast("上传失败！");
                    return;
                }
                mAvatarUrl = results.get(0);
                //提交用户信息
                updateUserInfo();
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

    //选择星座
    private void SelectConstellations() {
        ArrayList<String> constellationList = new ArrayList<>();
        constellationList.add("水瓶座");
        constellationList.add("双鱼座");
        constellationList.add("白羊座");
        constellationList.add("金牛座");
        constellationList.add("双子座");
        constellationList.add("巨蟹座");
        constellationList.add("狮子座");
        constellationList.add("处女座");
        constellationList.add("天秤座");
        constellationList.add("天蝎座");
        constellationList.add("射手座");
        constellationList.add("摩羯座");
        OptionsPickerBuilder pickerBuilder = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvConstellations.setText(constellationList.get(options1));
            }
        });
        OptionsPickerView<String> pickerView = pickerBuilder.isDialog(true).build();
        pickerView.setSelectOptions(5);
        pickerView.setPicker(constellationList);
        pickerView.isDialog();
        pickerView.show();
    }

    //选择头像dialog
    private void showSelectAvatarDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
        View view = View.inflate(mContext, R.layout.dialog_select_avatar, null);
        TextView tvTakePhoto = view.findViewById(R.id.tv_take_photo);
        TextView tvSelectPhoto = view.findViewById(R.id.tv_select_photo);
        TextView tvLastAvatar = view.findViewById(R.id.tv_last_avatar);
        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
        //拍照
        tvTakePhoto.setOnClickListener(view1 -> {
            bottomSheetDialog.dismiss();

            SelectPicture(PictureSelector.create(mContext)
                    .openCamera(PictureMimeType.ofImage()));

        });
        //相册
        tvSelectPhoto.setOnClickListener(view1 -> {
            bottomSheetDialog.dismiss();
            SelectPicture(PictureSelector.create(mContext)
                    .openGallery(PictureMimeType.ofImage()));

        });
        //上一张
        tvLastAvatar.setOnClickListener(view1 -> {
            bottomSheetDialog.dismiss();
            getLastAvatar();

        });
        //取消
        tvCancel.setOnClickListener(view1 -> {
            bottomSheetDialog.dismiss();

        });
    }

    /**
     * 上一张头像
     */
    private void getLastAvatar() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.OLD_USER_IMAGE)
                .get()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                if (StringUtils.isEmpty(json)) {
                    toast("您还没有上一张头像");
                } else {
                    mAvatarUrl = JSONUtils.getNoteJson(json, "isOldImg");
                    ImageUtils.getPic(Constants.IMAGE_BASE_URL + mAvatarUrl, civAvatar, mContext, R.mipmap.icon_default_avatar);
                }

            }

            @Override
            public void onError(int code, String msg) {
                toast("您还没有上一张头像");
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
//                        civAvatar.setImageResource();
                        LogUtils.e(TAG, mSelectList.get(0).getCompressPath());
                        Glide.with(mContext).load(new File(mSelectList.get(0).getCompressPath())).into(civAvatar);
                    }
                    break;
            }
        }
    }

}
