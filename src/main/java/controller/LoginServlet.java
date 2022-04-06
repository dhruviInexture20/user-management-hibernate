package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.mysql.cj.log.Log;

import bean.UserBean;
import dao.UserDao;
import dao.UserDaoImpl;
import service.UserService;
import service.UserServiceImpl;
import utility.ServletUtility;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(LoginServlet.class);
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		UserBean user = new UserBean();
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserService userService = new UserServiceImpl();
		UserBean user = userService.getUser(email, password);
		
		logger.info("null user =" + (user == null));
		//logger.info("user email =" + user.getEmail());
		
		
		if(user == null) {
			request.setAttribute("error", "Invalid User");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		
		// login as admin
		else if(user != null && user.getRole().equals("admin")) {
			HttpSession session = request.getSession();
			session.setAttribute("role", "admin");
			response.sendRedirect("adminDashboard.jsp");
		}
		
		// login as user
		else if(user != null && user.getRole().equals("user")) {
			HttpSession session = request.getSession();
			session.setAttribute("role", "user");
			session.setAttribute("userData", user);
//			request.getRequestDispatcher("welcome.jsp").forward(request, response);
			response.sendRedirect("welcome.jsp");
		}
	}
}
