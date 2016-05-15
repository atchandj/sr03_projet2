package dao.trainee;

import java.util.List;

import beans.trainee.Questionnaire;
import dao.DaoException;

public interface QuestionnaireListDao {
	List<Questionnaire> getQuestionnaireList() throws DaoException;
}
