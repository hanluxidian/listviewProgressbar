package com.hanlu.listviewprogressbar.download;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.hanlu.listviewprogressbar.activitys.Constants;
import com.hanlu.listviewprogressbar.bean.AppContent;
import com.hanlu.listviewprogressbar.bean.DownloadInfo;
import com.hanlu.listviewprogressbar.db.DownloadFileDAO;
import com.hanlu.listviewprogressbar.db.DownloadInfoDAO;
import com.hanlu.listviewprogressbar.utils.DownloadUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Downloador {
    public static final String TAG = "Downloador";
    private static final int THREAD_POOL_SIZE = 9;
    private static final int THREAD_NUM = 3;
    private static final int GET_LENGTH_SUCCESS = 1;
    public static final Executor THREAD_POOL_EXECUTOR = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    private List<DownloadTask> tasks;
    private InnerHandler handler = new InnerHandler();

    private AppContent appContent;
    private long downloadLength;
    private long fileLength;
    private Context context;
    private String downloadPath;

    public Downloador(Context context, AppContent appContent) {
        this.context = context;
        this.appContent = appContent;
        this.downloadPath = DownloadUtils.getDownloadPath();
    }

    public void download() {
        if(TextUtils.isEmpty(downloadPath)) {
            Toast.makeText(context, "11111", Toast.LENGTH_SHORT).show();
            return;
        }
        if(appContent == null) {
            throw new IllegalArgumentException("download content can not be null");
        }
        new Thread() {
            @Override
            public void run() {
                HttpURLConnection client = null;
                try {
                    client = (HttpURLConnection) new URL(appContent.getUrl()).openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                HttpGet request = new HttpGet(appContent.getUrl());
//                HttpResponse response = null;
                try {
//                    response = client.execute(request);
                    client.setRequestMethod("GET");
                    client.setRequestProperty("Content-Type", "application/octet-stream");
                    client.setDoOutput(true);
                    client.setDoInput(true);
                    client.setRequestProperty("Connection", "Keep-Alive");
                    fileLength = client.getContentLength();
                    client.disconnect();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                } finally {
//                    if (request != null) {
//                        request.abort();
//                    }
                }
                List<DownloadInfo> lists = DownloadInfoDAO.getInstance(context.getApplicationContext())
                        .getDownloadInfosByUrl(appContent.getUrl());
                for (DownloadInfo info : lists) {
                    downloadLength += info.getDownloadLength();
                }

                DownloadFileDAO.getInstance(context.getApplicationContext()).insertDownloadFile(appContent);
                Message.obtain(handler, GET_LENGTH_SUCCESS).sendToTarget();
            }
        }.start();
    }

    private void beginDownload() {
        Log.e(TAG, "beginDownload" + appContent.getUrl());
        appContent.setStatus(AppContent.Status.WAITING);
        long blockLength = fileLength / THREAD_NUM;
        for (int i = 0; i < THREAD_NUM; i++) {
            long beginPosition = i * blockLength;
            long endPosition = (i + 1) * blockLength;
            if (i == (THREAD_NUM - 1)) {
                endPosition = fileLength;
            }
            DownloadTask task = new DownloadTask(i, beginPosition, endPosition, this, context);
            task.executeOnExecutor(THREAD_POOL_EXECUTOR, appContent.getUrl());
            if(tasks == null) {
                tasks = new ArrayList<DownloadTask>();
            }
            tasks.add(task);
        }
    }

    public void pause() {
        for (DownloadTask task : tasks) {
            if (task != null && (task.getStatus() == AsyncTask.Status.RUNNING || !task.isCancelled())) {
                task.cancel(true);
            }
        }
        tasks.clear();
        appContent.setStatus(AppContent.Status.PAUSED);
        DownloadFileDAO.getInstance(context.getApplicationContext()).updateDownloadFile(appContent);
    }

    protected synchronized void resetDownloadLength() {
        this.downloadLength = 0;
    }

    protected synchronized void updateDownloadLength(long size){
        this.downloadLength += size;
        int percent = (int)((float)downloadLength * 100 / (float)fileLength);
        appContent.setDownloadPercent(percent);
        if(percent == 100 || downloadLength == fileLength) {
            appContent.setDownloadPercent(100);
            appContent.setStatus(AppContent.Status.FINISHED);
            DownloadFileDAO.getInstance(context.getApplicationContext()).updateDownloadFile(appContent);
        }
        Intent intent = new Intent(Constants.DOWNLOAD_MSG);
        if(appContent.getStatus() == AppContent.Status.WAITING) {
            appContent.setStatus(AppContent.Status.DOWNLOADING);
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("appContent", appContent);
        intent.putExtras(bundle);
        context.sendBroadcast(intent);
    }

    protected String getDownloadPath() {
        return downloadPath;
    }

    private class InnerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_LENGTH_SUCCESS :
                    beginDownload();
                    break;
            }
            super.handleMessage(msg);
        }
    }
}
