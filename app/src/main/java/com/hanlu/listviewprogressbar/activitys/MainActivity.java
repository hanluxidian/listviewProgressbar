package com.hanlu.listviewprogressbar.activitys;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hanlu.listviewprogressbar.R;
import com.hanlu.listviewprogressbar.adapter.myAdapter;
import com.hanlu.listviewprogressbar.bean.AppContent;
import com.hanlu.listviewprogressbar.download.Downloador;
import com.hanlu.listviewprogressbar.utils.DownloadUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private ListView mlistview;
    private List<AppContent> mlist;
    private Map<String, Downloador> downloadorMap;
    myAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mlistview = (ListView) findViewById(R.id.listview_progressbar);

        adapter = new myAdapter(this);
        mlist = DownloadUtils.getData();
        adapter.setmDatass(mlist);
        mlistview.setAdapter(adapter);
        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppContent appContent = mlist.get(position);
                if(downloadorMap != null && downloadorMap.containsKey(appContent.getUrl())) {
                    downloadorMap.get(appContent.getUrl()).pause();
                    downloadorMap.remove(appContent.getUrl());
                    appContent.setStatus(AppContent.Status.PAUSED);
                    adapter.notifyDataSetChanged();
                } else {
                    Downloador downloador = new Downloador(MainActivity.this, appContent);
                    downloador.download();
                    appContent.setStatus(AppContent.Status.WAITING);
                    adapter.notifyDataSetChanged();
                    if(downloadorMap == null) {
                        downloadorMap = new HashMap<String, Downloador>();
                    }
                    downloadorMap.put(appContent.getUrl(), downloador);
                }
            }
        });
        IntentFilter intent = new IntentFilter(Constants.DOWNLOAD_MSG);
        registerReceiver(downloadStatusReceiver, intent);
    }

    private BroadcastReceiver downloadStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            AppContent appContent = intent.getParcelableExtra("appContent");
            if(appContent == null) return;
            int itemIndex = 0;
            for(AppContent appContent1 : mlist) {
                if(appContent.getUrl().equals(appContent1.getUrl())) {
                    itemIndex = mlist.indexOf(appContent1);
                    appContent1.setDownloadPercent(appContent.getDownloadPercent());
                    break;
                }
            }
            updateView(itemIndex);
        }
    };

    private void updateView(int itemIndex) {
        int visiblePosition = mlistview.getFirstVisiblePosition();
        if (itemIndex - visiblePosition >= 0) {
            View view = mlistview.getChildAt(itemIndex - visiblePosition);
            adapter.updateView(view, itemIndex);
        }
    }
}
