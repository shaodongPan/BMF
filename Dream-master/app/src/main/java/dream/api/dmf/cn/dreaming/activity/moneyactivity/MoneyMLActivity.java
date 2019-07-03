package dream.api.dmf.cn.dreaming.activity.moneyactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.BindView;
import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.activity.MoneyRecordActivity;
import dream.api.dmf.cn.dreaming.activity.mineactivity.MJListActivity;
import dream.api.dmf.cn.dreaming.api.UserApi;
import dream.api.dmf.cn.dreaming.base.BaseMvpActivity;
import dream.api.dmf.cn.dreaming.base.mvp.Contract;
import dream.api.dmf.cn.dreaming.base.mvp.presenter.presenter;
import dream.api.dmf.cn.dreaming.bean.IsLoginBean;

public class MoneyMLActivity extends BaseMvpActivity<presenter> implements Contract.Iview, View.OnClickListener {

    public static final String TYPE = "TYPE";
    public static final String C = "C";
    public static final String TYPE_HTY = "18";
    public static final String TYPE_FHTY = "4";

    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.iv_back)
    ImageView mBack;

    private TextView mOne, mTwo, mThree, mFour;
    private String mUid;
    private String mShell;
    private Boolean username1;

    @Override
    public void getThisData() {
        HashMap<String, Object> headmap = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", mUid);
        map.put("shell", mShell);
        mPresenter.postData(UserApi.getIsLogin, headmap, map, IsLoginBean.class);
    }

    @Override
    public void getInitData() {
        mOne = findViewById(R.id.mone);
        mTwo = findViewById(R.id.mtwo);
        mThree = findViewById(R.id.mthree);
        mFour = findViewById(R.id.mfour);
        mBack = findViewById(R.id.iv_back);
        mTitle = findViewById(R.id.tv_title);
        mOne.setOnClickListener(this);
        mTwo.setOnClickListener(this);
        mThree.setOnClickListener(this);
        mFour.setOnClickListener(this);
        mTitle.setText("钱包记录");
        mTitle.setTextSize(16);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences(UserApi.SP, Context.MODE_PRIVATE);
        mUid = sharedPreferences.getString(UserApi.Uid, "");
        mShell = sharedPreferences.getString(UserApi.Shell, "");
        username1 = sharedPreferences.getBoolean("Username", true);

    }

    @Override
    public int getView() {
        return R.layout.activity_money_ml;
    }

    @Override
    protected presenter createP() {
        return new presenter();
    }

    @Override
    public void getData(Object object) {
        if (object instanceof IsLoginBean) {
            IsLoginBean isLoginBean = (IsLoginBean) object;
            if (username1 == true) {
                mOne.setText(isLoginBean.stock_dmf);
                mTwo.setText(isLoginBean.balance_dmf);
                mThree.setText(isLoginBean.regmoney_dmf);
                mFour.setText(isLoginBean.credit3);
            } else if (username1 == false) {
                mOne.setText(isLoginBean.stock);
                mTwo.setText(isLoginBean.balance);
                mThree.setText(isLoginBean.regmoney);
                mFour.setText(isLoginBean.credit4);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mone:
                startMoneyRecordActivity(TYPE_HTY);
                break;
            case R.id.mtwo:
                startMoneyRecordActivity(TYPE_FHTY);
                break;
            case R.id.mthree:

                break;
            case R.id.mfour:
                startActivity(new Intent(mContext, MJListActivity.class));
                finish();
                break;
        }
    }

    private void startMoneyRecordActivity(String type) {
        Intent intent = new Intent();
        intent.setClass(mContext, MoneyRecordActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, type);
        bundle.putString(C, username1 ? "2" : "1");
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
