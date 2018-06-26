package com.luquick.download;

/**
 * 作者：Created by Luquick on 2018/6/26.
 * 邮箱: xxxxxx@163.com
 * QQ号：930982728
 * 微信：p11225630
 * 作用：构建者模式来设计线程池的管理
 */
public class DownloadConfig {
    private int mCoreThreadSize;
    private int mMaxThreadSize;
    private int mLocalProgressThreadSize;


    public int getCoreThreadSize() {
        return mCoreThreadSize;
    }

    public int getMaxThreadSize() {
        return mMaxThreadSize;
    }

    public int getLocalProgressThreadSize() {
        return mLocalProgressThreadSize;
    }

    private DownloadConfig(Builder builder) {
        mCoreThreadSize = builder.coreThreadSize
                == 0 ? DownloadManager.MAX_THREAD
                : builder.coreThreadSize;
        mMaxThreadSize = builder.maxThreadSize
                == 0 ? DownloadManager.MAX_THREAD
                : builder.coreThreadSize;
        mLocalProgressThreadSize = builder.localProgressThreadSize
                == 0 ? DownloadManager.LOCAL_PROGRESS_SIZE
                : builder.localProgressThreadSize;

    }

    public static class Builder {
        private int coreThreadSize;
        private int maxThreadSize;
        private int localProgressThreadSize;

        public Builder setCoreThreadSize(int coreThreadSize) {
            this.coreThreadSize = coreThreadSize;
            return this;
        }

        public Builder setMaxThreadSize(int maxThreadSize) {
            this.maxThreadSize = maxThreadSize;
            return this;
        }

        public Builder setLocalProgressThreadSize(int localProgressThreadSize) {
            this.localProgressThreadSize = localProgressThreadSize;
            return this;
        }

        public DownloadConfig builder() {
            return new DownloadConfig(this);
        }
    }

}
