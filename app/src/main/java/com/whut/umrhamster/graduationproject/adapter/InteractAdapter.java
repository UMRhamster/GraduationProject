package com.whut.umrhamster.graduationproject.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.model.bean.Interact;

import java.util.List;

public class InteractAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Interact> interactList;

    public InteractAdapter(List<Interact> interactList){
        this.interactList = interactList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_item_interact,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SpannableString string = new SpannableString(interactList.get(position).getNickname()+":"+interactList.get(position).getContent());
        string.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")),interactList.get(position).getNickname().length()+1,string.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((ViewHolder)holder).textView.setText(string);

    }

    @Override
    public int getItemCount() {
        return interactList == null ? 0 : interactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.custom_rv_item_interact_tv);
        }
    }
}
