package com.thssh.recyclerviewstudy;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

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
        final List<String> data = genrenterMockDatas();
        setContentView(R.layout.activity_main);
        srlContainer = (SwipeRefreshLayout) findViewById(R.id.srl_container);
        mAdapter = new MyRecycleAdapter(data);
        mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rvList = (RecyclerView) findViewById(R.id.rv_list);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        rvList.setLayoutManager(new GridLayoutManager(this, 3));
//        rvList.setLayoutManager(mLayoutManager);
//        rvList.addItemDecoration(new MyItemDecoration(this, LinearLayoutManager.VERTICAL));
//        rvList.addItemDecoration(new RecyclerView.ItemDecoration(){
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                outRect.set(0, 5, 0, 10);
//            }
//        });

        rvList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
//                .color(Color.GRAY)
                .drawable(R.drawable.shape_decoration_1)
                .size(3)
                .build());

        mAdapter.setOnItemClickListener(new MyRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Snackbar.make(v, data.get(position), Snackbar.LENGTH_SHORT).show();
            }
        });
        rvList.setAdapter(mAdapter);
        srlContainer.setRefreshing(true);
        srlContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Observable.timer(4, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                srlContainer.setRefreshing(false);
                            }
                        });
            }
        });
        srlContainer.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_light,
                android.R.color.holo_green_light, android.R.color.darker_gray);
        srlContainer.setRefreshing(false);
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 获取最后一个完全显示的item position
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部并且是向下滑动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        // 加载更多
                        Snackbar.make(recyclerView, "加载更多", Snackbar.LENGTH_SHORT).show();
                    }
                }

                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToLast = dy > 0;
            }
        });
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

    @NonNull
    private List<String> genrenterMockDatas() {
        List<String> list = new ArrayList<>();
        String[] data = getResources().getStringArray(R.array.data);
        return Arrays.asList(data);
    }
}
