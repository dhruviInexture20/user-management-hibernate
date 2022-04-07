package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import bean.AddressBean;
import bean.UserBean;
import service.UserService;
import service.UserServiceImpl;

/**
 * Servlet implementation class EditProfileServlet
 */

@MultipartConfig
public class EditProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(EditProfileServlet.class);
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BasicConfigurator.configure();
		
		HttpSession session = request.getSession(false);
		UserBean user = new UserBean();
		
		String role = (String) session.getAttribute("role");
		logger.info("role = " + role);
		
//		if(role.equals("admin")) {
//			user = (UserBean) request.getAttribute("userData");
//			logger.info(user == null);
//		}
//		else if(role.equals("user")) {
//			user = (UserBean) session.getAttribute("userData");
//		}
		
		user = (UserBean) session.getAttribute("userData");
		
		logger.info(user == null);
		
		user.setFname(request.getParameter("firstname"));
		user.setLname(request.getParameter("lastname"));
		user.setPassword(request.getParameter("password"));
		user.setPhone(request.getParameter("phone"));
		user.setDesignation(request.getParameter("designation"));
		user.setDob(request.getParameter("birthdate"));
		user.setS_question(request.getParameter("security_question"));
		user.setS_answer(request.getParameter("security_answer"));
		
		InputStream inputStream = null;
		Part filepart = request.getPart("profilepic");
		inputStream = filepart.getInputStream();
//		user.setProfilepic(inputStream);
		logger.info("profile = " + (inputStream == null));
//		
		if(inputStream != null) {
			user.setProfilepic(inputStream);
		}
		
		String[] addressid = request.getParameterValues("addressid");
		String[] address = request.getParameterValues("address");
		String[] city = request.getParameterValues("city");
		String[] country = request.getParameterValues("country");
		String[] state = request.getParameterValues("state");
		String[] postalcode = request.getParameterValues("postal_code");
		
		List<AddressBean> oldAddressList = user.getAddressList();
		
		List<AddressBean> newAddressList = new ArrayList<>();
		
		
		for(int i = 0; i < city.length; i++) {
			AddressBean userAddress = new AddressBean();
			logger.info("addressid = " + addressid[i]);
			if(addressid[i].isEmpty()) {
				addressid[i] = "0";
			}
			logger.info("addressid = " + addressid[i]);
			
			userAddress.setAddressid(Integer.parseInt(addressid[i]));
			userAddress.setStreetAddress(address[i]);
			userAddress.setCity(city[i]);
			userAddress.setCountry(country[i]);
			userAddress.setPostalCode(postalcode[i]);
			userAddress.setState(state[i]);
			newAddressList.add(userAddress);	
		}
		user.setAddressList(newAddressList);
		
		logger.info("new address list = "+ newAddressList);
		logger.info("old address = " + oldAddressList);
		
		// update user in database 
		
		UserService userService = new UserServiceImpl();
		userService.updateUserData(user, oldAddressList);
		
		request.setAttribute("success" , "user successfully updated");
		request.setAttribute("userData", user);
		request.getRequestDispatcher("registration.jsp").forward(request, response);
		
		
	}
}
