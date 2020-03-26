package com.hanlu.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.AbstractSequentialList;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private Button btn_add;
    private Button btn_delete;
    private Button btn_list;
    private Button btn_grid;
    private Button btn_flow;
    private ArrayList<String> datas;
    private MyRecyclerviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();
        initdata();
        adapter = new MyRecyclerviewAdapter(this, datas);
        recyclerView.setAdapter(adapter);
        //layoutmanager
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));


    }

    private void initview() {
        btn_add = (Button) findViewById(R.id.btn_recyclerview_add);
        btn_delete = (Button) findViewById(R.id.btn_recyclerview_delete);
        btn_list = (Button) findViewById(R.id.btn_recyclerview_listview);
        btn_grid = (Button) findViewById(R.id.btn_recyclerview_grid);
        btn_flow = (Button) findViewById(R.id.btn_recyclerview_flow);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        btn_add.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_list.setOnClickListener(this);
        btn_grid.setOnClickListener(this);
        btn_flow.setOnClickListener(this);
    }

    private void initdata() {
        //准备数据集合
        datas = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            datas.add("content + " + i);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_recyclerview_add:
                break;
            case R.id.btn_recyclerview_delete:
                break;
            case R.id.btn_recyclerview_listview:
                break;
            case R.id.btn_recyclerview_grid:
                break;
            case R.id.btn_recyclerview_flow:
                break;
                default:
                    break;


        }
    }
}
