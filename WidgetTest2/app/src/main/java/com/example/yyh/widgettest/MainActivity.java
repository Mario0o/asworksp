package com.example.yyh.widgettest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import static com.example.yyh.widgettest.R.id.start_float_window;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button startFloatWindow = (Button) findViewById(start_float_window);
        startFloatWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FloatWindowService.class);
                startService(intent);
                Log.i("MainActivity","要进入Service");
                finish();
            }
        });

    }


}
