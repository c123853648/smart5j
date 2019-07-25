package com.jinandaxue.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jinandaxue.adapter.HistoryAdapter;
import com.jinandaxue.demo1.AboutActivity;
import com.jinandaxue.demo1.HistoryActivity;
import com.jinandaxue.demo1.MyPublishActivity;
import com.jinandaxue.demo1.PersonInfoActivity;
import com.jinandaxue.demo1.R;
import com.jinandaxue.demo1.SettingActivity;
import com.jinandaxue.utils.Config;
import com.jinandaxue.utils.MyApplication;


public class MyFragment extends Fragment implements View.OnClickListener{
    private View view;
    private RelativeLayout rl_one;
    private RelativeLayout rl_browse;
    private RelativeLayout rl_publish;
    private RelativeLayout rl_setting;
    private RelativeLayout rl_help;
    private SimpleDraweeView im_personlogo;
    private TextView tv_name;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Fresco.initialize(getActivity());
        view = inflater.inflate(R.layout.fragment_my, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rl_publish= (RelativeLayout) view.findViewById(R.id.rl_publish);
        rl_publish.setOnClickListener(this);
        rl_setting= (RelativeLayout) view.findViewById(R.id.rl_setting);
        rl_setting.setOnClickListener(this);
        rl_help= (RelativeLayout) view.findViewById(R.id.rl_help);
        rl_help.setOnClickListener(this);
        rl_browse= (RelativeLayout) view.findViewById(R.id.rl_browse);
        rl_browse.setOnClickListener(this);
        rl_one= (RelativeLayout) view.findViewById(R.id.rl_one);
        rl_one.setOnClickListener(this);
        im_personlogo= (SimpleDraweeView) view.findViewById(R.id.im_personlogo);
        tv_name= (TextView) view.findViewById(R.id.tv_name);
        im_personlogo.setImageURI(Config.URL+ MyApplication.userBean.getHeadlogo());
        tv_name.setText(MyApplication.userBean.getNickname());
        //设置点击事件
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_one:{
                Intent intent=new Intent(getActivity(),PersonInfoActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.rl_browse:{
                Intent intent=new Intent(getActivity(),HistoryActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.rl_publish:{
                Intent intent=new Intent(getActivity(),MyPublishActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.rl_setting:{
                Intent intent=new Intent(getActivity(),SettingActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.rl_help:{
                Intent intent=new Intent(getActivity(),AboutActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
