package eu.deustotech.animalclipsdemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import eu.deustotech.clips.Environment;

public class MainActivity extends Activity {
	
	Environment clips;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.d("clips", Environment.getCLIPSVersion());
		
		//initializeClips();
	}
	
    public static String readFileAsString(String filePath) {
	    String result = "";
	    File file = new File(filePath);
	    if ( file.exists() ) {
	        //byte[] buffer = new byte[(int) new File(filePath).length()];
	        FileInputStream fis = null;
	        try {
	            //f = new BufferedInputStream(new FileInputStream(filePath));
	            //f.read(buffer);
	
	            fis = new FileInputStream(file);
	            char current;
	            while (fis.available() > 0) {
	                current = (char) fis.read();
	                result = result + String.valueOf(current);
	
	            }
	
	        } catch (Exception e) {
	            Log.d("TourGuide", e.toString());
	        } finally {
	            if (fis != null)
	                try {
	                    fis.close();
	                } catch (IOException ignored) {
	            }
	        }
	        //result = new String(buffer);
	    }
	    return result;
	}

	private void initializeClips() {
		//final String sd_path = android.os.Environment.getExternalStorageDirectory().getPath();
		this.clips = new Environment();		
		//this.clips.load( sd_path + "/bcdemo.clp" );
		//this.clips.load( sd_path + "/animaldemo.clp" );
		// If load() method is troublesome, we can try to read the file rules in Java
		//  and evaluate them with CLIPS' eval() method:
		//     PrimitiveValue pv1 = clips.eval("(myFunction foo)");
		//     pv1.retain();
		//     PrimitiveValue pv2 = clips.eval("(myFunction bar)"); 
		//     pv1.retain();
		//     PrimitiveValue pv2 = clips.eval("(myFunction bar)");
		//     ...
		//     pv1.release();
		//this.clips.reset();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void nextState(View view) {
		initializeClips();
		/*final Button btnRestart = (Button) findViewById(R.id.btnRestart);
		btnRestart.setEnabled(true);
		
		final Button btnPrevious = (Button) findViewById(R.id.btnPrevious);
		btnPrevious.setEnabled(true);
		
		final TextView lbl = (TextView) findViewById(R.id.label);*/
	}

}
