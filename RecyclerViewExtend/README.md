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
