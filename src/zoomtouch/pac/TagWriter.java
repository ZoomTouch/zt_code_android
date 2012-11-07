package zoomtouch.pac;
import zoomtouch.pac.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TagWriter extends Activity{
	
	private static final String TAG = "TagWriter";
	Button   mButton; 
	EditText mEditEmail;
	EditText mEditPhoneNumber;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        setContentView(R.layout.tagwriter);

        mButton = (Button)findViewById(R.id.button_send);
        mEditEmail   = (EditText)findViewById(R.id.email); 
        mEditPhoneNumber = (EditText)findViewById(R.id.phone_number); 
     
        mButton.setOnClickListener( 
            new View.OnClickListener() 
            { 
                public void onClick(View view) 
                {
                    formatUrl(mEditPhoneNumber.getText().toString(),mEditEmail.getText().toString());
                } 
            }); 
    }
    
	private void formatUrl(String phone, String email) {
		//http://feswa.com/ztbeta/api/create.html?model=profiles&email=+email+&user_pass=89b4c0ef5aeaac4b33344bd33f567a97:c7&imei=343&phone_no=+phone+&first_name=Aditya&last_name=kolachana&address=Otaneimi,Espoo,2100,Finland&language=English
		Log.d(TAG, phone + email);		
	}
}
