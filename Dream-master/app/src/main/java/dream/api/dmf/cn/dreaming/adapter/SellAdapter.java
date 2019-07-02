package dream.api.dmf.cn.dreaming.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import dream.api.dmf.cn.dreaming.Constants;
import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.activity.moneylu.RecordDetailActivity;
import dream.api.dmf.cn.dreaming.bean.BigBean;
import dream.api.dmf.cn.dreaming.bean.SellBean;

/**
 * Created by SongNing on 2019/6/29.
 * email: 836883891@qq.com
 */
public class SellAdapter extends RecyclerView.Adapter<SellAdapter.ViewHolder> {
    Context mContext;
    List<BigBean.DataBean> datase;
    private ViewHolder holder;
    private String mTitle;
    private boolean mType;
    private String mChannel;

    public SellAdapter(Context mContext, List<BigBean.DataBean> datase, String title
            , Boolean type, String channel) {
        this.mContext = mContext;
        this.datase = datase;
        this.mTitle = title;
        this.mType = type;
        this.mChannel = channel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.lusell_item, null);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        holder.headnumber.setText(datase.get(i).realpay);
        holder.number.setText(datase.get(i).amount);
        holder.daipay.setText(datase.get(i).checked);
        holder.buytime.setText(datase.get(i).addtime);
        holder.price.setText(datase.get(i).price);
        holder.llBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.FROM_TITLE, mTitle); //标题
                bundle.putBoolean(Constants.TYPE, mType);//DMF 或HYT
                bundle.putString(Constants.CHANNEL, mChannel);
                bundle.putParcelable(Constants.JUMP_TO_RECORD, datase.get(i));
                Intent intent = new Intent(mContext, RecordDetailActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (datase == null) {
            return 0;
        }
        return datase.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView headnumber;
        private final TextView number;
        private final TextView price;
        private final TextView daipay;
        private final TextView buytime;
        private final LinearLayout llBg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            llBg = itemView.findViewById(R.id.ll_bg);
            headnumber = itemView.findViewById(R.id.buy_num);
            number = itemView.findViewById(R.id.buy_number);
            price = itemView.findViewById(R.id.buy_price);
            daipay = itemView.findViewById(R.id.buy_dai);
            buytime = itemView.findViewById(R.id.buy_time);
        }
    }
}
