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

public class Register extends Activity implements OnClickListener {
	ArrayList<String> goalNameList;
	JSONParser jsonParser = new JSONParser();
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private ProgressDialog pDialog;
	private Button mRegister;
	private EditText user;
	private EditText email;
	private EditText userPass;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registerpage);
		
		ActionBar ab = getActionBar();
		ab.setIcon(R.drawable.icon);
		ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FFFFFF"));
		ab.setTitle((Html.fromHtml("<font color=\"#000000\">" + getString(R.string.title) + "</font>")));
		ab.setBackgroundDrawable(colorDrawable);

		//define editText inputs
		user = (EditText)findViewById(R.id.usernameInput);
		email = (EditText)findViewById(R.id.emailInput);
		userPass = (EditText)findViewById(R.id.passwordInput);
		
		mRegister = (Button)findViewById(R.id.registerBtn);
		mRegister.setOnClickListener(this);
	}
	

	@Override
	public void onClick(View v) {
		new CreateUser().execute();
		
	}
	
	class CreateUser extends AsyncTask<String, String, String> {
		boolean failure = false;
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(Register.this);
            pDialog.setMessage("Creating User....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();

		}
		@Override
		protected String doInBackground(String... args) {
			int success;
			String username = user.getText().toString();
			String mail = email.getText().toString();
			String password = userPass.getText().toString();
			
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", username));
				params.add(new BasicNameValuePair("email", mail));
				params.add(new BasicNameValuePair("password", password));
				
				Log.d("Request Starting!", "request starting");
				//make the request and send params
				JSONObject json = jsonParser.makeHttpRequestNew("http://titantrackr.co.uk/registerWebService.php", "POST", params);
				Log.i("sendingStuff", params.toString());
				Log.d("login attempt", json.toString());
				
				//json success tag
				success = json.getInt(TAG_SUCCESS);
				if(success ==1){
					Log.d("Login Successful!", json.toString());
					Intent i = new Intent(Register.this, Login.class);
					i.putExtra("registeredUser", mail);
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
			super.onPostExecute(result);
			
		}
		
		}
	}	
