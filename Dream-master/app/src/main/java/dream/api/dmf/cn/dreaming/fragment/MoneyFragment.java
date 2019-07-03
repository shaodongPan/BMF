package dream.api.dmf.cn.dreaming.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.api.UserApi;
import dream.api.dmf.cn.dreaming.base.BaseMvpFragment;
import dream.api.dmf.cn.dreaming.base.mvp.Contract;
import dream.api.dmf.cn.dreaming.base.mvp.presenter.presenter;
import dream.api.dmf.cn.dreaming.bean.IsLoginBean;

public class MoneyFragment extends BaseMvpFragment<presenter> implements Contract.Iview {

    private ArrayList<Fragment> list;
    private List mHytlist;

    private String peice;
    private String hToday;
    private String hye;
    private String yUpdate;
    private String updatep;
    private String dmfday;

    @BindView(R.id.tGroup)
    RadioGroup mGroup;
    @BindView(R.id.m_button1)
    RadioButton mButton1;
    @BindView(R.id.m_button2)
    RadioButton mButton2;
    @BindView(R.id.m_pager)
    ViewPager mPage;
    @BindView(R.id.yesteday)
    TextView mYesDay;
    @BindView(R.id.today)
    TextView mToDay;
    @BindView(R.id.updatemoney)
    Button mUpMoney;

    private boolean username1;
    private SharedPreferences sharedPreferences;

    public static Fragment newInstance() {
        MoneyFragment fragment = new MoneyFragment();
        return fragment;
    }

    @Override
    protected presenter createPresenter() {
        return new presenter();
    }

    @Override
    protected int getFragmentView() {
        return R.layout.fragment_money;
    }

    @Override
    protected void initView(View view) {
        sharedPreferences = getActivity().getSharedPreferences(UserApi.SP, Context.MODE_PRIVATE);
        username1 = sharedPreferences.getBoolean("Username", true);

        initData();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void getData() {
        requestLoginMessage();
    }


    @Override
    public void getUserBean(IsLoginBean bean) {
        super.getUserBean(bean);

        dmfday = sharedPreferences.getString(UserApi.dmf_day_Today, "");
        peice = sharedPreferences.getString(UserApi.dmf_day_price, "0.0");
        updatep = sharedPreferences.getString(UserApi.updatemoney, "");
        hToday = sharedPreferences.getString(UserApi.HTODAY, "");
        hye = sharedPreferences.getString(UserApi.HYE, "");
        yUpdate = sharedPreferences.getString(UserApi.HUPDATE, "");

        if (username1) {
            mToDay.setText(dmfday);
            if (Double.parseDouble(updatep) < 0) {

                mUpMoney.setText("-" + cutDoubleNumber(Double.parseDouble(updatep)));
            } else {
                mUpMoney.setText("+" + cutDoubleNumber(Double.parseDouble(updatep)));
            }

            mYesDay.setText(peice);

        } else {
            mToDay.setText(hToday);
            if (Double.parseDouble(yUpdate) < 1) {
                mUpMoney.setText("-" + cutDoubleNumber(Double.parseDouble(yUpdate)));
            } else {
                mUpMoney.setText("+" + cutDoubleNumber(Double.parseDouble(yUpdate)));
            }
            //mUpMoney.setText(yUpdate);
            mYesDay.setText(hye);

        }
    }


    protected void initData() {
        list = new ArrayList<>();
        SellFragment frag_01 = new SellFragment();
        BuyFragment frag_02 = new BuyFragment();
        list.add(frag_01);
        list.add(frag_02);
        mPage.setAdapter(new FragmentPagerAdapter(this.getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        mGroup.check(mGroup.getChildAt(0).getId());
        mPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mGroup.check(mGroup.getChildAt(i).getId());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.m_button1:
                        mPage.setCurrentItem(0);
                        break;
                    case R.id.m_button2:
                        mPage.setCurrentItem(1);
                        break;

                }
            }
        });

    }

    public static String cutDoubleNumber(Double number) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.0");
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(number);
    }

    @Override
    public void getData(Object object) {

    }
}
