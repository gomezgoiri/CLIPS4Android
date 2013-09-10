package eu.deustotech.clips.demo.semanticreasoning;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParserRegistry;
import org.openrdf.rio.RDFWriterRegistry;
import org.openrdf.rio.rdfxml.RDFXMLParserFactory;
import org.openrdf.rio.rdfxml.RDFXMLWriterFactory;
import org.openrdf.rio.turtle.TurtleParserFactory;
import org.openrdf.rio.turtle.TurtleWriterFactory;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
import es.deusto.deustotech.rio.clips.CLPParserFactory;
import es.deusto.deustotech.rio.clips.CLPWriterFactory;
import eu.deustotech.clips.demo.semanticreasoning.reasoner.SemanticReasoner;
import eu.deustotech.clips.demo.semanticreasoning.util.SemanticLoader;
import eu.deustotech.clips.demo.semanticreasoning.util.SemanticWriter;

public class MainActivity extends Activity {
	
	static {
		RDFWriterRegistry.getInstance().add( new CLPWriterFactory() );
		RDFParserRegistry.getInstance().add( new CLPParserFactory() );
		RDFWriterRegistry.getInstance().add( new TurtleWriterFactory() );
		RDFParserRegistry.getInstance().add( new TurtleParserFactory() );
		RDFWriterRegistry.getInstance().add( new RDFXMLWriterFactory() );
		RDFParserRegistry.getInstance().add( new RDFXMLParserFactory() );
	}
	
	private static String logLabel = "SemanticReasoningDemo";
	private SemanticReasoner reasoner = null;
	// To ensure that just a Thread uses the reasoner at a time:
	// (JIC, I'm not sure whether Environment and the underlying code is thread safe or not)
	private ExecutorService executor = Executors.newSingleThreadExecutor();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initializeReasoner();
		loadSemanticContent();
		inferAndWrite();
	}
	
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
	
	private void initializeReasoner() {		
		executor.submit( new Runnable() {
			@Override
			public void run() {
				try {
					final String rdfsRulesFile = getRealFilePath( "/files/owl.clp" );
					final String owlsRulesFile = getRealFilePath( "/files/rdfs.clp" );
					
					reasoner = new SemanticReasoner( rdfsRulesFile, owlsRulesFile );
					reasoner.start();			
				} catch (FileNotFoundException e) {
					Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private void loadSemanticContent() {
		executor.submit( new Runnable() {
			@Override
			public void run() {
				if( reasoner==null ) {
					Log.d(MainActivity.logLabel, "Unable to load content: initialize CLIPS first.");
				} else {
					try {
						// http://www.w3.org/TR/owl-guide/wine.rdf only works with the browser, not really sure why
						final URL su = new URL( "http://krono.act.uji.es/Links/ontologies/wine.owl/at_download/file" );
						final String wine = SemanticLoader.readURL( su );
						reasoner.assertKnowledge( wine );
						reasoner.assertKnowledge( getSampleIndividuals() );
						
						/*// Former sample:
						this.reasoner.assertKnowledge("(. mm:Dog rdfs:subClassOf mm:Animal)");
						this.reasoner.assertKnowledge("(. mm:brother rdf:type owl:SymmetricProperty)");
						this.reasoner.assertKnowledge("(. mm:lagun rdf:type mm:Dog)");
						this.reasoner.assertKnowledge("(. mm:max mm:brother mm:lagun)");
						this.reasoner.getBySubject("mm:lagun");
						*/
					} catch (Exception e) {
						Log.e(MainActivity.logLabel, e.getMessage());
					}
				}
			}
		});
	}
	
	private void inferAndWrite() {
		executor.submit( new Runnable() {
			@Override
			public void run() {
				if( reasoner==null ) {
					Log.d(MainActivity.logLabel, "Unable to infer and write content: initialize CLIPS first.");
				} else {
					try {
						//final String blah = this.reasoner.getBySubject("<http://sample.com/foo#larioja>");
						final String kb = reasoner.getKnowledgeBaseSerialized();
						
						Log.d(MainActivity.logLabel,  "Started writing...");
						final OutputStream os = new FileOutputStream( android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/out.n3" );
						SemanticWriter.write( kb, os, RDFFormat.TURTLE );
						//SemanticWriter.writeAnything( kb, os );
						Log.d(MainActivity.logLabel,  "Finished writing...");
						
						runOnUiThread(
							new Runnable() {
								public void run() {
									Toast.makeText(getBaseContext(), "Inferred content written in file", Toast.LENGTH_LONG).show();
								}
							}
						);
					} catch (Exception e) {
							Log.e(MainActivity.logLabel, e.getMessage());
					}
				}
			}
		});
	}
	
	private String getSampleIndividuals() {
		final String sampleURI = "http://sample.com/foo#";
		final String wineURI = "http://krono.act.uji.es/Links/ontologies/wine.owl#";
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		final PrintStream ps = new PrintStream(os);
		
		ps.format("(. <%snavarra> <%sadjacentRegion> <%slarioja> )", sampleURI, wineURI, sampleURI);
		return new String(os.toByteArray());
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
