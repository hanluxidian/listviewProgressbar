package com.example.recyclerviewmultiitemtype.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewmultiitemtype.Image;
import com.example.recyclerviewmultiitemtype.MainActivity;
import com.example.recyclerviewmultiitemtype.MyAdapter;
import com.example.recyclerviewmultiitemtype.R;
import com.example.recyclerviewmultiitemtype.oneImageBean;
import com.example.recyclerviewmultiitemtype.recycleview.CommItemDecoration;
import com.example.recyclerviewmultiitemtype.threeImageBean;
import com.example.recyclerviewmultiitemtype.twoImageBean;

import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends Fragment {
    List<Image> mData;
    RecyclerView recyclerView;
    MyAdapter adapter;

    public static Fragment newInstance(String msg) {
        Fragment2 fragmentWeinxin = new Fragment2();
        Bundle bundle = new Bundle();
        bundle.putString("fragment2", msg);
        fragmentWeinxin.setArguments(bundle);
        return fragmentWeinxin;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
//        LifeCycleLog.lifeCycleLog(getContext(), " FragmentWeinxin onAttach " + getArguments());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);

//        LifeCycleLog.lifeCycleLog(getContext(), " FragmentWeinxin onCreate "  + getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        LifeCycleLog.lifeCycleLog(getContext(), " FragmentWeinxin onViewCreated "  + getArguments());
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
//        LifeCycleLog.lifeCycleLog(getContext(), " FragmentWeinxin onActivityCreated "  + getArguments());
        initData();
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerview_fragment2);
        adapter = new MyAdapter(mData, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
//        recyclerView.addItemDecoration(CommItemDecoration.createVertical(getActivity(), "#fffff0", 5));
        recyclerView.getChildCount();
        adapter.setOnItemClickLitener(new MyAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view) {
                int position = recyclerView.getChildViewHolder(view).getAdapterPosition();
                Toast.makeText(getActivity(),"这是条目 " + position , Toast.LENGTH_LONG).show();
            }
        });
        Log.d("hanlu...", "getChildCount == " + recyclerView.getChildCount());
    }
//
//    public void fragment2Onitemclick(View v) {
//        Log.d("hanluclick...", "fragment2Onitemclick");
//        int postion = recyclerView.getChildAdapterPosition(v);
//    }

    @Override
    public void onStart() {
        super.onStart();
//        LifeCycleLog.lifeCycleLog(getContext(), " FragmentWeinxin onStart "  + getArguments());
    }

    @Override
    public void onResume() {
        super.onResume();
//        LifeCycleLog.lifeCycleLog(getContext(), " FragmentWeinxin onResume "  + getArguments());
    }

    @Override
    public void onPause() {
        super.onPause();
//        LifeCycleLog.lifeCycleLog(getContext(), " FragmentWeinxin onPause "  + getArguments());
    }

    @Override
    public void onStop() {
        super.onStop();
//        LifeCycleLog.lifeCycleLog(getContext(), " FragmentWeinxin onStop "  + getArguments());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        LifeCycleLog.lifeCycleLog(getContext(), " FragmentWeinxin onDestroyView "  + getArguments());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        LifeCycleLog.lifeCycleLog(getContext(), " FragmentWeinxin onDestroy "  + getArguments());
//        LifeCycleLog.lifeCycleLog(getContext(), " FragmentWeinxin onDestroy ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        LifeCycleLog.lifeCycleLog(getContext(), " FragmentWeinxin onDetach "  + getArguments());
//        LifeCycleLog.lifeCycleLog(getContext(), " FragmentWeinxin onDetach ");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        LifeCycleLog.lifeCycleLog(getContext(), " FragmentWeinxin onSaveInstanceState");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
//        LifeCycleLog.lifeCycleLog(getContext(), " FragmentWeinxin onViewStateRestored");
    }


    private void initData() {
        mData = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            mData.add(new oneImageBean());
        }
//        Log.d("hanlu...", "mdata init == " + mData);
    }
}
