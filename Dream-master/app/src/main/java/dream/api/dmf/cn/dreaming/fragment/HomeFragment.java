package dream.api.dmf.cn.dreaming.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.adapter.ToolOftenAdapter;
import dream.api.dmf.cn.dreaming.api.UserApi;
import dream.api.dmf.cn.dreaming.base.BaseMvpFragment;
import dream.api.dmf.cn.dreaming.base.mvp.Contract;
import dream.api.dmf.cn.dreaming.base.mvp.presenter.presenter;
import dream.api.dmf.cn.dreaming.bean.HomeGongGao;
import dream.api.dmf.cn.dreaming.bean.HomeZixun;

public class HomeFragment extends BaseMvpFragment<presenter> implements Contract.Iview {


    private FlyBanner mBanner;
    private LinearLayout mGGPannel;
    private TextView mGGTitleTv;
    private ImageView mGGImageIv;

    private RecyclerView recyclerView;

    ToolOftenAdapter toolOftenAdapter;
    private SharedPreferences sharedPreferences;
    private String mUid;
    private String mShell;

    @Override
    protected presenter createPresenter() {
        return new presenter();
    }

    @Override
    protected int getFragmentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        mBanner = view.findViewById(R.id.banner);
        mGGPannel = view.findViewById(R.id.gg_pannel);
        mGGTitleTv = view.findViewById(R.id.gg_title);
        mGGImageIv = view.findViewById(R.id.gg_img);
        recyclerView = view.findViewById(R.id.recForeign);
        sharedPreferences = getActivity().getSharedPreferences(UserApi.SP, Context.MODE_PRIVATE);
        List<String> list = new ArrayList<>();
        list.add("http://api.xg360.cc/upload/img/1.jpg");
        list.add("http://api.xg360.cc/upload/img/2.jpg");
        list.add("http://api.xg360.cc/upload/img/3.jpg");
        mBanner.setImagesUrl(list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        toolOftenAdapter = new ToolOftenAdapter(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(toolOftenAdapter);
    }

    @Override
    protected void getData() {
        doRequest("");//请求公告
        doRequest("5");//请求资讯
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && mPresenter != null) {
            doRequest("");//请求公告
            doRequest("5");//请求资讯
        }
    }

    private void doRequest(String type) {
        mUid = sharedPreferences.getString(UserApi.Uid, "");
        mShell = sharedPreferences.getString(UserApi.Shell, "");
        HashMap<String, Object> headmap = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("shell", mShell);
        map.put("uid", mUid);
        if ("5".equals(type)) {
            map.put("limt", type);
            mPresenter.postData(UserApi.getHome, headmap, map, HomeZixun.class);
        } else {
            mPresenter.postData(UserApi.getHome, headmap, map, HomeGongGao.class);
        }

    }

    @Override
    public void getData(Object object) {
        if (object instanceof HomeGongGao) {
            HomeGongGao homeBean = (HomeGongGao) object;
            if (homeBean.data != null) {
                mGGPannel.setVisibility(View.VISIBLE);
                mGGTitleTv.setText("" + homeBean.data.title);
                Glide.with(getActivity()).load(homeBean.data.imgurl).thumbnail(0.1f).into(mGGImageIv);
            }
        } else if (object instanceof HomeZixun) {
            HomeZixun bean = (HomeZixun) object;
            if (bean.data != null) {
                toolOftenAdapter.setData(bean.data);
            }
        }
    }
}
