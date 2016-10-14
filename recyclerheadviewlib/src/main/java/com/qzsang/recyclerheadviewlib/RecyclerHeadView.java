package com.qzsang.recyclerheadviewlib;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
/**
 * Created by qzsang on 2016/10/14 0014.
 */

public class RecyclerHeadView extends LinearLayout implements NestedScrollingParent{
    private Context context;

    private final int headViewID = R.layout.view_recycer_head; //HeadView Id
    private View headView;
    private int headHeight;
    public RecyclerHeadView(Context context) {
        super(context);
        init(context);
    }

    public RecyclerHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public void init (Context context) {
        this.context = context;
        setOrientation(LinearLayout.VERTICAL);//设置垂直方向
        headView = View.inflate(context,headViewID,null);
        addView(headView,0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
   //     headView.measure(widthMeasureSpec,heightMeasureSpec);

        headView.measure(widthMeasureSpec,MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED));

        for (int i = 1;i < getChildCount();i++) {
            View childView = getChildAt(i);
            childView.measure(widthMeasureSpec,heightMeasureSpec);
        }
        headHeight = headView.getMeasuredHeight();
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.getSize(heightMeasureSpec) + headHeight);
    }


    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
       // super.onNestedPreScroll(target, dx, dy, consumed);
        int scrollY = getScrollY();
        boolean isShow = dy < 0 && scrollY > 0 && !ViewCompat.canScrollVertically(target,-1);
        boolean isHidden = dy > 0 && scrollY < headHeight ;
        if (isShow || isHidden) {
            scrollBy(0,dy);
            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        if (getScrollY() >= headHeight) return false;
        return true;
    }



    @Override
    public void scrollTo(int x, int y) {
        if (y < 0)
            y = 0;
        if (y > headHeight)
            y = headHeight;
        if (y == getScrollY())
            return;

        super.scrollTo(x, y);
    }

    class T implements NestedScrollingParent{

        @Override
        public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
            return false;
        }

        @Override
        public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {

        }

        @Override
        public void onStopNestedScroll(View target) {

        }

        @Override
        public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

        }

        @Override
        public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

        }

        @Override
        public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
            return false;
        }

        @Override
        public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
            return false;
        }

        @Override
        public int getNestedScrollAxes() {
            return 0;
        }
    }
}
