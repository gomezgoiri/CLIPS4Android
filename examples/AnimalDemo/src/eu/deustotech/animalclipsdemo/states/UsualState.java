package eu.deustotech.animalclipsdemo.states;

import java.util.Set;

public class UsualState extends AbstractState {
	private final String question;
	private final Set<StateChoice> choices; // text associated to the state
	
	public UsualState(String question, Set<StateChoice> choices) {
		super();
		this.question = question;
		this.choices = choices;
	}
		
	public String getQuestion() {
		return this.question;
	}
	
	public Set<StateChoice> getChoices() {
		return this.choices;
	}
}
