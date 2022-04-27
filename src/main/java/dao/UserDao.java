package dao;

import java.util.List;

import bean.UserBean;

public interface UserDao {
	int addUser(UserBean user);

//	int getNextUserID();

	UserBean getUser(String email);

	List<UserBean> getListOfUsers() throws Exception;

	boolean isEmailAvailable(String email);

	void updateUser(UserBean user);

//	void deleteUser(String userid);

	UserBean getUserByUserID(int userid);

	boolean checkSecurityQnA(String email, String security_question, String security_answer);

//	void resetPass(String email, String newPassword);

//	void storeOtp(String email, String otp);

//	String getUserOTP(String email);

	void deleteUser(int userid);

}
