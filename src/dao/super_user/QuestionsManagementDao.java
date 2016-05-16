package dao.super_user;

import beans.trainee.Questionnaire;
import dao.DaoException;

public interface QuestionsManagementDao {
	 Questionnaire getQuestionnaire(String topicName, String questionnaireName) throws DaoException;
	 void deleteQuestion(int questionnaireId, int questionOrderNumber) throws DaoException;
	 void activateQuestion(int questionnaireId, int questionOrderNumber) throws DaoException;
	 void activateAnswer(int questionId, int answerOrderNumber) throws DaoException;
	 void deleteAnswer(int questionId, int answerOrderNumber) throws DaoException;
	 void addAnswer(int questionId, String answerValue) throws DaoException;
}
