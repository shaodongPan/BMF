package dream.api.dmf.cn.dreaming.activity.mineactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.devio.takephoto.model.TImage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.activity.TakePhotoActivity;
import dream.api.dmf.cn.dreaming.api.UserApi;
import dream.api.dmf.cn.dreaming.base.BaseMvpActivity;
import dream.api.dmf.cn.dreaming.base.mvp.Contract;
import dream.api.dmf.cn.dreaming.base.mvp.presenter.presenter;
import dream.api.dmf.cn.dreaming.bean.BankBean;
import dream.api.dmf.cn.dreaming.bean.PhotoBean;
import okhttp3.MultipartBody;

public class WeChatActivity extends TakePhotoActivity implements Contract.Iview {
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
    private String method = "wechat";
    private String mName;
    private String mCode;
    private SharedPreferences sharedPreferences;
    private String mName1;
    private String mNameCode;

    @Override
    public void getThisData() {

    }

    @Override
    public void getInitData() {
        sharedPreferences = getSharedPreferences(UserApi.SP, Context.MODE_PRIVATE);
        mUid = sharedPreferences.getString(UserApi.Uid, "");
        mShell = sharedPreferences.getString(UserApi.Shell, "");
        mName1 = sharedPreferences.getString(UserApi.WECHAT, "");
        mNameCode = sharedPreferences.getString(UserApi.WECHATCode, "");
        etName = findViewById(R.id.et_name);
        etCode = findViewById(R.id.et_code);
        etName.setText(mName1);
        etCode.setText(mNameCode);
        mTitle = findViewById(R.id.tv_title);
        mTitle.setText("微信");
        mTitle.setTextSize(16);
    }

    @Override
    public int getView() {
        return R.layout.activity_we_chat;
    }

    @Override
    protected presenter createP() {
        return new presenter();
    }

    @Override
    public void getData(Object object) {
        if (isFinishing()) {
            return;
        }
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

    @Override
    public void getPhotoData(Object o) {
        super.getPhotoData(o);
        checkResultIsPhotoUpload(o);
    }

    private void checkResultIsPhotoUpload(Object o) {
        if (isFinishing()) {
            return;
        }

        if (o instanceof PhotoBean) {
            PhotoBean bankBean = (PhotoBean) o;
            int error = bankBean.getError();
            if (error == 0) {
                Toast.makeText(mContext, "上传成功", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mContext, "上传失败,请重试", Toast.LENGTH_LONG).show();
            }
        }
    }

    @OnClick({R.id.iv_back, R.id.iv_img, R.id.tv_send})
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
                mCode = etCode.getText().toString().trim();
                sharedPreferences.edit().putString(UserApi.WECHAT, mName).commit();
                sharedPreferences.edit().putString(UserApi.WECHATCode, mCode).commit();
                map.put("uid", mUid);
                map.put("shell", mShell);
                map.put("wechathm", mName);
                map.put("wechat", mCode);
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
        postHeadData(UserApi.getPhoneImage, gen(file), PhotoBean.class);
    }

    public List<MultipartBody.Part> gen(File file) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        parts.add(toRequestBodyOfText("shell", mShell));
        parts.add(toRequestBodyOfText("uid", mUid));
        parts.add(toRequestBodyOfImage("imgFile", file));
        parts.add(toRequestBodyOfText("type", "wechat"));
        return parts;
    }

    private void ShowImage(String path) {
        Glide.with(this)
                .load(path)
                .into(ivImg);
    }
}
