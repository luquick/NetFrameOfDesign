package com.luquick.http.lib.request;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：Created by Luquick on 2018/6/22.
 * 邮箱: xxxxxx@163.com
 * QQ号：930982728
 * 微信：p11225630
 * 作用：xxxxxxx
 */
public class HeadersHttp {

    private Request request = new Request.Builder()
            .url("https://www.baidu.com/")
            .addHeader("User_Agent","from ate http")
            .addHeader("Accept","text/plain, text/html")
            .build();

    /**
     * 通常的请求
     */
    public void sendRequest() {
        OkHttpClient client = new OkHttpClient();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println(response.headers().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
