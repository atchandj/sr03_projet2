package dao.trainee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;


import beans.super_user.SuperUser;
import beans.trainee.Questionnaire;
import beans.trainee.Trainee;
import dao.DaoException;
import dao.DaoFactory;
import dao.super_user.UsersManagementDao;

public class QuestionnaireListDaoImpl implements QuestionnaireListDao {
	 private DaoFactory daoFactory;

	    public QuestionnaireListDaoImpl(DaoFactory daoFactory) {
	        this.daoFactory = daoFactory;
	    }
	    
	    @Override
	    public List<Questionnaire> getQuestionnaireList() throws DaoException{
	    	List<Questionnaire> surveys = new ArrayList<Questionnaire>();
	    	Connection connexion = null;
	        PreparedStatement preparedStatement = null;
	        try{
	        	connexion = daoFactory.getConnection();
	        	preparedStatement = (PreparedStatement) connexion.prepareStatement("SELECT id, topic, name, active FROM Questionnaire;");
	        	ResultSet result = preparedStatement.executeQuery();
	        	while(result.next()){
	        		Questionnaire survey = new Questionnaire();
	        		
	        		String topic = result.getString("topic");
	                String name = result.getString("name");
	                boolean isActive = result.getBoolean("active");
	                
	                //survey.setTopic(topic);
	                survey.setName(name);
	                survey.setActive(isActive);
	                
	                surveys.add(survey);
	        	}
	        }
	        catch (SQLException e) {
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
	    	return surveys;  
	    }	    
	    
	   
}
