package com.jinandaxue.demo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jinandaxue.adapter.HistoryAdapter;
import com.jinandaxue.entity.GoodsBean;
import com.jinandaxue.utils.Config;
import com.jinandaxue.utils.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class HistoryActivity extends AppCompatActivity {
    private HistoryAdapter historyAdapter;
    private List<GoodsBean> mDatas;
    private ListView lv_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        lv_list= (ListView) findViewById(R.id.lv_list);
        mDatas=new ArrayList<>();
        historyAdapter=new HistoryAdapter(this,mDatas);

        lv_list.setAdapter(historyAdapter);

        //获取商品数据
        Map<String,String> params=new HashMap<>();
        params.put("uid", MyApplication.userBean.getId()+"");
        OkHttpUtils.post()//
                .url(Config.URL+"history/findAll")
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
                        List<GoodsBean> list = gson.fromJson(response, new TypeToken<List<GoodsBean>>() {
                        }.getType());
                        if (list.size()>0){
                            mDatas.clear();
                            for (GoodsBean bean:list){
                                mDatas.add(bean);
                            }
                            historyAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }
}
