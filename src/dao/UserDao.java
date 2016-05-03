package dao;

import beans.TemporaryUser;
import beans.super_user.SuperUser;
import beans.trainee.Trainee;

public interface UserDao {
	Trainee getTrainee(TemporaryUser tempUser) throws DaoException;
	SuperUser getSuperUser(TemporaryUser tempUser) throws DaoException;
}
