package com.example.yyh.widgettest;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import android.app.ActivityManager.RunningTaskInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by yyh on 2016/3/17.
 */
public class FloatWindowService extends Service {



    /**
     *
     * @param intent
     * @return
     *
     * 用于在线程中创建或者移除悬浮窗。
     */
    private Handler handler = new Handler();

    /**
     * 定时器，定时进行检测当前应该创建还是移除悬浮窗。
     */

    private Timer timer;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Service","onStartCommand");

        //开启定时器，每隔0.5秒刷新一次。
        if (timer==null){
            timer = new Timer();
            timer.scheduleAtFixedRate(new RefreshTask(),0,500);


        }
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer= null;
    }

    private class RefreshTask extends TimerTask {
        @Override
        public void run() {
            //当前界面是桌面，且没有悬浮窗显示，则创建悬浮窗。
            //注意小细节， 这里 写成了MyWindowManager.isWindowShowing() 没注意。 浪费了几个小时的时间
            if (isHome()&&!MyWindowManager.isWindowShowing()){
                Log.i("Service","要进入创建small");
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        Log.i("Service","创建small");
                        MyWindowManager.createSmallWindow(getApplicationContext());
                    }
                });



            }
            //当前界面不是桌面且有悬浮窗显示，则移除悬浮窗。
            else if (!isHome()&&MyWindowManager.isWindowShowing()){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                   MyWindowManager.removeSmallWindow(getApplicationContext());
                        MyWindowManager.removeBigWindow(getApplicationContext());
                    }
                });



            }
            //如果现在在桌面，且有悬浮窗，则更新悬浮窗。
            else if(isHome()&&MyWindowManager.isWindowShowing()){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                   MyWindowManager.updateUsedPercent(getApplicationContext());
                    }
                });

            }
        }
    }

    private boolean isHome() {
        Log.i("isHome","判断是否在桌面");
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> rti= activityManager.getRunningTasks(1);
        boolean ishome = getHomes().contains(rti.get(0).topActivity.getPackageName());
       // System.out.print(ishome);
       // String str = String.valueOf(ishome);
        Log.i("isHome",""+ishome);
        return ishome;




    }

    private List<String>  getHomes() {
        Log.i("getHomes","geHomes");
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
        for(ResolveInfo ri :resolveInfos){
            names.add(ri.activityInfo.packageName);

        }

        return names;
    }
}
