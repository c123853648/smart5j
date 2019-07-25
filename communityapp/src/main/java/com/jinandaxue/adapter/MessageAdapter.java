package com.jinandaxue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinandaxue.demo1.R;
import com.jinandaxue.entity.Message;

import java.util.List;



public class MessageAdapter extends BaseAdapter {
    Context context;
    private LayoutInflater inflater;
    private List<Message> dataLists;

    public MessageAdapter(Context context, List<Message> dataLists) {
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
            convertView = inflater.inflate(R.layout.item_message, parent, false);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText((String)dataLists.get(position).getTitle());
        viewHolder.tvContent.setText((String)dataLists.get(position).getContent());
        viewHolder.tvTime.setText((String)dataLists.get(position).getCreatetime());
     /*   viewHolder.llShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proId = dataLists.get(position).getId();
                price = dataLists.get(position).getPrice();
                clickListenerInterface.doAddPro(proId,price,v);
            }
        });*/
        return convertView;
    }
    class ViewHolder {
        TextView tvTitle;
        TextView tvContent;
        TextView tvTime;
    }
}
