package com.example.scheduler;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.VideoView;

public class SplashScreen extends Activity 
{

	private static int SPLASH_TIME_OUT = 3000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		VideoView videoview = (VideoView) findViewById(R.id.videoview);

		Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.splash);

		videoview.setVideoURI(uri);
		videoview.start();
		
		new Handler().postDelayed(new Runnable()	{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SplashScreen.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
			
		},SPLASH_TIME_OUT);
	}

}
