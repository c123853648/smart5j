package com.jinandaxue.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jinandaxue.demo1.CommentActivity;
import com.jinandaxue.demo1.R;
import com.jinandaxue.entity.MomentsVo;
import com.jinandaxue.utils.Config;

import java.util.List;



public class CommunityAdapter extends BaseAdapter {
    Context context;
    private LayoutInflater inflater;
    private List<MomentsVo> dataLists;

    public CommunityAdapter(Context context, List<MomentsVo> dataLists) {
        this.context = context;
        this.dataLists = dataLists;
        this.inflater = LayoutInflater.from(context);
    }
    private ClickListenerInterface clickListenerInterface;
    public interface ClickListenerInterface {
        void clickLove(int position, View view);
    }

    public void setClickListenerInterface(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
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
    public int getItemViewType(int position) {
        //判断依据
        return dataLists.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        //布局个数
        return 2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
         ViewHolder viewHolder=null;
         ViewHolder2 viewHolder2=null;
        if (convertView == null) {
            switch (type){
                case 0:{
                    viewHolder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.item_community, parent, false);
                    viewHolder.tv_nickname = (TextView) convertView.findViewById(R.id.tv_nickname);
                    viewHolder.tv_com = (TextView) convertView.findViewById(R.id.tv_com);
                    viewHolder.tv_hover = (TextView) convertView.findViewById(R.id.tv_hover);
                    viewHolder.ll_hover = (LinearLayout) convertView.findViewById(R.id.ll_hover);
                    viewHolder.ll_com = (LinearLayout) convertView.findViewById(R.id.ll_com);
                    viewHolder.iv_image = (SimpleDraweeView) convertView.findViewById(R.id.iv_image);
                    viewHolder.iv_logo = (SimpleDraweeView) convertView.findViewById(R.id.iv_logo);
                    viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
                    viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                    convertView.setTag(viewHolder);
                    break;
                }
                case 1:{
                    viewHolder2 = new ViewHolder2();
                    convertView = inflater.inflate(R.layout.item_community2, parent, false);
                    viewHolder2.tv_goodsname = (TextView) convertView.findViewById(R.id.tv_goodsname);
                    viewHolder2.tv_goodsdescribe = (TextView) convertView.findViewById(R.id.tv_goodsdescribe);
                    viewHolder2.iv_goodsimage = (SimpleDraweeView) convertView.findViewById(R.id.iv_goodsimage);
                    convertView.setTag(viewHolder2);
                    break;
                }
            }


        }

            switch (type){
                case 0:{
                    viewHolder = (ViewHolder) convertView.getTag();
                    viewHolder.tv_nickname.setText((String)dataLists.get(position).getNickname());
                    viewHolder.tv_content.setText((String)dataLists.get(position).getContent());
                    viewHolder.tv_time.setText((String)dataLists.get(position).getCreatetime());
                    viewHolder.iv_image.setImageURI(Config.URL+dataLists.get(position).getHeadlogo());
                    viewHolder.tv_com.setText("评论:"+dataLists.get(position).getComments());
                    if(!TextUtils.isEmpty(dataLists.get(position).getLogo())){
                        viewHolder.iv_logo.setImageURI(Config.URL+dataLists.get(position).getLogo());
                    }
                    viewHolder.ll_com.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(context, CommentActivity.class);
                            intent.putExtra("id",dataLists.get(position).getId()+"");
                            intent.putExtra("content",dataLists.get(position).getContent());
                            intent.putExtra("time",dataLists.get(position).getCreatetime());
                            intent.putExtra("headlogo",dataLists.get(position).getHeadlogo());
                            intent.putExtra("logo",dataLists.get(position).getLogo());
                            intent.putExtra("nickname",dataLists.get(position).getNickname());
                            context.startActivity(intent);
                        }
                    });
                    viewHolder.ll_hover.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (clickListenerInterface!=null){
                                clickListenerInterface.clickLove(position,view);
                            }
                        }
                    });
                    break;
                }
                case 1:{
                    viewHolder2 = (ViewHolder2) convertView.getTag();
                    viewHolder2.tv_goodsname.setText((String)dataLists.get(position).getGoodsname());
                    viewHolder2.tv_goodsdescribe.setText((String)dataLists.get(position).getGoodsdescribe());
                    if(!TextUtils.isEmpty(dataLists.get(position).getGoodsimage())){
                        viewHolder2.iv_goodsimage.setImageURI(Config.URL+dataLists.get(position).getGoodsimage());
                    }
                    break;
                }
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
        TextView tv_nickname;
        TextView tv_content;
        TextView tv_time;
        SimpleDraweeView iv_image;
        SimpleDraweeView iv_logo;
        LinearLayout ll_hover;
        LinearLayout ll_com;
        TextView tv_com;
        TextView tv_hover;
    }
    class ViewHolder2 {
        TextView tv_goodsname;
        TextView tv_goodsdescribe;
        SimpleDraweeView iv_goodsimage;
        SimpleDraweeView iv_logo;
    }
}
