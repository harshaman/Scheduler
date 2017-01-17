package com.example.scheduler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class TextAdapter extends ArrayAdapter<String>
{
	private LayoutInflater mInflater;
	
	private String[] mTitle,mSummary,mDate;
	
	private TypedArray mIcons;
	
	private int mViewResourceId;
	
	TextView tv1;
	
	CheckBox checkBox;
	
	public TextAdapter(Context context, int viewResourceId, String[] strings, String[] summary,String[] date) 
	{
		super(context, viewResourceId, strings);
		
		mInflater = (LayoutInflater)context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		mTitle = strings;
		mSummary=summary;
		mDate=date;
		
		
		mViewResourceId = viewResourceId;
		
		
	}
	

	@Override
	public int getCount() {
		return mTitle.length;
	}

	@Override
	public String getItem(int position) 
	{
		//return mTitle.get(position);
		return mTitle[position];
	}

	@Override
	public long getItemId(int position) 
	{
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		convertView = mInflater.inflate(mViewResourceId, null);

		TextView tv1 = (TextView)convertView.findViewById(R.id.option_text1);
		tv1.setText(mTitle[position]);
		
		TextView tv2 = (TextView)convertView.findViewById(R.id.option_text2);
		tv2.setText(mSummary[position]);
		
		
		
		TextView tv3 = (TextView)convertView.findViewById(R.id.option_text3);
		tv3.setText(mDate[position]);
		
		
		return convertView;
	}
}
