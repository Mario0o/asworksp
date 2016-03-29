package com.example.yyh.widgettest;

import android.app.Service;
import android.appwidget.AppWidgetManager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yyh on 2016/3/10.
 */
public class TimerClass extends Service {
    private Timer timer;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");





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

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer =null;
    }

    private void updateViews(){
        //PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);

        String time = sdf.format(new Date(System.currentTimeMillis()));
        RemoteViews rvs = new RemoteViews(getPackageName(),R.layout.new_app_widget);
        rvs.setTextViewText(R.id.appwidget_text,time);
        AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
        ComponentName cn = new ComponentName(getApplicationContext(),NewAppWidget.class);
        manager.updateAppWidget(cn, rvs);


    };
}
