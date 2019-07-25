package com.jinandaxue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinandaxue.demo1.R;
import com.jinandaxue.entity.Message;
import com.jinandaxue.entity.ReviewBean;

import java.util.List;



public class ReviewAdapter extends BaseAdapter {
    Context context;
    private LayoutInflater inflater;
    private List<ReviewBean> dataLists;

    public ReviewAdapter(Context context, List<ReviewBean> dataLists) {
        this.context = context;
        this.dataLists = dataLists;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataLists.size();
    }

    @Override
    public Object getItem(int position) {
        return dataLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_review, parent, false);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvContent.setText((String)dataLists.get(position).getNickname()+":"+dataLists.get(position).getReviewcontent());
        viewHolder.tvTime.setText((String)dataLists.get(position).getReviewtime());
        return convertView;
    }
    class ViewHolder {
        TextView tvContent;
        TextView tvTime;
    }
}
