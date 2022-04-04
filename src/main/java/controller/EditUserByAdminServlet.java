package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.UserBean;
import service.UserService;
import service.UserServiceImpl;

/**
 * Servlet implementation class EditUserByAdminServlet
 */
public class EditUserByAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userid = request.getParameter("userid");
		
		// get userdata
		
		UserService userService = new UserServiceImpl();
		UserBean user = userService.getUserForEdit(Integer.parseInt(userid));
		
	}

}
