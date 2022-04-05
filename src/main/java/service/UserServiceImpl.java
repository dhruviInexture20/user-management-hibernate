package service;


import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import bean.AddressBean;
import bean.EmailMessageBean;
import bean.UserBean;
import dao.AddressDao;
import dao.AddressDaoImpl;
import dao.UserDao;
import dao.UserDaoImpl;
import utility.DataUtility;
import utility.EmailUtility;

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

	@Override
	public List<AddressBean> getUserAddress(int userid){

		AddressDao addressDao = new AddressDaoImpl();
		List<AddressBean> addressList = addressDao.getAddress(userid);
		
		return addressList;
	}

	@Override
	public void updateUserData(UserBean user, List<AddressBean> oldAddressList) {
		
		BasicConfigurator.configure();
		
		UserDao userDao = new UserDaoImpl();
		AddressDao addressDao = new AddressDaoImpl();
		
		List<AddressBean> newAddressList = user.getAddressList();
		
		// update user data
		userDao.updateUser(user);
		
		// array of old user address id 
		List<Integer> oldAddressidList = new ArrayList<>();
		for( int i = 0; i < oldAddressList.size(); i++) {
			oldAddressidList.add(oldAddressList.get(i).getAddressid());
		}
		logger.info(oldAddressidList + "old addressid");
		
		AddressBean address = new AddressBean();
		
		for (int i = 0; i < newAddressList.size(); i++) {    
			address = newAddressList.get(i);
			logger.info(address.getAddressid());
			if(address.getAddressid() == 0) {
				// add
				logger.info("add" + i);
				
				addressDao.addAddress(address, user.getUserid());
				
			}
			else if(oldAddressidList.contains(address.getAddressid())){
				// update
				logger.info("update" + i);
				oldAddressidList.remove(oldAddressidList.indexOf(address.getAddressid()));
				addressDao.updateAddress(address);
			}
		}
		
		for(int i = 0; i < oldAddressidList.size(); i++) {
			// delete
			
			addressDao.deleteAddressById(oldAddressidList.get(i));
		}
		
		logger.info(oldAddressidList + " to delete");
	}

	
	@Override
	public void deleteUser(String userid) {
		
		UserDao userDao = new UserDaoImpl();
		userDao.deleteUser(userid);
		
	}

	@Override
	public UserBean getUserForEdit(int userid) {
		
		UserDao userDao = new UserDaoImpl();
		UserBean user = userDao.getUserByUserID(userid);
		
		return user;
	}

	@Override
	public String authUserForgetPass(String email, String security_question, String security_answer) {
		
		UserDao userDao = new UserDaoImpl();
		String msg = null;
		if(!userDao.isEmailAvailable(email)) {
			msg = "Email is not registered";
		}
		else if(!userDao.checkSecurityQnA(email, security_question, security_answer)){
			msg = "Wrong security question or answer";
		}
		
	return msg;
		
		
	}

	@Override
	public void sendOTPMail(String email) {
		
		String otp = DataUtility.generateOTP();
		logger.info("generated otp = " + otp);
		
		EmailMessageBean emailbean = new EmailMessageBean();
		
		emailbean.setTo(email);
		
		emailbean.setMessage("Your One Time Password is " + otp);
		
		try {
			EmailUtility emailUtility = new EmailUtility();
			emailUtility.sendMail(emailbean);
		} catch (AuthenticationException e) {
			logger.error(e);
			
		}
		
//
	}
}
