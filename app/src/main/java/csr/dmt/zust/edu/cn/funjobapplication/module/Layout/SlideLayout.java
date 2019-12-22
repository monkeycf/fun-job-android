package csr.dmt.zust.edu.cn.funjobapplication.module.Layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;

/**
 * created by monkeycf on 2019/12/21
 */
public class SlideLayout extends LinearLayout {

    private ViewDragHelper mDragHelper;
    private View contentView;
    private View actionView;
    private int dragDistance;

    public SlideLayout(Context context) {
        super(context);
        init();
    }

    public SlideLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SlideLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    //初始化
    public void init() {
        /*
        * ViewDragHelper.Callback是连接ViewDragHelper与view之间的桥梁（这个view一般是指拥子view的容器即parentView）
        * ViewDragHelper可以检测到是否触及到边缘
        * ViewDragHelper并不是直接作用于要被拖动的View，而是使其控制的视图容器中的子View可以被拖动，如果要指定某个子view的行为，需要在Callback中想办法；
        * ViewDragHelper的本质其实是分析onInterceptTouchEvent和onTouchEvent的MotionEvent参数，然后根据分析的结果去改变一个容器中被拖动子View的位置（
            通过offsetTopAndBottom(int offset)和offsetLeftAndRight(int offset)方法 ），他能在触摸的时候判断当前拖动的是哪个子View；
         */
        mDragHelper = ViewDragHelper.create(this, new DragHelperCallback());
    }

    /**
     * 把容器的事件处理委托给ViewDragHelper对象
     *
     * @param event MotionEvent
     * @return 是否允许拖动
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // shouldInterceptTouchEvent 决定是否应该拦截当前的事件
        if (mDragHelper.shouldInterceptTouchEvent(event)) {
            return true;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.performClick();
        // 不断移动时触发
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        // 初始化
        // contentView 常显示区域
        // actionView 拖动显示区域
        super.onFinishInflate();
        contentView = getChildAt(0);
        actionView = getChildAt(1);
        actionView.setVisibility(GONE);
    }

    //设置拖动的距离为actionView的宽度
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        dragDistance = actionView.getMeasuredWidth();
    }

    private class DragHelperCallback extends ViewDragHelper.Callback {
        //用来确定contentView和actionView是可以拖动的
        @Override
        public boolean tryCaptureView(@NonNull View view, int i) {
            return view == contentView || view == actionView;
        }

        // 被拖动的view位置改变的时候调用，如果被拖动的view是contentView，
        // 我们需要在这里更新actionView的位置
        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            // dx 每次移动的距离
            if (changedView == contentView) {
                actionView.offsetLeftAndRight(dx);
            } else {
                contentView.offsetLeftAndRight(dx);
            }
            // actionView 是否可见
            // VISIBLE  可见
            // INVISIBLE  不可见但是占用布局空间
            // GONE  不可见也不占用布局空间
            if (actionView.getVisibility() == View.GONE) {
                actionView.setVisibility(View.VISIBLE);
            }
        }

        //用来限制view在x轴上拖动
        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            if (child == contentView) {
                final int leftBound = getPaddingLeft();
                final int minLeftBound = -leftBound - dragDistance;
                return Math.min(Math.max(minLeftBound, left), 0);
            } else {
                final int minLeftBound = getPaddingLeft() + contentView.getMeasuredWidth() - dragDistance;
                final int maxLeftBound = getPaddingLeft() + contentView.getMeasuredWidth() + getPaddingRight();
                return Math.min(Math.max(left, minLeftBound), maxLeftBound);
            }
        }

        //用来限制view可以拖动的范围
        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return dragDistance;
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            return 0;
        }
    }
}
