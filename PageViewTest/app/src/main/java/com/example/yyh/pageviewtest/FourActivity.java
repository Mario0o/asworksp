package com.example.yyh.pageviewtest;

import android.graphics.BitmapFactory;


import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yyh on 2015/10/27.
 */
public class FourActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private ImageView imageView;
    private TextView tv_one,tv_two,tv_three;
    private ArrayList<View> viewArrayList;
    private int offset = 0;//移动条图片的偏移量
    private int curIndex = 0;//当前页的 编号
    private  int bmpWidth;//移动条图片的长度
    private int one = 0;//移动一页的偏移量
    private int two = 0;//移动两页的偏移量


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        setupViews();
    }

    private void setupViews() {
        viewPager = (ViewPager) findViewById(R.id.vpager_four);
        tv_one = (TextView) findViewById(R.id.tv_one);
        tv_two = (TextView) findViewById(R.id.tv_two);
        tv_three = (TextView) findViewById(R.id.tv_three);
        imageView = (ImageView) findViewById(R.id.img_cursor);
        //下划线动画的相关设置
        bmpWidth = BitmapFactory.decodeResource(getResources(),R.drawable.line).getWidth();//得到图片的宽度
        //当前窗口的宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        offset = (screenWidth/3-bmpWidth)/2;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);

        imageView.setImageMatrix(matrix);

        one = offset*2+bmpWidth;
        two=one*2;
        LayoutInflater inflater = getLayoutInflater();
        viewArrayList = new ArrayList<View>();
        viewArrayList.add(inflater.inflate(R.layout.view_one, null, false));
        viewArrayList.add(inflater.inflate(R.layout.view_two,null,false));
        viewArrayList.add(inflater.inflate(R.layout.view_three,null,false));
        viewPager.setAdapter(new MyAdapter(viewArrayList));
        viewPager.setCurrentItem(0);
        tv_one.setOnClickListener(this);
        tv_two.setOnClickListener(this);
        tv_three.setOnClickListener(this);

        viewPager.addOnPageChangeListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_one:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_two:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv_three:
                viewPager.setCurrentItem(2);
                break;

        }


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    @Override
    public void onPageSelected(int position) {
        Animation animation = null;
        switch (position){
            case 0:
                if (curIndex==1){
                    animation = new TranslateAnimation(one,0,0,0);
                }else if (curIndex==2){
                    animation = new TranslateAnimation(two,0,0,0);
                }
                break;
            case 1:
                if (curIndex==0){
                    animation = new TranslateAnimation(offset,one,0,0);
                }else if (curIndex==2){
                    animation = new TranslateAnimation(two,one,0,0);
                }
                break;
            case 2 :
                if (curIndex==0){
                    animation =new TranslateAnimation(offset,two,0,0);
                }else if (curIndex==1){
                    animation = new TranslateAnimation(one,two,0,0);

                }
                break;


        }
        curIndex = position;
        animation.setFillAfter(true);
        animation.setDuration(300);
        imageView.startAnimation(animation);


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
