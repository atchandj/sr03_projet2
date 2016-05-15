package dao.super_user;

import java.util.List;

import beans.trainee.Topic;
import dao.DaoException;

public interface TopicsManagementDao {
	List<Topic> getTopics() throws DaoException;
}