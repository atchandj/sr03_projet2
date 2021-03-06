package dao.super_user;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import beans.trainee.Answer;
import beans.trainee.BadAnswer;
import beans.trainee.GoodAnswer;
import beans.trainee.Question;
import beans.trainee.Questionnaire;
import dao.DaoException;
import dao.DaoFactory;

public class QuestionsManagementDaoImpl implements QuestionsManagementDao {
	
	private DaoFactory daoFactory;
	
    public QuestionsManagementDaoImpl(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }
	
    public Questionnaire getQuestionnaire(String topicName, String questionnaireName) throws DaoException{
        Connection connexion = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;
        PreparedStatement preparedStatement4 = null;
        String query1 = "SELECT Q.id AS id, Q.active AS active "
        		+ "FROM Questionnaire Q "
        		+ "WHERE Q.topic = ? AND Q.name = ?;";
        String query2 = null;
        String query3 = null;
        String query4 = null;
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        int i = 0;
        int previousQuestionOrderNumber = 0;
        boolean deletableQuestion = false;
        boolean changeableTrueAnswerQuestion = false;
        Question tmpQuestion = null;
        Answer tmpAnswer = null;
        Questionnaire questionnaire = null;
        try{
            connexion = daoFactory.getConnection();
            preparedStatement1 = (PreparedStatement) connexion.prepareStatement(query1);
            preparedStatement1.setString(1, topicName);
            preparedStatement1.setString(2, questionnaireName);
            ResultSet result1 = preparedStatement1.executeQuery();
            result1.next();
            int questionnaireId = result1.getInt("id");
            boolean questionnaireActive = result1.getBoolean("active");
            
            query2 = "SELECT * FROM( "
            		+ "SELECT NAQ.id AS questionId, NAQ.orderNumber AS questionOrderNumber, NAQ.value AS questionValue, NAQ.active AS activeQuestion, 0 AS activableQuestion, A.orderNumber AS answerOrderNumber, A.value AS answserValue, A.active AS activeAnswer, A.t AS answerType "
            		+ "FROM NotActivableQuestion NAQ LEFT OUTER JOIN Answer A "
            		+ "ON NAQ.id = A.question "
            		+ "WHERE NAQ.questionnaire = ? "
            		+ "UNION ALL "
            		+ "SELECT AQ.id AS questionId, AQ.orderNumber AS questionOrderNumber, AQ.value AS questionValue, AQ.active AS activeQuestion, 1 AS activableQuestion, A.orderNumber AS answerOrderNumber, A.value AS answserValue, A.active AS activeAnswer, A.t AS answerType "
            		+ "FROM ActivableQuestion AQ INNER JOIN Answer A "
            		+ "ON AQ.id = A.question  "
            		+ "WHERE AQ.questionnaire = ? "
            		+ ")R "
            		+ "ORDER BY R.questionOrderNumber, R.answerOrderNumber;";
            
            
            query3 = "SELECT * FROM( "
            		+ "SELECT NDQ.orderNumber AS questionOrderNumber, 0 AS deletableQuestion "
            		+ "FROM NotDeletableQuestion NDQ "
            		+ "WHERE NDQ.questionnaire = ? "
            		+ "UNION ALL "
            		+ "SELECT DQ.orderNumber AS questionOrderNumber, 1 AS deletableQuestion "
            		+ "FROM DeletableQuestion DQ "
            		+ "WHERE DQ.questionnaire = ? "
            		+ ")R "
            		+ "ORDER BY R.questionOrderNumber;";
            
            query4 = "SELECT * FROM( "
            		+ "SELECT NCTAQ.orderNumber AS questionOrderNumber, 0 AS changeableTrueAnswerQuestion "
            		+ "FROM NotChangeableTrueAnswerQuestion NCTAQ "
            		+ "WHERE NCTAQ.questionnaire = ? "
            		+ "UNION ALL "
            		+ "SELECT CTAQ.orderNumber AS questionOrderNumber, 1 AS changeableTrueAnswerQuestion "
            		+ "FROM ChangeableTrueAnswerQuestion CTAQ "
            		+ "WHERE CTAQ.questionnaire = ? "
            		+ ")R "
            		+ "ORDER BY R.questionOrderNumber;";
            
            preparedStatement2 = (PreparedStatement) connexion.prepareStatement(query2);
            preparedStatement2.setInt(1, questionnaireId);
            preparedStatement2.setInt(2, questionnaireId);
            preparedStatement3 = (PreparedStatement) connexion.prepareStatement(query3);
            preparedStatement3.setInt(1, questionnaireId);
            preparedStatement3.setInt(2, questionnaireId);
            preparedStatement4 = (PreparedStatement) connexion.prepareStatement(query4);
            preparedStatement4.setInt(1, questionnaireId);
            preparedStatement4.setInt(2, questionnaireId);
            ResultSet result2 = preparedStatement2.executeQuery();
            ResultSet result3 = preparedStatement3.executeQuery(); 
            ResultSet result4 = preparedStatement4.executeQuery();
            questionnaire = new Questionnaire(questionnaireId, questionnaireName, questionnaireActive);
            
            while (result2.next()) {            	
            	i += 1;          
            	int questionId = result2.getInt("questionId");
            	int questionOrderNumber = result2.getInt("questionOrderNumber");
            	String questionValue = result2.getString("questionValue");
            	boolean activeQuestion = result2.getBoolean("activeQuestion");
            	boolean activableQuestion = result2.getBoolean("activableQuestion");
            	int answerOrderNumber = result2.getInt("answerOrderNumber");
            	String answserValue = result2.getString("answserValue");
            	boolean activeAnswer = result2.getBoolean("activeAnswer");
            	String answerType = result2.getString("answerType");
            	
                if(previousQuestionOrderNumber != questionOrderNumber){
                	result3.next();
                	result4.next();
            		deletableQuestion = result3.getBoolean("deletableQuestion");
            		changeableTrueAnswerQuestion = result4.getBoolean("changeableTrueAnswerQuestion");
                	if(i != 1){
                		questionnaire.getQuestions().add(tmpQuestion);
                	}                	
                	
                	tmpQuestion = new Question(questionId, questionValue, questionOrderNumber, activeQuestion, activableQuestion, deletableQuestion, questionnaireId, changeableTrueAnswerQuestion);
                	previousQuestionOrderNumber = questionOrderNumber;            		
                }
                if(answerType != null){
                	if(answerType.equals("GoodAnswer")){
                		tmpAnswer = new GoodAnswer(answerOrderNumber, answserValue, activeAnswer);
                	}else{
                		tmpAnswer = new BadAnswer(answerOrderNumber, answserValue, activeAnswer);
                	}
                	tmpQuestion.getAnswers().add(tmpAnswer);    
                }            
            }
            if( i >= 1){
            	questionnaire.getQuestions().add(tmpQuestion);
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
        return questionnaire;
    }
    
    public void deleteQuestion(int questionnaireId, int questionOrderNumber) throws DaoException{
    	
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = "CALL deleteQuestion(?, ?);";
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, questionnaireId);
            preparedStatement.setInt(2, questionOrderNumber);
            preparedStatement.executeUpdate();
            connexion.commit();
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
    
    public void activateQuestion(int questionnaireId, int questionOrderNumber) throws DaoException{
    	
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = "UPDATE Question "
        		+ "SET active = TRUE "
        		+ "WHERE questionnaire = ? AND orderNumber = ?;";
        String questionErrorMessage = "Impossible d'activer la question.";
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, questionnaireId);
            preparedStatement.setInt(2, questionOrderNumber);
            int result = preparedStatement.executeUpdate();
            connexion.commit();
            if(result == 0){
            	throw new DaoException(questionErrorMessage);
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
    
    public void activateAnswer(int questionId, int answerOrderNumber) throws DaoException{
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = "UPDATE Answer "
        		+ "SET active = TRUE "
        		+ "WHERE question = ? AND orderNumber = ?;";
        String answerErrorMessage = "Impossible d'activer la réponse.";
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, questionId);
            preparedStatement.setInt(2, answerOrderNumber);
            int result = preparedStatement.executeUpdate();
            connexion.commit();
            if(result == 0){
            	throw new DaoException(answerErrorMessage);
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
    
    public void deleteAnswer(int questionId, int answerOrderNumber) throws DaoException{
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = "CALL deleteAnswer(?, ?);";
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, questionId);
            preparedStatement.setInt(2, answerOrderNumber);
            preparedStatement.executeUpdate();
            connexion.commit();
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
    
    public void addAnswer(int questionId, String answerValue) throws DaoException{
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = "CALL addAnswer(?, ?);";
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, questionId);
            preparedStatement.setString(2, answerValue);
            preparedStatement.executeUpdate();
            connexion.commit();
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
    
    public void addQuestion(int questionnaireId, String questionValue) throws DaoException{
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = "CALL addQuesion(?, ?);";
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, questionnaireId);
            preparedStatement.setString(2, questionValue);
            preparedStatement.executeUpdate();
            connexion.commit();
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
    
    public void setTrueAnswer(int questionId, int answerOrderNumber) throws DaoException{
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = "CALL changeTrueAnswerOfAQuestion(?, ?);";
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, questionId);
            preparedStatement.setInt(2, answerOrderNumber);
            preparedStatement.executeUpdate();
            connexion.commit();
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
    
    public Answer getAnswer(int questionId, int answerOrderNumber) throws DaoException{
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT A.value AS value "
        		+ "FROM Answer A "
        		+ "WHERE A.question = ? AND A.orderNumber = ?;";
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        Answer answer = null;
        String answerValue = null;
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, questionId);
            preparedStatement.setInt(2, answerOrderNumber);
            ResultSet result = preparedStatement.executeQuery();
            boolean ok = result.next();
            if(ok){
            	answerValue = result.getString("value");
            	answer = new Answer(answerOrderNumber, answerValue);            	
            }else{
            	throw new DaoException(databaseErrorMessage);
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
        return answer;
    }
    
	public void updateAnswer(int questionId, int answerOrderNumber, String answerValue) throws DaoException{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		String query = "UPDATE Answer "
				+ "SET value = ? "
				+ "WHERE question = ? AND orderNumber = ?;";
		String databaseErrorMessage = "Impossible de communiquer avec la base de données";
		String answerErrorMessage = "Impossible de mettre à jour la réponse";
		try{
		    connexion = daoFactory.getConnection();
		    preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
		    preparedStatement.setString(1, answerValue);
		    preparedStatement.setInt(2, questionId);
		    preparedStatement.setInt(3, answerOrderNumber);
		    int result = preparedStatement.executeUpdate();
		    connexion.commit();
            if(result == 0){
            	throw new DaoException(answerErrorMessage);
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
	
	public Question getQuestion(int questionnaireId, int questionOrderNumber) throws DaoException{
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT Q.id AS questionId, Q.value AS questionValue "
        		+ "FROM Question Q "
        		+ "WHERE Q.questionnaire = ? AND Q.orderNumber = ?;";
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        Question question = null;
        int questionId = 0;
        String questionValue = null;
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, questionnaireId);
            preparedStatement.setInt(2, questionOrderNumber);
            ResultSet result = preparedStatement.executeQuery();
            boolean ok = result.next();
            if(ok){
            	questionId = result.getInt("questionId");
            	questionValue = result.getString("questionValue");
            	question = new Question(questionId, questionValue);            	
            }else{
            	throw new DaoException(databaseErrorMessage);
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
        return question;		
	}
	
	public void updateQuestion(int questionId, String newQuestionValue) throws DaoException{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		String query = "UPDATE Question "
				+ "SET value = ? "
				+ "WHERE id = ?;";
		String databaseErrorMessage = "Impossible de communiquer avec la base de données";
		String answerErrorMessage = "Impossible de mettre à jour la question";
		try{
		    connexion = daoFactory.getConnection();
		    preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
		    preparedStatement.setString(1, newQuestionValue);
		    preparedStatement.setInt(2, questionId);
		    int result = preparedStatement.executeUpdate();
		    connexion.commit();
            if(result == 0){
            	throw new DaoException(answerErrorMessage);
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
	
	public void exchangeQuestionsOrder(int questionnaireId, int question1OrderNumber, int question2OrderNumber) throws DaoException{
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = "CALL changeQuestionsOrder(?, ?, ?);";
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, questionnaireId);
            preparedStatement.setInt(2, question1OrderNumber);
            preparedStatement.setInt(3, question2OrderNumber);
            preparedStatement.executeUpdate();
            connexion.commit();
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
	
	public void exchangeAnswersOrder(int questionId, int answer1OrderNumber, int answer2OrderNumber) throws DaoException{
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = "CALL changeAnswersOrder(?, ?, ?);";
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, questionId);
            preparedStatement.setInt(2, answer1OrderNumber);
            preparedStatement.setInt(3, answer2OrderNumber);
            preparedStatement.executeUpdate();
            connexion.commit();
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