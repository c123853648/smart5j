package com.jinandaxue.demo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.jinandaxue.adapter.MessageAdapter;
import com.jinandaxue.entity.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    private List<Message> mDatas;
    private ListView lv;
    private MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        lv= (ListView) findViewById(R.id.lv);
        mDatas=new ArrayList<>();
        messageAdapter=new MessageAdapter(this,mDatas);
        lv.setAdapter(messageAdapter);
    }
}
