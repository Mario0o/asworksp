package com.example.yyh.floatwindowdemo;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * Created by yyh on 2016/3/21.
 */
public class MyWindowManager {
    //悬浮窗管理类
    //大悬浮窗
    private static FloatBigWindow bigWindow;

    //小悬浮窗
    private  static FloatSmallWindow smallWindow;
    //大悬浮窗View的参数
    private static WindowManager.LayoutParams bigLayoutParams;

    //小悬浮窗VIew的参数

    private static WindowManager.LayoutParams smallLayoutParams;


    //悬浮窗管理
    private static WindowManager windowManager;

    //用以获取手机可用内存

    private static ActivityManager activityManager;


    /**
     * 创建一个小的悬浮窗
     * @param context
     */

    public static void createSmallWindow(Context context){
        WindowManager manager = getWindowManager(context);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(displayMetrics);

        int screenwidth =displayMetrics.widthPixels;
        int screenheight = displayMetrics.heightPixels;
        //当小悬浮窗不存在的时候，创建之
        if (smallWindow==null){
            smallWindow = new FloatSmallWindow(context);

        }
        //配置小悬浮窗的属性。
        if (smallLayoutParams==null){
            smallLayoutParams = new WindowManager.LayoutParams();

            smallLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            smallLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            smallLayoutParams.gravity = Gravity.LEFT|Gravity.TOP;
            //设置小悬浮窗View的宽、高。




            //设置小悬浮窗的位置。







        }
        //将属性设置
        smallWindow.setLayoutParams(smallLayoutParams);
        //加载view.
        windowManager.addView(smallWindow,smallLayoutParams);






    }

    /**
     * 移除小悬浮窗
     * @param context
     */
    public static void removeSmallWindow(Context context){

        if (smallWindow!=null){
            //移除view
            //1.先得到windowMananger，
            //2.用windowmanager管理view
            WindowManager smallmanager = getWindowManager(context);
           smallmanager.removeView(smallWindow);

            smallWindow = null;


        }


    }

    /**
     * 创建大悬浮窗
     * @param context
     */

    public static void createBigWindow(Context context){
        WindowManager windowManager = getWindowManager(context);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        //得到屏幕的宽高
        int screenWidth = displayMetrics.widthPixels;
        int screenHeigth = displayMetrics.densityDpi;

        //bigWindow没有，则创建

        if (bigWindow==null){
            bigWindow = new FloatBigWindow(context);
        }
        if (bigLayoutParams==null){
            bigLayoutParams = new WindowManager.LayoutParams();
            bigLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            bigLayoutParams.format = PixelFormat.RGBA_8888;
            bigLayoutParams.gravity = Gravity.LEFT|Gravity.TOP;

            //设置bigView的宽高


            //设置bigView的位置，

        }
        windowManager.addView(bigWindow,bigLayoutParams);





    }


    //移除大悬浮窗
    public static void removeBigWindow(Context context){
        if (bigWindow!=null){
            WindowManager bigmanager = getWindowManager(context);
            bigmanager.removeView(bigWindow);
            bigWindow = null;

        }

    }


    /**
     * 更新smallWindow上的数据
     * @param context
     */
    public static void updataUsedPercent(Context context){
        if (smallWindow!=null){
            TextView textView = (TextView) smallWindow.findViewById(R.id.percent);

            textView.setText(getUsedPercentValue(context));

        }


    }

    /**
     * 是否有悬浮窗显示在屏幕上
     * @return
     */
    public static boolean isWindowShowing(){

        return smallWindow!=null||bigWindow!=null;

    }







    public static  String getUsedPercentValue(Context context) {
        Log.i("getUsedPercentValue", "更新内存使用情况");
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
     * 获取当前可用内存,返回数据以字节为单位
     *
     * @param context
     * @return
     */
    private static long getAvailableMemory(Context context) {
     ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        getActivityManager(context).getMemoryInfo(mi);


        return mi.availMem;
    }


    /**
     * 如果windowmanager没有创建，则创建一个新的windowManager,否则，返回当前已经创建的windowManager.
     * @param context
     * @return
     */
    public static WindowManager getWindowManager(Context context) {
        if (windowManager==null){
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        }




        return windowManager;
    }



    /**
     *如果activitymananger未创建，则创建一个，否则，   返回当前已经创建的activityManager.
     * @param context
     * @return
     */
    public static ActivityManager getActivityManager(Context context){
        if (activityManager==null){
            activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);


        }

        return activityManager;

    }



}
