package dao.super_user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import beans.super_user.SuperUser;
import beans.trainee.Attempt;
import beans.trainee.Trainee;
import dao.DaoException;
import dao.DaoFactory;

public class UserDataDaoImpl implements UserDataDao {
	
	private DaoFactory daoFactory;
	
    public UserDataDaoImpl(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }
 
    @Override
    public Trainee getTrainee(String email) throws DaoException{
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        Trainee trainee = new Trainee();
        
        try{
            connexion = daoFactory.getConnection();
       	
            preparedStatement = (PreparedStatement) connexion.prepareStatement("SELECT surname, name, phone, company, accountCreation, accountStatus FROM Trainee WHERE email= ?;");
            preparedStatement.setString(1, email);
            ResultSet result = preparedStatement.executeQuery();         
            if(result.next()){
                String surname = result.getString("surname");
                String name = result.getString("name");
                String phone = result.getString("phone");
                String company = result.getString("company");
                Timestamp accountCreation = result.getTimestamp("accountCreation");
                boolean accountStatus = result.getBoolean("accountStatus");

                trainee.setEmail(email);
                trainee.setSurname(surname);
                trainee.setName(name);
                trainee.setPhone(phone);
                trainee.setCompany(company);
                trainee.setAccountCreation(accountCreation);
                trainee.setAccountStatus(accountStatus);
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
    public SuperUser getSuperUser(String email) throws DaoException{
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        SuperUser superUser = new SuperUser();
        
        try{
            connexion = daoFactory.getConnection();
       	
            preparedStatement = (PreparedStatement) connexion.prepareStatement("SELECT surname, name, phone, company, accountCreation, accountStatus FROM SuperUser WHERE email=?;");
            preparedStatement.setString(1, email);
            ResultSet result = preparedStatement.executeQuery();         
            if(result.next()){
                String surname = result.getString("surname");
                String name = result.getString("name");
                String phone = result.getString("phone");
                String company = result.getString("company");  
                Timestamp accountCreation = result.getTimestamp("accountCreation");
                boolean accountStatus = result.getBoolean("accountStatus");
                
                superUser.setEmail(email);
                superUser.setSurname(surname);
                superUser.setName(name);
                superUser.setPhone(phone);
                superUser.setCompany(company);
                superUser.setAccountCreation(accountCreation);
                superUser.setAccountStatus(true);
                superUser.setAccountStatus(accountStatus);
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
    
    @Override
	public void updateTrainee(Trainee trainee) throws DaoException{
    	// System.out.println("Mettre à jour stagiaire"); // Test
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try{
            connexion = daoFactory.getConnection();
            // System.out.println("UPDATE Trainee SET surname = ?, name = ?, phone = ?, company = ? WHERE email = ?;"); // Test
            preparedStatement = (PreparedStatement) connexion.prepareStatement("UPDATE Trainee SET surname = ?, name = ?, phone = ?, company = ? WHERE email = ?;");
            
            // System.out.println(trainee.getName());
            
            preparedStatement.setString(1, trainee.getSurname());
            preparedStatement.setString(2, trainee.getName());
            preparedStatement.setString(3, trainee.getPhone());
            preparedStatement.setString(4, trainee.getCompany());
            preparedStatement.setString(5, trainee.getEmail());
            int result = preparedStatement.executeUpdate();
            connexion.commit();
            // System.out.println(result); // Test
            if(result == 0){
            	// System.out.println("Stagiaire à mettre à jour inconnu."); // Test
            	throw new DaoException("Stagiaire à mettre à jour inconnu.");
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la base de données : " + e.getMessage());
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données : " + e.getMessage());
            }
        }
    }
    
    @Override
	public void updateSuperUser(SuperUser superUser) throws DaoException{
    	// System.out.println("Mettre à jour administrateur"); // Test
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try{
            connexion = daoFactory.getConnection();
            // System.out.println("UPDATE SuperUser SET surname = ?, name = ?, phone = ?, company = ? WHERE email = ?;"); // Test
            preparedStatement = (PreparedStatement) connexion.prepareStatement("UPDATE SuperUser SET surname = ?, name = ?, phone = ?, company = ? WHERE email = ?;");
            preparedStatement.setString(1, superUser.getSurname());
            preparedStatement.setString(2, superUser.getName());
            preparedStatement.setString(3, superUser.getPhone());
            preparedStatement.setString(4, superUser.getCompany());
            preparedStatement.setString(5, superUser.getEmail());
            int result = preparedStatement.executeUpdate();
            connexion.commit();
            System.out.println(result); // Test
            if(result == 0){
            	// System.out.println("Administrateur à mettre à jour inconnu."); // Test
            	throw new DaoException("Administrateur à mettre à jour inconnu.");
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la base de données : " + e.getMessage());
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données : " + e.getMessage());
            }
        }
    }
    
    @Override
    public List<Attempt> getAttemptsOfATrainee(String traineeEMail) throws DaoException{
    	List<Attempt> attemptsOfATrainee = new ArrayList<Attempt>();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = null;
        try{
            connexion = daoFactory.getConnection();
            query = "SELECT Top.name AS topicName, Q.name AS questionnaireName, A.score, A.beginning, A.end, "
            		+ "TIMESTAMPDIFF(SECOND, A.beginning, A.end) AS durationInSeconds, "
            		+ "(A.score/(TIMESTAMPDIFF(SECOND, A.beginning, A.end)))*100 AS scoreDivByDurationTimes100 "
            		+ "FROM Trainee Tr, Attempt A, Questionnaire Q, Topic Top "
            		+ "WHERE Tr.id = A.Trainee AND A.questionnaire = Q.id AND Q.topic= Top.name AND Tr.email = ? "
            		+ "ORDER BY topicName, A.beginning;";
            // System.out.println(query); // Test
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setString(1, traineeEMail);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
            	String topicName = result.getString("topicName");
                String questionnaireName = result.getString("questionnaireName");
                int score = result.getInt("score");
                Timestamp beginning = result.getTimestamp("beginning");
                Timestamp end = result.getTimestamp("end");
                int durationInSeconds = result.getInt("durationInSeconds");
                double scoreDivByDurationTimes100 = result.getDouble("scoreDivByDurationTimes100");

                Attempt tmpAttempt= new Attempt(topicName, questionnaireName,score, beginning, end, durationInSeconds, scoreDivByDurationTimes100);
                                
                attemptsOfATrainee.add(tmpAttempt);
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
        return attemptsOfATrainee;
    }
}