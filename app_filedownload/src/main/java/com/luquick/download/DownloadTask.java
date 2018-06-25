package com.luquick.download;

import com.luquick.download.http.interf.DownloadCallBack;

import java.util.Objects;

/**
 * 作者：Created by Luquick on 2018/6/25.
 * 邮箱: xxxxxx@163.com
 * QQ号：930982728
 * 微信：p11225630
 * 作用：xxxxxxx
 */
public class DownloadTask {

    private String mUrl;
    private DownloadCallBack mDownloadCallBack;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public DownloadCallBack getDownloadCallBack() {
        return mDownloadCallBack;
    }

    public void setDownloadCallBack(DownloadCallBack mDownloadCallBack) {
        this.mDownloadCallBack = mDownloadCallBack;
    }

    public DownloadTask(String mUrl, DownloadCallBack mDownloadCallBack) {
        this.mUrl = mUrl;
        this.mDownloadCallBack = mDownloadCallBack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DownloadTask that = (DownloadTask) o;
        return Objects.equals(mUrl, that.mUrl) &&
                Objects.equals(mDownloadCallBack, that.mDownloadCallBack);
    }

    @Override
    public int hashCode() {

        return Objects.hash(mUrl, mDownloadCallBack);
    }






}
