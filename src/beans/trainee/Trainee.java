package beans.trainee;

import beans.User;

public class Trainee extends User {
    public Trainee(String email, String surname, String name, String phone, String company){
    	super(email, surname, name, phone, company);
    }
    public Trainee(){
    	super();
    }
}
