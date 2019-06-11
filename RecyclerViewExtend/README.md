# RecyclerView分割线CustomItemDecoration
1. 目前只涉及到GridLayout和LinearLayout布局
2. 通过Builder构造ItemDecoration对象
3. gridLayout下边界和padding同时设置时，边界只有一条设置为true，那么padding失效

* 主要属性及方法

属性名|方法|作用
---|---|---
mod|setMod|哪种布局模式取值范围VERTICAL、HORIZONTAL、GRID，对应layoutManager
color|setColor|分割线的颜色，@ColorInt 类型
space|setSpace|分割线的宽度，像素
paddingLeft|setPaddingLeft|分割居左内边距
paddingTop|setPaddingTop|分割居上内边距
paddingBottom|setPaddingBottom|分割居下内边距
paddingTop|setPaddingTop|分割居右内边距
showLast|showLast|VERTICAL、HORIZONTAL模式下是否展示最后一条item下面一条分割线
showFirst|showFirst|VERTICAL、HORIZONTAL模式下是否展示第一条item上面一条分割线
showTop|showTop|GRID模式下显示最上面的边界分割线
showBottom|showBottom|GRID模式下显示最下面的边界分割线
showLeft|showLeft|GRID模式下显示最左面的边界分割线
showRight|showRight|GRID模式下显示最右面的边界分割线
showLastColumn|showLastColumn|GRID模式下是否展示最后一个item右边的分割线，只有当存在多行item时才有效



````
CustomItemDecoration horizontalDecoration = new CustomItemDecoration
                .Builder(this)
                .showFirst(true)
                .showLast(true)
                .setPaddingTop(10)
                .setPaddingBottom(10)
                .setMod(CustomItemDecoration.HORIZONTAL)
                .create();
````


# RecyclerView item动画
目前实现item动画有三种方式，分别是
1. 在RecyclerView.Adapter中的onBindViewHolder中实现动画，加载更多时的动画
2. 自定义ItemAnimator，这个只在item，添加，删除是起作用
3. LayoutAnimation，打开页面展示recyclerview时的item动画

### LayoutAnimation
layoutAnimation是最容易实现的，在anim文件中创建要实现的动画xml，设置在layoutAnimation的xml中，然后布局文件中的recyclerview中设置layoutAnimation属性，或者在Java文件中设置属性

### onBindViewHolder
在创建完成的adapter外面包一层AnimatorAdapter，实现setObjectAnimator方法，在该方法中实现需要的动画
````
mRecyclerView.setAdapter(new AnimatorAdapter(mRecyclerAdapter) {
            @Override
            public ObjectAnimator setObjectAnimator(RecyclerView.ViewHolder viewHolder) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(viewHolder.itemView, "translationX",
                        -viewHolder.itemView.getRootView().getWidth() * 2, -1);
                objectAnimator.setDuration(2000)
                        .setInterpolator(new OvershootInterpolator(0));
                return objectAnimator;
            }
        });
````

### ItemAnimator
继承BaseAnimator,实现抽象方法
1. animateRemoveImpl 移除item实现动画
2. animateAddImpl 添加item实现动画
3. preAnimateRemove 移除item之前操作
4. preAnimateAdd 添加item之前操作

在调用notifyItemInserted和notifyItemRemoved时起作用

````

    public SlideInLeftAnimator(Interpolator interpolator) {
        mInterpolator = interpolator;
    }

    /**
     * 动画移除之前
     *
     * @param viewHolder
     */
    @Override
    protected void preAnimateRemove(RecyclerView.ViewHolder viewHolder) {

    }

    /**
     * 动画添加之前
     *
     * @param viewHolder
     */
    @Override
    protected void preAnimateAdd(RecyclerView.ViewHolder viewHolder) {
        View view=viewHolder.itemView;
        view.setTranslationX(-view.getRootView().getMeasuredWidth() );
    }

    /**
     * 移除item动画实现
     *
     * @param viewHolder
     */
    @Override
    protected void animateRemoveImpl(RecyclerView.ViewHolder viewHolder) {
        View view = viewHolder.itemView;
        ViewPropertyAnimator animate = view.animate();
        animate.translationX(-view.getRootView().getMeasuredWidth())
                .setDuration(getRemoveDuration())
                .setListener(new DefaultRemoveAnimatorListenerAdpater(viewHolder,animate))
                .start();
    }

    /**
     * 添加item动画实现
     *
     * @param viewHolder
     */
    @Override
    protected void animateAddImpl(RecyclerView.ViewHolder viewHolder) {
        View view = viewHolder.itemView;
        ViewPropertyAnimator animate = view.animate();
        animate.translationX(0)
                .setDuration(getAddDuration())
                .setInterpolator(mInterpolator)
                .setListener(new DefaultAddAnimatorListenerAdpater(viewHolder, animate))
                .start();
    }
````
