package com.luquick.download.file;

import android.content.Context;
import android.os.Environment;

import com.luquick.download.utils.Md5Utils;

import java.io.File;
import java.io.IOException;

/**
 * 作者：Created by Luquick on 2018/6/24.
 * 邮箱: xxxxxx@163.com
 * QQ号：930982728
 * 微信：p11225630
 * 作用：管理所有文件存储的内容
 */
public class FileStorageManager {
    private Context mContext;
    private static final FileStorageManager fileManager = new FileStorageManager();

    /**
     * 单例模式
     *
     * @return
     */
    public static FileStorageManager getInstance() {
        return fileManager;
    }

    /**
     * 拒接实例化
     */
    private FileStorageManager() {
    }

    public void init(Context context) {
        this.mContext = context;
    }

    public File getFileByName(String url) {
        File parent;
        //判断是否有挂在SD卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            parent = mContext.getExternalCacheDir();
        } else {
            parent = mContext.getCacheDir();
        }
        String fileName = Md5Utils.generateCode(url);//Md5加密
        File file = new File(parent, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
