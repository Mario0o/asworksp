package com.example.yyh.pageviewtest;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by yyh on 2015/10/26.
 */
public class MyAdapter extends PagerAdapter {
    private ArrayList<View> viewArrayList;


    public MyAdapter() {
    }

    public MyAdapter(ArrayList<View> viewArrayList) {
        super();
        this.viewArrayList = viewArrayList;
    }


    @Override
    public int getCount() {
        return viewArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewArrayList.get(position));
        return viewArrayList.get(position);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewArrayList.get(position));
        //super.destroyItem(container, position, object);
    }
}
