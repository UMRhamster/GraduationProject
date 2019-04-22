package com.whut.umrhamster.graduationproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.model.bean.Foreshow;
import com.whut.umrhamster.graduationproject.view.CircleImageView;

import java.util.List;

public class ForeshowAdapter extends RecyclerView.Adapter<ForeshowAdapter.ViewHolder>{
    private List<Foreshow> foreshowList;

    public ForeshowAdapter(List<Foreshow> foreshowList){
        this.foreshowList = foreshowList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_item_foreshow,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.civIcon.setImageResource(foreshowList.get(position).getCover());
        Picasso.get()
                .load(foreshowList.get(position).getCover())
                .into(holder.rivCover);
        holder.civIcon.setImageResource(foreshowList.get(position).getIcon());
        holder.tvNickname.setText(foreshowList.get(position).getNickname());
        holder.tvTitle.setText(foreshowList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return foreshowList == null ? 0 : foreshowList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView rivCover;
        CircleImageView civIcon;
        TextView tvNickname;
        TextView tvTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            rivCover = itemView.findViewById(R.id.custom_rv_item_foreshow_riv_cover);
            civIcon = itemView.findViewById(R.id.custom_rv_item_foreshow_civ_icon);
            tvNickname = itemView.findViewById(R.id.custom_rv_item_foreshow_tv_nickname);
            tvTitle = itemView.findViewById(R.id.custom_rv_item_foreshow_tv_title);
        }
    }
}
