package com.jinandaxue.demo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class MyActivity extends AppCompatActivity implements View.OnClickListener{
    private RelativeLayout rl_one;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my);

        rl_one= (RelativeLayout) findViewById(R.id.rl_one);
        rl_one.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_one:{
                Intent intent=new Intent(MyActivity.this,PersonInfoActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
