package dao;

import bean.UserBean;

public interface UserDao {
	public int addUser(UserBean user);
	public int getNextUserID();
	public UserBean userLogin(String email);
	boolean checkEmailAvailability(String email);
	
}
