package beans.trainee;

public class BadAnswer extends Answer {
	public BadAnswer(int id, int questionId, int orderNumber, String value, boolean active){
		super(id, questionId, orderNumber, value, active);
	}
	
	public BadAnswer(int orderNumber, String value, boolean active){
		super(orderNumber, value, active);
	}
	
	public BadAnswer(){
		super();
	}
}
