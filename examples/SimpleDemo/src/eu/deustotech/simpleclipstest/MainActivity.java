package eu.deustotech.simpleclipstest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import eu.deustotech.clips.Environment;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Log.d("clips", Environment.getCLIPSVersion());
		final TextView lbl = (TextView) findViewById(R.id.label);
		lbl.setText( "CLIPS version: " + Environment.getCLIPSVersion() );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
