package com.luquick.download.db.entity;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "DOWNLOAD_ENTITY".
 */
public class DownloadEntityDao extends AbstractDao<DownloadEntity, Long> {

    public static final String TABLENAME = "DOWNLOAD_ENTITY";

    /**
     * Properties of entity DownloadEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Start_position = new Property(0, Long.class, "start_position", false, "START_POSITION");
        public final static Property End_position = new Property(1, Long.class, "end_position", false, "END_POSITION");
        public final static Property Progress_position = new Property(2, Long.class, "progress_position", false, "PROGRESS_POSITION");
        public final static Property Download_url = new Property(3, String.class, "download_url", false, "DOWNLOAD_URL");
        public final static Property Thread_id = new Property(4, Integer.class, "thread_id", false, "THREAD_ID");
        public final static Property Id = new Property(5, Long.class, "id", true, "_id");
    }


    public DownloadEntityDao(DaoConfig config) {
        super(config);
    }

    public DownloadEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"DOWNLOAD_ENTITY\" (" + //
                "\"START_POSITION\" INTEGER," + // 0: start_position
                "\"END_POSITION\" INTEGER," + // 1: end_position
                "\"PROGRESS_POSITION\" INTEGER," + // 2: progress_position
                "\"DOWNLOAD_URL\" TEXT," + // 3: download_url
                "\"THREAD_ID\" INTEGER," + // 4: thread_id
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT );"); // 5: id
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DOWNLOAD_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DownloadEntity entity) {
        stmt.clearBindings();

        Long start_position = entity.getStart_position();
        if (start_position != null) {
            stmt.bindLong(1, start_position);
        }

        Long end_position = entity.getEnd_position();
        if (end_position != null) {
            stmt.bindLong(2, end_position);
        }

        Long progress_position = entity.getProgress_position();
        if (progress_position != null) {
            stmt.bindLong(3, progress_position);
        }

        String download_url = entity.getDownload_url();
        if (download_url != null) {
            stmt.bindString(4, download_url);
        }

        Integer thread_id = entity.getThread_id();
        if (thread_id != null) {
            stmt.bindLong(5, thread_id);
        }

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(6, id);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DownloadEntity entity) {
        stmt.clearBindings();

        Long start_position = entity.getStart_position();
        if (start_position != null) {
            stmt.bindLong(1, start_position);
        }

        Long end_position = entity.getEnd_position();
        if (end_position != null) {
            stmt.bindLong(2, end_position);
        }

        Long progress_position = entity.getProgress_position();
        if (progress_position != null) {
            stmt.bindLong(3, progress_position);
        }

        String download_url = entity.getDownload_url();
        if (download_url != null) {
            stmt.bindString(4, download_url);
        }

        Integer thread_id = entity.getThread_id();
        if (thread_id != null) {
            stmt.bindLong(5, thread_id);
        }

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(6, id);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5);
    }

    @Override
    public DownloadEntity readEntity(Cursor cursor, int offset) {
        DownloadEntity entity = new DownloadEntity( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // start_position
                cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // end_position
                cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // progress_position
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // download_url
                cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // thread_id
                cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5) // id
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, DownloadEntity entity, int offset) {
        entity.setStart_position(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setEnd_position(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setProgress_position(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setDownload_url(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setThread_id(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setId(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
    }

    @Override
    protected final Long updateKeyAfterInsert(DownloadEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    @Override
    public Long getKey(DownloadEntity entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DownloadEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }

}
