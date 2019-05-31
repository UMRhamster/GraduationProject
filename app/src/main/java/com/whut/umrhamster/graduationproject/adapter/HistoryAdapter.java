package com.whut.umrhamster.graduationproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.model.bean.History;
import com.whut.umrhamster.graduationproject.model.bean.Live;
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.utils.other.TimeUtil;
import com.whut.umrhamster.graduationproject.widget.SmoothCheckBox;

import java.util.Calendar;
import java.util.List;
import java.util.TreeSet;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int SIGN = 0, ITEM = 1, FOOT = 2;

    private List<History> resultList;
//    private List<Boolean> checkList;
    private TreeSet<Integer> checkSet;
//    private int timeDecor = 0;

    private Context context;
    private OnItemClickListener onItemClickListener;

    private boolean isEdit = false;
    private boolean isAllSelected = false;
//    private boolean passiveAllSelected = false;
//    private boolean activeAllSelected = false;

    public HistoryAdapter(List<History> historyList, TreeSet<Integer> checkSet, Context context){
        this.resultList = historyList;
        this.checkSet = checkSet;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SIGN){
            return new SignViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_item_history_sign,parent,false));
        }else if (viewType == ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_item_history,parent,false);
            final ViewHolder  holder = new ViewHolder(view);
            //点击事件处理
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    if (isEdit){
                        if (holder.checkBox.isChecked()){
                            holder.checkBox.setChecked(false);
                            checkSet.remove(position);
                        }else {
                            holder.checkBox.setChecked(true);
                            checkSet.add(position);
                        }
                        onItemClickListener.onItemClick(-1);
                    }else {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
            //长按点击事件处理
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = (int) v.getTag();

                    if (isEdit){
                        return false;
                    }else {
                        checkSet.add(position);
//                        inEditing();
                        onItemClickListener.onItemLongClick(position);
                        return true;
                    }
                }
            });
            return holder;
        }else {
            return new FootViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_item_history_foot,parent,false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == resultList.size()){
            return FOOT;
        }
        if (resultList.get(position).getTag() != null){
            return SIGN;
        }else {
            return ITEM;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SignViewHolder){
            ((SignViewHolder) holder).textView.setText(resultList.get(position).getTag());
        }else if (holder instanceof ViewHolder){
            History history = resultList.get(position);
            if (history.getType() == 1){  //视频
                ((ViewHolder)holder).tvStatus.setVisibility(View.INVISIBLE);
                ((ViewHolder)holder).progressBar.setVisibility(View.VISIBLE);
                ((ViewHolder)holder).tvTime.setVisibility(View.VISIBLE);
                ((ViewHolder)holder).view.setVisibility(View.VISIBLE);
                Video video = history.getVideo();
                Picasso.with(context).load(video.getCover()).into(((ViewHolder) holder).rivCover);
                ((ViewHolder)holder).tvTitle.setText(video.getTitle());
                ((ViewHolder)holder).tvTime.setText(String.format(context.getResources().getString(R.string.history_progress),
                        TimeUtil.millint2String(history.getWatchedTime()),
                        TimeUtil.millint2String(video.getTotaltime())));

                ((ViewHolder)holder).progressBar.setMax(video.getTotaltime());
                ((ViewHolder)holder).progressBar.setProgress(history.getWatchedTime());
            }else if (history.getType() == 0){  //直播
                Live live = history.getLive();
                Picasso.with(context).load(live.getCover()).into(((ViewHolder) holder).rivCover);
                ((ViewHolder)holder).tvStatus.setVisibility(View.VISIBLE);
                ((ViewHolder)holder).progressBar.setVisibility(View.INVISIBLE);
                ((ViewHolder)holder).tvTime.setVisibility(View.INVISIBLE);
                ((ViewHolder)holder).view.setVisibility(View.INVISIBLE);
                ((ViewHolder)holder).tvTitle.setText(live.getTitle());
                if (live.getStatus() == 0){
                    ((ViewHolder) holder).tvStatus.setText("未开播");
                    ((ViewHolder) holder).tvStatus.setBackgroundResource(R.drawable.text_round_bg_color_gray);
                }else {
                    ((ViewHolder) holder).tvStatus.setText("直播中");
                    ((ViewHolder) holder).tvStatus.setBackgroundResource(R.drawable.text_round_bg_color_theme);
                }
            }
            if (isEdit){
                ((ViewHolder) holder).checkBox.setVisibility(View.VISIBLE);
                if (isAllSelected){
                    ((ViewHolder) holder).checkBox.setChecked(true);
                }else {
                    if (checkSet.contains(position)){
                        ((ViewHolder) holder).checkBox.setChecked(true);
                    }else {
                        ((ViewHolder) holder).checkBox.setChecked(false);
                    }
                }
            }else {
                ((ViewHolder) holder).checkBox.setVisibility(View.GONE);
            }
            //今天
            Calendar todayC = Calendar.getInstance();
            todayC.set(Calendar.HOUR_OF_DAY,0);
            todayC.set(Calendar.MINUTE,0);
            todayC.set(Calendar.SECOND,0);
            //昨天
            Calendar yesterdayC = Calendar.getInstance();
            yesterdayC.set(Calendar.DAY_OF_MONTH,yesterdayC.get(Calendar.DAY_OF_MONTH)-1);
            yesterdayC.set(Calendar.HOUR_OF_DAY,0);
            yesterdayC.set(Calendar.MINUTE,0);
            yesterdayC.set(Calendar.SECOND,0);

            Calendar date = Calendar.getInstance();
            date.setTime(history.getLastTime());
            if (date.after(todayC)){
                ((ViewHolder)holder).tvDate.setText(String.format(context.getResources().getString(R.string.history_last_time_today),
                        TimeUtil.fix0(date.get(Calendar.HOUR_OF_DAY)),
                        TimeUtil.fix0(date.get(Calendar.MINUTE))));
            }else if (date.after(yesterdayC)){
                ((ViewHolder)holder).tvDate.setText(String.format(context.getResources().getString(R.string.history_last_time_yesterday),
                        TimeUtil.fix0(date.get(Calendar.HOUR_OF_DAY)),
                        TimeUtil.fix0(date.get(Calendar.MINUTE))));
            }else {
                ((ViewHolder)holder).tvDate.setText(String.format(context.getResources().getString(R.string.history_last_time_early),
                        TimeUtil.fix0(date.get(Calendar.MONTH)+1),
                        TimeUtil.fix0(date.get(Calendar.DAY_OF_MONTH)),
                        TimeUtil.fix0(date.get(Calendar.HOUR_OF_DAY)),
                        TimeUtil.fix0(date.get(Calendar.MINUTE))));
            }
        }else {
            if (position == resultList.size() && isEdit){
                ((FootViewHolder)holder).linearLayout.setVisibility(View.VISIBLE);
            }else {
                ((FootViewHolder)holder).linearLayout.setVisibility(View.GONE);
            }
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return resultList == null ? 0 : resultList.size()+1;
    }

    public void chooseAll(){
        isAllSelected = true;
//        activeAllSelected = true;
        if (isEdit){
            for (int i=0;i<resultList.size();i++){
                if (resultList.get(i).getTag() == null){
                    checkSet.add(i);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void cancelAll(){
        isAllSelected = false;
//        activeAllSelected = false;
        checkSet.clear();
        notifyDataSetChanged();
    }

    public void inEditing(){
        isEdit = true;
        notifyDataSetChanged();
    }

    public void outEditing(){
        isEdit = false;
        isAllSelected = false;
        checkSet.clear();
        notifyDataSetChanged();
    }

    public boolean isAllSelected() {
        return isAllSelected;
    }

    public void setAllSelected(boolean allSelected) {
        isAllSelected = allSelected;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView rivCover;  //封面
        ProgressBar progressBar;    //进度条
        View view;                  //阴影背景
        TextView tvStatus;          //直播状态
        TextView tvTime;            //视频时长
        TextView tvTitle;           //标题
        TextView tvNickname;        //作者名
        TextView tvDate;            //记录时间
        TextView tvIdentify;        //身份
        SmoothCheckBox checkBox;
        public ViewHolder(View itemView) {
            super(itemView);
            rivCover = itemView.findViewById(R.id.custom_rv_item_history_rl_riv_cover);
            progressBar = itemView.findViewById(R.id.custom_rv_item_history_rl_pb);
            view = itemView.findViewById(R.id.custom_rv_item_history_rl_v_bg);
            tvTime = itemView.findViewById(R.id.custom_rv_item_history_rl_tv_time);
            tvStatus = itemView.findViewById(R.id.custom_rv_item_history_rl_tv_status);
            tvTitle = itemView.findViewById(R.id.custom_rv_item_history_rl_tv_title);
            tvNickname = itemView.findViewById(R.id.custom_rv_item_history_rl_tv_nickname);
            tvDate = itemView.findViewById(R.id.custom_rv_item_history_rl_tv_date);
            tvIdentify = itemView.findViewById(R.id.custom_rv_item_history_rl_tv_icon);
            checkBox = itemView.findViewById(R.id.custom_rv_item_history_cb);

        }
    }
    //时间标志ViewHolder
    public class SignViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public SignViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.custom_rv_item_history_sign_tv);
        }
    }
    //上拉加载ViewHolder
    public class FootViewHolder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
        public FootViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.custom_rv_item_history_foot_ll);
        }
    }

    //点击事件处理
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onItemLongClick(int position);
    }
}
