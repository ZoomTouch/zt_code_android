package zoomtouch.pac;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class JsonParser extends AsyncTask<String, String, Long>{

	private static final String TAG = "JsonParser";
	
	private ZoomTouch activity;
	
	 // JSON Node names
    private static final String TAG_ID = "tag_id";
    private static final String TAG_UNIQUE_ID = "unique_id";
    private static final String TAG_HASH_SHA1 = "hash_sha1";
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email_id";
    private static final String TAG_PHONE_NUMBER = "phone_number";
    private static final String TAG_VISIBILITY = "visibility";
    
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    
 // contacts JSONArray
    JSONArray contacts = null;
	
	public JsonParser (ZoomTouch activity) {
		   this.activity = activity;
		  }
	
	public JSONObject getJSONFromData(String data) {
		Log.d(TAG, "getJSONFromUrl()" );
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(data);
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing data " + e.toString());
        }
 
        // return JSON String
        return jObj;
 
    }

	void Initialize(String data){
		
		Log.d(TAG, "Initialize()" );	
		// getting JSON string from URL
		JSONObject json = getJSONFromData(data);
		
		Log.d(TAG, "gotjsonobject()" );	
		
		try {
			Log.d(TAG, "getting strings from json object()" );	
		        // Storing each json item in variable
		        //String id = json.getString(TAG_ID);
		      //  publishProgress(id);
		       // String uniqueid = json.getString(TAG_UNIQUE_ID);
		       // publishProgress(uniqueid);
		        //String hashsha1 = json.getString(TAG_HASH_SHA1);
		      //  publishProgress(hashsha1);
		        String name = json.getString(TAG_NAME);
		        publishProgress(name);
		        String email = json.getString(TAG_EMAIL);
		        publishProgress(email);
		        String home = json.getString(TAG_PHONE_NUMBER);
		        publishProgress(home);		        
		      //  String visibility = json.getString(TAG_VISIBILITY);
		     //   publishProgress(visibility);
		        
		        
		        Log.d(TAG, "completed getting strings from json object()" );	
		        
		        
		    
		} catch (JSONException e) {
		    e.printStackTrace();
		}
	}	

	@Override
	protected Long doInBackground(String... params) {
		Log.d(TAG, "doInBackground()" + params[0]);		
		Initialize(params[0]);
		return (long) 1;
	}

	@Override
	protected void onProgressUpdate(String... listItem) {
		Log.d(TAG, "onProgressUpdate()" );
		for (String s : listItem)
			Log.d(TAG, s);
		
		activity.showListView(listItem);
	}
}



