package com.atguiug.p2pinvest1020.activity.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.atguiug.p2pinvest1020.R;

/**
 * Created by 张永卫on 2017/3/13.
 */

/**
 * 自定义控件
 * 第一步 分析 继承 view / viewgroup / 其它控件
 *       一般来说一个功能的实现(一个控件)就继承view
 *       一般来说二个控件组合以上继承viewGroup
 *       对某些控件进行扩展的时候就继承该控件
 *
 */
public class MyProgress extends View {


    private Paint paint;
    private int measuredWidth;
    private int measuredHeight;
    private int roundWidth = 10;//圆环的宽度
    private int roundColor;//圆环的颜色
    private int sweepArc = 60;
    private int sweepColor = Color.RED;//进度的颜色
    private TypedArray typedArray;

    public MyProgress(Context context) {
        super(context);
        init();//当我们new一个控件的时候会调用该构造器
    }



    /**
     *
     * 1.什么是自定义属性？
     * 定义可以在布局文件的标签中使用的属性。
     * 2.为什么要自定义视图属性?
     * 这样就可以通过布局的方式给视图对象指定特定的属性值, 而不用动态的设置
     * 3.理解属性值的类型(format)
     * 1. reference 引用类型值 :@id/...
     * 2. color 颜色类型值  #ff00ff
     * 3. boolean 布尔类型值 true false
     * 4. dimension 尺寸类型值 dp / px /sp
     * 5. integer 整数类型值  weight  progress max
     * 6. float 小数类型值  0.5f
     * 7. string 字符串类型值  ""
     * 8. <enum> 枚举类型值 ：水平/垂直
     *
     * @param context
     * @param attrs
     */
    public MyProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();//在布局配置该控件的时候会调用该构造器
        //自定义属性
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.progress);

        //第一个参数获取attrs里面的配置属性名，第二个参数设置默认值
        roundColor = typedArray.getColor(R.styleable.progress_roundColor, Color.GRAY);

        sweepColor = typedArray.getColor(R.styleable.progress_sweepColor, Color.RED);

        sweepArc = typedArray.getInteger(R.styleable.progress_sweepArc, 0);

        typedArray.recycle();
    }

    /**
     *  /*
     * 进度条
     * 第一步 画圆
     * 第二步 画弧
     * 第三步 画文字
     *
     * */

    private void init() {

        //画笔
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);//设置抗锯齿
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //前两个参数是圆心坐标  第三个参数半径 第四个参数是画笔

        int cx = measuredWidth/2;

        int cy = measuredHeight/2;
        //控件的宽度 - 圆环宽度的一半
        int radius = cx -roundWidth /2;
        //设置圆环的颜色
        paint.setColor(roundColor);
        //设置圆环的宽度
        paint.setStrokeWidth(roundWidth);

        canvas.drawCircle(cx,cy,radius,paint);

        /**
         * 画弧
         */
        //存放了圆环中间矩形的左上顶点和右下顶点
        RectF rectF = new RectF(roundWidth/2,roundWidth/2,
                measuredWidth-roundWidth/2,measuredHeight-roundWidth/2);

        paint.setColor(sweepColor);

        //第二个参数是起始角 第三个参数多少度
        canvas.drawArc(rectF,0,sweepArc,false,paint);

        /**
         * 画文本
         */
        String text  = sweepArc*360/100+"%";

        Rect rect = new Rect();

        //第一个参数是文本 第二个到第三个参数是文字的截取的长度，第四个参数是存放测量结果的容器

        paint.setColor(Color.BLUE);

        paint.setStrokeWidth(0);

        paint.setTextSize(30);

        paint.getTextBounds(text,0,text.length(),rect);

        float tx = measuredWidth/2-rect.width()/2;

        float ty = measuredHeight/2-rect.height()/2;

        canvas.drawText(text,tx,ty,paint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //宽
        measuredWidth = getMeasuredWidth();

        //高
        measuredHeight = getMeasuredHeight();

    }

    public void setProgress(int progress){

        sweepArc = progress;
        /**
          invalidate 是在主线程强制刷新
       *  postinvalidate 是在分线程强制刷新
       * */
        postInvalidate();
    }
}
