package com.example.yyh.pageviewtest;

import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by yyh on 2015/10/27.
 */
public class ThreeActivity extends AppCompatActivity {
    private ArrayList<View> viewArrayList;
    private ArrayList<String> titleArrayList;
    private ViewPager viewPager;
    private MyAdapter2 adapter2;


    @Override
    public void onCreate(Bundle savedInstanceStatete) {
        super.onCreate(savedInstanceStatete);
        setContentView(R.layout.activity_three);
        viewPager = (ViewPager) findViewById(R.id.vpager_three);

        LayoutInflater inflater = getLayoutInflater();
        viewArrayList = new ArrayList<View>();
        viewArrayList.add(inflater.inflate(R.layout.view_one,null,false));
        viewArrayList.add(inflater.inflate(R.layout.view_two,null,false));
        viewArrayList.add(inflater.inflate(R.layout.view_three,null,false));

        titleArrayList = new ArrayList<String>();
        titleArrayList.add("第一页");
        titleArrayList.add("第二页");
        titleArrayList.add("第三页");
        adapter2 = new MyAdapter2(viewArrayList,titleArrayList);
        viewPager.setAdapter(adapter2);


    }
}
