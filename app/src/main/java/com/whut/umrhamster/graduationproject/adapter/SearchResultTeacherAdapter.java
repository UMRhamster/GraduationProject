package com.whut.umrhamster.graduationproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.model.bean.Teacher;
import com.whut.umrhamster.graduationproject.view.CircleImageView;

import java.util.List;

public class SearchResultTeacherAdapter extends RecyclerView.Adapter<SearchResultTeacherAdapter.ItemViewHolder> {
    private List<Teacher> teacherList;
    private Context context;

    private OnItemClickListener onItemClickListener;

    public SearchResultTeacherAdapter(List<Teacher> teacherList, Context context){
        this.teacherList = teacherList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_item_search_result_teacher,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Teacher teacher = teacherList.get(position);
        Picasso.with(context).load(teacher.getAvatar()).into(holder.civIcon);
        holder.tvNickname.setText(teacher.getNickname());
        holder.tvBrief.setText(teacher.getBrief());
//        holder.tvWatch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return teacherList == null ?0:teacherList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civIcon;
        TextView tvNickname;
        TextView tvBrief;
//        TextView tvWatch;
        public ItemViewHolder(View itemView) {
            super(itemView);
            civIcon = itemView.findViewById(R.id.rv_item_search_result_teacher_civ_icon);
            tvNickname = itemView.findViewById(R.id.rv_item_search_result_teacher_tv_nickname);
            tvBrief = itemView.findViewById(R.id.rv_item_search_result_teacher_tv_brief);
//            tvWatch = itemView.findViewById(R.id.rv_item_search_result_teacher_tv_watch);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onMenuClick(int position);
    }
}
