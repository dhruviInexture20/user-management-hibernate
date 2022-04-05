package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserBean user = new UserBean();
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserService userService = new UserServiceImpl();
		user = userService.getUser(email, password);
		
		// login as admin
		if(user != null && user.getRole().equals("admin")) {
			HttpSession session = request.getSession();
			session.setAttribute("adminData", user);
			response.sendRedirect("adminDashboard.jsp");
		}
		
		// login as user
		else if(user != null && user.getRole().equals("user")) {
			HttpSession session = request.getSession();
			//session.setAttribute("userData", user);
			request.setAttribute("userData", user);
			request.getRequestDispatcher("welcome.jsp").forward(request, response);
		}
		// invalid data
		else {
			//ServletUtility.setErrorMessage("Invalid User", request);
			request.setAttribute("error", "Invalid User");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}
