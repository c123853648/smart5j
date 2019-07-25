package com.jinandaxue.demo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jinandaxue.entity.LoginBean;
import com.jinandaxue.utils.Config;
import com.jinandaxue.utils.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class RepairActivity extends AppCompatActivity {
    private EditText et_name;
    private EditText et_lou;
    private EditText et_home;
    private EditText et_mobile;
    private EditText et_content;
    private Button bt_commit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair);

        et_name=(EditText)findViewById(R.id.et_name);
        et_lou=(EditText)findViewById(R.id.et_lou);
        et_home=(EditText)findViewById(R.id.et_home);
        et_mobile=(EditText)findViewById(R.id.et_mobile);
        et_content=(EditText)findViewById(R.id.et_content);
        bt_commit= (Button) findViewById(R.id.bt_commit);

        bt_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=et_name.getText().toString();
                if (TextUtils.isEmpty(name)){
                    Toast.makeText(RepairActivity.this,"请输入小区名称",Toast.LENGTH_SHORT).show();
                    return;
                }
                String lou=et_lou.getText().toString();
                if (TextUtils.isEmpty(lou)){
                    Toast.makeText(RepairActivity.this,"请输入哪栋楼",Toast.LENGTH_SHORT).show();
                    return;
                }
                String home=et_home.getText().toString();
                if (TextUtils.isEmpty(home)){
                    Toast.makeText(RepairActivity.this,"请输入房间号",Toast.LENGTH_SHORT).show();
                    return;
                }
                String mobile=et_mobile.getText().toString();
                if (TextUtils.isEmpty(mobile)){
                    Toast.makeText(RepairActivity.this,"请输入联系电话",Toast.LENGTH_SHORT).show();
                    return;
                }
                String content=et_content.getText().toString();
                if (TextUtils.isEmpty(content)){
                    Toast.makeText(RepairActivity.this,"请输入故障描叙",Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String,String> params=new HashMap<>();
                params.put("name",name);
                params.put("lou",lou);
                params.put("home",home);
                params.put("home",mobile);
                params.put("contact",home);
                params.put("content",content);
                OkHttpUtils.post()//
                        .url(Config.URL+"repair/add")
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
                                LoginBean loginBean=gson.fromJson(response, LoginBean.class);
                                if (loginBean.getResultcode()==200){
                                    Toast.makeText(RepairActivity.this,"报修成功",Toast.LENGTH_SHORT).show();
                                    HashMap<String,String> params=new HashMap<>();
                                    params.put("uid",MyApplication.userBean.getId()+"");
                                    params.put("behavename","报修");
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
                                                    finish();
                                                }
                                            });
                                    //finish();
                                }
                            }
                        });
            }
        });
    }
}
