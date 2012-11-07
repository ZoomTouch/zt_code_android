package zoomtouch.pac;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.util.Log;

public class NetworkAccessManager extends AsyncTask<String, Integer, Long>{

	private static final String TAG = "NetworkManager";	
	public String data;
	private ZoomTouch activity;
	private String phoneNumber;
	private String email;
	
	public String getPhoneNumber(){
		return phoneNumber;
	}
	
	public String getEmail(){
		return email;
	}
	
	public NetworkAccessManager (ZoomTouch activity) {
		   this.activity = activity;
		  }

	public void initializeNetwork(String urls){

		Log.d(TAG, "initializeNetwork()");
		try {
			Log.d(TAG, urls );
			
			urls = "http://" + urls;
			
			URL url = new URL(urls);
			Log.d(TAG, urls );
			URLConnection conn = url.openConnection();			
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					
			String line = null;	
		
			while ((line = rd.readLine()) != null) {
				Log.d(TAG, line );
				/*if(line.contains("email"))
					email = line.*/
				
				if(data == null)
				data = line;
				else 
				data += line;						
			}
			
			publishProgress(1);
		
		} catch (Exception ex){
			String err = (ex.getMessage()==null)?"Socket failed":ex.getMessage();
			Log.e(TAG,err);  
		}
	}

	@Override
	protected Long doInBackground(String...url) {
		Log.d(TAG, "doInBackground()" );		
		initializeNetwork(url[0]);
		return (long) 1;
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		Log.d(TAG, "Network onProgressUpdate()" );
		Log.d(TAG, data);
		activity.Parse(data);		
	}	
	
	/*@Override
	protected void onPostExecute(Void) {
		Log.d(TAG, "onPostExecute()");
	}*/
}
