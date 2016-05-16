package dao.trainee;

import java.util.ArrayList;
import java.util.List;

import beans.trainee.Answer;
import beans.trainee.Attempt;
import beans.trainee.BadAnswer;
import beans.trainee.GoodAnswer;
import beans.trainee.Question;
import beans.trainee.Topic;
import beans.trainee.Trainee;
import dao.DaoException;

public interface TopicsListDao {
	List<Topic> getActivatedTopics() throws DaoException;
	List<Question> getQuestions(int idQuestionnaire) throws DaoException;
	ArrayList<Answer> getAnswer(int idQuestion) throws DaoException;
	void addAttempt(Trainee trainee, Attempt attempt) throws DaoException;
}
