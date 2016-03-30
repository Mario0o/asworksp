package com.example.yyh.imageview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 这个例子不好，可能会出现OOM
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
