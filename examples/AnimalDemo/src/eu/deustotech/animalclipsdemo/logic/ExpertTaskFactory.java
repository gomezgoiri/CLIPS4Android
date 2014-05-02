package eu.deustotech.animalclipsdemo.logic;

import eu.deustotech.clips.CLIPSError;
import android.util.Log;

public class ExpertTaskFactory {
	
	private final ExpertSystem es;
	
	public ExpertTaskFactory(ExpertSystem es) {
		this.es = es;
	}
	
	public ExpertTask createRestartTask() {
		return new RestartTask( this.es ); 
	}
	
	public ExpertTask createNextTask(String stateId) {
		return new NextTask( this.es, stateId ); 
	}
	
	public ExpertTask createPreviousTask() {
		return new PreviousTask( this.es ); 
	}
}

abstract class ExpertTask implements Runnable {
	final ExpertSystem es;
	
	public ExpertTask(ExpertSystem es) {
		this.es = es;
	}
	
	@Override
	public void run() {
		try {
			executeTask();
		} catch (CLIPSError e) {
			Log.e(ExpertSystem.logLabel, e.getMessage());
		}
	}
	
	protected abstract void executeTask() throws CLIPSError;
}

class RestartTask extends ExpertTask {
	
	public RestartTask(ExpertSystem es) {
		super(es);
	}

	@Override
	protected void executeTask() throws CLIPSError {
		this.es.restart();
	}
}

class NextTask extends ExpertTask {
	final String stateId;
	
	public NextTask(ExpertSystem es, String stateId) {
		super(es);
		this.stateId = stateId;
	}
	
	@Override
	public void executeTask() throws CLIPSError {
		this.es.next( stateId );
	}
}

class PreviousTask extends ExpertTask {
	public PreviousTask(ExpertSystem es) {
		super(es);
	}
	
	@Override
	public void executeTask() throws CLIPSError {
		this.es.previous();
	}
}