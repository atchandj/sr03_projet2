package dao.super_user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import beans.super_user.SuperUser;
import beans.trainee.Trainee;
import dao.DaoException;
import dao.DaoFactory;

public class UsersManagementDaoImpl implements UsersManagementDao{
	
    private DaoFactory daoFactory;

    public UsersManagementDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    @Override
	public List<SuperUser> getSuperUsers(SuperUser superUser) throws DaoException{
        List<SuperUser> superUsers = new ArrayList<SuperUser>();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String superUserEmail = superUser.getEmail();        
        try{
            connexion = daoFactory.getConnection();
            System.out.println("SELECT email, surname, name, phone, company, accountCreation, accountStatus FROM SuperUser WHERE email <> ?;");
            preparedStatement = (PreparedStatement) connexion.prepareStatement("SELECT email, surname, name, phone, company, accountCreation, accountStatus FROM SuperUser WHERE email <> ?;");
            preparedStatement.setString(1, superUserEmail);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                SuperUser tmpSuperUser = new SuperUser();
            	
            	String email = result.getString("email");
                String surname = result.getString("surname");
                String name = result.getString("name");
                String phone = result.getString("phone");
                String company = result.getString("company");
                Timestamp accountCreation = result.getTimestamp("accountCreation");
                boolean accountStatus = result.getBoolean("accountStatus");

                tmpSuperUser.setEmail(email);
                tmpSuperUser.setSurname(surname);
                tmpSuperUser.setName(name);
                tmpSuperUser.setPhone(phone);
                tmpSuperUser.setCompany(company);
                tmpSuperUser.setAccountCreation(accountCreation);
                tmpSuperUser.setAccountStatus(accountStatus);
                
                System.out.println(tmpSuperUser.getEmail());
                System.out.println(tmpSuperUser.getSurname());
                System.out.println(tmpSuperUser.getName());
                
                superUsers.add(tmpSuperUser);
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la base de données");
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données");
            }
        }
        return superUsers;
    }    
    
    @Override
	public List<Trainee> getTrainees() throws DaoException{
        List<Trainee> trainees = new ArrayList<Trainee>();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try{
            connexion = daoFactory.getConnection();
            System.out.println("SELECT email, surname, name, phone, company, accountCreation, accountStatus FROM Trainee;");
            preparedStatement = (PreparedStatement) connexion.prepareStatement("SELECT email, surname, name, phone, company, accountCreation, accountStatus FROM Trainee;");
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Trainee tmpTrainee = new Trainee();
            	
            	String email = result.getString("email");
                String surname = result.getString("surname");
                String name = result.getString("name");
                String phone = result.getString("phone");
                String company = result.getString("company");
                Timestamp accountCreation = result.getTimestamp("accountCreation");
                boolean accountStatus = result.getBoolean("accountStatus");

                tmpTrainee.setEmail(email);
                tmpTrainee.setSurname(surname);
                tmpTrainee.setName(name);
                tmpTrainee.setPhone(phone);
                tmpTrainee.setCompany(company);
                tmpTrainee.setAccountCreation(accountCreation);
                tmpTrainee.setAccountStatus(accountStatus);
                
                System.out.println(tmpTrainee.getEmail());
                System.out.println(tmpTrainee.getSurname());
                System.out.println(tmpTrainee.getName());
                
                trainees.add(tmpTrainee);
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la base de données");
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données");
            }
        }
        return trainees;
    }
}
