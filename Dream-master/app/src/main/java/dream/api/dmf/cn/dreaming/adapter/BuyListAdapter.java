package dream.api.dmf.cn.dreaming.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.bean.BuyListBean;
import dream.api.dmf.cn.dreaming.bean.ConstomData;

/**
 * Created by SongNing on 2019/6/27.
 * email: 836883891@qq.com
 */
public class BuyListAdapter extends RecyclerView.Adapter<BuyListAdapter.ViewHolder> {
    Context mContext;
    List<ConstomData> data;
    ViewHolder holder;

    public BuyListAdapter(Context mContext, List<ConstomData> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.buy_item, null);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        holder.headnumber.setText(data.get(i).realpay);
        holder.number.setText(data.get(i).amount);
        holder.daipay.setText(data.get(i).checked);
        holder.buytime.setText(data.get(i).addtime);
        holder.price.setText(data.get(i).price);
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        /*  TextView tvNum;
          TextView tvPrice;
          TextView tvMoney;
          TextView tvSell;*/
        private final TextView headnumber;
        private final TextView number;
        private final TextView price;
        private final TextView daipay;
        private final TextView buytime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headnumber = itemView.findViewById(R.id.buy_num);
            number = itemView.findViewById(R.id.buy_number);
            price = itemView.findViewById(R.id.buy_price);
            daipay = itemView.findViewById(R.id.buy_dai);
            buytime = itemView.findViewById(R.id.buy_time);
           /* tvNum = itemView.findViewById(R.id.tv_num);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvMoney = itemView.findViewById(R.id.tv_money);
            tvSell = itemView.findViewById(R.id.tv_sell);*/
        }
    }
}
