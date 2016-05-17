package dao.trainee;

import java.util.List;

import beans.trainee.Attempt;
import dao.DaoException;

public interface DisplayResultDao {
	public List<Attempt> getAttempts(int traineeId) throws DaoException;
}
