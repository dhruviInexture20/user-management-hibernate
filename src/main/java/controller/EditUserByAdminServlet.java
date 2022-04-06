package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import bean.UserBean;
import service.UserService;
import service.UserServiceImpl;

/**
 * Servlet implementation class EditUserByAdminServlet
 */
public class EditUserByAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(EditUserByAdminServlet.class);
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userid = request.getParameter("userid");
		BasicConfigurator.configure();
		// get userdata
		logger.info("userid =" + userid);
		UserService userService = new UserServiceImpl();
		UserBean user = userService.getUserForEdit(Integer.parseInt(userid));
		
		request.setAttribute("userData", user);
		
		logger.info("forward to registration page");
		request.getRequestDispatcher("registration.jsp").forward(request, response);
		
		
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.sendRedirect("login.jsp");
	}

}
