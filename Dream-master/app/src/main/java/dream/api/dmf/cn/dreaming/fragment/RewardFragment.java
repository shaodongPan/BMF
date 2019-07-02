package dream.api.dmf.cn.dreaming.fragment;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.activity.RewardDanActivity;
import dream.api.dmf.cn.dreaming.activity.RewardListActivity;
import dream.api.dmf.cn.dreaming.activity.RewardMxActivity;
import dream.api.dmf.cn.dreaming.activity.RewardPicesActivity;
import dream.api.dmf.cn.dreaming.activity.RewardShenActivity;
import dream.api.dmf.cn.dreaming.activity.RewardTiActivity;
import dream.api.dmf.cn.dreaming.activity.RewardzzActivity;
import dream.api.dmf.cn.dreaming.adapter.VipNumberAdapter;
import dream.api.dmf.cn.dreaming.api.UserApi;
import dream.api.dmf.cn.dreaming.base.BaseMvpFragment;
import dream.api.dmf.cn.dreaming.base.mvp.Contract;
import dream.api.dmf.cn.dreaming.base.mvp.presenter.presenter;
import dream.api.dmf.cn.dreaming.bean.ReadBean;
import dream.api.dmf.cn.dreaming.listener.IItemClicked;
import dream.api.dmf.cn.dreaming.utils.JsonUtil;
import dream.api.dmf.cn.dreaming.view.CommonPoPWindow;


public class RewardFragment extends BaseMvpFragment<presenter> implements Contract.Iview, IItemClicked<String> {

    Unbinder unbinder1;
    @BindView(R.id.r_head_num)
    TextView rHeadNum;
    @BindView(R.id.r_head_name)
    TextView rHeadName;
    @BindView(R.id.mhead_image)
    RelativeLayout mheadImage;
    @BindView(R.id.r_image_left)
    ImageView rImageLeft;
    @BindView(R.id.r_quanreward)
    LinearLayout rQuanreward;
    @BindView(R.id.r_zong)
    TextView rZong;
    @BindView(R.id.r_xian)
    TextView rXian;
    @BindView(R.id.r_tui)
    TextView rTui;
    @BindView(R.id.r_tuan)
    TextView rTuan;
    @BindView(R.id.r_hui_left)
    ImageView rHuiLeft;
    @BindView(R.id.r_huireward)
    LinearLayout rHuireward;
    @BindView(R.id.r_tu)
    LinearLayout rTu;
    @BindView(R.id.r_cai_left)
    ImageView rCaiLeft;
    @BindView(R.id.r_reward)
    LinearLayout rReward;
    @BindView(R.id.r_showlist)
    ImageView rShowlist;
    @BindView(R.id.mjf_list)
    TextView mjfList;
    @BindView(R.id.r_xiang)
    ImageView rXiang;
    @BindView(R.id.mjf_xi)
    TextView mjfXi;
    @BindView(R.id.r_tixian)
    ImageView rTixian;
    @BindView(R.id.mjd_ti)
    TextView mjdTi;
    @BindView(R.id.r_shen)
    ImageView rShen;
    @BindView(R.id.r_jifen)
    ImageView rJifen;
    @BindView(R.id.mjf_zz)
    TextView mjfZz;
    @BindView(R.id.mjf_shen)
    TextView mjfShen;
    private View view;
    @BindView(R.id.r_dan)
    LinearLayout mRan;
    private SimpleDraweeView mImage;
    private TextView lNum;
    private TextView rNum;
    private SharedPreferences sharedPreferences;
    private String uPhone;
    private ReadBean.DataBean data;
    private TextView mZong;
    private TextView mXian;
    private TextView mTui;
    private TextView mTuan;
    private TextView mNumberTv;

    private CommonPoPWindow poPWindow;//会员编号弹框
    private List<String> mNumberList = new ArrayList<>();
    private String mLastNumber;

    @Override
    protected presenter createPresenter() {
        return new presenter();
    }

    @Override
    protected int getFragmentView() {
        return R.layout.fragment_reward;
    }

