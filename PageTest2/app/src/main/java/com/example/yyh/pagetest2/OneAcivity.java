package com.example.yyh.pagetest2;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by yyh on 2015/10/27.
 */
public class OneAcivity extends AppCompatActivity {
    private ArrayList<View> viewArrayList;
    private ViewPager viewPager_one;
    private MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        viewPager_one = (ViewPager) findViewById(R.id.vpager_one);
        viewArrayList = new ArrayList<View>();
        LayoutInflater inflater = getLayoutInflater();
        viewArrayList.add(inflater.inflate(R.layout.view_one,null,false));
        viewArrayList.add(inflater.inflate(R.layout.view_two,null,false));
        viewArrayList.add(inflater.inflate(R.layout.view_three,null,false));
        adapter = new MyAdapter(viewArrayList);
        viewPager_one.setAdapter(adapter);

    }
}
