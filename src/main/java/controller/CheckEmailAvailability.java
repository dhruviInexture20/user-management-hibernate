package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDaoImpl;


public class CheckEmailAvailability extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		
		UserDaoImpl userDao = new UserDaoImpl();
		if(userDao.isEmailAvailable( email)) {
			// email already exist
			out.print("false");
		}else {
			// email not exist
			out.print("true");
		}
	}

}
