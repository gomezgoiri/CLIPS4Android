package eu.deustotech.simpleclipstest;

import android.util.Log;
import eu.deustotech.clips.CLIPSError;
import eu.deustotech.clips.Environment;
import eu.deustotech.clips.FactAddressValue;
import eu.deustotech.clips.IntegerValue;
import eu.deustotech.clips.MultifieldValue;

/*
 * This class is based on an introductory example on how to use CLIPSJNI made by C. Daniel Sánchez Ramírez.
 *   https://bitbucket.org/ErunamoJAZZ/trash-it/src/94282935833e4271065787ba75d02a7ce6ff9b24/docs/Como%20Utilizar%20CPLISJNI.pdf?at=default
 */
public class CLIPSTest {
	
	public static final String tag = "CLIPSAgeTest";
	private final Environment clips;
	
	public CLIPSTest( String filepath ) throws CLIPSError {
		this.clips = new Environment();
		this.clips.load(filepath);
		Log.d(CLIPSTest.tag, "Loading .clp...\n\n");
		this.clips.reset();
	}
	
	public void stop() {
		this.clips.destroy();
	}
	
	private void showPerson(FactAddressValue personFact) {
		final String name = personFact.getFactSlot("name").toString();
		final int age =((IntegerValue) personFact.getFactSlot("age")).intValue();
		Log.d(CLIPSTest.tag, name + " is "+ age + " old.");
	}
	
	/** 
	 * Example of how to use "assert".
	 * @throws CLIPSError
	 */
	public void assertExample() throws CLIPSError {
		final FactAddressValue Sandra = this.clips.assertString("(person (name Sandra) (age 28))");
		showPerson( Sandra );
	}
	
	/**
	 * Example of how to get data using "eval".
	 * @throws CLIPSError 
	 */
	public void getAllFacts() throws CLIPSError {
		final String evalStr = "(find-all-facts (( ?f person )) TRUE)";
		final MultifieldValue evaluated = (MultifieldValue) this.clips.eval( evalStr );
		showPeople( evaluated );
	}
	
	/**
	 * Changing a fact using a rule defined in the original file.
	 * @throws CLIPSError
	 */
	public void modifyAFact() throws CLIPSError {
		this.clips.assertString("(birthday Ana)");
		this.clips.run();
	}
	
	private void showPeople(MultifieldValue evaluated) {
		for (int i=0; i < evaluated.size(); i++) {
			final FactAddressValue person = (FactAddressValue) evaluated.get(i);
			showPerson( person );
		}
	}
}