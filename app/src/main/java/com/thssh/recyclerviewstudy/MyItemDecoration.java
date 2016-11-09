package com.thssh.recyclerviewstudy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static android.support.v7.widget.LinearLayoutManager.*;

/**
 * Created by zhang on 2016/10/28.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration{
    /**
     * 巧妙利用系统自带,可定制
     */
    private final  static  int[] ATTRS = {android.R.attr.listDivider};
    /**
     * 获取布局走向
     */
    private final static int ORIENTETION_VERTIVAL = VERTICAL;
    private  final static int ORIENTATION_HORIZONTAL = HORIZONTAL;
    private Drawable mDivider;
    private int mOrientation;

    /**
     * 构造方法,获取系统属性,猴设置布局走向
     */
    public MyItemDecoration(Context context , int Orientation) {
        TypedArray ta = context.obtainStyledAttributes(ATTRS);
        mDivider = ta.getDrawable(0);
        ta.recycle();
        setOrientation(Orientation);
    }

    /**
     * 设置布局走向方法
     */
    private void setOrientation(int Orientation){
        if (mOrientation !=ORIENTETION_VERTIVAL && mOrientation != ORIENTATION_HORIZONTAL)
            throw new IllegalArgumentException("Error Orientation");
        mOrientation = Orientation;
    }
    /**
     * 要画分割线必须得有画布
     * onDraw方法
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == ORIENTETION_VERTIVAL){
            drawVertical(c,parent);
        }else{
            drawHorizontal(c,parent);
        }

    }
    /**
     * 横向
     */

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        //回去父布局的顶部内间距为top
        final int top = parent.getPaddingTop();
        //由图可知,利用parent的高度减去底部内间距为底
        final int buttom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0 ;i <childCount ;++i){
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams rl = (RecyclerView.LayoutParams) child.getLayoutParams();
            //获取子布局的最右位置+右外间距为右
            final int left = child.getRight() + rl.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left,top,right,buttom);
            mDivider.draw(c);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        //parent的左内间距为左
        final int left = parent.getPaddingLeft();
        //parent宽度减去parent的right为右
        final int right = parent.getWidth()- parent.getRight();
        final int childCount = parent.getChildCount();
        for (int i = 0 ; i <childCount ;++i){
            final View child = parent.getChildAt(i);
            final RecyclerView recyclerView = new RecyclerView(parent.getContext());
            final RecyclerView.LayoutParams rl = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + rl.bottomMargin;
            final int buttom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left,top,right,buttom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == ORIENTETION_VERTIVAL){
            outRect.set(0,0,0,mDivider.getIntrinsicHeight());
        }else{
            outRect.set(0,0,mDivider.getIntrinsicWidth(),0);
        }
    }
}
