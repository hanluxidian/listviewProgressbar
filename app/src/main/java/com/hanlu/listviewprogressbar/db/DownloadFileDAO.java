package com.hanlu.listviewprogressbar.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.hanlu.listviewprogressbar.bean.AppContent;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class: DownloadFileDAO
 * @Description: ÿ���ļ�����״̬��¼�����ݿ������
 * @author: lling(www.cnblogs.com/liuling)
 * @Date: 2015/10/13
 */
public class DownloadFileDAO {
    private static final String TAG = "DownloadFileDAO";
    private static DownloadFileDAO dao=null;
    private Context context;
    private DownloadFileDAO(Context context) {
        this.context=context;
    }

    synchronized public static DownloadFileDAO getInstance(Context context){
        if(dao==null){
            dao=new DownloadFileDAO(context);
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
     * @param appContent
     */
    public void insertDownloadFile(AppContent appContent) {
        if(appContent == null) {
            return;
        }
        //��������Ѿ����ڣ�ֱ���޸�
        if(getAppContentByUrl(appContent.getUrl()) != null) {
            updateDownloadFile(appContent);
            return;
        }
        SQLiteDatabase database = getConnection();
        try {
            String sql = "insert into download_file(app_name, url, download_percent, status) values (?,?,?,?)";
            Object[] bindArgs = { appContent.getmName(), appContent.getUrl(), appContent.getDownloadPercent()
                    , appContent.getStatus().getValue()};
            database.execSQL(sql, bindArgs);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (null != database) {
                database.close();
            }
        }
    }

    /**
     * ����url��ȡ�����ļ���Ϣ
     * @param url
     * @return
     */
    public AppContent getAppContentByUrl(String url) {
        if(TextUtils.isEmpty(url)) {
            return null;
        }
        SQLiteDatabase database = getConnection();
        AppContent appContent = null;
        Cursor cursor = null;
        try {
            String sql = "select * from download_file where url=?";
            cursor = database.rawQuery(sql, new String[] { url });
            if (cursor.moveToNext()) {
                appContent = new AppContent(cursor.getString(1), cursor.getString(2));
                appContent.setDownloadPercent(cursor.getInt(3));
                appContent.setStatus(AppContent.Status.getByValue(cursor.getInt(4)));
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
        return appContent;
    }

    /**
     * ����������Ϣ
     * @param appContent
     */
    public void updateDownloadFile(AppContent appContent) {
        if(appContent == null) {
            return;
        }
        SQLiteDatabase database = getConnection();
        try {
            Log.e(TAG, "update download_file,app name:" + appContent.getmName() + ",url:" + appContent.getUrl()
                    + ",percent" + appContent.getDownloadPercent() + ",status:" + appContent.getStatus().getValue());
            String sql = "update download_file set app_name=?, url=?, download_percent=?, status=? where url=?";
            Object[] bindArgs = {appContent.getmName(), appContent.getUrl(), appContent.getDownloadPercent()
                    , appContent.getStatus().getValue(), appContent.getUrl()};
            database.execSQL(sql, bindArgs);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (null != database) {
                database.close();
            }
        }
    }

    /**
     * ��ȡ���������ļ���¼
     * @return
     */
    public List<AppContent> getAll() {
        SQLiteDatabase database = getConnection();
        List<AppContent> list = new ArrayList<AppContent>();
        Cursor cursor = null;
        try {
            String sql = "select * from download_file";
            cursor = database.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                AppContent appContent = new AppContent(cursor.getString(1), cursor.getString(2));
                appContent.setDownloadPercent(cursor.getInt(3));
                appContent.setStatus(AppContent.Status.getByValue(cursor.getInt(4)));
                list.add(appContent);
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
        return list;
    }

    /**
     * ����urlɾ����¼
     * @param url
     */
    public void delByUrl(String url) {
        if(TextUtils.isEmpty(url)) {
            return;
        }
        SQLiteDatabase database = getConnection();
        try {
            String sql = "delete from download_file where url=?";
            Object[] bindArgs = { url };
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
