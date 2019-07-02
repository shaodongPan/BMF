package dream.api.dmf.cn.dreaming.activity.moneylu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.devio.takephoto.model.TImage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dream.api.dmf.cn.dreaming.Constants;
import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.activity.PhotoActivity;
import dream.api.dmf.cn.dreaming.activity.TakePhotoActivity;
import dream.api.dmf.cn.dreaming.api.UserApi;
import dream.api.dmf.cn.dreaming.base.mvp.Contract;
import dream.api.dmf.cn.dreaming.base.mvp.presenter.presenter;
import dream.api.dmf.cn.dreaming.bean.BigBean;
import dream.api.dmf.cn.dreaming.bean.PhotoBean;
import dream.api.dmf.cn.dreaming.bean.SafeBean;
import dream.api.dmf.cn.dreaming.utils.StringUtils;
import okhttp3.MultipartBody;

public class RecordDetailActivity extends TakePhotoActivity implements Contract.Iview {

    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_buy)
    TextView tvBuy;
    @BindView(R.id.tv_sell)
    TextView tvSell;
    @BindView(R.id.tv_member_id)
    TextView tvMemberId;
    @BindView(R.id.tv_member_phone)
    TextView tvMemberPhone;
    @BindView(R.id.tv_sale_time)
    TextView tvSaleTime;
    @BindView(R.id.tv_photo_uploaded)
    TextView tvPhotoUploaded;
    @BindView(R.id.ll_member_sell)
    LinearLayout llMemberSell;
    @BindView(R.id.ll_member_phone)
    LinearLayout llMemberPhone;
    @BindView(R.id.ll_member_time)
    LinearLayout llMemberTime;
    @BindView(R.id.ll_certificate)
    LinearLayout llCertificate;
    @BindView(R.id.rl_upload)
    RelativeLayout rlUpload;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.view_line2)
    View viewLine2;

    private String mTitle;
    private boolean mType;
    private BigBean.DataBean data;
    private SharedPreferences sharedPreferences;
    private String mUid;
    private String mShell;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public int getView() {
        return R.layout.activity_record_detail;
    }

    @Override
    public void getInitData() {
        sharedPreferences = getSharedPreferences(UserApi.SP, Context.MODE_PRIVATE);
        mUid = sharedPreferences.getString(UserApi.Uid, "");
        mShell = sharedPreferences.getString(UserApi.Shell, "");
        initTitle();
    }

    @Override
    public void getThisData() {
        setUpData();
    }

    private void setUpData() {
        mType = getType();

        data = getBigBeanData();
        if (data != null) {
            tvPrice.setText(data.realpay + " CNY");
            tvBuy.setText("买入数量 " + data.amount + " DMFB");
            tvSell.setText("买入价格 " + data.price + " CNY/DMFB");
            tvMemberId.setText(data.sale_username);
            tvMemberPhone.setText(data.sale_mobile);
            tvSaleTime.setText(data.buytime);
            tvPay.setVisibility(isHiddenButton(data) ? View.GONE : View.VISIBLE);
            if (data.status.equals("0") || data.status.equals("3")) {
                hideLayout();
            }

            if (!StringUtils.isEmpty(data.pzimagesUrl)) {
                tvPhotoUploaded.setText("查看支付凭证");
            }

            if (data.status.equals("2")) {
                tvPay.setText("确认收款");
            }

            if (mTitle.contains("卖出")) {
                rlUpload.setVisibility(View.GONE);
            }
        }
    }

    private void hideLayout() {
        llMemberSell.setVisibility(View.GONE);
        llMemberPhone.setVisibility(View.GONE);
        llMemberTime.setVisibility(View.GONE);
        llCertificate.setVisibility(View.GONE);
        viewLine.setVisibility(View.GONE);
        viewLine2.setVisibility(View.GONE);
    }

    private void initTitle() {
        TextView title = findViewById(R.id.tv_title);
        mTitle = getActivityTitle();
        title.setText(mTitle);
    }

    @OnClick({R.id.rl_upload
            , R.id.rl_view
            , R.id.tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_upload:
                showPhotoDialog();
                break;
            case R.id.rl_view:
                viewPhoto(data.pzimagesUrl);
                break;
            case R.id.tv_pay:
                pay();
                break;
        }
    }

    private void pay() {
        HashMap<String, Object> headmap = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", data.id);
        map.put("c", getChannel());
        map.put("type", mType ? "2" : "1");
        map.put("uid", mUid);
        map.put("shell", mShell);
        if (data.status.equals("2")) {
            mPresenter.postData(UserApi.getOk, headmap, map, SafeBean.class);
        } else if (data.status.equals("1")) {
            mPresenter.postData(UserApi.getPay, headmap, map, SafeBean.class);
        }
    }

    private void viewPhoto(String url) {
        Intent intent = new Intent(this, PhotoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PHOTO, url);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void takePhotoSuccess(Message msg) {
        super.takePhotoSuccess(msg);
        try {
            //默认只有一张图片，只取一张
            List<TImage> result = (List<TImage>) msg.obj;
            if (!isListEmpty(result)) {
                String path = result.get(0).getOriginalPath();
                File file = new File(path);
                if (file.exists()) {
                    postHeadData(UserApi.getPayImage, gen(file), PhotoBean.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getPhotoData(Object o) {
        super.getPhotoData(o);
        if (o instanceof PhotoBean) {
            PhotoBean bean = (PhotoBean) o;
            data.pzimagesUrl = bean.getUrl();
        }
    }

    public List<MultipartBody.Part> gen(File file) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        parts.add(toRequestBodyOfText("shell", mShell));
        parts.add(toRequestBodyOfText("uid", mUid));
        parts.add(toRequestBodyOfText("id", data.id));
        parts.add(toRequestBodyOfImage("imgFile", file));
        parts.add(toRequestBodyOfText("c", getChannel()));
        parts.add(toRequestBodyOfText("type", mType ? "2" : "1"));
        return parts;
    }

    @Nullable
    private BigBean.DataBean getBigBeanData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            return bundle.getParcelable(Constants.JUMP_TO_RECORD);
        }
        return null;
    }

    @Nullable
    private String getChannel() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            return bundle.getString(Constants.CHANNEL);
        }
        return "";
    }

    private String getActivityTitle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            return bundle.getString(Constants.FROM_TITLE);
        }
        return null;
    }

    private boolean getType() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            return bundle.getBoolean(Constants.TYPE);
        }
        return true;
    }

    private boolean isHiddenButton(BigBean.DataBean dataBean) {
        if (dataBean.status.equals("1") && mTitle.contains("买入")) {
            return false;
        } else return !dataBean.status.equals("2") || !mTitle.contains("卖出");
    }

    @Override
    public void getData(Object object) {
        if (object instanceof SafeBean) {
            SafeBean renBean = (SafeBean) object;
            if (renBean.error.equals("0")) {
                finish();
            } else {
                Toast.makeText(mContext, renBean.msg, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected presenter createP() {
        return new presenter();
    }
}
