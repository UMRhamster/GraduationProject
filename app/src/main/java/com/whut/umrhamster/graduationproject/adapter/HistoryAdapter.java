package com.whut.umrhamster.graduationproject.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.comparator.AllComparator;
import com.whut.umrhamster.graduationproject.comparator.HistoryComparator;
import com.whut.umrhamster.graduationproject.comparator.LiveComparator;
import com.whut.umrhamster.graduationproject.comparator.VideoComparator;
import com.whut.umrhamster.graduationproject.model.bean.History;
import com.whut.umrhamster.graduationproject.model.bean.Live;
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.utils.other.TimeUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int SIGN = 0, ITEM = 1;

//    private List<History> historyList;
    private List<History> resultList;
    private int timeDecor = 0;

    private Context context;

    public HistoryAdapter(List<History> historyList, Context context){
        this.resultList = historyList;
        this.context = context;
        initAdapter();
    }

    public void add(History history){
        resultList.add(history);
    }

    public void addAll(List<History> historyList){
        Log.d("test",""+historyList.size()+this.resultList.size());
        Collections.sort(historyList,new HistoryComparator());
        Calendar todayC = Calendar.getInstance();
        todayC.set(Calendar.HOUR_OF_DAY,0);
        todayC.set(Calendar.MINUTE,0);
        todayC.set(Calendar.SECOND,0);

        Calendar yesterdayC = Calendar.getInstance();
        yesterdayC.set(Calendar.DAY_OF_MONTH,yesterdayC.get(Calendar.DAY_OF_MONTH)-1);
        yesterdayC.set(Calendar.HOUR_OF_DAY,0);
        yesterdayC.set(Calendar.MINUTE,0);
        yesterdayC.set(Calendar.SECOND,0);
//        resultList.addAll(historyList);
        for (History history : historyList){
            if (history.getLastTime().after(todayC.getTime())){
                if (timeDecor == 0) {
                    resultList.add(new History("今天",-1));
                    timeDecor++;
                }
            }else if (history.getLastTime().after(yesterdayC.getTime())){
                if (timeDecor == 0 || timeDecor == 1){
                    resultList.add(new History("昨天",-1));
                    timeDecor+=2;
                }
            }else {
                if (timeDecor == 0 || timeDecor == 1 || timeDecor == 2 || timeDecor == 3){
                    resultList.add(new History("更早",-1));
                    timeDecor+=4;
                }
            }
            resultList.add(history);
        }
        Log.d("test",""+historyList.size()+this.resultList.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SIGN){
            return new SignViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_item_history_sign,parent,false));
        }else {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_item_history,parent,false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (resultList.get(position).getTotalTime() == -1){
            return SIGN;
        }else {
            return ITEM;
        }
    }

    private void initAdapter(){
        resultList = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        Log.d("dasd","11");
        if (holder instanceof SignViewHolder){
            ((SignViewHolder) holder).textView.setText(resultList.get(position).getTitle());
        }else if (holder instanceof ViewHolder){
            History history = resultList.get(position);
            if (history.getType() == 1){  //视频
                ((ViewHolder)holder).tvStatus.setVisibility(View.INVISIBLE);
                ((ViewHolder)holder).progressBar.setVisibility(View.VISIBLE);
                ((ViewHolder)holder).tvTime.setVisibility(View.VISIBLE);
                ((ViewHolder)holder).view.setVisibility(View.VISIBLE);
                ((ViewHolder)holder).tvTitle.setText(history.getTitle());
                ((ViewHolder)holder).tvTime.setText(TimeUtil.int2String(history.getWatchedTime())+"/"+TimeUtil.int2String(history.getTotalTime()));

                ((ViewHolder)holder).progressBar.setMax(history.getTotalTime());
                ((ViewHolder)holder).progressBar.setProgress(history.getWatchedTime());
            }else if (history.getType() == 0){  //直播
                ((ViewHolder)holder).tvStatus.setVisibility(View.VISIBLE);
                ((ViewHolder)holder).progressBar.setVisibility(View.INVISIBLE);
                ((ViewHolder)holder).tvTime.setVisibility(View.INVISIBLE);
                ((ViewHolder)holder).view.setVisibility(View.INVISIBLE);
                ((ViewHolder)holder).tvTitle.setText(history.getTitle());
                if (history.getStatus() == 0){
                    ((ViewHolder) holder).tvStatus.setText("未开播");
                }else {
                    ((ViewHolder) holder).tvStatus.setText("直播中");
                }
            }
            Picasso.get().load(history.getCover()).into(((ViewHolder) holder).rivCover);
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
//                ((ViewHolder)holder).tvDate.setText("今天"+date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE));
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
        }
    }

    @Override
    public int getItemCount() {
        return resultList == null ? 0 : resultList.size();
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

        }
    }

    public class SignViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public SignViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.custom_rv_item_history_sign_tv);
        }
    }
}
