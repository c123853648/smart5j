package com.jinandaxue.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jinandaxue.adapter.MessageAdapter;
import com.jinandaxue.demo1.PersonInfoActivity;
import com.jinandaxue.demo1.PublishDetailActivity;
import com.jinandaxue.demo1.R;
import com.jinandaxue.entity.Message;
import com.jinandaxue.entity.MomentsVo;
import com.jinandaxue.utils.Config;
import com.jinandaxue.utils.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;


public class InformationFragment extends Fragment implements View.OnClickListener{
    private List<Message> mDatas;
    private ListView lv;
    private MessageAdapter messageAdapter;
    private View view;
    private RelativeLayout rl_one;
    private TextView text;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_message, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        //设置点击事件
        lv= (ListView)view. findViewById(R.id.lv);
        text= (TextView) view. findViewById(R.id.text);
        mDatas=new ArrayList<>();
        messageAdapter=new MessageAdapter(getActivity(),mDatas);
        lv.setAdapter(messageAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), PublishDetailActivity.class);
                intent.putExtra("title",mDatas.get(position).getTitle());
                intent.putExtra("time",mDatas.get(position).getCreatetime());
                intent.putExtra("content",mDatas.get(position).getContent());
                startActivity(intent);
            }
        });

        double sum=Double.parseDouble(MyApplication.userBean.getScore1())+Double.parseDouble(MyApplication.userBean.getScore2())+Double.parseDouble(MyApplication.userBean.getScore3())+Double.parseDouble(MyApplication.userBean.getScore4());
        if (sum<0){
            lv.setVisibility(View.GONE);
            text.setVisibility(View.VISIBLE);
        }else{
            lv.setVisibility(View.VISIBLE);
            text.setVisibility(View.GONE);
        }
    }

    private void initData() {
        final HashMap<String,String> params=new HashMap<>();
        OkHttpUtils.post()//
                .url(Config.URL+"message/findAll")
                .params(params)//
                .build()//
                .execute(new StringCallback()
                {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("ceshi",e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("ceshi",response);
                        Gson gson=new Gson();
                        List<Message> list = gson.fromJson(response, new TypeToken<List<Message>>() {
                        }.getType());
                        mDatas.clear();
                        for (Message p:list){
                            mDatas.add(p);
                        }
                        messageAdapter.notifyDataSetChanged();

                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_one:{
                Intent intent=new Intent(getActivity(),PersonInfoActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
