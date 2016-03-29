package com.example.yyh.servicetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent it1 = new Intent("com.example.yyh.servicetest");
        it1.setPackage(getPackageName());
        Bundle bundle = new Bundle();
        bundle.putString("param", "s1");
        it1.putExtras(bundle);
        Intent it2 = new Intent("com.example.yyh.servicetest");
        it2.setPackage(getPackageName());
        Bundle bundle2 = new Bundle();
        bundle2.putString("param","s2");
        it1.putExtras(bundle2);



        startService(it1);
        startService(it2);
    }


}
