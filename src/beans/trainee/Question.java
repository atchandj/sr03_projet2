package beans.trainee;

import java.util.List;

public class Question {
	private int id;
	private String value;
	private int orderNumber;
	private boolean active;
	private List<BadAnswer> badAnswers;
	private GoodAnswer goodAnswer;
	
	public Question(int id, String value, int orderNumber, boolean active, List<BadAnswer> badAnswers, GoodAnswer goodAnswer){
		this.setValue(value);
		this.setOrderNumber(orderNumber);
		this.setActive(active);
		this.setBadAnswers(badAnswers);
		this.setGoodAnswer(goodAnswer);
	}
	public Question(){
		this.setId(0);
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
	public List<BadAnswer> getBadAnswers() {
		return badAnswers;
	}
	public void setBadAnswers(List<BadAnswer> badAnswers) {
		this.badAnswers = badAnswers;
	}
	public GoodAnswer getGoodAnswer() {
		return goodAnswer;
	}
	public void setGoodAnswer(GoodAnswer goodAnswer) {
		this.goodAnswer = goodAnswer;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
