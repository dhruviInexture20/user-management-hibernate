package service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import bean.UserBean;
import dao.UserDao;
import dao.UserDaoImpl;
import utility.DataUtility;

public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
	
	@Override
	public List<String> validateUser(UserBean user, String confirmPass) {
		
		BasicConfigurator.configure();
		
		List<String> msg = new ArrayList<>();
		
		if(!DataUtility.isName(user.getFname())) {
			msg.add("Please add valid FirstName");
			logger.error("invalid fname");
		}
		if(!DataUtility.isName(user.getLname())) {
			msg.add("Please add valid LastName");	
			logger.error("invalid lname");
		}
		if(!DataUtility.isEmail(user.getEmail())) {
			msg.add("Please enter valid Email");
			logger.error("invalid Email");
		}
		else {
			UserDao dao = new UserDaoImpl();
			boolean isAvailable = dao.isEmailAvailable(user.getEmail());
			if(isAvailable) {
				msg.add("Email Already registered");
				logger.error("Email Already Exist");
			}
		}
		
		
		if(!DataUtility.isPassword(user.getPassword())) {
			msg.add("Invalid Password");
			logger.error("invalid password" + user.getPassword());
		}
		
		
		if(!confirmPass.equals(user.getPassword())) {
			msg.add("password doesn't match");
			logger.error("Invalid confirm password");
		}
		
		if(DataUtility.isNull(user.getGender())) {
			msg.add("Select Your Gender");
			logger.error("gender not selected");
		}
		if(DataUtility.isNull(user.getDob())) {
			msg.add("Please Select your Birthdate");
			logger.error("enter your birthdate");
		}
		
		return msg;
	}

	@Override
	public int saveUser(UserBean user) {
		
		UserDao userDao = new UserDaoImpl();
		int i = userDao.addUser(user);
		
		return i;	
	}
	
	@Override
	public UserBean getUser(String email, String password) {
		
		UserDao userDao = new UserDaoImpl();
		UserBean user = new UserBean();
		// get true is email exist
		
		if(userDao.isEmailAvailable(email)) {
			// user exist
			// getting user details
			user = userDao.userLogin(email);
			logger.info("password = " + user.getPassword());
			if(!password.equals(user.getPassword())) {
				user = null;
			}
		}
		return user;
	}
}
