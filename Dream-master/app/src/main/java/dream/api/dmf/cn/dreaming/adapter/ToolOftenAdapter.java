package dream.api.dmf.cn.dreaming.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.bean.HomeZixun;
import dream.api.dmf.cn.dreaming.utils.OnRvItemClickListener;

/**
 * Created by jason on 2019/5/24.
 */
public class ToolOftenAdapter extends RecyclerView.Adapter<ToolOftenAdapter.MineFgListViewHolder> {
    private Context mContext;
    private List<HomeZixun.DataBean> mDatas = new ArrayList<>();

    public ToolOftenAdapter(Context context) {
        this.mContext = context;
    }


    public void setData(List<HomeZixun.DataBean> datas) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        mDatas = new ArrayList<>(datas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MineFgListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.fragment_tools_often, viewGroup, false);
        MineFgListViewHolder viewHolder = new MineFgListViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MineFgListViewHolder mineFgListViewHolder, final int position) {
        HomeZixun.DataBean item = mDatas.get(position);
        mineFgListViewHolder.txt.setText(item.title);
        Glide.with(mContext).load(item.imgurl).thumbnail(0.1f).into(mineFgListViewHolder.img);

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MineFgListViewHolder extends RecyclerView.ViewHolder {
        TextView txt;
        ImageView img;

        public MineFgListViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txt);
            img=itemView.findViewById(R.id.img);
        }
    }
}
