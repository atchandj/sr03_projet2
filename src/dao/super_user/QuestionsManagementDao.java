package dao.super_user;

import beans.trainee.Questionnaire;
import dao.DaoException;

public interface QuestionsManagementDao {
	 Questionnaire getQuestionnaire(String topicName, String questionnaireName) throws DaoException;
}
