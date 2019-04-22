package com.whut.umrhamster.graduationproject.adapter;

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
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.comparator.AllComparator;
import com.whut.umrhamster.graduationproject.comparator.LiveComparator;
import com.whut.umrhamster.graduationproject.comparator.VideoComparator;
import com.whut.umrhamster.graduationproject.model.bean.Live;
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.utils.other.TimeUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ALL = 0, VIDEO = 1, LIVE = 2;
    private static final int SIGN = 0, ITEM = 1;

    private List<Object> objectList;
    private List<Object> resultList;
    private int type;
    private int timeDecor = 0;

    public HistoryAdapter(List<Object> objectList, int type){
        this.objectList = objectList;
        this.type = type;
        initAdapter();
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
        Object obj = resultList.get(position);
        if (obj instanceof Video){
            if (((Video)resultList.get(position)).getTotalTime() == -1){
                return SIGN;
            }else {
                return ITEM;
            }
        }else {
            if (((Live)resultList.get(position)).getStatus() == -1){
                return SIGN;
            }else {
                return ITEM;
            }
        }
    }

    private void initAdapter(){
        resultList = new ArrayList<>();
        Calendar todayC = Calendar.getInstance();
        todayC.set(Calendar.HOUR_OF_DAY,0);
        todayC.set(Calendar.MINUTE,0);
        todayC.set(Calendar.SECOND,0);

        Calendar yesterdayC = Calendar.getInstance();
        yesterdayC.set(Calendar.DAY_OF_MONTH,yesterdayC.get(Calendar.DAY_OF_MONTH)-1);
        yesterdayC.set(Calendar.HOUR_OF_DAY,0);
        yesterdayC.set(Calendar.MINUTE,0);
        yesterdayC.set(Calendar.SECOND,0);
        switch (type){
            case ALL:
                Collections.sort(objectList,new AllComparator());
                break;
            case VIDEO:
                Collections.sort(objectList,new VideoComparator());
                break;
            case LIVE:
                Collections.sort(objectList,new LiveComparator());
                break;
            default:
                break;
        }
        for (Object object : objectList){
            if (object instanceof  Video){
                if (((Video)object).getDate().after(todayC)){
                    if (timeDecor == 0){
                        resultList.add(new Video("今天",-1));
                        timeDecor++;
                    }
                }else if (((Video)object).getDate().after(yesterdayC)){
                    if (timeDecor == 0 || timeDecor == 1){
                        resultList.add(new Video("昨天",-1));
                        timeDecor+=2;
                    }
                }else {
                    if (timeDecor == 0 || timeDecor == 1 || timeDecor == 2 || timeDecor == 3){
                        resultList.add(new Video("更早",-1));
                        timeDecor+=4;
                    }
                }
            }else {
                if (((Live)object).getCalendar().after(todayC)){
                    if (timeDecor == 0){
                        resultList.add(new Video("今天",-1));
                        timeDecor++;
                    }
                }else if (((Live)object).getCalendar().after(yesterdayC)){
                    if (timeDecor == 0 || timeDecor == 1){
                        resultList.add(new Video("昨天",-1));
                        timeDecor+=2;
                    }
                }else {
                    if (timeDecor == 0 || timeDecor == 1 || timeDecor == 2 || timeDecor == 3){
                        resultList.add(new Video("更早",-1));
                        timeDecor+=4;
                    }
                }
            }
            resultList.add(object);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SignViewHolder){
            ((SignViewHolder) holder).textView.setText(((Video)resultList.get(position)).getTitle());
        }else if (holder instanceof ViewHolder){
            Object obj =  resultList.get(position);
            if (obj instanceof Video){
                Video tmp = (Video) obj;
                ((ViewHolder)holder).tvStatus.setVisibility(View.INVISIBLE);
                ((ViewHolder)holder).progressBar.setVisibility(View.VISIBLE);
                ((ViewHolder)holder).tvTime.setVisibility(View.VISIBLE);
                ((ViewHolder)holder).view.setVisibility(View.VISIBLE);
                ((ViewHolder)holder).tvTitle.setText(tmp.getTitle());
                ((ViewHolder)holder).tvTime.setText(TimeUtil.int2String(tmp.getWatchedTime())+"/"+TimeUtil.int2String(tmp.getTotalTime()));

                Calendar todayC = Calendar.getInstance();
                todayC.set(Calendar.HOUR_OF_DAY,0);
                todayC.set(Calendar.MINUTE,0);
                todayC.set(Calendar.SECOND,0);

                Calendar yesterdayC = Calendar.getInstance();
                yesterdayC.set(Calendar.DAY_OF_MONTH,yesterdayC.get(Calendar.DAY_OF_MONTH)-1);
                yesterdayC.set(Calendar.HOUR_OF_DAY,0);
                yesterdayC.set(Calendar.MINUTE,0);
                yesterdayC.set(Calendar.SECOND,0);
                if (tmp.getDate().after(todayC)){
                    ((ViewHolder)holder).tvDate.setText("今天"+tmp.getDate().get(Calendar.HOUR_OF_DAY)+":"+tmp.getDate().get(Calendar.MINUTE));
                }else if (tmp.getDate().after(yesterdayC)){
                    ((ViewHolder)holder).tvDate.setText("昨天"+tmp.getDate().get(Calendar.HOUR_OF_DAY)+":"+tmp.getDate().get(Calendar.MINUTE));
                }else {
                    ((ViewHolder)holder).tvDate.setText((tmp.getDate().get(Calendar.MONTH)+1)+"-"+tmp.getDate().get(Calendar.DAY_OF_MONTH)+" "
                            +tmp.getDate().get(Calendar.HOUR_OF_DAY)+":"+tmp.getDate().get(Calendar.MINUTE));
                }
                ((ViewHolder)holder).progressBar.setMax(tmp.getTotalTime());
                ((ViewHolder)holder).progressBar.setProgress(tmp.getWatchedTime());
            }else if (obj instanceof Live){
                Live tmp = (Live) obj;
                ((ViewHolder)holder).tvStatus.setVisibility(View.VISIBLE);
                ((ViewHolder)holder).progressBar.setVisibility(View.INVISIBLE);
                ((ViewHolder)holder).tvTime.setVisibility(View.INVISIBLE);
                ((ViewHolder)holder).view.setVisibility(View.INVISIBLE);
                ((ViewHolder)holder).tvTitle.setText(tmp.getTitle());

                Calendar todayC = Calendar.getInstance();
                todayC.set(Calendar.HOUR_OF_DAY,0);
                todayC.set(Calendar.MINUTE,0);
                todayC.set(Calendar.SECOND,0);

                Calendar yesterdayC = Calendar.getInstance();
                yesterdayC.set(Calendar.DAY_OF_MONTH,yesterdayC.get(Calendar.DAY_OF_MONTH)-1);
                yesterdayC.set(Calendar.HOUR_OF_DAY,0);
                yesterdayC.set(Calendar.MINUTE,0);
                yesterdayC.set(Calendar.SECOND,0);
                if (tmp.getCalendar().after(todayC)){
                    ((ViewHolder)holder).tvDate.setText("今天"+tmp.getCalendar().get(Calendar.HOUR_OF_DAY)+":"+tmp.getCalendar().get(Calendar.MINUTE));
                }else if (tmp.getCalendar().after(yesterdayC)){
                    ((ViewHolder)holder).tvDate.setText("昨天"+tmp.getCalendar().get(Calendar.HOUR_OF_DAY)+":"+tmp.getCalendar().get(Calendar.MINUTE));
                }else {
                    ((ViewHolder)holder).tvDate.setText((tmp.getCalendar().get(Calendar.MONTH)+1)+"-"+tmp.getCalendar().get(Calendar.DAY_OF_MONTH)+" "
                            +tmp.getCalendar().get(Calendar.HOUR_OF_DAY)+":"+tmp.getCalendar().get(Calendar.MINUTE));
                }
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
