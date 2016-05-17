package dao.trainee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import beans.trainee.Attempt;
import dao.DaoException;
import dao.DaoFactory;

public class DisplayResultDaoImpl implements DisplayResultDao {
	private DaoFactory daoFactory;

    public DisplayResultDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    @Override
    public List<Attempt> getAttempts(int traineeId) throws DaoException {
        List<Attempt> attempts = new ArrayList<Attempt>();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = null;
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        
        try{
            connexion = daoFactory.getConnection();
            query = "SELECT R1.attemptId, R2.questionnaireName, R1.topicName, R1.score, R1.beginning, R1.end, "
            		+ "TIMESTAMPDIFF(SECOND, R1.beginning, R1.end) AS durationInSeconds, "
            		+ "(R1.score/(TIMESTAMPDIFF(SECOND, R1.beginning, R1.end)))*100 AS scoreDivByDurationTimes100 "
            		+ "FROM Trainee Tr,"
            		+ "(SELECT A.id as attemptId, A.trainee as traineeId, A.score, A.beginning, A.end, A.questionnaire as questionnaireId, UQ.topic as topicName "
            		+ "FROM ATTEMPT A INNER JOIN questionnaire UQ "
            		+ "ON UQ.id = A.questionnaire)R1 "
            		+ "LEFT OUTER JOIN ("
            		+ "SELECT UQ.topic as topicName , UQ.id as questionnaireId, UQ.name as questionnaireName "
            		+ "FROM Topic T INNER JOIN questionnaire UQ "
            		+ "ON T.name = UQ.topic)R2 "
            		+ "ON R1.topicName = R2.topicName AND R2.questionnaireId  = R1.questionnaireId "
            		+ "WHERE Tr.id = R1.traineeId AND R1.traineeId = ? "
            		+ "ORDER BY R1.topicName;";
            // System.out.println(query); // Test
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, traineeId);
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
            	
            	attempts.add(tmpAttempt);
            }
            
        } catch (SQLException e) {
        	e.printStackTrace();
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
        return attempts;
	}

}
