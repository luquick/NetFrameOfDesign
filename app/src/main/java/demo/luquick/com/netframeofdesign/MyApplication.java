package demo.luquick.com.netframeofdesign;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.luquick.download.db.DownloadHelper;
import com.luquick.download.file.FileStorageManager;
import com.luquick.download.http.impl.HttpManager;

/**
 * 作者：Created by Luquick on 2018/6/24.
 * 邮箱: xxxxxx@163.com
 * QQ号：930982728
 * 微信：p11225630
 * 作用：xxxxxxx
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化各个模块
        Stetho.initializeWithDefaults(this);//facebook开源工具 用于Android项目调试
        FileStorageManager.getInstance().init(this);
        HttpManager.getInterface().init(this);
        DownloadHelper.getInstances().init(this);
    }
}
