package com.thssh.recyclerviewstudy;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhang on 2016/10/28.
 */

public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.MyRecycleViewHolder>{
    private List<String> mDatas;
    private OnItemClickListener listener;

    public static interface OnItemClickListener {
        void onClick(View v, int position);
    }

    public MyRecycleAdapter(List<String> data) {
        this.mDatas = data;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public MyRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_recycle, parent, false);
        return new MyRecycleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyRecycleViewHolder holder, final int position) {
        if(mDatas == null || mDatas.size() < position || position < 0) return;
        String posItem = mDatas.get(position);
        holder.tvTitle.setText(posItem);
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    protected final static class MyRecycleViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        public MyRecycleViewHolder(View itemView) {
            super(itemView);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
