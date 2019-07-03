package dream.api.dmf.cn.dreaming.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.activity.minactivity.AllActivity;
import dream.api.dmf.cn.dreaming.activity.minactivity.FahuoActivity;
import dream.api.dmf.cn.dreaming.activity.minactivity.FinishActivity;
import dream.api.dmf.cn.dreaming.activity.minactivity.ShouActivity;
import dream.api.dmf.cn.dreaming.activity.minactivity.WaitActivity;
import dream.api.dmf.cn.dreaming.activity.mineactivity.AddressActivity;
import dream.api.dmf.cn.dreaming.activity.mineactivity.AnQuanActivity;
import dream.api.dmf.cn.dreaming.activity.mineactivity.LoadDownActivity;
import dream.api.dmf.cn.dreaming.activity.mineactivity.MJListActivity;
import dream.api.dmf.cn.dreaming.activity.mineactivity.SetActivity;
import dream.api.dmf.cn.dreaming.activity.mineactivity.ShareActivity;
import dream.api.dmf.cn.dreaming.activity.mineactivity.ShopActivity;
import dream.api.dmf.cn.dreaming.activity.mineactivity.ShouKActivity;
import dream.api.dmf.cn.dreaming.api.UserApi;
import dream.api.dmf.cn.dreaming.base.BaseMvpFragment;
import dream.api.dmf.cn.dreaming.base.mvp.Contract;
import dream.api.dmf.cn.dreaming.base.mvp.presenter.presenter;
import dream.api.dmf.cn.dreaming.bean.MsgNum;
import dream.api.dmf.cn.dreaming.bean.SignBean;


public class MineFragment extends BaseMvpFragment<presenter> implements Contract.Iview {


    @BindView(R.id.mine_head_img)
    SimpleDraweeView mineHeadImg;
    @BindView(R.id.r_head_num)
    TextView rHeadNum;
    @BindView(R.id.mine_head_image)
    ImageView mineHeadImage;
    @BindView(R.id.mine_head_phone)
    TextView mineHeadPhone;
    @BindView(R.id.mine_head_num)
    TextView mineHeadNum;
    @BindView(R.id.head_s_image)
    ImageView headSImage;
    @BindView(R.id.mhead_image)
    RelativeLayout mheadImage;
    @BindView(R.id.m_jin)
    TextView mJin;
    @BindView(R.id.m_fen)
    TextView mFen;
    @BindView(R.id.m_edu)
    TextView mEdu;
    @BindView(R.id.man_lin)
    LinearLayout manLin;
    @BindView(R.id.m_tone)
    LinearLayout mTone;
    @BindView(R.id.m_ttwo)
    LinearLayout mTtwo;
    @BindView(R.id.m_tshare)
    LinearLayout mTshare;
    @BindView(R.id.r_reward)
    LinearLayout rReward;
    @BindView(R.id.m_pay)
    LinearLayout mPay;
    @BindView(R.id.daifu_num)
    TextView daifu_num;
    @BindView(R.id.mjf_xi)
    TextView mjfXi;
    @BindView(R.id.m_waito)
    LinearLayout mWaito;
    @BindView(R.id.daida_num)
    TextView daida_num;
    @BindView(R.id.mjd_ti)
    TextView mjdTi;
    @BindView(R.id.m_Waitt)
    LinearLayout mWaitt;
    @BindView(R.id.daishou_num)
    TextView daishou_num;
    @BindView(R.id.mjf_shen)
    TextView mjfShen;
    @BindView(R.id.m_finish)
    LinearLayout mFinish;
    @BindView(R.id.wancheng_num)
    TextView wancheng_num;
    @BindView(R.id.m_shop)
    LinearLayout mShop;
    @BindView(R.id.m_address)
    LinearLayout mAddress;
    @BindView(R.id.m_fu)
    LinearLayout mFu;
    @BindView(R.id.m_set)
    LinearLayout mSet;
    private TextView mMoney;
    private SharedPreferences sharedPreferences;
    private String mMone;
    private WebView mWeb;
    private String username;
    private String mone;
    private String mtwo;

    private String mUid;
    private String mShell;

    @Override
    protected presenter createPresenter() {
        return new presenter();
    }

