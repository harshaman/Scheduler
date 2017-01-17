package com.example.scheduler;

import java.util.Locale;

//import com.example.texttospeechdemo.MainActivity;
//import com.example.texttospeechdemo.R;





import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class DetailActivity extends Activity {

	String s,sum,r,d;
	String [] str={"you have a ","You have to go for ","you have an upcoming task"};
	private final String SAMPLE_DB_NAME = "Scheduler";
	private final String SAMPLE_TABLE_NAME = "Events";
	SQLiteDatabase sampleDB = null;
	TextToSpeech tts;
	TextView title;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		title = (TextView)findViewById(R.id.title);
		TextView summary =(TextView)findViewById(R.id.summary);
		TextView rem=(TextView) findViewById(R.id.remdisplay);
		TextView reminder=(TextView) findViewById(R.id.reminder);
		final CheckBox delete=(CheckBox) findViewById(R.id.delete);
		TextView tv1 = (TextView)findViewById(R.id.textView1);
		tv1.setTextColor(Color.rgb(135,206, 250));
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            s =(String) bundle.get("Title");
            sum=(String) bundle.get("Summary");
            r=(String) bundle.get("Reminder");
            d=(String) bundle.get("Due");
            title.setText(s);
            summary.setText(sum);
            if(r!=null)
            {
            	rem.setText(r);
            	reminder.setText("Reminder:");
            }
        }
        
        try 
        {
        	sampleDB =  this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
		}
		catch (SQLiteException se ) {
        	Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }
        
        delete.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				if(delete.isChecked())
				{
					 try 
				        {
				        	
				        	sampleDB.execSQL("DELETE FROM " + SAMPLE_TABLE_NAME + " WHERE title="+"'"+s+"';");
				        	
				        	
				        } catch (SQLiteException se ) {
				        	Log.e(getClass().getSimpleName(), "Could not create or Open the database");
				        } finally {
				        	//sampleDB.execSQL("DROP TABLE " + SAMPLE_TABLE_NAME + ";");
				        		sampleDB.close();
				        }
						
						Intent intent = new Intent(DetailActivity.this, MainActivity.class);
						startActivity(intent);
						finish();
				}
			}
        	
        });

    		//Button b=(Button)findViewById(R.id.button1);
    		//b.setOnClickListener(this);
    		tts =new TextToSpeech(DetailActivity.this, new TextToSpeech.OnInitListener() {
    			
    			@Override
    			public void onInit(int status) {
    				// TODO Auto-generated method stub
    				if(status!=TextToSpeech.ERROR)
    				{
    					tts.setLanguage(Locale.ENGLISH);
    				}
    			}
    		});
    	Ripple btn=(Ripple) findViewById(R.id.btn);
    	btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(s.compareToIgnoreCase("meeting")==0||s.compareToIgnoreCase("class")==0)
	    			tts.speak(str[0]+s+"at"+d, TextToSpeech.QUEUE_FLUSH, null);
				else if(s.compareToIgnoreCase("breakfast")==0)
					tts.speak(str[1]+s+"at"+d, TextToSpeech.QUEUE_FLUSH, null);
				else
					tts.speak(str[2]+s+"at"+d,TextToSpeech.QUEUE_FLUSH,null);
	    		
				
			}
		});
    		//if(s.compareToIgnoreCase("meeting")==0)
    		
    			
    	}
	protected void onPause(){
		if(tts!=null)
		{
			tts.stop();
			tts.shutdown();
		}
		super.onPause();
		
	}
    	
    	@Override
    	public boolean onCreateOptionsMenu(Menu menu) 
    	{
    		// Inflate the menu; this adds items to the action bar if it is present.
    		//getMenuInflater().inflate(R.menu.detail, menu);
    		getMenuInflater().inflate(R.menu.main_activity_actions, menu);
    		SubMenu submenu = menu.addSubMenu(0, 1, 1, "Clear List");
    		SubMenu submenu1 =menu.addSubMenu(0, 2, 1,"About");
    		return true;
    	}
    	
    	@Override
    	public boolean onOptionsItemSelected(MenuItem item) 
    	{
    	    // Handle presses on the action bar items
    	    switch (item.getItemId()) {
    	        case R.id.action_edit:
    	            openEdit();
    	            return true;
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
    	
    	public void openEdit()
    	{
    		Intent intent=new Intent(DetailActivity.this,EditTask.class);
    		intent.putExtra("title",s);
    		startActivity(intent);
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
				
				Intent intent = new Intent(DetailActivity.this, MainActivity.class);
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
}



