package beans.trainee;

import java.util.ArrayList;

public class Topic {
	private String name;
	private ArrayList<Questionnaire> questionnaires;
	
	public Topic(String name, ArrayList<Questionnaire> questionnaires){
		this.setName(name);
		this.setQuestionnaires(questionnaires);		
	}

	public Topic(String name){
		this.setName(name);
		this.questionnaires = new ArrayList<Questionnaire>();	
	}
	
	public Topic(){
		this.name = null;
		this.questionnaires = new ArrayList<Questionnaire>();		
	}	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Questionnaire> getQuestionnaires() {
		return questionnaires;
	}
	public void setQuestionnaires(ArrayList<Questionnaire> questionnaires) {
		this.questionnaires = questionnaires;
	}	
}
