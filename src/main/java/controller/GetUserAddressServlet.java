package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bean.AddressBean;
import service.UserService;
import service.UserServiceImpl;

/**
 * Servlet implementation class GetUserAddressServlet
 */
public class GetUserAddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO get user address
		
		response.setContentType("text/html");
		PrintWriter out =  response.getWriter();
		
		UserService service = new UserServiceImpl();
		
		String useridString = request.getParameter("userid");
		int userid = Integer.parseInt(useridString);
		
		List<AddressBean> addressList = service.getUserAddress(userid);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		out.print(gson.toJsonTree(addressList));
	}

}
