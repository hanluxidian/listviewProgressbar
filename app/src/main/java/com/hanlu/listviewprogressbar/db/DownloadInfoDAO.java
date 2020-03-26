package com.hanlu.listviewprogressbar.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.hanlu.listviewprogressbar.bean.DownloadInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class: DownloadInfoDAO
 * @Description: ÿ�������߳�������Ϣ��¼�����ݿ������
 * @author: lling(www.cnblogs.com/liuling)
 * @Date: 2015/10/13
 */
public class DownloadInfoDAO {
    private static final String TAG = "DownloadInfoDAO";
    private static DownloadInfoDAO dao=null;
    private Context context;
    private  DownloadInfoDAO(Context context) {
        this.context=context;
    }

    synchronized public static DownloadInfoDAO getInstance(Context context){
        if(dao==null){
            dao=new DownloadInfoDAO(context);
        }
        return dao;
    }

    /**
     * ��ȡ���ݿ�����
     * @return
     */
    public SQLiteDatabase getConnection() {
        SQLiteDatabase sqliteDatabase = null;
        try {
            sqliteDatabase= new DBHelper(context).getReadableDatabase();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return sqliteDatabase;
    }

    /**
     * ��������
     * @param downloadInfo
     */
    public void insertDownloadInfo(DownloadInfo downloadInfo) {
        if(downloadInfo == null) {
            return;
        }
        //��������Ѿ����ڣ�ֱ���޸�
        if(getDownloadInfoByTaskIdAndUrl(downloadInfo.getTaskId(), downloadInfo.getUrl()) != null) {
            updateDownloadInfo(downloadInfo);
            return;
        }
        SQLiteDatabase database = getConnection();
        try {
            String sql = "insert into download_info(task_id, download_length, url, is_success) values (?,?,?,?)";
            Object[] bindArgs = { downloadInfo.getTaskId(), downloadInfo.getDownloadLength(),
                    downloadInfo.getUrl(), downloadInfo.isDownloadSuccess()};
            database.execSQL(sql, bindArgs);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (null != database) {
                database.close();
            }
        }
    }

    public List<DownloadInfo> getDownloadInfosByUrl(String url) {
        if(TextUtils.isEmpty(url)) {
            return null;
        }
        SQLiteDatabase database = getConnection();
        List<DownloadInfo> list = new ArrayList<DownloadInfo>();
        Cursor cursor = null;
        try {
            String sql = "select * from download_info where url=?";
            cursor = database.rawQuery(sql, new String[] { url });
            while (cursor.moveToNext()) {
                DownloadInfo info = new DownloadInfo();
                info.setTaskId(cursor.getInt(1));
                info.setDownloadLength(cursor.getLong(2));
                info.setDownloadSuccess(cursor.getInt(4));
                info.setUrl(cursor.getString(3));
                list.add(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != database) {
                database.close();
            }
            if (null != cursor) {
                cursor.close();
            }
        }
        return list;
    }

    /**
     * ����taskid��url��ȡ������Ϣ
     * @param taskId
     * @param url
     * @return
     */
    public DownloadInfo getDownloadInfoByTaskIdAndUrl(int taskId, String url) {
        if(TextUtils.isEmpty(url)) {
            return null;
        }
        SQLiteDatabase database = getConnection();
        DownloadInfo info = null;
        Cursor cursor = null;
        try {
            String sql = "select * from download_info where url=? and task_id=?";
            cursor = database.rawQuery(sql, new String[] { url, String.valueOf(taskId) });
            if (cursor.moveToNext()) {
                info = new DownloadInfo();
                info.setTaskId(cursor.getInt(1));
                info.setDownloadLength(cursor.getLong(2));
                info.setDownloadSuccess(cursor.getInt(4));
                info.setUrl(cursor.getString(3));
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (null != database) {
                database.close();
            }
            if (null != cursor) {
                cursor.close();
            }
        }
        return info;
    }

    /**
     * ����������Ϣ
     * @param downloadInfo
     */
    public void updateDownloadInfo(DownloadInfo downloadInfo) {
        if(downloadInfo == null) {
            return;
        }
        SQLiteDatabase database = getConnection();
        try {
            String sql = "update download_info set download_length=?, is_success=? where task_id=? and url=?";
            Object[] bindArgs = { downloadInfo.getDownloadLength(), downloadInfo.isDownloadSuccess(),
                    downloadInfo.getTaskId(), downloadInfo.getUrl() };
            database.execSQL(sql, bindArgs);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (null != database) {
                database.close();
            }
        }
    }

}
