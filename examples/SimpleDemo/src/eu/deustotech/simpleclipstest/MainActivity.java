package eu.deustotech.simpleclipstest;

import java.io.File;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import eu.deustotech.clips.Environment;

public class MainActivity extends Activity {
	
	private String getRealFilePath(String filepath) throws FileNotFoundException {
		final String state = android.os.Environment.getExternalStorageState();
		if( android.os.Environment.MEDIA_MOUNTED.equals(state) ) {
			// get the directory of the triple store
			final File topDir = android.os.Environment.getExternalStorageDirectory();
			final String realpath = topDir.getAbsolutePath() + filepath;
			final File file = new File(realpath);
			if( !file.exists() )
				throw new FileNotFoundException("The file doesn't exist in the external storage.");
			return realpath;
		}
		throw new FileNotFoundException("The external storage is not mounted.");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Log.d("clips", Environment.getCLIPSVersion());
		final TextView lbl = (TextView) findViewById(R.id.label);
		lbl.setText( "CLIPS version: " + Environment.getCLIPSVersion() );
		
		// And now, basic usage...
		Environment clips = new Environment();
		clips.eval("(printout t \"Hello CLIPS environment!\" )");
		clips.eval("(printout t (+ 3 4))");
		clips.destroy();
		
		// Checking that the rule file exists
		String factsFilePath;
		try {
			factsFilePath = getRealFilePath( "/files/sample.clp" );
			
			// And now, a little more complex usage...
			final CLIPSTest test = new CLIPSTest(factsFilePath);
			//test.getAllFacts();
			test.assertExample();
			//test.getAllFacts();
			test.modifyAFact();
			test.getAllFacts();
			test.stop();
		} catch (FileNotFoundException e1) {
			Log.d(CLIPSTest.tag, e1.getMessage());
		} catch (Exception e) {
			Log.d(CLIPSTest.tag, e.getMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
