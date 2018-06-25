package com.luquick.http.lib;

import com.luquick.http.download.Md5Utils;
import com.luquick.http.download.RangHttp;
import com.luquick.http.download.ThreadPollTest;
import com.luquick.http.lib.request.CacheHttp;
import com.luquick.http.lib.request.MultipartHttp;
import com.luquick.http.lib.request.PostHttp;

public class MyClass {

    public static void main(String[] args) {
//        new AsyncHttp().sendAsyncRequest();
//        new HeadersHttp().sendRequest();

//        new QueryHttp().sendQueryHttp();
//        new PostHttp().sendPostHttp();

//        new MultipartHttp().sendMultipartHttp();
//        new CacheHttp().sendCacheHttp();
//        new RangHttp().rangHttpTest();
        new ThreadPollTest().Test();

    }
}
