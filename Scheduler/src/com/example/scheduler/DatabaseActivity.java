package com.example.scheduler;

//mport android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class DatabaseActivity extends Activity 
{
	String str;
	SQLiteDatabase sampleDB = null;
	private final String SAMPLE_DB_NAME = "Scheduler";
	private final String SAMPLE_TABLE_NAME = "Events";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_database);
		
		try 
		{
        	sampleDB =  this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
		}
		catch (SQLiteException se ) 
		{
        	Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }

		try 
        {
        	
        	sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + SAMPLE_TABLE_NAME + " (Title varchar(50),Summary varchar(100),Tdate datetime,remdate datetime);");
        	
        	sampleDB.execSQL("DELETE FROM " + SAMPLE_TABLE_NAME + " WHERE Title="+str+";");
        	
        	
        } 
		catch (SQLiteException se ) 
        {
        	Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        } 
		finally 
        {
        	//sampleDB.execSQL("DROP TABLE " + SAMPLE_TABLE_NAME + ";");
        		sampleDB.close();
        }
		
		Intent intent = new Intent(DatabaseActivity.this, MainActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.database, menu);
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
