package com.whut.umrhamster.graduationproject.adapter;

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
    private List<Watch> watchList;

    public WatchAdapter(List<Watch> watchList){
        this.watchList = watchList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_item_watch,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Teacher teacher = watchList.get(position).getTeacher();
        Picasso.get().load(teacher.getAvatar()).into(holder.civIcon);
        holder.tvNickname.setText(teacher.getNickname());
//        holder.tvBrief.setText();
        holder.tvWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
}
