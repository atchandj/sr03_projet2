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
        PreparedStatement preparedStatement2 = null;
        String query = null;
        String query2 = null;
        String previousTopicName = "";
        Topic tmpTopic = null;
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        int i = 0;
        try{
            connexion = daoFactory.getConnection();
            query = "SELECT * FROM("
            		+ "SELECT NAQ.id as questionnaireId, T.name AS topicName, NAQ.name AS questionnaireName, NAQ.active AS activeQuestionnaire, 0 AS activableQuestionnaire "
            		+ "FROM Topic T INNER JOIN NotActivableQuestionnaire NAQ "
            		+ "ON T.name = NAQ.Topic "
            		+ "UNION ALL "
            		+ "SELECT AQ.id as questionnaireId, T.name AS topicName, AQ.name AS questionnaireName, AQ.active AS activeQuestionnaire, 1 AS activableQuestionnaire "
            		+ "FROM Topic T INNER JOIN ActivableQuestionnaire AQ "
            		+ "ON T.name = AQ.Topic "
            		+ "UNION ALL "
            		+ "SELECT UQ.id as questionnaireId, T.name AS topicName, UQ.name AS questionnaireName, UQ.active AS activeQuestionnaire, NULL AS activableQuestionnaire "
            		+ "FROM Topic T LEFT OUTER JOIN Questionnaire UQ "
            		+ "ON T.name = UQ.Topic "
            		+ "WHERE UQ.id IS NULL"
            		+ ")R "
            		+ "ORDER BY R.topicName, R.questionnaireName;";
            // System.out.println(query); // Test
            query2 = "SELECT * FROM( "
            		+ "SELECT NDQ.id as questionnaireId, T.name AS topicName, NDQ.name AS questionnaireName, 0 AS deletableQuestionnaire "
            		+ "FROM Topic T INNER JOIN NotDeletableQuestionnaire NDQ "
            		+ "ON T.name = NDQ.topic "
            		+ "UNION ALL "
            		+ "SELECT DQ.id as questionnaireId, T.name AS topicName, DQ.name AS questionnaireName, 1 AS deletableQuestionnaire "
            		+ "FROM Topic T INNER JOIN DeletableQuestionnaire DQ "
            		+ "ON T.name = DQ.topic "
            		+ "UNION ALL "
            		+ "SELECT UQ.id as questionnaireId, T.name AS topicName, UQ.name AS questionnaireName, NULL AS deletableQuestionnaire "
            		+ "FROM Topic T LEFT OUTER JOIN Questionnaire UQ "
            		+ "ON T.name = UQ.topic "
            		+ "WHERE UQ.id IS NULL "
            		+ ")R "
            		+ "ORDER BY R.topicName, R.questionnaireName;";
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement2 = (PreparedStatement) connexion.prepareStatement(query2);
            ResultSet result = preparedStatement.executeQuery();
            ResultSet result2 = preparedStatement2.executeQuery();  
            while (result.next()) {
            	result2.next();
            	i += 1;
            	String topicName = result.getString("topicName");
            	int questionnaireId = result.getInt("questionnaireId");
                String questionnaireName = result.getString("questionnaireName");
                boolean activeQuestionnaire = result.getBoolean("activeQuestionnaire");
                boolean activableQuestionnaire = result.getBoolean("activableQuestionnaire");
                boolean deletableQuestionnaire = result2.getBoolean("deletableQuestionnaire");
                
                if(!previousTopicName.equals(topicName)){
                	if(i != 1){
                		topics.add(tmpTopic);
                	}                	
            		tmpTopic = new Topic(topicName);
            		previousTopicName = topicName;            		
                }
                if(questionnaireName != null){
                	Questionnaire tmpQuestionnaire = new Questionnaire(questionnaireId, questionnaireName, activeQuestionnaire, activableQuestionnaire, deletableQuestionnaire);
                	tmpTopic.getQuestionnaires().add(tmpQuestionnaire);    
                }            
            }
            if(i >= 1){
            	topics.add(tmpTopic);
            }            
        } catch (SQLException e) {
            throw new DaoException(databaseErrorMessage + ": " + e.getMessage());
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException(databaseErrorMessage + ": " + e.getMessage());
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
    
    @Override
    public void addQuestionnaire(String topicName, String questionnaireName) throws DaoException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO Questionnaire(topic, name, active) "
        		+ "VALUE(?, ?, 0);";
        String topicErrorMessage = "Nouveau questionnaire impossible à ajouter.";
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        try{
            connexion = daoFactory.getConnection();
            // System.out.println(query); // Test
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setString(1, topicName);
            preparedStatement.setString(2, questionnaireName);
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
    
    @Override
	public void activateQuestionnaire(String topicName, String questionnaireName) throws DaoException{
    	// System.out.println("Activer questionnaire"); // Test
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = "UPDATE Questionnaire "
        		+ "SET active = 1 "
        		+ "WHERE topic = ? AND name = ?;";
        String questionnaireErrorMessage = "Impossible d'activer le questionnaire.";
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        try{
            connexion = daoFactory.getConnection();
            // System.out.println(query); // Test
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setString(1, topicName);
            preparedStatement.setString(2, questionnaireName);
            int result = preparedStatement.executeUpdate();
            connexion.commit();
            // System.out.println(result); // Test
            if(result == 0){
            	// System.out.println(questionnaireErrorMessage); // Test
            	throw new DaoException(questionnaireErrorMessage);
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
	
    @Override
	public void deleteTopic(String topicName) throws DaoException{
    	// System.out.println("Supprimer sujet"); // Test
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM Topic "
        		+ "WHERE name = ?;";
        String topicErrorMessage = "Impossible de supprimer le sujet.";
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        try{
            connexion = daoFactory.getConnection();
            // System.out.println(query); // Test
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setString(1, topicName);
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
	
    @Override
	public void deleteQuestionnaire(String topicName, String questionnaireName) throws DaoException{
    	// System.out.println("Supprimer questionnaire"); // Test
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM Questionnaire "
        		+ "WHERE topic = ? AND name = ?;";
        String questionnaireErrorMessage = "Impossible de supprimer le questionnaire.";
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        try{
            connexion = daoFactory.getConnection();
            // System.out.println(query); // Test
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setString(1, topicName);
            preparedStatement.setString(2, questionnaireName);
            // System.out.println(topicName);
            // System.out.println(questionnaireName);
            int result = preparedStatement.executeUpdate();
            connexion.commit();
            // System.out.println(result); // Test
            if(result == 0){
            	// System.out.println(questionnaireErrorMessage); // Test
            	throw new DaoException(questionnaireErrorMessage);
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
    
    public void updateTopic(String oldTopicName, String newTopicName) throws DaoException{
		// System.out.println("Mettre à jour le sujet"); // Test
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		String query = "UPDATE Topic "
				+ "SET name = ? "
				+ "WHERE name = ?;";
		String databaseErrorMessage = "Impossible de communiquer avec la base de données";
		String topicErrorMessage = "Impossible de mettre à jour le sujet";
		try{
		    connexion = daoFactory.getConnection();
		    // System.out.println(query); // Test
		    preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
		    // System.out.println(newTopicName); // Test
		    // System.out.println(oldTopicName); // Test
		    preparedStatement.setString(1, newTopicName);
		    preparedStatement.setString(2, oldTopicName);
		    int result = preparedStatement.executeUpdate();
		    connexion.commit();
            if(result == 0){
            	throw new DaoException(topicErrorMessage);
            }		    
		} catch (SQLException e) {
		    throw new DaoException(databaseErrorMessage + ": " + e.getMessage());
		}
		finally {
		    try {
		        if (connexion != null) {
		            connexion.close();  
		        }
		    } catch (SQLException e) {
		        throw new DaoException(databaseErrorMessage + ": " + e.getMessage());
		    }
		}	
    }
    
	public void updateQuestionnaire(String topicName, String oldQuestionnaireName, String newQuestionnaireName) throws DaoException{
		// System.out.println("Mettre à jour le questionnaire"); // Test
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		String query = "UPDATE Questionnaire "
				+ "SET name = ? "
				+ "WHERE topic = ? AND name = ?;";
		String databaseErrorMessage = "Impossible de communiquer avec la base de données";
		String questionnaireErrorMessage = "Impossible de mettre à jour le questionnaire";
		try{
		    connexion = daoFactory.getConnection();
		    // System.out.println(query); // Test
		    preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
		    // System.out.println(newQuestionnaireName); // Test
		    // System.out.println(topicName); // Test
		    // System.out.println(oldQuestionnaireName); // Test
		    preparedStatement.setString(1, newQuestionnaireName);
		    preparedStatement.setString(2, topicName);
		    preparedStatement.setString(3, oldQuestionnaireName);
		    int result = preparedStatement.executeUpdate();
		    connexion.commit();
            if(result == 0){
            	throw new DaoException(questionnaireErrorMessage);
            }		    
		} catch (SQLException e) {
		    throw new DaoException(databaseErrorMessage + ": " + e.getMessage());
		}
		finally {
		    try {
		        if (connexion != null) {
		            connexion.close();  
		        }
		    } catch (SQLException e) {
		        throw new DaoException(databaseErrorMessage + ": " + e.getMessage());
		    }
		}
	}
}
