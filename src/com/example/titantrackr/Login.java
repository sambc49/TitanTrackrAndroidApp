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
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity implements OnClickListener{
	private EditText user; 
	private EditText userPass;
	private Button mSubmit;
	private Button mRegister;
	JSONParser jsonParser = new JSONParser();
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private ProgressDialog pDialog;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		ActionBar ab = getActionBar();
		ab.setIcon(R.drawable.icon);
		ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FFFFFF"));
		ab.setTitle((Html.fromHtml("<font color=\"#000000\">" + getString(R.string.title) + "</font>")));
		ab.setBackgroundDrawable(colorDrawable);
		
		//setup login fields
		user = (EditText)findViewById(R.id.editTextUsername);
		userPass = (EditText)findViewById(R.id.editTextPassword);
		
		mSubmit = (Button)findViewById(R.id.loginButton);
		mSubmit.setOnClickListener(this);
		mRegister = (Button)findViewById(R.id.registerButton);
		mRegister.setOnClickListener(this);
		Intent intent = getIntent();
		String newUserText = intent.getStringExtra("registeredUser");
		if(newUserText != null){
			Log.i("usernameIntent", newUserText);
			user.setText(newUserText);
		}
		SharedPreferences sPrefs = getSharedPreferences("loginData", 0);
		String userName = sPrefs.getString("usernamePref", "empty");
		String passWord = sPrefs.getString("passwordPref", "empty");
		if(userName.toString() == "empty"){
			Log.i("userPrefEmpty", "username bit is empty mate");
			Log.i("userPref", userName.toString());
		}
		else {
			Log.i("userPrefNotEmpty", userName.toString());
			
		}
		

	}

	@Override
	public void onClick(View v) {
		//case switch of button is pressed
		switch (v.getId()){
		case R.id.loginButton:
			new AttemptLogin().execute();
			break;
		case R.id.registerButton:
			Intent i = new Intent(this, Register.class);
			startActivity(i);
			break;
			default:
				break;
		}
		
	}
	
	class AttemptLogin extends AsyncTask<String, String, String> {
		boolean failure = false;
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(Login.this);
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage("Attempting login...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();

		}
		@Override
		protected String doInBackground(String... args) {
			int success;
			String username = user.getText().toString();
			String password = userPass.getText().toString();
			
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", username));
				params.add(new BasicNameValuePair("password", password));
				
				Log.d("Request Starting!", "request starting");
				//make the request and send params
				JSONObject json = jsonParser.makeHttpRequestNew("http://titantrackr.co.uk/loginWebService.php", "POST", params);
				Log.i("sendingStuff", params.toString());
				Log.d("login attempt", json.toString());
				
				//json success tag
				success = json.getInt(TAG_SUCCESS);
				if(success ==1){
					Log.d("Login Successful!", json.toString());
					
					
					
					Intent i = new Intent(Login.this, MainActivity.class);
					//pass across username
					i.putExtra("username", username);
					finish();
					startActivity(i);
					return json.getString(TAG_MESSAGE);
			} else {
				Log.d("Login Failure!", json.getString(TAG_MESSAGE));
				return json.getString(TAG_MESSAGE);
				
			}
		} catch (JSONException e){
			e.printStackTrace();
		}
		return null;
	}
		
		
		@Override
		protected void onPostExecute(String result) {
			pDialog.dismiss();
			
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
		
	}
}
