package com.whut.umrhamster.graduationproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.model.bean.Comment;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.view.CircleImageView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ItemViewHolder> {
    private List<Comment> commentList;

    public CommentAdapter(List<Comment> commentList){
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_item_comment,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Student student = commentList.get(position).getStudent();
        Picasso.get().load(student.getAvatar()).into(holder.civIcon);
        holder.tvNickname.setText(student.getNickname());
        holder.tvContent.setText(commentList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return commentList == null ? 0 : commentList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civIcon;
        TextView tvNickname;
        TextView tvDate;
        TextView tvContent;
        public ItemViewHolder(View itemView) {
            super(itemView);

            civIcon = itemView.findViewById(R.id.custom_rv_item_comment_civ_icon);
            tvNickname = itemView.findViewById(R.id.custom_rv_item_comment_tv_nickname);
            tvDate = itemView.findViewById(R.id.custom_rv_item_comment_tv_date);
            tvContent = itemView.findViewById(R.id.custom_rv_item_comment_tv_content);
        }
    }
}
