package com.jinandaxue.demo1;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jinandaxue.entity.LoginBean;
import com.jinandaxue.utils.Config;
import com.jinandaxue.utils.HttpUtils;
import com.jinandaxue.utils.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class LoginActivity extends AppCompatActivity {

    private EditText et_phone,et_password;
    private Button bt_login,bt_register;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                String result=(String) msg.obj;
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    Toast.makeText(LoginActivity.this,jsonObject.optString("reason"),Toast.LENGTH_SHORT).show();
                    if (jsonObject.optString("resultcode").equals(200)){
                        //登录成功跳转代码
                        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initview( );

        //给按钮添加监听事件
        /**
         * 1.借助onclick方法（Java上没有此类监听事件的方法）
         * 2.继承OnclickListner接口------>实现该接口的方法（Onclick方法）
         * 3.内部类的方法
         * 4.匿名内部类
         */
        /**
         * Intent--->用来跳转或者是传值
         */

    }

    private void initview() {
        et_phone= (EditText) findViewById(R.id.et_phone);
        et_password= (EditText) findViewById(R.id.et_password);
        bt_login= (Button) findViewById(R.id.bt_login);
        bt_register= (Button) findViewById(R.id.bt_register);
        //登录按钮的点击事件
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sno=et_phone.getText().toString();
                if (TextUtils.isEmpty(sno)){
                    Toast.makeText(LoginActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                String passowrd=et_password.getText().toString();
                if (TextUtils.isEmpty(passowrd)){
                    Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                final HashMap<String,String> params=new HashMap<>();
                params.put("phone",sno);
                params.put("password",passowrd);
                OkHttpUtils.post()//
                        .url(Config.URL+"users/login")
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
                                LoginBean loginBean=gson.fromJson(response,LoginBean.class);
                                if (loginBean.getResultcode()==200){
                                    MyApplication.userBean=loginBean.getData();
                                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this,loginBean.getReason(),Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
        //注册按钮的点击事件
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    public void forgrtpass(View view){
        Intent intent=new Intent(LoginActivity.this,Forgetpass.class);
        startActivity(intent);
    }

    public void register(View view){
        Intent intent=new Intent(LoginActivity.this,Register.class);
        startActivity(intent);
    }
}
