package zoomtouch.pac;

import java.io.IOException;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.net.Uri;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class TAGReader extends Activity {
	private static final String TAG = "TAGREADER";
  static private final String[] PREFIXES={"http://www.", "https://www.",
                                          "http://", "https://",
                                          "tel:", "mailto:",
                                          "ftp://anonymous:anonymous@",
                                          "ftp://ftp.", "ftps://",
                                          "sftp://", "smb://",
                                          "nfs://", "ftp://",
                                          "dav://", "news:",
                                          "telnet://", "imap:",
                                          "rtsp://", "urn:",
                                          "pop:", "sip:", "sips:",
                                          "tftp:", "btspp://",
                                          "btl2cap://", "btgoep://",
                                          "tcpobex://",
                                          "irdaobex://",
                                          "file://", "urn:epc:id:",
                                          "urn:epc:tag:",
                                          "urn:epc:pat:",
                                          "urn:epc:raw:",
                                          "urn:epc:", "urn:nfc:"};
//NFC stuffs
	private static NfcAdapter m_adapter;
	private static PendingIntent m_pendingIntent;
	private static IntentFilter[] m_filters;
	private static String[][] m_techLists;
	private String m_url;
	
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
 // Initialize NFC
    m_adapter = NfcAdapter.getDefaultAdapter(this);
    
    // Now create a PendingIntent that will be delivered to the activity.
    // The NFC stack fills the intent details found in a tag.
    m_pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
    		getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    
    // Setup an intent filter for all MIME based dispatches
    IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
    
    try {
		ndef.addDataType("*/*");
	} catch (MalformedMimeTypeException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    m_filters = new IntentFilter[] { ndef, };
    
    // Setup a tech list for all NFC tags
    m_techLists = new String[][] {new String[] { MifareUltralight.class.getName() } };
  }
  
  @Override
  protected void onNewIntent(Intent intent) {
	  String action = intent.getAction();
	  
	  if(NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)){
		  Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		  Ndef ndefTag = Ndef.get(tagFromIntent);
		  
		  try {
			ndefTag.connect();
			NdefMessage readNdefMsg = ndefTag.getNdefMessage();
			NdefRecord readNdefRecords[] = readNdefMsg.getRecords();
			
			for(NdefRecord ndefRec : readNdefRecords)
			{
				
				//ndefRec.getType().equals(NdefRecord.RTD_URI) 
				{
					byte[] ndefPayload = ndefRec.getPayload();
					m_url = new String(ndefPayload, "UTF8");
					Log.d(TAG,m_url);
					Intent resultIntent = new Intent();
					
					
					resultIntent.putExtra("TAG_URL", m_url);
					setResult(Activity.RESULT_OK, resultIntent);
					finish();
					
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
  }
  
  @Override
  public void onResume() {
    super.onResume();    
    m_adapter.enableForegroundDispatch(this, m_pendingIntent, m_filters, m_techLists);
  }
  
  @Override
  public void onPause() {
	  super.onPause();
		m_adapter.disableForegroundDispatch(this);
  }
  
  private byte[] buildUrlBytes() {
    String raw=getIntent().getStringExtra(Intent.EXTRA_TEXT);
    int prefix=0;
    String subset=raw;
    
    for (int i=0;i<PREFIXES.length;i++) {
      if (raw.startsWith(PREFIXES[i])) {
        prefix=i+1;
        subset=raw.substring(PREFIXES[i].length());
        
        break;
      }
    }
    
    byte[] subsetBytes=subset.getBytes();
    byte[] result=new byte[subsetBytes.length+1];
    
    result[0]=(byte)prefix;
    System.arraycopy(subsetBytes, 0, result, 1, subsetBytes.length);
    
    return(result);
  }
  
  static class WriteTask extends AsyncTask<Void, Void, Void> {
    Activity host=null;
    NdefMessage msg=null;
    Tag tag=null;
    String text=null;
    
    WriteTask(Activity host, NdefMessage msg, Tag tag) {
      this.host=host;
      this.msg=msg;
      this.tag=tag;
    }
    
    @Override
    protected Void doInBackground(Void... arg0) {
      int size=msg.toByteArray().length;

      try {
        Ndef ndef=Ndef.get(tag);
        
        if (ndef==null) {
          NdefFormatable formatable=NdefFormatable.get(tag);
          
          if (formatable!=null) {
            try {
              formatable.connect();
              
              try {
                formatable.format(msg);
              }
              catch (Exception e) {
                text="Tag refused to format";
              }
            }
            catch (Exception e) {
              text="Tag refused to connect";
            }
            finally {
              formatable.close();
            }
          }
          else {
            text="Tag does not support NDEF";
          }
        }
        else {
          ndef.connect();

          try {
            if (!ndef.isWritable()) {
              text="Tag is read-only";
            }
            else if (ndef.getMaxSize()<size) {
              text="Message is too big for tag";
            }
            else {
              ndef.writeNdefMessage(msg);
            }
          }
          catch (Exception e) {
            text="Tag refused to connect";
          }
          finally {
            ndef.close();
          }
        }
      }
      catch (Exception e) {
        Log.e("URLTagger", "Exception when writing tag", e);
        text="General exception: "+e.getMessage();
      }
      
      return(null);
    }
    
    @Override
    protected void onPostExecute(Void unused) {
      if (text!=null) {
        Toast.makeText(host, text, Toast.LENGTH_SHORT).show();
      }
      
      host.finish();
    }
  }
}

