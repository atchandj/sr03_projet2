package beans.trainee;

import java.util.ArrayList;

public class Question {
	private String value;
	private int orderNumber;
	private boolean active;
	private ArrayList<BadAnswer> badAnswers;
	private GoodAnswer goodAnswer;
	
	public Question(String value, int orderNumber, boolean active, ArrayList<BadAnswer> badAnswers, GoodAnswer goodAnswer){
		this.setValue(value);
		this.setOrderNumber(orderNumber);
		this.setActive(active);
		this.setBadAnswers(badAnswers);
		this.setGoodAnswer(goodAnswer);
	}
	public Question(){
		this.value = null;
		this.orderNumber = 0;
		this.active = false;
		this.badAnswers = null;
		this.goodAnswer = null;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public ArrayList<BadAnswer> getBadAnswers() {
		return badAnswers;
	}
	public void setBadAnswers(ArrayList<BadAnswer> badAnswers) {
		this.badAnswers = badAnswers;
	}
	public GoodAnswer getGoodAnswer() {
		return goodAnswer;
	}
	public void setGoodAnswer(GoodAnswer goodAnswer) {
		this.goodAnswer = goodAnswer;
	}
}
