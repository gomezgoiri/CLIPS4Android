package eu.deustotech.simpleclipstest;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import eu.deustotech.clips.Environment;

public class MainActivity extends Activity {
	
	private static final String FILE_NAME = "sample.clp";

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
		
		generateRuleFileInAppFileDir();
		// Checking that the rule file exists
		String factsFilePath;
		try {
			factsFilePath = getFilesDir().getAbsolutePath() + "/" + FILE_NAME;
			
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

	private void generateRuleFileInAppFileDir()
	{
		FileOutputStream destinationFileStream = null;
		InputStream assetsOriginFileStream = null;
		try{
			destinationFileStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
			assetsOriginFileStream = getAssets().open(FILE_NAME);
			
			int aByte;
			while((aByte = assetsOriginFileStream.read())!=-1){
				destinationFileStream.write(aByte);
			}			
		}
		catch (IOException e){
			Log.d(CLIPSTest.tag, e.getMessage());
		}
		finally{
			try
			{
				assetsOriginFileStream.close();
				destinationFileStream.close();
			}
			catch (IOException e){
				e.printStackTrace();
			}			
		}		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
