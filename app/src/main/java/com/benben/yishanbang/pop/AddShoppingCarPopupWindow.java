package com.benben.yishanbang.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.RxBus;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.Interface.OnItemClickLitener;
import com.benben.yishanbang.ui.tea.adapter.AddShoppingCarAdapter;
import com.benben.yishanbang.ui.tea.bean.NormsBean;
import com.benben.yishanbang.ui.tea.bean.SweetnessBean;
import com.benben.yishanbang.ui.tea.bean.TemperatureBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Administrator on 2019/8/12 0012
 * Describe:加入购物车弹窗
 */
public class AddShoppingCarPopupWindow extends PopupWindow implements View.OnClickListener {

    @BindView(R.id.iv_shopping_car_icon)
    ImageView ivShoppingCarIcon;
    @BindView(R.id.tv_shopping_car_name)
    TextView tvShoppingCarName;
    @BindView(R.id.tv_shopping_car_spec)
    TextView tvShoppingCarSpec;
    @BindView(R.id.tv_shopping_car_price)
    TextView tvShoppingCarPrice;
    @BindView(R.id.tv_choose_goods_priced)
    TextView tvShoppingCarOldPrice;

    @BindView(R.id.iv_shopping_car_minus)
    ImageView ivShoppingCarMinus;
    @BindView(R.id.tv_shopping_car_count)
    TextView tvShoppingCarCount;
    @BindView(R.id.iv_shopping_car_add)
    ImageView ivShoppingCarAdd;
    @BindView(R.id.tv_goods_describe)
    TextView tvGoodsDescribe;
    @BindView(R.id.btn_gdd_shopping_car)
    Button btnGddShoppingCar;
    @BindView(R.id.rlv_add_car_spec)
    RecyclerView rlvAddCarSpec;
    @BindView(R.id.rlv_add_car_ploy)
    RecyclerView rlvAddCarPloy;
    @BindView(R.id.rlv_add_car_ice)
    RecyclerView rlvAddCarIce;
    private Context mContext;
    private View view;
    private AddShoppingCarAdapter mAddShoppingCarAdapter;

    private List<NormsBean> mNormsBeanList = new ArrayList<>();

    private List<SweetnessBean> mSweetBeanList = new ArrayList<>();

    private List<TemperatureBean> mTemperatureBeanList = new ArrayList<>();

    private SelectMilkTeaSweetAdapter mSelectMilkTeaSweetAdapter;//甜度适配器

    private SelectMilkTeaNormsAdapter mSelectMilkTeaNormsAdapter;//杯子尺寸适配器

    private SelectMilkTeaTemperatureAdapter mSelectMilkTeaTemperatureAdapter;//温度适配器

    private String mGoodsName, mDescription, mImgUrl, mPrice, mOldPrice, mGoodId, mShopId;

    private String mNorsName = "小杯", mSweetName = "无糖", mTemperatureName = "常温", mGoodCount = "1";

    private ShoppingCarPopupWindow mShoppingCarPopupWindow;//购物车弹窗


    public AddShoppingCarPopupWindow(Context context, String mDescription, String mImgUrl, String mPrice,
                                     String mGoodsName, String mOldPrice, String mGoodId, String mShopId) {
        super(context);
        this.mContext = context;
        this.mDescription = mDescription;
        this.mImgUrl = mImgUrl;
        this.mPrice = mPrice;
        this.mGoodsName = mGoodsName;
        this.mOldPrice = mOldPrice;
        this.mShopId = mShopId;
        this.mGoodId = mGoodId;
        init();

    }


    private void init() {

        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pop_add_shopping_car, null);
        ButterKnife.bind(this, view);

