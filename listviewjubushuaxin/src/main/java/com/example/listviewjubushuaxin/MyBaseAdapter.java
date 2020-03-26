package com.example.listviewjubushuaxin;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MyBaseAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;

    public MyBaseAdapter(Context context, List<BeanType> mData) {
        this.context = context;
        this.mData = mData;
    }

    private List<BeanType> mData;
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("hanlu...", "getview 111 " + "parent = " + parent);
        if(getItemViewType(position) == BeanType.Type_1){
            ViewHolder viewHolder;
            if(convertView == null){
                Log.d("hanlu...", "getview 222");
                convertView = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.tvName = (TextView)convertView.findViewById(R.id.textview_name);
                viewHolder.tvAction = (TextView)convertView.findViewById(R.id.textview_action);
                viewHolder.btn = (Button) convertView.findViewById(R.id.item_button);
                convertView.setTag(viewHolder);
            } else {
                Log.d("hanlu...", "getview 333");
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvName.setText(((DataBean)mData.get(position)).getmName());
            viewHolder.tvAction.setText(((DataBean)mData.get(position)).getmAction());
            viewHolder.btn.setTag(position);
            viewHolder.btn.setOnClickListener(this);
            return convertView;
        } else if(getItemViewType(position) == BeanType.Type_2) {
            ViewHolder2 viewHolder2;
            if(convertView == null){
                Log.d("hanlu...", "getview 222");
                convertView = LayoutInflater.from(context).inflate(R.layout.item_layout_2, parent, false);
                viewHolder2 = new ViewHolder2();
                viewHolder2.tvName = (TextView)convertView.findViewById(R.id.textview_name);
                viewHolder2.tvAction = (TextView)convertView.findViewById(R.id.textview_action);
                viewHolder2.btn = (Button) convertView.findViewById(R.id.item_button);
                convertView.setTag(viewHolder2);
            } else {
                Log.d("hanlu...", "getview 333");
                viewHolder2 = (ViewHolder2) convertView.getTag();
            }
            viewHolder2.tvName.setText(((DataBean2)mData.get(position)).getmName());
            viewHolder2.tvAction.setText(((DataBean2)mData.get(position)).getmAction());
            viewHolder2.btn.setTag(position);
            viewHolder2.btn.setOnClickListener(this);
            return convertView;
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {
                int position = (int) v.getTag();
//                updateItemView(position);
                updateItemPos(position);
    }
    private void updateItemPos(int position) {
        DataBean dataBean = getNewDataben();
        mData.remove(position);
        mData.set(position, dataBean);
        notifyDataSetChanged();
    }
    private void updateItemView(int position) {
        if(listview != null){
            View view = listview.getChildAt(position - listview.getFirstVisiblePosition());
            TextView tvName = (TextView)view.findViewById(R.id.textview_name);
            TextView tvAction = (TextView)view.findViewById(R.id.textview_action);
            DataBean dataBean = getNewDataben();
            tvName.setText(dataBean.getmName());
            tvAction.setText(dataBean.getmAction());
        }
    }

    private DataBean getNewDataben() {
        DataBean ll = new DataBean("美国队长", "打联盟");
        return ll;
    }

    private ListView listview;
    public void setListview(ListView listview){
        this.listview = listview;
    }

    static class ViewHolder{
        TextView tvName;
        TextView tvAction;
        Button btn;
    }

    static class ViewHolder2{
        TextView tvName;
        TextView tvAction;
        Button btn;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
