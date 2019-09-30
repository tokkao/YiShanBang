package com.benben.yishanbang.ui.discount.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.benben.commoncore.utils.RxBus;
import com.benben.commoncore.utils.StringUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.ui.discount.bean.SelectedAddressInfoBean;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.addapp.pickers.picker.SinglePicker;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/6
 * Time: 17:14
 * 选择地区
 */
public class SelectAreaActivity extends BaseActivity {
    @BindView(R.id.tv_province)
    TextView tvProvince;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private String mProvince = "";//省
    private String mCity = "";//城市
    private String mArea = "";//地区

    private List<String> mShowList = new ArrayList<>();//显示内容，根据需要填充

    private SinglePicker picker = null;
    //地区选择对象
    private CityPickerView mPicker;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_area;
    }

    @Override
    protected void initData() {
        initTitle("地区选择");
        //申明对象
        mPicker = new CityPickerView();
        mPicker.init(this);
        //添加默认的配置，不需要自己定义，当然也可以自定义相关熟悉，详细属性请看demo
        CityConfig cityConfig = new CityConfig.Builder().build();
        mPicker.setConfig(cityConfig);

        //监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
//                MyApplication.mPreferenceProvider.setProvince(province.getName());
//                MyApplication.mPreferenceProvider.setCity(city.getName());
//                MyApplication.mPreferenceProvider.setDistrict(district.getName());
                mProvince = province.getName();
                mCity = city.getName();
                mArea = district.getName();
                //省份province
                //城市city
                //地区district

                tvProvince.setText(mProvince);
                tvCity.setText(mCity);
                tvArea.setText(mArea);
            }

            @Override
            public void onCancel() {
                ToastUtils.showLongToast(mContext, "已取消");
            }
        });

    }

    @OnClick({R.id.tv_province, R.id.tv_city, R.id.tv_area, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_province:

                /*mShowList.clear();
                for (int i = 0; i < 30; i++) {
                    mShowList.add("河南");
                    mShowList.add("河北");
                    mShowList.add("北京");
                }
                picker = new SinglePicker(this, mShowList);
                picker.setGravity(Gravity.BOTTOM);
                picker.setTitleText("请选择省");
                picker.setCanLoop(false);
                picker.setLineColor(getResources().getColor(R.color.theme));
                picker.setSelectedTextColor(getResources().getColor(R.color.theme));
                picker.setSubmitTextColor(getResources().getColor(R.color.theme));
                picker.setPressedTextColor(getResources().getColor(R.color.theme));
                picker.setTitleTextColor(getResources().getColor(R.color.theme));
                picker.setTopLineColor(getResources().getColor(R.color.view_line));
                picker.setOnItemPickListener(new OnItemPickListener() {
                    @Override
                    public void onItemPicked(int i, Object o) {
                        if (!o.equals(mProvince)) {
                            tvProvince.setText("" + o);
                            tvCity.setText("请选择");
                            tvArea.setText("请选择");

                            mProvince = (String) o;
                            mCity = "";
                            mArea = "";
                        }
                    }
                });
                picker.show();*/
            case R.id.tv_city:
               /* mShowList.clear();
                mShowList.add("河南");
                mShowList.add("河北");
                mShowList.add("北京");
                picker = new SinglePicker(this, mShowList);
                picker.setGravity(Gravity.BOTTOM);
                picker.setTitleText("请选择市");
                picker.setCanLoop(false);
                picker.setLineColor(getResources().getColor(R.color.theme));
                picker.setSelectedTextColor(getResources().getColor(R.color.theme));
                picker.setSubmitTextColor(getResources().getColor(R.color.theme));
                picker.setPressedTextColor(getResources().getColor(R.color.theme));
                picker.setTitleTextColor(getResources().getColor(R.color.theme));
                picker.setTopLineColor(getResources().getColor(R.color.view_line));
                picker.setOnItemPickListener(new OnItemPickListener() {
                    @Override
                    public void onItemPicked(int i, Object o) {
                        if (!o.equals(mCity)) {
                            tvCity.setText("" + o);
                            tvArea.setText("请选择");

                            mCity = (String) o;
                            mArea = "";
                        }
                    }
                });
                picker.show();*/
            case R.id.tv_area:
               /* mShowList.clear();
                mShowList.add("河南");
                mShowList.add("河北");
                mShowList.add("北京");
                picker = new SinglePicker(this, mShowList);
                picker.setGravity(Gravity.BOTTOM);
                picker.setTitleText("请选择县");
                picker.setCanLoop(false);
                picker.setLineColor(getResources().getColor(R.color.theme));
                picker.setSelectedTextColor(getResources().getColor(R.color.theme));
                picker.setSubmitTextColor(getResources().getColor(R.color.theme));
                picker.setPressedTextColor(getResources().getColor(R.color.theme));
                picker.setTitleTextColor(getResources().getColor(R.color.theme));
                picker.setTopLineColor(getResources().getColor(R.color.view_line));
                picker.setOnItemPickListener(new OnItemPickListener() {
                    @Override
                    public void onItemPicked(int i, Object o) {
                        tvArea.setText("" + o);
                        mArea = (String) o;
                    }
                });
                picker.show();*/
                //显示
                mPicker.showCityPicker();
                break;
            case R.id.btn_submit:
                submit();
                break;
        }
    }

    /**
     * 确认选择
     */
    private void submit() {
       /* if (TextUtils.isEmpty(mProvince)) {
            Toast.makeText(mContext, "请选择省", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mCity)) {
            Toast.makeText(mContext, "请选择市", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mArea)) {
            Toast.makeText(mContext, "请选择县", Toast.LENGTH_SHORT).show();
            return;
        }*/
       if(StringUtils.isEmpty(mProvince)&&StringUtils.isEmpty(mCity)&&StringUtils.isEmpty(mArea)){
           finish();
           return;
       }
        SelectedAddressInfoBean selectedAddressInfoBean = new SelectedAddressInfoBean();
        selectedAddressInfoBean.setProvince(mProvince);
        selectedAddressInfoBean.setCity(mCity);
        selectedAddressInfoBean.setDistrict(mArea);
        RxBus.getInstance().post(selectedAddressInfoBean);
        finish();
    }

}
