package dream.api.dmf.cn.dreaming;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hjm.bottomtabbar.BottomTabBar;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import dream.api.dmf.cn.dreaming.activity.CrowdActivity;
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
import dream.api.dmf.cn.dreaming.utils.CustomHelper;
import dream.api.dmf.cn.dreaming.utils.WeakHandler;

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
        return R.layout.activity_main;
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

        if (user == false) {
            startActivity(new Intent(MainActivity.this, CrowdActivity.class));
        }

        mTabbar = findViewById(R.id.mfragment);
        mTabbar.init(getSupportFragmentManager())
                .setImgSize(50, 50)
                .setFontSize(8)
                .setTabPadding(4, 6, 10)
                .setChangeColor(Color.RED, Color.DKGRAY)
                .addTabItem("首页", R.drawable.hoem_butn, HomeFragment.class)
                .addTabItem("钱包", R.drawable.trading_butn, TradingFragment.class)
                .addTabItem("商城", R.drawable.center_butn, CenterFragment.class)
                .addTabItem("奖励", R.drawable.reward_butn, RewardFragment.class)
                .addTabItem("我的", R.drawable.mine_butn, MineFragment.class)
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
//        switch (requestCode) {
//            case RC_CAMERA:
//                Bitmap bitmap = data.getParcelableExtra("data");
//                Uri uri1 = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
//                if (uri1 != null) {
//                    mZImage.setImageURI(uri1);
//                    //调用工具类将uri图片转为path
//                    String path = ImageUtil.getPath(this, uri1);
//                    if (path != null) {
//                        //将图片转为file
//                        File file = new File(path);
//                        //调用P层
//                        HashMap<String, Object> headmap = new HashMap<>();
//                        HashMap<String, Object> map = new HashMap<>();
//                        map.put("shell", mShell);
//                        map.put("uid", mUid);
//                        map.put("type", rcard);
//                        /*   mPresenter.postHeadData*/
//                        // mPresenter.postMorePicture(UserApi.getPhoneImage,headmap,map,file,RenBean.class);
//                        /*  mPresenter.postHeadData(UserApi.getPhoneImage,headmap,file,RenBean.class);*/
//                        // mPresenter.postData(UserApi.getPhoneImage,headmap,map,RenBean.class);
//                        HashMap<String, Object> headsmap = new HashMap<>();
//                        HashMap<String, Object> maps = new HashMap<>();
//                        map.put("shell", mShell);
//                        map.put("uid", mUid);
//                        map.put("type", fcard);
//                        map.put("imgFile", file);
//                        //mPresenter.postData(UserApi.getPhoneImage,headsmap,maps,RenBean.class);
//                    }
//                }
//              /*  Uri url = data.getParcelableExtra("data");
//                mZImage.setImageURI(Uri.parse(url.toString()));
//                HashMap<String,Object> headmap=new HashMap<>();
//                HashMap<String,Object>map=new HashMap<>();
//                map.put("")
//*/
//                Toast.makeText(MainActivity.this, "相机回调", Toast.LENGTH_SHORT).show();
//                break;
//            case RC_ALBUM:
//                Uri uri = data.getData();
//                if (uri != null) {
//                    mZImage.setImageURI(uri);
//                    //调用工具类将uri图片转为path
//                    String path = ImageUtil.getPath(this, uri);
//
//                    if (path != null) {
//                        //将图片转为file
//                        File file = new File(path);
//                        HashMap<String, Object> headmap = new HashMap<>();
//                        HashMap<String, Object> map = new HashMap<>();
//                        map.put("shell", mShell);
//                        map.put("uid", mUid);
//                        map.put("type", rcard);
//                        map.put("imgFile", file);
//                        mPresenter.postData(UserApi.getPhoneImage, headmap, map, RenBean.class);
//                        HashMap<String, Object> headsmap = new HashMap<>();
//                        HashMap<String, Object> maps = new HashMap<>();
//                        map.put("shell", mShell);
//                        map.put("uid", mUid);
//                        map.put("type", fcard);
//                        map.put("imgFile", file);
//                        mPresenter.postData(UserApi.getPhoneImage, headsmap, maps, RenBean.class);
//                    }
//                }
//                break;
//        }
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
