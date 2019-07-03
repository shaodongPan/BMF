package dream.api.dmf.cn.dreaming.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.activity.MoneyRecordActivity;
import dream.api.dmf.cn.dreaming.activity.moneyactivity.MoneyMLActivity;
import dream.api.dmf.cn.dreaming.activity.moneyactivity.MoneyQActivity;
import dream.api.dmf.cn.dreaming.activity.moneyactivity.MoneyluActivity;
import dream.api.dmf.cn.dreaming.api.UserApi;
import dream.api.dmf.cn.dreaming.base.BaseMvpFragment;
import dream.api.dmf.cn.dreaming.base.mvp.Contract;
import dream.api.dmf.cn.dreaming.base.mvp.presenter.presenter;
import dream.api.dmf.cn.dreaming.bean.IsLoginBean;

public class DMFFragment extends BaseMvpFragment<presenter> implements Contract.Iview {
    @BindView(R.id.m_money_lu)
    LinearLayout mMoneyLu;
    @BindView(R.id.m_money_qq)
    LinearLayout mMoneyQq;
    @BindView(R.id.m_money_q)
    LinearLayout mMoneyQ;
    Unbinder unbinder;
    private boolean username1;
    private TextView name;
    private TextView mname;
    private TextView one;
    private TextView two;
    private TextView three;
    private TextView four;


    public static Fragment newInstance() {
        return new DMFFragment();
    }

    @Override
    protected presenter createPresenter() {
        return new presenter();
    }

    @Override
    protected int getFragmentView() {
        return R.layout.fragment_dmf;
    }

    @Override
    protected void initView(View view) {
        name = view.findViewById(R.id.dname);
        mname = view.findViewById(R.id.d_name);
        one = view.findViewById(R.id.num_one);
        two = view.findViewById(R.id.num_two);
        three = view.findViewById(R.id.num_three);
        four = view.findViewById(R.id.num_four);
    }

    @Override
    protected void getData() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(UserApi.SP, Context.MODE_PRIVATE);
        username1 = sharedPreferences.getBoolean("Username", true);
        String mUid = sharedPreferences.getString(UserApi.Uid, "");
        String mShell = sharedPreferences.getString(UserApi.Shell, "");
        HashMap<String, Object> headmap = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", mUid);
        map.put("shell", mShell);
        mPresenter.postData(UserApi.getIsLogin, headmap, map, IsLoginBean.class);
    }

    @Override
    public void getData(Object object) {
        if (object instanceof IsLoginBean) {
            IsLoginBean isLoginBean = (IsLoginBean) object;
            if (username1) {
                name.setText("DMFB");
                mname.setText("FDMFB");
                one.setText(isLoginBean.stock_dmf);
                two.setText(isLoginBean.getBalanceDmf());
                three.setText(isLoginBean.credit3);
                four.setText(isLoginBean.regmoney_dmf);
            } else {
                one.setText(isLoginBean.stock);
                two.setText(isLoginBean.balance);
                three.setText(isLoginBean.credit4);
                four.setText(isLoginBean.regmoney);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.m_money_lu
            , R.id.m_money_qq
            , R.id.m_money_q})
    public void onViewClicked(View view) {
        switch (view.getId()) {
           /* //转账
            case R.id.m_money_zz:
                startActivity(new Intent(getActivity(),MoneyZActivity.class));
                break;*/
            //交易记录
            case R.id.m_money_lu:
                startActivity(new Intent(getActivity(), MoneyluActivity.class));
                break;
            //钱包
            case R.id.m_money_qq:
                startActivity(new Intent(getActivity(), MoneyMLActivity.class));
                break;
         /*    //HYT转入
            case R.id.m_money_r:
                startActivity(new Intent(getActivity(),MoneyHRActivity.class));
                break;
             //HYT转出
            case R.id.m_money_c:
                startActivity(new Intent(getActivity(),MoneyHCActivity.class));
                break;*/
            //金元转换
            case R.id.m_money_q:
                startActivity(new Intent(getActivity(), MoneyQActivity.class));
                break;
        }
    }
}
