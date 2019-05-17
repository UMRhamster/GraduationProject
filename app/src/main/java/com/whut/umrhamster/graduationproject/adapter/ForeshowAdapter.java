package com.whut.umrhamster.graduationproject.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.model.bean.Foreshow;
import com.whut.umrhamster.graduationproject.model.bean.Live;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.view.CircleImageView;

import java.util.List;

public class ForeshowAdapter extends RecyclerView.Adapter<ForeshowAdapter.ViewHolder>{
    private List<Foreshow> foreshowList;

    private OnItemClickListener onItemClickListener;
    private Context context;

    public ForeshowAdapter(List<Foreshow> foreshowList, Context context){
        this.foreshowList = foreshowList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_item_foreshow,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onItemClickListener.onItemClick((Integer) v.getTag());
            }
        });
        return new ViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.civIcon.setImageResource(foreshowList.get(position).getCover());
        long type = foreshowList.get(position).getStartTime().getTime()-System.currentTimeMillis();
        if (type <= 1000*60*60*24*3){
            holder.rlBackground.setBackgroundResource(R.drawable.custom_rv_item_foreshow_bg_red);
        }else if (type <= 1000*60*60*24*10){
            holder.rlBackground.setBackgroundResource(R.drawable.custom_rv_item_foreshow_bg_yellow);
        }else {
            holder.rlBackground.setBackgroundResource(R.drawable.custom_rv_item_foreshow_bg_green);
        }
        Live live = foreshowList.get(position).getLive();
        Picasso.get()
                .load(live.getCover())
                .resize(AdaptionUtil.dp2px(context,360),AdaptionUtil.dp2px(context,101))
                .config(Bitmap.Config.RGB_565)
                .into(holder.rivCover);
        Picasso.get().load(live.getTeacher().getAvatar()).into(holder.civIcon);
        holder.tvNickname.setText(live.getTeacher().getNickname());
        holder.tvTitle.setText(live.getTitle());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return foreshowList == null ? 0 : foreshowList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout rlBackground;
        RoundedImageView rivCover;
        CircleImageView civIcon;
        TextView tvNickname;
        TextView tvTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            rlBackground = itemView.findViewById(R.id.custom_rv_item_foreshow_bg);
            rivCover = itemView.findViewById(R.id.custom_rv_item_foreshow_riv_cover);
            civIcon = itemView.findViewById(R.id.custom_rv_item_foreshow_civ_icon);
            tvNickname = itemView.findViewById(R.id.custom_rv_item_foreshow_tv_nickname);
            tvTitle = itemView.findViewById(R.id.custom_rv_item_foreshow_tv_title);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
