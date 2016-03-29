package com.example.yyh.floatwindowdemo;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yyh on 2016/3/21.
 */
public class FloatService extends Service {
    //用以在线程中移除service;
    private Handler handler = new Handler();
        //定时器，定时检测

    private Timer timer ;








    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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
        timer=null;
    }

    private class RefreshTask extends TimerTask {
        @Override
        public void run() {
            if (isHome()&&!MyWindowManager.isWindowShowing()){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                    MyWindowManager.createSmallWindow(getApplicationContext());
                    }
                });


            }
            else if (!isHome()&&MyWindowManager.isWindowShowing()){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyWindowManager.removeSmallWindow(getApplicationContext());
                        MyWindowManager.removeBigWindow(getApplicationContext());
                    }
                });

            }
            else if (isHome()&&MyWindowManager.isWindowShowing()){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                   MyWindowManager.updataUsedPercent(getApplicationContext());
                    }
                });

            }

        }
    }

    private boolean isHome() {
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
        return getHomes().contains(rti.get(0).topActivity.getPackageName());
    }

    private List<String> getHomes() {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : resolveInfo) {
            names.add(ri.activityInfo.packageName);
        }
        return names;
    }
}
