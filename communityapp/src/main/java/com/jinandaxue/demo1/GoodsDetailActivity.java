package com.jinandaxue.demo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.jinandaxue.entity.LoginBean;
import com.jinandaxue.utils.Config;
import com.jinandaxue.utils.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;

import okhttp3.Call;

public class GoodsDetailActivity extends AppCompatActivity {

    private SimpleDraweeView iv_logo;
    private TextView tv_goodsname;
    private TextView tv_goodsdescribe;
    private String logo;
    private String goodsname;
    private String goodsdescribe;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_goods_detail);
        id=getIntent().getStringExtra("id");
        logo=getIntent().getStringExtra("logo");
        goodsname=getIntent().getStringExtra("goodsname");
        goodsdescribe=getIntent().getStringExtra("goodsdescribe");

        iv_logo= (SimpleDraweeView) findViewById(R.id.iv_logo);
        tv_goodsname= (TextView) findViewById(R.id.tv_goodsname);
        tv_goodsdescribe= (TextView) findViewById(R.id.tv_goodsdescribe);

        if (!TextUtils.isEmpty(logo)){
            iv_logo.setImageURI(Config.URL+logo);
        }
        tv_goodsname.setText("商品名称:"+goodsname);
        tv_goodsdescribe.setText("商品描述:"+goodsdescribe);

        final HashMap<String,String> params=new HashMap<>();
        params.put("uid",MyApplication.userBean.getId()+"");
        params.put("gid",id);
        OkHttpUtils.post()//
                .url(Config.URL+"history/add")
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

                    }
                });
    }
}
