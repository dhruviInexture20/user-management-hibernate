package controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import bean.AddressBean;
import bean.UserBean;
import dao.UserDao;
import dao.UserDaoImpl;
import utility.ServletUtility;


@MultipartConfig
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(RegistrationServlet.class);
	private UserDaoImpl userDao = new UserDaoImpl();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BasicConfigurator.configure();
		logger.info("in logger");
		
		String fname = request.getParameter("firstname");
		String lname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String confirm_password = request.getParameter("confirm_password");
		String designation = request.getParameter("designation");
		String birthdate = request.getParameter("birthdate");
		String gender = request.getParameter("gender");
		InputStream inputStream = null;
		Part filepart = request.getPart("profilepic");
		inputStream = filepart.getInputStream();
		
		String[] address = request.getParameterValues("address");
		String[] city = request.getParameterValues("city");
		String[] country = request.getParameterValues("country");
		String[] state = request.getParameterValues("state");
		String[] postalcode = request.getParameterValues("postal_code");
		
		UserBean user = new UserBean();
		user.setFname(fname);
		user.setLname(lname);
		user.setGender(gender);
		user.setDesignation(designation);
		user.setEmail(email);
		user.setPhone(phone);
		user.setPassword(password);
		user.setDob(birthdate);
		user.setProfilepic(inputStream);
		
		List<AddressBean> addressList = new ArrayList<>();
		for(int i = 0; i < city.length; i++) {
			AddressBean userAddress = new AddressBean();
			userAddress.setStreetAddress(address[i]);
			userAddress.setCity(city[i]);
			userAddress.setCountry(country[i]);
			userAddress.setPostalCode(postalcode[i]);
			userAddress.setState(state[i]);
			addressList.add(userAddress);	
		}
		user.setAddressList(addressList);
		
		UserDaoImpl userDao = new UserDaoImpl();
		int i = userDao.addUser(user);
		
		if(i > 0) {
			ServletUtility.setSuccessMessage("User registered sucessfully", request);
			logger.info("User added successfully");
		}
		else {
			ServletUtility.setErrorMessage("User not insterted", request, response);
			logger.error("User not inserted");
		}
		
		request.getRequestDispatcher("registration.jsp").forward(request, response);
		
	}

}
