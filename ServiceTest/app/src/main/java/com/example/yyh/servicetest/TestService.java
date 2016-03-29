package com.example.yyh.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by yyh on 2015/11/29.
 */
public class TestService extends IntentService {
    private final String TAG = "abcefg";

    public TestService() {
        super("TestService");
    }




    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getExtras().getString("param");
        if (action.equals("s1"))
            Log.i(TAG,"启动service1");
        else if (action.equals("s2"))
            Log.i(TAG,"启动service2");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind");
        return super.onBind(intent);
    }

    @Override
    public void onCreate() {
        Log.i(TAG,"onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Log.i(TAG,"onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void setIntentRedelivery(boolean enabled) {
        Log.i(TAG,"setIntentRedelivery");
        super.setIntentRedelivery(enabled);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG,"onDestroy");
        super.onDestroy();


    }
}
