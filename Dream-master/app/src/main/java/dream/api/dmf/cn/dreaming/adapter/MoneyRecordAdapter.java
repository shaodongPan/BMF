package dream.api.dmf.cn.dreaming.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.bean.Record;

public class MoneyRecordAdapter extends BaseQuickAdapter<Record.DataBean, BaseViewHolder> {

    public MoneyRecordAdapter(@Nullable List<Record.DataBean> data) {
        super(R.layout.item_money_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Record.DataBean item) {

        helper.setText(R.id.tv_time, item.getCreatetime())
                .setText(R.id.tv_status, item.getContent())
                .setText(R.id.tv_number, item.getJnums())
                .setTextColor(R.id.tv_number, getColor(item.getClassname()));
    }

    private int getColor(String className) {
        int green = ContextCompat.getColor(mContext, R.color.material_green);
        int red = ContextCompat.getColor(mContext, R.color.material_red);
        if (className.equals("green")) {
            return green;
        } else if (className.equals("red")) {
            return green;
        } else {
            return red;
        }
    }
}
