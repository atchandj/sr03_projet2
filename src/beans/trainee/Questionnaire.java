package beans.trainee;

import java.util.ArrayList;

public class Questionnaire {
	private int id;
	private String name;
	private boolean active;
	private ArrayList<Question> questions;
	private boolean activable;
	private boolean deletable;
	
	public Questionnaire(int id, String name, boolean active, ArrayList<Question> questions, boolean activable, boolean deletable){
		this.setId(id);
		this.setName(name);
		this.setActive(active);
		this.setQuestions(questions);
		this.setActivable(activable);
		this.setDeletable(deletable);
	}
	
	public Questionnaire(int id, String name, boolean active, boolean activable, boolean deletable){
		this.setId(id);
		this.setName(name);
		this.setActive(active);
		this.questions = new ArrayList<Question>();	
		this.setActivable(activable);
		this.setDeletable(deletable);
	}
	
	public Questionnaire(int id, String name, boolean active, boolean activable){
		this.setId(id);
		this.setName(name);
		this.setActive(active);
		this.questions = new ArrayList<Question>();	
		this.setActivable(activable);
		this.setDeletable(false);
	}
	
	public Questionnaire(String name){
		this.setId(0);
		this.setName(name);
		this.active = false;
		this.questions = new ArrayList<Question>();	
		this.activable = false;
		this.deletable = false;
	}
	
	public Questionnaire(int id, String name){
		this.setId(id);
		this.setName(name);
		this.active = false;
		this.questions = new ArrayList<Question>();	
		this.activable = false;
		this.deletable = false;
	}
	
	public Questionnaire(){
		this.setId(0);
		this.name = null;
		this.active = false;
		this.questions = new ArrayList<Question>();	
		this.activable = false;
		this.deletable = false;
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
	public boolean isDeletable() {
		return deletable;
	}
	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
