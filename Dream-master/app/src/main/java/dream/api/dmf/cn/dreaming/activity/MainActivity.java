package dream.api.dmf.cn.dreaming.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.hjm.bottomtabbar.BottomTabBar;

import java.util.HashMap;

import butterknife.ButterKnife;
import dream.api.dmf.cn.dreaming.api.UserApi;
import dream.api.dmf.cn.dreaming.base.BaseMvpActivity;
import dream.api.dmf.cn.dreaming.base.mvp.Contract;
import dream.api.dmf.cn.dreaming.base.mvp.presenter.presenter;
import dream.api.dmf.cn.dreaming.bean.IsLoginBean;
import dream.api.dmf.cn.dreaming.bean.ManBean;
import dream.api.dmf.cn.dreaming.bean.RenBean;
import dream.api.dmf.cn.dreaming.fragment.CenterFragment;
import dream.api.dmf.cn.dreaming.fragment.HomeFragment;
import dream.api.dmf.cn.dreaming.fragment.MineFragment;
import dream.api.dmf.cn.dreaming.fragment.RewardFragment;
import dream.api.dmf.cn.dreaming.fragment.TradingFragment;

public class MainActivity extends BaseMvpActivity<presenter> implements Contract.Iview {


    private BottomTabBar mTabbar;
    private SharedPreferences sharedPreferences;
    private String usern;
    private String mUid;
    private String mShell;
    private String mIdcard;

    private final int RC_CAMERA = 1;
    private final int RC_ALBUM = 2;
    private String fcard;

    private static final String TAG_EXIT = "exit";
    private TextView textView;
    private int i = 3;


    @Override
    public int getView() {
        return dream.api.dmf.cn.dreaming.R.layout.activity_main;
    }

    @Override
    protected presenter createP() {
        return new presenter();
    }

    @Override
    public void getData(Object object) {
        if (object instanceof IsLoginBean) {
            IsLoginBean isLoginBean = (IsLoginBean) object;
            sharedPreferences.edit().putString(UserApi.idcard, (String) isLoginBean.idcard).commit();
        }
        if (object instanceof ManBean) {
            ManBean manBean = (ManBean) object;
            if (manBean.error.equals("0")) {
                Toast.makeText(mContext, "实名认证成功", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mContext, manBean.msg, Toast.LENGTH_LONG).show();
            }
        }
        if (object instanceof RenBean) {
            RenBean renBean = (RenBean) object;
            if (renBean.error == 0) {
                //Toast.makeText(mContext,renBean.message,Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mContext, renBean.message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

    }

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

        sharedPreferences = mContext.getSharedPreferences(UserApi.SP, Context.MODE_PRIVATE);
        mUid = sharedPreferences.getString(UserApi.Uid, "");
        mShell = sharedPreferences.getString(UserApi.Shell, "");
        String username = sharedPreferences.getString(UserApi.UserName, "");
        mIdcard = sharedPreferences.getString(UserApi.idcard, "");
        boolean user = sharedPreferences.getBoolean("user", false);

//        if (user == false) {
//            startActivity(new Intent(MainActivity.this, CrowdActivity.class));
//        }

        mTabbar = findViewById(dream.api.dmf.cn.dreaming.R.id.mfragment);
        mTabbar.init(getSupportFragmentManager())
                .setImgSize(50, 50)
                .setFontSize(8)
                .setTabPadding(4, 6, 10)
                .setChangeColor(Color.RED, Color.DKGRAY)
                .addTabItem("首页", dream.api.dmf.cn.dreaming.R.drawable.hoem_butn, HomeFragment.class)
                .addTabItem("钱包", dream.api.dmf.cn.dreaming.R.drawable.trading_butn, TradingFragment.class)
                .addTabItem("商城", dream.api.dmf.cn.dreaming.R.drawable.center_butn, CenterFragment.class)
                .addTabItem("奖励", dream.api.dmf.cn.dreaming.R.drawable.reward_butn, RewardFragment.class)
                .addTabItem("我的", dream.api.dmf.cn.dreaming.R.drawable.mine_butn, MineFragment.class)
                .isShowDivider(false)
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, String name) {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    private long exitTime = 0;

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(mContext, "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            this.finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            boolean isExit = intent.getBooleanExtra(TAG_EXIT, false);
            if (isExit) {
                this.finish();
            }
        }
    }

}
