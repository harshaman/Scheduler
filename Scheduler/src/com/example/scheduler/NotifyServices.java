package com.example.scheduler;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;


public class NotifyServices extends Service	{

	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	NotificationManager nm;
	@Override
	public void onCreate()	{
		super.onCreate();
		
		showNotification();
	}
	
	@Override
	public void onDestroy()	{
		super.onCreate();
		
		nm.cancel(R.string.app_name);
	}

	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void showNotification() {
		// TODO Auto-generated method stub
		
		Notification.Builder builder = new Notification.Builder(this);
		
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(getApplicationContext(), MainActivity.class), 0);
		
		builder.setContentIntent(pendingIntent) 
					.setSmallIcon(R.drawable.ic_launcher) 
					.setContentText(getString(R.string.hello_world))
					.setContentTitle(getString(R.string.app_name))
					.setContentInfo("My Notification")
					.setTicker("Welcome to my example");
					
		
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		Notification n = builder.build();
		nm.notify(R.string.app_name,n);
	}
	

}

