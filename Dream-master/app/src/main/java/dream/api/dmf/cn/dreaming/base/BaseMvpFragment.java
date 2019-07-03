package dream.api.dmf.cn.dreaming.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dream.api.dmf.cn.dreaming.api.ApiService;
import dream.api.dmf.cn.dreaming.api.UserApi;
import dream.api.dmf.cn.dreaming.bean.IsLoginBean;
import dream.api.dmf.cn.dreaming.utils.RetrofitUtils;
import dream.api.dmf.cn.dreaming.utils.StringUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public abstract class BaseMvpFragment<P extends BasePresenter> extends Fragment {

    ApiService apiService = RetrofitUtils.getInstance().create(ApiService.class);

    Unbinder unbinder1;
    protected P mPresenter;
    public FragmentActivity mContext;
    private SharedPreferences mSp;
    private String mUid;
    private String mShell;
    private CompositeDisposable mDisposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentView(), container, false);
        unbinder1 = ButterKnife.bind(this, view);
        mContext = getActivity();
        initView(view);

        mSp = mContext.getSharedPreferences(UserApi.SP, Context.MODE_PRIVATE);
        mUid = mSp.getString(UserApi.Uid, "");
        mShell = mSp.getString(UserApi.Shell, "");
        mDisposable = new CompositeDisposable();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //如果队列中还有未完成的请求，直接清除
        if (!mDisposable.isDisposed()) {
            mDisposable.clear();
        }
        mPresenter.detachV();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
        mPresenter.attachV(this);
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 请求isLogin接口，并存储数据到sp中
     */
    public void requestLoginMessage() {
        Disposable disposable = apiService.verifyLogin(mUid, mShell)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultConsumer, errorConsumer);

        //添加到队列
        mDisposable.add(disposable);
    }

    Consumer<Throwable> errorConsumer = new Consumer<Throwable>() {
        @Override
        public void accept(Throwable throwable) {
            //之后可以上传到服务器分析用户发生的错误
            Log.e(BaseMvpFragment.class.getSimpleName(), throwable.toString());
        }
    };

    Consumer<ResponseBody> resultConsumer = new Consumer<ResponseBody>() {
        @Override
        public void accept(ResponseBody responseBody) {
            try {
                String dataString = responseBody.string();
                if (!StringUtils.isEmpty(dataString)) {
                    //存数据到本地
                    mSp.edit().putString(UserApi.SAVE_USER_INFO, dataString).apply();
                    parseUserData(dataString);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void parseUserData(String dataString) {
        try {
            Gson gson = new Gson();
            IsLoginBean bean = gson.fromJson(dataString, IsLoginBean.class);
            getUserBean(bean);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getUserBean(IsLoginBean bean){
        //子类实现获取userBean
    }
    @Override
    public void onDetach() {
        super.onDetach();

    }

    protected abstract P createPresenter();

    protected abstract int getFragmentView();

    protected abstract void initView(View view);

    protected abstract void getData();


}
