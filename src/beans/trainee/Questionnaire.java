package beans.trainee;

import java.util.ArrayList;

public class Questionnaire {
	private String name;
	private boolean active;
	private ArrayList<Question> questions;
	private boolean activable;
	
	public Questionnaire(String name, boolean active, ArrayList<Question> questions, boolean activable){
		this.setName(name);
		this.setActive(active);
		this.setQuestions(questions);
		this.setActivable(activable);
	}
	
	public Questionnaire(String name, boolean active, boolean activable){
		this.setName(name);
		this.setActive(active);
		this.questions = new ArrayList<Question>();	
		this.setActivable(activable);
	}
	
	public Questionnaire(){
		this.name = null;
		this.active = false;
		this.questions = new ArrayList<Question>();	
		this.activable = false;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}	
	public boolean isActivable() {
		return activable;
	}
	public void setActivable(boolean activable) {
		this.activable = activable;
	}
}
