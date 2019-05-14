package com.whut.umrhamster.graduationproject.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.model.bean.Live;
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<Live> liveList;
    private Context context;

    private OnItemClickListener onItemClickListener;

    public HomeAdapter(List<Live> liveList, Context context){
        this.liveList = liveList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_item_home,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick((Integer) v.getTag());
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickListener.onItemLongClick((Integer) v.getTag());
                return true;
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //test for donothing
//        holder.roundedImageView.setCornerRadius(AdaptionUtil.dp2px(context,8),AdaptionUtil.dp2px(context,8),0,0);
        Picasso.get()
                .load(liveList.get(position).getCover())
                .into(holder.rivCover);
        holder.tvTitle.setText(liveList.get(position).getTitle());
        holder.tvTeacher.setText(liveList.get(position).getTeacher().getNickname());
        holder.tvViewers.setText(""+liveList.get(position).getViewers());
        holder.tvClssify.setText(liveList.get(position).getClassify().getName());
        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return liveList == null ? 0 : liveList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView rivCover;
        TextView tvTitle;
        TextView tvTeacher;
        TextView tvViewers;
        TextView tvClssify;
        public ViewHolder(View itemView) {
            super(itemView);
            rivCover = itemView.findViewById(R.id.custom_rv_item_home_iv_cover);
            tvTitle = itemView.findViewById(R.id.custom_rv_item_home_tv_title);
            tvTeacher = itemView.findViewById(R.id.custom_rv_item_home_rl_tv_teacher);
            tvViewers = itemView.findViewById(R.id.custom_rv_item_home_rl_tv_nop);
            tvClssify = itemView.findViewById(R.id.custom_rv_item_home_tv_classify);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public static interface OnItemClickListener{
        void onItemClick(int position);
        void onItemLongClick(int position);
    }
}
