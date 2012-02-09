package zoomtouch.pac;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import zoomtouch.pac.R;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ZoomTouch extends Activity {

	private static final String TAG = "ZoomTouch";
	private ArrayList<String> Data = new ArrayList<String>(4);
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);         
        
        setContentView(R.layout.main);   
        
        WebView webview = (WebView) findViewById(R.id.webview);        
        webview.loadUrl("http://nfc.get2q.com/index.php?service_id=1&tag_id=1");     
      
              
      final Button callButton = (Button) findViewById(R.id.telephone_button);       
       
       callButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.telephone));
       
       callButton.setOnClickListener(new View.OnClickListener() {   	 

		public void onClick(View v) {
			 Log.d(TAG, "Starting Call" );			
			 String telephone = Data.get(1);
			 String uri = "tel:" + telephone.trim() ;			
			 Intent intent = new Intent(Intent.ACTION_CALL);
			 intent.setData(Uri.parse(uri));
			 startActivity(intent);
		}       
       });       
       
       final Button browserButton = (Button) findViewById(R.id.internet_button);
       
       browserButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.internet));
       
       browserButton.setOnClickListener(new View.OnClickListener() {   	 

		public void onClick(View v) {
			 Log.d(TAG, "Connecting to Bonnier" );			
			 String url = Data.get(2);
			 Intent i = new Intent(Intent.ACTION_VIEW);
			 i.setData(Uri.parse(url));
			 startActivity(i);
		}       
       });       
       
       final Button shopButton = (Button) findViewById(R.id.shop_button); 
       
       shopButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.shop));
       
       shopButton.setOnClickListener(new View.OnClickListener() {   	 

		public void onClick(View v) {
			 Log.d(TAG, "Connecting to Zoomtouch" );			
			 String url = "http://wishbuddy.get2q.com";
			 Intent i = new Intent(Intent.ACTION_VIEW);
			 i.setData(Uri.parse(url));
			 startActivity(i);
		}       
       });

       //@SuppressWarnings("unused")
       AsyncTask<String, Integer, Long> network = new NetworkAccessManager(this).execute("http://wishbuddy.get2q.com/reply.php");      
    }
    
    public void Parse(String data) {
		Log.d(TAG, "Main Parse()" );		
		@SuppressWarnings("unused")
		AsyncTask<String, String, Long> parser = new XMLParser(this).execute(data);			
	}    
    
    public void showListView(String[] listItem) {
    	if(listItem[0] != null)
         Data.add(listItem[0]);
	}
}

