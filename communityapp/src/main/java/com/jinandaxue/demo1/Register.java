package com.jinandaxue.demo1;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jinandaxue.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class Register extends Activity{
    private EditText phonenum;
    private EditText yanzhengnum;
    private EditText password;
    private Button submit;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                String result=(String) msg.obj;
                try {
                    Log.e("ceshi",result);
                    JSONObject jsonObject=new JSONObject(result);
                    Toast.makeText(Register.this,jsonObject.optString("msg"),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initView();
    }

    private void initView() {
        phonenum=(EditText)findViewById(R.id.phonenum);
        yanzhengnum=(EditText)findViewById(R.id.yanzhengnum);
        password=(EditText)findViewById(R.id.password);
        submit=(Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone=phonenum.getText().toString();
                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(Register.this,"电话号码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                String email=yanzhengnum.getText().toString();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this,"邮箱不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                String pass=password.getText().toString();
                if (TextUtils.isEmpty(pass)){
                    Toast.makeText(Register.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                final HashMap<String,String> params=new HashMap<>();
                params.put("phone",phone);
                params.put("password",pass);
                params.put("email",email);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                       String result=HttpUtils.requestPost("http://192.168.0.178:8080/community/UsersServlet?method=register",params);
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
