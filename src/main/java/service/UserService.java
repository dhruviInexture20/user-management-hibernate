package service;

import java.util.List;

import bean.AddressBean;
import bean.UserBean;

public interface UserService {

	List<String> validateUser(UserBean user, String confirmPass);

	int saveUser(UserBean user);

	UserBean getUser(String email, String password);

	List<AddressBean> getUserAddress(int userid);

	void updateUserData(UserBean user, List<AddressBean> oldAddressList);

	void deleteUser(String userid);

	UserBean getUserForEdit(int parseInt);

	
}