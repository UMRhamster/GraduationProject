package com.whut.umrhamster.graduationproject.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.utils.other.TimeUtil;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ItemViewHolder> {
    private List<Video> videoList;

    private OnItemClickListener onItemClickListener;
    private Context context;

    public VideoAdapter(List<Video> videoList, Context context){
        this.videoList = videoList;
        this.context =context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_item_video,parent,false);
        ItemViewHolder holder = new ItemViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick((Integer) v.getTag());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Video video = videoList.get(position);
        Picasso.with(context)
                .load(video.getCover())
                .resize(AdaptionUtil.dp2px(context,360),AdaptionUtil.dp2px(context,101))
                .config(Bitmap.Config.RGB_565)
                .into(holder.rivCover);
        holder.tvViewers.setText(""+video.getViewers());
        holder.tvDuration.setText(TimeUtil.millint2String(video.getTotaltime()));
        holder.tvTitle.setText(video.getTitle());
        holder.tvClassify.setText(video.getClassify().getName());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return videoList == null ? 0 :videoList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView rivCover;
        TextView tvViewers;
        TextView tvDuration;
        TextView tvTitle;
        TextView tvClassify;
        ImageView ivOp;
        public ItemViewHolder(View itemView) {
            super(itemView);
            rivCover = itemView.findViewById(R.id.custom_rv_item_video_iv_cover);
            tvViewers = itemView.findViewById(R.id.custom_rv_item_video_tv_viewers);
            tvDuration = itemView.findViewById(R.id.custom_rv_item_video_rl_tv_duration);
            tvTitle = itemView.findViewById(R.id.custom_rv_item_video_tv_title);
            tvClassify = itemView.findViewById(R.id.custom_rv_item_video_tv_classify);
            ivOp = itemView.findViewById(R.id.custom_rv_item_video_rl_iv_more);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onItemLongClick(int position);
    }
}
