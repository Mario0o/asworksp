package com.example.yyh.pagetest2;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by yyh on 2015/10/27.
 */
public class MyAdapter2 extends PagerAdapter {
    private ArrayList<View> viewArrayList;
    private ArrayList<String> titleArrayList;

    public MyAdapter2 (){


    }

    public MyAdapter2(ArrayList<View> viewArrayList, ArrayList<String> titleArrayList) {
        this.viewArrayList = viewArrayList;
        this.titleArrayList = titleArrayList;
    }

    @Override
    public int getCount() {
        return viewArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewArrayList.get(position));
        return viewArrayList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewArrayList.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleArrayList.get(position);
    }
}
