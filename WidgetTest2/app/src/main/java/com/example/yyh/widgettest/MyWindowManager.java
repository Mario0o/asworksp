package com.example.yyh.widgettest;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by yyh on 2016/3/17.
 */
public class MyWindowManager {
    //小悬浮窗的View实例
    private static FloatWindowSmallView smallView;
    //大悬浮窗的View实例
    private static FloatWindowBigView bigView;


    //小悬浮窗的参数
    private static WindowManager.LayoutParams smallWindowParams;


    //大悬浮窗的参数

    private static WindowManager.LayoutParams bigWindowParams;


    //用于控制在屏幕上添加或移除悬浮窗

    private static WindowManager windowManager;



    //用于获取手机可用内存。

    private static ActivityManager activityManager;

    /**
     * 创建一个小的悬浮窗。初始位置为屏幕的右部中间位置
     * @param context
     */
    public static void createSmallWindow(Context context){
        Log.i("MyWindowManager", "建立small");
        WindowManager windowManager = getWindowManager(context);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        //当小悬浮窗不在的时候
        if(smallView==null){
            smallView = new FloatWindowSmallView(context);
            if(smallWindowParams==null){
                smallWindowParams = new WindowManager.LayoutParams();
                smallWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                smallWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                smallWindowParams.gravity = Gravity.LEFT|Gravity.TOP;
                smallWindowParams.width = FloatWindowSmallView.viewWidth;
                smallWindowParams.height =FloatWindowSmallView.viewHeight;
                smallWindowParams.x = screenWidth;
                smallWindowParams.y = screenHeight/2;

            }
            smallView.setParams(smallWindowParams);
            windowManager.addView(smallView,smallWindowParams);
        }






    }

    /**
     * 将小悬浮窗从屏幕移除
     */
    public static void removeSmallWindow(Context context){
        if(smallView!=null){
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(smallView);
            smallView = null;
        }
    }

    /**
     * 创建一个大的悬浮窗，位置在屏幕中间
     * @param context
     */
    public static void createBigWindow(Context context){
        WindowManager windowManager = getWindowManager(context);
        DisplayMetrics metrics  = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        if (bigView==null){
            bigView = new FloatWindowBigView(context);

        }
        if(bigWindowParams==null){
            bigWindowParams = new WindowManager.LayoutParams();
            bigWindowParams.x = screenWidth/2-FloatWindowBigView.viewWidth/2;
            bigWindowParams.y = screenHeight/2-FloatWindowBigView.viewHeight/2;
            bigWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            bigWindowParams.format = PixelFormat.RGBA_8888;
            bigWindowParams.gravity = Gravity.LEFT|Gravity.TOP;
            bigWindowParams.width = FloatWindowBigView.viewWidth;
            bigWindowParams.height = FloatWindowBigView.viewHeight;


        }
        windowManager.addView(bigView,bigWindowParams);



    }

    /**
     * 移除大悬浮窗
     * @param context
     */
    public static void removeBigWindow(Context context){
        if(bigView!=null){
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(bigView);
            bigView = null;
        }

    }

    /**
     * 更新小悬浮窗上的数据，显示内存使用的百分比
     * @param context
     */
    public static void updateUsedPercent(Context context){
        if(smallView!=null){
            TextView percentView = (TextView) smallView.findViewById(R.id.percent);
            percentView.setText(getUsedPercentValue(context));
        }


    }

    /**
     * 是否有悬浮窗显示在屏幕上
     * @return
     */
    public static boolean isWindowShowing(){

        Log.i("isWindowShowing","是否有悬浮窗"+(smallView!=null||bigView!=null));
        return smallView!=null||bigView!=null;

    }


    /**如果windowManager还未创建，则创建一个新的windowMananger,否则返回当前已创建的windowManager.
     *
     * @param context
     * @return
     */

    private static WindowManager getWindowManager(Context context) {
        if (windowManager==null){
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        }
        return windowManager;






    }

    /**
     * 如果activityManager还未创建，则创建一个新的ActivityManager返回，否则返回当前已创建的activityManager.
     * @param context
     * @return activityManager 实例，用于获取手机可用内存
     */
    private static ActivityManager getActivityManager(Context context){
        if (activityManager==null){
            activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);


        }
        return activityManager;


    }

    public static String getUsedPercentValue(Context context){
        Log.i("getUsedPercentValue","更新内存使用情况");
        //String dir = "/proc/meminfo";
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr,2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));

            br.close();

            long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll("\\D+",""));
            long availableSize = getAvailableMemory(context)/1024;

           // int percent = (int)((totalMemorySize-availableSize)/(float)(totalMemorySize*100));
            int percent = (int) ((totalMemorySize - availableSize) / (float) totalMemorySize * 100);
            return percent+"%";


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "悬浮窗";
    }

    /**
     * 获取当前可用内存，返回数据以字节为单位。
     * @param context
     * @return
     */
    private static long getAvailableMemory(Context context) {

        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        getActivityManager(context).getMemoryInfo(mi);
        return mi.availMem;
    }


}
