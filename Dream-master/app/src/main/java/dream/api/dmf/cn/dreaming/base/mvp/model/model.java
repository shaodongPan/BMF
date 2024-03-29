package dream.api.dmf.cn.dreaming.base.mvp.model;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import dream.api.dmf.cn.dreaming.api.ApiService;
import dream.api.dmf.cn.dreaming.base.mvp.Contract;
import dream.api.dmf.cn.dreaming.utils.JsonUtil;
import dream.api.dmf.cn.dreaming.utils.RetrofitUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;


public class model implements Contract.Imodel {

    ApiService apiService = RetrofitUtils.getInstance().create(ApiService.class);

    @Override
    public void getData(String url, Map<String, Object> headMap, Map<String, Object> map, final Class clazz, final Contract.GetDataCallBack getDataCallBack) {

        apiService.getData(url, headMap, map)
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
                            String string = null;
                            string = responseBody.string();
                            Object o = gson.fromJson(string, clazz);
                            getDataCallBack.getDataTrue(o);
                        } catch (Exception e) {
                            e.printStackTrace();
                            getDataCallBack.getDataFalse(null);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        getDataCallBack.getDataFalse(e);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void postData(final String url, Map<String, Object> headMap, Map<String, Object> map, final Class clazz, final Contract.GetDataCallBack getDataCallBack) {
        apiService.postData(url, headMap, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String string = "";
                        try {
                            string = responseBody.string();
                            Object o = new Gson().fromJson(string, clazz);
                            getDataCallBack.getDataTrue(o);
                        } catch (IOException e) {
                            e.printStackTrace();
                            getDataCallBack.getDataFalse(null);
                        } catch (JsonSyntaxException ee) {
                            if (!TextUtils.isEmpty(string)) {
                                getDataCallBack.getDataFalse(new Throwable(string));
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        getDataCallBack.getDataFalse(e);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void putData(String url, Map<String, Object> headMap, Map<String, Object> map, final Class clazz, final Contract.GetDataCallBack getDataCallBack) {

        apiService.putData(url, headMap, map)
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
                            String string = null;
                            string = responseBody.string();
                            Object o = gson.fromJson(string, clazz);
                            getDataCallBack.getDataTrue(o);
                        } catch (Exception e) {
                            e.printStackTrace();
                            getDataCallBack.getDataFalse(null);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        getDataCallBack.getDataFalse(e);

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    public void delData(String url, Map<String, Object> headMap, Map<String, Object> map, final Class clazz, final Contract.GetDataCallBack getDataCallBack) {

        apiService.deleteData(url, headMap, map)
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
                            String string = null;
                            string = responseBody.string();
                            Object o = gson.fromJson(string, clazz);
                            getDataCallBack.getDataTrue(o);
                        } catch (Exception e) {
                            e.printStackTrace();
                            getDataCallBack.getDataFalse(null);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        getDataCallBack.getDataFalse(e);

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    //上传头像
    @Override
    public void postHeadData(String url, Map<String, Object> headMap, File part, final Class clazz, final Contract.GetDataCallBack getDataCallBack) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data;charset=UTF-8"), part);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imgFile", part.getName(), requestFile);

        apiService.postHeadData(url, headMap, body)
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
                            getDataCallBack.getDataTrue(o);
                        } catch (Exception e) {
                            e.printStackTrace();
                            getDataCallBack.getDataFalse(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getDataCallBack.getDataFalse(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //多图上传
    @Override
    public void postMorePicture(String url, Map<String, Object> headMap, Map<String, Object> map, List<File> img, final Class clazz, final Contract.GetDataCallBack getDataCallBack) {
        File file = (File) map.get("file");
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        apiService.postMorePicture(url, headMap, map, body)
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
                            String string = responseBody.string();
                            Object o = gson.fromJson(string, clazz);
                            getDataCallBack.getDataTrue(o);
                        } catch (Exception e) {
                            e.printStackTrace();
                            getDataCallBack.getDataFalse(null);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        getDataCallBack.getDataFalse(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
