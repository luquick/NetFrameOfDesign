package com.luquick.download.http.impl;

import android.content.Context;

import com.luquick.download.file.FileStorageManager;
import com.luquick.download.http.interf.DownloadCallBack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：Created by Luquick on 2018/6/24.
 * 邮箱: xxxxxx@163.com
 * QQ号：930982728
 * 微信：p11225630
 * 作用：xxxxxxx
 */
public class HttpManager {

    private static final HttpManager sManager = new HttpManager();
    /**
     * 网络请求失败，
     * 临时值
     */
    public static final int NETWORK_ERROR_CODE = 1;
    public static final int CONTENT_LENGTH_ERROR_CODE = 2;
    public static final int TASK_RUNNING_ERROR_CODE = 3;

    private Context mContext;
    private OkHttpClient mClient;

    public static HttpManager getInterface() {
        return sManager;
    }

    private HttpManager() {
        mClient = new OkHttpClient();
    }

    public void init(Context context) {
        mContext = context;
    }

    /**
     * 同步请求
     *
     * @param url
     * @return
     */
    public Response syncRequest(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            return mClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param url
     * @param start
     * @param end
     * @return response
     */
    public Response syncRequestByRange(String url, long start, long end) {
        Request request = new Request.Builder()
                .addHeader("Range", "bytes=" + start + "-" + end)//支持 Range
                .url(url)
                .build();
        try {
            return mClient.newCall(request).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 异步下载文件请求
     *
     * @param url
     * @return
     */
    public void asyncRequestDownload(final String url, final DownloadCallBack cb) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful() && cb != null) {
                    cb.fail(NETWORK_ERROR_CODE, "网络请求失败");
                }

                File file = FileStorageManager.getInstance().getFileByName(url);
                byte[] buff = new byte[1024 * 500];
                int len;
                FileOutputStream fileOut = new FileOutputStream(file);
                InputStream inStream = response.body().byteStream();
                while (-1 != (len = inStream.read(buff, 0, buff.length))) {
                    fileOut.write(buff, 0, len);

                    fileOut.flush();
                }
                /**
                 * 释放资源
                 */
                inStream.close();
                fileOut.close();
                cb.success(file);
            }
        });
    }

    /**
     * 异步下载文件请求
     *
     * @param url
     * @return
     */
    public void asyncRequestOfMultiThreadDownload(final String url, final Callback callBack) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        mClient.newCall(request).enqueue(callBack);
    }
}
