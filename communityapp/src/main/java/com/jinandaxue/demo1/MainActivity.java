package com.jinandaxue.demo1;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jinandaxue.entity.BannerBean;
import com.jinandaxue.fragment.CommunityFragment;
import com.jinandaxue.fragment.HomeFragment;
import com.jinandaxue.fragment.InformationFragment;
import com.jinandaxue.fragment.MyFragment;
import com.jinandaxue.utils.Config;
import com.jinandaxue.utils.GlideImageLoader;
import com.jinandaxue.utils.HttpUtils;
import com.jinandaxue.utils.MyApplication;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import com.jinandaxue.service.RouteService;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{
    private RadioButton rb_bottom_home, rb_bottom_my, rb_bottom_info,rb_bottom_community;
    private int tab = 1;
    private FragmentManager fManager;
    private HomeFragment mHomeFragment;
    private InformationFragment mInfoFragment;
    private CommunityFragment mCommunityFragment;
    private MyFragment mMyFragment;
    private ImageView Iv_home_bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.addRecordActivity(this);
        EventBus.getDefault().register(this);
        rb_bottom_home = (RadioButton) findViewById(R.id.rb_bottom_home);
        rb_bottom_my = (RadioButton) findViewById(R.id.rb_bottom_my);
        rb_bottom_info = (RadioButton) findViewById(R.id.rb_bottom_info);
        rb_bottom_community = (RadioButton) findViewById(R.id.rb_bottom_community);

        Iv_home_bg = (ImageView) findViewById(R.id.Iv_home_bg);
        mHomeFragment=new HomeFragment();
        mInfoFragment=new InformationFragment();
        mCommunityFragment=new CommunityFragment();
        mMyFragment=new MyFragment();
        // 默认切换 homeFragment
        tab = 1;
        changeToF1();

        initListener();
        Intent intent = new Intent(this, RouteService.class);
        startService(intent);
    }
    private void initListener() {
        rb_bottom_home.setOnClickListener(this);
        rb_bottom_my.setOnClickListener(this);
        rb_bottom_info.setOnClickListener(this);
        rb_bottom_community.setOnClickListener(this);
        Iv_home_bg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_bottom_home:
                tab = 1;
                changeToF1();
                break;
            case R.id.rb_bottom_info:
                tab = 2;
                changeToF2();
                break;
            case R.id.rb_bottom_community:
                tab = 3;
                changeToF3("");
                break;
            case R.id.rb_bottom_my:
                tab = 4;
                changeToF4();
                break;
            case R.id.Iv_home_bg:
                Intent intent=new Intent(this,PublishMomentsActivity.class);
                startActivity(intent);
                break;
        }
    }
    /**
     * 切换homeFragment
     */
    private void changeToF1() {
        fManager = getSupportFragmentManager();
        FragmentTransaction transaction = fManager.beginTransaction();
        commonChange(transaction);

        rb_bottom_home.setChecked(true);

        if (mHomeFragment == null) {
            mHomeFragment=new HomeFragment();
            transaction.add(R.id.container, mHomeFragment,"f1");
        } else {
            transaction.show(mHomeFragment);

        }
        transaction.commitAllowingStateLoss();

    }
    /**
     * 切换infoFragment
     */
    private void changeToF2() {
        fManager = getSupportFragmentManager();
        FragmentTransaction transaction = fManager.beginTransaction();

        commonChange(transaction);
        rb_bottom_info.setChecked(true);
        if (mInfoFragment == null) {
            transaction.add(R.id.container, new InformationFragment(), "f2");
        } else {
            transaction.show(mInfoFragment);
        }
        transaction.commitAllowingStateLoss();
    }
    /**
     * 切换infoFragment
     */
    private void changeToF3(String key) {
        fManager = getSupportFragmentManager();
        FragmentTransaction transaction = fManager.beginTransaction();

        commonChange(transaction);
        rb_bottom_community.setChecked(true);
        Bundle bund=new Bundle();
        if (!TextUtils.isEmpty(key)){
            bund.putString("key",key);
        }

        if (mCommunityFragment == null) {
            mCommunityFragment=new CommunityFragment();

            transaction.add(R.id.container, mCommunityFragment, "f3");

        } else {
            //mCommunityFragment.setArguments(bund);

            transaction.show(mCommunityFragment);
        }
        mCommunityFragment.initData(key);
        transaction.commitAllowingStateLoss();
    }
    private void changeToF4() {
        fManager = getSupportFragmentManager();
        FragmentTransaction transaction = fManager.beginTransaction();
        commonChange(transaction);
        rb_bottom_my.setChecked(true);
        if (mMyFragment == null) {
            transaction.add(R.id.container, new MyFragment(), "f4");
        } else {
            transaction.show(mMyFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 每次切换时 隐藏以前打开的fragment 类似删除
     *
     * @param transaction
     */
    private void commonChange(FragmentTransaction transaction) {
        mHomeFragment = (HomeFragment) fManager.findFragmentByTag("f1");
        mInfoFragment = (InformationFragment) fManager.findFragmentByTag("f2");
        mCommunityFragment = (CommunityFragment) fManager.findFragmentByTag("f3");
        mMyFragment = (MyFragment) fManager.findFragmentByTag("f4");


        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mInfoFragment != null) {
            transaction.hide(mInfoFragment);
        }
        if (mMyFragment != null) {
            transaction.hide(mMyFragment);
        }
        if (mCommunityFragment!=null){
            transaction.hide(mCommunityFragment);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }
    @Subscribe
    public void onEventMainThread(Integer type){
        Log.d("XX", "EventBus收到int:"+type);
        //Toast.makeText(this, "处理eventbus请求", 0).show();
        if(type==TWO){//二手市场
            //tvMain.performClick();//模拟点击首页
            //mainFragment.setCurrentItem(1);//跳转到推荐页面
            tab=3;
            changeToF3("二手市场");
        }else if (type==NEWS){
            tab=3;
            changeToF3("小区新闻");
        }
    }

   public static  int TWO=1;
   public static  int NEWS=2;
}
