package dao.trainee;

import java.util.List;

import beans.trainee.Question;
import beans.trainee.Topic;
import dao.DaoException;

public interface TopicsListDao {
	List<Topic> getActivatedTopics() throws DaoException;
	List<Question> getQuestions(int idQuestionnaire) throws DaoException;
}
