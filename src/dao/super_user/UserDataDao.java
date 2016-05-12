package dao.super_user;

import java.util.List;

import beans.super_user.SuperUser;
import beans.trainee.Attempt;
import beans.trainee.Trainee;
import dao.DaoException;

public interface UserDataDao {
	Trainee getTrainee(String email) throws DaoException;
	SuperUser getSuperUser(String email) throws DaoException;
	void updateTrainee(Trainee trainee) throws DaoException;
	void updateSuperUser(SuperUser superUser) throws DaoException;
    List<Attempt> getAttemptsOfATrainee(String traineeEMail) throws DaoException;
}