package com.whut.umrhamster.graduationproject.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.utils.other.TimeUtil;

import java.util.List;

public class TeacherVideoAdapter extends RecyclerView.Adapter<TeacherVideoAdapter.ItemViewHolder> {
    private List<Video> videoList;
    private Context context;

    private OnItemClickListener onItemClickListener;

    public TeacherVideoAdapter(List<Video> videoList, Context context){
        this.videoList = videoList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_item_teacher_video,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick((Integer) v.getTag());
            }
        });
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Video video = videoList.get(position);
        Picasso.with(context)
                .load(video.getCover())
                .resize(AdaptionUtil.dp2px(context,120),AdaptionUtil.dp2px(context,75))
                .config(Bitmap.Config.RGB_565)
                .into(holder.rivCover);
        holder.tvTitle.setText(video.getTitle());
        holder.tvDate.setText(TimeUtil.uptime2string(video.getUploadtime()));
        holder.tvDuration.setText(TimeUtil.millint2String(video.getTotaltime()));
        holder.tvNop.setText(""+video.getViewers());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return videoList == null ? 0 : videoList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView rivCover;
        TextView tvTitle;
        TextView tvDate;
        TextView tvDuration;
        TextView tvNop;
        public ItemViewHolder(View itemView) {
            super(itemView);
            rivCover = itemView.findViewById(R.id.custom_rv_item_teacher_video_riv_cover);
            tvTitle = itemView.findViewById(R.id.custom_rv_item_teacher_video_tv_title);
            tvDate = itemView.findViewById(R.id.custom_rv_item_teacher_video_tv_date);
            tvDuration = itemView.findViewById(R.id.custom_rv_item_teacher_video_tv_duration);
            tvNop = itemView.findViewById(R.id.custom_rv_item_teacher_video_tv_nop);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
