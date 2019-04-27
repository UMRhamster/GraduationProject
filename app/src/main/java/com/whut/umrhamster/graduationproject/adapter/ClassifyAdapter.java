package com.whut.umrhamster.graduationproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.model.bean.Classify;
import com.whut.umrhamster.graduationproject.model.bean.History;

import java.util.List;

public class ClassifyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final static int TYPE_TITLE = 0;
    private final static int TYPE_ITEM = 1;

    private List<Classify> classifyList;

    public ClassifyAdapter(List<Classify> classifyList){
        this.classifyList = classifyList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM){
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_item_classify,parent,false));
        }else {
            return new TitleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.classificaiton_first,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){
            ((ViewHolder)holder).icon.setImageResource(classifyList.get(position).getIcon());
            ((ViewHolder)holder).title.setText(classifyList.get(position).getTitle());
        }else if (holder instanceof TitleViewHolder){
            if (position == 0){
                ((TitleViewHolder)holder).view.setVisibility(View.GONE);
            }
            ((TitleViewHolder)holder).title.setText(classifyList.get(position).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return classifyList == null ? 0 : classifyList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (classifyList.get(position).getIcon() == -1){
            return TYPE_TITLE;
        }else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager){
            GridLayoutManager gridManager = (GridLayoutManager) manager;
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type){
                        case TYPE_ITEM:
                            return 1;
                        case TYPE_TITLE:
                            return 4;
                        default:
                            return 1;
                    }
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView icon;
        TextView title;
        public ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.custom_rv_item_classify_iv_icon);
            title = itemView.findViewById(R.id.custom_rv_item_classify_iv_title);
        }
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView title;
        public TitleViewHolder(View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.classification_first_v);
            title = itemView.findViewById(R.id.classification_first_tv);
        }
    }
}
