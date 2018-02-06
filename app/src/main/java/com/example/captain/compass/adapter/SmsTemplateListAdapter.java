package com.example.captain.compass.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.captain.compass.R;
import com.example.captain.compass.bean.SmsTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by captain on 2018/2/6.
 */

public class SmsTemplateListAdapter extends RecyclerView.Adapter<SmsTemplateListAdapter.SmsTemplateViewHolder> {
    private List<SmsTemplate> smsTemplates = null;
    private OnItemClickListener onItemClickListener = null;
    private OnItemLongClickListener onItemLongClickListener = null;

    public SmsTemplateListAdapter(List<SmsTemplate> smsTemplates) {
        this.smsTemplates = smsTemplates;
    }

    @Override
    public SmsTemplateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_sms_template, parent, false);
        return new SmsTemplateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SmsTemplateViewHolder holder, int position) {
        SmsTemplate smsTemplate = smsTemplates.get(position);
        holder.tvTitle.setText(smsTemplate.getTitle());
        holder.tvContent.setText(smsTemplate.getContent());
        holder.tvUpdateTime.setText(new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA).format(new Date(smsTemplate.getUpdateTime())));
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(smsTemplate, position));
        holder.itemView.setOnLongClickListener(v -> onItemLongClickListener.onItemLongClick(smsTemplate, position));
    }

    @Override
    public int getItemCount() {
        return smsTemplates.size();
    }

    public class SmsTemplateViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_content)
        TextView tvContent;

        @BindView(R.id.tv_update_time)
        TextView tvUpdateTime;

        public SmsTemplateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(SmsTemplate smsTemplate, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(SmsTemplate smsTemplate, int position);
    }
}
