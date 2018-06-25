package com.luquick.http.download;

import java.awt.font.NumericShaper;
import java.io.IOException;

import okhttp3.Headers;
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
public class RangHttp {
    OkHttpClient client = new OkHttpClient.Builder().build();
    Request request = new Request.Builder()
            .url("https://img2.mukewang.com/58c64f960001794b02500250-100-100.jpg")
            //content-length:2726
            .addHeader("Range","bytes=0-2") //结果会出现 content-Length : 3  Content-Range : bytes 0-2/2726
            .build();

    public void rangHttpTest() {

        try {
            Response response = client.newCall(request).execute();

            System.out.println("content-length:"+response.body().contentLength());

            if (response.isSuccessful()) {
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    System.out.println(headers.name(i) + " : " + headers.value(i));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
