package com.jinandaxue.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jinandaxue.adapter.CommunityAdapter;
import com.jinandaxue.demo1.GoodsDetailActivity;
import com.jinandaxue.demo1.PublishMomentsActivity;
import com.jinandaxue.demo1.R;
import com.jinandaxue.entity.LoginBean;
import com.jinandaxue.entity.MomentsVo;
import com.jinandaxue.utils.Config;
import com.jinandaxue.utils.MyApplication;
import com.jinandaxue.view.WrapContentListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;


public class CommunityFragment extends Fragment implements View.OnClickListener{
    private View view;
    private RelativeLayout rl_one;
    private TextView tv_add;
    private ListView listview;
    private List<MomentsVo> mDatas;
    private CommunityAdapter communityAdapter;

    private boolean flag;
    private TextView tv_1,tv_2,tv_3,tv_4,tv_5;

    private String key;

    private CommunityAdapter recommendAdapter;
    private List<MomentsVo> recommendDatas;
    private LinearLayout ll_bottom;

    private TextView text;
    @Override
    public void onResume() {
        super.onResume();
        if (flag){
            initData(null);
        }
        flag=true;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Fresco.initialize(getActivity());
        view = inflater.inflate(R.layout.fragment_community, container, false);
        Bundle bundle=getArguments();
    //    key=bundle.getString("key");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDatas=new ArrayList<>();
        communityAdapter=new CommunityAdapter(getActivity(),mDatas);
        //设置点击事件
        tv_add=(TextView) view.findViewById(R.id.tv_add);
        listview= (ListView) view.findViewById(R.id.listview);
        text= (TextView) view. findViewById(R.id.text);
        tv_add.setOnClickListener(this);
        tv_1 = (TextView) view.findViewById(R.id.tv_1);
        tv_1.setOnClickListener(this);
        tv_2 = (TextView) view.findViewById(R.id.tv_2);
        tv_2.setOnClickListener(this);
        tv_3 = (TextView) view.findViewById(R.id.tv_3);
        tv_3.setOnClickListener(this);
        tv_4 = (TextView) view.findViewById(R.id.tv_4);
        tv_4.setOnClickListener(this);
        tv_5 = (TextView) view.findViewById(R.id.tv_5);
        tv_5.setOnClickListener(this);

        initData(key);

        LayoutInflater inflater = LayoutInflater.from(getActivity());;
        View headView = inflater.inflate(R.layout.item_commenthead, null);

        recommendDatas=new ArrayList<>();
        recommendAdapter=new CommunityAdapter(getActivity(),recommendDatas);
        ll_bottom = (LinearLayout) headView.findViewById(R.id.ll_bottom);
        WrapContentListView contentListView=(WrapContentListView)headView.findViewById(R.id.contentListView);
        contentListView.setAdapter(recommendAdapter);

//        listview.addHeaderView(headView);
        listview.setAdapter(communityAdapter);
        final HashMap<String,String> params=new HashMap<>();
        params.put("uid",MyApplication.userBean.getId()+"");
        OkHttpUtils.post()//
                .url(Config.URL+"moments/findTuiAll")
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
                        List<MomentsVo> list = gson.fromJson(response, new TypeToken<List<MomentsVo>>() {
                        }.getType());
                        recommendDatas.clear();
                        for (MomentsVo p:list){
                            recommendDatas.add(p);
                        }
                        if (recommendDatas.size()>0){
                            ll_bottom.setVisibility(View.VISIBLE);
                        }else {
                            ll_bottom.setVisibility(View.GONE);
                        }
                        recommendAdapter.notifyDataSetChanged();

                    }
                });
        //设置点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                MomentsVo goodsBean=mDatas.get(position);
                if (goodsBean.getType()==1){
                    Intent intent=new Intent(getActivity(), GoodsDetailActivity.class);
                    intent.putExtra("id",goodsBean.getId()+"");
                    intent.putExtra("logo",goodsBean.getGoodsimage());
                    intent.putExtra("goodsname",goodsBean.getGoodsname());
                    intent.putExtra("goodsdescribe",goodsBean.getGoodsdescribe());
                    startActivity(intent);
                }

            }
        });
        //点赞
        communityAdapter.setClickListenerInterface(new CommunityAdapter.ClickListenerInterface() {
            @Override
            public void clickLove(final int position, View view) {
                final HashMap<String,String> params=new HashMap<>();
                params.put("id",mDatas.get(position).getId()+"");
                OkHttpUtils.postString()
                            .url(Config.URL+"moments/updateLove")
                            .content(new Gson().toJson(params))
                            .mediaType(MediaType.parse("application/json; charset=utf-8"))
                            .build()
                            .execute(new StringCallback()
                            {

                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Log.e("ceshi",e.getMessage());
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    Log.e("ceshi",response);
                                    mDatas.get(position).setLoved((Integer.parseInt(mDatas.get(position).getLoved())+1)+"");
                                    communityAdapter.notifyDataSetChanged();

                                }
                            });
            }
        });

        params.put("uid",MyApplication.userBean.getId()+"");
        params.put("behavename","社区朋友圈");
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

        double sum=Double.parseDouble(MyApplication.userBean.getScore1())+Double.parseDouble(MyApplication.userBean.getScore2())+Double.parseDouble(MyApplication.userBean.getScore3())+Double.parseDouble(MyApplication.userBean.getScore4());
        if (sum<0){
            listview.setVisibility(View.GONE);
            text.setVisibility(View.VISIBLE);
        }else{
            listview.setVisibility(View.VISIBLE);
            text.setVisibility(View.GONE);
        }
    }

    public void initData(String key){
        final HashMap<String,String> params=new HashMap<>();
        if (!TextUtils.isEmpty(key))
            params.put("key",key);
        params.put("uid",MyApplication.userBean.getId()+"");
        OkHttpUtils.post()//
                .url(Config.URL+"moments/findAll")
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
                        List<MomentsVo> list = gson.fromJson(response, new TypeToken<List<MomentsVo>>() {
                        }.getType());
                        mDatas.clear();
                        for (MomentsVo p:list){
                            mDatas.add(p);
                        }
                        communityAdapter.notifyDataSetChanged();

                    }
                });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_add:{
                Intent intent=new Intent(getActivity(),PublishMomentsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.tv_1:{
                initData("趣闻轶事");
//                Intent intent=new Intent(getActivity(),PublishListActivity.class);
//                startActivity(intent);
                break;
            }
            case R.id.tv_2:{
                initData("顺风车");
                //Intent intent=new Intent(getActivity(),PublishListActivity.class);
               // startActivity(intent);
                break;
            }
            case R.id.tv_3:{
                initData("二手市场");
                break;
            }
            case R.id.tv_4:{
                //Intent intent=new Intent(getActivity(),PublishListActivity.class);
                //startActivity(intent);
                break;
           }
            case R.id.tv_5:{
                initData("小区新闻");
               // Intent intent=new Intent(getActivity(),PublishListActivity.class);
                //startActivity(intent);
                break;
            }
        }
    }
}
