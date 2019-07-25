package com.jinandaxue.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jinandaxue.demo1.R;
import com.jinandaxue.entity.Publish;
import com.jinandaxue.utils.Config;

import java.util.List;



public class MyPublishAdapter extends BaseAdapter {
    Context context;
    private LayoutInflater inflater;
    private List<Publish> dataLists;

    public MyPublishAdapter(Context context, List<Publish> dataLists) {
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
            convertView = inflater.inflate(R.layout.item_mypublish, parent, false);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.iv_logo = (SimpleDraweeView) convertView.findViewById(R.id.iv_logo);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_content.setText("内容:"+(String)dataLists.get(position).getContent());
        viewHolder.tv_time.setText("时间:"+dataLists.get(position).getCreatetime());
        if (!TextUtils.isEmpty(dataLists.get(position).getLogo())){
            viewHolder.iv_logo.setImageURI(Config.URL+dataLists.get(position).getLogo());
        }else {
            viewHolder.iv_logo.setVisibility(View.GONE);
        }
        return convertView;
    }
    class ViewHolder {
        TextView tv_content;
        TextView tv_time;
        SimpleDraweeView iv_logo;
    }
}