    @Override
    protected int getFragmentView() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view) {
        sharedPreferences = mContext.getSharedPreferences(UserApi.SP, Context.MODE_PRIVATE);
        mMoney = view.findViewById(R.id.m_edu);
        rHeadNum = view.findViewById(R.id.r_head_num);
        rReward = view.findViewById(R.id.r_reward);
        rReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mineHeadNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> headmap = new HashMap<>();
                HashMap<String, Object> map = new HashMap<>();
                map.put("shell", mShell);
                map.put("uid", mUid);
                mPresenter.postData(UserApi.getSign, headmap, map, SignBean.class);
            }
        });

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && rHeadNum != null) {
            username = sharedPreferences.getString(UserApi.UserName, "");
            mone = sharedPreferences.getString(UserApi.credit3, "");
            mtwo = sharedPreferences.getString(UserApi.credit4, "");
            mUid = sharedPreferences.getString(UserApi.Uid, "");
            mShell = sharedPreferences.getString(UserApi.Shell, "");
            rHeadNum.setText(username);
            getData();
        }
    }

    @Override
    protected void getData() {
        username = sharedPreferences.getString(UserApi.UserName, "");
        mUid = sharedPreferences.getString(UserApi.Uid, "");
        mShell = sharedPreferences.getString(UserApi.Shell, "");
        //获取 消息数量
        HashMap<String, Object> headmap = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("shell", mShell);
        map.put("uid", mUid);
        map.put("phone", username);
        mPresenter.postData(UserApi.getMsgNumber, headmap, map, MsgNum.class);
    }

    @Override
    public void getData(Object object) {
        if (object instanceof SignBean) {
            SignBean signBean = (SignBean) object;
            if (signBean.getError() == 0) {
                Toast.makeText(mContext, signBean.getMsg(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, signBean.getMsg(), Toast.LENGTH_SHORT).show();
            }
        } else if (object instanceof MsgNum) {
            MsgNum bean = (MsgNum) object;
            if (bean.data != null) {
                int num = 0;
                for (MsgNum.Item item : bean.data) {
                    num = item.nums;
                    if ("0".equals(item.status)) {
                        daifu_num.setText("" + num);
                        daifu_num.setVisibility(num == 0 ? View.GONE : View.VISIBLE);
                    } else if ("1".equals(item.status)) {
                        daida_num.setText("" + num);
                        daida_num.setVisibility(num == 0 ? View.GONE : View.VISIBLE);
                    } else if ("2".equals(item.status)) {
                        daishou_num.setText("" + num);
                        daishou_num.setVisibility(num == 0 ? View.GONE : View.VISIBLE);
                    } else if ("3".equals(item.status)) {
                        wancheng_num.setText("" + num);
                        wancheng_num.setVisibility(num == 0 ? View.GONE : View.VISIBLE);
                    }
                }
            }
        }
    }


    @OnClick({R.id.mhead_image, R.id.m_jin, R.id.m_fen, R.id.man_lin, R.id.m_tone, R.id.m_ttwo, R.id.m_tshare, R.id.r_reward, R.id.m_pay, R.id.mjf_xi, R.id.m_waito, R.id.mjd_ti, R.id.m_Waitt, R.id.mjf_shen, R.id.m_finish, R.id.m_shop, R.id.m_address, R.id.m_fu, R.id.m_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mhead_image:
                break;
            case R.id.m_jin:
                startActivity(new Intent(getActivity(), MJListActivity.class));
                break;
            case R.id.m_fen:
                break;
            //实名认证
            case R.id.man_lin:

                break;
            //收款方式
            case R.id.m_tone:
                startActivity(new Intent(getActivity(), ShouKActivity.class));
                break;
            //安全中心
            case R.id.m_ttwo:
                startActivity(new Intent(getActivity(), AnQuanActivity.class));
                break;
            //分享下载
            case R.id.m_tshare:
                startActivity(new Intent(getActivity(), LoadDownActivity.class));
                break;
            //查看全部
            case R.id.r_reward:
                startActivity(new Intent(getActivity(), AllActivity.class));
                break;
            //代付款
            case R.id.m_pay:
                startActivity(new Intent(getActivity(), WaitActivity.class));
                break;
            case R.id.mjf_xi:
                break;
            //代发货
            case R.id.m_waito:
                startActivity(new Intent(getActivity(), FahuoActivity.class));
                break;
            case R.id.mjd_ti:
                break;
            //待收货
            case R.id.m_Waitt:
                startActivity(new Intent(getActivity(), ShouActivity.class));
                break;
            case R.id.mjf_shen:
                break;
            //已完成
            case R.id.m_finish:
                startActivity(new Intent(getActivity(), FinishActivity.class));
                break;
            //购物车
            case R.id.m_shop:
                startActivity(new Intent(getActivity(), ShopActivity.class));
                break;
            //收货地址
            case R.id.m_address:
                startActivity(new Intent(getActivity(), AddressActivity.class));
                break;
            //客服中心
            case R.id.m_fu:
                startActivity(new Intent(getActivity(), ShareActivity.class));
                break;
            //设置
            case R.id.m_set:
                startActivity(new Intent(getActivity(), SetActivity.class));
                break;
        }
    }
}

