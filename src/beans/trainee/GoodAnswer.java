package beans.trainee;

public class GoodAnswer extends Answer {
	public GoodAnswer(int id, int questionId, int orderNumber, String value, boolean active){
		super(id, questionId, orderNumber, value, active);
	}
	
	public GoodAnswer(int orderNumber, String value, boolean active){
		super(orderNumber, value, active);
	}
	
	public GoodAnswer(){
		super();
	}
	
}
