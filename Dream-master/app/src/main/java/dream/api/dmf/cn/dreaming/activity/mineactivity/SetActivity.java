package dream.api.dmf.cn.dreaming.activity.mineactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.activity.LoginActivity;
import dream.api.dmf.cn.dreaming.api.ApiService;
import dream.api.dmf.cn.dreaming.api.UserApi;
import dream.api.dmf.cn.dreaming.app.MyApp;
import dream.api.dmf.cn.dreaming.bean.ManBean;
import dream.api.dmf.cn.dreaming.bean.RenBean;
import dream.api.dmf.cn.dreaming.utils.CustomHelper;
import dream.api.dmf.cn.dreaming.utils.RetrofitUtils;
import dream.api.dmf.cn.dreaming.utils.WeakHandler;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;

public class SetActivity extends AppCompatActivity implements TakePhoto.TakeResultListener, InvokeListener {
    private static final int TAKE_PHOTO_SUCCESS = 10000;
    private static final int TAKE_PHOTO_FAILURE = 10001;
    private static final int TAKE_PHOTO_CANCEL = 10002;
    private int FROM_POSITIVE = 1; //正面
    private int FROM_NEGATIVE = 2; //反面
    private int FROM = FROM_POSITIVE;

    private AlertDialog alertDialog;
    private TextView mtitle;
    private ImageView mBack;
    private TextView mBa;
    private SharedPreferences sharedPreferences;
    private TextView mMode;
    private TextView mwallet;
    private TextView account;
    private TakePhoto takePhoto;
    private CustomHelper mHelper;
    private InvokeParam invokeParam;
    private ImageView mZImage;
    private ImageView mFImage;
    private ImageView mPositiveView;
    private ImageView mNegativeView;
    private EditText mName;
    private String rcard;
    private String mUid;
    private String mShell;

    ApiService apiService = RetrofitUtils.getInstance().create(ApiService.class);

