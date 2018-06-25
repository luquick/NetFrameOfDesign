package com.luquick.http.lib.request;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：Created by Luquick on 2018/6/22.
 * 邮箱: xxxxxx@163.com
 * QQ号：930982728
 * 微信：p11225630
 * 作用：异步请求
 */
public class AsyncHttp {

    /**
     * 构建一个 request 对象
     */
    private Request request = new Request.Builder().url("https://www.baidu.com/").build();

    /**
     * 通常的请求
     */
    public void sendRequest() {
        OkHttpClient client = new OkHttpClient();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步请求
     */
    public void sendAsyncRequest() {
        System.out.println(Thread.currentThread().getId());
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    System.out.println("-----" + Thread.currentThread().getId());
                }
            }
        });
    }
}
