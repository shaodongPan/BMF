package dream.api.dmf.cn.dreaming.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.activity.MoneyActivity;
import dream.api.dmf.cn.dreaming.api.UserApi;
import dream.api.dmf.cn.dreaming.base.BaseMvpFragment;
import dream.api.dmf.cn.dreaming.base.mvp.Contract;
import dream.api.dmf.cn.dreaming.base.mvp.presenter.presenter;
import dream.api.dmf.cn.dreaming.bean.IsLoginBean;
import dream.api.dmf.cn.dreaming.bean.TradingBean;
import dream.api.dmf.cn.dreaming.utils.LogUtils;

public class TradingFragment extends BaseMvpFragment<presenter> implements Contract.Iview {
    private String HYT = "HYT";
    private String DMF = "DMF";
    private String FROM = HYT;

    @BindView(R.id.r_butn)
    Button mButn;
    @BindView(R.id.r_butn2)
    Button mButn2;
    Unbinder unbinder;
    @BindView(R.id.r_oname)
    TextView mOname;
    @BindView(R.id.r_tname)
    TextView mTname;
    @BindView(R.id.spinner)
    AppCompatSpinner spinner;
    @BindView(R.id.ttt)
    TextView ttt;
    @BindView(R.id.t_newprice)
    TextView mNewprice;
    @BindView(R.id.t_addprice)
    TextView mAddprice;
    @BindView(R.id.h_newprice)
    TextView mhNewprice;
    @BindView(R.id.t_haddprice)
    TextView mHaddprice;
    private List<String> xValue = new ArrayList<>();
    //y轴坐标对应的数据
    private List<Float> yValue = new ArrayList<>();
    //折线对应的数据
    private Map<String, Float> value = new HashMap<>();
    private LineChart chartView;
    private TradingBean bean;
    private String ids;
    private RecyclerView mRecy;
    private String tex;
    private String te;
    private IsLoginBean isLoginBean;
    private SharedPreferences sharedPreferences;
    private String teup;
    private String hye;
    private String htoday;
    private String hupdate;
    private String unmae;
    private TextView pid;
    private String mUid;
    private String mShell;

    @Override
    protected presenter createPresenter() {
        return new presenter();
    }

    @Override
    protected int getFragmentView() {
        return R.layout.fragment_trading;
    }

    @Override
    protected void initView(View view) {
        chartView = view.findViewById(R.id.lineChart);
        sharedPreferences = mContext.getSharedPreferences(UserApi.SP, Context.MODE_PRIVATE);
        mUid = sharedPreferences.getString(UserApi.Uid, "");
        mShell = sharedPreferences.getString(UserApi.Shell, "");
        pid = view.findViewById(R.id.ttt);

        pid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshTitleView();
                requestChartData();
                switchType();
            }
        });
    }

    private void switchType() {
        if (FROM.equals(HYT)) {
            FROM = DMF;
        } else {
            FROM = HYT;
        }
    }

    private void refreshTitleView() {
        if (FROM.equals(HYT)) {
            pid.setText("DMF折线图");
        } else {
            pid.setText("HYT折线图");
        }
    }

    private void requestChartData() {
        HashMap<String, Object> headmap = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", mUid);
        map.put("shell", mShell);
        if (FROM.equals(HYT)) {
            map.put("type", "2");
        } else {
            map.put("type", "1");
        }
        mPresenter.postData(UserApi.getMoney, headmap, map, TradingBean.class);
    }

    @Override
    protected void getData() {
        ids = "1";
        pid.setText("HYT折线图");
        HashMap<String, Object> headmap = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", mUid);
        map.put("shell", mShell);
        map.put("type", ids);
        mPresenter.postData(UserApi.getMoney, headmap, map, TradingBean.class);

        requestLoginMessage();
    }

    @Override
    public void getData(Object object) {
        if (object instanceof TradingBean) {
            bean = (TradingBean) object;
            initData();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void getUserBean(IsLoginBean bean) {
        super.getUserBean(bean);
        mNewprice.setText(bean.dmf_day_price.today);
        mAddprice.setText(bean.dmf_day_price.updatemoney);
        mhNewprice.setText(bean.hyt_day_price.today);
        mHaddprice.setText(bean.hyt_day_price.updatemoney);

        tex = bean.dmf_day_price.yestoday;
        te = bean.dmf_day_price.today;
        teup = bean.dmf_day_price.updatemoney;
        hye = bean.hyt_day_price.yestoday;
        htoday = bean.hyt_day_price.today;
        hupdate = bean.hyt_day_price.updatemoney;

        unmae = sharedPreferences.getString(UserApi.ac_status, "");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.r_butn
            , R.id.r_butn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.r_butn:
                mOname.setText("DMF");
                mOname.setTextSize(16);
                if (unmae == null) {
                    return;
                }
                if (unmae.equals("2")) {
                    Intent intent = new Intent(getActivity(), MoneyActivity.class);
                    intent.putExtra("name", tex);
                    intent.putExtra("today", te);
                    intent.putExtra("update", teup);
                    intent.putExtra("uname", "1");
                    sharedPreferences.edit().putBoolean("Username", true).commit();
                    startActivity(intent);
                    break;
                } else {
                    Toast.makeText(mContext, "未激活", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.r_butn2:
                if (unmae == null) {
                    return;
                }
                if (unmae.equals("2")) {
                    Intent intent2 = new Intent(getActivity(), MoneyActivity.class);
                    intent2.putExtra("name", hye);
                    intent2.putExtra("today", htoday);
                    intent2.putExtra("update", hupdate);
                    intent2.putExtra("uname", "2");
                    sharedPreferences.edit().putBoolean("Username", false).commit();
                    startActivity(intent2);
                    break;
                } else {
                    Toast.makeText(mContext, "未激活", Toast.LENGTH_LONG).show();
                }
                break;

        }
    }

    public void initData() {
        clearData();
        //x轴坐标对应的数据
        for (int i = 0; i < bean.data.size(); i++) {
            TradingBean.DataBean dataBean = bean.data.get(i);
            xValue.add(dataBean.date);
            yValue.add(Float.valueOf(dataBean.price));
            value.put(dataBean.date, Float.valueOf(dataBean.amount));
        }

        float maxY = 0f;
        for (TradingBean.DataBean dataBean : bean.data) {
            float price = Float.valueOf(dataBean.price);
            if (maxY <= price) {
                maxY = price;
            }
        }
        YAxis leftYAxis = chartView.getAxisLeft();
        YAxis rightYAxis = chartView.getAxisRight();
        leftYAxis.setAxisMinimum(0);
        leftYAxis.setAxisMaximum(maxY + 2);
        rightYAxis.setEnabled(false);

        XAxis xAxis = chartView.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return xValue.get((int) value);
            }
        });

        ArrayList<Entry> pointList = new ArrayList<>();

        for (int i = 0; i < yValue.size(); i++) {
            pointList.add(new Entry(i, yValue.get(i)));
        }

        Legend legend = chartView.getLegend();
        legend.setEnabled(false);
        LineDataSet dataSet = new LineDataSet(pointList, "");
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        LineData lineData = new LineData(dataSet);
        chartView.setData(lineData);
        chartView.invalidate();
    }

    private void clearData() {
        value.clear();
        yValue.clear();
        xValue.clear();
    }
}
