package eu.deustotech.clips.demo.semanticreasoning.reasoner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
	
	/*public void addFileToKnowledgeBase(String filePath) {
		
	}*/
	
	public void assertKnowledge(String triplesSerializedToCLP) {
		// Line by line, because apparently it doesn't assert a set of assertions separated by a \n.
		// The following sentence doesn't work properly: this.clips.assertString( triplesSerializedToCLP );
		final String[] lines = triplesSerializedToCLP.split("\n");
		for( String line: lines ) { 
			// It is also helpful to identify whether any of them has an incorrect syntax.
			//Log.d(SemanticReasoner.logLabel, "Asserting: " + line);
			this.clips.assertString(line);
		}
	}
	
	private String serializeToCLP(PrimitiveValue triple) throws Exception {
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		final PrintStream ps = new PrintStream(os);
		ps.format("(. %s %s %s )\n", triple.get(0), triple.get(1), triple.get(2) );
		return new String(os.toByteArray());
	}
	
	private String getKnowledgeBaseSerialized(ResultsFilter[] rf) {
		final StringBuilder sb = new StringBuilder();
		this.clips.run();
		
		final String evalStr = "(find-all-facts ((?f .)) TRUE)";
		final PrimitiveValue pv = this.clips.eval(evalStr);
		//final String evalStr = "(find-all-facts ((?f .)) TRUE)";
		//Log.d(SemanticReasoner.logLabel, pv.getValue().toString());
		
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
				boolean retain = true;
				
				if( rf!=null ) {
					for( ResultsFilter f: rf) {
						if( !f.isRetained(triple) ) {
							retain = false;
							break;
						}
					}
				}
				
				if( retain )
					sb.append( serializeToCLP(triple) );
			}
		} catch (Exception e) {
			Log.e(SemanticReasoner.logLabel, e.getMessage());
		}
		return sb.toString();
	}
	
	public String getKnowledgeBaseSerialized() {
		return getKnowledgeBaseSerialized(
				new ResultsFilter[] {
						new IncorrectSubjectFilter(),
						new ObviousSameAsFilter()
				});
	}
	
	public String getBySubject(String subj) {
		try {
			return getKnowledgeBaseSerialized(
							new ResultsFilter[] {
									new IncorrectSubjectFilter(),
									new ObviousSameAsFilter(),
									new SubjectFilter(subj)
							} );
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


class SubjectFilter implements ResultsFilter {
	
	final String subject;
	
	SubjectFilter(String subject) {
		this.subject = subject;
	}
	
	@Override
	public boolean isRetained(PrimitiveValue triple) throws Exception {
		return triple.get(0).toString().equals( this.subject );
	}
}

class ObviousSameAsFilter implements ResultsFilter {
	@Override
	public boolean isRetained(PrimitiveValue triple) throws Exception {
		if( triple.get(1).toString().equals("owl:sameAs") ) {
			return !triple.get(0).toString().equals( triple.get(2).toString() );
		}
		return true;
	}
}

// This is a quickfix for badly asserted triples: the subject cannot be a String!
// Fixing the rules would be a much better solution than this.
// For example, changing eq-ref to ignore objects which are not URIs
class IncorrectSubjectFilter implements ResultsFilter {
	@Override
	public boolean isRetained(PrimitiveValue triple) throws Exception {
		return !triple.get(0).toString().startsWith("\"");
	}
}