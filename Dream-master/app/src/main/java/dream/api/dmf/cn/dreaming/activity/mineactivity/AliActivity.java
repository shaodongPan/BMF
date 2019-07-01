package dream.api.dmf.cn.dreaming.activity.mineactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.devio.takephoto.model.TImage;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.activity.TakePhotoActivity;
import dream.api.dmf.cn.dreaming.api.UserApi;
import dream.api.dmf.cn.dreaming.base.mvp.Contract;
import dream.api.dmf.cn.dreaming.base.mvp.presenter.presenter;
import dream.api.dmf.cn.dreaming.bean.BankBean;

public class AliActivity extends TakePhotoActivity implements Contract.Iview {
    TextView mTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_send)
    TextView tvSend;

    private String mUid;
    private String mShell;
    private String method = "alipay";
    private String mName;
    private String mPhone;
    private SharedPreferences sharedPreferences;
    private String aliname;
    private String aliCode;

    @Override
    public void getThisData() {

    }

    @Override
    public void getInitData() {
        sharedPreferences = getSharedPreferences(UserApi.SP, Context.MODE_PRIVATE);
        mUid = sharedPreferences.getString(UserApi.Uid, "");
        mShell = sharedPreferences.getString(UserApi.Shell, "");
        etName = findViewById(R.id.et_name);
        etCode = findViewById(R.id.et_code);
        aliname = sharedPreferences.getString(UserApi.ALI, "");
        aliCode = sharedPreferences.getString(UserApi.ALICode, "");
        etName.setText(aliname);
        etCode.setText(aliCode);
        mTitle = findViewById(R.id.tv_title);
        mTitle.setText("支付宝");
        mTitle.setTextSize(16);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public int getView() {
        return R.layout.activity_ali;
    }

    @Override
    protected presenter createP() {
        return new presenter();
    }

    @Override
    public void getData(Object object) {
        if (object instanceof BankBean) {
            BankBean bankBean = (BankBean) object;
            if (bankBean.error.equals("0")) {
                Toast.makeText(mContext, "绑定成功", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(mContext, bankBean.msg, Toast.LENGTH_LONG).show();
            }
        }

    }

    @OnClick({R.id.iv_back
            , R.id.iv_img
            , R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_img:
                showPhotoDialog();
                break;
            case R.id.tv_send:
                HashMap<String, Object> headmap = new HashMap<>();
                HashMap<String, Object> map = new HashMap<>();
                mName = etName.getText().toString().trim();
                mPhone = etCode.getText().toString().trim();
                sharedPreferences.edit().putString(UserApi.ALI, mName).commit();
                sharedPreferences.edit().putString(UserApi.ALICode, mPhone).commit();
                map.put("uid", mUid);
                map.put("shell", mShell);
                map.put("alipayhm", mName);
                map.put("alipay", mPhone);
                map.put("method", method);
                mPresenter.postData(UserApi.getBACK, headmap, map, BankBean.class);
                break;
        }
    }

    @Override
    public void takePhotoSuccess(Message msg) {
        super.takePhotoSuccess(msg);
        ivImg.setClickable(false);
        try {
            //默认只有一张图片，只取一张
            List<TImage> result = (List<TImage>) msg.obj;
            if (!isListEmpty(result)) {
                String path = result.get(0).getOriginalPath();
                ShowImage(path);
                uploadPhotoToService(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadPhotoToService(String path) {
        File file = new File(path);
        if (file.exists()) {
            upLoadPhoto(file);
        }
    }

    private void upLoadPhoto(File file) {

    }

    private void ShowImage(String path) {
        Glide.with(this)
                .load(path)
                .into(ivImg);
    }
}
