package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UserBean;
import dao.UserDaoImpl;
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
		
		UserDaoImpl userDao = new UserDaoImpl();
		user = userDao.userLogin(email);
		// login as admin
		if(user != null && user.getRole().equals("admin")) {
			HttpSession session = request.getSession();
			session.setAttribute("adminData", user);
			response.sendRedirect("adminDashboard.jsp");
		}
		// login as user
		else if(user != null && user.getRole().equals("user")) {
			HttpSession session = request.getSession();
			session.setAttribute("userData", user);
			response.sendRedirect("welcome.jsp");
		}
		// invalid data
		else {
			ServletUtility.setErrorMessage("Invalid User", request, response);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		
		
		
	}
}
