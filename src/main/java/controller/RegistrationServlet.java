package controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import bean.AddressBean;
import bean.UserBean;
import service.UserServiceImpl;



@MultipartConfig
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(RegistrationServlet.class);
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BasicConfigurator.configure();
		
		
		// getting user information from registration page
		String fname = request.getParameter("firstname");
		String lname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String confirm_password = request.getParameter("confirm_password");
		String designation = request.getParameter("designation");
		String birthdate = request.getParameter("birthdate");
		String gender = request.getParameter("gender");
		String s_question = request.getParameter("security_question");
		String s_answer = request.getParameter("security_answer");
		
		InputStream inputStream = null;
		
		Part filepart = request.getPart("profilepic");
		inputStream = filepart.getInputStream();
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int bytesRead = -1;

		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}
		byte[] imageBytes = outputStream.toByteArray();
		String base64Image = Base64.getEncoder().encodeToString(imageBytes);
		
		
		// getting all address field values
		String[] address = request.getParameterValues("address");
		String[] city = request.getParameterValues("city");
		String[] country = request.getParameterValues("country");
		String[] state = request.getParameterValues("state");
		String[] postalcode = request.getParameterValues("postal_code");
		
		// setting information to userBean
		UserBean user = new UserBean();
		user.setFname(fname);
		user.setLname(lname);
		user.setGender(gender);
		user.setDesignation(designation);
		user.setEmail(email);
		user.setPhone(phone);
		user.setPassword(password);
		user.setDob(birthdate);
//		user.setProfilepic(inputStream);
		user.setBase64Image(base64Image);
		user.setS_question(s_question);
		user.setS_answer(s_answer);
		
		
		// setting address list to user object
		List<AddressBean> addressList = new ArrayList<>();
		for(int i = 0; i < city.length; i++) {
			AddressBean userAddress = new AddressBean();
			userAddress.setStreetAddress(address[i]);
			userAddress.setCity(city[i]);
			userAddress.setCountry(country[i]);
			userAddress.setPostalCode(postalcode[i]);
			userAddress.setState( i >= state.length ? null  : state[i]);
			addressList.add(userAddress);
		}
		user.setAddressList(addressList);
		
		// perform validation
		UserServiceImpl userService = new UserServiceImpl();
		Set<String> msg = userService.validateUser(user, confirm_password, "newUser");
		
		if(msg.isEmpty()) {
			// no error
			int i = userService.saveUser(user);
			
			// successfully added
			if(i > 0) {
				request.setAttribute("success", "User registered sucessfully");
				logger.info("User added successfully");
			}
			// user not added
			else {
				request.setAttribute("error", "User not inserted");
				logger.error("User not inserted");
			}
		}
		else {
			// errors 

//			request.setAttribute("role", "newUser");
//			request.setAttribute("userData", user);
			request.setAttribute("errorMsg", msg);
		}
		request.getRequestDispatcher("registration.jsp").forward(request, response);
	}

}
