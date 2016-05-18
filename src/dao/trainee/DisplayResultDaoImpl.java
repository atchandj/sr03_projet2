package dao.trainee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import beans.trainee.Answer;
import beans.trainee.Attempt;
import beans.trainee.BadAnswer;
import beans.trainee.GoodAnswer;
import beans.trainee.Question;
import dao.DaoException;
import dao.DaoFactory;

public class DisplayResultDaoImpl implements DisplayResultDao {
	private DaoFactory daoFactory;

    public DisplayResultDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    @Override
    public List<Attempt> getAttemptsList(int traineeId) throws DaoException {
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
            	int attemptId = result.getInt("attemptId");
            	String topicName = result.getString("topicName");
                String questionnaireName = result.getString("questionnaireName");
                int score = result.getInt("score");
                Timestamp beginning = result.getTimestamp("beginning");
                Timestamp end = result.getTimestamp("end");
                int durationInSeconds = result.getInt("durationInSeconds");
                double scoreDivByDurationTimes100 = result.getDouble("scoreDivByDurationTimes100");
                Attempt tmpAttempt= new Attempt(attemptId, topicName, questionnaireName,score, beginning, end, durationInSeconds, scoreDivByDurationTimes100);
            	
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
    
    @Override
    public Attempt getAttempt(int traineeId, int attemptId) throws DaoException {
        Attempt attempt = new Attempt();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = null;
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        try{
            connexion = daoFactory.getConnection();
            query = "SELECT R1.attemptId, R2.questionnaireName, R1.topicName, R1.score, R1.beginning, R1.end, R1.questionnaireId, "
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
            		+ "WHERE Tr.id = R1.traineeId AND R1.traineeId = ? AND R1.attemptId = ? "
            		+ "ORDER BY R1.topicName;";
            // System.out.println(query); // Test
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, traineeId);
            preparedStatement.setInt(2, attemptId);
            ResultSet result = preparedStatement.executeQuery();            
            while (result.next()) {
            	String topicName = result.getString("topicName");
                String questionnaireName = result.getString("questionnaireName");
                int score = result.getInt("score");
                Timestamp beginning = result.getTimestamp("beginning");
                Timestamp end = result.getTimestamp("end");
                int durationInSeconds = result.getInt("durationInSeconds");
                int questionnaireId = result.getInt("questionnaireId");
                double scoreDivByDurationTimes100 = result.getDouble("scoreDivByDurationTimes100");
                
                attempt = new Attempt(attemptId, topicName, questionnaireName,score, beginning, end, durationInSeconds, scoreDivByDurationTimes100);
                attempt.setQuestionnaireId(questionnaireId);
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
        return attempt;
	}
    
    
    @Override
    public ArrayList<Answer> getAttemptedAnswers(int attemptId) throws DaoException {
    	ArrayList<Answer> answers = new ArrayList<Answer>();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = null;
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        try{
            connexion = daoFactory.getConnection();
            query = "SELECT A.id as answerId, A.question as questionId, A.value as answerValue, A.active answerActive, A.orderNumber as answerOrderNumber, A.t as answerType "
            		+ "FROM ( "
            		+ "SELECT ATP.id as attemptId, AA.answer as answerId "
            		+ "FROM attemptanswer AA INNER JOIN attempt ATP "
            		+ "ON AA.attempt = ATP.id "
            		+ "WHERE ATP.id = ?)R1 "
            		+ "INNER JOIN answer A ON A.id = R1.answerId;";
            // System.out.println(query); // Test
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, attemptId);
            ResultSet result = preparedStatement.executeQuery();            
            while (result.next()) {	            	
                String answerValue = result.getString("answerValue");
                boolean answerActive = result.getBoolean("answerActive");
                int answerOrderNumber = result.getInt("answerOrderNumber"),
                	answerId = result.getInt("answerId"),
                	questionId = result.getInt("questionId");
                
                if(result.getString("answerType").equals("GoodAnswer")){
                	answers.add(new GoodAnswer(answerId, questionId, answerOrderNumber, answerValue, answerActive));
                }
                else{
                	answers.add(new BadAnswer(answerId, questionId, answerOrderNumber, answerValue, answerActive));
                }
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
        return answers;
	}
    
    @Override
    public List<Question> getQuestions(int idAttempt) throws DaoException {
        List<Question> questions = new ArrayList<Question>();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = null;
        Question tmpQuestion = null;
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        try{
            connexion = daoFactory.getConnection();
            query = "SELECT Q.id as questionId, Q.questionnaire as questionnaireId, Q.value as questionValue, Q.active as questionActive, Q.orderNumber as questionOrderNumber "
            		+ "FROM ("
            		+ "SELECT A.id as answerId, A.question as questionId, A.value as answerValue, A.active answerActive, A.orderNumber as answerOrderNumber, A.t as answerType "
            		+ "FROM ( "
            		+ "SELECT ATP.id as attemptId, AA.answer as answerId "
            		+ "FROM attemptanswer AA INNER JOIN attempt ATP ON AA.attempt = ATP.id "
            		+ "WHERE ATP.id = ?)R1 INNER JOIN answer A ON A.id = R1.answerId)R2, question Q "
            		+ "WHERE Q.id = R2.questionId;";
             //System.out.println(query); // Test
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, idAttempt);
            ResultSet result = preparedStatement.executeQuery();            
            while (result.next()) {
            	tmpQuestion = new Question();
            	
            	int questionId = result.getInt("questionId");
                String questionValue = result.getString("questionValue");
                boolean questionActive = result.getBoolean("questionActive");
                int questionOrderNumber = result.getInt("questionOrderNumber");
                int questionnaireId = result.getInt("questionnaireId");
                
                tmpQuestion.setId(questionId);
                tmpQuestion.setValue(questionValue);
                tmpQuestion.setOrderNumber(questionOrderNumber);
                tmpQuestion.setActive(questionActive);
                tmpQuestion.setQuestionnaireId(questionnaireId);
                tmpQuestion.setAnswers(this.daoFactory.getQuestionnairesListDao().getAnswer(questionId));;
                questions.add(tmpQuestion);
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
            	e.printStackTrace();
                throw new DaoException(databaseErrorMessage);
            }
        }
        return questions;
	}
}
