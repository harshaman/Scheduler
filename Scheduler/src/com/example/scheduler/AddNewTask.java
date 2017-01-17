package com.example.scheduler;

import java.util.ArrayList;
import java.util.Calendar;



//import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class AddNewTask extends Activity implements OnClickListener
{
	private final String SAMPLE_DB_NAME = "Scheduler";
	private final String SAMPLE_TABLE_NAME = "Events";
	SQLiteDatabase sampleDB = null;
	
	private int mYear, mMonth, mDay, mHour, mMinute;
	private int rmYear, rmMonth, rmDay, rmHour, rmMinute;
	Button datebtn,timebtn,remdate,remtime;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_add_new_task);
		timebtn=(Button) findViewById(R.id.timebtn);
		datebtn=(Button) findViewById(R.id.btn);
		remdate=(Button) findViewById(R.id.remdisplay);
		remtime=(Button) findViewById(R.id.remtime);
		final Button btn = (Button) findViewById(R.id.savebutton);
		final EditText ed1= (EditText) findViewById(R.id.editText1);
		final EditText ed2= (EditText) findViewById(R.id.editText2);
		final CheckBox checkBox = (CheckBox) findViewById(R.id.rem);
		final Calendar calendar =  Calendar.getInstance();
		TiltEffectAttacher.attach(timebtn);
		TiltEffectAttacher.attach(datebtn);
		TiltEffectAttacher.attach(remtime);
		TiltEffectAttacher.attach(remdate);
		btn.setBackgroundColor(Color.parseColor("#1E88E5"));
		timebtn.setBackgroundColor(Color.parseColor("#1E88E5"));
		datebtn.setBackgroundColor(Color.parseColor("#1E88E5"));
		remtime.setBackgroundColor(Color.parseColor("#1E88E5"));
		remdate.setBackgroundColor(Color.parseColor("#1E88E5"));
		
		try {
        	sampleDB =  this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
		}
		catch (SQLiteException se ) {
        	Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }
		InputFilter filter = new InputFilter() { 
			public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
		        try {
		            Character c = source.charAt(0);
		            if (Character.isLetter(c) || Character.isDigit(c)) {
		                return "" + Character.toUpperCase(c);
		            } else {
		                return "";
		            }
		        } catch (Exception e) {
		        }
		        return null;
		    }

	}; 

	ed1.setFilters(new InputFilter[]{filter});
		btn.setOnClickListener(new OnClickListener()	
		{
			ArrayList<String> results = new ArrayList<String>();
			public void onClick(View v)	
			{
    
		        try 
		        {
		        	
		        	sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + SAMPLE_TABLE_NAME + " (Title varchar(50),Summary varchar(100),Tdate datetime,remdate datetime);");
		        	
		        	sampleDB.execSQL("INSERT INTO " + SAMPLE_TABLE_NAME + " Values ("+"'"+ed1.getText()+"'"+","+"'"+ed2.getText()+"'"+","+"'"+datebtn.getText()+" "+timebtn.getText()+":00"+"'"+","+((remdate.getText().toString().compareTo("Reminder Date")==0)?"null":("'"+remdate.getText())+" ")+((remtime.getText().toString().compareTo("Reminder Time")==0)?"":remtime.getText()+":00'")+");");
		        	
		        	
		        } catch (SQLiteException se ) {
		        	Log.e(getClass().getSimpleName(), "Could not create or Open the database");
		        } finally {
		        	//sampleDB.execSQL("DROP TABLE " + SAMPLE_TABLE_NAME + ";");
		        		sampleDB.close();
		        }
		        
		        if(remdate.getText().toString().compareTo("Reminder Date")!=0&&remdate.getText().toString().compareTo("Reminder Time")!=0)
				{
					int y,m,d,h,min;
					y=rmYear;
					m=rmMonth;
					d=rmDay;
					min=rmMinute;
					h=rmHour;
					calendar.set(y,m,d,h,min,0);
					
					long when = calendar.getTimeInMillis();         // notification time

					Intent intentAlarm = new Intent(AddNewTask.this, AlarmReceiver.class);
					intentAlarm.putExtra("title",ed1.getText().toString());
					intentAlarm.putExtra("summary",ed2.getText().toString());

					// create the object
					AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

					 //set the alarm for particular time
					alarmManager.set(AlarmManager.RTC_WAKEUP,when, PendingIntent.getBroadcast(AddNewTask.this,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
				}
				
				Intent intent = new Intent(AddNewTask.this, MainActivity.class);
				startActivity(intent);
				finish();
			}

			
		});
		
		checkBox.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				if (checkBox.isChecked()) 
				{
		            remdate.setVisibility(View.VISIBLE);
		            remtime.setVisibility(View.VISIBLE);
		        }
				if (!checkBox.isChecked())
				{
					 remdate.setVisibility(View.GONE);
			         remtime.setVisibility(View.GONE);
			         remdate.setText("Reminder Date");
			         remtime.setText("Reminder Time");
				}
			}
			
		});
		datebtn.setOnClickListener(this);
		timebtn.setOnClickListener(this); 
		remdate.setOnClickListener(this);
		remtime.setOnClickListener(this);
		
		
	}
	
	public void onClick(View v) 
	{
		if(v==datebtn)
		{
			final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
	        mMonth = c.get(Calendar.MONTH);
	        mDay = c.get(Calendar.DAY_OF_MONTH);
	
	        // Launch Date Picker Dialog
	        DatePickerDialog dpd = new DatePickerDialog(this,
	                new DatePickerDialog.OnDateSetListener() {
	
	                    @Override
	                    public void onDateSet(DatePicker view, int year,
	                            int monthOfYear, int dayOfMonth) {
	                        // Display Selected date in textbox
	                        datebtn.setText(year + "-"
	                                + (monthOfYear + 1) + "-" + dayOfMonth);
	
	                    }
	                }, mYear, mMonth, mDay);
	        dpd.show();
		}
		if(v==timebtn)
		{
			// Process to get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
 
            // Launch Time Picker Dialog
            TimePickerDialog tpd = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
 
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                int minute) {
                            // Display Selected time in textbox
                            timebtn.setText(hourOfDay + ":" + minute);
                        }

                    }, mHour, mMinute, false);
            tpd.show();
		}
		if(v==remdate)
		{
			final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
	        mMonth = c.get(Calendar.MONTH);
	        mDay = c.get(Calendar.DAY_OF_MONTH);
	
	        // Launch Date Picker Dialog
	        DatePickerDialog dpd = new DatePickerDialog(this,
	                new DatePickerDialog.OnDateSetListener() {
	
	                    @Override
	                    public void onDateSet(DatePicker view, int year,
	                            int monthOfYear, int dayOfMonth) {
	                        // Display Selected date in textbox
	                        remdate.setText(year + "-"
	                                + (monthOfYear + 1) + "-" + dayOfMonth);
	                        rmYear=year;
	                        rmMonth=monthOfYear;
	                        rmDay=dayOfMonth;
	                    }
	                }, mYear, mMonth, mDay);
	        dpd.show();
		}
		if(v==remtime)
		{
			// Process to get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
 
            // Launch Time Picker Dialog
            TimePickerDialog tpd = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
 
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                int minute) {
                            // Display Selected time in textbox
                            remtime.setText(hourOfDay + ":" + minute);
                            rmHour=hourOfDay;
                            rmMinute=minute;
                        }

                    }, mHour, mMinute, false);
            tpd.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.add_new_task, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		//int id = item.getItemId();
		//if (id == R.id.action_settings) {
			//return true;
		//}
		return super.onOptionsItemSelected(item);
	}
}
