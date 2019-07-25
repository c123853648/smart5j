package com.jinandaxue.demo1;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jinandaxue.adapter.MyPublishAdapter;
import com.jinandaxue.entity.GoodsBean;
import com.jinandaxue.entity.Publish;
import com.jinandaxue.utils.Config;
import com.jinandaxue.utils.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class MyPublishActivity extends AppCompatActivity {

    private ListView lv_listview;
    private MyPublishAdapter myPublishAdapter;
    private List<Publish> mDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_publish);

        lv_listview= (ListView) findViewById(R.id.lv_listview);
        mDatas=new ArrayList<>();
        myPublishAdapter=new MyPublishAdapter(this,mDatas);
        lv_listview.setAdapter(myPublishAdapter);
        Map<String,String> params=new HashMap<>();
        params.put("uid", MyApplication.userBean.getId()+"");
        OkHttpUtils.post()//
                .url(Config.URL+"moments/findMyList")
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
                        List<Publish> list = gson.fromJson(response, new TypeToken<List<Publish>>() {
                        }.getType());
                        if (list.size()>0){
                            mDatas.clear();
                            for (Publish bean:list){
                                mDatas.add(bean);
                            }
                            myPublishAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }
}
