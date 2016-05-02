package dao;

import beans.SuperUser;
import beans.TemporaryUser;
import beans.Trainee;

public interface UserDao {
	Trainee getTrainee(TemporaryUser tempUser) throws DaoException;
	SuperUser getSuperUser(TemporaryUser tempUser) throws DaoException;
}
