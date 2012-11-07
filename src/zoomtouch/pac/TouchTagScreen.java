package zoomtouch.pac;

import zoomtouch.pac.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class TouchTagScreen extends Activity {
	private static final String TAG = "TouchTagScreen";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.touchtagscreen);

		Animation fadeIn = new AlphaAnimation(0, 1);
		fadeIn.setDuration(1000);

		
		  new Thread(new Runnable() { public void run() {
		  
		  try { Thread.sleep(2000); } catch (final InterruptedException e) { }
		  
		  // Update the progress bar 
		  runOnUiThread(new Runnable() { public void
		  run() { handleButtonClick(); } }); } }).start();
		 
	}

	private void handleButtonClick() {
		Log.d(TAG, "Starting Call");
		startActivity(new Intent(this, zoomtouch.pac.ZoomTouch.class));
	}
}