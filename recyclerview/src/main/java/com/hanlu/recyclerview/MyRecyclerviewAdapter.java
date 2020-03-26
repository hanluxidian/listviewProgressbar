package com.hanlu.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerviewAdapter extends RecyclerView.Adapter<MyRecyclerviewAdapter.MyViewHoder> {

    private final Context context;
    private  ArrayList<String> datas;

    public MyRecyclerviewAdapter(Context context, ArrayList<String> datas){
        this.context = context;
        this.datas = datas;
    }
    @NonNull
    @Override
    public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("hanlu...", "onCreateViewHolder viewtype = " + viewType);
        View itemView = View.inflate(context, R.layout.item_recyclerview,null);
        return new MyViewHoder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
        String data = datas.get(position);
        holder.tv_title.setText(data);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHoder extends RecyclerView.ViewHolder{
        private ImageView iv_icon;
        private TextView tv_title;
        public MyViewHoder(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);

        }
    }
}
