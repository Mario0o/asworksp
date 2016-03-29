package com.example.yyh.floatwindowdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by yyh on 2016/3/21.
 */
public class FloatSmallWindow extends LinearLayout {

    //记录小悬浮窗的高度
    public static int viewHeigth;

    //记录小悬浮窗的宽度

    public static int viewWidth;


    //记录系统状态栏的高度

    private static int statusBarHeight;

    //用以更新管理小悬浮窗

    private WindowManager smallWindowManager;

    private WindowManager.LayoutParams smalllayoutParams;


    //记录当前手指在屏幕上的位置

    private float xInScreen;
    private float yInScreen;


    //记录手指按下时在屏幕上的位置

    private float xDownScreen;
    private float yDownScreen;

    //记录手指按下时，在view中的位置。

    private  float xDownView;
    private float yDownView;









    public FloatSmallWindow(Context context) {


        super(context);
        smallWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //加载view中的布局文件
        LayoutInflater.from(context).inflate(R.layout.layout_small,this);
        View view = findViewById(R.id.layout_small);
        //view的宽高
        viewWidth = view.getLayoutParams().width;
        viewHeigth = view.getLayoutParams().height;

        TextView tv = (TextView) findViewById(R.id.percent);
        tv.setText(MyWindowManager.getUsedPercentValue(context));





    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       switch (event.getAction()){
           case MotionEvent.ACTION_DOWN:

               //在view中的位置
               xDownView = event.getX();
               yDownView = event.getY();


               xDownScreen =event.getRawX();
               yDownScreen = event.getRawY()-getStatusBarHeight();

               xInScreen =event.getRawX();
               yInScreen = event.getRawY()-getStatusBarHeight();





               break;
           case MotionEvent.ACTION_MOVE:
               xInScreen = event.getRawX();
               yInScreen = event.getRawY()-getStatusBarHeight();


               //手指移动时更新小悬浮窗的位置

                updataViewPosition();




               break;
           case MotionEvent.ACTION_UP:
               //如果手指松开时，这两个相等，则只是触发了点击事件
               if (xDownScreen==xInScreen&&yDownScreen==yInScreen){
                   openBigWindow();


               }

               break;

       }

        return true;
    }

    public void setSmalllayoutParams(WindowManager.LayoutParams layoutParams){
        smalllayoutParams = layoutParams;

    }




    //创建大悬浮窗
    private void openBigWindow() {
        MyWindowManager.createBigWindow(getContext());
        MyWindowManager.removeSmallWindow(getContext());



    }

    /**
     * 更新小悬浮窗在屏幕中的位置
     */
    private void updataViewPosition() {
        smalllayoutParams.x = (int) (xInScreen-xDownView);
        smalllayoutParams.y = (int) (yInScreen-yDownView);
        smallWindowManager.updateViewLayout(this,smalllayoutParams);

    }
    //用以获取状态栏的高度
    private float getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }
    }

