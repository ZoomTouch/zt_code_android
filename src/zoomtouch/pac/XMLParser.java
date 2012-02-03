package zoomtouch.pac;


import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import android.os.AsyncTask;
import android.util.Log;

public class XMLParser extends AsyncTask<String, String, Long>{

	private static final String TAG = "Parser";
	
	private ZoomTouch activity;
	
	public XMLParser (ZoomTouch activity) {
		   this.activity = activity;
		  }

	void Initialize(String data){

		Log.d(TAG, "initializeParser()" +  data);
		
		try{		
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();					
			InputSource is = new InputSource(new StringReader(data));			
			Document doc = db.parse(is);			
			doc.getDocumentElement().normalize();			
			NodeList nodeList = doc.getElementsByTagName("*");
			int length = nodeList.getLength();		
			
			Log.d(TAG, "length:" + length);		
			
			for(int i=1;i<length;i++){				
			Node n = nodeList.item(i).getFirstChild();			
		    if (n != null){
		    	publishProgress(n.getNodeValue());
		    	Log.d(TAG, "Child"+ i + ":" + n.getNodeValue());
		    	}
			}		      
		} 
		catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
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
		activity.showListView(listItem);		
	}
}


