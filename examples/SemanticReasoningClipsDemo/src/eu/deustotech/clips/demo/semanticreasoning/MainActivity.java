package eu.deustotech.clips.demo.semanticreasoning;

import java.io.File;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;
import eu.deustotech.clips.demo.semanticreasoning.reasoner.SemanticReasoner;

public class MainActivity extends Activity {
	
	private SemanticReasoner reasoner;

	private String getRealFilePath(String filepath) throws FileNotFoundException {
		final String state = android.os.Environment.getExternalStorageState();
		if( android.os.Environment.MEDIA_MOUNTED.equals(state) ) {
			// get the directory of the triple store
			final File topDir = android.os.Environment.getExternalStorageDirectory();
			final String realpath = topDir.getAbsolutePath() + filepath;
			final File file = new File(realpath);
			if( !file.exists() )
				throw new FileNotFoundException("The file " + realpath + " doesn't exist in the external storage.");
			return realpath;
		}
		throw new FileNotFoundException("The external storage is not mounted.");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try {
			final String rdfsRulesFile = getRealFilePath( "/files/owl.clp" );
			final String owlsRulesFile = getRealFilePath( "/files/rdfs.clp" );
			
			this.reasoner = new SemanticReasoner( rdfsRulesFile, owlsRulesFile );
			this.reasoner.start();
			
			this.reasoner.addTripleToKnowledgeBase("(. mm:Dog rdfs:subClassOf mm:Animal)");
			this.reasoner.addTripleToKnowledgeBase("(. mm:brother rdf:type owl:SymmetricProperty)");
			this.reasoner.addTripleToKnowledgeBase("(. mm:lagun rdf:type mm:Dog)");
			this.reasoner.addTripleToKnowledgeBase("(. mm:max mm:brother mm:lagun)");
			
			this.reasoner.getBySubject("mm:lagun");
		} catch (FileNotFoundException e) {
			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	protected void onDestroy() {
		if( this.reasoner != null ) {
			this.reasoner.stop();
		}
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
