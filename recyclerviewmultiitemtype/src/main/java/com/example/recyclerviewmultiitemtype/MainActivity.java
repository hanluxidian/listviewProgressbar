package com.example.recyclerviewmultiitemtype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.recyclerviewmultiitemtype.fragment.Fragment1;
import com.example.recyclerviewmultiitemtype.fragment.Fragment2;
import com.example.recyclerviewmultiitemtype.recycleview.CommItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Image> mData;
    RecyclerView recyclerView;
    Fragment fragment1 = Fragment1.newInstance("hello");
    Fragment fragment2 = Fragment2.newInstance("hello fragment2");

    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment1 = Fragment1.newInstance("hello");
    }

    //按钮添加一个fragment
    public void addFragment1(View v) {
        Log.d("hanlu...", "addFragment1");

        FragmentManager fm = getSupportFragmentManager();
        addFragment(fragment1, fm);
    }

    public void addFragment2(View v) {
        Log.d("hanlu...", "addFragment2");

        FragmentManager fm = getSupportFragmentManager();
        addFragment(fragment2, fm);
    }

    private void addFragment(Fragment fragment, FragmentManager fm) {
        for (Fragment fragment3 : fragmentList) {
            fm.beginTransaction().hide(fragment3).commit();
        }
        if(fragment.isAdded()){
            Log.d("hanlu...", "addFragment isAdded");
            fm.beginTransaction().show(fragment).commit();
        } else {
            Log.d("hanlu...", "addFragment else");
            fm.beginTransaction().add(R.id.framelayout_for_fragment, fragment, "fragment1").commit();
            fragmentList.add(fragment);
        }
    }
}
