package com.example.titantrackr;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NfcOverview extends Activity {
	JSONParser jsonParser = new JSONParser();
	String username;
	String reps;
	String weight;
	String time;
	String weID;
	JSONObject json;
	int responseCount;
	List<NameValuePair> params = new ArrayList<NameValuePair>();
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nfc_overview_new);
		
		ActionBar ab = getActionBar();
		ab.setIcon(R.drawable.icon);
		ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FFFFFF"));
		ab.setTitle((Html.fromHtml("<font color=\"#000000\">" + getString(R.string.title) + "</font>")));
		ab.setBackgroundDrawable(colorDrawable);
		/*Set text to show NFC data
		TextView showNFC = (TextView)findViewById(R.id.showNfc); 
		showNFC.setText(getIntent().getExtras().getString("NFCDATA"));*/
		
		//Set params variable as the NFC data carried from Intent
		final String nfcData = (getIntent().getExtras().getString("NFCDATA"));
		
		
		
		try {
			JSONObject nfcDataJson = new JSONObject(nfcData);
			username = nfcDataJson.getString("username");
			reps = nfcDataJson.getString("reps");
			weight = nfcDataJson.getString("weight");
			time = nfcDataJson.getString("time");
			weID = nfcDataJson.getString("weID");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Set Repitions text View as the data from NFC tag
		final EditText repData = (EditText)findViewById(R.id.repsData);
		repData.setText(reps);
		final EditText weightData = (EditText)findViewById(R.id.weightData); 
		//TextView weightData = (TextView)findViewById(R.id.weightData);
		weightData.setText(weight);
		final EditText timeData = (EditText)findViewById(R.id.timeData);
		timeData.setText(time);
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("reps", reps));
		params.add(new BasicNameValuePair("weight", weight));
		params.add(new BasicNameValuePair("time", time));
		params.add(new BasicNameValuePair("weID", weID));
		
		//when edit button pressed
		Button editBtn = (Button)findViewById(R.id.EditData);
		editBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//update values set from editText fields
				
				params.add(new BasicNameValuePair("reps", repData.getText().toString()));
				params.add(new BasicNameValuePair("weight", weightData.getText().toString()));
				params.add(new BasicNameValuePair("time", timeData.getText().toString()));
				
			}
		});
		
		
		//When Sync data button pressed
		Button syncBtn = (Button)findViewById(R.id.sendData);
		syncBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 
				//Run the JSON method to send data to php web service. 
				Log.i("Button", "Send Data Button pressed");
				Log.i("parameters", nfcData);
				
				new sendJson().execute();
				if(responseCount == 1){
					Toast.makeText(getApplicationContext(), "Data successfully sent!", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
	
	//menu items
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.scan_activity:
			//scan button clicked
			sendToMain();
			return true;
		case R.id.enter_data_activity:
			//enter data button clicke
			sendToManual();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	// launch main scan activity
	private void sendToMain(){
		Intent i = new Intent(NfcOverview.this, MainActivity.class);
		//Get username from Login page
		String userName = (getIntent().getExtras().getString("username"));
		i.putExtra("userName", userName);
		startActivity(i);
	}
	
	private void sendToManual(){
		Intent i = new Intent(NfcOverview.this, EnterManually.class);
		//Get username from Login page
		String userName = (getIntent().getExtras().getString("username"));
		i.putExtra("userName", userName);
		startActivity(i);
	}
	
	private class sendJson extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... args) {
			json = jsonParser.makeHttpRequestNew("http://titantrackr.co.uk/json.php", "POST", params);
			Log.i("Sending", "http://titantrackr.co.uk/json.php" + params);
			Log.i("JSON", json.toString());
			if(json != null){
				responseCount = 1;
			}
			return json.toString();	
		}
	}	
}
