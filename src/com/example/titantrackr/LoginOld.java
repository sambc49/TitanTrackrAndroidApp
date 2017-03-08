package com.example.titantrackr;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginOld extends Activity {
	JSONParser jsonParser = new JSONParser();
	JSONObject json;
	String usernameInput;
	String passwordInput;
	List<NameValuePair> params = new ArrayList<NameValuePair>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		//Define edit text inputs
		final EditText editPw = (EditText)findViewById(R.id.editTextPassword);
		final EditText editUname = (EditText)findViewById(R.id.editTextUsername);

		
		//Set the button to respond to click
		Button b = (Button)findViewById(R.id.loginButton);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				usernameInput = editUname.getText().toString();
				passwordInput = editPw.getText().toString();
				
				params.add(new BasicNameValuePair("username", usernameInput));
				params.add(new BasicNameValuePair("password", passwordInput));
				Log.i("usernameInput", usernameInput);
				Log.i("passwordInput", passwordInput);
				new sendLoginData().execute();
				

			}
		});
		
	}
	 class sendLoginData extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... args) {
			json = jsonParser.makeHttpRequestNew("http://titantrackr.co.uk/loginWebService.php", "POST", params);
			Log.i("Sending", "http://titantrackr.co.uk/loginWebService.php" + params);
			Log.i("JSON", json.toString());
			return json.toString();	
			
			
		}
	}
	 
	public void doSomething(){
		Log.i("jsonMate", "INNIT MATE");
	}
	
	

}
