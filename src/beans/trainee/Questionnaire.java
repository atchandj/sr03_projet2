package beans.trainee;

import java.util.ArrayList;

public class Questionnaire {
	private String name;
	private boolean active;
	private ArrayList<Question> questions;
	
	public Questionnaire(String name, boolean active, ArrayList<Question> questions){
		this.setName(name);
		this.setActive(active);
		this.setQuestions(questions);
	}
	
	public Questionnaire(){
		this.name = null;
		this.active = false;
		this.questions = null;
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
}
