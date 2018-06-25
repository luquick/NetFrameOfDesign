package com.luquick.download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.luquick.download.db.entity.DaoMaster;
import com.luquick.download.db.entity.DaoSession;
import com.luquick.download.db.entity.DownloadEntity;
import com.luquick.download.db.entity.DownloadEntityDao;

import java.util.List;

/**
 * 作者：Created by Luquick on 2018/6/25.
 * 邮箱: xxxxxx@163.com
 * QQ号：930982728
 * 微信：p11225630
 * 作用：xxxxxxx
 */
public class DownloadHelper {

    /**
     * 单利模式
     */
    private static DownloadHelper sDownloadHelper = new DownloadHelper();
    public static DownloadHelper getInstances() {
        return sDownloadHelper;
    }
    private DownloadHelper() {
    }

    /**
     * 数据库控制管理器
     */
    private DaoMaster mDaoMaster;

    /**
     * 数据会话对象
     */
    private DaoSession mDaoSession;

    /**
     * 下载相关的数据库实体类的对象
     */
    private DownloadEntityDao mDownloadEntityDao;


    public void init(Context context) {
        SQLiteDatabase db = new DaoMaster.DevOpenHelper(context,"download.db",null)
                .getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
        mDownloadEntityDao = mDaoSession.getDownloadEntityDao();

    }

    /**
     * 数据库查询
     * @param downloadEntity
     */
    public void insert(DownloadEntity downloadEntity) {
        mDownloadEntityDao.insert(downloadEntity);
    }

    /**
     * 根据 Url 查询相关数据
     */
    public List<DownloadEntity> getAll(String url) {
        return mDownloadEntityDao.queryBuilder()
                .where(DownloadEntityDao.Properties.Download_url.eq(url))
                .orderAsc(DownloadEntityDao.Properties.Thread_id)
                .list();
    }
}
