package dao.trainee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import beans.trainee.Answer;
import beans.trainee.Attempt;
import beans.trainee.BadAnswer;
import beans.trainee.GoodAnswer;
import beans.trainee.Question;
import beans.trainee.Questionnaire;
import beans.trainee.Topic;
import beans.trainee.Trainee;
import dao.DaoException;
import dao.DaoFactory;

public class QuestionnairesListDaoImpl implements QuestionnairesListDao {
	 private DaoFactory daoFactory;

    public QuestionnairesListDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    @Override
    public List<Topic> getActivatedQuestionnaire() throws DaoException {
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
            		+ "SELECT UQ.id as questionnaireId, T.name AS topicName, UQ.name AS questionnaireName, UQ.active AS questionnaireActive, NULL AS questionnaireValidable "
            		+ "FROM Topic T INNER JOIN Questionnaire UQ "
            		+ "ON T.name = UQ.Topic "
            		+ "WHERE UQ.id IS NOT NULL AND UQ.active = TRUE"
            		+ ")R "
            		+ "ORDER BY R.topicName, R.questionnaireName;";
            
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
            query = "SELECT Q.id as questionId, Q.questionnaire as questionnaireId, Q.value as questionValue, Q.active as questionActive, Q.orderNumber as questionOrderNumber "
            		+ "FROM Question Q INNER JOIN Questionnaire UQ "
            		+ "ON Q.questionnaire = UQ.id "
            		+ "WHERE UQ.id = ? AND Q.active = TRUE AND UQ.active = TRUE "
            		+ "ORDER By Q.orderNumber ASC ;";
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, idQuestionnaire);
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
                tmpQuestion.setAnswers(getAnswer(questionId));;
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
    
    @Override
    public ArrayList<Answer> getAnswer(int idQuestion) throws DaoException {
    	ArrayList<Answer> answers = new ArrayList<Answer>();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = null;
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        try{
            connexion = daoFactory.getConnection();
            query = "SELECT A.id as answerId, A.question as questionId, A.value as answerValue, A.active answerActive, A.orderNumber as answerOrderNumber, A.t as answerType  "
            		+ "FROM Answer A INNER JOIN Question Q "
            		+ "ON Q.id = A.question "
            		+ "WHERE A.question = ? AND A.active = TRUE AND Q.active = TRUE "
            		+ "ORDER BY A.orderNumber ASC;";
            		
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, idQuestion);
            ResultSet result = preparedStatement.executeQuery();            
            while (result.next()) {	            	
                String answerValue = result.getString("answerValue");
                boolean answerActive = result.getBoolean("answerActive");
                int answerOrderNumber = result.getInt("answerOrderNumber"),
                	answerId = result.getInt("answerId"),
                	questionId = result.getInt("questionId");
                
                if(result.getString("answerType").equals("GoodAnswer")){
                	answers.add(new GoodAnswer(answerId,questionId, answerOrderNumber, answerValue, answerActive));
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
            	e.printStackTrace();
                throw new DaoException(databaseErrorMessage);
            }
        }
        return answers;
	}
     
    @Override
    public void addAttempt(Trainee trainee, Attempt attempt) throws DaoException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        try{
            connexion = daoFactory.getConnection();
            String query = "INSERT INTO Attempt "
            		+ "(trainee, questionnaire, score, beginning, end) "
            		+ "VALUES (?, ?, ?, ?, ?);";
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, trainee.getId());
            preparedStatement.setInt(2, attempt.getQuestionnaireId());
            preparedStatement.setInt(3, attempt.getScore());
            preparedStatement.setString(4, attempt.getBeginingSql());
            preparedStatement.setString(5, attempt.getEndSql());
            preparedStatement.executeUpdate();
            ResultSet valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if(valeursAutoGenerees.next()){
            	int attemptId = valeursAutoGenerees.getInt( 1 );
            	query = "INSERT INTO AttemptAnswer "
            			+ "(attempt, answer) "
            			+ "VALUES (?, ?);";
            	for(Answer a : attempt.getAttemptedAnswers())
            	{
            		preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
	            	preparedStatement.setInt(1, attemptId);
		            preparedStatement.setInt(2, a.getId());
		            preparedStatement.executeUpdate();
            	}
            }
            connexion.commit();
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
    }
	   
}
