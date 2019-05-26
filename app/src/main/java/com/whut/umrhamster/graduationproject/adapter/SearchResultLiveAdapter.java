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
import com.whut.umrhamster.graduationproject.model.bean.Live;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;

import java.util.List;

public class SearchResultLiveAdapter extends RecyclerView.Adapter<SearchResultLiveAdapter.ItemViewHolder> {
    private List<Live> liveList;
    private Context context;

    private OnItemClickListener onItemClickListener;
    public SearchResultLiveAdapter(List<Live> liveList,Context context){
        this.liveList = liveList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_item_search_result_live,parent,false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Picasso.with(context)
                .load(liveList.get(position).getCover())
                .resize(AdaptionUtil.dp2px(context,360),AdaptionUtil.dp2px(context,101))
                .config(Bitmap.Config.RGB_565)
                .into(holder.rivCover);
        holder.tvTitle.setText(liveList.get(position).getTitle());
        holder.tvTeacher.setText(liveList.get(position).getTeacher().getNickname());
        holder.tvViewers.setText(""+liveList.get(position).getViewers());
        holder.tvClassify.setText(liveList.get(position).getClassify().getName());
        if (liveList.get(position).getStatus() == 0){
            holder.tvStatus.setText("未开播");
            holder.tvStatus.setBackgroundResource(R.drawable.text_round_bg_color_gray);
        }else {
            holder.tvStatus.setText("直播中");
            holder.tvStatus.setBackgroundResource(R.drawable.text_round_bg_color_theme);
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return liveList == null ? 0:liveList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView rivCover;
        TextView tvTitle;
        TextView tvTeacher;
        TextView tvViewers;
        TextView tvClassify;
        TextView tvStatus;
        public ItemViewHolder(View itemView) {
            super(itemView);
            rivCover = itemView.findViewById(R.id.custom_rv_item_search_result_live_iv_cover);
            tvTitle = itemView.findViewById(R.id.custom_rv_item_search_result_live_tv_title);
            tvTeacher = itemView.findViewById(R.id.custom_rv_item_search_result_live_rl_tv_teacher);
            tvViewers = itemView.findViewById(R.id.custom_rv_item_search_result_live_rl_tv_nop);
            tvClassify = itemView.findViewById(R.id.custom_rv_item_search_result_live_tv_classify);
            tvStatus = itemView.findViewById(R.id.custom_rv_item_search_result_live_tv_status);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onMenuClick(int position);
    }
}
