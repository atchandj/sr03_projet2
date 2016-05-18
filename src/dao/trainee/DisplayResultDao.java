package dao.trainee;

import java.util.ArrayList;
import java.util.List;

import beans.trainee.Answer;
import beans.trainee.Attempt;
import dao.DaoException;

public interface DisplayResultDao {
	public List<Attempt> getAttemptsList(int traineeId) throws DaoException;
	public Attempt getAttempt(int traineeId, int attemptId) throws DaoException;
	public ArrayList<Answer> getAttemptedAnswers(int attemptId) throws DaoException;
}
