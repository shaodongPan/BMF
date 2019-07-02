package dream.api.dmf.cn.dreaming.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.listener.IItemClicked;

/**
 * Time:2019/7/2
 * <p>
 * <p>
 * Description: 会员编号适配器
 */
public class VipNumberAdapter extends RecyclerView.Adapter<VipNumberAdapter.TxtHolder> {

    private List<String> mDatas = new ArrayList<>();
    private IItemClicked<String> mListener;

    public VipNumberAdapter(IItemClicked cb) {
        mListener = cb;
    }

    public void setData(List<String> datas) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        mDatas = new ArrayList<>(datas);
        notifyDataSetChanged();
    }

    public void setSelect(String key) {

    }

    @NonNull
    @Override
    public VipNumberAdapter.TxtHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TxtHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycleview_txt, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final VipNumberAdapter.TxtHolder viewHolder, final int i) {
        viewHolder.txt.setText(mDatas.get(i));
        viewHolder.txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClicked(v, i, mDatas.get(i));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class TxtHolder extends RecyclerView.ViewHolder {
        TextView txt;

        public TxtHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txt);
        }
    }

}
