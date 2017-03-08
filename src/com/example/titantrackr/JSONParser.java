package com.example.titantrackr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {
	String name = "";
	String version = "";
	String result="";
	static InputStream webs = null;
	JSONObject jObj = null;
	static String json;
	
	public void JsonDataFromUrl(String url) {
		//Http Connection code
		try {
		
			//Create new client object 
			HttpClient httpclient = new DefaultHttpClient();
			//PHP URL 
			HttpPost httppost = new HttpPost(url);
			//Execute response
			HttpResponse response = httpclient.execute(httppost);
			//Get message from response 
			HttpEntity entity = response.getEntity();
			//Get content of message
			webs = entity.getContent();
			//Convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(webs, "iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null){
				sb.append(line + "/n");
			}
			//close our input stream
			webs.close();
			//convert our stringbuilder object to a string
			result=sb.toString();

		}catch (Exception e){
			Log.e("log_tag", "Error converting result "+e.toString());
		}
		}catch(Exception e){
			Log.e("log_tag", "Error in http connection" +e.toString());
		}
		//Json code
		try {
			JSONObject jsonData = new JSONObject(result);
			name = jsonData.getString("name");
			version = jsonData.getString("version");
		}
		catch(JSONException e){
			Log.e("log_tag", "Error parsing data "+e.toString());
		}
		Log.i("JSON", name);
		Log.i("JSON2", version);	
	}

	public JSONObject postJsonData(String url){
	
		try {
        // request method is POST
        // defaultHttpClient
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);;
        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        
        
        webs = httpEntity.getContent();
		} catch(UnsupportedEncodingException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	 public JSONObject makeHttpRequestNew(String url, String method, List<NameValuePair> params) {
	        // Making HTTP request
	        try {

	            // check for request method
	            if(method == "POST"){

	                // request method is POST
	                // defaultHttpClient
	                DefaultHttpClient httpClient = new DefaultHttpClient();
	                HttpPost httpPost = new HttpPost(url);
	                httpPost.setEntity(new UrlEncodedFormEntity(params));
	                
	                HttpResponse httpResponse = httpClient.execute(httpPost);
	                
	                HttpEntity httpEntity = httpResponse.getEntity();

	                webs = httpEntity.getContent();
	            } else if(method == "GET"){
	                // request method is GET

	                DefaultHttpClient httpClient = new DefaultHttpClient();
	                String paramString = URLEncodedUtils.format(params, "utf-8");
	                url += "?" + paramString;
	                HttpGet httpGet = new HttpGet(url);
	                HttpResponse httpResponse = httpClient.execute(httpGet);
	                HttpEntity httpEntity = httpResponse.getEntity();
	                webs = httpEntity.getContent();
	            }          
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
	        try {

	            BufferedReader reader = new BufferedReader(new InputStreamReader(webs, "utf-8"), 8);
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	            webs.close();
	            json = sb.toString();
	        } catch (Exception e) {
	            Log.e("Buffer Error", "Error converting result " + e.toString());
	        }
	        // try parse the string to a JSON object
	        try {
	            jObj = new JSONObject(json);
	        } catch (JSONException e) {
	            Log.e("JSON Parser", "Error parsing data " + e.toString());
	        }  
	        // return JSON String
	        return jObj;
	    }
	}

	
/*    public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params) {
        // Making HTTP request
    	if(method == "POST"){
	        try {
	                DefaultHttpClient httpClient = new DefaultHttpClient();
	                HttpPost httpPost = new HttpPost(url);
	                httpPost.setEntity(new UrlEncodedFormEntity(params));
	                HttpResponse httpResponse = httpClient.execute(httpPost);
	                httpPost.setHeader("Content-type", "application/json");
	                HttpEntity httpEntity = httpResponse.getEntity();
	
	                webs = httpEntity.getContent();
	            
	        catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        
	        
        	else if(method =="GET"){
                // request method is GET

                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }          
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }        		
    	}
	        
        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(webs, "utf-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            webs.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
           
        }  
        // return JSON String
        return jObj;        
           
    } 
    
    public static String convertURL(String str) {
        String url = null;
        try{
        url = new String(str.trim().replace(" ", "%20").replace("&", "%26")
            .replace(",", "%2c").replace("(", "%28").replace(")", "%29")
            .replace("!", "%21").replace("=", "%3D").replace("<", "%3C")
            .replace(">", "%3E").replace("#", "%23").replace("$", "%24")
            .replace("'", "%27").replace("*", "%2A").replace("-", "%2D")
            .replace(".", "%2E").replace("/", "%2F").replace(":", "%3A")
            .replace(";", "%3B").replace("?", "%3F").replace("@", "%40")
            .replace("[", "%5B").replace("\\", "%5C").replace("]", "%5D")
            .replace("_", "%5F").replace("`", "%60").replace("{", "%7B")
            .replace("|", "%7C").replace("}", "%7D"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return url;
    } */


