package com.example.yyh.widgettest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by yyh on 2016/3/17.
 */
public class FloatWindowBigView extends LinearLayout{

    /**
     * 记录大悬浮窗的宽度
     */
    public static int viewWidth;

    /**
     * 记录大悬浮窗的高度
     */

    public static int viewHeight;



    public FloatWindowBigView(final Context context) {


        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_big,this);
        View view = findViewById(R.id.layout_big);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;

        final Button close = (Button) findViewById(R.id.close);
        Button back = (Button) findViewById(R.id.back);

        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击关闭悬浮窗，移除所有悬浮窗，并停止Service;
                MyWindowManager.removeBigWindow(getContext());
                MyWindowManager.removeSmallWindow(getContext());
                Intent intent = new Intent(getContext(),FloatWindowService.class);
                context.stopService(intent);
            }
        });

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击返回，移除大悬浮窗，创建小悬浮窗
                MyWindowManager.removeBigWindow(getContext());
                MyWindowManager.createSmallWindow(getContext());

            }
        });

    }
}
