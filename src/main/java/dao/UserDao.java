package dao;

import java.sql.ResultSet;

import bean.UserBean;

public interface UserDao {
	public int addUser(UserBean user);
	public int getNextUserID();
	public UserBean userLogin(String email);
	public ResultSet getListOfUsers();
	boolean isEmailAvailable(String email);
	
}
