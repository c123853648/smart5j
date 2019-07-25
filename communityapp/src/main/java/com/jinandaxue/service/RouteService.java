package com.jinandaxue.service;

/**
 * Created by ceshi on 17/2/4.
 */

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.jinandaxue.demo1.LoginActivity;
import com.jinandaxue.demo1.MainActivity;
import com.jinandaxue.entity.LoginBean;
import com.jinandaxue.entity.WeatherBean;
import com.jinandaxue.utils.Config;
import com.jinandaxue.utils.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;


public class RouteService extends Service  {

    private double currentLatitude, currentLongitude;

    private LocationClient mlocationClient = null;
    private MylocationListener mlistener;
//    private BitmapDescriptor mIconLocation;
    private String rt_time, rt_distance, rt_price;
    //定位图层显示方式
//    private MyLocationConfiguration.LocationMode locationMode;
    //AllInterface.IUpdateLocation iUpdateLocation;
    public  int totalDistance = 0;
    public  float totalPrice = 0;
    public  long beginTime = 0, totalTime = 0;
    Notification notification;
    RemoteViews contentView;

    private boolean flag;
    public void onCreate() {
        super.onCreate();
        beginTime = System.currentTimeMillis();
//        RouteDBHelper dbHelper = new RouteDBHelper(this);
//        // 只有调用了DatabaseHelper的getWritableDatabase()方法或者getReadableDatabase()方法之后，才会创建或打开一个连接
//        SQLiteDatabase sqliteDatabase = dbHelper.getReadableDatabase();
        totalTime = 0;
        totalDistance = 0;
        totalPrice = 0;

    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ceshi", "RouteService--------onStartCommand---------------");
        initLocation();//初始化LocationgClient
      //  initNotification();
      //  Utils.acquireWakeLock(this);
        // 开启轨迹记录线程
        return super.onStartCommand(intent, flags, startId);
    }

    private void initNotification() {
       // int icon = R.mipmap.bike_icon2;
       // contentView = new RemoteViews(getPackageName(), R.layout.notification_layout);
      //  notification = new NotificationCompat.Builder(this).setContent(contentView).setSmallIcon(icon).build();
      //  Intent notificationIntent = new Intent(this, MainActivity.class);
      //  notificationIntent.putExtra("flag", "notification");
      //  notification.contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
    }

    private void initLocation() {
      /*  mIconLocation = BitmapDescriptorFactory
                .fromResource(R.mipmap.location_marker);*/
//        locationMode = MyLocationConfiguration.LocationMode.NORMAL;
        mlistener = new MylocationListener();
        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        mlocationClient = new LocationClient(this);

//        initMarkerClickEvent();
        //注册监听器
        mlocationClient.registerLocationListener(mlistener);
        //配置定位SDK各配置参数，比如定位模式、定位时间间隔、坐标系类型等
        LocationClientOption mOption = new LocationClientOption();
        //设置坐标类型
        mOption.setCoorType("bd09ll");
        //设置是否需要地址信息，默认为无地址
        mOption.setIsNeedAddress(true);

        mOption.setIsNeedLocationDescribe(true);
        //设置是否打开gps进行定位
        //mOption.setOpenGps(true);
        //设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效
        mOption.setScanSpan(1000*10);
        //设置 LocationClientOptionƒ20
        mlocationClient.setLocOption(mOption);

        //初始化图标,BitmapDescriptorFactory是bitmap 描述信息工厂类.
        //mIconLocation = BitmapDescriptorFactory
              //  .fromResource(R.mipmap.location_marker);

//        mSearch = RoutePlanSearch.newInstance();
//        mSearch.setOnGetRoutePlanResultListener(this);
//        //开启定位
//        mBaiduMap.setMyLocationEnabled(true);
        if (!mlocationClient.isStarted()) {
            mlocationClient.start();
        }
    }

    private void startNotifi(String time, String distance, String price) {
        startForeground(1, notification);
      //  contentView.setTextViewText(R.id.bike_time, time);
      //  contentView.setTextViewText(R.id.bike_distance, distance);
       // contentView.setTextViewText(R.id.bike_price, price);
        rt_time=time;
        rt_distance=distance;
        rt_price=price;
    }


    public IBinder onBind(Intent intent) {
        Log.d("ceshi", "onBind-------------");
        return null;
    }

