package dao.super_user;

import beans.trainee.Answer;
import beans.trainee.Question;
import beans.trainee.Questionnaire;
import dao.DaoException;

public interface QuestionsManagementDao {
	 Questionnaire getQuestionnaire(String topicName, String questionnaireName) throws DaoException;
	 void deleteQuestion(int questionnaireId, int questionOrderNumber) throws DaoException;
	 void activateQuestion(int questionnaireId, int questionOrderNumber) throws DaoException;
	 void activateAnswer(int questionId, int answerOrderNumber) throws DaoException;
	 void deleteAnswer(int questionId, int answerOrderNumber) throws DaoException;
	 void addAnswer(int questionId, String answerValue) throws DaoException;
	 void addQuestion(int questionnaireId, String questionValue) throws DaoException;
	 void setTrueAnswer(int questionId, int answerOrderNumber) throws DaoException;
	 Answer getAnswer(int questionId, int answerOrderNumber) throws DaoException;
	 void updateAnswer(int questionId, int answerOrderNumber, String answerValue) throws DaoException;
	 Question getQuestion(int questionnaireId, int questionOrderNumber) throws DaoException;
	 void updateQuestion(int questionId, String newQuestionValue) throws DaoException;
	 void exchangeQuestionsOrder(int questionnaireId, int question1OrderNumber, int question2OrderNumber) throws DaoException;
}
