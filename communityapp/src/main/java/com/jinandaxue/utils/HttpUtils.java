package com.jinandaxue.utils;

import android.os.Message;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtils {
    public static String  requestPost(String baseUrl,HashMap<String, String> paramsMap) {
        try {
            //合成参数
            StringBuilder tempParams = new StringBuilder();
            boolean      isfist = true;
            int pos = 0;
            for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            /*    if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key,  URLEncoder.encode(paramsMap.get(key),"utf-8")));
                pos++;*/
              /*  if (isfist) {
                    isfist = false;
                } else {
                    tempParams.append("&");
                }*/
                tempParams.append("&").append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(),"utf-8"));
            //    String value = entry.getValue();
            }
            String params =tempParams.toString();
            // 请求的参数转换为byte数组
//            byte[] postData = params.getBytes();
            // 新建一个URL对象
            URL url = new URL(baseUrl+params);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间
            urlConn.setConnectTimeout(5 * 1000);
            //设置从主机读取数据超时
            urlConn.setReadTimeout(5 * 1000);
            // Post请求必须设置允许输出 默认false
            urlConn.setDoOutput(true);
            //设置请求允许输入 默认是true
            urlConn.setDoInput(true);
            // Post请求不能使用缓存
            urlConn.setUseCaches(false);
            // 设置为Post请求
            urlConn.setRequestMethod("POST");
            //设置本次连接是否自动处理重定向
            urlConn.setInstanceFollowRedirects(true);
            // 配置请求Content-Type
            urlConn.setRequestProperty("Content-Type", "application/json");
            // 开始连接
            urlConn.connect();
            // 发送请求参数
//            DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
//            dos.write(postData);
//            dos.flush();
//            dos.close();
            // 判断请求是否成功
            if (urlConn.getResponseCode() == 200) {
                // 获取返回的数据
                String result = streamToString(urlConn.getInputStream());
                return result;
            } else {
            }
            // 关闭连接
            urlConn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String  requestPost() {
       /* try {
            HttpClient client=new DefaultHttpClient();
            HttpPost httpPost=new HttpPost(Config.URL+"UsersServlet?method=login");
            httpPost.setEntity(new UrlEncodedFormEntity(parameters,"utf-8"));
            HttpResponse response=client.execute(httpPost);
            int code=response.getStatusLine().getStatusCode();
            if (code==200){
                InputStream inputStream=response.getEntity().getContent();
                String result= StreamTools.readStream(inputStream);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return "访问请求失败";
    }


    /**
     * 将输入流转换成字符串
     *
     * @param is 从网络获取的输入流
     * @return
     */
    public static String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
