package com.luquick.http.lib.request;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 作者：Created by Luquick on 2018/6/23.
 * 邮箱: xxxxxx@163.com
 * QQ号：930982728
 * 微信：p11225630
 * 作用：xxxxxxx
 */
public class MultipartHttp {

    OkHttpClient client = new OkHttpClient();
    RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"),new File("C:\\Users\\Asus\\Desktop\\9552ecc61113.png"));
    MultipartBody body = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)//设置表单类型，否则分拣名称将为null
            .addFormDataPart("name","xxxxxxxxx")//设置文件名称
            .addFormDataPart("filename","hello.png", requestBody)//设置发送到服务器的文件名称为 hello.png
            .build();

    Request request = new Request.Builder()
            //通过本机IP地址同样可以
            //.url("http://192.168.1.104:8080/web/UploadServlet")
            .url("http://localhost:8080/web/UploadServlet")
            .post(body)
            .build();

    public void sendMultipartHttp() {
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
