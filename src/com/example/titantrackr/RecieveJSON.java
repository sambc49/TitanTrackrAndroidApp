package com.example.titantrackr;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class RecieveJSON extends Activity {
	JSONParser jsonParser = new JSONParser();
	final static String url = "http://192.168.0.16/titantrackr/app/index.php";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		new LoadttpRequests().execute();
	}
	
	private class LoadttpRequests extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			jsonParser.JsonDataFromUrl(url);
			return null;
		}
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recieve_json, menu);
		return true;
	}
}