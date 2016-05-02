package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import beans.SuperUser;
import beans.TemporaryUser;
import beans.Trainee;

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
       	
            System.out.println("SELECT surname, name, password, phone, company, accountCreation, accountStatus FROM Trainee WHERE email= "+ tempUser.getEmail() + " AND surname="+ tempUser.getPassword() +";");
            preparedStatement = (PreparedStatement) connexion.prepareStatement("SELECT surname, name, password, phone, company, accountCreation, accountStatus FROM Trainee WHERE email=? AND password=?;");
            preparedStatement.setString(1, tempUser.getEmail());
            preparedStatement.setString(2, tempUser.getPassword());
            ResultSet result = preparedStatement.executeQuery();         
            if(result.next()){
                String surname = result.getString("surname");
                String name = result.getString("name");

                trainee.setSurname(surname);
                trainee.setName(name);
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
       	
            System.out.println("SELECT surname, name, password, phone, company, accountCreation, accountStatus FROM SuperUser WHERE email= "+ tempUser.getEmail() + " AND surname="+ tempUser.getPassword() +";");
            preparedStatement = (PreparedStatement) connexion.prepareStatement("SELECT surname, name, password, phone, company, accountCreation, accountStatus FROM SuperUser WHERE email=? AND password=?;");
            preparedStatement.setString(1, tempUser.getEmail());
            preparedStatement.setString(2, tempUser.getPassword());
            ResultSet result = preparedStatement.executeQuery();         
            if(result.next()){
                String surname = result.getString("surname");
                String name = result.getString("name");

                superUser.setSurname(surname);
                superUser.setName(name);
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
