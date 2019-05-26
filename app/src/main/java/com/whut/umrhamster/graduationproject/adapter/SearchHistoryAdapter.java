package com.whut.umrhamster.graduationproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.whut.umrhamster.graduationproject.R;

import java.util.List;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ItemViewHolder> {
    private List<String> histories;

    private OnItemClickListener onItemClickListener;

    public SearchHistoryAdapter(List<String> histories){
        this.histories = histories;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_item_search_history,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick((Integer) v.getTag());
            }
        });
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {
        if (position == histories.size()){
            holder.tvName.setText("清除历史记录");
            holder.ivIcon.setImageResource(R.drawable.trash_can);
            holder.ivDelete.setVisibility(View.INVISIBLE);
        }else {
            holder.tvName.setText(histories.get(position));
            holder.ivIcon.setImageResource(R.drawable.search_history);
            holder.ivDelete.setVisibility(View.VISIBLE);
            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemDeleteClick(holder.getAdapterPosition());
                }
            });
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return histories == null ? 0:histories.size()+1;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        ImageView ivDelete;
        ImageView ivIcon;
        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.custom_rv_item_search_history_name);
            ivDelete = itemView.findViewById(R.id.custom_rv_item_search_history_delete);
            ivIcon = itemView.findViewById(R.id.custom_rv_item_search_history_icon);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onItemDeleteClick(int position);
    }
}
