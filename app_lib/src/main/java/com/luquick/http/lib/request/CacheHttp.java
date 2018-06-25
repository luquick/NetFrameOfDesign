package com.luquick.http.lib.request;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：Created by Luquick on 2018/6/23.
 * 邮箱: xxxxxx@163.com
 * QQ号：930982728
 * 微信：p11225630
 * 作用： 缓存请求
 */
public class CacheHttp {
    //10MB
    int maxCacheSize = 10 * 1024 * 1024;
    //桌面文件夹，最大缓存尺寸10
    Cache cache = new Cache(new File("C:\\Users\\Asus\\Desktop\\WebTestDir"), maxCacheSize);

    OkHttpClient client = new OkHttpClient
            .Builder()
            .cache(cache)
            .build();

    Request request = new Request.Builder()
            /*.cacheControl(new CacheControl
                    .Builder()
                    .noCache()
                    //.maxStale(365, TimeUnit.DAYS)//强制指定缓存持续有效365天。
                    .build())//指定不支持缓存，*/
            .url("http://www.qq.com")//QQ网站支持缓存，60秒
            //.url("http://www.imooc.com")//imooc网站不支持缓存，所以运行效果都是从网络重新获取数据。
            .build();


    /**
     * 当前缓存文件中没有索要请求的内容，从网络请求，
     * 如果缓存文件中没有过期并且存在，则加载缓存数据，不需重新访问服务器数据。
     */
    public void sendCacheHttp() {
        {
            try {
                Response response = client
                        .newCall(request)
                        .execute();
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    System.out.println("networkResponse = " + response.networkResponse());
                    System.out.println("cacheResponse = " + response.cacheResponse());
                    System.out.println("*********************************");
                    Response response1 = client.newCall(request).execute();
                    String str1 = response1.body().string();
                    System.out.println("networkResponse = " + response1.networkResponse());
                    System.out.println("cacheResponse = " + response1.cacheResponse());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
