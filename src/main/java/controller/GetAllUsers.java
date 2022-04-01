package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import bean.UserBean;
import service.AdminServiceImpl;

/**
 * Servlet implementation class GetAllUsers
 */
public class GetAllUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(GetAllUsers.class);
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BasicConfigurator.configure();
		PrintWriter out = response.getWriter();
		
		AdminServiceImpl adminService = new AdminServiceImpl();
		
		try {
			List<UserBean> users  = adminService.getList();
			logger.info(users);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			JsonObject json = new JsonObject();
			json.add("data",gson.toJsonTree(users));
				
			logger.info(json);
			out.print(json);
			
		} catch (Exception e) {
			logger.error(e);
		}
		
	}

}