    @Override
    protected void initView(View view) {
        sharedPreferences = mContext.getSharedPreferences(UserApi.SP, Context.MODE_PRIVATE);
        mImage = view.findViewById(R.id.r_head_image);
        lNum = view.findViewById(R.id.left_num);
        rNum = view.findViewById(R.id.right_num);
        mZong = view.findViewById(R.id.r_zong);
        mXian = view.findViewById(R.id.r_xian);
        mTui = view.findViewById(R.id.r_tui);
        mTuan = view.findViewById(R.id.r_tuan);
        mNumberTv = view.findViewById(R.id.vip_number);

        mNumberTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindow(v);
            }
        });


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && mPresenter != null) {
            getData();
        }
    }

    @Override
    protected void getData() {
        uPhone = sharedPreferences.getString(UserApi.LOGIN_ACCOUNT, "");
        getDataByNumber("");
    }

    protected void getDataByNumber(String number) {
        HashMap<String, Object> headmap = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", uPhone);
        if (!TextUtils.isEmpty(number)) {
            map.put("number", number);
        }
        mPresenter.postData(UserApi.getRewardList, headmap, map, ReadBean.class);
    }

    @Override
    public void getData(Object object) {
        uPhone = sharedPreferences.getString(UserApi.LOGIN_ACCOUNT, "");
        rHeadNum.setText("" + uPhone);
        if (object == null) {
            return;
        }
        if (object instanceof Throwable) {
            String error = ((Throwable) object).getMessage();
            Toast.makeText(mContext, JsonUtil.getError(error), Toast.LENGTH_SHORT).show();
            return;
        }
        ReadBean readBean = (ReadBean) object;
        if (readBean.status.equals("200")) {
            data = readBean.data;
            if (data.info == null) {
                mNumberList.clear();
                lNum.setText("0");
                rNum.setText("0");
                rHeadNum.setText(sharedPreferences.getString(UserApi.LOGIN_ACCOUNT, ""));
                mZong.setText("0");
                mXian.setText("0");
                mTui.setText("0");
                mTuan.setText("0");
                mNumberTv.setText("会员编号");
                mLastNumber = "";
                updateLevel("");
                return;
            } else {
                mNumberList = data.arr;
                lNum.setText("" + data.info.left_duipeng);
                rNum.setText("" + data.info.right_duipeng);
                rHeadNum.setText("" + data.info.phone);
                mZong.setText("" + data.info.total_rewqrd);
                mXian.setText("" + data.info.now_jifen);
                mTui.setText("" + data.info.commend_count);
                mTuan.setText("" + data.info.team_count);
                mNumberTv.setText("会员编号" + data.info.number);
                mLastNumber = data.info.number;
                updateLevel(data.info.level);
            }
        } else {
            Toast.makeText(mContext, readBean.message, Toast.LENGTH_SHORT).show();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.r_showlist, R.id.r_xiang, R.id.r_tixian, R.id.r_shen, R.id.r_jifen, R.id.mjf_zz, R.id.r_tu, R.id.r_dan})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.r_dan://推荐清单
                intent = new Intent(getActivity(), RewardDanActivity.class);
                break;
            //奖励列表
            case R.id.r_showlist:
                intent = new Intent(getActivity(), RewardListActivity.class);
                break;
            //积分明细
            case R.id.r_xiang:
                intent = new Intent(getActivity(), RewardMxActivity.class);
                break;
            //积分转换
            case R.id.r_tixian:
                intent = new Intent(getActivity(), RewardTiActivity.class);
                break;
            //申请服务
            case R.id.r_shen:
                intent = new Intent(getActivity(), RewardShenActivity.class);
                break;
            //积分转增
            case R.id.r_jifen:
                intent = new Intent(getActivity(), RewardzzActivity.class);
                break;
            //跳转到网络图谱
            case R.id.r_tu:
                intent = new Intent(getActivity(), RewardPicesActivity.class);
                break;
        }
        if (intent != null) {
            intent.putExtra("data", mLastNumber);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
        }

    }


    private void showPopWindow(View anchor) {
        if (poPWindow != null) {
            poPWindow.dismiss();
        }

        if (mNumberList == null || mNumberList.size() == 0) {
            return;
        }
        poPWindow = new CommonPoPWindow(getActivity(), new CommonPoPWindow.PopCallback() {
            @Override
            public View getPopWindowChildView(View mMenuView) {
                RecyclerView listview = mMenuView.findViewById(R.id.listview);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                listview.setLayoutManager(layoutManager);
                VipNumberAdapter adapter = new VipNumberAdapter(RewardFragment.this);
                adapter.setData(mNumberList);
                adapter.setSelect(mLastNumber);
                listview.setAdapter(adapter);
                return mMenuView;
            }
        }, R.layout.pop_window_item);
        poPWindow.showAsDropDown(anchor, 0, 0);
    }

    @Override
    public void onItemClicked(View view, int position, String data) {
        if (poPWindow != null) {
            poPWindow.dismiss();
        }
        getDataByNumber(data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (poPWindow != null) {
            poPWindow.dismiss();
        }
    }

    private void updateLevel(String level) {
        if ("1".equals(level)) {
            rHeadName.setText("银卡");
        } else if ("2".equals(level)) {
            rHeadName.setText("金卡");
        } else if ("3".equals(level)) {
            rHeadName.setText("钻卡");
        } else if ("4".equals(level)) {
            rHeadName.setText("金钻");
        } else {
            rHeadName.setText("普通");
        }

    }
}
