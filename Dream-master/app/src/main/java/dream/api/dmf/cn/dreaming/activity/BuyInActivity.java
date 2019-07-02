package dream.api.dmf.cn.dreaming.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.api.UserApi;
import dream.api.dmf.cn.dreaming.base.BaseMvpActivity;
import dream.api.dmf.cn.dreaming.base.mvp.Contract;
import dream.api.dmf.cn.dreaming.base.mvp.presenter.presenter;
import dream.api.dmf.cn.dreaming.bean.BuyIdBean;
import dream.api.dmf.cn.dreaming.fragment.bigfragment.BigBugFragment;

public class BuyInActivity extends BaseMvpActivity<presenter> implements Contract.Iview {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private SharedPreferences sharedPreferences;
    private Boolean username1;
    private String mUid;
    private String mShell;
    private TextView mReplay;
    private TextView mNums;
    private TextView mPricess;
    private TextView mButn;
    private Button mFalse;
    private String mid;

    @Override
    public void getThisData() {
        sharedPreferences = getSharedPreferences(UserApi.SP, Context.MODE_PRIVATE);
        username1 = sharedPreferences.getBoolean("Username", true);
        mUid = sharedPreferences.getString(UserApi.Uid, "");
        mShell = sharedPreferences.getString(UserApi.Shell, "");
    }

    @Override
    public void getInitData() {
        Intent intent = getIntent();
        String replay = intent.getStringExtra("replay");
        String amount = intent.getStringExtra("amount");
        String price = intent.getStringExtra("price");
        mid = intent.getStringExtra("id");
        mReplay = findViewById(R.id.buy_replay);
        mNums = findViewById(R.id.buy_nums);
        mPricess = findViewById(R.id.buy_prices);
        mFalse = findViewById(R.id.buy_false1);
        mButn = findViewById(R.id.buy_butn);
        mReplay.setText(replay);
        mNums.setText(amount);
        mPricess.setText(price);
        mFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, BigBugFragment.class));
            }
        });
        mButn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(BuyInActivity.this);
                // 创建对话框构建器
                View view=View.inflate(BuyInActivity.this,R.layout.updawho,null);
                // 获取布局中的控件
                TextView edmail = (TextView) view.findViewById(R.id.bbuy);
                final TextView unfalse = (TextView) view.findViewById(R.id.fal_qu);
                unfalse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                builder.setTitle("买入")
                        .setView(view);
                // 创建对话框
                final AlertDialog alertDialog = builder.create();
                edmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (username1){
                            HashMap<String,Object>headmap=new HashMap<>();
                            HashMap<String,Object>map=new HashMap<>();
                            map.put("uid",mUid);
                            map.put("shell",mShell);
                            map.put("id",mid);
                            mPresenter.postData(UserApi.getBUYid,headmap,map,BuyIdBean.class);

                        }else{
                            HashMap<String,Object>headmap=new HashMap<>();
                            HashMap<String,Object>map=new HashMap<>();
                            map.put("uid",mUid);
                            map.put("shell",mShell);
                            map.put("id",mid);
                            mPresenter.postData(UserApi.getHYTBYID,headmap,map,BuyIdBean.class);
                        }
                    }
                });
                alertDialog.show();




            }
        });

     /*   map.put("id",0);
        mPresenter.postData(UserApi.getBUYid);*/
    }

    @Override
    public int getView() {
        return R.layout.activity_buy_in;
    }

    @Override
    protected presenter createP() {
        return new presenter();
    }

    @Override
    public void getData(Object object) {
        if (object instanceof BuyIdBean) {
            BuyIdBean buBean = (BuyIdBean) object;
            if (buBean.error.equals("0")) {
                Toast.makeText(mContext, "成功", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(mContext, buBean.msg, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
