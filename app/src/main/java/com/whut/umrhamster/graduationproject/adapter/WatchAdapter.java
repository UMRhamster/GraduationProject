package com.whut.umrhamster.graduationproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.model.bean.Teacher;
import com.whut.umrhamster.graduationproject.model.bean.Watch;
import com.whut.umrhamster.graduationproject.view.CircleImageView;

import java.util.List;

public class WatchAdapter extends RecyclerView.Adapter<WatchAdapter.ItemViewHolder> {
    private List<Watch> watchList;  //存放数据的集合
    private Context context;   //上下文

    private OnItemClickListener onItemClickListener;

    //通过构造方法绑定集合
    public WatchAdapter(List<Watch> watchList){
        this.watchList = watchList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  //创建ViewHolder用于复用控件，避免重复findviewbyid
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_item_watch,parent,false);
        view.setOnClickListener(new View.OnClickListener() { //处理选项卡的点击事件
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick((Integer) v.getTag());
            }
        });
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {
        Teacher teacher = watchList.get(position).getTeacher();
        Picasso.with(context).load(teacher.getAvatar()).into(holder.civIcon); //使用picasso加载图片
        holder.tvNickname.setText(teacher.getNickname());  //显示昵称
//        holder.tvBrief.setText();
        holder.tvWatch.setOnClickListener(new View.OnClickListener() {  //菜单点击事件
            @Override
            public void onClick(View v) {
                onItemClickListener.onWatchClick(holder.getAdapterPosition());
            }
        });
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return watchList == null ? 0 : watchList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civIcon;
        TextView tvNickname;
        TextView tvBrief;
        TextView tvWatch;

        public ItemViewHolder(View itemView) {
            super(itemView);
            civIcon = itemView.findViewById(R.id.rv_item_watch_civ_icon);
            tvNickname = itemView.findViewById(R.id.rv_item_watch_tv_nickname);
            tvBrief = itemView.findViewById(R.id.rv_item_watch_tv_brief);
            tvWatch = itemView.findViewById(R.id.rv_item_watch_tv_watch);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onWatchClick(int position);
    }
}
