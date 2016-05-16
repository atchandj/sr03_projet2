package beans.super_user;

import beans.User;

public class SuperUser extends User {
    public SuperUser(int id, String email, String surname, String name, String phone, String company){
    	super(id, email, surname, name, phone, company);
    }
    public SuperUser(String email, String surname, String name, String phone, String company){
    	super(email, surname, name, phone, company);
    }
    public SuperUser(){
    	super();
    }
}