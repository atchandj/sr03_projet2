package dao.super_user;

import beans.super_user.SuperUser;
import beans.trainee.Trainee;
import dao.DaoException;

public interface UserDataDao {
	Trainee getTrainee(String email) throws DaoException;
	SuperUser getSuperUser(String email) throws DaoException;
}