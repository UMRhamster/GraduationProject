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
import com.whut.umrhamster.graduationproject.model.bean.Collection;
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.utils.other.TimeUtil;

import java.util.List;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.ItemViewHolder> {
    private List<Collection> videoList;
    private Context context;

    private OnItemClickListener onItemClickListener;

    public CollectAdapter(List<Collection> videoList, Context context){
        this.videoList = videoList;
        this.context = context;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_info_collect,parent,false);
        final ItemViewHolder holder = new ItemViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick((Integer) v.getTag());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {
        Video video = videoList.get(position).getVideo();
        Picasso.get().load(video.getCover())  //此处注意，placeholder可能会导致OOM
                .placeholder(R.drawable.default_cover)
                .config(Bitmap.Config.RGB_565)
                .resize(AdaptionUtil.dp2px(context,120),AdaptionUtil.dp2px(context,75))
                .into(holder.rivCover);
        holder.tvTitle.setText(video.getTitle());
//        holder.tvNickname.setText(video.getAuthor().getNickname());
        holder.tvNofw.setText(""+video.getViewers());
        holder.tvDuration.setText(TimeUtil.millint2String(video.getTotaltime()));
        holder.itemView.setTag(position);

        holder.ivOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onMenuClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList == null ? 0 : videoList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView rivCover;
        TextView tvTitle;
        TextView tvNickname;
        TextView tvNofw;
        ImageView ivOp;
        TextView tvDuration;
        public ItemViewHolder(View itemView) {
            super(itemView);
            rivCover = itemView.findViewById(R.id.rv_info_collect_riv_cover);
            tvNickname = itemView.findViewById(R.id.rv_info_collect_tv_nickname);
            tvTitle = itemView.findViewById(R.id.rv_info_collect_tv_title);
            tvNofw = itemView.findViewById(R.id.rv_info_collect_tv_now);
            ivOp = itemView.findViewById(R.id.rv_info_collect_iv_op);
            tvDuration = itemView.findViewById(R.id.rv_info_collect_tv_duration);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onMenuClick(int position);
    }
}
