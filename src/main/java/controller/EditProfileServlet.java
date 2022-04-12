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
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import bean.AddressBean;
import bean.UserBean;
import service.UserServiceImpl;

/**
 * Servlet implementation class EditProfileServlet
 */

@MultipartConfig
public class EditProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(EditProfileServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BasicConfigurator.configure();

		HttpSession session = request.getSession(false);
		UserBean oldUser;

		String role = (String) session.getAttribute("role");
		logger.info("role = " + role);

		oldUser = (UserBean) session.getAttribute("userData");

		UserBean user = new UserBean();

		user.setUserid(oldUser.getUserid());
		user.setEmail(oldUser.getEmail());
		user.setFname(request.getParameter("firstname"));
		user.setLname(request.getParameter("lastname"));
		user.setPassword(request.getParameter("password"));
		user.setPhone(request.getParameter("phone"));
		user.setGender(request.getParameter("gender"));
		user.setDesignation(request.getParameter("designation"));
		user.setDob(request.getParameter("birthdate"));
		user.setS_question(request.getParameter("security_question"));
		user.setS_answer(request.getParameter("security_answer"));
		String confirm_password = request.getParameter("confirm_password");

		InputStream inputStream = null;
		Part filepart = request.getPart("profilepic");

		if (filepart.getSize() > 0) {
			// update
			inputStream = filepart.getInputStream();

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead = -1;

			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			byte[] imageBytes = outputStream.toByteArray();
			String base64Image = Base64.getEncoder().encodeToString(imageBytes);
			user.setBase64Image(base64Image);
		} else {
			user.setBase64Image(oldUser.getBase64Image());
		}

		String[] addressid = request.getParameterValues("addressid");
		String[] address = request.getParameterValues("address");
		String[] city = request.getParameterValues("city");
		String[] country = request.getParameterValues("country");
		String[] state = request.getParameterValues("state");
		String[] postalcode = request.getParameterValues("postal_code");

		List<AddressBean> oldAddressList = oldUser.getAddressList();

		List<AddressBean> newAddressList = new ArrayList<>();

		for (int i = 0; i < city.length; i++) {
			AddressBean userAddress = new AddressBean();
			logger.info("addressid = " + addressid[i]);
			if (addressid[i].isEmpty()) {
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

		// perform validation
		UserServiceImpl userService = new UserServiceImpl();
		Set<String> msg = userService.validateUser(user, confirm_password, role);

		if (msg.isEmpty()) {
			// no error
			// update user in database
			userService.updateUserData(user, oldAddressList);
			request.setAttribute("success", "user successfully updated");
			request.setAttribute("userData", user);

		} else {
			request.setAttribute("userData", user);
			request.setAttribute("errorMsg", msg);
		}

		request.getRequestDispatcher("registration.jsp").forward(request, response);

	}
}
