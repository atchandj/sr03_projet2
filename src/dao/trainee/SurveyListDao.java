package dao.trainee;

import java.util.List;

import beans.Survey;
import dao.DaoException;

public interface SurveyListDao {
	List<Survey> getSurveyList() throws DaoException;
}
