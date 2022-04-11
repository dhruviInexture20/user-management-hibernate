package service;

import java.util.List;
import java.util.Set;

import bean.AddressBean;
import bean.UserBean;

public interface UserService {

	Set<String> validateUser(UserBean user, String confirmPass, String role);

	int saveUser(UserBean user);

	UserBean getUser(String email, String password);

	List<AddressBean> getUserAddress(int userid);

	void updateUserData(UserBean user, List<AddressBean> oldAddressList);

	void deleteUser(String userid);

	UserBean getUserForEdit(int parseInt);

	String authUserForgetPass(String email, String security_question, String security_answer);

	String sendOTPMail(String email);

	void resetPassword(String email, String newPassword);

	void saveOTP(String email, String otp);

	int getOtpByEmail(String email);

	
}