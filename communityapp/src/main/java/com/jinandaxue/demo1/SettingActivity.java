package com.jinandaxue.demo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jinandaxue.utils.MyApplication;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    private RelativeLayout rl_one;
    private TextView tv_num;
    private Button bt_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        MyApplication.addRecordActivity(this);
        int i = (int)(10+Math.random()*(20-10+1));
        rl_one= (RelativeLayout) findViewById(R.id.rl_one);
        tv_num= (TextView) findViewById(R.id.tv_num);
        tv_num.setText(i+"M");
        bt_logout= (Button) findViewById(R.id.bt_logout);
        bt_logout.setOnClickListener(this);
        rl_one.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_one:{
                tv_num.setText("0M");
                Toast.makeText(this, "清理成功", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.bt_logout:{
                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);
                MyApplication.clearRecordActivity();
                break;
            }
        }
    }
}
