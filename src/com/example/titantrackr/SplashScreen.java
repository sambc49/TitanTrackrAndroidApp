package com.example.titantrackr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {
	private static int SPLASH_TIME_OUT = 3000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_new);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// Start the MainActivity
				Intent i = new Intent(SplashScreen.this, Login.class);
				startActivity(i);
				//close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);
	}

}
