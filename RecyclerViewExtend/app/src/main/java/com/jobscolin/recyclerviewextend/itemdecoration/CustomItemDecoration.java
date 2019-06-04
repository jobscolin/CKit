package com.jobscolin.recyclerviewextend.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author : XuDonglin
 * @time : 2019/05/30
 * @describe :RecyclerView通用的分割线
 */
public class CustomItemDecoration extends RecyclerView.ItemDecoration {

    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;
    public static final int GRID = 2;

    private float space = 10.0f;
    private float paddingLeft = 0f;
    private float paddingRight = 0f;
    private float paddingTop = 0f;
    private float paddingBottom = 0f;
    private boolean showLast = false;
    private boolean showFirst = false;

    /**
     * 这4个参数用于grid模式下，表示RecyclerView最外层边界是否显示
     */
    private boolean showTop = false;
    private boolean showBottom = false;
    private boolean showLeft = false;
    private boolean showRight = false;
    /**
     * 这个字段用于grid模式下，如果最后一行不是不满的情况下，最后一个item是否展示竖直分割线
     * 如果item个数只有一行那么这个参数失效
     */
    private boolean showLastColumn = true;
    private int color;
    private Context mContext;
    private Paint divider;
    private int mod = VERTICAL;

    private CustomItemDecoration() {
    }

    private void setParams(Context context, Params params) {
        mContext = context;
        this.space = params.space;
        this.mod = params.mod;
        this.paddingLeft = params.paddingLeft;
        this.paddingRight = params.paddingRight;
        this.paddingTop = params.paddingTop;
        this.paddingBottom = params.paddingBottom;
        this.showLast = params.showLast;
        this.showFirst = params.showFirst;
        this.color = params.color;
        this.showLastColumn = params.showLastColumn;
        this.showTop = params.showTop;
        this.showBottom = params.showBottom;
        this.showRight = params.showRight;
        this.showLeft = params.showLeft;
        divider = new Paint();
        divider.setColor(mContext.getResources().getColor(color));
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (mod == VERTICAL) {
            drawVertical(c, parent, state);
        } else if (mod == HORIZONTAL) {
            drawHorizontal(c, parent, state);
        } else {
            drawGrid(c, parent, state);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        if (mod == VERTICAL) {
            setVerticalOffsets(outRect, parent, view);
        } else if (mod == HORIZONTAL) {
            setHorizontalOffsets(outRect, parent, view);
        } else {
            setGridOffsets(outRect, parent, view, state);
        }
    }

    private int getSpanCount(RecyclerView parent) {
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        int spanCount = layoutManager.getSpanCount();
        return spanCount;
    }

    /**
     * 设置grid item间距
     * @param outRect
     * @param parent
     * @param view
     * @param state
     */
    private void setGridOffsets(Rect outRect, RecyclerView parent, View view, RecyclerView.State state) {
        int spanCount = getSpanCount(parent);
        int itemCount = state.getItemCount();
        int remainder = itemCount % spanCount;
        int childPosition = parent.getChildLayoutPosition(view);
        boolean firstColumn = childPosition % spanCount == 0;
        boolean lastColumn = childPosition % spanCount == (spanCount - 1);
        // 行数
        int row = (itemCount + spanCount - 1) / spanCount;

        int top = showTop ? (int) space : 0;
        int bottom = showBottom ? (int) space : 0;
        int left = showLeft ? (int) space : 0;
        int right = showRight ? (int) space : 0;
        //如果只有一行
        if (itemCount <= spanCount) {
            // 如果只有一个
            if (itemCount == 1) {
                right = showLastColumn ? (int) space : 0;
                outRect.set(left, top, right, bottom);
            } else {
                // 如果是第一个
                if (childPosition == 0) {
                    outRect.set(left, top, (int) space, bottom);
                } else if (childPosition == itemCount - 1) {
                    // 最后一个 最后一个item是铺满全屏的
                    if (itemCount == spanCount) {
                        right = showRight ? (int) space : 0;
                        outRect.set(0, top, right, bottom);
                    } else {
                        right = showLastColumn ? (int) space : 0;
                        outRect.set(0, top, right, bottom);
                    }
                } else {
                    // 中间的
                    outRect.set(0, top, (int) space, bottom);
                }
            }
        } else {
            // 存在多行的情况下 每个item下和左设置间距

            if (childPosition < spanCount) {
                // 第一行
                if (firstColumn) {
                    // 第一列
                    outRect.set(left, top, (int) space, (int) space);
                } else if (lastColumn) {
                    // 最后一列
                    outRect.set(0, top, right, (int) space);
                } else {
                    // 中间
                    outRect.set(0, top, (int) space, (int) space);
                }
            } else if (childPosition >= (row - 1) * spanCount) {
                // 最后一行
                if (remainder == 0) {
                    right = showRight ? (int) space : 0;
                    outRect.set(0, 0, right, bottom);
                } else if (childPosition == (row - 1) * spanCount) {
                    outRect.set(left, 0, (int) space, bottom);
                } else {
                    outRect.set(0, 0, (int) space, bottom);
                }

            } else {
                // 中间的
                if (firstColumn) {
                    outRect.set(left, 0, (int) space, (int) space);
                } else if (lastColumn) {
                    outRect.set(0, 0, right, (int) space);
                } else {
                    outRect.set(0, 0, (int) space, (int) space);
                }
            }
        }

    }

    /**
     * 设置水平item间距
     *
     * @param outRect
     * @param parent
     * @param view
     */
    private void setHorizontalOffsets(Rect outRect, RecyclerView parent, View view) {
        int childLayoutPosition = parent.getChildLayoutPosition(view);
        if (showFirst && !showLast) {
            outRect.set((int) space, 0, 0, 0);
        } else if (!showFirst && showLast) {
            outRect.set(0, 0, (int) space, 0);
        } else if (showFirst && showLast) {
            if (childLayoutPosition == 0) {
                outRect.set((int) space, 0, (int) space, 0);
            } else {
                outRect.set(0, 0, (int) space, 0);
            }
        } else {
            if (childLayoutPosition == 0) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set((int) space, 0, 0, 0);
            }
        }
    }

