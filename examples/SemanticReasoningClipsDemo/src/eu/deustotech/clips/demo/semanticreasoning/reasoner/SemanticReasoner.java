package eu.deustotech.clips.demo.semanticreasoning.reasoner;

import java.util.HashSet;
import java.util.Set;

import android.util.Log;
import eu.deustotech.clips.Environment;
import eu.deustotech.clips.PrimitiveValue;

/*
 * Sample usage outside this library;
 * 
 * clips -f ficherodereglas.clp
 * 
 * CLIPS> (list-defrules)
 * CLIPS> (assert (. perro rdfs:subClassOf animal))
 * CLIPS> (assert (. blb rdf:type perro))
 * CLIPS> (run)
 * CLIPS> (facts)
 */
public class SemanticReasoner {
	
	final public static String logLabel = "SemanticReasoner"; 
	
	final private Environment clips;
	final private String[] rulesFilePaths;
	
	public SemanticReasoner(String rdfsRulesFilePath, String owlRulesFilePath) {
		this.clips = new Environment();
		this.rulesFilePaths = new String[] { rdfsRulesFilePath, owlRulesFilePath };
	}
	
	public void start() {
		for(String fileToLoad: this.rulesFilePaths) {
			Log.d( SemanticReasoner.logLabel, "Loading rule file '" + fileToLoad + "'... " );
			this.clips.load(fileToLoad);
			Log.d( SemanticReasoner.logLabel, "Loaded" );
		}
		this.clips.reset();
	}
	
	public void addFileToKnowledgeBase(String filePath) {
		
	}
	
	public void addTripleToKnowledgeBase(String triple) {
		this.clips.assertString(triple);
		
	}
	
	public String getBySubject(String subj) {
		final Set<String> trips = new HashSet<String>();
		this.clips.run();
		
		final String evalStr = "(find-all-facts ((?f .)) TRUE))";
		final PrimitiveValue pv = this.clips.eval(evalStr);
		//final String evalStr = "(find-all-facts ((?f .)) TRUE))";
		Log.d(SemanticReasoner.logLabel, pv.getValue().toString());
		
		//final PrimitiveValue pvx = this.clips.eval("(facts)");
		try {
			//Log.d(SemanticReasoner.logLabel, pvx.getValue().toString());
			for(int i=0; i< pv.size(); i++) {
				final PrimitiveValue pv2 = pv.get(i);
				//final String evalStr2 = "(find-all ((?f .)) (eq ?f:id " + pv2.getValue().toString() + ")))";
				//final PrimitiveValue pv3 = this.clips.eval(evalStr2);
				//Log.d(SemanticReasoner.logLabel, pv2.getValue().toString());
				//Log.d(SemanticReasoner.logLabel, pv2.getFactSlot(null).toString());
				final PrimitiveValue triple = pv2.getFactSlot(null);
				if( triple.get(0).toString().equals( subj ) )
					Log.d( SemanticReasoner.logLabel, triple.toString() );
			}
		} catch (Exception e) {
			Log.e(SemanticReasoner.logLabel, e.getMessage());
		}
		return null;
	}
	
	public void writeInferredDataInto(String outputFilePath) {
		
	}
	
	public void stop() {
		this.clips.destroy();
	}
}