        // 导入布局
        this.setContentView(view);
        // 设置动画效果
        setAnimationStyle(R.style.popwindow_anim_style);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        // 设置可触
        setFocusable(true);
        final ColorDrawable dw = new ColorDrawable(0x0000000);
        setBackgroundDrawable(dw);
        /// 单击屏幕关闭弹窗
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int height = view.findViewById(R.id.llyt_shopping_car_add).getTop();
                int bottom = view.findViewById(R.id.llyt_shopping_car_add).getBottom();
                int y = (int) motionEvent.getY();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height || y > bottom) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        tvGoodsDescribe.setText(mDescription);
        tvShoppingCarPrice.setText("¥ " + mPrice);
        tvShoppingCarOldPrice.setText("¥" + mOldPrice);
        tvShoppingCarName.setText(mGoodsName);
        ImageUtils.getPic(Constants.IMAGE_BASE_URL + mImgUrl, ivShoppingCarIcon, mContext, R.mipmap.ic_default_pic);

        getNorms();
        getSweet();
        getTemperature();

        ivShoppingCarMinus.setOnClickListener(this);
        ivShoppingCarAdd.setOnClickListener(this);
        btnGddShoppingCar.setOnClickListener(this);

    }

    //获取商品规格
    private void getNorms() {
        NormsBean normsBean1 = new NormsBean();
        normsBean1.setId("1");
        normsBean1.setName("小杯");
        normsBean1.setChecked(true);
        mNormsBeanList.add(normsBean1);

        NormsBean normsBean2 = new NormsBean();
        normsBean2.setId("2");
        normsBean2.setName("中杯");
        normsBean2.setChecked(false);
        mNormsBeanList.add(normsBean2);

        NormsBean normsBean3 = new NormsBean();
        normsBean3.setId("3");
        normsBean3.setName("大杯");
        normsBean3.setChecked(false);
        mNormsBeanList.add(normsBean3);

        mSelectMilkTeaNormsAdapter = new SelectMilkTeaNormsAdapter(mNormsBeanList, mContext);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        rlvAddCarSpec.setLayoutManager(gridLayoutManager);
        rlvAddCarSpec.setAdapter(mSelectMilkTeaNormsAdapter);
    }


    //获取商品甜度
    private void getSweet() {
        SweetnessBean sweetnessBean1 = new SweetnessBean();
        sweetnessBean1.setId("1");
        sweetnessBean1.setName("无糖");
        sweetnessBean1.setChecked(true);
        mSweetBeanList.add(sweetnessBean1);

        SweetnessBean sweetnessBean2 = new SweetnessBean();
        sweetnessBean2.setId("2");
        sweetnessBean2.setName("半糖");
        sweetnessBean2.setChecked(false);
        mSweetBeanList.add(sweetnessBean2);

        SweetnessBean sweetnessBean3 = new SweetnessBean();
        sweetnessBean3.setId("3");
        sweetnessBean3.setName("多糖");
        sweetnessBean3.setChecked(false);
        mSweetBeanList.add(sweetnessBean3);


        mSelectMilkTeaSweetAdapter = new SelectMilkTeaSweetAdapter(mSweetBeanList, mContext);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        rlvAddCarPloy.setLayoutManager(gridLayoutManager);
        rlvAddCarPloy.setAdapter(mSelectMilkTeaSweetAdapter);

    }


    //获取商品温度规格
    private void getTemperature() {
        TemperatureBean temperatureBean1 = new TemperatureBean();
        temperatureBean1.setId("1");
        temperatureBean1.setName("常温");
        temperatureBean1.setChecked(true);
        mTemperatureBeanList.add(temperatureBean1);

        TemperatureBean temperatureBean2 = new TemperatureBean();
        temperatureBean2.setId("2");
        temperatureBean2.setName("少冰");
        temperatureBean2.setChecked(false);
        mTemperatureBeanList.add(temperatureBean2);

        TemperatureBean temperatureBean3 = new TemperatureBean();
        temperatureBean3.setId("3");
        temperatureBean3.setName("多冰");
        temperatureBean3.setChecked(false);
        mTemperatureBeanList.add(temperatureBean3);

        mSelectMilkTeaTemperatureAdapter = new SelectMilkTeaTemperatureAdapter(mTemperatureBeanList, mContext);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        rlvAddCarIce.setLayoutManager(gridLayoutManager);
        rlvAddCarIce.setAdapter(mSelectMilkTeaTemperatureAdapter);

    }

    //展示已选规格
    private void setNorms() {

        if (!("").equals(mNorsName) && ("").equals(mTemperatureName) && ("").equals(mSweetName)) {
            tvShoppingCarSpec.setText("已选：" + mNorsName);
        }

        if (!("").equals(mTemperatureName) && ("").equals(mSweetName) && ("").equals(mNorsName)) {
            tvShoppingCarSpec.setText("已选：" + mTemperatureName);
        }

        if (!("").equals(mSweetName) && ("").equals(mTemperatureName) && ("").equals(mNorsName)) {
            tvShoppingCarSpec.setText("已选：" + mSweetName);
        }

        if (!("").equals(mNorsName) && !("").equals(mSweetName) && ("").equals(mTemperatureName)) {
            tvShoppingCarSpec.setText("已选：" + mNorsName + "/" + mSweetName);
        }

        if (!("").equals(mSweetName) && !("").equals(mTemperatureName) && ("").equals(mNorsName)) {
            tvShoppingCarSpec.setText("已选：" + mSweetName + "/" + mTemperatureName);
        }

        if (!("").equals(mNorsName) && !("").equals(mTemperatureName) && ("").equals(mSweetName)) {
            tvShoppingCarSpec.setText("已选：" + mNorsName + "/" + mTemperatureName);
        }


        if (("").equals(mNorsName) && ("").equals(mSweetName) && ("").equals(mTemperatureName)) {
            tvShoppingCarSpec.setText("已选：");
        }

        /*if (!("").equals(mNorsName)&&!("").equals(mSweetName)&&!("").equals(mTemperatureName)){
            tvShoppingCarSpec.setText("已选："+mNorsName+"/"+mSweetName+"/"+mTemperatureName);
        }*/

        if (mNorsName != null && mSweetName != null && mTemperatureName != null) {
            tvShoppingCarSpec.setText("已选：" + mNorsName + "/" + mSweetName + "/" + mTemperatureName);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_shopping_car_minus:
                int count = Integer.parseInt(tvShoppingCarCount.getText().toString().trim());
                if (count <= 1) {
                    return;
                }
                --count;
                tvShoppingCarCount.setText(count + "");

                mGoodCount = count + "";
                break;
            case R.id.iv_shopping_car_add:
                int count1 = Integer.parseInt(tvShoppingCarCount.getText().toString().trim());
                ++count1;
                tvShoppingCarCount.setText(count1 + "");
                if (count1 > 0) {
                    ivShoppingCarMinus.setEnabled(true);
                }
                mGoodCount = count1 + "";
                break;
            case R.id.btn_gdd_shopping_car:
                addShoppingCar();
                break;
        }
    }

    //添加到购物车
    private void addShoppingCar() {
        if (StringUtils.isEmpty(mGoodId)) {
            ToastUtils.show(mContext, "商品不能为空");
            return;
        }
        if (StringUtils.isEmpty(mNorsName)) {
            ToastUtils.show(mContext, "请选择规格");
            return;
        }
        if (StringUtils.isEmpty(mSweetName)) {
            ToastUtils.show(mContext, "请选择糖度");
            return;
        }
        if (StringUtils.isEmpty(mTemperatureName)) {
            ToastUtils.show(mContext, "请选择温度");
            return;
        }
        if (StringUtils.isEmpty(mGoodCount) || ("0").equals(mGoodCount)) {
            ToastUtils.show(mContext, "请添加商品");
            return;
        }
        BaseOkHttpClient.newBuilder()
                .addParam("id", mGoodId)//商品Id
                .addParam("spec", mNorsName)//商品规格
                .addParam("temp", mTemperatureName)//商品糖度
                .addParam("suger", mSweetName)//商品温度
                .addParam("goodsNum", mGoodCount)//商品数量
                .addParam("shopid", mShopId)//店铺id
                .url(NetUrlUtils.ADD_SHOPPING_CAR)
                .post()
                .build().enqueue((Activity) mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                dismiss();
                ToastUtils.show(mContext, "添加购物车成功");
                //传到activity  刷新店铺商品信息
                RxBus.getInstance().post(Constants.REFRESH_TEA_SHOP_GOODS_INFO);
                //mShoppingCarPopupWindow = new ShoppingCarPopupWindow((Activity) mContext, mShopId);
                //mShoppingCarPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
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


    class SelectMilkTeaNormsAdapter extends RecyclerView.Adapter {
        private Context mContext;
        private List<NormsBean> mNormsData;

        public SelectMilkTeaNormsAdapter(List<NormsBean> mNormsData, Context mContext) {
            this.mNormsData = mNormsData;
            this.mContext = mContext;
        }

        private OnItemClickLitener mOnItemClickLitener;

        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_milktea_select_norms, parent, false);

            return new MultiViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MultiViewHolder) {
                final MultiViewHolder viewHolder = (MultiViewHolder) holder;

                if (mNormsData.get(position) != null) {
                    viewHolder.tv_norms.setText(mNormsData.get(position).getName());
                }

                if (mOnItemClickLitener != null) {
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickLitener.onItemClick(viewHolder.itemView, viewHolder.getAdapterPosition());
                        }
                    });
                }

                viewHolder.tv_norms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mNorsName = mNormsData.get(position).getName();

                        for (int i = 0; i < mNormsData.size(); i++) {
                            if (position == i) {
                                mNormsData.get(i).setChecked(true);
                            } else {
                                mNormsData.get(i).setChecked(false);
                            }
                        }
                        notifyDataSetChanged();
                        setNorms();
                    }
                });

                if (mNormsData.get(position).isChecked()) {
                    viewHolder.tv_norms.setBackgroundResource(R.drawable.shape_border_green_radius4);
                    viewHolder.tv_norms.setTextColor(Color.parseColor("#7DD33C"));
                } else {
                    viewHolder.tv_norms.setBackgroundResource(R.drawable.shape_border_grey_radius4);
                    viewHolder.tv_norms.setTextColor(Color.parseColor("#666666"));
                }
            }

        }

        @Override
        public int getItemCount() {
            return mNormsData.size();
        }

        class MultiViewHolder extends RecyclerView.ViewHolder {
            TextView tv_norms;


            public MultiViewHolder(View itemView) {
                super(itemView);
                tv_norms = (TextView) itemView.findViewById(R.id.tv_norms);
            }
        }
    }


    class SelectMilkTeaSweetAdapter extends RecyclerView.Adapter {

        private Context mContext;
        private List<SweetnessBean> mSweetnData;

        public SelectMilkTeaSweetAdapter(List<SweetnessBean> mSweetnData, Context mContext) {
            this.mSweetnData = mSweetnData;
            this.mContext = mContext;
        }

        private OnItemClickLitener mOnItemClickLitener;

        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_milktea_select_norms, parent, false);

            return new MultiViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MultiViewHolder) {
                final MultiViewHolder viewHolder = (MultiViewHolder) holder;

                if (mSweetnData.get(position) != null) {
                    viewHolder.tv_norms.setText(mSweetnData.get(position).getName());
                }

                if (mOnItemClickLitener != null) {
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickLitener.onItemClick(viewHolder.itemView, viewHolder.getAdapterPosition());
                        }
                    });
                }

                viewHolder.tv_norms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mSweetName = mSweetnData.get(position).getName();

                        for (int i = 0; i < mSweetnData.size(); i++) {
                            if (position == i) {
                                mSweetnData.get(i).setChecked(true);
                            } else {
                                mSweetnData.get(i).setChecked(false);
                            }
                        }
                        notifyDataSetChanged();
                        setNorms();
                    }
                });

                if (mSweetnData.get(position).isChecked()) {
                    viewHolder.tv_norms.setBackgroundResource(R.drawable.shape_border_green_radius4);
                    viewHolder.tv_norms.setTextColor(Color.parseColor("#7DD33C"));
                } else {
                    viewHolder.tv_norms.setBackgroundResource(R.drawable.shape_border_grey_radius4);
                    viewHolder.tv_norms.setTextColor(Color.parseColor("#666666"));
                }
            }

        }

        @Override
        public int getItemCount() {
            return mSweetnData.size();
        }

        class MultiViewHolder extends RecyclerView.ViewHolder {
            TextView tv_norms;


            public MultiViewHolder(View itemView) {
                super(itemView);
                tv_norms = (TextView) itemView.findViewById(R.id.tv_norms);
            }
        }
    }

    class SelectMilkTeaTemperatureAdapter extends RecyclerView.Adapter {
        private Context mContext;
        private List<TemperatureBean> mTemperatureData;

        public SelectMilkTeaTemperatureAdapter(List<TemperatureBean> mTemperatureData, Context mContext) {
            this.mTemperatureData = mTemperatureData;
            this.mContext = mContext;
        }

        private OnItemClickLitener mOnItemClickLitener;

        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_milktea_select_norms, parent, false);

            return new MultiViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MultiViewHolder) {
                final MultiViewHolder viewHolder = (MultiViewHolder) holder;

                if (mTemperatureData.get(position) != null) {
                    viewHolder.tv_norms.setText(mTemperatureData.get(position).getName());
                }

                if (mOnItemClickLitener != null) {
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickLitener.onItemClick(viewHolder.itemView, viewHolder.getAdapterPosition());
                        }
                    });
                }

                viewHolder.tv_norms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mTemperatureName = mTemperatureData.get(position).getName();

                        for (int i = 0; i < mTemperatureData.size(); i++) {
                            if (position == i) {
                                mTemperatureData.get(i).setChecked(true);
                            } else {
                                mTemperatureData.get(i).setChecked(false);
                            }
                        }
                        notifyDataSetChanged();
                        setNorms();
                    }
                });

                if (mTemperatureData.get(position).isChecked()) {
                    viewHolder.tv_norms.setBackgroundResource(R.drawable.shape_border_green_radius4);
                    viewHolder.tv_norms.setTextColor(Color.parseColor("#7DD33C"));
                } else {
                    viewHolder.tv_norms.setBackgroundResource(R.drawable.shape_border_grey_radius4);
                    viewHolder.tv_norms.setTextColor(Color.parseColor("#666666"));
                }
            }

        }

        @Override
        public int getItemCount() {
            return mTemperatureData.size();
        }

        class MultiViewHolder extends RecyclerView.ViewHolder {
            TextView tv_norms;


            public MultiViewHolder(View itemView) {
                super(itemView);
                tv_norms = (TextView) itemView.findViewById(R.id.tv_norms);
            }
        }
    }

}


