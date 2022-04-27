package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;


import service.UserService;
import service.UserServiceImpl;


public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BasicConfigurator.configure();
		PrintWriter out = response.getWriter();
		
		String userid = request.getParameter("userid");
		
		UserService userService = new UserServiceImpl();
		userService.deleteUser(userid);
		
		out.print("true");
		
	}

}
