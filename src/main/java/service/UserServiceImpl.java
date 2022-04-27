package service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.AuthenticationException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import bean.AddressBean;
import bean.EmailMessageBean;
import bean.UserBean;
import dao.UserDao;
import dao.UserDaoImpl;
import utility.DataUtility;
import utility.EmailUtility;

public class UserServiceImpl implements UserService {
	
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	
	public Set<String> validateUser(UserBean user, String confirmPass, String role) {
		
		BasicConfigurator.configure();
		
		Set<String> msg = new HashSet<String>();
		
		if(!DataUtility.isName(user.getFname())) {
			msg.add("Please add valid FirstName");
		}
		if(!DataUtility.isName(user.getLname())) {
			msg.add("Please add valid LastName");	
		}
		
		if(role.equals("newUser")){
			if(!DataUtility.isEmail(user.getEmail())) {
				msg.add("Please enter valid Email");
			}
			else {
				UserDao dao = new UserDaoImpl();
				boolean isAvailable = dao.isEmailAvailable(user.getEmail());
				if(isAvailable) {
					msg.add("Email Already registered");
				}
			}
		}
		
		
		if(!DataUtility.isPassword(user.getPassword())) {
			msg.add("Invalid Password");
		}
		
		if(!confirmPass.equals(user.getPassword())) {
			msg.add("password doesn't match");
		}
		
		if(DataUtility.isNull(user.getGender())) {
			msg.add("Select Your Gender");
		}
		if(DataUtility.isNull(user.getDob())) {
			msg.add("Please Select your Birthdate");
		}
		if(DataUtility.isNull(user.getDesignation())) {
			msg.add("Please Select designation");
		}
		if(!DataUtility.isPhoneNo(user.getPhone())) {
			msg.add("invalid phone number");
		}
		
		List<AddressBean> addressList = user.getAddressList();
		
		addressList.stream().forEach(address -> {
			
			if(DataUtility.isNull(address.getStreetAddress())) {
				msg.add("Fill all street addresses");
			}
			if(DataUtility.isNull(address.getCity())) {
				msg.add("Select city in all address Fieldsets");
			}
			if(DataUtility.isNull(address.getState())) {
				msg.add("select state in all address Fieldsets");
			
			}
			if(DataUtility.isNull(address.getPostalCode())) {
				msg.add("Fill all postalCodes in all address Fieldsets");
			}
			if(DataUtility.isNull(address.getCountry())) {
				msg.add("select country in all address Fieldsets");
			}
		});
		
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
		UserBean user;
		
		user = userDao.getUser(email);
		if(user == null || user.getUserid() == 0) {
			return null;
		}
		else if(user.getPassword().equals(password)) {
			return user;
		}
		return null;
	}

//	@Override
//	public List<AddressBean> getUserAddress(int userid){
//
//		AddressDao addressDao = new AddressDaoImpl();
//		List<AddressBean> addressList = addressDao.getAddress(userid);
//		
//		return addressList;
//	}

	@Override
	public void updateUserData(UserBean user, List<AddressBean> oldAddressList) {
		
		BasicConfigurator.configure();
		
		UserDao userDao = new UserDaoImpl();
		
		// update user data
		userDao.updateUser(user);
	
	}

	
	@Override
	public void deleteUser(String userid) {
		
		UserDao userDao = new UserDaoImpl();
		userDao.deleteUser(Integer.parseInt(userid));
		
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
	public String sendOTPMail(String email) {
		
		String otp = DataUtility.generateOTP();
		logger.info("generated otp = " + otp);
		
		EmailMessageBean emailbean = new EmailMessageBean();
		
		emailbean.setTo(email);
		
		emailbean.setMessage("Your One Time Password is " + otp);
		
		try {
			EmailUtility emailUtility = new EmailUtility();
			emailUtility.sendMail(emailbean);
			saveOTP(email, otp);
			
		} catch (AuthenticationException e) {
			logger.error(e);
			
		}
		return otp;
	}

	@Override
	public void resetPassword(String email, String newPassword) {
		
		UserDao userDao = new UserDaoImpl();
		
		UserBean user = userDao.getUser(email);
		user.setPassword(newPassword);
		userDao.updateUser(user);
	}
		

	@Override
	public void saveOTP(String email, String otp) {
		
		UserDao userDao = new UserDaoImpl();
		
		UserBean user = userDao.getUser(email);
		user.setOtp(Integer.parseInt(otp));
		
		userDao.updateUser(user);
		
		
	}

	@Override
	public int getOtpByEmail(String email) {
		
		UserDao userDao = new UserDaoImpl();
		UserBean user = userDao.getUser(email);
		
		return user.getOtp();
	}
}
