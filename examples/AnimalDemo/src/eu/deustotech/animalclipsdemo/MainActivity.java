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
		final String sd_path = android.os.Environment.getExternalStorageDirectory().getPath();
		
		 try {

		 this.clips = new Environment();
		 Log.d( "clips", "1" );
	
		  } catch (RuntimeException e) {
			  for(StackTraceElement t: e.getStackTrace() ) {
				  Log.e( "clips", t.toString() );
			  }
		  }
		
		//this.clips.load( sd_path + "/bcdemo.clp" );
		//Log.d( "clips", "2" );
		//this.clips.load( sd_path + "/animaldemo.clp" );
		//Log.d( "clips", "3" );
		//this.clips.reset();
		//final String s = readFileAsString( sd_path + "/animaldemo.clp");
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
