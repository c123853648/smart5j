package com.jinandaxue.demo1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.jinandaxue.entity.LoginBean;
import com.jinandaxue.utils.Config;
import com.jinandaxue.utils.MyApplication;
import com.jinandaxue.utils.PictureUtil;
import com.jinandaxue.view.PhotoPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class PublishMomentsActivity extends AppCompatActivity {
    PhotoPopupWindow mPhotoPopupWindow;
    private static final int REQUEST_IMAGE_GET = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SMALL_IMAGE_CUTTING = 2;
    private static final int REQUEST_CHANGE_USER_NICK_NAME = 10;
    private static final String IMAGE_FILE_NAME = "user_head_icon.jpg";

    private SimpleDraweeView iv_image;
    private EditText et_content;
    private TextView tv_ok;
    private TextView tv_back;

    private Spinner spinner2;
    private String imgPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_publish_moments);
        et_content= (EditText) findViewById(R.id.et_content);
        spinner2= (Spinner) findViewById(R.id.spinner2);
        tv_back= (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_ok= (TextView) findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content=et_content.getText().toString();
                if (TextUtils.isEmpty(content)){
                    Toast.makeText(PublishMomentsActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String newstype= (String) spinner2.getSelectedItem();
              //  Toast.makeText(PublishMomentsActivity.this,newstype,Toast.LENGTH_SHORT).show();
                PostFormBuilder builder= OkHttpUtils.post();//
                if (!TextUtils.isEmpty(imgPath)){
                    Map<String,String> params=new HashMap<String, String>();
                    params.put("content",content);
                    params.put("newstype",newstype);
                    params.put("uid", MyApplication.userBean.getId()+"");
                    Map<String,String> headers=new HashMap<String, String>();
                    builder.addFile("file", "messenger_01.png", new File(imgPath));//
                    headers.put("Content-Type","multipart/form-data");
                    builder.url(Config.URL+"moments/add")
                            .headers(headers)//
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
                                    LoginBean loginBean=gson.fromJson(response,LoginBean.class);
                                    if (loginBean.getResultcode()==200){
                                        MyApplication.userBean=loginBean.getData();
                                        Toast.makeText(PublishMomentsActivity.this,"发布成功!",Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else {
                                        Toast.makeText(PublishMomentsActivity.this,loginBean.getReason(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    final HashMap<String,String> params=new HashMap<>();
                    params.put("content",content);
                    params.put("newstype",newstype);
                    params.put("uid", MyApplication.userBean.getId()+"");
                    OkHttpUtils.post()//
                            .url(Config.URL+"moments/addNoImage")
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
                                    LoginBean loginBean=gson.fromJson(response,LoginBean.class);
                                    if (loginBean.getResultcode()==200){
                                        Toast.makeText(PublishMomentsActivity.this,"发布成功!",Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else {
                                        Toast.makeText(PublishMomentsActivity.this,loginBean.getReason(),Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }


                
            }
        });
        iv_image= (SimpleDraweeView) findViewById(R.id.iv_image);
        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureUtil.mkdirMyPetRootDirectory();
                mPhotoPopupWindow = new PhotoPopupWindow(PublishMomentsActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 文件权限申请
                        if (ContextCompat.checkSelfPermission(PublishMomentsActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            // 权限还没有授予，进行申请
                            ActivityCompat.requestPermissions(PublishMomentsActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200); // 申请的 requestCode 为 200
                        } else {
                            // 如果权限已经申请过，直接进行图片选择
                            mPhotoPopupWindow.dismiss();
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            // 判断系统中是否有处理该 Intent 的 Activity
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(intent, REQUEST_IMAGE_GET);
                            } else {
                                Toast.makeText(PublishMomentsActivity.this, "未找到图片查看器", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, new View.OnClickListener()
                {
                    @Override
                    public void onClick (View v){
                        // 拍照及文件权限申请
                        if (ContextCompat.checkSelfPermission(PublishMomentsActivity.this,
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                                || ContextCompat.checkSelfPermission(PublishMomentsActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            // 权限还没有授予，进行申请
                            ActivityCompat.requestPermissions(PublishMomentsActivity.this,
                                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 300); // 申请的 requestCode 为 300
                        } else {
                            // 权限已经申请，直接拍照
                            mPhotoPopupWindow.dismiss();
                            imageCapture();
                        }
                    }
                });
                View rootView = LayoutInflater.from(PublishMomentsActivity.this).inflate(R.layout.activity_person_info, null);
                mPhotoPopupWindow.showAtLocation(rootView,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }

    private void imageCapture() {
        Intent intent;
        Uri pictureUri;
        //getMyPetRootDirectory()得到的是Environment.getExternalStorageDirectory() + File.separator+"MyPet"
        //也就是我之前创建的存放头像的文件夹（目录）
        File pictureFile = new File(PictureUtil.getMyPetRootDirectory(), IMAGE_FILE_NAME);
        // 判断当前系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //这一句非常重要
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //""中的内容是随意的，但最好用package名.provider名的形式，清晰明了
            pictureUri = FileProvider.getUriForFile(this,
                    "com.example.mypet.fileprovider", pictureFile);
        } else {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureUri = Uri.fromFile(pictureFile);
        }
        // 去拍照,拍照的结果存到oictureUri对应的路径中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        Log.e("ceshi","before take photo"+pictureUri.toString());
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 200:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPhotoPopupWindow.dismiss();
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    // 判断系统中是否有处理该 Intent 的 Activity
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, REQUEST_IMAGE_GET);
                    } else {
                        Toast.makeText(PublishMomentsActivity.this, "未找到图片查看器", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mPhotoPopupWindow.dismiss();
                }
                break;
            case 300:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPhotoPopupWindow.dismiss();
                    imageCapture();
                } else {
                    mPhotoPopupWindow.dismiss();
                }
                break;
        }
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showHeadImage() {
        boolean isSdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);// 判断sdcard是否存在
        if (isSdCardExist) {

            String path = imgPath;// 获取图片路径

            File file = new File(path);
            if (file.exists()) {
                Bitmap bm = BitmapFactory.decodeFile(path);
                // 将图片显示到ImageView中
                iv_image.setImageBitmap(bm);
            }else{
                Log.e("ceshi","no file");
                iv_image.setImageResource(R.mipmap.person_head_default);
            }
        } else {
            Log.e("ceshi","no SD card");
            iv_image.setImageResource(R.mipmap.person_head_default);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 回调成功
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                // 切割
                case REQUEST_SMALL_IMAGE_CUTTING:
                    File cropFile=new File(PictureUtil.getMyPetRootDirectory(),"crop.jpg");
                    Uri cropUri = Uri.fromFile(cropFile);
                    setPicToView(cropUri);
                    break;

                // 相册选取
                case REQUEST_IMAGE_GET:
                    Uri uri= PictureUtil.getImageUri(this,data);
                    startPhotoZoom(uri);
                    break;

                // 拍照
                case REQUEST_IMAGE_CAPTURE:
                    File pictureFile = new File(PictureUtil.getMyPetRootDirectory(), IMAGE_FILE_NAME);
                    Uri pictureUri;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        pictureUri = FileProvider.getUriForFile(this,
                                "com.example.mypet.fileprovider", pictureFile);
                    } else {
                        pictureUri = Uri.fromFile(pictureFile);
                    }
                    startPhotoZoom(pictureUri);
                    break;
                // 获取changeinfo销毁 后 回传的数据
                case REQUEST_CHANGE_USER_NICK_NAME:
                    String returnData = data.getStringExtra("data_return");
                    // InfoPrefs.setData(Constants.UserInfo.NAME,returnData);
                    /// textView_user_nick_name.setText(InfoPrefs.getData(Constants.UserInfo.NAME));
                    break;
                default:
            }
        }else{
            // Log.e(TAG,"result = "+resultCode+",request = "+requestCode);
        }
    }

    public void setPicToView(Uri uri)  {
        if (uri != null) {
            Bitmap photo = null;
            try {
                photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
            // 创建 smallIcon 文件夹
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //String storage = Environment.getExternalStorageDirectory().getPath();
                File dirFile = new File(PictureUtil.getMyPetRootDirectory(),  "Icon");
                if (!dirFile.exists()) {
                    if (!dirFile.mkdirs()) {
                        Log.d("ceshi", "in setPicToView->文件夹创建失败");
                    } else {
                        Log.d("ceshi", "in setPicToView->文件夹创建成功");
                    }
                }
                File file = new File(dirFile, IMAGE_FILE_NAME);
                imgPath=file.getPath();
                //Log.d("result",file.getPath());
                // Log.d("result",file.getAbsolutePath());
                // 保存图片
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(file);
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 在视图中显示图片
            showHeadImage();
            //circleImageView_user_head.setImageBitmap(InfoPrefs.getData(Constants.UserInfo.GEAD_IMAGE));
        }
    }

    private void startPhotoZoom(Uri uri) {
        Log.d("ceshi","Uri = "+uri.toString());
        //保存裁剪后的图片
        File cropFile=new File(PictureUtil.getMyPetRootDirectory(),"crop.jpg");
        try{
            if(cropFile.exists()){
                cropFile.delete();
                Log.e("ceshi","delete");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        Uri cropUri;
        cropUri = Uri.fromFile(cropFile);

        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300); // 输出图片大小
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);

        Log.e("ceshi","cropUri = "+cropUri.toString());

        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, REQUEST_SMALL_IMAGE_CUTTING);
    }
}
