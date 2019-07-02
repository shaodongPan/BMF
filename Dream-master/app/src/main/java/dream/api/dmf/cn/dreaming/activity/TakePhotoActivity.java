package dream.api.dmf.cn.dreaming.activity;

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
import java.util.ArrayList;
import java.util.List;

import dream.api.dmf.cn.dreaming.api.ApiService;
import dream.api.dmf.cn.dreaming.api.UserApi;
import dream.api.dmf.cn.dreaming.base.BaseMvpActivity;
import dream.api.dmf.cn.dreaming.base.mvp.presenter.presenter;
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

public class TakePhotoActivity extends BaseMvpActivity<presenter> implements TakePhoto.TakeResultListener
        , InvokeListener {

    ApiService apiService = RetrofitUtils.getInstance().create(ApiService.class);

    private static final int TAKE_PHOTO_SUCCESS = 10000;
    private static final int TAKE_PHOTO_FAILURE = 10001;
    private static final int TAKE_PHOTO_CANCEL = 10002;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private CustomHelper mHelper;
    private SharedPreferences mSp;
    private String mUid;
    private String mShell;


    private WeakHandler mHandler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case TAKE_PHOTO_SUCCESS:
                    takePhotoSuccess(msg);
                    break;
                case TAKE_PHOTO_FAILURE:
                    break;
                case TAKE_PHOTO_CANCEL:
                    break;
            }
            return false;
        }
    });


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        initTakePhoto();
        initData();
    }

    private void initData() {
        mSp = getSharedPreferences(UserApi.SP, Context.MODE_PRIVATE);
        mUid = mSp.getString(UserApi.Uid, "");
        mShell = mSp.getString(UserApi.Shell, "");
    }

    private void initTakePhoto() {
        mHelper = new CustomHelper(getTakePhoto());
    }

    @Override
    public void getThisData() {

    }

    @Override
    public void getInitData() {

    }

    @Override
    public int getView() {
        return 0;
    }

    @Override
    protected presenter createP() {
        return null;
    }

    public void takePhotoSuccess(Message msg) {

    }

    public static boolean isListEmpty(List list) {
        if (list != null && list.size() > 0) {
            return false;
        }
        return true;
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

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

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


    public void showPhotoDialog() {
        //定义dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(TakePhotoActivity.this);
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

    /**
     * 上传图片
     */
    public void postHeadData(File part, final Class clazz, String type) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        parts.add(toRequestBodyOfText("shell", mShell));
        parts.add(toRequestBodyOfText("uid", mUid));
        parts.add(toRequestBodyOfText("type", type));
        parts.add(toRequestBodyOfImage("imgFile", part));

        apiService.addImageInfo(parts)
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

                        } catch (Exception e) {
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

    private MultipartBody.Part toRequestBodyOfText(String keyStr, String value) {
        return MultipartBody.Part.createFormData(keyStr, value);
    }

    private MultipartBody.Part toRequestBodyOfImage(String keyStr, File pFile) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), pFile);
        return MultipartBody.Part.createFormData(keyStr, pFile.getName(), requestBody);
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
}
