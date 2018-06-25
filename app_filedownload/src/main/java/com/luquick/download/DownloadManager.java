package com.luquick.download;

import android.support.annotation.NonNull;

import com.luquick.download.db.DownloadHelper;
import com.luquick.download.db.entity.DownloadEntity;
import com.luquick.download.http.impl.HttpManager;
import com.luquick.download.http.interf.DownloadCallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 作者：Created by Luquick on 2018/6/24.
 * 邮箱: xxxxxx@163.com
 * QQ号：930982728
 * 微信：p11225630
 * 作用：多线程下载管理器； 线程池，任务组
 */
public class DownloadManager {

    private final static int MAX_THREAD = 2;

    private static final DownloadManager sManager = new DownloadManager();

//    private static final ExecutorService sLocal_progress_Poll = new Executors.newFixedThreadPool(1);

    //当前任务唯一
    //用于防止同一个任务的多此提交
    private HashSet<DownloadTask> mHashSet = new HashSet<>();

    /**
     *
     */
    private List<DownloadEntity> mCache;

    private DownloadManager() {
    }

    public static DownloadManager getInstance() {
        return sManager;
    }

    //线程池
    /**
     * 参数说明
     * 核心线程数
     * 最大线程数
     * 存活时间
     * 时间单位类型
     * //任务队列---------------------
     * SynchronousQueue ?? --- LinkedBlockingDeque  ??
     * //-----------------------------
     * ThreadFactory ??
     */
    private static final ThreadPoolExecutor sThreadPool = new ThreadPoolExecutor(
            MAX_THREAD,
            MAX_THREAD,
            60,
            TimeUnit.MILLISECONDS,
            new SynchronousQueue<Runnable>(),
            new ThreadFactory() {
                private AtomicInteger mInteger = new AtomicInteger();

                @Override
                public Thread newThread(@NonNull Runnable r) {
                    Thread thread = new Thread(r, "download thread # "
                            + mInteger.getAndIncrement());
                    return thread;
                }
            });


    /**
     * 清除任务——操作
     *
     * @param task
     */
    private void finish(DownloadTask task) {
        mHashSet.remove(task);
    }

    /**
     * 异步，多线程下载
     *
     * @param url
     * @param callBack
     */
    public void download(final String url, final DownloadCallBack callBack) {

        //添加队列
        //用于防止同一个任务的多此提交
        final DownloadTask downloadTask = new DownloadTask(url, callBack);
        if (mHashSet.contains(downloadTask)) {
            callBack.fail(HttpManager.TASK_RUNNING_ERROR_CODE, "任务已经执行了。。");
            return;
        }
        mHashSet.add(downloadTask);
        mCache = DownloadHelper.getInstances().getAll(url);
        //没有数据的情况下，下载。
        if (mCache == null || mCache.size() == 0) {
            HttpManager.getInterface().asyncRequestOfMultiThreadDownload(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    finish(downloadTask);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful() && callBack != null) {
                        callBack.fail(HttpManager.NETWORK_ERROR_CODE, "网络出现错误");
                        return;
                    }

                    long length = response.body().contentLength();
                    if (length == -1) {
                        callBack.fail(HttpManager.CONTENT_LENGTH_ERROR_CODE, "content length  -1");
                        return;
                    }
                    processDownload(url, length, callBack, mCache);
                    //TODO 需要在下载完成之后操作  CallBack之后
                    finish(downloadTask);
                }
            });
        } else {
            //快捷键 todo + enter
            // TODO: 2018/6/25 处理已经下载过的数据
            //快捷键 XXX.for + enter
            for (DownloadEntity entity : mCache) {

                //是一个 BUG,检测本地文件大小，需要拿到文件计算长度，然后根据长度后面部分下载。
                //startDownloadSize ---- 当前下载的数据长度.getStart_position() + entity.getProgress_position();
                //endDownloadSize---数据不变，还是等于索要下载数据的总长度
                long startSize = entity.getStart_position() + entity.getProgress_position();
                long endSize = entity.getEnd_position();
                sThreadPool.execute(new DownloadRunnable(
                        startSize,
                        endSize,
                        url,
                        callBack, entity));
            }

        }
    }

    /**
     * 多线程下载，分配线程平均下载量。
     *
     * @param url              ---
     * @param length           ---
     * @param callBack         ---
     * @param downloadEntities 可能为空，需要处理
     */
    private void processDownload(String url, long length, DownloadCallBack callBack, List<DownloadEntity> downloadEntities) {
        //计算每个线程下载长度
        //100 2 50 0-49 50-99
        long threadDownloadSize = length / MAX_THREAD;
        if (downloadEntities == null || downloadEntities.size() == 0) {
            mCache = new ArrayList<>();
        }
        for (int i = 0; i < MAX_THREAD; i++) {
            DownloadEntity dEntity = new DownloadEntity();
            long startDownloadSize = i * threadDownloadSize;
            long endDownloadSize = (i + 1) * threadDownloadSize - 1;
            //特殊情况 最后一个线程（最后一个线程可能会下载多一些）
            if (i == MAX_THREAD - 1) {
                //说明是最后一个线程
                endDownloadSize = length - 1;
            }

            //数据缓存
            dEntity.setDownload_url(url);
            dEntity.setStart_position(startDownloadSize);
            dEntity.setEnd_position(endDownloadSize);
            dEntity.setThread_id(i + 1);

            sThreadPool.execute(new DownloadRunnable(
                    startDownloadSize,
                    endDownloadSize,
                    url,
                    callBack, dEntity));
        }
    }


}
