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
	
	public NetworkAccessManager (ZoomTouch activity) {
		   this.activity = activity;
		  }

	public void initializeNetwork(String[] urls){

		Log.d(TAG, "initializeNetwork()");
		try {
			URL url = new URL("http://wishbuddy.get2q.com/reply.php"); 
			URLConnection conn = url.openConnection();			
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					
			String line = null;	
		
			while ((line = rd.readLine()) != null) {				
				if(data == null)
				data = line;
				else 
				data += line;						
			}
			
			publishProgress(1);
		
		} catch (UnknownHostException e) {
			Log.e(TAG, "Server Not Found");
		} catch (IOException e) {
			Log.e(TAG, "Couldn't open socket");
		}
	}

	@Override
	protected Long doInBackground(String...url) {
		Log.d(TAG, "doInBackground()" );
		initializeNetwork(url);
		return (long) 1;
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		Log.d(TAG, "Network onProgressUpdate()" );	
		activity.Parse(data);			
	}	
	
	/*@Override
	protected void onPostExecute(Void) {
		Log.d(TAG, "onPostExecute()");
	}*/
}
