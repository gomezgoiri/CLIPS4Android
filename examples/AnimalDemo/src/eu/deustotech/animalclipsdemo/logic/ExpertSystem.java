package eu.deustotech.animalclipsdemo.logic;

import java.util.HashSet;
import java.util.Set;

import android.util.Log;
import eu.deustotech.animalclipsdemo.states.FinalState;
import eu.deustotech.animalclipsdemo.states.InitialState;
import eu.deustotech.animalclipsdemo.states.NextStateListener;
import eu.deustotech.animalclipsdemo.states.UsualState;
import eu.deustotech.animalclipsdemo.states.StateChoice;
import eu.deustotech.clips.CLIPSError;
import eu.deustotech.clips.Environment;
import eu.deustotech.clips.FactAddressValue;
import eu.deustotech.clips.MultifieldValue;
import eu.deustotech.clips.PrimitiveValue;
import eu.deustotech.clips.SymbolValue;

/* Original source code:
 * http://sourceforge.net/projects/clipsrules/files/CLIPS/6.30/
 */
public class ExpertSystem {
	
	final public static String logLabel = "Expert_system"; 
	
	final private Environment clips;
	final private String[] filesToLoad;
	final private Set<NextStateListener> listeners;
	
	public ExpertSystem(String[] filesToLoad) {
		this.clips = new Environment();
		this.listeners = new HashSet<NextStateListener>();
		this.filesToLoad = filesToLoad; 
	}
	
	public void addListener(NextStateListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(NextStateListener listener) {
		this.listeners.remove(listener);
	}
	
	public void start() {
		for(String fileToLoad: this.filesToLoad) {
			Log.d( ExpertSystem.logLabel, "Loading rule file '" + fileToLoad + "'... " );
			try {
				this.clips.load(fileToLoad);
				Log.d( ExpertSystem.logLabel, "Loaded" );
			} catch (CLIPSError e) {
				Log.e( ExpertSystem.logLabel, "The file could not be loaded.", e );
			}
			
		}
		this.clips.reset();
	}
	
	public void stop() {
		this.clips.destroy();
	}
	
	protected void restart() throws CLIPSError {
		this.clips.reset();
		nextState();
	}
	
	protected void next(String selection) throws CLIPSError {
		String assertion = "(next (id " + getCurrentStateId() + ")";
		if( selection == null ) {
			assertion += " (value-set FALSE))";
		} else {
			assertion += " (value " + selection + ") (value-set TRUE))";
		}
		this.clips.assertString( assertion );
		nextState();
	}
	
	protected void previous() throws CLIPSError {
		this.clips.assertString( "(prev (id " + getCurrentStateId() + "))" );
		nextState();
	}
	
	private FactAddressValue getFirstFact(String evalStr) throws CLIPSError {
		final MultifieldValue result = (MultifieldValue) this.clips.eval(evalStr);
		return (FactAddressValue) result.get(0);
	}
	
	private String getCurrentStateId() throws CLIPSError {
		final String evalStr = "(find-all-facts ((?f state-list)) TRUE)";
		return getFirstFact(evalStr).getFactSlot("current").toString();
	}
	
	private FactAddressValue getCurrentState() throws CLIPSError {
		final String evalStr =  "(find-all-facts ((?f UI-state)) " + "(eq ?f:id " + getCurrentStateId() + "))";
		return getFirstFact(evalStr);
	}
	
	private Set<StateChoice> getChoices(FactAddressValue currentState) {
		final Set<StateChoice> choices = new HashSet<StateChoice>();
		
		final MultifieldValue validAnswers = (MultifieldValue) currentState.getFactSlot("valid-answers");
		final MultifieldValue displayAnswers = (MultifieldValue) currentState.getFactSlot("display-answers");
		final String selected = currentState.getFactSlot("response").toString();

		for (int i = 0, j = 0; (i < validAnswers.size()) && (j < displayAnswers.size()); i++, j++) {
			final PrimitiveValue validAnswer = validAnswers.get(i);
			final PrimitiveValue displayAnswer = displayAnswers.get(j);
			
			// I don't really get this "validity" thing... (maybe when running)
			final boolean valid = validAnswer.toString().equals(selected);
			choices.add( new StateChoice( displayAnswer.getValue().toString(), valid) );
		}
		
		return choices;
	}
	
	private String getText(FactAddressValue currentState) {
		return ((SymbolValue) currentState.getFactSlot("display")).symbolValue();
	}
	
	private void nextState() throws CLIPSError {
		this.clips.run(); // Important! 
		
		final FactAddressValue currentState = getCurrentState();
		final String stateName = currentState.getFactSlot("state").toString();
		
		if( stateName.equals("final") ) {
			for( NextStateListener listener: this.listeners) {
				listener.finished( new FinalState( getText(currentState) ) );
			}
        } else if( stateName.equals("initial") ) {
        	for( NextStateListener listener: this.listeners) {
				listener.started( new InitialState( getText(currentState), getChoices(currentState) ) );
        	}
        } else {
        	for( NextStateListener listener: this.listeners) {
				listener.nextState( new UsualState( getText(currentState), getChoices(currentState) ) );
        	}
        }
	}
}
