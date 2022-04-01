package service;

import java.util.List;

import bean.UserBean;

public interface UserService {

	List<String> validateUser(UserBean user, String confirmPass);

	int saveUser(UserBean user);

	UserBean getUser(String email, String password);

	
}