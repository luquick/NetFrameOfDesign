package com.luquick.download.http.interf;

import java.io.File;

/**
 * 作者：Created by Luquick on 2018/6/24.
 * 邮箱: xxxxxx@163.com
 * QQ号：930982728
 * 微信：p11225630
 * 作用：接口，用于上层调用网络请求处理接口回掉
 *
 *
 * 接口定义规则：
 */
public interface DownloadCallBack {

    /**
     * 文件下载成功的回掉
     * @param file
     */
    void success(File file);

    /**
     * 下载文件失败回掉
     * @param errorCode
     * @param errorMessage 那种类型的错误
     */
    void fail(int errorCode, String errorMessage);

    /**
     * 下载进度
     * @param progress
     */
    void progress(int progress);

}
