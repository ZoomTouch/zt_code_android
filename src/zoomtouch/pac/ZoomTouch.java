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
import android.widget.Button;
import android.widget.TextView;


public class ZoomTouch extends Activity {

	private static final String TAG = "ZoomTouch";
	private ArrayList<String> Data = new ArrayList<String>(4);
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);         
        
        WebView webview = new WebView(this);
        setContentView(webview);      
        
        webview.loadUrl("http://facebook.com");
        
      // setContentView(R.layout.main);       
       
    /*   final Button callButton = (Button) findViewById(R.id.telephone_button);       
       
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
       });*/

     //  @SuppressWarnings("unused")
     //  AsyncTask<String, Integer, Long> network = new NetworkAccessManager(this).execute("http://wishbuddy.get2q.com/reply.php");      
    }
    
    public void Parse(String data) {
		Log.d(TAG, "Main Parse()" );		
		@SuppressWarnings("unused")
		AsyncTask<String, String, Long> parser = new XMLParser(this).execute(data);			
	}    
    
    public void showListView(String[] listItem) {
    	 TextView myTextView = (TextView) findViewById(R.id.OfferView);
    	 myTextView.append("\n");
         myTextView.append(listItem[0]);
         Log.d(TAG, "show list view:::"+ listItem[0] );	
         if(listItem[0] != null)
         Data.add(listItem[0]);
	}
}

