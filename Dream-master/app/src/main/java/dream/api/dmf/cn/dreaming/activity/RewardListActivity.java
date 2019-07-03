package dream.api.dmf.cn.dreaming.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.transition.Fade;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.api.UserApi;
import dream.api.dmf.cn.dreaming.base.BaseMvpActivity;
import dream.api.dmf.cn.dreaming.base.mvp.Contract;
import dream.api.dmf.cn.dreaming.base.mvp.presenter.presenter;
import dream.api.dmf.cn.dreaming.bean.MouthBean;
import dream.api.dmf.cn.dreaming.bean.RewardBean;
import dream.api.dmf.cn.dreaming.utils.DateUtil;

public class RewardListActivity extends BaseMvpActivity<presenter> implements Contract.Iview {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.r_left)
    ImageView mLeft;
    @BindView(R.id.r_left_time)
    TextView mLeftTime;
    @BindView(R.id.r_right_time)
    TextView mRightTime;
    @BindView(R.id.r_right)
    ImageView mRight;
    @BindView(R.id.vip_number)
    TextView mPhone;
    @BindView(R.id.total_price)
    TextView mNumber;

    @BindView(R.id.num0_6)
    TextView num0_6;
    @BindView(R.id.num1_1)
    TextView num1_1;
    @BindView(R.id.num1_2)
    TextView num1_2;
    @BindView(R.id.num1_3)
    TextView num1_3;
    @BindView(R.id.num1_4)
    TextView num1_4;
    @BindView(R.id.num1_5)
    TextView num1_5;
    @BindView(R.id.num1_6)
    TextView num1_6;

    @BindView(R.id.num2_1)
    TextView num2_1;
    @BindView(R.id.num2_2)
    TextView num2_2;
    @BindView(R.id.num2_3)
    TextView num2_3;
    @BindView(R.id.num2_4)
    TextView num2_4;
    @BindView(R.id.num2_5)
    TextView num2_5;
    @BindView(R.id.num2_6)
    TextView num2_6;
    @BindView(R.id.num2_7)
    TextView num2_7;
    @BindView(R.id.num3_1)
    TextView num3_1;
    @BindView(R.id.num3_2)
    TextView num3_2;
    @BindView(R.id.num3_3)
    TextView num3_3;
    @BindView(R.id.num3_4)
    TextView num3_4;
    @BindView(R.id.num3_5)
    TextView num3_5;
    @BindView(R.id.num3_6)
    TextView num3_6;
    @BindView(R.id.num3_7)
    TextView num3_7;
    @BindView(R.id.r_listnew)
    TextView rListnew;
    @BindView(R.id.r_lin_butn)
    Button rLinButn;
    @BindView(R.id.date_pannel)
    LinearLayout mDatePannel;

    @BindView(R.id.num4_1)
    TextView num4_1;
    @BindView(R.id.num4_2)
    TextView num4_2;
    @BindView(R.id.num4_3)
    TextView num4_3;
    @BindView(R.id.num4_4)
    TextView num4_4;
    @BindView(R.id.num4_5)
    TextView num4_5;
    @BindView(R.id.num4_6)
    TextView num4_6;

    private TimePickerView pvTime, mTime;

    private SharedPreferences sharedPreferences;
    private boolean m = true;
    private String righttime;
    private String lEftime;
    private String username;
    private String mMouth;
    private String number;

    @Override
    public int getView() {
        return R.layout.activity_reward_list;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void getInitData() {
        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra("data")) {
            finish();
            return;
        }
        number = getIntent().getStringExtra("data");
        sharedPreferences = getSharedPreferences(UserApi.SP, Context.MODE_PRIVATE);
        username = sharedPreferences.getString(UserApi.UserName, "");
        initTimePicker();
        initTime();
        getWindow().setEnterTransition(new Fade().setDuration(2000));
        tvTitle.setText("奖励列表");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (TextUtils.isEmpty(number)) {
            number = "";
        }
        mPhone.setText(number);
        mLeftTime.setText(DateUtil.getTodayDate(-1));
        mRightTime.setText(DateUtil.getTodayDate(0));
        rListnew.setText(DateUtil.getTodayMonth());

    }

    @Override
    public void getThisData() {
        HashMap<String, Object> headmap = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        String lefttime = mLeftTime.getText().toString();
        String righttime = mRightTime.getText().toString();
        map.put("phone", username);
        map.put("start", lefttime);
        map.put("end", righttime);
        map.put("number", number);
        mPresenter.postData(UserApi.getREList, headmap, map, RewardBean.class);
    }

    @Override
    public void getData(Object object) {
        if (object instanceof RewardBean) {
            RewardBean rewardBean = (RewardBean) object;
            if (rewardBean.status.equals("200") && rewardBean.data != null) {
                RewardBean.DataBean.FirstBean first = rewardBean.data.first;
                if (first != null) {
                    num0_6.setText("(收" + rewardBean.data.fee + "%)");
                    num1_1.setText(first.commend);
                    num1_2.setText(first.share);
                    num1_3.setText(first.share_commission);
                    num1_4.setText(first.form);
                    num1_5.setText(first.manger);
                    num1_6.setText(first.real_reward);
                }

                RewardBean.DataBean.LastBean last = rewardBean.data.last;
                if (last != null) {
                    num2_1.setText(last.commend);
                    num2_2.setText(last.share);
                    num2_3.setText(last.share_commission);
                    num2_5.setText(last.form);
                    num2_6.setText(last.manger);
                    num2_7.setText(last.real_reward);
                }

                RewardBean.DataBean.TotalBean total = rewardBean.data.total;
                if (total != null) {
                    num3_1.setText(total.commend);
                    num3_2.setText(total.share);
                    num3_3.setText(total.share_commission);
                    num3_5.setText(total.form);
                    num3_6.setText(total.manger);
                    num3_7.setText(total.real_reward);
                }
                mPhone.setText("" + rewardBean.data.number);
                mNumber.setText("");
            }
        } else if (object instanceof MouthBean) {
            MouthBean mouthBean = (MouthBean) object;
            if (mouthBean.status == 200 && mouthBean.data != null) {
                num3_1.setText(mouthBean.data.commend);
                num3_2.setText(mouthBean.data.share);
                num3_3.setText(mouthBean.data.share_commission);
                num3_4.setText(mouthBean.data.share_jifen);
                num3_5.setText(mouthBean.data.form);
                num3_6.setText(mouthBean.data.real_reward);
            } else {
                Toast.makeText(mContext, mouthBean.message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected presenter createP() {
        return new presenter();
    }

    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                rListnew.setText(getTime(date));
                mMouth = rListnew.getText().toString().trim();
                HashMap<String, Object> headmap = new HashMap<>();
                HashMap<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("phone", username);
                paramsMap.put("date", mMouth);
                paramsMap.put("end", righttime);
                paramsMap.put("number", number);
                mPresenter.postData(UserApi.getMouth, headmap, paramsMap, MouthBean.class);
            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                }).setType(new boolean[]{true, true, false, false, false, false}) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setLabel("年", "月", "日", "时", "", "")//默认设置为年月日时分秒
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
    }

    private void initTime() {//Dialog 模式下，在底部弹出
        mTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (m) {
                    mLeftTime.setText(getTime2(date));
                } else {
                    mRightTime.setText(getTime2(date));
                }

                HashMap<String, Object> headmap = new HashMap<>();
                HashMap<String, Object> map = new HashMap<>();
                String lefttime = mLeftTime.getText().toString();
                String righttime = mRightTime.getText().toString();
                map.put("phone", username);
                map.put("start", mLeftTime.getText().toString());
                map.put("end", mRightTime.getText().toString());
                map.put("number", number);
                mPresenter.postData(UserApi.getREList, headmap, map, RewardBean.class);
            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                    }
                }).setType(new boolean[]{true, true, true, false, false, false}) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setLabel("年", "月", "日", "时", "", "")//默认设置为年月日时分秒
                // .setRangDate(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .build();

        Dialog mDialog = mTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            mTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM");
        return format.format(date);
    }

    private String getTime2(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    @OnClick({R.id.r_left_time, R.id.r_right_time, R.id.date_pannel})
    public void onViewClicked(View view) {
        HashMap<String, Object> headmap = new HashMap<>();
        HashMap<String, Object> paramsMap = new HashMap<>();
        headmap.clear();
        paramsMap.clear();
        switch (view.getId()) {
            case R.id.r_left_time:
                m = true;
                if (mTime != null) {
                    mTime.show(view);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
                }
                lEftime = mLeftTime.getText().toString().trim();
                break;
            case R.id.r_right_time:
                m = false;
                if (mTime != null) {
                    mTime.show(view);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
                }
                righttime = mRightTime.getText().toString().trim();
                paramsMap.put("phone", username);
                paramsMap.put("start", lEftime);
                paramsMap.put("end", righttime);
                paramsMap.put("number", number);
                mPresenter.postData(UserApi.getREList, headmap, paramsMap, RewardBean.class);
                break;
            case R.id.date_pannel:
                if (pvTime != null) {
                    pvTime.show(view);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
                }
                break;
        }
    }
}
