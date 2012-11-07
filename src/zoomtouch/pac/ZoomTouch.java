package zoomtouch.pac;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

//import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import zoomtouch.pac.R;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.Button;

public class ZoomTouch extends Activity {

	private static final String TAG = "ZoomTouch";
	private ArrayList<String> Data = new ArrayList<String>(4);
	private String phoneNumber;
	private String email;
	private String results;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
       // TAGReader reader = new TAGReader();
        //startActivity(new Intent(this, zoomtouch.pac.TAGReader.class));
        Intent tagReaderIntent = new Intent(this, TAGReader.class);

        
        startActivityForResult(tagReaderIntent, 9);

       /* try {
            HttpClient client = new DefaultHttpClient(); 
            String url = "http://tiny.pl/htk"; //The tiny URL 
            String getURL = "http://untiny.me/api/1.0/extract?url="+url+"&format=text"; //The API service URL
            HttpGet get = new HttpGet(getURL);
            HttpResponse responseGet = client.execute(get);  
            HttpEntity resEntityGet = responseGet.getEntity();  
            if (resEntityGet != null) {  
            	 String response = EntityUtils.toString(resEntityGet);
                 Log.i("GET RESPONSE",response);
               //  output.setText(response); //This is a TextView      
            }
        } catch(Exception e) {
           // output.setText("exception");
        }*/

        //@SuppressWarnings("unused")
        //AsyncTask<String, Integer, Long> network = new NetworkAccessManager(this).execute("http://feswa.com/ztbeta/api/view.html?model=profiles&id=6&format=json");
    	//phoneNumber = ((NetworkAccessManager) network).getPhoneNumber();
    	//email =((NetworkAccessManager) network).getEmail();
    	
    	//startActivity(new Intent(this, zoomtouch.pac.TagWriter.class));
        
//        setContentView(R.layout.main);   
//        
//        WebView webview = (WebView) findViewById(R.id.webview);        
//        WebSettings webSettings = webview.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webview.setWebViewClient(new WebViewClient());
//        webview.loadUrl("http://www.feswa.com/ztbeta/api/view?model=tagDetail&id=2323074437");
//       // webview.loadUrl("http://www.newlc.com");
//        
//        showButtons();
//        
//        Animation fadeIn = new AlphaAnimation(0, 1);
//        fadeIn.setDuration(1000);
    }
   // @SuppressLint("SetJavaScriptEnabled")
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    		super.onActivityResult(requestCode, resultCode, data);
    		if (resultCode == Activity.RESULT_OK) {
    			Log.d(TAG, data.getStringExtra("TAG_URL"));
    			
    			String url = data.getStringExtra("TAG_URL");
    			
    			Log.d(TAG,url.substring(1));
    			
    			//AsyncTask<String, Integer, Long> network = new NetworkAccessManager(this).execute(url.substring(1));
    			
    			setContentView(R.layout.main);
    			WebView webview = (WebView) findViewById(R.id.webview);
    	        WebSettings webSettings = webview.getSettings();
    	        webSettings.setJavaScriptEnabled(true);
    	        webSettings.setLoadWithOverviewMode(true);
    			webSettings.setUseWideViewPort(true);
    	        webview.setWebViewClient(new WebViewClient());
    	        Log.d(TAG, url.substring(1));
    	        webview.loadUrl("http://" + url.substring(1));
    		}
    }
    @Override
    public void onResume() {
    		super.onResume();
    		Log.d(TAG,"zoomtouch onResume");
//    		AsyncTask<String, Integer, Long> network = new NetworkAccessManager(this).execute("http://feswa.com/ztbeta/api/view.html?model=profiles&id=6&format=json");
//    		WebView webview = (WebView) findViewById(R.id.webview);        
//          WebSettings webSettings = webview.getSettings();
//          webSettings.setJavaScriptEnabled(true);
//          webview.setWebViewClient(new WebViewClient());
//          webview.loadUrl("http://www.feswa.com/ztbeta/api/view?model=tagDetail&id=2323074437");
    }
    
    public void showButtons(){
        final Button callButton = (Button) findViewById(R.id.telephone_button);       
        
        callButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.telephone));
        
        callButton.setOnClickListener(new View.OnClickListener() {   	 

 		public void onClick(View v) {
 			 Log.d(TAG, "Starting Call" );			
 			 String telephone = Data.get(5);
 			 Log.d(TAG, telephone);
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
 			 String url = Data.get(1);
 			 Log.d(TAG, url);
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
 			 String url = "http://feswa.com/ztbeta/api/view.html?model=profiles&id=6&format=html";
 			 Intent i = new Intent(Intent.ACTION_VIEW);
 			 i.setData(Uri.parse(url));
 			 startActivity(i);
 		}       
        });
    	
    }
    
    public void Parse(String data) {
		Log.d(TAG, "Main Parse()" + data );		
		@SuppressWarnings("unused")
		//AsyncTask<String, String, Long> parser = new XMLParser(this).execute(data);
		// Creating JSON Parser instance
		AsyncTask<String, String, Long> jsonparser = new JsonParser(this).execute(data);
	}    
    
    public void showListView(String[] arrayList) {    	
    	for (String s : arrayList){   	        	
         Data.add(s);
    	}
    	
    	//if(!Data.isEmpty())
    		//showButtons();
	}
}