    /**
     * 设置垂直item 间距
     *
     * @param outRect
     * @param parent
     * @param view
     */
    private void setVerticalOffsets(Rect outRect, RecyclerView parent, View view) {
        int childLayoutPosition = parent.getChildLayoutPosition(view);
        if (showFirst && !showLast) {
            outRect.set(0, (int) space, 0, 0);

        } else if (!showFirst && showLast) {
            outRect.set(0, 0, 0, (int) space);

        } else if (showFirst && showLast) {
            if (childLayoutPosition == 0) {
                outRect.set(0, (int) space, 0, (int) space);
            } else {
                outRect.set(0, 0, 0, (int) space);
            }
        } else {
            if (childLayoutPosition == 0) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, (int) space, 0, 0);
            }
        }
    }

    /**
     * grid分割线
     * 暂时只考虑分割线只有在最外层有padding
     * 常规画每个item的右边和下边分割线，边界情况下特殊处理
     * 边界padding 同时存在时比较复杂，考虑存在边界是padding无效话处理
     *
     * @param canvas
     * @param parent
     * @param state
     */
    private void drawGrid(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int spanCount = getSpanCount(parent);
        int itemCount = state.getItemCount();
        int remainder = itemCount % spanCount;
        int childCount = parent.getChildCount();
        int row = (itemCount + spanCount - 1) / spanCount;
        float left, top, right, bottom;

        if (showLeft || showRight || showTop || showBottom) {
            // 存在边界

            if (row == 1) {
                //只有一行数据，这时候showRight是最后一个child的边界
                View firstChild = parent.getChildAt(0);
                View lastChild = parent.getChildAt(itemCount - 1);

                //优先画边界
                if (showLeft) {
                    left = firstChild.getLeft() - space;
                    right = left + space;
                    top = firstChild.getTop();
                    bottom = firstChild.getBottom();
                    canvas.drawRect(left, top, right, bottom, divider);
                }

                if (showTop) {
                    if (showLeft) {
                        left = firstChild.getLeft() - space;
                    } else {
                        left = firstChild.getLeft();
                    }
                    if (showRight) {
                        right = lastChild.getRight() + space;
                    } else {
                        right = lastChild.getRight();
                    }
                    top = firstChild.getTop() - space;
                    bottom = top + space;
                    canvas.drawRect(left, top, right, bottom, divider);
                }

                if (showRight) {
                    left = lastChild.getRight();
                    right = left + space;
                    top = lastChild.getTop();
                    bottom = lastChild.getBottom();
                    canvas.drawRect(left, top, right, bottom, divider);
                }

                if (showBottom) {
                    if (showLeft) {
                        left = firstChild.getLeft() - space;
                    } else {
                        left = firstChild.getLeft();
                    }
                    if (showRight) {
                        right = lastChild.getRight() + space;
                    } else {
                        right = lastChild.getRight();
                    }
                    top = firstChild.getBottom();
                    bottom = top + space;
                    canvas.drawRect(left, top, right, bottom, divider);
                }

                for (int i = 0; i < itemCount - 1; i++) {
                    View child = parent.getChildAt(i);
                    left = child.getRight();
                    right = left + space;
                    top = child.getTop();
                    bottom = child.getBottom();
                    canvas.drawRect(left, top, right, bottom, divider);
                }

            } else if (row > 1) {
                for (int i = 0; i < childCount; i++) {
                    View child = parent.getChildAt(i);
                    int childPosition = parent.getChildLayoutPosition(child);

                    if (showLeft && childPosition % spanCount == 0) {
                        left = child.getLeft() - space;
                        right = left + space;
                        top = child.getTop();
                        if (childPosition / spanCount == row - 1) {
                            bottom = child.getBottom();
                        } else {
                            bottom = child.getBottom() + space;
                        }
                        canvas.drawRect(left, top, right, bottom, divider);
                    }

                    if (showRight && childPosition % spanCount == spanCount - 1) {
                        left = child.getRight();
                        right = left + space;
                        top = child.getTop();
                        if (row * spanCount == childPosition + 1) {
                            bottom = child.getBottom();
                        } else {
                            bottom = child.getBottom() + space;
                        }
                        canvas.drawRect(left, top, right, bottom, divider);
                    }

                    if (showTop && childPosition < spanCount) {
                        left = child.getLeft();
                        if (childPosition == spanCount - 1) {
                            right = child.getRight();
                        } else {
                            right = child.getRight() + space;
                        }
                        top = child.getTop() - space;
                        bottom = top + space;
                        canvas.drawRect(left, top, right, bottom, divider);
                    }

                    if (showBottom && childPosition >= (row - 1) * spanCount) {
                        left = child.getLeft();
                        if ((childPosition + 1) % spanCount == 0) {
                            right = child.getRight();
                        } else {
                            right = child.getRight() + space;
                        }
                        top = child.getBottom();
                        bottom = top + space;
                        canvas.drawRect(left, top, right, bottom, divider);
                    }

                    if (showLeft && showTop && childPosition == 0) {
                        left = child.getLeft() - space;
                        right = left + space;
                        top = child.getTop() - space;
                        bottom = top + space;
                        canvas.drawRect(left, top, right, bottom, divider);
                    }

                    if (showTop && showRight && childPosition == spanCount - 1) {
                        left = child.getRight();
                        right = left + space;
                        top = child.getTop() - space;
                        bottom = top + space;
                        canvas.drawRect(left, top, right, bottom, divider);
                    }

                    if (showLeft && showBottom && (row - 1) * spanCount == childPosition) {
                        left = child.getLeft() - space;
                        right = left + space;
                        top = child.getBottom();
                        bottom = top + space;
                        canvas.drawRect(left, top, right, bottom, divider);
                    }

                    if (showRight && showBottom && itemCount % spanCount == 0 && childPosition == itemCount - 1) {
                        left = child.getRight();
                        right = left + space;
                        top = child.getBottom();
                        bottom = top + space;
                        canvas.drawRect(left, top, right, bottom, divider);
                    }

                    if (childPosition % spanCount != spanCount - 1) {
                        left = child.getRight();
                        right = left + space;
                        top = child.getTop();
                        bottom = child.getBottom();
                        canvas.drawRect(left, top, right, bottom, divider);
                    }
                    if (childPosition < (row - 1) * spanCount) {
                        left = child.getLeft();
                        if (childPosition % spanCount == spanCount - 1) {
                            right = child.getRight();
                        } else {
                            right = child.getRight() + space;
                        }
                        top = child.getBottom();
                        bottom = top + space;
                        canvas.drawRect(left, top, right, bottom, divider);
                    }

                }
            }

        } else {

            if (row == 1) {
                for (int i = 0; i < childCount; i++) {
                    View child = parent.getChildAt(i);
                    if (remainder == 0 || showLastColumn || (i != childCount - 1)) {
                        left = child.getRight();
                        right = left + space;
                        top = child.getTop() + paddingTop;
                        bottom = child.getBottom() - paddingBottom;
                        canvas.drawRect(left, top, right, bottom, divider);
                    }
                }
            } else if (row > 1) {
                for (int i = 0; i < childCount; i++) {
                    View child = parent.getChildAt(i);
                    int childPosition = parent.getChildLayoutPosition(child);
                    // 竖线
                    if (childPosition % spanCount != spanCount - 1) {
                        if (childPosition < spanCount) {
                            // 第一行
                            top = child.getTop() + paddingTop;
                            bottom = child.getBottom();
                            left = child.getRight();
                            right = left + space;
                            canvas.drawRect(left, top, right, bottom, divider);

                        } else if (childPosition >= (row - 1) * spanCount) {
                            // 最后一行
                            top = child.getTop();
                            bottom = child.getBottom() - paddingBottom;
                            left = child.getRight();
                            right = left + space;
                            if (showLastColumn || childPosition != itemCount - 1 || remainder == 0) {
                                canvas.drawRect(left, top, right, bottom, divider);
                            }
                        } else {
                            top = child.getTop();
                            bottom = child.getBottom();
                            left = child.getRight();
                            right = left + space;
                            canvas.drawRect(left, top, right, bottom, divider);
                        }
                    }

                    //横线
                    if (childPosition < (row - 1) * spanCount) {
                        if (childPosition % spanCount == 0) {
                            // 第一竖排
                            left = child.getLeft() + paddingLeft;
                            right = child.getRight() + space;
                        } else if (childPosition % spanCount == spanCount - 1) {
                            // 最后一竖排
                            left = child.getLeft();
                            right = child.getRight() - paddingRight;
                        } else {
                            left = child.getLeft();
                            right = child.getRight() + space;
                        }
                        top = child.getBottom();
                        bottom = top + space;
                        canvas.drawRect(left, top, right, bottom, divider);
                    }
                }
            }
        }

    }

    /**
     * 水平布局分割线
     *
     * @param canvas
     * @param parent
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            float left, top, right, bottom;
            int childLayoutPosition = parent.getChildLayoutPosition(child);

            if (!(childLayoutPosition == 0 && !showFirst)) {
                left = child.getLeft() - space;
                right = left + space;
                top = parent.getPaddingTop() + paddingTop;
                bottom = parent.getHeight() - parent.getPaddingBottom() - paddingBottom;
                canvas.drawRect(left, top, right, bottom, divider);
            }

            if (childLayoutPosition == state.getItemCount() - 1 && showLast) {
                left = child.getRight();
                right = left + space;
                top = parent.getPaddingTop() + paddingTop;
                bottom = parent.getHeight() - parent.getPaddingBottom() - paddingBottom;
                canvas.drawRect(left, top, right, bottom, divider);
            }

        }
    }

    /**
     * 垂直布局的分割线
     *
     * @param canvas
     * @param parent
     */
    private void drawVertical(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {

            View child = parent.getChildAt(i);
            int childLayoutPosition = parent.getChildLayoutPosition(child);
            float left, top, right, bottom;

            //判断当前item的position是否是第一个和创建时是否需要展示第一条item上面的分割线
            //除了第一条且设置了不展示第一条分割线的情况下，都画分割线
            if (!(childLayoutPosition == 0 && !showFirst)) {
                top = child.getTop() - space;
                left = parent.getPaddingLeft() + paddingLeft;
                right = parent.getWidth() - parent.getPaddingRight() - paddingRight;
                bottom = top + space;
                canvas.drawRect(left, top, right, bottom, divider);
            }
            //在最后一天item的情况下判断是否设置了要展示分割线
            if (childLayoutPosition == state.getItemCount() - 1 && showLast) {
                top = child.getBottom();
                left = parent.getPaddingLeft() + paddingLeft;
                right = parent.getWidth() - parent.getPaddingRight() - paddingRight;
                bottom = top + space;
                canvas.drawRect(left, top, right, bottom, divider);
            }

        }
    }

    public static class Builder {
        private Context mContext;
        private Params mParams;

        public Builder(Context context) {
            mContext = context;
            mParams = new Params();
        }

        public Builder setSpace(float space) {
            mParams.space = space;
            return this;
        }

        public Builder setMod(int mod) {
            mParams.mod = mod;
            return this;
        }

        public Builder setPaddingLeft(float paddingLeft) {
            mParams.paddingLeft = paddingLeft;
            return this;
        }

        public Builder setPaddingRight(float paddingRight) {
            mParams.paddingRight = paddingRight;
            return this;
        }

        public Builder setPaddingTop(float paddingTop) {
            mParams.paddingTop = paddingTop;
            return this;
        }

        public Builder setPaddingBottom(float paddingBottom) {
            mParams.paddingBottom = paddingBottom;
            return this;
        }

        public Builder showLast(boolean isShow) {
            mParams.showLast = isShow;
            return this;
        }

        public Builder showFirst(boolean isShow) {
            mParams.showFirst = isShow;
            return this;
        }

        public Builder setColor(@ColorInt int color) {
            mParams.color = color;
            return this;
        }

        public Builder showTop(boolean isShow) {
            mParams.showTop = isShow;
            return this;
        }

        public Builder showBottom(boolean isShow) {
            mParams.showBottom = isShow;
            return this;
        }

        public Builder showLeft(boolean isShow) {
            mParams.showLeft = isShow;
            return this;
        }

        public Builder showRight(boolean isShow) {
            mParams.showRight = isShow;
            return this;
        }

        public Builder showLastColumn(boolean isShow) {
            mParams.showLastColumn = isShow;
            return this;
        }

        public CustomItemDecoration create() {
            CustomItemDecoration customItemDecoration = new CustomItemDecoration();
            customItemDecoration.setParams(mContext, mParams);
            return customItemDecoration;
        }

    }

    public static class Params {
        public float space = 10.0f;
        public int mod = VERTICAL;
        public float paddingLeft = 0f;
        public float paddingRight = 0f;
        public float paddingTop = 0f;
        public float paddingBottom = 0f;
        public boolean showLast = false;
        public boolean showFirst = false;
        public boolean showTop = false;
        public boolean showBottom = false;
        public boolean showLeft = false;
        public boolean showRight = false;
        public boolean showLastColumn = true;
        public int color = android.R.color.holo_red_dark;
    }
}
