package dream.api.dmf.cn.dreaming.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.api.UserApi;
import dream.api.dmf.cn.dreaming.base.BaseMvpFragment;
import dream.api.dmf.cn.dreaming.base.mvp.Contract;
import dream.api.dmf.cn.dreaming.base.mvp.presenter.presenter;
import dream.api.dmf.cn.dreaming.bean.DuSellBean;
import dream.api.dmf.cn.dreaming.bean.IsLoginBean;
import dream.api.dmf.cn.dreaming.utils.BankDialog;
import dream.api.dmf.cn.dreaming.utils.BuyNumDialog;
import dream.api.dmf.cn.dreaming.utils.ScreenSizeUtil;

public class SellFragment extends BaseMvpFragment<presenter> implements Contract.Iview {

    Unbinder unbinder;

    @BindView(R.id.s_price)
    TextView sPrice;
    @BindView(R.id.ss_num)
    TextView ssNum;
    @BindView(R.id.s_nump)
    TextView sNump;
    @BindView(R.id.s_banck)
    TextView sBanck;
    @BindView(R.id.ll_pay_info)
    LinearLayout llPayInfo;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.ed_one)
    TextView edOne;
    @BindView(R.id.ed_num)
    TextView edNum;
    @BindView(R.id.s_edsell)
    TextView eEdsell;
    @BindView(R.id.s_num)
    TextView sNum;
    EditText sRpass;
    CheckBox loginExe;
    @BindView(R.id.s_butn)
    Button sButn;
    Unbinder unbinder1;
    /*   TextView sNums;*/
    private String dmfday;
    private String nums;
    private List<String> numList;
    private List list;
    private String mEd;
    private String mUid;
    private String mShell;
    private String mEdx;
    private String paytype;
    private TextView mPrice;
    private TextView sNums;
    private Double Ter;
    private boolean username1;
    private LinearLayout layout;
    private String number;
    private String back;
    private TextView edone;
    private TextView edtwo;
    private IsLoginBean isLoginBean;
    private SharedPreferences sharedPreferences;

    private String dmfpmoney;
    private String tdmfpmoney;
    private String jy2;
    private String toshopmoney;

    private int mDp;

    @Override
    protected presenter createPresenter() {
        return new presenter();
    }

    @Override
    protected int getFragmentView() {

        return R.layout.fragment_sell;
    }

    @Override
    protected void initView(View view) {

        mPrice = view.findViewById(R.id.s_price);
        sharedPreferences = getActivity().getSharedPreferences(UserApi.SP, Context.MODE_PRIVATE);
        mUid = sharedPreferences.getString(UserApi.Uid, "");
        mShell = sharedPreferences.getString(UserApi.Shell, "");

        username1 = sharedPreferences.getBoolean("Username", true);
        edone = view.findViewById(R.id.ed_one);
        edtwo = view.findViewById(R.id.ed_num);


        dmfday = sharedPreferences.getString(UserApi.dmf_day_Today, "");

        dmfpmoney = sharedPreferences.getString(UserApi.dmfpmoney, "");
        tdmfpmoney = sharedPreferences.getString(UserApi.tdmfpmoney, "");
        jy2 = sharedPreferences.getString(UserApi.jy2, "");
        toshopmoney = sharedPreferences.getString(UserApi.toshopmoney, "");

        mEd = sharedPreferences.getString(UserApi.DMFED, "");
        nums = sharedPreferences.getString(UserApi.DmfNUm, "");

        if (nums.length() > 1) {
            String[] splitNums = nums.substring(1, nums.length() - 1).replace("\"", "").split(",");
            numList = Arrays.asList(splitNums);
        } else {
            numList = new ArrayList<>();
        }

        loginExe = view.findViewById(R.id.login_exe);
        sRpass = view.findViewById(R.id.s_rpass);
        list = Arrays.asList(getResources().getStringArray(R.array.bank));

        if (username1) {
            mPrice.setText(dmfpmoney);
        } else {
            mPrice.setText(toshopmoney);
        }

        if (sRpass.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            //密码可见,点击之后设置成不可见的
            sRpass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        } else {
            //不可见设置成可见
            sRpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        loginExe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    if (sRpass.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                        //不可见设置成可见
                        sRpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                } else {
                    //密码可见,点击之后设置成不可见的
                    sRpass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                }
            }
        });

        mDp = ScreenSizeUtil.Dp2Px(mContext.getApplicationContext(), 10);
    }

    @Override
    protected void getData() {


        ssNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // new BuyNumDialog(mContext, numList, sNums);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String mNum = ssNum.getText().toString();
                Double myu = (Double.parseDouble(mNum) * Double.parseDouble(mEd));
                double num = Double.parseDouble(mNum);
                sNump.setText(num + myu + "");
                //sNum.setText((Double.parseDouble(mNum)*);

                String s1 = cutDoubleNumber(Double.valueOf(Double.parseDouble(mNum) * Double.parseDouble(dmfpmoney) + ""));
                String s2 = cutDoubleNumber(Double.valueOf(Double.parseDouble(mNum) * Double.parseDouble(toshopmoney) + ""));
                //cutDoubleNumber(Double.parseDouble(dmfday));

                if (username1) {
                    sNum.setText(s1);
                } else {
                    sNum.setText(s2);
                }
                //Toast.makeText(mContext, "111111111", Toast.LENGTH_SHORT).show();
                //String mNum = ssNum.getText().toString();
                //sNump.setText(Double.parseDouble(mEd) * Double.parseDouble(ssNum) + "");
                // sNum.setText((Double.parseDouble(mNum) * Double.parseDouble(dmfday) + (Double.parseDouble(mEd) * Double.parseDouble(String.valueOf(mNum))) + ""));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        HashMap<String, Object> headmap = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", mUid);
        map.put("shell", mShell);
        mPresenter.postData(UserApi.getIsLogin, headmap, map, IsLoginBean.class);
    }

    public IsLoginBean getIsLoginBean() {

        return isLoginBean;
    }

    @Override
    public void getData(Object object) {
        if (!isAdded()) {
            return;
        }
        if (object instanceof DuSellBean) {
            DuSellBean duSellBean = (DuSellBean) object;
            if (duSellBean.error.equals("0")) {
                Toast.makeText(mContext, "卖出成功", Toast.LENGTH_SHORT).show();
                sNump.setText("");
                sNum.setText("");
                sRpass.setText("");
                sBanck.setText("");
            } else {
                Toast.makeText(mContext, duSellBean.msg, Toast.LENGTH_SHORT).show();
            }
        }
        if (object instanceof IsLoginBean) {
            isLoginBean = (IsLoginBean) object;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);

        String done = sharedPreferences.getString(UserApi.STock_mdf, "0");
        String dfour = sharedPreferences.getString(UserApi.credit3, "0");
        String hone = sharedPreferences.getString(UserApi.STOCK, "0");
        String hfour = sharedPreferences.getString(UserApi.credit4, "0");
        if (username1) {
            tvType.setText("DMF");
            edOne.setText(done);
            eEdsell.setText(dfour);
            sButn.setText("卖出(DMF)");
            //mPrice.setText(isLoginBean.dmfpmoney);
        } else {
            tvType.setText("HYT");
            edOne.setText(hone);
            eEdsell.setText(hfour);
            sButn.setText("卖出(HYTB)");
            //mPrice.setText(isLoginBean.toshopmoney);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }

    @OnClick({R.id.ss_num, R.id.s_nump, R.id.s_banck, R.id.s_num, R.id.s_rpass, R.id.login_exe, R.id.s_edsell, R.id.s_butn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //点击
            case R.id.ss_num:
                new BuyNumDialog(mContext, numList, ssNum);
                break;
            //实付数量
            case R.id.s_nump:

                break;
            case R.id.s_banck:
                BankDialog dialog = new BankDialog(mContext, list, sBanck);
                dialog.setCallBack(new BankDialog.BandDialogCallBack() {
                    @Override
                    public void select(String select) {
                        addPayInfoLayout(select);
                    }
                });
                if (list.get(0).equals("银行卡")) {
                    paytype = "1";
                    return;
                } else if (list.get(1).equals("支付宝")) {
                    paytype = "2";
                    return;
                } else if (list.get(2).equals("微信")) {
                    paytype = "3";
                    return;
                }
                break;
            //收款
            case R.id.s_num:
                break;
            //安全密码
            case R.id.s_rpass:
                break;
            case R.id.login_exe:
                break;
            case R.id.s_edsell:
                break;
            case R.id.s_butn:
                if (username1 == true) {
                    HashMap<String, Object> headmap = new HashMap<>();
                    HashMap<String, Object> map = new HashMap<>();
                    number = ssNum.getText().toString().trim();
                    map.put("uid", mUid);
                    map.put("shell", mShell);
                    map.put("howmoney", number);
                    map.put("paytype", paytype);
                    mPresenter.postData(UserApi.getMDFSELL, headmap, map, DuSellBean.class);

                } else if (username1 == false) {
                    HashMap<String, Object> headmap = new HashMap<>();
                    HashMap<String, Object> map = new HashMap<>();
                    String number = ssNum.getText().toString().trim();
                    map.put("uid", mUid);
                    map.put("shell", mShell);
                    map.put("howmoney", number);
                    map.put("paytype", paytype);
                    mPresenter.postData(UserApi.getMCSell, headmap, map, DuSellBean.class);
                }

                break;
        }
    }

    private void addPayInfoLayout(String select) {
//        if (select.equals())
//        TextView textView = getMessageTextView(select);
//
//        llPayInfo.addView(textView);
    }

    private TextView getMessageTextView(String select) {
        TextView textView = new TextView(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(mDp, mDp, 0, 0);
        textView.setLayoutParams(layoutParams);
        textView.setText(select);
        textView.setTextSize(16.0f);
        return textView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {// 不在最前端界面显示
        } else {// 重新显示到最前端中
            //initDBView();
        }
    }

    public static String cutDoubleNumber(Double number) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.0");
        df.setRoundingMode(RoundingMode.FLOOR);
        String d = df.format(number);
        return d;
    }
}
