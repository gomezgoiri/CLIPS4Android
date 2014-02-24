package eu.deustotech.clips.demo.semanticreasoning;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
	
	public static String logLabel = "SemanticReasoningDemo";
	private static String appRootDirectory = "/files";
	private SemanticReasoner reasoner = null;
	// To ensure that just a Thread uses the reasoner at a time:
	// (JIC, I'm not sure whether Environment and the underlying code is thread safe or not)
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	
	// Measure times
	final private boolean logTimeMeasures = true; // Change this in development time to avoid time logs 
	private Timer globalTimer = null;
	
	public MainActivity() {
		super();
		init();
	}
	
	// This is a workaround for the ServiceLoader
	// FIXME use the http://developer.android.com/reference/java/util/ServiceLoader.html
	private void init() {
		RDFWriterRegistry.getInstance().add( new CLPWriterFactory() );
		RDFParserRegistry.getInstance().add( new CLPParserFactory() );
		RDFWriterRegistry.getInstance().add( new TurtleWriterFactory() );
		RDFParserRegistry.getInstance().add( new TurtleParserFactory() );
		RDFWriterRegistry.getInstance().add( new RDFXMLWriterFactory() );
		RDFParserRegistry.getInstance().add( new RDFXMLParserFactory() );
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.globalTimer = new Timer("All the process");
		initializeReasoner();
		loadSemanticContent();
		inferAndWrite();
	}
	
	private void createRootDirectoryIfDoesNotExist() throws IOException {
		final String state = android.os.Environment.getExternalStorageState();
		if( android.os.Environment.MEDIA_MOUNTED.equals(state) ) {
			// get the directory of the triple store
			final File topDir = android.os.Environment.getExternalStorageDirectory();
			final String realpath = topDir.getAbsolutePath() + MainActivity.appRootDirectory;
			final File file = new File(realpath);
			if( !file.exists() ) {
				file.mkdirs(); // it creates parent folders too
			}
		} else throw new IOException("The external storage is not mounted.");
	}
	
	private String getRealFilePath(String filename) throws IOException {
		final String state = android.os.Environment.getExternalStorageState();
		if( android.os.Environment.MEDIA_MOUNTED.equals(state) ) {
			// get the directory of the triple store
			final File topDir = android.os.Environment.getExternalStorageDirectory();
			final String realpath = topDir.getAbsolutePath() + MainActivity.appRootDirectory + "/" + filename;
			return realpath;
		}
		throw new IOException("The external storage is not mounted.");
	}
	
	private String getRealFilePathCreatingIfDoesNotExist(String filename) throws IOException {
		final String realpath = getRealFilePath(filename);
		
		final File file = new File(realpath);
		if( !file.exists() ) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new FileNotFoundException("The unexisting file '" + realpath + "' could not be created");
			}
			
			InputStream input;
			try {
				input = getResources().getAssets().open(filename);
			} catch (IOException e) {
				throw new FileNotFoundException("That's weird, the file '" + filename + "' is not available as an asset.");
			}
			
			final OutputStream output = new FileOutputStream(file);
			
			try {
				final byte[] buffer = new byte[1024]; // Adjust if you want
			    int bytesRead;
			    while ((bytesRead = input.read(buffer)) != -1) {
			        output.write(buffer, 0, bytesRead);
			    }
			} catch (IOException e) {
				throw new IOException("Not able to write the file '" + realpath + "'.");
			} finally {
				output.close();
			}
		}
		// At this point, if it didn't exist, it does now
		return realpath;
	}
	
	private void initializeReasoner() {		
		executor.submit( new Runnable() {
			@Override
			public void run() {
				try {
					createRootDirectoryIfDoesNotExist();
					final String rdfsRulesFile = getRealFilePathCreatingIfDoesNotExist( "owl.clp" );
					final String owlsRulesFile = getRealFilePathCreatingIfDoesNotExist( "rdfs.clp" );
					
					final Timer t = new Timer("Loading CLP files");
					reasoner = new SemanticReasoner( rdfsRulesFile, owlsRulesFile );
					reasoner.start();
					t.end(logTimeMeasures);
				} catch (IOException e) {
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
						final Timer t = new Timer("Loading wine ont");
						final String wine = SemanticLoader.readURL( su );
						t.end(logTimeMeasures);
						
						final Timer t2 = new Timer("Assertions");
						reasoner.assertKnowledge( wine );
						reasoner.assertKnowledge( getSampleIndividuals() );
						t2.end(logTimeMeasures);
						
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
						final Timer t = new Timer("Reasoning and getting serialization");
						final ByteArrayOutputStream kb = reasoner.getKnowledgeBaseSerialized();
						t.end(logTimeMeasures);
						
						Log.d(MainActivity.logLabel,  "Started writing...");
						final Timer t2 = new Timer("Writing file");
						
						// http://source.android.com/devices/tech/storage/#multi-user-external-storage
						// Warning:
						//   Starting in Android 4.2, devices can support multiple users.
						//   As a consequence, you won't see any file if you mount the external storage folder.
						//   However, you must be able to check and get it using DDMS's file explorer.
						//   In my case, the folder was located under /mnt/shell/emulated/0
						SemanticWriter.writeFile( new ByteArrayInputStream(kb.toByteArray()), getRealFilePath("out.n3"), RDFFormat.TURTLE );
						//SemanticWriter.writeAnything( kb, os );
						t2.end(logTimeMeasures);
						
						Log.d(MainActivity.logLabel,  "Finished writing...");
						
						globalTimer.end(logTimeMeasures);
						
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

class Timer {
	static String LABEL = "MEASUREMENTS";
	
	final String logMsg;
	long startTime;
	long endTime;
	
	public Timer(String logMsg) {
		start();
		this.logMsg = logMsg;
	}
	
	private void start() {
		this.startTime = System.currentTimeMillis();
	}
	
	protected void end(boolean andLog) {
		this.endTime = System.currentTimeMillis();
		if (andLog)
			Log.d(Timer.LABEL, this.logMsg + ": " + (this.endTime - this.startTime));
	}
}