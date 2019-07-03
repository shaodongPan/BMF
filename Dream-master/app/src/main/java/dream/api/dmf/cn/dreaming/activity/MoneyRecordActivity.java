package dream.api.dmf.cn.dreaming.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.activity.moneyactivity.MoneyMLActivity;
import dream.api.dmf.cn.dreaming.adapter.MoneyRecordAdapter;
import dream.api.dmf.cn.dreaming.api.UserApi;
import dream.api.dmf.cn.dreaming.base.BaseMvpActivity;
import dream.api.dmf.cn.dreaming.base.mvp.Contract;
import dream.api.dmf.cn.dreaming.base.mvp.presenter.presenter;
import dream.api.dmf.cn.dreaming.bean.Record;

public class MoneyRecordActivity extends BaseMvpActivity<presenter> implements Contract.Iview {

    private MoneyRecordAdapter mAdapter;

    @Override
    public int getView() {
        return R.layout.activity_money_record;
    }

    @Override
    public void getInitData() {
        initRecyclerView();
    }

    @Override
    public void getThisData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String type = bundle.getString(MoneyMLActivity.TYPE);
            String c = bundle.getString(MoneyMLActivity.C);

            mPresenter.postData(UserApi.getLog, getHeadMap()
                    , getParamsMap(c, type), Record.class);
        }

        initView();
    }

    private void initView() {
        ImageView ivBack = findViewById(R.id.iv_back);
        TextView tvTitle = findViewById(R.id.tv_title);

        tvTitle.setText("记录");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView rvRecord = findViewById(R.id.rv_record);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvRecord.setLayoutManager(manager);
        mAdapter = new MoneyRecordAdapter(null);
        rvRecord.setAdapter(mAdapter);
    }

    @Override
    protected presenter createP() {
        return new presenter();
    }

    @Override
    public void getData(Object object) {
        if (!isFinishing()) {
            if (object instanceof Record) {
                Record record = (Record) object;
                mAdapter.setNewData(record.getData());
            }
        }
    }

    public HashMap<String, Object> getParamsMap(String c, String type) {
        SharedPreferences sp = getSharedPreferences(UserApi.SP, Context.MODE_PRIVATE);
        String mUid = sp.getString(UserApi.Uid, "");
        String mShell = sp.getString(UserApi.Shell, "");

        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", mUid);
        map.put("shell", mShell);
        map.put("c", c);
        map.put("type", type);
        return map;
    }
}
