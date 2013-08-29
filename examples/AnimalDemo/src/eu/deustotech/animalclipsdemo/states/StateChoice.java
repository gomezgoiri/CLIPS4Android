package eu.deustotech.animalclipsdemo.states;

public class StateChoice {
	private final String id;
	private final boolean isValid;
	
	public StateChoice(String id, boolean isValid) {
		this.id = id;
		this.isValid = isValid;
	}
	
	public boolean isValid() {
		return isValid;
	}
	public String getId() {
		return id;
	}
}
