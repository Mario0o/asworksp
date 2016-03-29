package com.example.yyh.widgettest2;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.POWER_SERVICE;

/**
 * Created by yyh on 2016/3/10.
 */
public class TimerService extends Service {
   // String time ;
    private Timer timer;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH时:mm分:ss秒 E");


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {


        super.onCreate();



      timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            updateViews();
            }
        },0,1000);


    }

    private void updateViews(){

/*

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (pm.WakeLock!=pm.S)

*//*
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean screen = pm.isScreenOn();
        if (true==screen){
            time = sdf.format(new Date(System.currentTimeMillis()));

        }


*/
        String time = sdf.format(new Date(System.currentTimeMillis()));
        Log.i("时间啊。。。",time);
        RemoteViews rvs = new RemoteViews(getPackageName(),R.layout.app_widget);
        rvs.setTextViewText(R.id.appwidget_text, time);
        AppWidgetManager manager =AppWidgetManager.getInstance(getApplicationContext());
        ComponentName cn = new ComponentName(getApplicationContext(),AppWidget.class);
        manager.updateAppWidget(cn,rvs);


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        timer = null;
        //unregisterReceiver();
    }
}
