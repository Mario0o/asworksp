package com.example.yyh.pagetest2;

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
 * Created by yyh on 2015/10/28.
 */
public class FourActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ImageView img_cursor; //移动条图片

    private ArrayList<View> viewArrayList;
    private MyAdapter2 myAdapter2;
    private ViewPager viewPager_four;
    private TextView tv_one,tv_two,tv_three;
    private int offset = 0;// 移动条图片的偏移量

    private int one;//移动条滑动一页的距离
    private int two; // 两页
    private int currIndex; //当前页面位置


    private  int  bmpWidth ;//移动条的长度





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
       setupViews();

    }

    private void setupViews() {
        viewPager_four = (ViewPager) findViewById(R.id.vpager_four);

        LayoutInflater inflater = getLayoutInflater();
       // viewPager_four.addView(inflater.inflate(R.layout.view_one, null, false));

        //加载页面
        viewArrayList = new ArrayList<View>();
        viewArrayList.add(inflater.inflate(R.layout.view_one, null, false));
        viewArrayList.add(inflater.inflate(R.layout.view_two, null, false));
        viewArrayList.add(inflater.inflate(R.layout.view_three, null, false));


        tv_one = (TextView) findViewById(R.id.tv_one);
        tv_two = (TextView) findViewById(R.id.tv_two);
        tv_three = (TextView) findViewById(R.id.tv_three);

        img_cursor = (ImageView) findViewById(R.id.img_cursor);



        viewPager_four.setAdapter(new MyAdapter(viewArrayList));
        viewPager_four.setCurrentItem(0);
        tv_one.setOnClickListener(this);
        tv_two.setOnClickListener(this);
        tv_three.setOnClickListener(this);

        viewPager_four.addOnPageChangeListener(this);


        //下划线的相关设置

        bmpWidth = BitmapFactory.decodeResource(getResources(),R.drawable.line).getWidth();//获取图片长度
        //获取屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;

        //计算偏移量

        offset = (screenWidth/3-bmpWidth)/2;

        //偏移动画的相关设置

        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);

        img_cursor.setImageMatrix(matrix);//设置动画初始位置
        //移动距离

        one= offset*2+bmpWidth;
        two=one*2;








    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_one:
                viewPager_four.setCurrentItem(0);
                break;
            case R.id.tv_two:
                viewPager_four.setCurrentItem(1);
                break;
            case R.id.tv_three:
                viewPager_four.setCurrentItem(2);
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
                if (currIndex==1){
                    animation = new TranslateAnimation(one,0,0,0);
                }else if(currIndex==2){
                    animation = new TranslateAnimation(two,0,0,0);
                }
                break;
            case 1:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, one, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, one, 0, 0);
                }
                break;
            case 2:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, two, 0, 0);
                } else if (currIndex == 1) {
                    animation = new TranslateAnimation(one, two, 0, 0);
                }
                break;

        }
        currIndex =position;
        animation.setFillAfter(true);
        animation.setDuration(300);
        img_cursor.startAnimation(animation);


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
