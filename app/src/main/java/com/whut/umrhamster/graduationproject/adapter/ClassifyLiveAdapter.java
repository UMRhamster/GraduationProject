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
import com.whut.umrhamster.graduationproject.model.bean.Live;

import java.util.List;

public class ClassifyLiveAdapter extends RecyclerView.Adapter<ClassifyLiveAdapter.ItemViewHolder> {
    private List<Live> liveList;

    private OnItemClickListener onItemClickListener;

    public ClassifyLiveAdapter(List<Live> liveList){
        this.liveList = liveList;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_item_classify_live,parent,false);
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
        Live live = liveList.get(position);
        Picasso.get().load(live.getCover()).into(holder.rivCover);
        holder.tvNickname.setText(live.getTeacher().getNickname());
        holder.tvNov.setText(""+live.getViewers());
        holder.tvTitle.setText(live.getTitle());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return liveList == null ? 0 : liveList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView rivCover;
        TextView tvNickname;
        TextView tvNov;
        TextView tvTitle;
        public ItemViewHolder(View itemView) {
            super(itemView);
            rivCover = itemView.findViewById(R.id.custom_rv_item_classify_live_iv_cover);
            tvNickname = itemView.findViewById(R.id.custom_rv_item_classify_live_rl_tv_teacher);
            tvNov = itemView.findViewById(R.id.custom_rv_item_classify_live_rl_tv_nop);
            tvTitle = itemView.findViewById(R.id.custom_rv_item_classify_live_tv_title);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
