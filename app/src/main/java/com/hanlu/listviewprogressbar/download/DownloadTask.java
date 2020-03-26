package com.hanlu.listviewprogressbar.download;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hanlu.listviewprogressbar.bean.DownloadInfo;
import com.hanlu.listviewprogressbar.db.DownloadInfoDAO;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Integer , Long> {
    private static final String TAG = "DownloadTask";

    private int taskId;
    private long beginPosition;
    private long endPosition;
    private long downloadLength;
    private String url;
    private Downloador downloador;
    private DownloadInfoDAO downloadInfoDAO;

    public DownloadTask(int taskId, long beginPosition, long endPosition, Downloador downloador,
                        Context context) {
        this.taskId = taskId;
        this.beginPosition = beginPosition;
        this.endPosition = endPosition;
        this.downloador = downloador;
        downloadInfoDAO = DownloadInfoDAO.getInstance(context.getApplicationContext());
    }

    @Override
    protected void onPreExecute() {
        Log.e(TAG, "onPreExecute");
    }

    @Override
    protected void onPostExecute(Long aLong) {
        Log.e(TAG, url + "taskId:" + taskId + "executed");
//        downloador.updateDownloadInfo(null);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
//        downloador.updateDownloadLength(values[0]);
    }

    @Override
    protected void onCancelled() {
        Log.e(TAG, "onCancelled");
//        downloador.updateDownloadInfo(null);
    }

    @Override
    protected Long doInBackground(String... params) {
        if(isCancelled()) {
            return null;
        }
        url = params[0];
        if(url == null) {
            return null;
        }
        HttpURLConnection client = null;
        try {
            client = (HttpURLConnection)new URL(url).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        HttpGet request = new HttpGet(url);
//        HttpResponse response;
        InputStream is;
        RandomAccessFile fos = null;
        OutputStream output = null;

        DownloadInfo downloadInfo = null;
        try {
            File file = new File(downloador.getDownloadPath() + File.separator + url.substring(url.lastIndexOf("/") + 1));

            downloadInfo = downloadInfoDAO.getDownloadInfoByTaskIdAndUrl(taskId, url);
            if(file.exists() && downloadInfo != null) {
                if(downloadInfo.isDownloadSuccess() == 1) {
                    return null;
                }
                beginPosition = beginPosition + downloadInfo.getDownloadLength();
                downloadLength = downloadInfo.getDownloadLength();
            }
            if(!file.exists()) {
                downloador.resetDownloadLength();
            }

//            Header header_size = new BasicHeader("Range", "bytes=" + beginPosition + "-" + endPosition);
//            request.addHeader(header_size);
//            response = client.execute(request);
//            is = response.getEntity().getContent();
            client.setRequestMethod("GET");
            client.connect();
            is = client.getInputStream();

            fos = new RandomAccessFile(file, "rw");
            fos.seek(beginPosition);

            byte buffer [] = new byte[1024];
            int inputSize = -1;
            while((inputSize = is.read(buffer)) != -1) {
                fos.write(buffer, 0, inputSize);
                downloadLength += inputSize;
                downloador.updateDownloadLength(inputSize);

                if (isCancelled()) {
                    if(downloadInfo == null) {
                        downloadInfo = new DownloadInfo();
                    }
                    downloadInfo.setUrl(url);
                    downloadInfo.setDownloadLength(downloadLength);
                    downloadInfo.setTaskId(taskId);
                    downloadInfo.setDownloadSuccess(0);
                    downloadInfoDAO.insertDownloadInfo(downloadInfo);
                    return null;
                }
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } finally{
            try{
//                if (request != null) {
//                    request.abort();
//                }
                if(output != null) {
                    output.close();
                }
                if(fos != null) {
                    fos.close();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        if(downloadInfo == null) {
            downloadInfo = new DownloadInfo();
        }
        downloadInfo.setUrl(url);
        downloadInfo.setDownloadLength(downloadLength);
        downloadInfo.setTaskId(taskId);
        downloadInfo.setDownloadSuccess(1);
        downloadInfoDAO.insertDownloadInfo(downloadInfo);
        return null;
    }
}
