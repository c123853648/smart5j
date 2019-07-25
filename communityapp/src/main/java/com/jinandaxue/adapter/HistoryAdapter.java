package com.jinandaxue.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jinandaxue.demo1.R;
import com.jinandaxue.entity.GoodsBean;
import com.jinandaxue.utils.Config;

import java.util.List;



public class HistoryAdapter extends BaseAdapter {
    Context context;
    private LayoutInflater inflater;
    private List<GoodsBean> dataLists;

    public HistoryAdapter(Context context, List<GoodsBean> dataLists) {
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
            convertView = inflater.inflate(R.layout.item_history, parent, false);
            viewHolder.tv_goodsname = (TextView) convertView.findViewById(R.id.tv_goodsname);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_goodsdescribe = (TextView) convertView.findViewById(R.id.tv_goodsdescribe);
            viewHolder.iv_goodsimage = (SimpleDraweeView) convertView.findViewById(R.id.iv_goodsimage);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_goodsname.setText((String)dataLists.get(position).getGoodsname());
        viewHolder.tv_goodsdescribe.setText((String)dataLists.get(position).getGoodsdescribe());
        //viewHolder.tv_time.setText("浏览时间:"+(String)dataLists.get(position).getCreatetime());
        if(!TextUtils.isEmpty(dataLists.get(position).getGoodsimage())){
            viewHolder.iv_goodsimage.setImageURI(Config.URL+dataLists.get(position).getGoodsimage());
        }
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
        TextView tv_goodsname;
        TextView tv_goodsdescribe;
        TextView tv_time;
        SimpleDraweeView iv_goodsimage;
        SimpleDraweeView iv_logo;
    }
}
