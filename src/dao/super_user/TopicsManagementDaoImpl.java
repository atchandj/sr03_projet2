package dao.super_user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import beans.trainee.Questionnaire;
import beans.trainee.Topic;
import dao.DaoException;
import dao.DaoFactory;

public class TopicsManagementDaoImpl implements TopicsManagementDao {

	private DaoFactory daoFactory;
	
    public TopicsManagementDaoImpl(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }
	
	@Override
	public List<Topic> getTopics() throws DaoException {
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
            		+ "SELECT T.name AS topicName, NAQ.name AS questionnaireName, NAQ.active AS questionnaireActive, 0 AS questionnaireValidable "
            		+ "FROM Topic T INNER JOIN NotActivableQuestionnaire NAQ "
            		+ "ON T.name = NAQ.Topic "
            		+ "UNION ALL "
            		+ "SELECT T.name AS topicName, AQ.name AS questionnaireName, AQ.active AS questionnaireActive, 1 AS questionnaireValidable "
            		+ "FROM Topic T INNER JOIN ActivableQuestionnaire AQ "
            		+ "ON T.name = AQ.Topic "
            		+ "UNION ALL "
            		+ "SELECT T.name AS topicName, UQ.name AS questionnaireName, UQ.active AS questionnaireActive, NULL AS questionnaireValidable "
            		+ "FROM Topic T LEFT OUTER JOIN Questionnaire UQ "
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
                	Questionnaire tmpQuestionnaire = new Questionnaire(questionnaireName, questionnaireActive, questionnaireValidable);
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
    public void addTopic(String newTopicName) throws DaoException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO Topic "
        		+ "VALUE(?);";
        String topicErrorMessage = "Nouveau sujet impossible à ajouter.";
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        try{
            connexion = daoFactory.getConnection();
            // System.out.println(query); // Test
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setString(1, newTopicName);
            int result = preparedStatement.executeUpdate();
            connexion.commit();
            // System.out.println(result); // Test
            if(result == 0){
            	// System.out.println(topicErrorMessage); // Test
            	throw new DaoException(topicErrorMessage);
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
    }
}
