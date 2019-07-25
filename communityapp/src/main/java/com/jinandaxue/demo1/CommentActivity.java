package com.jinandaxue.demo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jinandaxue.adapter.ReviewAdapter;
import com.jinandaxue.entity.MomentsVo;
import com.jinandaxue.entity.ReviewBean;
import com.jinandaxue.utils.Config;
import com.jinandaxue.utils.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

public class CommentActivity extends AppCompatActivity {
    private SimpleDraweeView iv_image;
    private SimpleDraweeView iv_logo;
    private TextView tv_nickname;
    private TextView tv_time;
    private TextView tv_content;
    private ListView lv_comment;
    private List<ReviewBean> mDatas;
    private ReviewAdapter reviewAdapter;
    private Button bt_commit;
    private EditText et_content;
    private  String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_comment);
//        intent.putExtra("id",dataLists.get(position).getId()+"");
//        intent.putExtra("content",dataLists.get(position).getContent());
//        intent.putExtra("time",dataLists.get(position).getCreatetime());
//        intent.putExtra("headlogo",dataLists.get(position).getHeadlogo());
//        intent.putExtra("logo",dataLists.get(position).getLogo());
        mDatas=new ArrayList<>();
        id=getIntent().getStringExtra("id");
        String content=getIntent().getStringExtra("content");
        String time=getIntent().getStringExtra("time");
        String headlogo=getIntent().getStringExtra("headlogo");
        String logo=getIntent().getStringExtra("logo");
        String nickname=getIntent().getStringExtra("nickname");
        et_content=(EditText)findViewById(R.id.et_content);
        bt_commit= (Button) findViewById(R.id.bt_commit);
        bt_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content=et_content.getText().toString();
                if(TextUtils.isEmpty(content)){
                    Toast.makeText(CommentActivity.this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                final HashMap<String,String> params=new HashMap<>();
                params.put("cid",id);
                params.put("uid", MyApplication.userBean.getId()+"");
                params.put("reviewcontent", content);
                OkHttpUtils.post()//
                        .url(Config.URL+"review/add")
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
                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    if (jsonObject.optInt("resultcode")==200){
                                        Toast.makeText(CommentActivity.this, "评论成功!", Toast.LENGTH_SHORT).show();
                                        et_content.setText("");
                                        initData();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
            }
        });
        iv_image=(SimpleDraweeView)findViewById(R.id.iv_image);
        iv_image.setImageURI(Config.URL+headlogo);
        iv_logo=(SimpleDraweeView)findViewById(R.id.iv_logo);
        if (!TextUtils.isEmpty(logo)){
            iv_logo.setImageURI(Config.URL+logo);
        }
        tv_nickname=(TextView)findViewById(R.id.tv_nickname);
        tv_nickname.setText(nickname);
        tv_time=(TextView)findViewById(R.id.tv_time);
        tv_time.setText(time);
        tv_content=(TextView)findViewById(R.id.tv_content);
        tv_content.setText(content);
        lv_comment= (ListView) findViewById(R.id.lv_comment);
        reviewAdapter=new ReviewAdapter(this,mDatas);
        lv_comment.setAdapter(reviewAdapter);

        initData();

    }

    private void initData() {
        final HashMap<String,String> params=new HashMap<>();
        params.put("cid",id);
        OkHttpUtils.post()//
                .url(Config.URL+"review/findAll")
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
                        List<ReviewBean> list = gson.fromJson(response, new TypeToken<List<ReviewBean>>() {
                        }.getType());
                        mDatas.clear();
                        for (ReviewBean p:list){
                            mDatas.add(p);
                        }
                        reviewAdapter.notifyDataSetChanged();

                    }
                });
    }
}
