package eu.deustotech.animalclipsdemo;

import eu.deustotech.animalclipsdemo.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Log.d("clips", Environment.getCLIPSVersion());
		final TextView lbl = (TextView) findViewById(R.id.label);
		//lbl.setText( getResources().getString(R.string.WelcomeMessage) );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
