package com.example.yyh.floatwindowdemo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by yyh on 2016/3/21.
 */
public class FloatBigWindow extends LinearLayout {

    private static int viewWidth;
    private static int viewHeigth;












    public FloatBigWindow(final Context context) {


        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_bigwindow,this);
        View view = findViewById(R.id.layout_big);
        viewWidth =view.getLayoutParams().width;
        viewHeigth = view.getLayoutParams().height;
        final Button close = (Button) findViewById(R.id.layout_close);
        Button back = (Button) findViewById(R.id.layout_back);
        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MyWindowManager.removeBigWindow(context);
                MyWindowManager.removeSmallWindow(context);
                Intent intent = new Intent(getContext(),FloatService.class);
                context.stopService(intent);
            }
        });
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MyWindowManager.removeBigWindow(context);
                MyWindowManager.createSmallWindow(context);
            }
        });



    }


}
