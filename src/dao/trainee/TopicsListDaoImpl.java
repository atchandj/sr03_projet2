package dao.trainee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;


import beans.super_user.SuperUser;
import beans.trainee.Question;
import beans.trainee.Questionnaire;
import beans.trainee.Topic;
import beans.trainee.Trainee;
import dao.DaoException;
import dao.DaoFactory;
import dao.super_user.UsersManagementDao;

public class TopicsListDaoImpl implements TopicsListDao {
	 private DaoFactory daoFactory;

	    public TopicsListDaoImpl(DaoFactory daoFactory) {
	        this.daoFactory = daoFactory;
	    }
	    
	    @Override
	    public List<Topic> getActivatedTopics() throws DaoException {
	        List<Topic> topics = new ArrayList<Topic>();
	        Connection connexion = null;
	        PreparedStatement preparedStatement = null;
	        String query = null;
	        String previousTopicName = "";
	        Topic tmpTopic = null;
	        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
	        int i = 0;
	        try{
	            connexion = daoFactory.getConnection();
	            query = "SELECT * FROM("
	            		+ "SELECT NAQ.id as questionnaireId, T.name AS topicName, NAQ.name AS questionnaireName, NAQ.active AS questionnaireActive, 0 AS questionnaireValidable "
	            		+ "FROM Topic T INNER JOIN NotActivableQuestionnaire NAQ "
	            		+ "ON T.name = NAQ.Topic "
	            		+ "UNION ALL "
	            		+ "SELECT UQ.id as questionnaireId, T.name AS topicName, UQ.name AS questionnaireName, UQ.active AS questionnaireActive, NULL AS questionnaireValidable "
	            		+ "FROM Topic T INNER JOIN Questionnaire UQ "
	            		+ "ON T.name = UQ.Topic "
	            		+ "WHERE UQ.id IS NULL"
	            		+ ")R "
	            		+ "ORDER BY R.topicName, R.questionnaireName;";
	            // System.out.println(query); // Test
	            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
	            ResultSet result = preparedStatement.executeQuery();            
	            while (result.next()) {
	            	i += 1;	            	
	            	String topicName = result.getString("topicName");
	            	int questionnaireId = result.getInt("questionnaireId");
	                String questionnaireName = result.getString("questionnaireName");
	                boolean questionnaireActive = result.getBoolean("questionnaireActive");
	                boolean questionnaireValidable = result.getBoolean("questionnaireValidable");
	                
	                if(!previousTopicName.equals(topicName)){
	                	if(i != 1){
	                		topics.add(tmpTopic);
	                	}                	
	            		tmpTopic = new Topic(topicName);
	            		previousTopicName = topicName;            		
	                }
	                if(questionnaireName != null){
	                	Questionnaire tmpQuestionnaire = new Questionnaire(questionnaireId, questionnaireName, questionnaireActive, questionnaireValidable);
	                	tmpTopic.getQuestionnaires().add(tmpQuestionnaire);    
	                }            
	            }
	            topics.add(tmpTopic);
	        } catch (SQLException e) {
	            throw new DaoException(databaseErrorMessage);
	        }
	        finally {
	            try {
	                if (connexion != null) {
	                    connexion.close();  
	                }
	            } catch (SQLException e) {
	                throw new DaoException(databaseErrorMessage);
	            }
	        }
	        return topics;
		}
	    
	    @Override
	    public List<Question> getQuestions(int idQuestionnaire) throws DaoException {
	        List<Question> questions = new ArrayList<Question>();
	        Connection connexion = null;
	        PreparedStatement preparedStatement = null;
	        String query = null;
	        Question tmpQuestion = null;
	        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
	        try{
	            connexion = daoFactory.getConnection();
	            query = "SELECT UQ.id, Q.value as questionValue, Q.active as questionActive, Q.orderNumber as questionOrderNumber "
	            		+ "FROM question Q INNER JOIN questionnaire UQ "
	            		+ "ON Q.questionnaire = UQ.id "
	            		+ "WHERE UQ.id = " + idQuestionnaire + " AND Q.active = 1 "
	            		+ "ORDER By questionOrderNumber ASC ;";
	             //System.out.println(query); // Test
	            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
	            ResultSet result = preparedStatement.executeQuery();            
	            while (result.next()) {
	            	tmpQuestion = new Question();
	            	
	                String questionValue = result.getString("questionValue");
	                boolean questionActive = result.getBoolean("questionActive");
	                int questionOrderNumber = result.getInt("questionOrderNumber");
	                
	                tmpQuestion.setValue(questionValue);
	                tmpQuestion.setOrderNumber(questionOrderNumber);
	                tmpQuestion.setActive(questionActive);
	                questions.add(tmpQuestion);
	            }	            
	        } catch (SQLException e) {
	            throw new DaoException(databaseErrorMessage);
	        }
	        finally {
	            try {
	                if (connexion != null) {
	                    connexion.close();  
	                }
	            } catch (SQLException e) {
	                throw new DaoException(databaseErrorMessage);
	            }
	        }
	        return questions;
		}
	   
}
