package com.example.scheduler;

import java.util.ArrayList;










//import android.support.v7.app.ActionBarActivity;
import android.widget.AdapterView.OnItemClickListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener
{
	
	SQLiteDatabase sampleDB = null;
	private final String SAMPLE_DB_NAME = "Scheduler";
	private final String SAMPLE_TABLE_NAME = "Events";
	
	
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		
		
		setContentView(R.layout.activity_main);
		final ArrayList<String> results1 = new ArrayList<String>();
		final ArrayList<String> results2 = new ArrayList<String>();
		final ArrayList<String> results3 = new ArrayList<String>();
		final ArrayList<String> results4 = new ArrayList<String>();
		Context ctx = getApplicationContext();
		Resources res = ctx.getResources();
			
			
			
		
		try 
		{
        	sampleDB =  this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
		}
		catch (SQLiteException se ) 
		{
        	Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }
		
		ListView lv1= (ListView) findViewById(R.id.lv1);
		ColorDrawable devidrColor = new ColorDrawable(
			this.getResources().getColor(android.R.color.holo_blue_bright));
			lv1.setDivider(devidrColor);
			lv1.setDividerHeight(1);
		try
		{
			Cursor c = sampleDB.rawQuery("SELECT Title, summary, Tdate,Remdate FROM " +	SAMPLE_TABLE_NAME, null);
			
			if (c != null ) {
        		if  (c.moveToFirst()) {
        			do {
        				String title = c.getString(c.getColumnIndex("Title"));
        				String summary = c.getString(c.getColumnIndex("Summary"));
        				String tdate = c.getString(c.getColumnIndex("Tdate"));
        				String remdate = c.getString(c.getColumnIndex("remdate"));
        				/*if(remdate.compareTo("null")==0)
        					remdate=new String("");
        				else
        					remdate=new String(remdate);*/
        				results1.add(title);
        				results2.add(summary);
        				results3.add(tdate);
        				results4.add(remdate);
        			}while (c.moveToNext());
        		} 
        	}
		}
		catch (SQLiteException se ) {
        	Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        } finally {
        	//sampleDB.execSQL("DROP TABLE " + SAMPLE_TABLE_NAME + ";");
        		sampleDB.close();
        }
		
		String[] titles = new String[results1.size()];
		String[] summary = new String[results2.size()];
		String[] dates = new String[results3.size()];
		
		titles = results1.toArray(titles);
		summary = results2.toArray(summary);
		dates = results3.toArray(dates);
		
		lv1.setAdapter(new TextAdapter(ctx, R.layout.activity_text_adapter,
				titles,summary,dates ));
		
		//lv1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,results));
		
		Ripple btn=(Ripple) findViewById(R.id.btn);
		TiltEffectAttacher.attach(btn);
		//Button btn = (Button) findViewById(R.id.btn);
		btn.setBackgroundColor(Color.parseColor("#1E88E5"));
		btn.setOnClickListener(new OnClickListener()	
		{
			public void onClick(View v)	
			{
				Intent intent = new Intent(MainActivity.this, AddNewTask.class);
				startActivity(intent);
				//finish();
			}			
		});
	
		lv1.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				TextView tv1 = (TextView) findViewById(R.id.option_text1);
				//Toast.makeText(getApplicationContext(), view.getItem(), Toast.LENGTH_SHORT).show();
					Intent i=new Intent(MainActivity.this,DetailActivity.class);
					String s=(results1.toString());
					String summary=(results2.toString());
					String rem=(results4.toString());
					String due=(results3.toString());
					i.putExtra("Title",results1.get(position));
					i.putExtra("Summary",results2.get(position));
					i.putExtra("Reminder",results4.get(position));
					i.putExtra("Due",results3.get(position));
					startActivity(i);
			}
			
		});
		int[] items = new int[20];
        for (int i = 0; i < items.length; i++) {
            items[i] = (i + 1);
        }
  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.detail, menu);
		//getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		SubMenu submenu = menu.addSubMenu(0, 1, 1, "Clear List");
		SubMenu submenu1 =menu.addSubMenu(0, 2, 1,"About");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle presses on the action bar items
	    switch (item.getItemId()) {
	     /*   case R.id.action_edit:
	            openEdit();
	            return true;*/
	        case 1:
	            openSettings();
	            return true;
	        case 2:
	        	openAbout();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
    
	}

	
	public void openSettings()
	{
		try 
        {
        	sampleDB =  this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
		}
		catch (SQLiteException se ) {
        	Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }

		 try 
	        {
	        	
			 sampleDB.execSQL("DROP TABLE " + SAMPLE_TABLE_NAME + ";");
	        	
	        	
	        } catch (SQLiteException se ) {
	        	Log.e(getClass().getSimpleName(), "Could not create or Open the database");
	        } finally {
	        	//sampleDB.execSQL("DROP TABLE " + SAMPLE_TABLE_NAME + ";");
	        		sampleDB.close();
	        }
			
			Intent intent = new Intent(MainActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
	}
	
	@SuppressWarnings("deprecation")
	public void openAbout()
	{
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("About");
		alertDialog.setMessage("This app has been developed by Aman Harsh as a part of the Android project.");
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() 
		{
		   public void onClick(DialogInterface dialog, int which) 
		   {
		      alertDialog.dismiss();
		   }
		});
		// Set the Icon for the Dialog
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.show();
	}
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		
	}
}
