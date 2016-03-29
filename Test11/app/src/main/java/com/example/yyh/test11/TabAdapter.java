package com.example.yyh.test11;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter
{

	public static String[] TITLES = new String[]
	{ "�γ�", "�ʴ�", "���", "ѧϰ", "�ƻ�" };

	public TabAdapter(FragmentManager fm)
	{
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0)
	{
		TabFragment fragment = new TabFragment(arg0);
		return fragment;
	}

	@Override
	public int getCount()
	{
		return TITLES.length;
	}

	@Override
	public CharSequence getPageTitle(int position)
	{
		return TITLES[position];
	}

}
