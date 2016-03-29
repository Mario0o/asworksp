package com.example.yyh.widgettest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Created by yyh on 2016/3/17.
 */
public class FloatWindowSmallView extends LinearLayout{

    /**
     * 记录小悬浮窗的宽度
     */
    public static int viewWidth;

    /**
     * 记录小悬浮窗的高度
     */
    public static int viewHeight;


    /**
     * 记录系统状态栏的高度
     * @param context
     */
    private static int statusBarHeight;

    /**
     * 更新小悬浮窗的位置
     * @param context
     */
    private WindowManager windowManager;

    /**
     * 小悬浮窗的参数
     */
    private WindowManager.LayoutParams layoutParams;
    /**
     * 记录当前手指位置在屏幕上的横坐标位置
     */
    private float xInScreen;

    /**
     * 记录当前手指位置在屏幕上的纵坐标位置
     */
    private float yInScreen;

    /**
     * 记录手指位置在View上的横坐标
     */
    private float xInView;

    /**
     * 记录手指位置在View上的纵坐标
     */
    private float yInView;



    private float xDownInScreen;

    private float yDownInScreen;




    public FloatWindowSmallView(Context context) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.float_window_smalll,this);
        View view = findViewById(R.id.layout_small);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;

        TextView percentView = (TextView) findViewById(R.id.percent);
        percentView.setText(MyWindowManager.getUsedPercentValue(context));

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
               xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY()-getStatusBarHeight();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY()-getStatusBarHeight();


                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY()-getStatusBarHeight();

                //手指移动的时候更新小悬浮窗的位置
                updateViewPosition();





                break;
            case MotionEvent.ACTION_UP:
                if(xDownInScreen==xInScreen&&yDownInScreen==yInScreen){
                    openBigWindow();

                }


                break;
            default:
                break;

        }
        return true;
    }

    /**
     * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置
     * @param params
     */
    public void setParams (WindowManager.LayoutParams params){
        layoutParams = params;

    }

    private void openBigWindow() {
        MyWindowManager.createBigWindow(getContext());
        MyWindowManager.removeSmallWindow(getContext());

    }

    private void updateViewPosition() {
        layoutParams.x = (int)(xInScreen-xInView);
        layoutParams.y =(int)(yInScreen-yInView);
        windowManager.updateViewLayout(this, layoutParams);


    }

    private float getStatusBarHeight() {
        if (statusBarHeight==0){

            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (int) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

        }
        return statusBarHeight;
    }


}
