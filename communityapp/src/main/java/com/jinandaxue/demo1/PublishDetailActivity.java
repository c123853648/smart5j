package com.jinandaxue.demo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jinandaxue.entity.LoginBean;
import com.jinandaxue.utils.Config;
import com.jinandaxue.utils.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;

import okhttp3.Call;

public class PublishDetailActivity extends AppCompatActivity {
    private TextView tv_title;
    private TextView tv_time;
    private TextView tv_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_detail);

        String title=getIntent().getStringExtra("title");
        String time=getIntent().getStringExtra("time");
        String content=getIntent().getStringExtra("content");
        tv_title=(TextView)findViewById(R.id.tv_title);
        tv_title.setText("标题:"+title);
        tv_time=(TextView)findViewById(R.id.tv_time);
        tv_time.setText("通知时间:"+time);
        tv_content=(TextView)findViewById(R.id.tv_content);
        tv_content.setText(content);
        HashMap<String,String> params=new HashMap<>();
        params.put("uid",MyApplication.userBean.getId()+"");
        params.put("behavename","物业");
        OkHttpUtils.post()//
                .url(Config.URL+"behavior/addOrUpdate")
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
                        Gson gson=new Gson();
                        LoginBean loginBean=gson.fromJson(response,LoginBean.class);
                        if (loginBean.getResultcode()==200){
                            MyApplication.userBean=loginBean.getData();
                        }
                    }
                });
    }
}
