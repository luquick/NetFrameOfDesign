package com.luquick.http.lib.request;

import java.io.IOException;

import okhttp3.HttpUrl;
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
public class QueryHttp {

    OkHttpClient client = new OkHttpClient();
    HttpUrl  httpUrl = HttpUrl.parse("https://free-api.heweather.com/v5/weather")
            .newBuilder()
            .addQueryParameter("city","深圳")
            .addQueryParameter("key","c537a35238df453682431cdca74d00b7")
            .build();

    public void sendQueryHttp() {
        System.out.println(httpUrl.toString());
        Request request = new Request.Builder().url(httpUrl).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println(response.body().toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

}
