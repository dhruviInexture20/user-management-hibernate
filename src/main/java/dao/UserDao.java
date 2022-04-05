package dao;

import java.sql.ResultSet;

import bean.UserBean;

public interface UserDao {
	public int addUser(UserBean user);
	public int getNextUserID();
	public UserBean userLogin(String email);
	public ResultSet getListOfUsers();
	boolean isEmailAvailable(String email);
	public void updateUser(UserBean user);
	public void deleteUser(String userid);
	public UserBean getUserByUserID(int userid);
	public boolean checkSecurityQnA(String email, String security_question, String security_answer);
	
}
