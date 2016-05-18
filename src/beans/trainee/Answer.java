package beans.trainee;

public class Answer {
	private int id;
	private int orderNumber;
	private String value;
	private boolean active;
	private int questionId;
	
	public Answer(int id, int questionId, int orderNumber, String value, boolean active){
		this.setId(id);
		this.setQuestionId(questionId);
		this.setOrderNumber(orderNumber);
		this.setValue(value);
		this.setActive(active);
	}
	
	public Answer(int orderNumber, String value, boolean active){
		this.id = 0;
		this.setOrderNumber(orderNumber);
		this.setValue(value);
		this.setActive(active);
	}
	
	public Answer(int orderNumber, String value){
		this.id = 0;
		this.setOrderNumber(orderNumber);
		this.setValue(value);
		this.active = false;
	}
	
	public Answer(){
		this.id = 0;
		this.orderNumber = 0;
		this.value = null;
		this.active = false;
	}
	
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}	
}
