package com.example.titantrackr;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class EnterManually extends Activity {
	JSONParser jsonParser = new JSONParser();
	ArrayList<String> exerciseList;
	String excSelected;
	String reps;
	String weight;
	String time;
	String weID = "";
	JSONObject json;
	int responseCount;
	List<NameValuePair> params = new ArrayList<NameValuePair>();
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enter_manually);
		ActionBar ab = getActionBar();
		ab.setIcon(R.drawable.icon);
		ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FFFFFF"));
		ab.setTitle((Html.fromHtml("<font color=\"#000000\">" + getString(R.string.title) + "</font>")));
		ab.setBackgroundDrawable(colorDrawable);
		
		exerciseList = new ArrayList<String>();
		getExercises();
		
		ListView exerciseListView = (ListView)findViewById(R.id.exerciseListView);
		final EditText repData = (EditText)findViewById(R.id.repsData);
		final EditText weightData = (EditText)findViewById(R.id.weightData); 
		//TextView weightData = (TextView)findViewById(R.id.weightData);
		final EditText timeData = (EditText)findViewById(R.id.timeData);
		
		//get username
		final String userName = (getIntent().getExtras().getString("username"));
		
		 ArrayAdapter<String> arrayAdapter =      
         new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, exerciseList);
		 // Set The Adapter
         exerciseListView.setAdapter(arrayAdapter); 
                 
         exerciseListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				// TODO Auto-generated method stub
				v.setSelected(true);
				String selectedExercise=exerciseList.get(position);
				if(selectedExercise == "Incline Press"){
					weID = "8";
				}
				else if(selectedExercise == "Cable Crossover"){
					weID = "10";
				}
				Toast.makeText(getApplicationContext(), "exercise seleceted:" + selectedExercise, Toast.LENGTH_LONG).show();
			}
		});        
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
         

         
         
 		//When Sync data button pressed
 		Button syncBtn = (Button)findViewById(R.id.sendData);
 		syncBtn.setOnClickListener(new OnClickListener() {
 			@Override
 			public void onClick(View v) {
			 
	 				//Run the JSON method to send data to php web service. 
	 				Log.i("Button", "Send Data Button pressed");
	 				Log.i("parameters", params.toString());
	 				//add edit text values to params list
	 				params.add(new BasicNameValuePair("username", "sam123"));
	 				params.add(new BasicNameValuePair("reps", repData.getText().toString()));
	 				params.add(new BasicNameValuePair("weight", weightData.getText().toString()));
	 				params.add(new BasicNameValuePair("time", timeData.getText().toString()));
	 				params.add(new BasicNameValuePair("weID", weID));
	 				new sendJson().execute();
	 				if(responseCount == 1){
	 					Toast.makeText(getApplicationContext(), "Data successfully sent!", Toast.LENGTH_LONG).show();
	 				}
				}
			});
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
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.newmenu, menu);
		return true;
	}
	
	void getExercises(){
		exerciseList.add("Incline Press");
		exerciseList.add("Cable Crossover");
		exerciseList.add("Deadlift");
		exerciseList.add("Flat Press");
		exerciseList.add("Hammer Curls");
		exerciseList.add("Lat Pulldown");
	}

}
