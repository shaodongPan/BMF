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
import dream.api.dmf.cn.dreaming.app.MyApp;
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


//        mDisposable = new CompositeDisposable();
        initView(view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //如果队列中还有未完成的请求，直接清除
//        if (!mDisposable.isDisposed()) {
//            mDisposable.clear();
//        }
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
        mSp = getContext().getSharedPreferences(UserApi.SP, Context.MODE_PRIVATE);
        mUid = mSp.getString(UserApi.Uid, "");
        mShell = mSp.getString(UserApi.Shell, "");
        apiService.verifyLogin(mUid, mShell)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultConsumer, errorConsumer);

        //添加到队列
//        mDisposable.add(disposable);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getUserBean(IsLoginBean bean) {
        //子类实现获取userBean
        mSp.edit().putString(UserApi.dmf_day_price, bean.dmf_day_price.yestoday).commit();
        mSp.edit().putString(UserApi.dmf_day_Today, bean.dmf_day_price.today).commit();
        mSp.edit().putString(UserApi.updatemoney, bean.dmf_day_price.updatemoney).commit();
        mSp.edit().putString(UserApi.DmfNUm, String.valueOf(bean.dmf_num)).commit();
        mSp.edit().putString(UserApi.HYE, bean.hyt_day_price.yestoday).commit();
        mSp.edit().putString(UserApi.HTODAY, bean.hyt_day_price.today).commit();
        mSp.edit().putString(UserApi.HUPDATE, bean.hyt_day_price.updatemoney).commit();
        mSp.edit().putString(UserApi.ac_status, bean.ac_status).commit();
        mSp.edit().putString(UserApi.dmfpmoney, bean.dmfpmoney).commit();
        mSp.edit().putString(UserApi.tdmfpmoney, bean.tdmfpmoney).commit();
        mSp.edit().putString(UserApi.jy2, bean.jy2).commit();
        mSp.edit().putString(UserApi.toshopmoney, bean.toshopmoney).commit();


        //dmf
        mSp.edit().putString(UserApi.STock_mdf, bean.stock_dmf).commit();
        mSp.edit().putString(UserApi.balanceDMF, bean.balance_dmf).commit();
        mSp.edit().putString(UserApi.regmoneyDMF, bean.regmoney_dmf).commit();
        mSp.edit().putString(UserApi.credit3, bean.credit3).commit();
        mSp.edit().putString(UserApi.STOCK, bean.stock).commit();
        mSp.edit().putString(UserApi.balance, bean.balance).commit();
        mSp.edit().putString(UserApi.regmoney, bean.regmoney).commit();
        mSp.edit().putString(UserApi.credit4, bean.credit4).commit();
        mSp.edit().putString(UserApi.dmf_day_Today, bean.dmf_day_price.today).commit();
        mSp.edit().putString(UserApi.updatemoney, bean.dmf_day_price.updatemoney).commit();
        mSp.edit().putString(UserApi.HYTT_PRICE, bean.hyt_day_price.today).commit();
        mSp.edit().putString(UserApi.DMFED, String.valueOf(bean.dmfed)).commit();
        mSp.edit().putString(UserApi.tdmf_num, String.valueOf(bean.tdmf_num)).commit();
        mSp.edit().putString(UserApi.STock_mdf, bean.balance_dmf).commit();
        mSp.edit().putString(UserApi.HYTED, String.valueOf(bean.hyted)).commit();
        mSp.edit().putString(UserApi.BUYNUM, String.valueOf(bean.jy4)).commit();
        mSp.edit().putString(UserApi.idcard, (String) bean.idcard).commit();
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
