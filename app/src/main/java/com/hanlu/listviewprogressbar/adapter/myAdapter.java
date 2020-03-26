package com.hanlu.listviewprogressbar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hanlu.listviewprogressbar.R;
import com.hanlu.listviewprogressbar.bean.AppContent;

import java.util.List;

public class myAdapter extends BaseAdapter {

    private List<AppContent> mDatas = null;
    private Context mContext;

    public myAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setmDatass(List<AppContent> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_download, null);
            holder.name = (TextView)convertView.findViewById(R.id.name);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressbar);
            holder.downloadPercent = (TextView) convertView.findViewById(R.id.download_percent);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        setData(holder, position);
        return convertView;
    }

    private void setData(ViewHolder holder, int position) {

        AppContent appContent = mDatas.get(position);
        holder.name.setText(appContent.getmName());
        holder.progressBar.setProgress(appContent.getDownloadPercent());
    }

    public void updateView(View view, int itemIndex) {
        if(view == null) {
            return;
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.name = (TextView) view.findViewById(R.id.name);
        holder.downloadPercent = (TextView) view.findViewById(R.id.download_percent);
        holder.progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        setData(holder, itemIndex);
    }

    private class ViewHolder {
        private TextView name;
        private TextView downloadPercent;
        private ProgressBar progressBar;
    }

}