    private WeakHandler mHandler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case TAKE_PHOTO_SUCCESS:
                    refreshPhotoLayout(msg);
                    break;
                case TAKE_PHOTO_FAILURE:
                    break;
                case TAKE_PHOTO_CANCEL:
                    break;
            }
            return false;
        }
    });

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        sharedPreferences = getSharedPreferences(UserApi.SP, Context.MODE_PRIVATE);
        mUid = sharedPreferences.getString(UserApi.Uid, "");
        mShell = sharedPreferences.getString(UserApi.Shell, "");


        mtitle = findViewById(R.id.tv_title);
        account = findViewById(R.id.tv_account);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SetActivity.this, AnQuanActivity.class));
            }
        });
        mtitle.setText("设置");
        mtitle.setTextSize(18);
        sharedPreferences = getSharedPreferences(UserApi.SP, MODE_PRIVATE);
        mBack = findViewById(R.id.iv_back);
        mMode = findViewById(R.id.tv_mode);
        mwallet = findViewById(R.id.tv_clear);
        mwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().clear().commit();
                Toast.makeText(SetActivity.this, "清除成功", Toast.LENGTH_LONG).show();

            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SetActivity.this, ShouKActivity.class));
                finish();
            }
        });
        mBa = findViewById(R.id.tv_exit);
        mBa.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       AlertDialog.Builder builder = new AlertDialog.Builder(SetActivity.this);
                                       builder.setTitle("设置");
                                       builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                           public void onClick(DialogInterface dialog, int which) {
                                               startActivity(new Intent(SetActivity.this, LoginActivity.class));
                                               finish();
                                           }
                                       });
                                       builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                                           public void onClick(DialogInterface dialog, int which) {
                                               //Toast.makeText(SetActivity.this, "我是消极", Toast.LENGTH_LONG).show();

                                           }
                                       });


                                       builder.show();

                                       sharedPreferences.edit().clear().commit();
                                       MyApp.getInstance().exit();
                                   }
                               }
        );

        findViewById(R.id.tv_authentication).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SetActivity.this);
                // 创建对话框构建器
                View view = View.inflate(SetActivity.this, R.layout.login_pop, null);
                // 获取布局中的控件
                mZImage = view.findViewById(R.id.r_zheng_image);
                mPositiveView = view.findViewById(R.id.iv_positive);
                mNegativeView = view.findViewById(R.id.iv_negative);
                mFImage = view.findViewById(R.id.r_fan_image);
                mName = (EditText) view.findViewById(R.id.r_name);
                final EditText mPhone = (EditText) view.findViewById(R.id.r_phone);
                Button mButn = view.findViewById(R.id.r_butn);

                initTakePhoto();

                mZImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FROM = FROM_POSITIVE;
                        openbutton();
                    }
                });
                mFImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FROM = FROM_NEGATIVE;
                        openbutton();
                    }
                });
                mButn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Name = mName.getText().toString().trim();
                        String Phone = mPhone.getText().toString().trim();
                        if (Name.isEmpty()) {
                            Toast.makeText(SetActivity.this, "请输入您的姓名", Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (Phone.isEmpty()) {
                            Toast.makeText(SetActivity.this, "请输入您的身份证号", Toast.LENGTH_LONG).show();
                        }

                        HashMap<String, Object> headmap = new HashMap<>();
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("truename", Name);
                        map.put("idcard", Phone);
                        map.put("uid", mUid);
                        map.put("shell", mShell);

                        postData(Name, Phone, mUid, mShell);
                    }
                });

                builder.setTitle("")
                        .setView(view);

                alertDialog = builder.create();


                alertDialog.show();
            }
        });
    }

    private void openbutton() {
        //定义dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(SetActivity.this);
        builder.setTitle("请选择");
        builder.setItems(new String[]{"相机", "相册"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    //相机
                    case 0:
                        mHelper.takePhoto();
                        break;
                    //相册
                    case 1:
                        mHelper.selectPhoto(1);
                        break;
                }
            }
        });
        builder.create().show();
    }

    private ImageView getShowImageView() {
        if (FROM == FROM_POSITIVE) {
            return mPositiveView;
        } else if (FROM == FROM_NEGATIVE) {
            return mNegativeView;
        }
        return null;
    }

    public static boolean isListEmpty(List list) {
        if (list != null && list.size() > 0) {
            return false;
        }
        return true;
    }


    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void takeSuccess(TResult result) {
        //选择成功
        ArrayList<TImage> images = result.getImages();
        if (!isListEmpty(images)) {
            Message message = Message.obtain();
            message.obj = images;
            message.what = TAKE_PHOTO_SUCCESS;
            mHandler.sendMessage(message);
        }
    }

    private void refreshPhotoLayout(Message msg) {
        try {
            //默认只有一张图片，只取一张
            List<TImage> result = (List<TImage>) msg.obj;
            if (!isListEmpty(result)) {
                String path = result.get(0).getOriginalPath();
                if (getShowImageView() != null) {
                    Glide.with(this)
                            .load(path)
                            .into(getShowImageView());
                    File file = new File(path);
                    if (file.exists()) {
                        upLoadPhoto(file);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void postData(String truename,
                         String idcard,
                         String uid,
                         String shell) {
        apiService.verification(truename, idcard, uid, shell)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String body = responseBody.toString();
                        Gson gson = new Gson();
                        RenBean renBean = gson.fromJson(body, RenBean.class);
                        if (renBean != null) {
                            if (renBean.error == 0) {
                                alertDialog.dismiss();
                            } else {
                                Toast.makeText(SetActivity.this, renBean.message, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {


                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void upLoadPhoto(File file) {
        postHeadData(file, RenBean.class);
    }

    /**
     * 上传证件照正反面图片
     */
    public void postHeadData(File part, final Class clazz) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        parts.add(toRequestBodyOfText("shell", mShell));
        parts.add(toRequestBodyOfText("uid", mUid));
        parts.add(toRequestBodyOfText("type", getPhotoType()));
        parts.add(toRequestBodyOfImage("imgFile", part));

        apiService.addImageInfo(UserApi.getPhoneImage,parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            Gson gson = new Gson();
                            String string;
                            string = responseBody.string();
                            Object o = gson.fromJson(string, clazz);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private String getPhotoType() {
        if (FROM == FROM_POSITIVE) {
            return "rcard";
        } else if (FROM == FROM_NEGATIVE) {
            return "fcard";
        }
        return "";
    }

    private MultipartBody.Part toRequestBodyOfText(String keyStr, String value) {
        MultipartBody.Part body = MultipartBody.Part.createFormData(keyStr, value);
        return body;
    }

    private MultipartBody.Part toRequestBodyOfImage(String keyStr, File pFile) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), pFile);
        MultipartBody.Part filedata = MultipartBody.Part.createFormData(keyStr, pFile.getName(), requestBody);
        return filedata;
    }

    private void initTakePhoto() {
        mHelper = new CustomHelper(getTakePhoto());
    }
}
