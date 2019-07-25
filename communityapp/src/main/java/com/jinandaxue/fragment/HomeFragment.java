package com.jinandaxue.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jinandaxue.adapter.GoodsAdapter;
import com.jinandaxue.demo1.GoodsDetailActivity;
import com.jinandaxue.demo1.MainActivity;
import com.jinandaxue.demo1.PersonInfoActivity;
import com.jinandaxue.demo1.PublishListActivity;
import com.jinandaxue.demo1.R;
import com.jinandaxue.demo1.RepairActivity;
import com.jinandaxue.entity.BannerBean;
import com.jinandaxue.entity.GoodsBean;
import com.jinandaxue.entity.Message;
import com.jinandaxue.entity.WeatherBean;
import com.jinandaxue.utils.Config;
import com.jinandaxue.utils.GlideImageLoader;
import com.jinandaxue.utils.MyApplication;
import com.jinandaxue.view.WrapContentListView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;


public class HomeFragment extends Fragment implements View.OnClickListener{
    private Banner banner;
    private TextView tv_bao;
    private TextView tv_twoshop;
    private TextView tv_nes;
    private TextView tv_wether;
    private ListView listview;
    private List<GoodsBean> mDatas;
    private GoodsAdapter goodsAdapter;

    private GoodsAdapter recommendAdapter;
    private List<GoodsBean> recommendDatas;
    private View view;
    private TextView tv_title1;
    private TextView tv_title2;
    private TextView tv_time1;
    private TextView tv_time2;
    private RelativeLayout rl_one;
    private LinearLayout ll_bottom;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Fresco.initialize(getActivity());
        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // rl_one= (RelativeLayout) view.findViewById(R.id.rl_one);
       // rl_one.setOnClickListener(this);
        //设置点击事件
        mDatas=new ArrayList<>();
        recommendDatas=new ArrayList<>();
        goodsAdapter=new GoodsAdapter(getActivity(),mDatas);
        recommendAdapter=new GoodsAdapter(getActivity(),recommendDatas);
        listview = (ListView) view.findViewById(R.id.listview);
        LayoutInflater inflater = LayoutInflater.from(getActivity());;
        View headView = inflater.inflate(R.layout.item_head, null);


        banner = (Banner) headView.findViewById(R.id.banner);
        ll_bottom = (LinearLayout) headView.findViewById(R.id.ll_bottom);
        WrapContentListView contentListView=(WrapContentListView)headView.findViewById(R.id.contentListView);
        contentListView.setAdapter(recommendAdapter);


        tv_bao = (TextView) headView.findViewById(R.id.tv_bao);
        tv_wether = (TextView) headView.findViewById(R.id.tv_wether);
        tv_title1 = (TextView) headView.findViewById(R.id.tv_title1);
        tv_title2 = (TextView) headView.findViewById(R.id.tv_title2);
        tv_time1 = (TextView) headView.findViewById(R.id.tv_time1);
        tv_time2 = (TextView) headView.findViewById(R.id.tv_time2);

        tv_bao.setOnClickListener(this);
        tv_twoshop = (TextView) headView.findViewById(R.id.tv_twoshop);

        tv_twoshop.setOnClickListener(this);
        tv_nes = (TextView) headView.findViewById(R.id.tv_nes);

        tv_nes.setOnClickListener(this);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());


        listview.addHeaderView(headView);
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,data);//新建并配置ArrayAapeter
        listview.setAdapter(goodsAdapter);
        final HashMap<String,String> params=new HashMap<>();
        OkHttpUtils.post()//
                .url(Config.URL+"banner/findAll")
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
                        List<BannerBean> list = gson.fromJson(response, new TypeToken<List<BannerBean>>() {
                        }.getType());
                        if (list.size()>0){
                            //设置图片集合
                            List<String> images=new ArrayList<>();
                            for (BannerBean bean:list){
                                images.add(Config.URL+bean.getUrl());
                            }
                            banner.setImages(images);
                            //设置轮播时间
                            banner.setDelayTime(1500);
                            //设置指示器位置（当banner模式中有指示器时）
                            banner.setIndicatorGravity(BannerConfig.CENTER);
                            //banner设置方法全部调用完毕时最后调用
                            banner.start();
                        }

                    }
                });
        OkHttpUtils.post()//
                .url(Config.URL+"index/getWeather")
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
                        WeatherBean weatherBean=gson.fromJson(response, WeatherBean.class);
                        WeatherBean.DataBean.ForecastBean forecastBean =weatherBean.getData().getForecast().get(0);
                        String type=forecastBean.getType();
                        String high=forecastBean.getHigh();
                        String low=forecastBean.getLow();
                        String result=type+"\n"+low.substring(3)+"-"+high.substring(3);
                        tv_wether.setText(result);
                    }
                });
        //获取商品数据
        OkHttpUtils.post()//
                .url(Config.URL+"goods/findShouAll")
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
                        List<GoodsBean> list = gson.fromJson(response, new TypeToken<List<GoodsBean>>() {
                        }.getType());
                        if (list.size()>0){
                            mDatas.clear();
                            for (GoodsBean bean:list){
                                mDatas.add(bean);
                            }
                            goodsAdapter.notifyDataSetChanged();
                        }

                    }
                });
        //获取推荐商品数据
        params.put("uid", MyApplication.userBean.getId()+"");
        OkHttpUtils.post()//
                .url(Config.URL+"goods/recommendAll")
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
                        List<GoodsBean> list = gson.fromJson(response, new TypeToken<List<GoodsBean>>() {
                        }.getType());
                        if (list.size()>0){
                            recommendDatas.clear();
                            for (GoodsBean bean:list){
                                recommendDatas.add(bean);
                            }
                            recommendAdapter.notifyDataSetChanged();
                            ll_bottom.setVisibility(View.VISIBLE);
                        }else {
                            ll_bottom.setVisibility(View.GONE);
                        }

                    }
                });
        //获取小区播报数据
        OkHttpUtils.post()//
                .url(Config.URL+"message/findShou")
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
                        List<Message> list = gson.fromJson(response, new TypeToken<List<Message>>() {
                        }.getType());
                        if (list.size()>0){
                            tv_title1.setText(list.get(0).getTitle());
                            tv_time1.setText(list.get(0).getCreatetime().substring(5,10));
                            tv_title2.setText(list.get(1).getTitle());
                            tv_time2.setText(list.get(1).getCreatetime().substring(5,10));
                        }

                    }
                });

        //设置点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                GoodsBean goodsBean=mDatas.get(position-1);
                Intent intent=new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("id",goodsBean.getId()+"");
                intent.putExtra("logo",goodsBean.getGoodsimage());
                intent.putExtra("goodsname",goodsBean.getGoodsname());
                intent.putExtra("goodsdescribe",goodsBean.getGoodsdescribe());
                startActivity(intent);
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_one:{
                Intent intent=new Intent(getActivity(),PersonInfoActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.tv_bao:{
                Intent intent=new Intent(getActivity(),RepairActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.tv_twoshop:{
                EventBus.getDefault().post(MainActivity.TWO);
               // Intent intent=new Intent(getActivity(),PublishListActivity.class);
               // startActivity(intent);
                break;
            }
            case R.id.tv_nes:{
                EventBus.getDefault().post(MainActivity.NEWS);
               // Intent intent=new Intent(getActivity(),PublishListActivity.class);
               // startActivity(intent);
                break;
            }
        }
    }
}
