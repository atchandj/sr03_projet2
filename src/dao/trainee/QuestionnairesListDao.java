package dao.trainee;

import java.util.ArrayList;
import java.util.List;

import beans.trainee.Answer;
import beans.trainee.Attempt;
import beans.trainee.Question;
import beans.trainee.Topic;
import beans.trainee.Trainee;
import dao.DaoException;

public interface QuestionnairesListDao {
	public List<Topic> getActivatedQuestionnaire() throws DaoException;
	public List<Question> getQuestions(int idQuestionnaire) throws DaoException;
	public ArrayList<Answer> getAnswer(int idQuestion) throws DaoException;
	public void addAttempt(Trainee trainee, Attempt attempt) throws DaoException;
}
