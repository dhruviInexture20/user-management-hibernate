package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.mysql.cj.x.protobuf.MysqlxCrud.Update;

import service.UserService;
import service.UserServiceImpl;
import utility.DataUtility;

/**
 * Servlet implementation class UpdatePasswordServlet
 */
public class UpdatePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(UpdatePasswordServlet.class);
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// UPDATE PASSWORD
		
		
		String email = request.getParameter("email");
		
		String newPassword = request.getParameter("password1");
		String newPassword2 = request.getParameter("password2");
		String msg = null;
		boolean isValid = true;
		if(!DataUtility.isPassword(newPassword)) {
			// invalid password format
			msg = "Password must contain one capital letter,one numerical and one special character";
			isValid = false;
		}
		else if(!newPassword.equals(newPassword2)) {
			// password doesn't match
			msg = "Both Password fields doesn't match";
			isValid = false;
		}
		
		if(isValid) {
			UserService userService = new UserServiceImpl();
			userService.resetPassword(email, newPassword);
			
			request.setAttribute("success", "password changed");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			
			
		}
		else {
			request.setAttribute("msg", msg);
			request.setAttribute("email", email);
			request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
		}
		
		
		
//		logger.info("email = " + email);
//		logger.info("password = " + newPassword);
//		UserService userService = new UserServiceImpl();
//		userService.resetPassword(email, newPassword);
		
//		response.sendRedirect("login.jsp");
	
	}

}
