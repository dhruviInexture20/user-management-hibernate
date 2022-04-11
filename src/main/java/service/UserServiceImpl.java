package service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.mail.Address;
import javax.naming.AuthenticationException;
import javax.xml.crypto.Data;

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
import utility.PasswordSecurity;

public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
	
	
	public Set<String> validateUser(UserBean user, String confirmPass, String role) {
		
		BasicConfigurator.configure();
		
		Set<String> msg = new HashSet<String>();
		
		if(!DataUtility.isName(user.getFname())) {
			msg.add("Please add valid FirstName");
			logger.error("invalid fname");
		}
		if(!DataUtility.isName(user.getLname())) {
			msg.add("Please add valid LastName");	
			logger.error("invalid lname");
		}
		
		if(role.equals("newUser")){
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
		UserBean user = new UserBean();
		
		user = userDao.userLogin(email);
		if(user == null || user.getUserid() == 0) {
			return null;
		}
		else if(user.getPassword().equals(password)) {
			return user;
		}
		return null;
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
	
		List<Integer> oldAddressidList = oldAddressList.stream().map( oldAddress -> oldAddress.getAddressid()).collect(Collectors.toList());
		
		newAddressList.stream().forEach(address -> {
			
			if(address.getAddressid() == 0) {
				
				// add address
				addressDao.addAddress(address, user.getUserid());
				logger.info("add" + address.getAddressid());
			}
			else if(oldAddressidList.contains(address.getAddressid())) {
				// update address
				oldAddressidList.remove(oldAddressidList.indexOf(address.getAddressid()));
				addressDao.updateAddress(address);
				logger.info("update" + address.getAddressid());
			}
		});  
		
		// delete address
		oldAddressidList.stream().forEach( addressid -> addressDao.deleteAddressById(addressid));
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
		
		PasswordSecurity secure;
		try {
			secure = new PasswordSecurity();
			newPassword = secure.encrypt(newPassword);
			
		} catch (Exception e) {
			logger.error(e);
		}
		
		userDao.resetPass(email, newPassword);
		
		logger.info(email);
		logger.info(newPassword);
		
	}

	@Override
	public void saveOTP(String email, String otp) {
		
		UserDao userDao = new UserDaoImpl();
		userDao.storeOtp(email, otp);
		
	}

	@Override
	public int getOtpByEmail(String email) {
		
		UserDao userDao = new UserDaoImpl();
		String otp = userDao.getUserOTP(email);
		int otpInt;
		if(otp == null) {
			otpInt = 0;
		}
		else {
			otpInt = Integer.parseInt(otp);
		}
		return otpInt;
	}
}
