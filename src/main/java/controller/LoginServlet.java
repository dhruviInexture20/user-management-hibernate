package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UserBean;
import service.UserService;
import service.UserServiceImpl;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		UserBean user = new UserBean();
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserService userService = new UserServiceImpl();
		UserBean user = userService.getUser(email, password);
		
		
		if(user == null) {
			request.setAttribute("error", "Invalid User");
			request.setAttribute("loginEmail", email);
			request.setAttribute("loginPass", password);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		
		// login as admin
		else if(user.getRole().equals("admin")) {
			HttpSession session = request.getSession();
			session.setAttribute("role", "admin");
			response.sendRedirect("adminDashboard.jsp");
		}
		
		// login as user
		else if( user.getRole().equals("user")) {
			HttpSession session = request.getSession();
			session.setAttribute("role", "user");
			session.setAttribute("userData", user);
			response.sendRedirect("welcome.jsp");
		}
	}
}
