package com.luquick.download;

import com.luquick.download.db.DownloadHelper;
import com.luquick.download.db.entity.DownloadEntity;
import com.luquick.download.file.FileStorageManager;
import com.luquick.download.http.impl.HttpManager;
import com.luquick.download.http.interf.DownloadCallBack;
import com.luquick.download.utils.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Response;

/**
 * 作者：Created by Luquick on 2018/6/24.
 * 邮箱: xxxxxx@163.com
 * QQ号：930982728
 * 微信：p11225630
 * 作用：xxxxxxx
 */
public class DownloadRunnable implements Runnable {

    private long mStart;

    private long mEnd;

    private String mUrl;

    private DownloadCallBack mCallBack;

    private DownloadEntity mDEntity;

    public DownloadRunnable(long mStart, long mEnd, String mUrl, DownloadCallBack mCallBack) {
        this.mStart = mStart;
        this.mEnd = mEnd;
        this.mUrl = mUrl;
        this.mCallBack = mCallBack;
    }

    public DownloadRunnable(long mStart, long mEnd, String mUrl, DownloadCallBack mCallBack, DownloadEntity mDEntity) {
        this.mStart = mStart;
        this.mEnd = mEnd;
        this.mUrl = mUrl;
        this.mCallBack = mCallBack;
        this.mDEntity = mDEntity;
    }

    @Override
    public void run() {
        Response response = HttpManager.getInterface().syncRequestByRange(mUrl,mStart,mEnd);
        if (response == null && mCallBack != null) {
            mCallBack.fail(HttpManager.NETWORK_ERROR_CODE,"网络出现问题");
            return;
        }

        File file = FileStorageManager.getInstance().getFileByName(mUrl);
        //进度，刚开始就是 0 ;
        long progress = 0;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rwd");//rwd文件可读，可写，可同步添加到存储设备。
            randomAccessFile.seek(mStart);//跳过已经下载的字节
            //文件写入
            byte[] buff = new byte[1024 * 500];
            int len;
            InputStream inStream = response.body().byteStream();
            while (-1 != (len = inStream.read(buff, 0, buff.length))) {
                randomAccessFile.write(buff, 0, len);
                //每个线程的进度
                progress += len;
                //将数据同步到每个线程 progress.
                mDEntity.setProgress_position(progress);
                Logger.debug("Hello","progress -----> " + progress);
            }
            /**
             * 释放资源
             */
            mCallBack.success(file);
            //TODO 任务成功清除本次任务 task
            //DownloadManager...finish();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //不论成功或者失败，将数据同步到数据库。减少数据库的相关操作
            DownloadHelper.getInstances().insert(mDEntity);
        }
    }
}
