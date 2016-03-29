package com.example.yyh.wedgittest3;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yyh on 2016/3/18.
 */
public class MyService extends Service{
    private Button btnView;
   private static WindowManager windowManager;
   private static WindowManager.LayoutParams layoutParams;
    boolean isAdded = false ;
    private ActivityManager activityManager;
    private static final int HANDLE_CHECK_ACTIVITY = 200;

    public static final String OPERATION="operation";
    public static final int OPERATION_SHOW =100;
    public static final int OPERATION_HIDE=101;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.i("handler中","进入handler");
            Log.i("handler中","handler判断是否在桌面aaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            switch (msg.what){
                case HANDLE_CHECK_ACTIVITY:
                    if (isHome()){
                        Log.i("handler中","handler判断是否在桌面");
                        Log.i("handler中","进入isAdded");
                        if(!isAdded){
                            windowManager.addView(btnView,layoutParams);
                            isAdded =true;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i =0;i<10;i++){
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        Message m = new Message();
                                        m.what = 2;
                                        handler.sendMessage(m);
                                    }
                                }
                            }).start();
                        }

                    }
                    //又是小细节。 else 放到if里了， 这个问题浪费了一上午时间。以后要注意。


                    else {
                        Log.i("handler中","handler判断不在桌面");
                        if (isAdded){
                            windowManager.removeView(btnView);
                            isAdded = false;

                        }


                    }
                    handler.sendEmptyMessageDelayed(HANDLE_CHECK_ACTIVITY,0);
                    break;


            }
        }
    };




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        createWindowView();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int operation = intent.getIntExtra(OPERATION, OPERATION_SHOW);
        switch(operation) {
            case OPERATION_SHOW:
                handler.removeMessages(HANDLE_CHECK_ACTIVITY);
                handler.sendEmptyMessage(HANDLE_CHECK_ACTIVITY);
               /* SimpleDateFormat sdf = new SimpleDateFormat("HH时mm分ss秒");
                String str = sdf.format(new Date());
                btnView.setText(str);*/
                break;
            case OPERATION_HIDE:
                handler.removeMessages(HANDLE_CHECK_ACTIVITY);
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }



    //创建悬浮窗
    private void createWindowView(){
        btnView = new Button(getApplicationContext());

       /* SimpleDateFormat sdf = new SimpleDateFormat("HH时mm分ss秒");
        String str = sdf.format(new Date());
        btnView.setText(str);
*/

        btnView.setBackgroundResource(R.mipmap.ic_launcher);
        windowManager = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();

        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL| WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN;


        layoutParams.format = PixelFormat.RGBA_8888;


        layoutParams.width = 200;

        layoutParams.height = 200;

        layoutParams.gravity = Gravity.LEFT;
        layoutParams.x = 200;
        layoutParams.y = 200;

        btnView.setOnTouchListener(new View.OnTouchListener() {
            int lastX, lastY;
            int paramX,paramY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //记录手指按下位置
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        paramX = layoutParams.x;
                        paramY = layoutParams.y;

                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) (event.getRawX()-lastX);
                        int dy = (int) (event.getRawY()-lastY);
                        layoutParams.x = paramX+dx;
                        layoutParams.y = paramY+dy;
                        windowManager.updateViewLayout(btnView,layoutParams);



                        break;
                    default:
                        break;


                }


                return true;
            }
        });
        windowManager.addView(btnView, layoutParams);
        isAdded= true;





    }

    /**
     * 判断是否在桌面
     * @return
     */
    public boolean isHome(){
        if(activityManager==null){
            activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        }
        List<ActivityManager.RunningTaskInfo> rti = activityManager.getRunningTasks(1);
        Log.i("isHome",""+getHomes().contains(rti.get(0).topActivity.getPackageName()));
        return getHomes().contains(rti.get(0).topActivity.getPackageName());



    }
   //桌面应用集合
    private List<String> getHomes(){
        List<String> names= new ArrayList<>();
        PackageManager packageManager = this.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
        for(ResolveInfo ri:resolveInfos){
            names.add(ri.activityInfo.packageName);
        }

        return names;
    }
}
