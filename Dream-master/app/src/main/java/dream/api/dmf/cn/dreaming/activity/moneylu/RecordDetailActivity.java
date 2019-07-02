package dream.api.dmf.cn.dreaming.activity.moneylu;

import android.widget.TextView;

import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.base.BaseMvpActivity;
import dream.api.dmf.cn.dreaming.base.mvp.Contract;
import dream.api.dmf.cn.dreaming.base.mvp.presenter.presenter;

public class RecordDetailActivity extends BaseMvpActivity<presenter> implements Contract.Iview {

    @Override
    public int getView() {
        return R.layout.activity_record_detail;
    }

    @Override
    public void getInitData() {
       TextView title = findViewById(R.id.tv_title);
       title.setText("");
    }

    @Override
    public void getThisData() {

    }

    @Override
    public void getData(Object object) {

    }

    @Override
    protected presenter createP() {
        return new presenter();
    }
}