    public boolean onUnBind(Intent intent) {
        Log.d("ceshi", "onBind-------------");
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mlocationClient.stop();

     /*   Gson gson = new Gson();
        String routeListStr = gson.toJson(routPointList);
        List<Map<String, Object>> routeLists=new ArrayList<>();
        for (RoutePoint routePoint:routPointList) {
            Map<String,Object> map=new HashMap<>();
            map.put("latitude",routePoint.getLatitude());
            map.put("lontitude",routePoint.getLontitude());
            map.put("loctime",routePoint.getLoctime());
            map.put("loctype",routePoint.getLoctype());
            map.put("addr",routePoint.getAddr());
            map.put("message",routePoint.getMessage());
            routeLists.add(map);
        }
        String formdata=MyUtils.getInstance(this).getUploadLocation(routeLists);
        OkHttpUtil.sendPost(Constant.UPLOAD_LOCATION,formdata,this,Constant.UPLOAD_LOCATION_CODE);*/
       


    }



    //所有的定位信息都通过接口回调来实现
    public class MylocationListener implements BDLocationListener {
        //定位请求回调接口
        private boolean isFirstIn = true;

        //定位请求回调函数,这里面会得到定位信息
        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.e("ceshi",(null == location)+"");
            if (null == location) return;
            //"4.9E-324"表示目前所处的环境（室内或者是网络状况不佳）造成无法获取到经纬度 //过滤百度定位失败
            if ("4.9E-324".equals(String.valueOf(location.getLatitude())) || "4.9E-324".equals(String.valueOf(location.getLongitude()))) {
                Log.e("ceshi","定位失败");
                return;
            }else {
                double routeLat = location.getLatitude();
                double routeLng = location.getLongitude();
                if (!flag){
                    Log.e("ceshi","service经纬度:"+routeLat+"  "+routeLng);
                    HashMap<String,String> params=new HashMap<>();
                    params.put("latitude",routeLat+"");
                    params.put("longitude",routeLng+"");
                    params.put("uid",MyApplication.userBean.getId()+"");
                    params.put("behavename","登录");
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
                }
                flag=true;
             /*   L.e("service经纬度:"+routeLat+"  "+routeLng);
                SPUtils.putString(getApplication(),"lat",routeLat+"");
                SPUtils.putString(getApplication(),"lon",routeLng+"");
                SPUtils.putString(getApplication(),"address",location.getAddrStr());*/

            }


        }
    }

    public static class NetWorkReceiver extends BroadcastReceiver

    {
        public NetWorkReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            NetworkInfo.State wifiState = null;
            NetworkInfo.State mobileState = null;
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            if (wifiState != null && mobileState != null
                    && NetworkInfo.State.CONNECTED != wifiState
                    && NetworkInfo.State.CONNECTED == mobileState) {
//                Toast.makeText(context, context.getString(R.string.net_mobile), Toast.LENGTH_SHORT).show();
                // 手机网络连接成功
            } else if (wifiState != null && mobileState != null
                    && NetworkInfo.State.CONNECTED != wifiState
                    && NetworkInfo.State.CONNECTED != mobileState) {
//                Toast.makeText(context, context.getString(R.string.net_none), Toast.LENGTH_SHORT).show();

                // 手机没有任何的网络
            } else if (wifiState != null && NetworkInfo.State.CONNECTED == wifiState) {
                // 无线网络连接成功
//                Toast.makeText(context, context.getString(R.string.net_wifi), Toast.LENGTH_SHORT).show();

            }
        }
    }

    public void insertData(String routeListStr) {
        ContentValues values = new ContentValues();
       // // 向该对象中插入键值对，其中键是列名，值是希望插入到这一列的值，值必须和数据当中的数据类型一致
       // values.put("cycle_date", Utils.getDateFromMillisecond(beginTime));
        values.put("cycle_time", totalTime);
        values.put("cycle_distance", totalDistance);
        values.put("cycle_price", totalPrice);
        values.put("cycle_points", routeListStr);
        // 创建DatabaseHelper对象
     //   RouteDBHelper dbHelper = new RouteDBHelper(this);
        // 得到一个可写的SQLiteDatabase对象
      //  SQLiteDatabase sqliteDatabase = dbHelper.getWritableDatabase();
        // 调用insert方法，就可以将数据插入到数据库当中
        // 第一个参数:表名称
        // 第二个参数：SQl不允许一个空列，如果ContentValues是空的，那么这一列被明确的指明为NULL值
        // 第三个参数：ContentValues对象
      //  sqliteDatabase.insert("cycle_route", null, values);
      //  sqliteDatabase.close();
    }
}
