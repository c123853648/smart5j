package com.jinandaxue.demo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jinandaxue.adapter.MessageAdapter;
import com.jinandaxue.adapter.PublishAdapter;
import com.jinandaxue.entity.LoginBean;
import com.jinandaxue.entity.Message;
import com.jinandaxue.entity.Publish;
import com.jinandaxue.utils.Config;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static android.R.id.list;

public class PublishListActivity extends AppCompatActivity {
    private PublishAdapter publishAdapter;
    private List<Publish> mDatas;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_publish_list);

        lv= (ListView) findViewById(R.id.lv);
        mDatas=new ArrayList<>();
        publishAdapter=new PublishAdapter(this,mDatas);
        lv.setAdapter(publishAdapter);

        initData();
    }

    private void initData() {
        OkHttpUtils.post()//
                .url(Config.URL+"twoinfo/findAll")
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
                            for (Publish p:list){
                                mDatas.add(p);
                            }
                            publishAdapter.notifyDataSetChanged();
                    }
                });
    }
}
