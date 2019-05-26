package com.whut.umrhamster.graduationproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.model.bean.Video;

import java.util.List;

public class ClassifyVideoAdapter extends RecyclerView.Adapter<ClassifyVideoAdapter.ItemViewHolder> {
    private List<Video> videoList;
    private Context context;

    private OnItemClickListener onItemClickListener;

    public ClassifyVideoAdapter(List<Video> videoList,Context context){
        this.videoList = videoList;
        this.context = context;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_item_classify_video,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick((Integer) view.getTag());
            }
        });
        return new ItemViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Video video = videoList.get(position);
        Picasso.with(context).load(video.getCover()).into(holder.rivCover);
        holder.tvTitle.setText(video.getTitle());
        holder.tvNickname.setText(video.getUploader().getNickname());
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
        TextView tvNickname;
        TextView tvNop;
        public ItemViewHolder(View itemView) {
            super(itemView);
            rivCover = itemView.findViewById(R.id.custom_rv_item_classify_video_riv_cover);
            tvTitle = itemView.findViewById(R.id.custom_rv_item_classify_video_tv_title);
            tvNickname = itemView.findViewById(R.id.custom_rv_item_classify_video_tv_nickname);
            tvNop = itemView.findViewById(R.id.custom_rv_item_classify_video_tv_nop);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
