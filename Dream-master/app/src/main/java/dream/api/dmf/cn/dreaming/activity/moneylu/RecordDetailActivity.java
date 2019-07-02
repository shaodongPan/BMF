package dream.api.dmf.cn.dreaming.activity.moneylu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.api.dmf.cn.dreaming.Constants;
import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.base.BaseMvpActivity;
import dream.api.dmf.cn.dreaming.base.mvp.Contract;
import dream.api.dmf.cn.dreaming.base.mvp.presenter.presenter;
import dream.api.dmf.cn.dreaming.bean.BigBean;

public class RecordDetailActivity extends BaseMvpActivity<presenter> implements Contract.Iview {

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
    @BindView(R.id.ll_member_sell)
    LinearLayout llMemberSell;
    @BindView(R.id.ll_member_phone)
    LinearLayout llMemberPhone;
    @BindView(R.id.ll_member_time)
    LinearLayout llMemberTime;
    @BindView(R.id.ll_certificate)
    LinearLayout llCertificate;
    @BindView(R.id.tv_pay)
    TextView tvPay;

    private String mTitle;

    @Override
    protected void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public int getView() {
        return R.layout.activity_record_detail;
    }

    @Override
    public void getInitData() {
        initTitle();
    }

    @Override
    public void getThisData() {
        setUpData();
    }

    private void setUpData() {
        BigBean.DataBean data = getBigBeanData();
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
        }
    }

    private void hideLayout() {
        llMemberSell.setVisibility(View.GONE);
        llMemberPhone.setVisibility(View.GONE);
        llMemberTime.setVisibility(View.GONE);
        llCertificate.setVisibility(View.GONE);
    }

    private void initTitle() {
        TextView title = findViewById(R.id.tv_title);
        mTitle = getActivityTitle();
        title.setText(mTitle);
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

    private String getActivityTitle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            return bundle.getString(Constants.FROM_TITLE);
        }
        return null;
    }

    private boolean isHiddenButton(BigBean.DataBean dataBean) {
        if (dataBean.status.equals("1") && mTitle.contains("买入")) {
            return false;
        } else if (dataBean.status.equals("2") && mTitle.contains("卖出")) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void getData(Object object) {

    }

    @Override
    protected presenter createP() {
        return new presenter();
    }
}
