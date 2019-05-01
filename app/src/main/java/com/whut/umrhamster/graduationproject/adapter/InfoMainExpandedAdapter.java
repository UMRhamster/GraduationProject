package com.whut.umrhamster.graduationproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hgdendi.expandablerecycleradapter.BaseExpandableRecyclerViewAdapter;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.model.bean.InfoChildBean;
import com.whut.umrhamster.graduationproject.model.bean.InfoGroupBean;
import com.whut.umrhamster.graduationproject.utils.other.IconUtil;
import com.whut.umrhamster.graduationproject.utils.other.TimeUtil;

import java.util.List;

public class InfoMainExpandedAdapter extends BaseExpandableRecyclerViewAdapter<InfoGroupBean,InfoChildBean,
        InfoMainExpandedAdapter.GroupViewHolder,InfoMainExpandedAdapter.ChildViewHolder> {
    List<InfoGroupBean> groupBeanList;

    public InfoMainExpandedAdapter(List<InfoGroupBean> groupBeanList){
        this.groupBeanList = groupBeanList;
    }

    @Override
    public int getGroupCount() {
        return groupBeanList.size();
    }

    @Override
    public InfoGroupBean getGroupItem(int groupIndex) {
        return groupBeanList.get(groupIndex);
    }

    @Override
    public GroupViewHolder onCreateGroupViewHolder(ViewGroup parent, int groupViewType) {
        return new GroupViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_info_group,parent,false));
    }

    @Override
    public void onBindGroupViewHolder(GroupViewHolder holder, InfoGroupBean groupBean, boolean isExpand) {
        holder.tvTitle.setText(groupBean.getName());
        holder.tvTotalTime.setText(TimeUtil.time2String(groupBean.getTotalTime()));
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int childViewType) {
        return new ChildViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_info_child,parent,false));
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, InfoGroupBean groupBean, InfoChildBean infoChildBean) {
        holder.tvTitle.setText(infoChildBean.getName());
        holder.ivIcon.setImageResource(IconUtil.getTypeResourceByInt(infoChildBean.getId()));
        holder.tvTime.setText(TimeUtil.time2String(infoChildBean.getTime()));
    }

    static class GroupViewHolder extends BaseExpandableRecyclerViewAdapter.BaseGroupViewHolder{
        TextView tvTitle;
        ImageView ivArrow;
        TextView tvTotalTime;
//        View decor;
        GroupViewHolder(View itemView){
            super(itemView);
            tvTitle = itemView.findViewById(R.id.rv_info_group_tv_title);
            ivArrow = itemView.findViewById(R.id.rv_info_group_iv_arr);
            tvTotalTime = itemView.findViewById(R.id.rv_info_group_tv_totaltime);
//            decor = itemView.findViewById(R.id.rv_info_group_v_decor);
        }
        @Override
        protected void onExpandStatusChanged(RecyclerView.Adapter relatedAdapter, boolean isExpanding) {
            if (isExpanding){
                ivArrow.setImageResource(R.drawable.expan_arr_down);
            }else {
                ivArrow.setImageResource(R.drawable.expan_arr_right);
            }
        }
    }

    static class ChildViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        ImageView ivIcon;
        TextView tvTime;
        public ChildViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.rv_info_child_tv_title);
            ivIcon = itemView.findViewById(R.id.rv_info_child_iv_icon);
            tvTime = itemView.findViewById(R.id.rv_info_child_tv_time);
        }
    }
}
