package com.benben.yishanbang.ui.tea.activity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.tea.adapter.UpdateUserSkillAdapter;
import com.benben.yishanbang.ui.tea.bean.UpdateSkillBean;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2019/8/16 0016
 * Describe:修改他的技能
 */
public class UpdateSkillActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.rlv_update_user_skill)
    RecyclerView rlvUpdateUserSkill;
    private UpdateUserSkillAdapter mUpdateUserSkillAdapter;
    private List<UpdateSkillBean> mSkillList;
    private List<String> mIdList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_user_skill;
    }

    @Override
    protected void initData() {
        centerTitle.setText("我的技能");
        rightTitle.setText("保存");

        mUpdateUserSkillAdapter = new UpdateUserSkillAdapter(mContext);
        rlvUpdateUserSkill.setAdapter(mUpdateUserSkillAdapter);
        rlvUpdateUserSkill.setLayoutManager(new LinearLayoutManager(mContext));
        getSkillList();

        mUpdateUserSkillAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<UpdateSkillBean>() {
            @Override
            public void onItemClick(View view, int position, UpdateSkillBean model) {
                mIdList.add(model.getId());
            }

            @Override
            public void onItemLongClick(View view, int position, UpdateSkillBean model) {

            }
        });
    }

    //获取技能列表
    private void getSkillList() {
        BaseOkHttpClient.newBuilder()
                .addParam("token", MyApplication.mPreferenceProvider.getToken())
                .url(NetUrlUtils.GET_USER_SKILL_LIST)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                mSkillList = JSONUtils.jsonString2Beans(json, UpdateSkillBean.class);
                mUpdateUserSkillAdapter.refreshList(mSkillList);

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

    //保存技能信息
    private void saveSkillInfo() {
        if (mSkillList == null || mSkillList.size() <= 0) {
            toast("没有获取到技能信息，无法保存");
            return;
        }
        JSONArray skillIdArray = new JSONArray();
        for (int i = 0; i < mIdList.size(); i++) {
            skillIdArray.put(mIdList.get(i));
        }
        BaseOkHttpClient.newBuilder()
                .addParam("id", skillIdArray)
                .url(NetUrlUtils.UPDATE_USER_SKILL)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
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


    @OnClick({R.id.rl_back, R.id.right_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back://返回
                onBackPressed();
                break;
            case R.id.right_title://保存
                saveSkillInfo();
                break;
        }
    }

}
