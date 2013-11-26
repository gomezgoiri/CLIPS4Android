package eu.deustotech.simpleclipstest;

import android.util.Log;
import eu.deustotech.clips.Environment;
import eu.deustotech.clips.FactAddressValue;
import eu.deustotech.clips.PrimitiveValue;


/*
 * This class is based on an introductory example on how to use CLIPSJNI made by C. Daniel Sánchez Ramírez.
 *   https://bitbucket.org/ErunamoJAZZ/trash-it/src/94282935833e4271065787ba75d02a7ce6ff9b24/docs/Como%20Utilizar%20CPLISJNI.pdf?at=default
 */
public class CLIPSTest {
	
	public static final String tag = "CLIPSAgeTest";
	private final Environment clips;
	
	public CLIPSTest( String filepath ) {
		this.clips = new Environment();
		clips.load(filepath);
		Log.d(CLIPSTest.tag, "Loading .clp...\n\n");
		clips.reset();
	}
	
	public void stop() {
		clips.destroy();
	}
	
	private void showPerson(PrimitiveValue personFact) throws Exception {
		final String name = personFact.getFactSlot("name").toString();
		final int age = personFact.getFactSlot("age").intValue();
		Log.d(CLIPSTest.tag, name + " is "+ age + " old.");
	}
	
	/** 
	 * Example of how to use "assert".
	 * @throws Exception 
	 */
	public void assertExample() throws Exception {
		final FactAddressValue Sandra = clips.assertString("(person (name Sandra) (age 28))");
		showPerson( Sandra );
	}
	
	/**
	 * Example of how to get data using "eval".
	 * @throws Exception 
	 */
	public void getAllFacts() throws Exception {
		final String evalStr = "(find-all-facts (( ?f person )) TRUE)";
		final PrimitiveValue evaluated = clips.eval( evalStr );
		showPeople( evaluated );
	}
	
	/**
	 * Changing a fact using a rule defined in the original file.
	 * @throws Exception 
	 */
	public void modifyAFact() throws Exception {
		clips.assertString("(birthday Ana)");
		clips.run();
	}
	
	private void showPeople(PrimitiveValue evaluated) throws Exception{
		for (int i=0; i < evaluated.size(); i++) {
			final PrimitiveValue person = evaluated.get(i);
			showPerson( person );
		}
	}
}