package com.whut.umrhamster.graduationproject.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.model.bean.Evaluation;
import com.whut.umrhamster.graduationproject.utils.other.TimeUtil;
import com.whut.umrhamster.graduationproject.view.CircleImageView;

import java.util.List;

public class TeacherEvaluationAdapter extends RecyclerView.Adapter<TeacherEvaluationAdapter.ItemViewHolder> {
    private List<Evaluation> evaluationList;
    private Context context;

    public TeacherEvaluationAdapter(List<Evaluation> evaluationList, Context context){
        this.evaluationList = evaluationList;
        this.context = context;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_item_teacher_evaluation,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Evaluation evaluation = evaluationList.get(position);
        Picasso.with(context).load(evaluation.getStudent().getAvatar()).config(Bitmap.Config.RGB_565)
                .placeholder(R.drawable.default_user_icon).error(R.drawable.default_user_icon)
                .into(holder.civIcon);
        holder.tvNickname.setText(evaluation.getStudent().getNickname());
        holder.tvDate.setText(TimeUtil.uptime2string(evaluation.getDate()));
        holder.tvContent.setText(evaluation.getContent());
    }

    @Override
    public int getItemCount() {
        return evaluationList == null ? 0 : evaluationList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civIcon;
        TextView tvNickname;
        TextView tvDate;
        TextView tvContent;
        public ItemViewHolder(View itemView) {
            super(itemView);
            civIcon = itemView.findViewById(R.id.custom_rv_item_evaluation_civ_icon);
            tvNickname = itemView.findViewById(R.id.custom_rv_item_evaluation_tv_nickname);
            tvDate = itemView.findViewById(R.id.custom_rv_item_evaluation_tv_date);
            tvContent = itemView.findViewById(R.id.custom_rv_item_evaluation_tv_content);
        }
    }
}
