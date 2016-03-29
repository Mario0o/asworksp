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
public class ThreeActivity extends AppCompatActivity {
    private ArrayList<View> viewArrayList;
    private ArrayList<String> titleArrayList;
    private ViewPager viewPager_three;
    private MyAdapter2 adapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        viewPager_three = (ViewPager) findViewById(R.id.vpager_three);
        viewArrayList = new ArrayList<View>();
        LayoutInflater inflater = getLayoutInflater();
        viewArrayList.add(inflater.inflate(R.layout.view_one,null,false));
        viewArrayList.add(inflater.inflate(R.layout.view_two,null,false));
        viewArrayList.add(inflater.inflate(R.layout.view_three,null,false));
        titleArrayList = new ArrayList<String >();
        titleArrayList.add("第一页");
        titleArrayList.add("第二页");
        titleArrayList.add("第三页");

        adapter2 = new MyAdapter2(viewArrayList,titleArrayList);
        viewPager_three.setAdapter(adapter2);



    }
}
