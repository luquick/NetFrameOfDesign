package com.luquick.http.lib.request;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：Created by Luquick on 2018/6/23.
 * 邮箱: xxxxxx@163.com
 * QQ号：930982728
 * 微信：p11225630
 * 作用：xxxxxxx
 */
public class PostHttp {

    OkHttpClient client = new OkHttpClient();
    FormBody formBody = new FormBody.Builder()
            .add("username","zhang")
            .add("userage","22")
            .build();
    Request request = new Request.Builder()
            .url("http://localhost:8080/web/HelloTest")
            .post(formBody)
            .build();


    public void sendPostHttp() {
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
