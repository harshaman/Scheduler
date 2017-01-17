package com.example.scheduler;

import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class EditTask extends Activity implements OnClickListener {

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
		timebtn.setBackgroundColor(Color.parseColor("#1E88E5"));
		datebtn.setBackgroundColor(Color.parseColor("#1E88E5"));
	
		final Button btn = (Button) findViewById(R.id.savebutton);
		btn.setBackgroundColor(Color.parseColor("#1E88E5"));
		final TextView ed1= (TextView) findViewById(R.id.editText1);
		Bundle bundle = getIntent().getExtras();
		ed1.setText((String) bundle.get("title"));
		ed1.setFocusable(false);
		final EditText ed2= (EditText) findViewById(R.id.editText2);
		
		final Calendar calendar =  Calendar.getInstance();
		
		
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
		        	
		        	//sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + SAMPLE_TABLE_NAME + " (Title varchar(50),Summary varchar(100),Tdate datetime,remdate datetime);");
		        	
		        	sampleDB.execSQL("UPDATE " + SAMPLE_TABLE_NAME + " SET summary="+"'"+ed2.getText()+"'"+","+"Tdate='"+datebtn.getText()+" "+timebtn.getText()+":00"+"' WHERE title="+"'"+ed1.getText()+"';");
		        	
		        	
		        } catch (SQLiteException se ) {
		        	Log.e(getClass().getSimpleName(), "Could not create or Open the database");
		        } finally {
		        	//sampleDB.execSQL("DROP TABLE " + SAMPLE_TABLE_NAME + ";");
		        		sampleDB.close();
		        }
		        
				Intent intent=new Intent(EditTask.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		
	
			
		});
		
		
		datebtn.setOnClickListener(this);
		timebtn.setOnClickListener(this); 
		
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
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_new_task, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

