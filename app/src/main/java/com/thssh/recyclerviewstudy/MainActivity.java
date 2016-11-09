package com.thssh.recyclerviewstudy;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvList;
    private MyRecycleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout srlContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<String> data = genrenterMockDatas();
        setContentView(R.layout.activity_main);
        srlContainer = (SwipeRefreshLayout) findViewById(R.id.srl_container);
        mAdapter = new MyRecycleAdapter(data);
        mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rvList = (RecyclerView) findViewById(R.id.rv_list);
        rvList.setItemAnimator(new DefaultItemAnimator());
//        rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvList.setLayoutManager(new GridLayoutManager(this,3));
//        rvList.addItemDecoration(new MyItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvList.addItemDecoration(new RecyclerView.ItemDecoration(){
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(10, 10, 10, 10);
            }
        });
        rvList.setAdapter(mAdapter);
        srlContainer.setRefreshing(true);
        srlContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Observable.timer(5, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                srlContainer.setRefreshing(false);
                            }
                        });
//                Toast.makeText(MainActivity.this, "刷新..." , Toast.LENGTH_SHORT).show();
            }
        });
        srlContainer.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_light,
                android.R.color.holo_green_light, android.R.color.darker_gray);
        srlContainer.setRefreshing(false);
        Toast.makeText(MainActivity.this, "刷新..." , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if(srlContainer.isRefreshing()) {
            srlContainer.setRefreshing(false);
        }else{
            super.onBackPressed();
        }
    }

    private List<String> genrenterMockDatas() {
        List<String> list = new ArrayList<>();
        String[] data = getResources().getStringArray(R.array.data);
        return Arrays.asList(data);
//        list.add("sdfsadfsadfsadfsadf 1");
//        list.add("f 2");
//        list.add("dsds 3");
//        list.add("sdfsadfsadfsadfsadf 4");
//        list.add("fff 5");
//        list.add("sdfsadfsadfsadfsadf");
//        list.add("sdfsadfsadfsadfsadfsdfsadfsadfsadfsadfsdfsadfsadfsadfsadf");
//        list.add("sdfsadfsadfsadfsadf");
//        list.add("sdfsadfsadfsadfsadf");
//        list.add("sdfsadfsadfsadfsadfsdfsadfsadfsadfsadfsdfsadfsadfsadfsadf");
//        list.add("sdfsadfsadfsadfsadf");
//        list.add("sdfsadfsadfsadfsadf");
//        list.add("sdfsadfsadfsadfsadfsdfsadfsadfsadfsadfsdfsadfsadfsadfsadf");
//        list.add("sdfsadfsadfsadfsadf");
//        list.add("sdfsadfsadfsadfsadf");
//        list.add("sdfsadfsadfsadfsadfsdfsadfsadfsadfsadfsdfsadfsadfsadfsadf");
//        list.add("sdfsadfsadfsadfsadf");
//        list.add("sdfsadfsadfsadfsadf");
//        list.add("sdfsadfsadfsadfsadfsdfsadfsadfsadfsadfsdfsadfsadfsadfsadf");
//        list.add("sdfsadfsadfsadfsadf");
//        list.add("sdfsadfsadfsadfsadf");
//        list.add("dddd");
//        list.add("sdfsadfsadfsadfsadf");
//        list.add("dd");
//        list.add("sdfsadfsadfsadfsadf");
//        list.add("sdfsadfsadfsadfsadf");
//        list.add("sdfsadfsadfsadfsadf");
//        list.add("sdfsadfsadfsadfsadf");
//        list.add("sdfsadfsadfsadfsadf");
//        list.add("sdfsadfsadfsadfsadf");
//        list.add("sdfsadfsadfsadfsadf");
//        list.add("sdfsadfsadfsadfsadf");
//        return list;
    }
}
