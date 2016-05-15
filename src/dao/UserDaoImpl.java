package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import beans.TemporaryUser;
import beans.super_user.SuperUser;
import beans.trainee.Trainee;

public class UserDaoImpl implements UserDao {
    private DaoFactory daoFactory;

    UserDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public Trainee getTrainee(TemporaryUser tempUser) throws DaoException{
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        Trainee trainee = new Trainee();
        
        try{
            connexion = daoFactory.getConnection();
       	
            preparedStatement = (PreparedStatement) connexion.prepareStatement("SELECT surname, name, phone, company, accountCreation FROM Trainee WHERE email= ? AND password=? AND accountStatus=TRUE;");
            preparedStatement.setString(1, tempUser.getEmail());
            preparedStatement.setString(2, tempUser.getPassword());
            ResultSet result = preparedStatement.executeQuery();         
            if(result.next()){
            	String email = tempUser.getEmail();
                String surname = result.getString("surname");
                String name = result.getString("name");
                String phone = result.getString("phone");
                String company = result.getString("company");
                Timestamp accountCreation = result.getTimestamp("accountCreation");

                trainee.setEmail(email);
                trainee.setSurname(surname);
                trainee.setName(name);
                trainee.setPhone(phone);
                trainee.setCompany(company);
                trainee.setAccountCreation(accountCreation);
                trainee.setAccountStatus(true);
            }else{
            	throw new DaoException("Not a trainee.");
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
        return trainee;
    }
    
    @Override
    public SuperUser getSuperUser(TemporaryUser tempUser) throws DaoException{
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        SuperUser superUser = new SuperUser();
        
        try{
            connexion = daoFactory.getConnection();
       	
            preparedStatement = (PreparedStatement) connexion.prepareStatement("SELECT surname, name, phone, company, accountCreation FROM SuperUser WHERE email=? AND password=? AND accountStatus=TRUE;");
            preparedStatement.setString(1, tempUser.getEmail());
            preparedStatement.setString(2, tempUser.getPassword());
            ResultSet result = preparedStatement.executeQuery();         
            if(result.next()){
            	String email = tempUser.getEmail();
                String surname = result.getString("surname");
                String name = result.getString("name");
                String phone = result.getString("phone");
                String company = result.getString("company");  
                Timestamp accountCreation = result.getTimestamp("accountCreation");
                
                superUser.setEmail(email);
                superUser.setSurname(surname);
                superUser.setName(name);
                superUser.setPhone(phone);
                superUser.setCompany(company);
                superUser.setAccountCreation(accountCreation);
                superUser.setAccountStatus(true);
            }else{
            	throw new DaoException("Not a super user.");
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
        return superUser;
    }
}