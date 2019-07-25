package com.jinandaxue.demo1;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jinandaxue.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class Forgetpass extends Activity{
    private EditText yanzhengnum;
    private Button submit;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                String result=(String) msg.obj;
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    Toast.makeText(Forgetpass.this,jsonObject.optString("msg"),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginsuccess);
        initView();
    }

    private void initView() {
        yanzhengnum=(EditText)findViewById(R.id.yanzhengnum);
        submit=(Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=yanzhengnum.getText().toString();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(Forgetpass.this,"邮箱不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                final HashMap<String,String> params=new HashMap<>();
                params.put("email",email);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String result= HttpUtils.requestPost("http://192.168.0.178:8080/community/UsersServlet?method=findPass",params);
                        Message message=new Message();
                        message.what=1;
                        message.obj=result;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }
}
