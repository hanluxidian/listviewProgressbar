package com.example.listviewjubushuaxin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<BeanType> mData;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.listview_111);
        Log.d("hanlu...", "listview in maniactivity = " + listview);
        initData();
        MyBaseAdapter myBaseAdapter = new MyBaseAdapter(this, mData);
        myBaseAdapter.setListview(listview);
        listview.setAdapter(myBaseAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("hanlu333", "onitemclick " + position + " parent = " + parent);
            }
        });

    }

    private void initData() {
        mData = new LinkedList<>();
        mData.add(new DataBean("孙悟空", "打妖怪"));
        mData.add(new DataBean2("猪八戒", "打妖怪1"));
        mData.add(new DataBean("孙悟空", "打妖怪2"));
        mData.add(new DataBean2("唐僧", "取经"));
        mData.add(new DataBean("孙悟空", "打妖怪"));
        mData.add(new DataBean2("猪八戒1", "打妖怪1"));
        mData.add(new DataBean("孙悟空", "打妖怪2"));
        mData.add(new DataBean2("唐僧1", "取经"));
        mData.add(new DataBean("孙悟空", "打妖怪"));
        mData.add(new DataBean2("猪八戒2", "打妖怪1"));
        mData.add(new DataBean("孙悟空", "打妖怪2"));
        mData.add(new DataBean2("唐僧2", "取经"));
    }
}
